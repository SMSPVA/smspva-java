package com.smspva.sdk;
import com.fasterxml.jackson.databind.JsonNode;
public final class ApiException extends RuntimeException {
    private final String family, operation;
    private final int httpStatus;
    private final JsonNode code, response;
    public ApiException(String family, String operation, int httpStatus, JsonNode code, JsonNode response) {
        super("SMSPVA " + family + " request failed (HTTP " + httpStatus + ")");
        this.family=family; this.operation=operation; this.httpStatus=httpStatus; this.code=code; this.response=response;
    }
    public String family() { return family; }
    public String operation() { return operation; }
    public int httpStatus() { return httpStatus; }
    public JsonNode code() { return code; }
    public JsonNode response() { return response; }
}
