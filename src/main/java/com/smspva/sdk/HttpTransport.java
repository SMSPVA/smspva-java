package com.smspva.sdk;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Map;

/** A single HTTP exchange. Implementations must not retry, redirect or attach account cookies. */
public interface HttpTransport extends AutoCloseable {
    record Request(String method, URI uri, Map<String,String> headers, Duration timeout) {
        @Override public String toString() { return "SMSPVA HTTP request [" + method + ", URL redacted]"; }
    }
    record Response(int status, String body) {}
    Response execute(Request request) throws IOException, InterruptedException;
    @Override default void close() throws IOException {}
}
