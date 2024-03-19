/*
 * LaunchDarkly REST API
 * # Overview  ## Authentication  LaunchDarkly's REST API uses the HTTPS protocol with a minimum TLS version of 1.2.  All REST API resources are authenticated with either [personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.  LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and JavaScript-based SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations such as fetching feature flag settings.  | Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          | | ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- | | [Personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export. | | SDK keys                                                                                        | Can only access read-only resources specific to server-side SDKs. Restricted to a single environment. | Server-side SDKs                     | | Mobile keys                                                                                     | Can only access read-only resources specific to mobile SDKs, and only for flags marked available to mobile keys. Restricted to a single environment.           | Mobile SDKs                                        | | Client-side ID                                                                                  | Can only access read-only resources specific to JavaScript-based client-side SDKs, and only for flags marked available to client-side. Restricted to a single environment.           | Client-side JavaScript                             |  > #### Keep your access tokens and SDK keys private > > Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [**Account settings**](https://app.launchdarkly.com/settings/tokens) page. > > The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.  ### Authentication using request header  The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.  Manage personal access tokens from the [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.  ### Authentication using session cookie  For testing purposes, you can make API calls directly from your web browser. If you are logged in to the LaunchDarkly application, the API will use your existing session to authenticate calls.  If you have a [role](https://docs.launchdarkly.com/home/team/built-in-roles) other than Admin, or have a [custom role](https://docs.launchdarkly.com/home/team/custom-roles) defined, you may not have permission to perform some API calls. You will receive a `401` response code in that case.  > ### Modifying the Origin header causes an error > > LaunchDarkly validates that the Origin header for any API request authenticated by a session cookie matches the expected Origin header. The expected Origin header is `https://app.launchdarkly.com`. > > If the Origin header does not match what's expected, LaunchDarkly returns an error. This error can prevent the LaunchDarkly app from working correctly. > > Any browser extension that intentionally changes the Origin header can cause this problem. For example, the `Allow-Control-Allow-Origin: *` Chrome extension changes the Origin header to `http://evil.com` and causes the app to fail. > > To prevent this error, do not modify your Origin header. > > LaunchDarkly does not require origin matching when authenticating with an access token, so this issue does not affect normal API usage.  ## Representations  All resources expect and return JSON response bodies. Error responses also send a JSON body. To learn more about the error format of the API, read [Errors](https://apidocs.launchdarkly.com).  In practice this means that you always get a response with a `Content-Type` header set to `application/json`.  In addition, request bodies for `PATCH`, `POST`, and `PUT` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.  ### Summary and detailed representations  When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource, such as a single feature flag, you receive a _detailed representation_ of the resource.  The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.  ### Expanding responses  Sometimes the detailed representation of a resource does not include all of the attributes of the resource by default. If this is the case, the request method will clearly document this and describe which attributes you can include in an expanded response.  To include the additional attributes, append the `expand` request parameter to your request and add a comma-separated list of the attributes to include. For example, when you append `?expand=members,roles` to the [Get team](https://apidocs.launchdarkly.com) endpoint, the expanded response includes both of these attributes.  ### Links and addressability  The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:  - Links to other resources within the API are encapsulated in a `_links` object - If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link  Each link has two attributes:  - An `href`, which contains the URL - A `type`, which describes the content type  For example, a feature resource might return the following:  ```json {   \"_links\": {     \"parent\": {       \"href\": \"/api/features\",       \"type\": \"application/json\"     },     \"self\": {       \"href\": \"/api/features/sort.order\",       \"type\": \"application/json\"     }   },   \"_site\": {     \"href\": \"/features/sort.order\",     \"type\": \"text/html\"   } } ```  From this, you can navigate to the parent collection of features by following the `parent` link, or navigate to the site page for the feature by following the `_site` link.  Collections are always represented as a JSON object with an `items` attribute containing an array of representations. Like all other representations, collections have `_links` defined at the top level.  Paginated collections include `first`, `last`, `next`, and `prev` links containing a URL with the respective set of elements in the collection.  ## Updates  Resources that accept partial updates use the `PATCH` verb. Most resources support the [JSON patch](https://apidocs.launchdarkly.com) format. Some resources also support the [JSON merge patch](https://apidocs.launchdarkly.com) format, and some resources support the [semantic patch](https://apidocs.launchdarkly.com) format, which is a way to specify the modifications to perform as a set of executable instructions. Each resource supports optional [comments](https://apidocs.launchdarkly.com) that you can submit with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.  When a resource supports both JSON patch and semantic patch, we document both in the request method. However, the specific request body fields and descriptions included in our documentation only match one type of patch or the other.  ### Updates using JSON patch  [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) is a way to specify the modifications to perform on a resource. JSON patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. JSON patch documents are always arrays, where each element contains an operation, a path to the field to update, and the new value.  For example, in this feature flag representation:  ```json {     \"name\": \"New recommendations engine\",     \"key\": \"engine.enable\",     \"description\": \"This is the description\",     ... } ``` You can change the feature flag's description with the following patch document:  ```json [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"This is the new description\" }] ```  You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:  ```json [   { \"op\": \"test\", \"path\": \"/version\", \"value\": 10 },   { \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" } ] ```  The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.  Attributes that are not editable, such as a resource's `_links`, have names that start with an underscore.  ### Updates using JSON merge patch  [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) is another format for specifying the modifications to perform on a resource. JSON merge patch is less expressive than JSON patch. However, in many cases it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:  ```json {   \"description\": \"New flag description\" } ```  ### Updates using semantic patch  Some resources support the semantic patch format. A semantic patch is a way to specify the modifications to perform on a resource as a set of executable instructions.  Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, you can define semantic patch instructions independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.  To make a semantic patch request, you must append `domain-model=launchdarkly.semanticpatch` to your `Content-Type` header.  Here's how:  ``` Content-Type: application/json; domain-model=launchdarkly.semanticpatch ```  If you call a semantic patch resource without this header, you will receive a `400` response because your semantic patch will be interpreted as a JSON patch.  The body of a semantic patch request takes the following properties:  * `comment` (string): (Optional) A description of the update. * `environmentKey` (string): (Required for some resources only) The environment key. * `instructions` (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a `kind` property that indicates the instruction. If the instruction requires parameters, you must include those parameters as additional fields in the object. The documentation for each resource that supports semantic patch includes the available instructions and any additional parameters.  For example:  ```json {   \"comment\": \"optional comment\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  If any instruction in the patch encounters an error, the endpoint returns an error and will not change the resource. In general, each instruction silently does nothing if the resource is already in the state you request.  ### Updates with comments  You can submit optional comments with `PATCH` changes.  To submit a comment along with a JSON patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"patch\": [{ \"op\": \"replace\", \"path\": \"/description\", \"value\": \"The new description\" }] } ```  To submit a comment along with a JSON merge patch document, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"merge\": { \"description\": \"New flag description\" } } ```  To submit a comment along with a semantic patch, use the following format:  ```json {   \"comment\": \"This is a comment string\",   \"instructions\": [ {\"kind\": \"turnFlagOn\"} ] } ```  ## Errors  The API always returns errors in a common format. Here's an example:  ```json {   \"code\": \"invalid_request\",   \"message\": \"A feature with that key already exists\",   \"id\": \"30ce6058-87da-11e4-b116-123b93f75cba\" } ```  The `code` indicates the general class of error. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly Support to debug a problem with a specific API call.  ### HTTP status error response codes  | Code | Definition        | Description                                                                                       | Possible Solution                                                | | ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- | | 400  | Invalid request       | The request cannot be understood.                                    | Ensure JSON syntax in request body is correct.                   | | 401  | Invalid access token      | Requestor is unauthorized or does not have permission for this API call.                                                | Ensure your API access token is valid and has the appropriate permissions.                                     | | 403  | Forbidden         | Requestor does not have access to this resource.                                                | Ensure that the account member or access token has proper permissions set. | | 404  | Invalid resource identifier | The requested resource is not valid. | Ensure that the resource is correctly identified by ID or key. | | 405  | Method not allowed | The request method is not allowed on this resource. | Ensure that the HTTP verb is correct. | | 409  | Conflict          | The API request can not be completed because it conflicts with a concurrent API request. | Retry your request.                                              | | 422  | Unprocessable entity | The API request can not be completed because the update description can not be understood. | Ensure that the request body is correct for the type of patch you are using, either JSON patch or semantic patch. | 429  | Too many requests | Read [Rate limiting](https://apidocs.launchdarkly.com).                                               | Wait and try again later.                                        |  ## CORS  The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise the request returns a wildcard, `Access-Control-Allow-Origin: *`. For more information on CORS, read the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:  ```http Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH Access-Control-Allow-Origin: * Access-Control-Max-Age: 300 ```  You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](https://apidocs.launchdarkly.com). If you are using session authentication, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted entities.  ## Rate limiting  We use several rate limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs return a `429` status code. Calls to our APIs include headers indicating the current rate limit status. The specific headers returned depend on the API route being called. The limits differ based on the route, authentication mechanism, and other factors. Routes that are not rate limited may not contain any of the headers described below.  > ### Rate limiting and SDKs > > LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs.  ### Global rate limits  Authenticated requests are subject to a global limit. This is the maximum number of calls that your account can make to the API per ten seconds. All service and personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits may return the headers below:  | Header name                    | Description                                                                      | | ------------------------------ | -------------------------------------------------------------------------------- | | `X-Ratelimit-Global-Remaining` | The maximum number of requests the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |  We do not publicly document the specific number of calls that can be made globally. This limit may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limit.  ### Route-level rate limits  Some authenticated routes have custom rate limits. These also reset every ten seconds. Any service or personal access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits return the headers below:  | Header name                   | Description                                                                                           | | ----------------------------- | ----------------------------------------------------------------------------------------------------- | | `X-Ratelimit-Route-Remaining` | The maximum number of requests to the current route the account is permitted to make per ten seconds. | | `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.                         |  A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](https://apidocs.launchdarkly.com) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.  We do not publicly document the specific number of calls that an account can make to each endpoint per ten seconds. These limits may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limits.  ### IP-based rate limiting  We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.  ## OpenAPI (Swagger) and client libraries  We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.  We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit the [collection of client libraries on GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories). You can also use this specification to generate client libraries to interact with our REST API in your language of choice.  Our OpenAPI specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to explore our APIs.  ## Method overriding  Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `DELETE`, `PATCH`, and `PUT` verbs are inaccessible.  To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to \"tunnel\" `DELETE`, `PATCH`, and `PUT` requests using a `POST` request.  For example, to call a `PATCH` endpoint using a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.  ## Beta resources  We sometimes release new API resources in **beta** status before we release them with general availability.  Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.  We mark beta resources with a \"Beta\" callout in our documentation, pictured below:  > ### This feature is in beta > > To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](https://apidocs.launchdarkly.com). > > Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.  ### Using beta resources  To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you receive a `403` response.  Use this header:  ``` LD-API-Version: beta ```  ## Federal environments  The version of LaunchDarkly that is available on domains controlled by the United States government is different from the version of LaunchDarkly available to the general public. If you are an employee or contractor for a United States federal agency and use LaunchDarkly in your work, you likely use the federal instance of LaunchDarkly.  If you are working in the federal instance of LaunchDarkly, the base URI for each request is `https://app.launchdarkly.us`. In the \"Try it\" sandbox for each request, click the request path to view the complete resource path for the federal environment.  To learn more, read [LaunchDarkly in federal environments](https://docs.launchdarkly.com/home/advanced/federal).  ## Versioning  We try hard to keep our REST API backwards compatible, but we occasionally have to make backwards-incompatible changes in the process of shipping new features. These breaking changes can cause unexpected behavior if you don't prepare for them accordingly.  Updates to our REST API include support for the latest features in LaunchDarkly. We also release a new version of our REST API every time we make a breaking change. We provide simultaneous support for multiple API versions so you can migrate from your current API version to a new version at your own pace.  ### Setting the API version per request  You can set the API version on a specific request by sending an `LD-API-Version` header, as shown in the example below:  ``` LD-API-Version: 20220603 ```  The header value is the version number of the API version you would like to request. The number for each version corresponds to the date the version was released in `yyyymmdd` format. In the example above the version `20220603` corresponds to June 03, 2022.  ### Setting the API version per access token  When you create an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.  Tokens created before versioning was released have their version set to `20160426`, which is the version of the API that existed before the current versioning scheme, so that they continue working the same way they did before versioning.  If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.  > ### Best practice: Set the header for every client or integration > > We recommend that you set the API version header explicitly in any client or integration you build. > > Only rely on the access token API version during manual testing.  ### API version changelog  |<div style=\"width:75px\">Version</div> | Changes | End of life (EOL) |---|---|---| | `20220603` | <ul><li>Changed the [list projects](https://apidocs.launchdarkly.com) return value:<ul><li>Response is now paginated with a default limit of `20`.</li><li>Added support for filter and sort.</li><li>The project `environments` field is now expandable. This field is omitted by default.</li></ul></li><li>Changed the [get project](https://apidocs.launchdarkly.com) return value:<ul><li>The `environments` field is now expandable. This field is omitted by default.</li></ul></li></ul> | Current | | `20210729` | <ul><li>Changed the [create approval request](https://apidocs.launchdarkly.com) return value. It now returns HTTP Status Code `201` instead of `200`.</li><li> Changed the [get users](https://apidocs.launchdarkly.com) return value. It now returns a user record, not a user. </li><li>Added additional optional fields to environment, segments, flags, members, and segments, including the ability to create big segments. </li><li> Added default values for flag variations when new environments are created. </li><li>Added filtering and pagination for getting flags and members, including `limit`, `number`, `filter`, and `sort` query parameters. </li><li>Added endpoints for expiring user targets for flags and segments, scheduled changes, access tokens, Relay Proxy configuration, integrations and subscriptions, and approvals. </li></ul> | 2023-06-03 | | `20191212` | <ul><li>[List feature flags](https://apidocs.launchdarkly.com) now defaults to sending summaries of feature flag configurations, equivalent to setting the query parameter `summary=true`. Summaries omit flag targeting rules and individual user targets from the payload. </li><li> Added endpoints for flags, flag status, projects, environments, audit logs, members, users, custom roles, segments, usage, streams, events, and data export. </li></ul> | 2022-07-29 | | `20160426` | <ul><li>Initial versioning of API. Tokens created before versioning have their version set to this.</li></ul> | 2020-12-12 | 
 *
 * The version of the OpenAPI document: 2.0
 * Contact: support@launchdarkly.com
 *
 * NOTE: This class is auto generated by Konfig (https://konfigthis.com).
 * Do not edit the class manually.
 */


