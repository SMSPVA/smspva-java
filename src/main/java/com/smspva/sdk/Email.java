package com.smspva.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

/** Generated named API methods. */
public final class Email {
    private final SmsPva transport;
    Email(SmsPva transport) { this.transport=transport; }
    public JsonNode getProviders() { return getProviders(RequestOptions.DEFAULT); }
    public JsonNode getProviders(RequestOptions options) { return transport.request("email.getProviders", Map.of(), options); }
    public JsonNode getData(Requests.EmailGetData params) { return getData(params, RequestOptions.DEFAULT); }
    public JsonNode getData(Requests.EmailGetData params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("email.getData", params.toMap(), options);
    }
    public JsonNode getData() { return transport.request("email.getData", Map.of(), RequestOptions.DEFAULT); }
    public JsonNode getCount(Requests.EmailGetCount params) { return getCount(params, RequestOptions.DEFAULT); }
    public JsonNode getCount(Requests.EmailGetCount params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("email.getCount", params.toMap(), options);
    }
    public JsonNode getCount() { return transport.request("email.getCount", Map.of(), RequestOptions.DEFAULT); }
    public JsonNode createOrder(Requests.EmailCreateOrder params) { return createOrder(params, RequestOptions.DEFAULT); }
    public JsonNode createOrder(Requests.EmailCreateOrder params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("email.createOrder", params.toMap(), options);
    }
    public JsonNode getOrders() { return getOrders(RequestOptions.DEFAULT); }
    public JsonNode getOrders(RequestOptions options) { return transport.request("email.getOrders", Map.of(), options); }
    public JsonNode getMails(Requests.EmailGetMails params) { return getMails(params, RequestOptions.DEFAULT); }
    public JsonNode getMails(Requests.EmailGetMails params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("email.getMails", params.toMap(), options);
    }
    public JsonNode getMailBody(Requests.EmailGetMailBody params) { return getMailBody(params, RequestOptions.DEFAULT); }
    public JsonNode getMailBody(Requests.EmailGetMailBody params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("email.getMailBody", params.toMap(), options);
    }
    public JsonNode buyAlias(Requests.EmailBuyAlias params) { return buyAlias(params, RequestOptions.DEFAULT); }
    public JsonNode buyAlias(Requests.EmailBuyAlias params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("email.buyAlias", params.toMap(), options);
    }
    public JsonNode getMailsByAlias(Requests.EmailGetMailsByAlias params) { return getMailsByAlias(params, RequestOptions.DEFAULT); }
    public JsonNode getMailsByAlias(Requests.EmailGetMailsByAlias params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("email.getMailsByAlias", params.toMap(), options);
    }
    public JsonNode prolongOrder(Requests.EmailProlongOrder params) { return prolongOrder(params, RequestOptions.DEFAULT); }
    public JsonNode prolongOrder(Requests.EmailProlongOrder params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("email.prolongOrder", params.toMap(), options);
    }
    public JsonNode cancelOrder(Requests.EmailCancelOrder params) { return cancelOrder(params, RequestOptions.DEFAULT); }
    public JsonNode cancelOrder(Requests.EmailCancelOrder params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("email.cancelOrder", params.toMap(), options);
    }
    public JsonNode getArchivedOrders() { return getArchivedOrders(RequestOptions.DEFAULT); }
    public JsonNode getArchivedOrders(RequestOptions options) { return transport.request("email.getArchivedOrders", Map.of(), options); }
    public JsonNode calcRestoreOrder(Requests.EmailCalcRestoreOrder params) { return calcRestoreOrder(params, RequestOptions.DEFAULT); }
    public JsonNode calcRestoreOrder(Requests.EmailCalcRestoreOrder params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("email.calcRestoreOrder", params.toMap(), options);
    }
    public JsonNode restoreOrder(Requests.EmailRestoreOrder params) { return restoreOrder(params, RequestOptions.DEFAULT); }
    public JsonNode restoreOrder(Requests.EmailRestoreOrder params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("email.restoreOrder", params.toMap(), options);
    }
}
