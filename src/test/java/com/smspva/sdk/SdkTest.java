package com.smspva.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SdkTest {
    static final String SECRET="java-local-+&?=/";
    static final ObjectMapper JSON=new ObjectMapper().enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS).enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);
    static JsonNode cases() throws IOException { try(InputStream input=SdkTest.class.getResourceAsStream("/cases.json")) { return JSON.readTree(input); } }
    static JsonNode contracts() throws IOException { try(InputStream input=SmsPva.class.getResourceAsStream("contracts.json")) { return JSON.readTree(input).get("operations"); } }
    static class Fake implements HttpTransport {
        int status=200; String body="{}"; IOException failure;
        boolean interrupted; final List<Request> requests=new ArrayList<>();
        @Override public Response execute(Request request) throws IOException, InterruptedException {
            requests.add(request);
            if(interrupted) throw new InterruptedException();
            if(failure!=null) throw failure;
            return new Response(status,body);
        }
    }
    static Map<String,String> query(String raw) {
        Map<String,String> values=new LinkedHashMap<>();
        if(raw==null) return values;
        for(String pair:raw.split("&")) { String[] pieces=pair.split("=",2); values.put(URLDecoder.decode(pieces[0],StandardCharsets.UTF_8), URLDecoder.decode(pieces[1],StandardCharsets.UTF_8)); }
        return values;
    }
    static JsonNode invoke(SmsPva client, JsonNode item) throws Exception {
        String family=item.path("family").asText(), method=item.path("jsName").asText();
        Object resource=SmsPva.class.getMethod(family).invoke(client);
        Class<?> request;
        try { request=Class.forName("com.smspva.sdk.Requests$"+Character.toUpperCase(family.charAt(0))+family.substring(1)+Character.toUpperCase(method.charAt(0))+method.substring(1)); }
        catch(ClassNotFoundException e) { return (JsonNode)resource.getClass().getMethod(method).invoke(resource); }
        var components=request.getRecordComponents(); Object[] values=new Object[components.length]; Class<?>[] types=new Class<?>[components.length];
        for(int i=0;i<components.length;i++) {
            types[i]=components[i].getType(); JsonNode value=item.path("params").get(components[i].getName());
            values[i]=value==null || value.isNull()?null:types[i]==String.class?value.asText():value.longValue();
        }
        return (JsonNode)resource.getClass().getMethod(method,request).invoke(resource,request.getDeclaredConstructor(types).newInstance(values));
    }
    @Test void all56NamedMethodsPreserveWireAndJsonValues() throws Exception {
        Fake http=new Fake(); JsonNode contracts=contracts();
        try(SmsPva client=new SmsPva(SECRET,Duration.ofSeconds(30),http,Map.of())) {
            for(JsonNode item:cases()) {
                http.body=item.get("response").toString();
                JsonNode response=invoke(client,item);
                assertEquals(item.get("response"),response,item.get("key").asText());
                var request=http.requests.get(http.requests.size()-1); JsonNode op=contracts.get(item.get("key").asText());
                assertEquals(op.get("method").asText(),request.method());
                String expected=op.get("path").asText();
                Map<String,String> expectedQuery=new LinkedHashMap<>();
                if(op.has("action")) expectedQuery.put("method",op.get("action").asText());
                for(JsonNode parameter:op.get("params")) {
                    String name=parameter.get("name").asText(); JsonNode value=item.path("params").get(name);
                    if(value==null || value.isNull() || parameter.path("omitEmpty").asBoolean() && value.asText().isEmpty()) continue;
                    if(parameter.get("in").asText().equals("path")) expected=expected.replace("{"+name+"}",java.net.URLEncoder.encode(value.asText(),StandardCharsets.UTF_8).replace("+","%20"));
                    else if(parameter.get("in").asText().equals("query")) expectedQuery.put(name,value.asText());
                }
                boolean auth=!op.get("auth").asText().equals("none");
                if(!item.get("family").asText().equals("activation") && auth) expectedQuery.put("apikey",SECRET);
                assertEquals(expected,request.uri().getRawPath());
                assertEquals(expectedQuery,query(request.uri().getRawQuery()));
                assertEquals(item.get("family").asText().equals("activation") && auth?SECRET:null,request.headers().get("apikey"));
                assertFalse(request.toString().contains(SECRET));
            }
            assertEquals(56,http.requests.size());
        }
    }
    @Test void pendingAndNoStockRemainNormalResults() {
        Fake http=new Fake();
        try(SmsPva client=new SmsPva(SECRET,Duration.ofSeconds(30),http,Map.of())) {
            http.status=202; http.body="{\"statusCode\":202,\"error\":{\"type\":\"SMS_NOT_YET_RECEIVED\"}}";
            assertTrue(SmsPva.isPending(client.activation().receiveSms(new Requests.ActivationReceiveSms(123L,null))));
            http.status=200; http.body="{\"status\":2}";
            assertTrue(SmsPva.isNoStock(client.email().createOrder(new Requests.EmailCreateOrder("test","week",1L,null))));
        }
    }
    @Test void publicCallsOptionalAuthAndEncoding() {
        Fake http=new Fake(); http.body="{\"status\":1,\"data\":[]}";
        try(SmsPva client=new SmsPva(SECRET,Duration.ofSeconds(30),http,Map.of())) {
            client.email().getProviders(); assertFalse(query(http.requests.get(0).uri().getRawQuery()).containsKey("apikey"));
            client.email().getData(new Requests.EmailGetData("")); assertFalse(query(http.requests.get(1).uri().getRawQuery()).containsKey("pcode"));
            client.email().getData(new Requests.EmailGetData("a +&?/中")); assertEquals("a +&?/中",query(http.requests.get(2).uri().getRawQuery()).get("pcode"));
            client.rental().getCountries(); assertFalse(query(http.requests.get(3).uri().getRawQuery()).containsKey("apikey"));
        }
    }
    @Test void apiAndProtocolErrorsRetainCodesWithoutLeakingKeysInMessages() {
        Fake http=new Fake();
        try(SmsPva client=new SmsPva(SECRET,Duration.ofSeconds(30),http,Map.of())) {
            http.status=402; http.body="{\"statusCode\":402,\"error\":{\"type\":\"NO_BALANCE\"}}";
            ApiException failure=assertThrows(ApiException.class,()->client.activation().getBalance());
            assertEquals(402,failure.httpStatus()); assertEquals("NO_BALANCE",failure.code().asText()); assertEquals(402,failure.response().get("statusCode").asInt());
            assertFalse(failure.getMessage().contains(SECRET)); assertNull(failure.getCause());
            http.status=200;
            for(String value:List.of("<html>oops</html>","[]","{}","{\"statusCode\":\"200\"}","{\"statusCode\":true}","{\"statusCode\":200} garbage","{\"statusCode\":200} {}")) {
                http.body=value; assertThrows(ProtocolException.class,()->client.activation().getBalance());
            }
            http.body="{\"status\":0,\"errorid\":12}";
            assertEquals(12,assertThrows(ApiException.class,()->client.email().getProviders()).code().asInt());
        }
    }
    @Test void validationMakesNoRequest() {
        Fake http=new Fake();
        try(SmsPva client=new SmsPva(null,Duration.ofSeconds(30),http,Map.of())) {
            assertThrows(ValidationException.class,()->client.activation().getBalance());
            assertThrows(ValidationException.class,()->client.email().createOrder(new Requests.EmailCreateOrder(null,"week",1L,null)));
            assertThrows(ValidationException.class,()->client.email().createOrder(new Requests.EmailCreateOrder("test","invalid",1L,null)));
            assertThrows(ValidationException.class,()->client.activation().getOperators(new Requests.ActivationGetOperators("..")));
            assertEquals(0,http.requests.size());
        }
        assertThrows(ValidationException.class,()->new SmsPva("bad\nkey"));
        assertThrows(ValidationException.class,()->new SmsPva(null,Duration.ZERO,http,Map.of()));
        assertThrows(ValidationException.class,()->new SmsPva(null,Duration.ofSeconds(1),http,Map.of("activation","https://user:secret@example.com")));
    }
    @Test void timeoutAndInterruptionNeverRetryAndPreserveUnknownOutcome() {
        Fake http=new Fake(); http.failure=new SocketTimeoutException("URL secret must not escape");
        try(SmsPva client=new SmsPva(SECRET,Duration.ofSeconds(30),http,Map.of())) {
            Requests.ActivationGetNumber params=new Requests.ActivationGetNumber("US","test",null);
            TransportException timeout=assertThrows(TransportException.class,()->client.activation().getNumber(params));
            assertEquals("timeout",timeout.kind()); assertTrue(timeout.outcomeUnknown()); assertNull(timeout.getCause()); assertEquals(1,http.requests.size());
            http.failure=null; http.interrupted=true;
            try { assertEquals("interrupted",assertThrows(TransportException.class,()->client.activation().getBalance()).kind()); assertTrue(Thread.currentThread().isInterrupted()); }
            finally { Thread.interrupted(); }
            assertEquals(2,http.requests.size());
        }
    }
    @Test void realHttpTransportRefusesRedirectsCookiesAndAutomaticGetReplay() throws Exception {
        AtomicInteger requests=new AtomicInteger(); List<String> paths=new ArrayList<>();
        HttpServer server=HttpServer.create(new InetSocketAddress("127.0.0.1",0),0);
        server.createContext("/",exchange->{
            requests.incrementAndGet(); paths.add(exchange.getRequestURI().getPath());
            assertNull(exchange.getRequestHeaders().getFirst("Cookie"));
            if(exchange.getRequestURI().getPath().endsWith("balance")) {
                exchange.getResponseHeaders().set("Location","/destination"); exchange.getResponseHeaders().set("Set-Cookie","test=secret");
                byte[] body="{\"statusCode\":302}".getBytes(StandardCharsets.UTF_8); exchange.sendResponseHeaders(302,body.length); exchange.getResponseBody().write(body);
            }
            exchange.close();
        });
        server.start();
        String base="http://127.0.0.1:"+server.getAddress().getPort();
        try(SmsPva client=new SmsPva(SECRET,Duration.ofMillis(500),new ApacheTransport(),Map.of("activation",base))) {
            assertEquals(302,assertThrows(ApiException.class,()->client.activation().getBalance()).httpStatus());
            assertEquals(1,requests.get());
            Requests.ActivationGetNumber params=new Requests.ActivationGetNumber("US","test",null);
            TransportException error=assertThrows(TransportException.class,()->client.activation().getNumber(params));
            assertTrue(error.outcomeUnknown()); assertEquals(2,requests.get());
            assertFalse(paths.contains("/destination"));
        } finally { server.stop(0); }
    }
}
