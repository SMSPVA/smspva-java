package com.smspva.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.node.IntNode;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.Set;

/** Synchronous Java client. Owns and closes its configured transport. */
public final class SmsPva implements AutoCloseable {
    private static final ObjectMapper JSON = new ObjectMapper()
        .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
        .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
        .enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);
    private static final JsonNode CONTRACTS = loadContracts();
    private final String apiKey;
    private final HttpTransport http;
    private final Duration timeout;
    private final Map<String,String> baseUrls;
    private final Activation activation;
    private final Rental rental;
    private final Email email;

    public SmsPva() { this(null); }
    public SmsPva(String apiKey) { this(apiKey, Duration.ofSeconds(30), new ApacheTransport(), Map.of()); }
    public SmsPva(String apiKey, Duration timeout, HttpTransport http, Map<String,String> baseUrls) {
        if (apiKey!=null && (apiKey.isEmpty() || apiKey.indexOf('\r')>=0 || apiKey.indexOf('\n')>=0)) throw new ValidationException("apiKey must be nonempty without line breaks");
        validTimeout(timeout);
        if(http==null || baseUrls==null) throw new ValidationException("Transport and base URLs must not be null");
        this.apiKey=apiKey; this.timeout=timeout; this.http=http;
        this.baseUrls=new LinkedHashMap<>();
        baseUrls.forEach((family,url)->{
            if(!Set.of("activation","rental","email").contains(family)) throw new ValidationException("Unknown API family");
            this.baseUrls.put(family, validBase(url));
        });
        activation=new Activation(this); rental=new Rental(this); email=new Email(this);
    }
    public Activation activation() { return activation; }
    public Rental rental() { return rental; }
    public Email email() { return email; }
    private static JsonNode loadContracts() {
        try(InputStream input=SmsPva.class.getResourceAsStream("contracts.json")) {
            if(input==null) throw new IllegalStateException("Missing bundled SMSPVA contracts");
            return JSON.readTree(input).get("operations");
        } catch(IOException e) { throw new IllegalStateException("Invalid bundled SMSPVA contracts"); }
    }
    private static void validTimeout(Duration timeout) {
        if(timeout==null || timeout.isNegative() || timeout.isZero() || timeout.compareTo(Duration.ofMillis(Integer.MAX_VALUE))>0 || timeout.toMillis()<1) throw new ValidationException("timeout must be between 1 and 2147483647 milliseconds");
    }
    private static String validBase(String value) {
        try {
            URI uri=URI.create(value);
            if(!Set.of("http","https").contains(uri.getScheme()) || uri.getHost()==null || uri.getRawUserInfo()!=null || uri.getRawQuery()!=null || uri.getRawFragment()!=null) throw new IllegalArgumentException();
            return value.endsWith("/") ? value.substring(0,value.length()-1) : value;
        } catch(RuntimeException e) { throw new ValidationException("Base URL must be HTTP(S) without credentials, query or fragment"); }
    }
    private static String encode(String value) { return URLEncoder.encode(value,StandardCharsets.UTF_8).replace("+","%20"); }
    private static void validate(JsonNode param, Object value) {
        String name=param.path("name").asText(); JsonNode schema=param.path("schema");
        if(schema.path("type").asText().equals("integer") && !(value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long)) throw new ValidationException("Invalid integer parameter: "+name);
        if(schema.path("type").asText().equals("string") && !(value instanceof String)) throw new ValidationException("Invalid string parameter: "+name);
        if(schema.has("enum")) {
            boolean matches=false;
            for(JsonNode item:schema.get("enum")) if(item.isTextual()? item.asText().equals(value) : value instanceof Number && item.longValue()==((Number)value).longValue()) matches=true;
            if(!matches) throw new ValidationException("Invalid enum value for parameter: "+name);
        }
        if(value instanceof Number number) {
            if(schema.has("minimum") && number.longValue()<schema.get("minimum").longValue() || schema.has("maximum") && number.longValue()>schema.get("maximum").longValue()) throw new ValidationException("Out-of-range parameter: "+name);
        } else if(value instanceof String text) {
            if(schema.has("minLength") && text.length()<schema.get("minLength").asInt() || schema.has("maxLength") && text.length()>schema.get("maxLength").asInt() || schema.has("pattern") && !java.util.regex.Pattern.compile(schema.get("pattern").asText()).matcher(text).find()) throw new ValidationException("Invalid parameter value: "+name);
        }
    }
    JsonNode request(String operation, Map<String,Object> input, RequestOptions options) {
        JsonNode op=CONTRACTS.get(operation);
        if(op==null) throw new ValidationException("Unknown SDK operation");
        if(options==null) throw new ValidationException("Request options must not be null");
        String family=op.path("family").asText(), path=op.path("path").asText();
        boolean stateful=op.path("stateful").asBoolean();
        Duration callTimeout=options.timeout()==null ? timeout : options.timeout(); validTimeout(callTimeout);
        Set<String> allowed=new HashSet<>(); for(JsonNode p:op.get("params")) allowed.add(p.path("name").asText());
        if(!allowed.containsAll(input.keySet())) throw new ValidationException("Unexpected request parameter");
        Map<String,String> query=new LinkedHashMap<>(), headers=new LinkedHashMap<>(); headers.put("Accept","application/json");
        if(op.has("action")) query.put("method",op.get("action").asText());
        for(JsonNode param:op.get("params")) {
            String name=param.path("name").asText(); Object value=input.get(name);
            if(value==null || param.path("omitEmpty").asBoolean() && value.equals("")) {
                if(param.path("required").asBoolean()) throw new ValidationException("Missing required parameter: "+name);
                continue;
            }
            validate(param,value); String text=value.toString();
            if(param.path("in").asText().equals("path")) {
                if(text.equals(".") || text.equals("..")) throw new ValidationException("Invalid path parameter: "+name);
                path=path.replace("{"+name+"}",encode(text));
            } else if(param.path("in").asText().equals("header")) {
                if(text.indexOf('\r')>=0 || text.indexOf('\n')>=0) throw new ValidationException("Invalid header parameter: "+name);
                headers.put(name,text);
            } else query.put(name,text);
        }
        String auth=op.path("auth").asText();
        if(auth.equals("required") && apiKey==null) throw new ValidationException("Set apiKey for this operation");
        if(apiKey!=null && !auth.equals("none") && (auth.equals("required") || options.useApiKey())) {
            if(family.equals("activation")) headers.put("apikey",apiKey); else query.put("apikey",apiKey);
        }
        StringBuilder url=new StringBuilder(baseUrls.getOrDefault(family,op.path("baseUrl").asText())).append(path);
        boolean first=true;
        for(var entry:query.entrySet()) { url.append(first?'?':'&').append(encode(entry.getKey())).append('=').append(encode(entry.getValue())); first=false; }
        HttpTransport.Response received;
        try { received=http.execute(new HttpTransport.Request(op.path("method").asText(), URI.create(url.toString()), Map.copyOf(headers), callTimeout)); }
        catch(InterruptedException e) { Thread.currentThread().interrupt(); throw new TransportException(family,operation,"interrupted",stateful); }
        catch(SocketTimeoutException e) { throw new TransportException(family,operation,"timeout",stateful); }
        catch(InterruptedIOException e) { throw new TransportException(family,operation,Thread.currentThread().isInterrupted()?"interrupted":"timeout",stateful); }
        catch(IOException e) { throw new TransportException(family,operation,"network",stateful); }
        catch(RuntimeException e) { throw new TransportException(family,operation,"network",stateful); }
        JsonNode result;
        try { result=JSON.readTree(received.body()); } catch(IOException e) { throw new ProtocolException(family,operation,received.status()); }
        if(result==null || !result.isObject()) throw new ProtocolException(family,operation,received.status());
        JsonNode status=result.path(family.equals("activation")?"statusCode":"status");
        JsonNode code=result.path("error").path("type");
        if(!code.isTextual()) code=result.path("errorid");
        if(!code.isIntegralNumber() && !code.isTextual()) code=status.isIntegralNumber()?status:IntNode.valueOf(received.status());
        if(received.status()<200 || received.status()>=300) throw new ApiException(family,operation,received.status(),code,result);
        if(!status.isIntegralNumber()) throw new ProtocolException(family,operation,received.status());
        if(op.path("pending").asBoolean() && received.status()==202 && exactCode(status,202) && isPending(result)) return result;
        if(op.path("noStock").asBoolean() && received.status()==200 && exactCode(status,2)) return result;
        if(family.equals("activation") ? !exactCode(status,200) || result.hasNonNull("error") : !exactCode(status,1)) throw new ApiException(family,operation,received.status(),code,result);
        return result;
    }
    private static boolean exactCode(JsonNode code, int expected) { return code.isIntegralNumber() && code.bigIntegerValue().equals(java.math.BigInteger.valueOf(expected)); }
    public static boolean isPending(JsonNode response) { return response!=null && exactCode(response.path("statusCode"),202) && response.path("error").path("type").asText().equals("SMS_NOT_YET_RECEIVED"); }
    public static boolean isNoStock(JsonNode response) { return response!=null && exactCode(response.path("status"),2); }
    @Override public void close() {
        try { http.close(); } catch(IOException e) { throw new TransportException("client","close","network",false); }
    }
}