package com.konfigthis.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.JsonElement;
import io.gsonfire.GsonFireBuilder;
import io.gsonfire.TypeSelector;

import okio.ByteString;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

/*
 * A JSON utility class
 *
 * NOTE: in the future, this class may be converted to static, which may break
 *       backward-compatibility
 */
public class JSON {
    private static Gson gson;
    private static boolean isLenientOnJson = false;
    private static DateTypeAdapter dateTypeAdapter = new DateTypeAdapter();
    private static SqlDateTypeAdapter sqlDateTypeAdapter = new SqlDateTypeAdapter();
    private static OffsetDateTimeTypeAdapter offsetDateTimeTypeAdapter = new OffsetDateTimeTypeAdapter();
    private static LocalDateTypeAdapter localDateTypeAdapter = new LocalDateTypeAdapter();
    private static ByteArrayAdapter byteArrayAdapter = new ByteArrayAdapter();

    @SuppressWarnings("unchecked")
    public static GsonBuilder createGson() {
        GsonFireBuilder fireBuilder = new GsonFireBuilder()
        ;
        GsonBuilder builder = fireBuilder.createGsonBuilder();
        return builder;
    }

    private static String getDiscriminatorValue(JsonElement readElement, String discriminatorField) {
        JsonElement element = readElement.getAsJsonObject().get(discriminatorField);
        if (null == element) {
            throw new IllegalArgumentException("missing discriminator field: <" + discriminatorField + ">");
        }
        return element.getAsString();
    }

