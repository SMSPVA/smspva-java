# SMSPVA Java SDK

Official Java client for [SMSPVA](https://smspva.com/), a service for receiving verification SMS, renting phone numbers and renting private email addresses. Covers all 56 methods in Activation V2 (20), Rental (22) and Private Email (14). Requires Java 17 or later and uses Jackson and Apache HttpClient 5. This is a synchronous alpha release. Alternative API is excluded.

## Installation

Add the SDK dependency to your Maven project:

```xml
<dependency>
  <groupId>com.smspva</groupId>
  <artifactId>smspva-sdk</artifactId>
  <version>0.1.0-alpha.1</version>
</dependency>
```

[Sign in to your SMSPVA account](https://smspva.com/signin.html), open **Profile and ApiKey**, and set **`SMSPVA_API_KEY`** in your server environment. Keep it out of source control. The example reads your balance and reference lists; it does not create an order.

```java
import com.smspva.sdk.SmsPva;

try (SmsPva client = new SmsPva(System.getenv("SMSPVA_API_KEY"))) {
    System.out.println(client.activation().getBalance());
    System.out.println(client.rental().getCountries());
    System.out.println(client.email().getProviders());
}
```

Public reference methods also work with `new SmsPva()` and never send an account key. Country, service, provider, price and availability methods fetch live API data; the library has no fixed lists.

## Typed requests and unchanged results

`Requests` contains a record for each method with parameters. Required fields come first; optional fields use nullable boxed values and are omitted when null. Integer API inputs use `Long`, so pass `123L` rather than `123`. Strings and integer fields keep their documented API types. Source enum and range constraints are checked before the request.

```java
import com.smspva.sdk.Requests;

// Choose pcode from getProviders(), rather than hard-coding a provider list.
var services = client.email().getData(new Requests.EmailGetData("PROVIDER_CODE"));
```

Returned Jackson `JsonNode` objects retain API field names, envelopes and JSON types. String prices and IDs remain strings; numeric values use exact integer/decimal nodes. Optional schema fields are not invented. For Rental multi-service count, provide `dtype` and `dcount` explicitly: the original documentation warns against relying on server defaults.

All named methods have a `RequestOptions` overload for a per-call timeout and optional authentication policy. `RequestOptions.withoutOptionalKey()` omits the account key for optional-key Rental calls. Required-key methods still require and send it; public methods never send it. Activation uses the `apikey` header, Rental and Email the `apikey` query parameter. Empty or null Email `pcode` is omitted entirely.

## Pending results and errors

Receiving a new activation SMS can trigger payment. For an existing order:

```java
var response = client.activation().receiveSms(
    new Requests.ActivationReceiveSms(123L, null));
if (SmsPva.isPending(response)) {
    System.out.println("The SMS has not arrived yet.");
} else {
    System.out.println(response.get("data"));
}
```

Only the documented HTTP 202 / `SMS_NOT_YET_RECEIVED` combination is accepted as pending. Email creation `status=2` is likewise a normal no-stock result; `SmsPva.isNoStock(response)` recognises it.

- `ApiException`: `family()`, `operation()`, `httpStatus()`, original JSON `code()` and `response()`.
- `ProtocolException`: invalid JSON or a missing/invalid response status.
- `TransportException`: `kind()` (`network`, `timeout`, `interrupted`) and `outcomeUnknown()` for paid/state-changing operations.
- `ValidationException`: invalid input or missing credentials; no request is sent.

Exception messages omit request URLs and keys, and transport exceptions do not retain a potentially credential-bearing cause. Raw API error bodies remain available. Redact query URLs if you enable your own HTTP/debug logging.

## Timeouts, lifecycle and retries

The default timeout is 30 seconds per network operation, including waiting for a pooled connection and response socket activity. It is not a guaranteed whole-response deadline against a server that continually sends partial data. Set a client or per-call `Duration` between 1 and 2147483647 milliseconds. Use try-with-resources to close the owned transport and its connections.

Apache HttpClient 5 runs with automatic retries, redirects, authentication caching and cookie handling disabled. Some paid operations use GET; the SDK makes one attempt and never repeats them automatically. If a response is lost, inspect current orders before creating another. Custom `HttpTransport` implementations must preserve these policies.

This alpha exposes synchronous Java methods. It has no `CompletableFuture` facade or polling helper. For repeated SMS checks, save the order expiry once, stop at that deadline, wait at least five seconds between pending responses, and stop on terminal/transport errors. Do not reset the deadline or purchase another number as part of polling. A thread interruption observed by the transport is reported with the thread's interrupt flag restored; cancellation cannot undo an operation already accepted by SMSPVA.

For testing or advanced proxy transport, the four-argument constructor accepts a `HttpTransport` and optional base URLs keyed by `activation`, `rental` or `email`. These are application configuration, not request parameters. The client closes the supplied transport when it closes.

## Regeneration and checks

From the repository root:

```sh
node scripts/generate.mjs
mvn verify
mvn install
```

Generation reads the bundled contract and wire fixtures, then produces typed records and methods. A standalone source checkout already includes the generated files and builds without the documentation tree.

Tests cover all 56 named methods and exact wire paths, HTTP verbs, parameters, auth and returned values. Local HTTP tests verify that redirects, cookies and automatic recovery of a disconnected paid GET are disabled. No production API request or purchase is made. The alpha has not been validated against paid production operations.

The transport policy follows [Apache HttpClient's retry controls](https://hc.apache.org/httpcomponents-client-5.6.x/5.6.5/httpclient5/apidocs/org/apache/hc/client5/http/impl/classic/HttpClientBuilder.html); JSON handling uses [Jackson](https://github.com/FasterXML/jackson).

## License

MIT. See [LICENSE](https://github.com/SMSPVA/smspva-java/blob/main/LICENSE).

## Documentation and support

Read the [SMSPVA API documentation](https://docs.smspva.com/) for parameters, response codes and workflows. Country, service and provider lists come from the live API. SMSPVA supports [phone number rentals](https://smspva.com/rent) and [private email rentals](https://smspva.com/rent-mail.html); availability and prices can change.

For account or billing questions, visit the [SMSPVA help center](https://smspva.com/help.html). Report SDK bugs through [GitHub issues](https://github.com/SMSPVA/smspva-java/issues), without API keys or private message content.
