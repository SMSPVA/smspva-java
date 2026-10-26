package com.smspva.sdk;
import java.time.Duration;
/** Null timeout uses the client's timeout. useApiKey only affects optional-auth operations. */
public record RequestOptions(Duration timeout, boolean useApiKey) {
    public static final RequestOptions DEFAULT = new RequestOptions(null, true);
    public static RequestOptions withoutOptionalKey() { return new RequestOptions(null, false); }
}
