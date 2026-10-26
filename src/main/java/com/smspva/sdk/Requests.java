package com.smspva.sdk;

import java.util.Map;
import java.util.LinkedHashMap;

/** Generated typed request values. Null optional values are omitted; API names are preserved. */
public final class Requests {
    private Requests() {}
    public interface Params { Map<String,Object> toMap(); }
    public record ActivationGetOperators(String country) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("country", country);
            return values;
        }
    }
    public record ActivationGetConversions(String service) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("service", service);
            return values;
        }
    }
    public record ActivationGetCountryNumberCounts(String country) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("country", country);
            return values;
        }
    }
    public record ActivationGetServicePrice(String country, String service, Long voice) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("country", country);
            values.put("service", service);
            values.put("voice", voice);
            return values;
        }
    }
    public record ActivationGetCountryOperatorPrices(String country, Long voice) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("country", country);
            values.put("voice", voice);
            return values;
        }
    }
    public record ActivationGetServicePrices(String service, Long voice) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("service", service);
            values.put("voice", voice);
            return values;
        }
    }
    public record ActivationGetAllServicePrices(Long voice) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("voice", voice);
            return values;
        }
    }
    public record ActivationGetNumber(String country, String service, Long voice) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("country", country);
            values.put("service", service);
            values.put("voice", voice);
            return values;
        }
    }
    public record ActivationGetNumberByOperator(String country, String service, String operator, Long voice) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("country", country);
            values.put("service", service);
            values.put("operator", operator);
            values.put("voice", voice);
            return values;
        }
    }
    public record ActivationReceiveSms(Long orderid, String partnerkey) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("partnerkey", partnerkey);
            values.put("orderid", orderid);
            return values;
        }
    }
    public record ActivationGetNumberStatus(String number, String service) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("number", number);
            values.put("service", service);
            return values;
        }
    }
    public record ActivationClearSms(Long orderid) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("orderid", orderid);
            return values;
        }
    }
    public record ActivationStopSms(Long orderid) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("orderid", orderid);
            return values;
        }
    }
    public record ActivationCancelOrder(Long orderid) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("orderid", orderid);
            return values;
        }
    }
    public record ActivationBlockNumber(Long orderid) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("orderid", orderid);
            return values;
        }
    }
    public record RentalGetCountriesByService(String service, String dtype, Long dcount) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("service", service);
            values.put("dtype", dtype);
            values.put("dcount", dcount);
            return values;
        }
    }
    public record RentalGetData(String country, String dtype, Long dcount, String extend) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("country", country);
            values.put("dtype", dtype);
            values.put("dcount", dcount);
            values.put("extend", extend);
            return values;
        }
    }
    public record RentalGetDataWithProviders(String country, String dtype, Long dcount, String extend) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("country", country);
            values.put("dtype", dtype);
            values.put("dcount", dcount);
            values.put("extend", extend);
            return values;
        }
    }
    public record RentalGetCount(String country, String dtype, Long dcount) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("country", country);
            values.put("dtype", dtype);
            values.put("dcount", dcount);
            return values;
        }
    }
    public record RentalGetCountWithProviders(String country, String dtype, Long dcount) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("country", country);
            values.put("dtype", dtype);
            values.put("dcount", dcount);
            return values;
        }
    }
    public record RentalGetMultiServiceCount(String country, String services, String dtype, Long dcount) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("country", country);
            values.put("services", services);
            values.put("dtype", dtype);
            values.put("dcount", dcount);
            return values;
        }
    }
    public record RentalGetMinimumPaypalDays(String country, String service) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("country", country);
            values.put("service", service);
            return values;
        }
    }
    public record RentalCreate(String dtype, Long dcount, String country, String service, String provider, String partner_key) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("dtype", dtype);
            values.put("dcount", dcount);
            values.put("country", country);
            values.put("service", service);
            values.put("provider", provider);
            values.put("partner_key", partner_key);
            return values;
        }
    }
    public record RentalCreateMulti(String dtype, Long dcount, String country, String services, String provider, String partner_key) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("dtype", dtype);
            values.put("dcount", dcount);
            values.put("country", country);
            values.put("services", services);
            values.put("provider", provider);
            values.put("partner_key", partner_key);
            return values;
        }
    }
    public record RentalAddServiceToOrder(Long id, String service, String pnumber, Long rent_days) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("id", id);
            values.put("service", service);
            values.put("pnumber", pnumber);
            values.put("rent_days", rent_days);
            return values;
        }
    }
    public record RentalActivate(Long id) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("id", id);
            return values;
        }
    }
    public record RentalGetSms(Long id) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("id", id);
            return values;
        }
    }
    public record RentalProlong(Long id, String dtype, Long dcount) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("id", id);
            values.put("dtype", dtype);
            values.put("dcount", dcount);
            return values;
        }
    }
    public record RentalProlongByDays(Long id, String dtype, Long dcount) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("id", id);
            values.put("dtype", dtype);
            values.put("dcount", dcount);
            return values;
        }
    }
    public record RentalDeleteOrder(Long id) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("id", id);
            return values;
        }
    }
    public record RentalGetHistory(Long skip, Long take) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("skip", skip);
            values.put("take", take);
            return values;
        }
    }
    public record RentalGetRestoreCost(Long id) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("id", id);
            return values;
        }
    }
    public record RentalRestore(Long id) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("id", id);
            return values;
        }
    }
    public record EmailGetData(String pcode) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("pcode", pcode);
            return values;
        }
    }
    public record EmailGetCount(String pcode) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("pcode", pcode);
            return values;
        }
    }
    public record EmailCreateOrder(String scode, String dtype, Long dcount, String pcode) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("scode", scode);
            values.put("pcode", pcode);
            values.put("dtype", dtype);
            values.put("dcount", dcount);
            return values;
        }
    }
    public record EmailGetMails(Long orderid, Long t, Long s, String sq) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("orderid", orderid);
            values.put("t", t);
            values.put("s", s);
            values.put("sq", sq);
            return values;
        }
    }
    public record EmailGetMailBody(Long orderid, Long emailid) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("orderid", orderid);
            values.put("emailid", emailid);
            return values;
        }
    }
    public record EmailBuyAlias(Long orderid, Long aliasid) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("orderid", orderid);
            values.put("aliasid", aliasid);
            return values;
        }
    }
    public record EmailGetMailsByAlias(Long orderid, Long aliasid, Long t, Long s, String sq) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("orderid", orderid);
            values.put("aliasid", aliasid);
            values.put("t", t);
            values.put("s", s);
            values.put("sq", sq);
            return values;
        }
    }
    public record EmailProlongOrder(Long orderid, String dtype, Long dcount) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("orderid", orderid);
            values.put("dtype", dtype);
            values.put("dcount", dcount);
            return values;
        }
    }
    public record EmailCancelOrder(Long orderid) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("orderid", orderid);
            return values;
        }
    }
    public record EmailCalcRestoreOrder(Long orderid) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("orderid", orderid);
            return values;
        }
    }
    public record EmailRestoreOrder(Long orderid) implements Params {
        public Map<String,Object> toMap() {
            Map<String,Object> values=new LinkedHashMap<>();
            values.put("orderid", orderid);
            return values;
        }
    }
}
