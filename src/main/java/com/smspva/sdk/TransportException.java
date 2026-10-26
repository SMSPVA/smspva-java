package com.smspva.sdk;
public final class TransportException extends RuntimeException {
    private final String family, operation, kind;
    private final boolean outcomeUnknown;
    public TransportException(String family, String operation, String kind, boolean outcomeUnknown) {
        super("SMSPVA " + family + " request " + kind + "; no automatic retry");
        this.family=family; this.operation=operation; this.kind=kind; this.outcomeUnknown=outcomeUnknown;
    }
    public String family() { return family; }
    public String operation() { return operation; }
    public String kind() { return kind; }
    public boolean outcomeUnknown() { return outcomeUnknown; }
}
