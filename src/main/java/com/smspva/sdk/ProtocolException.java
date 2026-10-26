package com.smspva.sdk;
public final class ProtocolException extends RuntimeException {
    private final String family, operation;
    private final int httpStatus;
    public ProtocolException(String family, String operation, int httpStatus) {
        super("SMSPVA " + family + " returned an invalid response (HTTP " + httpStatus + ")");
        this.family=family; this.operation=operation; this.httpStatus=httpStatus;
    }
    public String family() { return family; }
    public String operation() { return operation; }
    public int httpStatus() { return httpStatus; }
}
