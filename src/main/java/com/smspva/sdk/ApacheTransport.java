package com.smspva.sdk;

import java.io.IOException;
import java.net.SocketTimeoutException;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.Timeout;
import java.nio.charset.StandardCharsets;

/** Default single-exchange transport: automatic retries, redirects and cookie handling are disabled. */
public final class ApacheTransport implements HttpTransport {
    private final CloseableHttpClient client = HttpClients.custom().disableAutomaticRetries()
        .disableRedirectHandling().disableCookieManagement().disableAuthCaching().build();

    @Override public Response execute(Request input) throws IOException, InterruptedException {
        if (Thread.currentThread().isInterrupted()) throw new InterruptedException();
        HttpUriRequestBase request = new HttpUriRequestBase(input.method(), input.uri());
        input.headers().forEach(request::setHeader);
        Timeout timeout = Timeout.ofMilliseconds(Math.max(1, input.timeout().toMillis()));
        request.setConfig(RequestConfig.custom().setConnectionRequestTimeout(timeout)
            .setConnectTimeout(timeout).setResponseTimeout(timeout).build());
        Response response = client.execute(request, received -> new Response(received.getCode(),
            received.getEntity() == null ? "" : EntityUtils.toString(received.getEntity(), StandardCharsets.UTF_8)));
        if (Thread.currentThread().isInterrupted()) throw new InterruptedException();
        return response;
    }
    @Override public void close() throws IOException { client.close(); }
}
