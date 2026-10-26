package com.smspva.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

/** Generated named API methods. */
public final class Rental {
    private final SmsPva transport;
    Rental(SmsPva transport) { this.transport=transport; }
    public JsonNode getCountries() { return getCountries(RequestOptions.DEFAULT); }
    public JsonNode getCountries(RequestOptions options) { return transport.request("rental.getCountries", Map.of(), options); }
    public JsonNode getDefaultServices() { return getDefaultServices(RequestOptions.DEFAULT); }
    public JsonNode getDefaultServices(RequestOptions options) { return transport.request("rental.getDefaultServices", Map.of(), options); }
    public JsonNode getCountriesByService(Requests.RentalGetCountriesByService params) { return getCountriesByService(params, RequestOptions.DEFAULT); }
    public JsonNode getCountriesByService(Requests.RentalGetCountriesByService params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.getCountriesByService", params.toMap(), options);
    }
    public JsonNode getData(Requests.RentalGetData params) { return getData(params, RequestOptions.DEFAULT); }
    public JsonNode getData(Requests.RentalGetData params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.getData", params.toMap(), options);
    }
    public JsonNode getDataWithProviders(Requests.RentalGetDataWithProviders params) { return getDataWithProviders(params, RequestOptions.DEFAULT); }
    public JsonNode getDataWithProviders(Requests.RentalGetDataWithProviders params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.getDataWithProviders", params.toMap(), options);
    }
    public JsonNode getCount(Requests.RentalGetCount params) { return getCount(params, RequestOptions.DEFAULT); }
    public JsonNode getCount(Requests.RentalGetCount params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.getCount", params.toMap(), options);
    }
    public JsonNode getCountWithProviders(Requests.RentalGetCountWithProviders params) { return getCountWithProviders(params, RequestOptions.DEFAULT); }
    public JsonNode getCountWithProviders(Requests.RentalGetCountWithProviders params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.getCountWithProviders", params.toMap(), options);
    }
    public JsonNode getMultiServiceCount(Requests.RentalGetMultiServiceCount params) { return getMultiServiceCount(params, RequestOptions.DEFAULT); }
    public JsonNode getMultiServiceCount(Requests.RentalGetMultiServiceCount params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.getMultiServiceCount", params.toMap(), options);
    }
    public JsonNode getCanadaPaypalDays() { return getCanadaPaypalDays(RequestOptions.DEFAULT); }
    public JsonNode getCanadaPaypalDays(RequestOptions options) { return transport.request("rental.getCanadaPaypalDays", Map.of(), options); }
    public JsonNode getMinimumPaypalDays(Requests.RentalGetMinimumPaypalDays params) { return getMinimumPaypalDays(params, RequestOptions.DEFAULT); }
    public JsonNode getMinimumPaypalDays(Requests.RentalGetMinimumPaypalDays params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.getMinimumPaypalDays", params.toMap(), options);
    }
    public JsonNode create(Requests.RentalCreate params) { return create(params, RequestOptions.DEFAULT); }
    public JsonNode create(Requests.RentalCreate params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.create", params.toMap(), options);
    }
    public JsonNode createMulti(Requests.RentalCreateMulti params) { return createMulti(params, RequestOptions.DEFAULT); }
    public JsonNode createMulti(Requests.RentalCreateMulti params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.createMulti", params.toMap(), options);
    }
    public JsonNode addServiceToOrder(Requests.RentalAddServiceToOrder params) { return addServiceToOrder(params, RequestOptions.DEFAULT); }
    public JsonNode addServiceToOrder(Requests.RentalAddServiceToOrder params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.addServiceToOrder", params.toMap(), options);
    }
    public JsonNode getOrders() { return getOrders(RequestOptions.DEFAULT); }
    public JsonNode getOrders(RequestOptions options) { return transport.request("rental.getOrders", Map.of(), options); }
    public JsonNode activate(Requests.RentalActivate params) { return activate(params, RequestOptions.DEFAULT); }
    public JsonNode activate(Requests.RentalActivate params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.activate", params.toMap(), options);
    }
    public JsonNode getSms(Requests.RentalGetSms params) { return getSms(params, RequestOptions.DEFAULT); }
    public JsonNode getSms(Requests.RentalGetSms params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.getSms", params.toMap(), options);
    }
    public JsonNode prolong(Requests.RentalProlong params) { return prolong(params, RequestOptions.DEFAULT); }
    public JsonNode prolong(Requests.RentalProlong params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.prolong", params.toMap(), options);
    }
    public JsonNode prolongByDays(Requests.RentalProlongByDays params) { return prolongByDays(params, RequestOptions.DEFAULT); }
    public JsonNode prolongByDays(Requests.RentalProlongByDays params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.prolongByDays", params.toMap(), options);
    }
    public JsonNode deleteOrder(Requests.RentalDeleteOrder params) { return deleteOrder(params, RequestOptions.DEFAULT); }
    public JsonNode deleteOrder(Requests.RentalDeleteOrder params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.deleteOrder", params.toMap(), options);
    }
    public JsonNode getHistory(Requests.RentalGetHistory params) { return getHistory(params, RequestOptions.DEFAULT); }
    public JsonNode getHistory(Requests.RentalGetHistory params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.getHistory", params.toMap(), options);
    }
    public JsonNode getHistory() { return transport.request("rental.getHistory", Map.of(), RequestOptions.DEFAULT); }
    public JsonNode getRestoreCost(Requests.RentalGetRestoreCost params) { return getRestoreCost(params, RequestOptions.DEFAULT); }
    public JsonNode getRestoreCost(Requests.RentalGetRestoreCost params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.getRestoreCost", params.toMap(), options);
    }
    public JsonNode restore(Requests.RentalRestore params) { return restore(params, RequestOptions.DEFAULT); }
    public JsonNode restore(Requests.RentalRestore params, RequestOptions options) {
        if(params==null) throw new ValidationException("Request parameters must not be null");
        return transport.request("rental.restore", params.toMap(), options);
    }
}