    /**
     * Returns the Java class that implements the OpenAPI schema for the specified discriminator value.
     *
     * @param classByDiscriminatorValue The map of discriminator values to Java classes.
     * @param discriminatorValue The value of the OpenAPI discriminator in the input data.
     * @return The Java class that implements the OpenAPI schema
     */
    private static Class getClassByDiscriminator(Map classByDiscriminatorValue, String discriminatorValue) {
        Class clazz = (Class) classByDiscriminatorValue.get(discriminatorValue);
        if (null == clazz) {
            throw new IllegalArgumentException("cannot determine model class of name: <" + discriminatorValue + ">");
        }
        return clazz;
    }

    {
        GsonBuilder gsonBuilder = createGson();
        gsonBuilder.registerTypeAdapter(Date.class, dateTypeAdapter);
        gsonBuilder.registerTypeAdapter(java.sql.Date.class, sqlDateTypeAdapter);
        gsonBuilder.registerTypeAdapter(OffsetDateTime.class, offsetDateTimeTypeAdapter);
        gsonBuilder.registerTypeAdapter(LocalDate.class, localDateTypeAdapter);
        gsonBuilder.registerTypeAdapter(byte[].class, byteArrayAdapter);

        /**
         * For the "type: number" schema we accept both Double and Integer
         * In the case that we pass "1.0" or "1" we serialize the JsonPrimitive
         * as the "1" literal. This is useful when the server expects an integer.
         */
        gsonBuilder.registerTypeAdapter(Double.class, new JsonSerializer<Double>() {

            @Override
            public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                if (src == src.longValue())
                    return new JsonPrimitive(src.longValue());
                return new JsonPrimitive(src);
            }
        });
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Access.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.AccessAllowedReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.AccessAllowedRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.AccessDenied.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.AccessDeniedReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.AccessTokenPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ActionInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ActionOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ApplicationCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ApplicationFlagCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ApplicationRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ApplicationVersionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ApplicationVersionsCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ApprovalRequestResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ApprovalSettings.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Audience.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.AudiencePost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.AuditLogEntryListingRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.AuditLogEntryListingRepCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.AuditLogEntryRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.AuthorizedAppDataRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.BigSegmentStoreIntegration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.BigSegmentStoreIntegrationCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.BigSegmentStoreIntegrationCollectionLinks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.BigSegmentStoreIntegrationLinks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.BigSegmentStoreStatus.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.BigSegmentTarget.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.BooleanDefaults.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.BooleanFlagDefaults.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.BranchCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.BranchRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.BulkEditMembersRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.BulkEditTeamsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Clause.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Client.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ClientCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ClientSideAvailability.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ClientSideAvailabilityPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CompletedBy.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ConditionInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ConditionOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ConfidenceIntervalRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Conflict.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ConflictOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextAttributeName.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextAttributeNames.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextAttributeNamesCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextAttributeValue.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextAttributeValues.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextAttributeValuesCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextInstanceEvaluation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextInstanceEvaluationReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextInstanceEvaluations.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextInstanceRecord.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextInstanceSearch.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextInstanceSegmentMembership.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextInstanceSegmentMemberships.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextInstances.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextKindRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextKindsCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextRecord.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ContextSearch.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Contexts.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CopiedFromEnv.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CreateApprovalRequestRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CreateCopyFlagConfigApprovalRequestRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CreateFlagConfigApprovalRequestRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CreatePhaseInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CreateReleasePipelineInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CreateWorkflowTemplateInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CredibleIntervalRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CustomProperty.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CustomRole.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CustomRolePost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CustomRoles.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CustomWorkflowInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CustomWorkflowMeta.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CustomWorkflowOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CustomWorkflowStageMeta.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.CustomWorkflowsListingOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.DefaultClientSideAvailability.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.DefaultClientSideAvailabilityPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Defaults.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.DependentExperimentRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.DependentFlag.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.DependentFlagEnvironment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.DependentFlagsByEnvironment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.DependentMetricGroupRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.DependentMetricGroupRepWithMetrics.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.DependentMetricOrMetricGroupRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.DeploymentCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.DeploymentRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Destination.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.DestinationPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Destinations.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Distribution.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Environment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.EnvironmentPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.EnvironmentSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Environments.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.EvaluationReason.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.EvaluationsSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExecutionOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExpandableApprovalRequestResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExpandableApprovalRequestsResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExpandedFlagRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Experiment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExperimentAllocationRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExperimentBayesianResultsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExperimentCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExperimentEnabledPeriodRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExperimentEnvironmentSettingRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExperimentInfoRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExperimentMetadataRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExperimentPatchInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExperimentPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExperimentResults.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExperimentStatsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExperimentTimeSeriesSlice.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExperimentTimeSeriesVariationSlice.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExperimentTotalsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExpiringTarget.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExpiringTargetError.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExpiringTargetGetResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExpiringTargetPatchResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExpiringUserTargetGetResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExpiringUserTargetItem.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExpiringUserTargetPatchResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Export.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Extinction.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ExtinctionCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FailureReasonRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FeatureFlag.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FeatureFlagBody.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FeatureFlagConfig.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FeatureFlagScheduledChange.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FeatureFlagScheduledChanges.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FeatureFlagStatus.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FeatureFlagStatusAcrossEnvironments.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FeatureFlagStatuses.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FeatureFlags.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FileRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagConfigApprovalRequestResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagConfigApprovalRequestsResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagConfigEvaluation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagConfigMigrationSettingsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagCopyConfigEnvironment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagCopyConfigPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagDefaultsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagEventCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagEventExperiment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagEventExperimentCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagEventExperimentIteration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagEventImpactRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagEventMemberRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagEventRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagFollowersByProjEnvGetRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagFollowersGetRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagLinkCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagLinkMember.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagLinkPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagLinkRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagListingRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagMigrationSettingsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagReferenceCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagReferenceRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagScheduledChangesInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagSempatch.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagStatusRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FlagTriggerInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FollowFlagMember.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.FollowersPerFlag.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.HunkRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InitiatorRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightGroup.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightGroupCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightGroupCollectionMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightGroupCollectionScoreMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightGroupScores.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightGroupsCountByIndicator.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightPeriod.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightScores.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsChart.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsChartBounds.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsChartMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsChartMetric.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsChartSeries.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsChartSeriesDataPoint.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsChartSeriesMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsChartSeriesMetadataAxis.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsMetricIndicatorRange.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsMetricScore.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsMetricTierDefinition.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsRepository.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsRepositoryCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsRepositoryProject.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsRepositoryProjectCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InsightsRepositoryProjectMappings.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.InstructionUserRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Integration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IntegrationDeliveryConfiguration.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IntegrationDeliveryConfigurationCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IntegrationDeliveryConfigurationCollectionLinks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IntegrationDeliveryConfigurationLinks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IntegrationDeliveryConfigurationPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IntegrationDeliveryConfigurationResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IntegrationMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IntegrationStatus.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IntegrationStatusRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IntegrationSubscriptionStatusRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Integrations.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IpList.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IterationInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.IterationRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.LastSeenMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.LeadTimeStagesRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.LegacyExperimentRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Link.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MaintainerRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MaintainerTeam.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Member.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MemberDataRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MemberImportItem.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MemberPermissionGrantSummaryRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MemberSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MemberTeamSummaryRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MemberTeamsPostInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Members.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MembersPatchInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MetricCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MetricEventDefaultRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MetricGroupCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MetricGroupPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MetricGroupRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MetricGroupResultsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MetricInGroupRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MetricInGroupResultsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MetricInMetricGroupInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MetricInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MetricListingRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MetricPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MetricRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MetricSeen.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MetricV2Rep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MigrationSafetyIssueRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MigrationSettingsPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ModelImport.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Modification.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MultiEnvironmentDependentFlag.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.MultiEnvironmentDependentFlags.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.NewMemberForm.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.OauthClientPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ParameterDefault.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ParameterRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ParentResourceRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PatchFlagsRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PatchOperation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PatchSegmentExpiringTargetInputRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PatchSegmentExpiringTargetInstruction.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PatchSegmentInstruction.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PatchSegmentRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PatchUsersRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PatchWithComment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PermissionGrantInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Phase.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PostApprovalRequestApplyRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PostApprovalRequestReviewRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PostDeploymentEventInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PostFlagScheduledChangesInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PostInsightGroupParams.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Prerequisite.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Project.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProjectPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProjectRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProjectSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ProjectSummaryCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Projects.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PullRequestCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PullRequestLeadTimeRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PullRequestRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.PutBranch.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.RandomizationSettingsPut.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.RandomizationSettingsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.RandomizationUnitInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.RandomizationUnitRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.RecentTriggerBody.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ReferenceRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.RelativeDifferenceRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.RelayAutoConfigCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.RelayAutoConfigPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.RelayAutoConfigRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Release.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ReleasePhase.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ReleasePipeline.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ReleasePipelineCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.RepositoryCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.RepositoryPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.RepositoryRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ResourceAccess.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ResourceIDResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ResourceId.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ReviewOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ReviewResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Rollout.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.RootResponse.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Rule.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.RuleClause.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SdkListRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SdkVersionListRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SdkVersionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SegmentBody.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SegmentMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SegmentTarget.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SegmentUserList.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SegmentUserState.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SegmentsBetaStartBigSegmentImportRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Series.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SeriesIntervalsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SeriesListRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SlicedResultsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SourceEnv.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SourceFlag.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.StageInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.StageOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Statement.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.StatementPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.StatisticCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.StatisticRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.StatisticsRoot.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.StoreIntegrationError.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SubjectDataRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.SubscriptionPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TagCollection.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Target.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TargetResourceRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Team.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TeamCustomRole.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TeamCustomRoles.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TeamImportsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TeamMaintainers.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TeamMembers.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TeamPatchInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TeamPostInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TeamProjects.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Teams.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TeamsAddMultipleMembersToTeamRequest.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TeamsPatchInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TimestampRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Token.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TokenSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Tokens.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TreatmentInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TreatmentParameterInput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TreatmentRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TreatmentResultRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TriggerPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TriggerWorkflowCollectionRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.TriggerWorkflowRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UpsertContextKindPayload.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UpsertFlagDefaultsPayload.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UpsertPayloadRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UpsertResponseRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UrlPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.User.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UserAttributeNamesRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UserFlagSetting.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UserFlagSettings.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UserRecord.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UserSegment.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UserSegmentRule.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UserSegments.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Users.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.UsersRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.ValuePut.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Variation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.VariationEvalSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.VariationOrRolloutRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.VariationSummary.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.VersionsRep.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Webhook.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WebhookPost.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.Webhooks.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WeightedVariation.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WorkflowTemplateMetadata.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WorkflowTemplateOutput.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WorkflowTemplateParameter.CustomTypeAdapterFactory());
        gsonBuilder.registerTypeAdapterFactory(new com.konfigthis.client.model.WorkflowTemplatesListingOutputRep.CustomTypeAdapterFactory());
        gsonBuilder.disableHtmlEscaping();
        gson = gsonBuilder.create();
    }

    /**
     * Get Gson.
     *
     * @return Gson
     */
    public static Gson getGson() {
        return gson;
    }

    /**
     * Set Gson.
     *
     * @param gson Gson
     */
    public static void setGson(Gson gson) {
        JSON.gson = gson;
    }

    public static void setLenientOnJson(boolean lenientOnJson) {
        isLenientOnJson = lenientOnJson;
    }

    /**
     * Serialize the given Java object into JSON string.
     *
     * @param obj Object
     * @return String representation of the JSON
     */
    public static String serialize(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * Deserialize the given JSON string to Java object.
     *
     * @param <T>        Type
     * @param body       The JSON string
     * @param returnType The type to deserialize into
     * @return The deserialized Java object
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String body, Type returnType) {
        try {
            if (isLenientOnJson) {
                JsonReader jsonReader = new JsonReader(new StringReader(body));
                // see https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/stream/JsonReader.html#setLenient(boolean)
                jsonReader.setLenient(true);
                return gson.fromJson(jsonReader, returnType);
            } else {
                return gson.fromJson(body, returnType);
            }
        } catch (JsonParseException e) {
            // Fallback processing when failed to parse JSON form response body:
            // return the response body string directly for the String return type;
            if (returnType.equals(String.class)) {
                return (T) body;
            } else {
                throw (e);
            }
        }
    }

    /**
     * Gson TypeAdapter for Byte Array type
     */
    public static class ByteArrayAdapter extends TypeAdapter<byte[]> {

        @Override
        public void write(JsonWriter out, byte[] value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(ByteString.of(value).base64());
            }
        }

        @Override
        public byte[] read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String bytesAsBase64 = in.nextString();
                    ByteString byteString = ByteString.decodeBase64(bytesAsBase64);
                    return byteString.toByteArray();
            }
        }
    }

    /**
     * Gson TypeAdapter for JSR310 OffsetDateTime type
     */
    public static class OffsetDateTimeTypeAdapter extends TypeAdapter<OffsetDateTime> {

        private DateTimeFormatter formatter;

        public OffsetDateTimeTypeAdapter() {
            this(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }

        public OffsetDateTimeTypeAdapter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        public void setFormat(DateTimeFormatter dateFormat) {
            this.formatter = dateFormat;
        }

        @Override
        public void write(JsonWriter out, OffsetDateTime date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(date));
            }
        }

        @Override
        public OffsetDateTime read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    if (date.endsWith("+0000")) {
                        date = date.substring(0, date.length()-5) + "Z";
                    }
                    return OffsetDateTime.parse(date, formatter);
            }
        }
    }

    /**
     * Gson TypeAdapter for JSR310 LocalDate type
     */
    public static class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {

        private DateTimeFormatter formatter;

        public LocalDateTypeAdapter() {
            this(DateTimeFormatter.ISO_LOCAL_DATE);
        }

        public LocalDateTypeAdapter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        public void setFormat(DateTimeFormatter dateFormat) {
            this.formatter = dateFormat;
        }

        @Override
        public void write(JsonWriter out, LocalDate date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(date));
            }
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    return LocalDate.parse(date, formatter);
            }
        }
    }

    public static void setOffsetDateTimeFormat(DateTimeFormatter dateFormat) {
        offsetDateTimeTypeAdapter.setFormat(dateFormat);
    }

    public static void setLocalDateFormat(DateTimeFormatter dateFormat) {
        localDateTypeAdapter.setFormat(dateFormat);
    }

    /**
     * Gson TypeAdapter for java.sql.Date type
     * If the dateFormat is null, a simple "yyyy-MM-dd" format will be used
     * (more efficient than SimpleDateFormat).
     */
    public static class SqlDateTypeAdapter extends TypeAdapter<java.sql.Date> {

        private DateFormat dateFormat;

        public SqlDateTypeAdapter() {}

        public SqlDateTypeAdapter(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        public void setFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public void write(JsonWriter out, java.sql.Date date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                String value;
                if (dateFormat != null) {
                    value = dateFormat.format(date);
                } else {
                    value = date.toString();
                }
                out.value(value);
            }
        }

        @Override
        public java.sql.Date read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    String date = in.nextString();
                    try {
                        if (dateFormat != null) {
                            return new java.sql.Date(dateFormat.parse(date).getTime());
                        }
                        return new java.sql.Date(ISO8601Utils.parse(date, new ParsePosition(0)).getTime());
                    } catch (ParseException e) {
                        throw new JsonParseException(e);
                    }
            }
        }
    }

    /**
     * Gson TypeAdapter for java.util.Date type
     * If the dateFormat is null, ISO8601Utils will be used.
     */
    public static class DateTypeAdapter extends TypeAdapter<Date> {

        private DateFormat dateFormat;

        public DateTypeAdapter() {}

        public DateTypeAdapter(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        public void setFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public void write(JsonWriter out, Date date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                String value;
                if (dateFormat != null) {
                    value = dateFormat.format(date);
                } else {
                    value = ISO8601Utils.format(date, true);
                }
                out.value(value);
            }
        }

        @Override
        public Date read(JsonReader in) throws IOException {
            try {
                switch (in.peek()) {
                    case NULL:
                        in.nextNull();
                        return null;
                    default:
                        String date = in.nextString();
                        try {
                            if (dateFormat != null) {
                                return dateFormat.parse(date);
                            }
                            return ISO8601Utils.parse(date, new ParsePosition(0));
                        } catch (ParseException e) {
                            throw new JsonParseException(e);
                        }
                }
            } catch (IllegalArgumentException e) {
                throw new JsonParseException(e);
            }
        }
    }

    public static void setDateFormat(DateFormat dateFormat) {
        dateTypeAdapter.setFormat(dateFormat);
    }

    public static void setSqlDateFormat(DateFormat dateFormat) {
        sqlDateTypeAdapter.setFormat(dateFormat);
    }
}
