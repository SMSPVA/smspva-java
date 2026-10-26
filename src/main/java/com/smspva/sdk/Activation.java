package com.smspva.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

/** Generated named API methods. */
public final class Activation {
    private final SmsPva transport;
    Activation(SmsPva transport) { this.transport=transport; }
    public JsonNode getBalance() { return getBalance(RequestOptions.DEFAULT); }
    public JsonNode getBalance(RequestOptions options) { return transport.request("activation.getBalance", Map.of(), options); }
    public JsonNode getUserInfo() { return getUserInfo(RequestOptions.DEFAULT); }
    public JsonNode getUserInfo(RequestOptions options) { return transport.request("activation.getUserInfo", Map.of(), options); }
    public JsonNode getOperators(Requests.ActivationGetOperators params) { return getOperators(params, RequestOptions.DEFAULT); }
    public JsonNode getOperators(Requests.ActivationGetOperators params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("activation.getOperators", params.toMap(), options);
    }
    public JsonNode getConversions(Requests.ActivationGetConversions params) { return getConversions(params, RequestOptions.DEFAULT); }
    public JsonNode getConversions(Requests.ActivationGetConversions params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("activation.getConversions", params.toMap(), options);
    }
    public JsonNode getMessageTypeSettings() { return getMessageTypeSettings(RequestOptions.DEFAULT); }
    public JsonNode getMessageTypeSettings(RequestOptions options) { return transport.request("activation.getMessageTypeSettings", Map.of(), options); }
    public JsonNode getAllNumberCounts() { return getAllNumberCounts(RequestOptions.DEFAULT); }
    public JsonNode getAllNumberCounts(RequestOptions options) { return transport.request("activation.getAllNumberCounts", Map.of(), options); }
    public JsonNode getCountryNumberCounts(Requests.ActivationGetCountryNumberCounts params) { return getCountryNumberCounts(params, RequestOptions.DEFAULT); }
    public JsonNode getCountryNumberCounts(Requests.ActivationGetCountryNumberCounts params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("activation.getCountryNumberCounts", params.toMap(), options);
    }
    public JsonNode getServicePrice(Requests.ActivationGetServicePrice params) { return getServicePrice(params, RequestOptions.DEFAULT); }
    public JsonNode getServicePrice(Requests.ActivationGetServicePrice params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("activation.getServicePrice", params.toMap(), options);
    }
    public JsonNode getCountryOperatorPrices(Requests.ActivationGetCountryOperatorPrices params) { return getCountryOperatorPrices(params, RequestOptions.DEFAULT); }
    public JsonNode getCountryOperatorPrices(Requests.ActivationGetCountryOperatorPrices params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("activation.getCountryOperatorPrices", params.toMap(), options);
    }
    public JsonNode getServicePrices(Requests.ActivationGetServicePrices params) { return getServicePrices(params, RequestOptions.DEFAULT); }
    public JsonNode getServicePrices(Requests.ActivationGetServicePrices params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("activation.getServicePrices", params.toMap(), options);
    }
    public JsonNode getAllServicePrices(Requests.ActivationGetAllServicePrices params) { return getAllServicePrices(params, RequestOptions.DEFAULT); }
    public JsonNode getAllServicePrices(Requests.ActivationGetAllServicePrices params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("activation.getAllServicePrices", params.toMap(), options);
    }
    public JsonNode getAllServicePrices() { return transport.request("activation.getAllServicePrices", Map.of(), RequestOptions.DEFAULT); }
    public JsonNode getNumber(Requests.ActivationGetNumber params) { return getNumber(params, RequestOptions.DEFAULT); }
    public JsonNode getNumber(Requests.ActivationGetNumber params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("activation.getNumber", params.toMap(), options);
    }
    public JsonNode getNumberByOperator(Requests.ActivationGetNumberByOperator params) { return getNumberByOperator(params, RequestOptions.DEFAULT); }
    public JsonNode getNumberByOperator(Requests.ActivationGetNumberByOperator params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("activation.getNumberByOperator", params.toMap(), options);
    }
    public JsonNode receiveSms(Requests.ActivationReceiveSms params) { return receiveSms(params, RequestOptions.DEFAULT); }
    public JsonNode receiveSms(Requests.ActivationReceiveSms params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("activation.receiveSms", params.toMap(), options);
    }
    public JsonNode getOrders() { return getOrders(RequestOptions.DEFAULT); }
    public JsonNode getOrders(RequestOptions options) { return transport.request("activation.getOrders", Map.of(), options); }
    public JsonNode getNumberStatus(Requests.ActivationGetNumberStatus params) { return getNumberStatus(params, RequestOptions.DEFAULT); }
    public JsonNode getNumberStatus(Requests.ActivationGetNumberStatus params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("activation.getNumberStatus", params.toMap(), options);
    }
    public JsonNode clearSms(Requests.ActivationClearSms params) { return clearSms(params, RequestOptions.DEFAULT); }
    public JsonNode clearSms(Requests.ActivationClearSms params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("activation.clearSms", params.toMap(), options);
    }
    public JsonNode stopSms(Requests.ActivationStopSms params) { return stopSms(params, RequestOptions.DEFAULT); }
    public JsonNode stopSms(Requests.ActivationStopSms params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("activation.stopSms", params.toMap(), options);
    }
    public JsonNode cancelOrder(Requests.ActivationCancelOrder params) { return cancelOrder(params, RequestOptions.DEFAULT); }
    public JsonNode cancelOrder(Requests.ActivationCancelOrder params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("activation.cancelOrder", params.toMap(), options);
    }
    public JsonNode blockNumber(Requests.ActivationBlockNumber params) { return blockNumber(params, RequestOptions.DEFAULT); }
    public JsonNode blockNumber(Requests.ActivationBlockNumber params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("activation.blockNumber", params.toMap(), options);
    }
}
