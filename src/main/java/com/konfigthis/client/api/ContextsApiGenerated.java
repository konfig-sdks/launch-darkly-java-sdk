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


package com.konfigthis.client.api;

import com.konfigthis.client.ApiCallback;
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.Pair;
import com.konfigthis.client.ProgressRequestBody;
import com.konfigthis.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import com.konfigthis.client.model.ContextAttributeNamesCollection;
import com.konfigthis.client.model.ContextAttributeValuesCollection;
import com.konfigthis.client.model.ContextInstanceEvaluations;
import com.konfigthis.client.model.ContextInstanceSearch;
import com.konfigthis.client.model.ContextInstances;
import com.konfigthis.client.model.ContextKindsCollectionRep;
import com.konfigthis.client.model.ContextSearch;
import com.konfigthis.client.model.Contexts;
import com.konfigthis.client.model.UpsertContextKindPayload;
import com.konfigthis.client.model.UpsertResponseRep;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class ContextsApiGenerated {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public ContextsApiGenerated() throws IllegalArgumentException {
        this(Configuration.getDefaultApiClient());
    }

    public ContextsApiGenerated(ApiClient apiClient) throws IllegalArgumentException {
        if (apiClient.getApiKey() == null) {
            throw new IllegalArgumentException("\"Authorization\" is required but no API key was provided. Please set \"Authorization\" with ApiClient#setApiKey(String).");
        }
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public int getHostIndex() {
        return localHostIndex;
    }

    public void setHostIndex(int hostIndex) {
        this.localHostIndex = hostIndex;
    }

    public String getCustomBaseUrl() {
        return localCustomBaseUrl;
    }

    public void setCustomBaseUrl(String customBaseUrl) {
        this.localCustomBaseUrl = customBaseUrl;
    }

    private okhttp3.Call createOrUpdateKindCall(String projectKey, String key, UpsertContextKindPayload upsertContextKindPayload, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = upsertContextKindPayload;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/context-kinds/{key}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "key" + "}", localVarApiClient.escapeString(key.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createOrUpdateKindValidateBeforeCall(String projectKey, String key, UpsertContextKindPayload upsertContextKindPayload, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling createOrUpdateKind(Async)");
        }

        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling createOrUpdateKind(Async)");
        }

        // verify the required parameter 'upsertContextKindPayload' is set
        if (upsertContextKindPayload == null) {
            throw new ApiException("Missing the required parameter 'upsertContextKindPayload' when calling createOrUpdateKind(Async)");
        }

        return createOrUpdateKindCall(projectKey, key, upsertContextKindPayload, _callback);

    }


    private ApiResponse<UpsertResponseRep> createOrUpdateKindWithHttpInfo(String projectKey, String key, UpsertContextKindPayload upsertContextKindPayload) throws ApiException {
        okhttp3.Call localVarCall = createOrUpdateKindValidateBeforeCall(projectKey, key, upsertContextKindPayload, null);
        Type localVarReturnType = new TypeToken<UpsertResponseRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call createOrUpdateKindAsync(String projectKey, String key, UpsertContextKindPayload upsertContextKindPayload, final ApiCallback<UpsertResponseRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = createOrUpdateKindValidateBeforeCall(projectKey, key, upsertContextKindPayload, _callback);
        Type localVarReturnType = new TypeToken<UpsertResponseRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class CreateOrUpdateKindRequestBuilder {
        private final String name;
        private final String projectKey;
        private final String key;
        private String description;
        private Integer version;
        private Boolean hideInTargeting;
        private Boolean archived;

        private CreateOrUpdateKindRequestBuilder(String name, String projectKey, String key) {
            this.name = name;
            this.projectKey = projectKey;
            this.key = key;
        }

        /**
         * Set description
         * @param description The context kind description (optional)
         * @return CreateOrUpdateKindRequestBuilder
         */
        public CreateOrUpdateKindRequestBuilder description(String description) {
            this.description = description;
            return this;
        }
        
        /**
         * Set version
         * @param version The context kind version. If not specified when the context kind is created, defaults to 1. (optional)
         * @return CreateOrUpdateKindRequestBuilder
         */
        public CreateOrUpdateKindRequestBuilder version(Integer version) {
            this.version = version;
            return this;
        }
        
        /**
         * Set hideInTargeting
         * @param hideInTargeting Alias for archived. (optional)
         * @return CreateOrUpdateKindRequestBuilder
         */
        public CreateOrUpdateKindRequestBuilder hideInTargeting(Boolean hideInTargeting) {
            this.hideInTargeting = hideInTargeting;
            return this;
        }
        
        /**
         * Set archived
         * @param archived Whether the context kind is archived. Archived context kinds are unavailable for targeting. (optional)
         * @return CreateOrUpdateKindRequestBuilder
         */
        public CreateOrUpdateKindRequestBuilder archived(Boolean archived) {
            this.archived = archived;
            return this;
        }
        
        /**
         * Build call for createOrUpdateKind
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context kind upsert response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            UpsertContextKindPayload upsertContextKindPayload = buildBodyParams();
            return createOrUpdateKindCall(projectKey, key, upsertContextKindPayload, _callback);
        }

        private UpsertContextKindPayload buildBodyParams() {
            UpsertContextKindPayload upsertContextKindPayload = new UpsertContextKindPayload();
            upsertContextKindPayload.description(this.description);
            upsertContextKindPayload.version(this.version);
            upsertContextKindPayload.name(this.name);
            upsertContextKindPayload.hideInTargeting(this.hideInTargeting);
            upsertContextKindPayload.archived(this.archived);
            return upsertContextKindPayload;
        }

        /**
         * Execute createOrUpdateKind request
         * @return UpsertResponseRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context kind upsert response </td><td>  -  </td></tr>
         </table>
         */
        public UpsertResponseRep execute() throws ApiException {
            UpsertContextKindPayload upsertContextKindPayload = buildBodyParams();
            ApiResponse<UpsertResponseRep> localVarResp = createOrUpdateKindWithHttpInfo(projectKey, key, upsertContextKindPayload);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute createOrUpdateKind request with HTTP info returned
         * @return ApiResponse&lt;UpsertResponseRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context kind upsert response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<UpsertResponseRep> executeWithHttpInfo() throws ApiException {
            UpsertContextKindPayload upsertContextKindPayload = buildBodyParams();
            return createOrUpdateKindWithHttpInfo(projectKey, key, upsertContextKindPayload);
        }

        /**
         * Execute createOrUpdateKind request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context kind upsert response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<UpsertResponseRep> _callback) throws ApiException {
            UpsertContextKindPayload upsertContextKindPayload = buildBodyParams();
            return createOrUpdateKindAsync(projectKey, key, upsertContextKindPayload, _callback);
        }
    }

    /**
     * Create or update context kind
     * Create or update a context kind by key. Only the included fields will be updated.
     * @param projectKey The project key (required)
     * @param key The context kind key (required)
     * @param upsertContextKindPayload  (required)
     * @return CreateOrUpdateKindRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Context kind upsert response </td><td>  -  </td></tr>
     </table>
     */
    public CreateOrUpdateKindRequestBuilder createOrUpdateKind(String name, String projectKey, String key) throws IllegalArgumentException {
        if (name == null) throw new IllegalArgumentException("\"name\" is required but got null");
            

        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (key == null) throw new IllegalArgumentException("\"key\" is required but got null");
            

        return new CreateOrUpdateKindRequestBuilder(name, projectKey, key);
    }
    private okhttp3.Call deleteContextInstanceCall(String projectKey, String environmentKey, String id, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/environments/{environmentKey}/context-instances/{id}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteContextInstanceValidateBeforeCall(String projectKey, String environmentKey, String id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling deleteContextInstance(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling deleteContextInstance(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling deleteContextInstance(Async)");
        }

        return deleteContextInstanceCall(projectKey, environmentKey, id, _callback);

    }


    private ApiResponse<Void> deleteContextInstanceWithHttpInfo(String projectKey, String environmentKey, String id) throws ApiException {
        okhttp3.Call localVarCall = deleteContextInstanceValidateBeforeCall(projectKey, environmentKey, id, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call deleteContextInstanceAsync(String projectKey, String environmentKey, String id, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteContextInstanceValidateBeforeCall(projectKey, environmentKey, id, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class DeleteContextInstanceRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private final String id;

        private DeleteContextInstanceRequestBuilder(String projectKey, String environmentKey, String id) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.id = id;
        }

        /**
         * Build call for deleteContextInstance
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return deleteContextInstanceCall(projectKey, environmentKey, id, _callback);
        }


        /**
         * Execute deleteContextInstance request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public void execute() throws ApiException {
            deleteContextInstanceWithHttpInfo(projectKey, environmentKey, id);
        }

        /**
         * Execute deleteContextInstance request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            return deleteContextInstanceWithHttpInfo(projectKey, environmentKey, id);
        }

        /**
         * Execute deleteContextInstance request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<Void> _callback) throws ApiException {
            return deleteContextInstanceAsync(projectKey, environmentKey, id, _callback);
        }
    }

    /**
     * Delete context instances
     * Delete context instances by ID.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param id The context instance ID (required)
     * @return DeleteContextInstanceRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
     </table>
     */
    public DeleteContextInstanceRequestBuilder deleteContextInstance(String projectKey, String environmentKey, String id) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (id == null) throw new IllegalArgumentException("\"id\" is required but got null");
            

        return new DeleteContextInstanceRequestBuilder(projectKey, environmentKey, id);
    }
    private okhttp3.Call evaluateFlagsForContextInstanceCall(String projectKey, String environmentKey, Map<String, Object> requestBody, Long limit, Long offset, String sort, String filter, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = requestBody;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/environments/{environmentKey}/flags/evaluate"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        if (sort != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sort", sort));
        }

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call evaluateFlagsForContextInstanceValidateBeforeCall(String projectKey, String environmentKey, Map<String, Object> requestBody, Long limit, Long offset, String sort, String filter, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling evaluateFlagsForContextInstance(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling evaluateFlagsForContextInstance(Async)");
        }

        // verify the required parameter 'requestBody' is set
        if (requestBody == null) {
            throw new ApiException("Missing the required parameter 'requestBody' when calling evaluateFlagsForContextInstance(Async)");
        }

        return evaluateFlagsForContextInstanceCall(projectKey, environmentKey, requestBody, limit, offset, sort, filter, _callback);

    }


    private ApiResponse<ContextInstanceEvaluations> evaluateFlagsForContextInstanceWithHttpInfo(String projectKey, String environmentKey, Map<String, Object> requestBody, Long limit, Long offset, String sort, String filter) throws ApiException {
        okhttp3.Call localVarCall = evaluateFlagsForContextInstanceValidateBeforeCall(projectKey, environmentKey, requestBody, limit, offset, sort, filter, null);
        Type localVarReturnType = new TypeToken<ContextInstanceEvaluations>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call evaluateFlagsForContextInstanceAsync(String projectKey, String environmentKey, Map<String, Object> requestBody, Long limit, Long offset, String sort, String filter, final ApiCallback<ContextInstanceEvaluations> _callback) throws ApiException {

        okhttp3.Call localVarCall = evaluateFlagsForContextInstanceValidateBeforeCall(projectKey, environmentKey, requestBody, limit, offset, sort, filter, _callback);
        Type localVarReturnType = new TypeToken<ContextInstanceEvaluations>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class EvaluateFlagsForContextInstanceRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private Long limit;
        private Long offset;
        private String sort;
        private String filter;

        private EvaluateFlagsForContextInstanceRequestBuilder(String projectKey, String environmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Set limit
         * @param limit The number of feature flags to return. Defaults to -1, which returns all flags (optional)
         * @return EvaluateFlagsForContextInstanceRequestBuilder
         */
        public EvaluateFlagsForContextInstanceRequestBuilder limit(Long limit) {
            this.limit = limit;
            return this;
        }
        
        /**
         * Set offset
         * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
         * @return EvaluateFlagsForContextInstanceRequestBuilder
         */
        public EvaluateFlagsForContextInstanceRequestBuilder offset(Long offset) {
            this.offset = offset;
            return this;
        }
        
        /**
         * Set sort
         * @param sort A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order (optional)
         * @return EvaluateFlagsForContextInstanceRequestBuilder
         */
        public EvaluateFlagsForContextInstanceRequestBuilder sort(String sort) {
            this.sort = sort;
            return this;
        }
        
        /**
         * Set filter
         * @param filter A comma-separated list of filters. Each filter is of the form &#x60;field operator value&#x60;. Supported fields are explained above. (optional)
         * @return EvaluateFlagsForContextInstanceRequestBuilder
         */
        public EvaluateFlagsForContextInstanceRequestBuilder filter(String filter) {
            this.filter = filter;
            return this;
        }
        
        /**
         * Build call for evaluateFlagsForContextInstance
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Flag evaluation collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            Map<String, Object> requestBody = buildBodyParams();
            return evaluateFlagsForContextInstanceCall(projectKey, environmentKey, requestBody, limit, offset, sort, filter, _callback);
        }

        private Map<String, Object> buildBodyParams() {
            Map<String, Object> requestBody = new HashMap<String, Object>();
            return requestBody;
        }

        /**
         * Execute evaluateFlagsForContextInstance request
         * @return ContextInstanceEvaluations
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Flag evaluation collection response </td><td>  -  </td></tr>
         </table>
         */
        public ContextInstanceEvaluations execute() throws ApiException {
            Map<String, Object> requestBody = buildBodyParams();
            ApiResponse<ContextInstanceEvaluations> localVarResp = evaluateFlagsForContextInstanceWithHttpInfo(projectKey, environmentKey, requestBody, limit, offset, sort, filter);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute evaluateFlagsForContextInstance request with HTTP info returned
         * @return ApiResponse&lt;ContextInstanceEvaluations&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Flag evaluation collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ContextInstanceEvaluations> executeWithHttpInfo() throws ApiException {
            Map<String, Object> requestBody = buildBodyParams();
            return evaluateFlagsForContextInstanceWithHttpInfo(projectKey, environmentKey, requestBody, limit, offset, sort, filter);
        }

        /**
         * Execute evaluateFlagsForContextInstance request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Flag evaluation collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ContextInstanceEvaluations> _callback) throws ApiException {
            Map<String, Object> requestBody = buildBodyParams();
            return evaluateFlagsForContextInstanceAsync(projectKey, environmentKey, requestBody, limit, offset, sort, filter, _callback);
        }
    }

    /**
     * Evaluate flags for context instance
     * Evaluate flags for a context instance, for example, to determine the expected flag variation. **Do not use this API instead of an SDK.** The LaunchDarkly SDKs are specialized for the tasks of evaluating feature flags in your application at scale and generating analytics events based on those evaluations. This API is not designed for that use case. Any evaluations you perform with this API will not be reflected in features such as flag statuses and flag insights. Context instances evaluated by this API will not appear in the Contexts list. To learn more, read [Comparing LaunchDarkly&#39;s SDKs and REST API](https://docs.launchdarkly.com/guide/api/comparing-sdk-rest-api).  ### Filtering   LaunchDarkly supports the &#x60;filter&#x60; query param for filtering, with the following fields:  - &#x60;query&#x60; filters for a string that matches against the flags&#39; keys and names. It is not case sensitive. For example: &#x60;filter&#x3D;query equals dark-mode&#x60;. - &#x60;tags&#x60; filters the list to flags that have all of the tags in the list. For example: &#x60;filter&#x3D;tags contains [\&quot;beta\&quot;,\&quot;q1\&quot;]&#x60;.  You can also apply multiple filters at once. For example, setting &#x60;filter&#x3D;query equals dark-mode, tags contains [\&quot;beta\&quot;,\&quot;q1\&quot;]&#x60; matches flags which match the key or name &#x60;dark-mode&#x60; and are tagged &#x60;beta&#x60; and &#x60;q1&#x60;. 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param requestBody  (required)
     * @return EvaluateFlagsForContextInstanceRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Flag evaluation collection response </td><td>  -  </td></tr>
     </table>
     */
    public EvaluateFlagsForContextInstanceRequestBuilder evaluateFlagsForContextInstance(String projectKey, String environmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new EvaluateFlagsForContextInstanceRequestBuilder(projectKey, environmentKey);
    }
    private okhttp3.Call getAttributeNamesCall(String projectKey, String environmentKey, String filter, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/environments/{environmentKey}/context-attributes"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAttributeNamesValidateBeforeCall(String projectKey, String environmentKey, String filter, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getAttributeNames(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getAttributeNames(Async)");
        }

        return getAttributeNamesCall(projectKey, environmentKey, filter, _callback);

    }


    private ApiResponse<ContextAttributeNamesCollection> getAttributeNamesWithHttpInfo(String projectKey, String environmentKey, String filter) throws ApiException {
        okhttp3.Call localVarCall = getAttributeNamesValidateBeforeCall(projectKey, environmentKey, filter, null);
        Type localVarReturnType = new TypeToken<ContextAttributeNamesCollection>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getAttributeNamesAsync(String projectKey, String environmentKey, String filter, final ApiCallback<ContextAttributeNamesCollection> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAttributeNamesValidateBeforeCall(projectKey, environmentKey, filter, _callback);
        Type localVarReturnType = new TypeToken<ContextAttributeNamesCollection>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetAttributeNamesRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private String filter;

        private GetAttributeNamesRequestBuilder(String projectKey, String environmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Set filter
         * @param filter A comma-separated list of context filters. This endpoint only accepts &#x60;kind&#x60; filters, with the &#x60;equals&#x60; operator, and &#x60;name&#x60; filters, with the &#x60;startsWith&#x60; operator. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). (optional)
         * @return GetAttributeNamesRequestBuilder
         */
        public GetAttributeNamesRequestBuilder filter(String filter) {
            this.filter = filter;
            return this;
        }
        
        /**
         * Build call for getAttributeNames
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context attribute names collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getAttributeNamesCall(projectKey, environmentKey, filter, _callback);
        }


        /**
         * Execute getAttributeNames request
         * @return ContextAttributeNamesCollection
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context attribute names collection response </td><td>  -  </td></tr>
         </table>
         */
        public ContextAttributeNamesCollection execute() throws ApiException {
            ApiResponse<ContextAttributeNamesCollection> localVarResp = getAttributeNamesWithHttpInfo(projectKey, environmentKey, filter);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getAttributeNames request with HTTP info returned
         * @return ApiResponse&lt;ContextAttributeNamesCollection&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context attribute names collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ContextAttributeNamesCollection> executeWithHttpInfo() throws ApiException {
            return getAttributeNamesWithHttpInfo(projectKey, environmentKey, filter);
        }

        /**
         * Execute getAttributeNames request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context attribute names collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ContextAttributeNamesCollection> _callback) throws ApiException {
            return getAttributeNamesAsync(projectKey, environmentKey, filter, _callback);
        }
    }

    /**
     * Get context attribute names
     * Get context attribute names. Returns only the first 100 attribute names per context.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @return GetAttributeNamesRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Context attribute names collection response </td><td>  -  </td></tr>
     </table>
     */
    public GetAttributeNamesRequestBuilder getAttributeNames(String projectKey, String environmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new GetAttributeNamesRequestBuilder(projectKey, environmentKey);
    }
    private okhttp3.Call getByKindAndKeyCall(String projectKey, String environmentKey, String kind, String key, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/environments/{environmentKey}/contexts/{kind}/{key}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "kind" + "}", localVarApiClient.escapeString(kind.toString()))
            .replace("{" + "key" + "}", localVarApiClient.escapeString(key.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (continuationToken != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("continuationToken", continuationToken));
        }

        if (sort != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sort", sort));
        }

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
        }

        if (includeTotalCount != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("includeTotalCount", includeTotalCount));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getByKindAndKeyValidateBeforeCall(String projectKey, String environmentKey, String kind, String key, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getByKindAndKey(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getByKindAndKey(Async)");
        }

        // verify the required parameter 'kind' is set
        if (kind == null) {
            throw new ApiException("Missing the required parameter 'kind' when calling getByKindAndKey(Async)");
        }

        // verify the required parameter 'key' is set
        if (key == null) {
            throw new ApiException("Missing the required parameter 'key' when calling getByKindAndKey(Async)");
        }

        return getByKindAndKeyCall(projectKey, environmentKey, kind, key, limit, continuationToken, sort, filter, includeTotalCount, _callback);

    }


    private ApiResponse<Contexts> getByKindAndKeyWithHttpInfo(String projectKey, String environmentKey, String kind, String key, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount) throws ApiException {
        okhttp3.Call localVarCall = getByKindAndKeyValidateBeforeCall(projectKey, environmentKey, kind, key, limit, continuationToken, sort, filter, includeTotalCount, null);
        Type localVarReturnType = new TypeToken<Contexts>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getByKindAndKeyAsync(String projectKey, String environmentKey, String kind, String key, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount, final ApiCallback<Contexts> _callback) throws ApiException {

        okhttp3.Call localVarCall = getByKindAndKeyValidateBeforeCall(projectKey, environmentKey, kind, key, limit, continuationToken, sort, filter, includeTotalCount, _callback);
        Type localVarReturnType = new TypeToken<Contexts>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetByKindAndKeyRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private final String kind;
        private final String key;
        private Long limit;
        private String continuationToken;
        private String sort;
        private String filter;
        private Boolean includeTotalCount;

        private GetByKindAndKeyRequestBuilder(String projectKey, String environmentKey, String kind, String key) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.kind = kind;
            this.key = key;
        }

        /**
         * Set limit
         * @param limit Specifies the maximum number of items in the collection to return (max: 50, default: 20) (optional)
         * @return GetByKindAndKeyRequestBuilder
         */
        public GetByKindAndKeyRequestBuilder limit(Long limit) {
            this.limit = limit;
            return this;
        }
        
        /**
         * Set continuationToken
         * @param continuationToken Limits results to contexts with sort values after the value specified. You can use this for pagination, however, we recommend using the &#x60;next&#x60; link we provide instead. (optional)
         * @return GetByKindAndKeyRequestBuilder
         */
        public GetByKindAndKeyRequestBuilder continuationToken(String continuationToken) {
            this.continuationToken = continuationToken;
            return this;
        }
        
        /**
         * Set sort
         * @param sort Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying &#x60;ts&#x60; for this value, or descending order by specifying &#x60;-ts&#x60;. (optional)
         * @return GetByKindAndKeyRequestBuilder
         */
        public GetByKindAndKeyRequestBuilder sort(String sort) {
            this.sort = sort;
            return this;
        }
        
        /**
         * Set filter
         * @param filter A comma-separated list of context filters. This endpoint only accepts an &#x60;applicationId&#x60; filter. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). (optional)
         * @return GetByKindAndKeyRequestBuilder
         */
        public GetByKindAndKeyRequestBuilder filter(String filter) {
            this.filter = filter;
            return this;
        }
        
        /**
         * Set includeTotalCount
         * @param includeTotalCount Specifies whether to include or omit the total count of matching contexts. Defaults to true. (optional)
         * @return GetByKindAndKeyRequestBuilder
         */
        public GetByKindAndKeyRequestBuilder includeTotalCount(Boolean includeTotalCount) {
            this.includeTotalCount = includeTotalCount;
            return this;
        }
        
        /**
         * Build call for getByKindAndKey
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Contexts collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getByKindAndKeyCall(projectKey, environmentKey, kind, key, limit, continuationToken, sort, filter, includeTotalCount, _callback);
        }


        /**
         * Execute getByKindAndKey request
         * @return Contexts
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Contexts collection response </td><td>  -  </td></tr>
         </table>
         */
        public Contexts execute() throws ApiException {
            ApiResponse<Contexts> localVarResp = getByKindAndKeyWithHttpInfo(projectKey, environmentKey, kind, key, limit, continuationToken, sort, filter, includeTotalCount);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getByKindAndKey request with HTTP info returned
         * @return ApiResponse&lt;Contexts&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Contexts collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Contexts> executeWithHttpInfo() throws ApiException {
            return getByKindAndKeyWithHttpInfo(projectKey, environmentKey, kind, key, limit, continuationToken, sort, filter, includeTotalCount);
        }

        /**
         * Execute getByKindAndKey request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Contexts collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<Contexts> _callback) throws ApiException {
            return getByKindAndKeyAsync(projectKey, environmentKey, kind, key, limit, continuationToken, sort, filter, includeTotalCount, _callback);
        }
    }

    /**
     * Get contexts
     * Get contexts based on kind and key.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param kind The context kind (required)
     * @param key The context key (required)
     * @return GetByKindAndKeyRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Contexts collection response </td><td>  -  </td></tr>
     </table>
     */
    public GetByKindAndKeyRequestBuilder getByKindAndKey(String projectKey, String environmentKey, String kind, String key) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (kind == null) throw new IllegalArgumentException("\"kind\" is required but got null");
            

        if (key == null) throw new IllegalArgumentException("\"key\" is required but got null");
            

        return new GetByKindAndKeyRequestBuilder(projectKey, environmentKey, kind, key);
    }
    private okhttp3.Call getContextAttributeValuesCall(String projectKey, String environmentKey, String attributeName, String filter, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/environments/{environmentKey}/context-attributes/{attributeName}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "attributeName" + "}", localVarApiClient.escapeString(attributeName.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getContextAttributeValuesValidateBeforeCall(String projectKey, String environmentKey, String attributeName, String filter, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getContextAttributeValues(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getContextAttributeValues(Async)");
        }

        // verify the required parameter 'attributeName' is set
        if (attributeName == null) {
            throw new ApiException("Missing the required parameter 'attributeName' when calling getContextAttributeValues(Async)");
        }

        return getContextAttributeValuesCall(projectKey, environmentKey, attributeName, filter, _callback);

    }


    private ApiResponse<ContextAttributeValuesCollection> getContextAttributeValuesWithHttpInfo(String projectKey, String environmentKey, String attributeName, String filter) throws ApiException {
        okhttp3.Call localVarCall = getContextAttributeValuesValidateBeforeCall(projectKey, environmentKey, attributeName, filter, null);
        Type localVarReturnType = new TypeToken<ContextAttributeValuesCollection>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getContextAttributeValuesAsync(String projectKey, String environmentKey, String attributeName, String filter, final ApiCallback<ContextAttributeValuesCollection> _callback) throws ApiException {

        okhttp3.Call localVarCall = getContextAttributeValuesValidateBeforeCall(projectKey, environmentKey, attributeName, filter, _callback);
        Type localVarReturnType = new TypeToken<ContextAttributeValuesCollection>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetContextAttributeValuesRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private final String attributeName;
        private String filter;

        private GetContextAttributeValuesRequestBuilder(String projectKey, String environmentKey, String attributeName) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.attributeName = attributeName;
        }

        /**
         * Set filter
         * @param filter A comma-separated list of context filters. This endpoint only accepts &#x60;kind&#x60; filters, with the &#x60;equals&#x60; operator, and &#x60;value&#x60; filters, with the &#x60;startsWith&#x60; operator. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). (optional)
         * @return GetContextAttributeValuesRequestBuilder
         */
        public GetContextAttributeValuesRequestBuilder filter(String filter) {
            this.filter = filter;
            return this;
        }
        
        /**
         * Build call for getContextAttributeValues
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context attribute values collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getContextAttributeValuesCall(projectKey, environmentKey, attributeName, filter, _callback);
        }


        /**
         * Execute getContextAttributeValues request
         * @return ContextAttributeValuesCollection
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context attribute values collection response </td><td>  -  </td></tr>
         </table>
         */
        public ContextAttributeValuesCollection execute() throws ApiException {
            ApiResponse<ContextAttributeValuesCollection> localVarResp = getContextAttributeValuesWithHttpInfo(projectKey, environmentKey, attributeName, filter);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getContextAttributeValues request with HTTP info returned
         * @return ApiResponse&lt;ContextAttributeValuesCollection&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context attribute values collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ContextAttributeValuesCollection> executeWithHttpInfo() throws ApiException {
            return getContextAttributeValuesWithHttpInfo(projectKey, environmentKey, attributeName, filter);
        }

        /**
         * Execute getContextAttributeValues request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context attribute values collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ContextAttributeValuesCollection> _callback) throws ApiException {
            return getContextAttributeValuesAsync(projectKey, environmentKey, attributeName, filter, _callback);
        }
    }

    /**
     * Get context attribute values
     * Get context attribute values.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param attributeName The attribute name (required)
     * @return GetContextAttributeValuesRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Context attribute values collection response </td><td>  -  </td></tr>
     </table>
     */
    public GetContextAttributeValuesRequestBuilder getContextAttributeValues(String projectKey, String environmentKey, String attributeName) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (attributeName == null) throw new IllegalArgumentException("\"attributeName\" is required but got null");
            

        return new GetContextAttributeValuesRequestBuilder(projectKey, environmentKey, attributeName);
    }
    private okhttp3.Call getContextInstancesCall(String projectKey, String environmentKey, String id, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/environments/{environmentKey}/context-instances/{id}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (continuationToken != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("continuationToken", continuationToken));
        }

        if (sort != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sort", sort));
        }

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
        }

        if (includeTotalCount != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("includeTotalCount", includeTotalCount));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getContextInstancesValidateBeforeCall(String projectKey, String environmentKey, String id, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getContextInstances(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getContextInstances(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getContextInstances(Async)");
        }

        return getContextInstancesCall(projectKey, environmentKey, id, limit, continuationToken, sort, filter, includeTotalCount, _callback);

    }


    private ApiResponse<ContextInstances> getContextInstancesWithHttpInfo(String projectKey, String environmentKey, String id, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount) throws ApiException {
        okhttp3.Call localVarCall = getContextInstancesValidateBeforeCall(projectKey, environmentKey, id, limit, continuationToken, sort, filter, includeTotalCount, null);
        Type localVarReturnType = new TypeToken<ContextInstances>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getContextInstancesAsync(String projectKey, String environmentKey, String id, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount, final ApiCallback<ContextInstances> _callback) throws ApiException {

        okhttp3.Call localVarCall = getContextInstancesValidateBeforeCall(projectKey, environmentKey, id, limit, continuationToken, sort, filter, includeTotalCount, _callback);
        Type localVarReturnType = new TypeToken<ContextInstances>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetContextInstancesRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private final String id;
        private Long limit;
        private String continuationToken;
        private String sort;
        private String filter;
        private Boolean includeTotalCount;

        private GetContextInstancesRequestBuilder(String projectKey, String environmentKey, String id) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.id = id;
        }

        /**
         * Set limit
         * @param limit Specifies the maximum number of context instances to return (max: 50, default: 20) (optional)
         * @return GetContextInstancesRequestBuilder
         */
        public GetContextInstancesRequestBuilder limit(Long limit) {
            this.limit = limit;
            return this;
        }
        
        /**
         * Set continuationToken
         * @param continuationToken Limits results to context instances with sort values after the value specified. You can use this for pagination, however, we recommend using the &#x60;next&#x60; link we provide instead. (optional)
         * @return GetContextInstancesRequestBuilder
         */
        public GetContextInstancesRequestBuilder continuationToken(String continuationToken) {
            this.continuationToken = continuationToken;
            return this;
        }
        
        /**
         * Set sort
         * @param sort Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying &#x60;ts&#x60; for this value, or descending order by specifying &#x60;-ts&#x60;. (optional)
         * @return GetContextInstancesRequestBuilder
         */
        public GetContextInstancesRequestBuilder sort(String sort) {
            this.sort = sort;
            return this;
        }
        
        /**
         * Set filter
         * @param filter A comma-separated list of context filters. This endpoint only accepts an &#x60;applicationId&#x60; filter. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). (optional)
         * @return GetContextInstancesRequestBuilder
         */
        public GetContextInstancesRequestBuilder filter(String filter) {
            this.filter = filter;
            return this;
        }
        
        /**
         * Set includeTotalCount
         * @param includeTotalCount Specifies whether to include or omit the total count of matching context instances. Defaults to true. (optional)
         * @return GetContextInstancesRequestBuilder
         */
        public GetContextInstancesRequestBuilder includeTotalCount(Boolean includeTotalCount) {
            this.includeTotalCount = includeTotalCount;
            return this;
        }
        
        /**
         * Build call for getContextInstances
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context instances collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getContextInstancesCall(projectKey, environmentKey, id, limit, continuationToken, sort, filter, includeTotalCount, _callback);
        }


        /**
         * Execute getContextInstances request
         * @return ContextInstances
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context instances collection response </td><td>  -  </td></tr>
         </table>
         */
        public ContextInstances execute() throws ApiException {
            ApiResponse<ContextInstances> localVarResp = getContextInstancesWithHttpInfo(projectKey, environmentKey, id, limit, continuationToken, sort, filter, includeTotalCount);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getContextInstances request with HTTP info returned
         * @return ApiResponse&lt;ContextInstances&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context instances collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ContextInstances> executeWithHttpInfo() throws ApiException {
            return getContextInstancesWithHttpInfo(projectKey, environmentKey, id, limit, continuationToken, sort, filter, includeTotalCount);
        }

        /**
         * Execute getContextInstances request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context instances collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ContextInstances> _callback) throws ApiException {
            return getContextInstancesAsync(projectKey, environmentKey, id, limit, continuationToken, sort, filter, includeTotalCount, _callback);
        }
    }

    /**
     * Get context instances
     * Get context instances by ID.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param id The context instance ID (required)
     * @return GetContextInstancesRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Context instances collection response </td><td>  -  </td></tr>
     </table>
     */
    public GetContextInstancesRequestBuilder getContextInstances(String projectKey, String environmentKey, String id) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (id == null) throw new IllegalArgumentException("\"id\" is required but got null");
            

        return new GetContextInstancesRequestBuilder(projectKey, environmentKey, id);
    }
    private okhttp3.Call listContextKindsByProjectCall(String projectKey, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/context-kinds"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call listContextKindsByProjectValidateBeforeCall(String projectKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling listContextKindsByProject(Async)");
        }

        return listContextKindsByProjectCall(projectKey, _callback);

    }


    private ApiResponse<ContextKindsCollectionRep> listContextKindsByProjectWithHttpInfo(String projectKey) throws ApiException {
        okhttp3.Call localVarCall = listContextKindsByProjectValidateBeforeCall(projectKey, null);
        Type localVarReturnType = new TypeToken<ContextKindsCollectionRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call listContextKindsByProjectAsync(String projectKey, final ApiCallback<ContextKindsCollectionRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = listContextKindsByProjectValidateBeforeCall(projectKey, _callback);
        Type localVarReturnType = new TypeToken<ContextKindsCollectionRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ListContextKindsByProjectRequestBuilder {
        private final String projectKey;

        private ListContextKindsByProjectRequestBuilder(String projectKey) {
            this.projectKey = projectKey;
        }

        /**
         * Build call for listContextKindsByProject
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context kinds collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return listContextKindsByProjectCall(projectKey, _callback);
        }


        /**
         * Execute listContextKindsByProject request
         * @return ContextKindsCollectionRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context kinds collection response </td><td>  -  </td></tr>
         </table>
         */
        public ContextKindsCollectionRep execute() throws ApiException {
            ApiResponse<ContextKindsCollectionRep> localVarResp = listContextKindsByProjectWithHttpInfo(projectKey);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute listContextKindsByProject request with HTTP info returned
         * @return ApiResponse&lt;ContextKindsCollectionRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context kinds collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ContextKindsCollectionRep> executeWithHttpInfo() throws ApiException {
            return listContextKindsByProjectWithHttpInfo(projectKey);
        }

        /**
         * Execute listContextKindsByProject request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context kinds collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ContextKindsCollectionRep> _callback) throws ApiException {
            return listContextKindsByProjectAsync(projectKey, _callback);
        }
    }

    /**
     * Get context kinds
     * Get all context kinds for a given project.
     * @param projectKey The project key (required)
     * @return ListContextKindsByProjectRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Context kinds collection response </td><td>  -  </td></tr>
     </table>
     */
    public ListContextKindsByProjectRequestBuilder listContextKindsByProject(String projectKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        return new ListContextKindsByProjectRequestBuilder(projectKey);
    }
    private okhttp3.Call searchContextInstancesCall(String projectKey, String environmentKey, ContextInstanceSearch contextInstanceSearch, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = contextInstanceSearch;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/environments/{environmentKey}/context-instances/search"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (continuationToken != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("continuationToken", continuationToken));
        }

        if (sort != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sort", sort));
        }

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
        }

        if (includeTotalCount != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("includeTotalCount", includeTotalCount));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call searchContextInstancesValidateBeforeCall(String projectKey, String environmentKey, ContextInstanceSearch contextInstanceSearch, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling searchContextInstances(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling searchContextInstances(Async)");
        }

        // verify the required parameter 'contextInstanceSearch' is set
        if (contextInstanceSearch == null) {
            throw new ApiException("Missing the required parameter 'contextInstanceSearch' when calling searchContextInstances(Async)");
        }

        return searchContextInstancesCall(projectKey, environmentKey, contextInstanceSearch, limit, continuationToken, sort, filter, includeTotalCount, _callback);

    }


    private ApiResponse<ContextInstances> searchContextInstancesWithHttpInfo(String projectKey, String environmentKey, ContextInstanceSearch contextInstanceSearch, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount) throws ApiException {
        okhttp3.Call localVarCall = searchContextInstancesValidateBeforeCall(projectKey, environmentKey, contextInstanceSearch, limit, continuationToken, sort, filter, includeTotalCount, null);
        Type localVarReturnType = new TypeToken<ContextInstances>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call searchContextInstancesAsync(String projectKey, String environmentKey, ContextInstanceSearch contextInstanceSearch, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount, final ApiCallback<ContextInstances> _callback) throws ApiException {

        okhttp3.Call localVarCall = searchContextInstancesValidateBeforeCall(projectKey, environmentKey, contextInstanceSearch, limit, continuationToken, sort, filter, includeTotalCount, _callback);
        Type localVarReturnType = new TypeToken<ContextInstances>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class SearchContextInstancesRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private String filter;
        private String sort;
        private Integer limit;
        private String continuationToken;
        private Long limit;
        private String continuationToken;
        private String sort;
        private String filter;
        private Boolean includeTotalCount;

        private SearchContextInstancesRequestBuilder(String projectKey, String environmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Set filter
         * @param filter A collection of context instance filters (optional)
         * @return SearchContextInstancesRequestBuilder
         */
        public SearchContextInstancesRequestBuilder filter(String filter) {
            this.filter = filter;
            return this;
        }
        
        /**
         * Set sort
         * @param sort Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying &lt;code&gt;ts&lt;/code&gt; for this value, or descending order by specifying &lt;code&gt;-ts&lt;/code&gt;. (optional)
         * @return SearchContextInstancesRequestBuilder
         */
        public SearchContextInstancesRequestBuilder sort(String sort) {
            this.sort = sort;
            return this;
        }
        
        /**
         * Set limit
         * @param limit Specifies the maximum number of items in the collection to return (max: 50, default: 20) (optional)
         * @return SearchContextInstancesRequestBuilder
         */
        public SearchContextInstancesRequestBuilder limit(Integer limit) {
            this.limit = limit;
            return this;
        }
        
        /**
         * Set continuationToken
         * @param continuationToken Limits results to context instances with sort values after the value specified. You can use this for pagination, however, we recommend using the &lt;code&gt;next&lt;/code&gt; link instead, because this value is an obfuscated string. (optional)
         * @return SearchContextInstancesRequestBuilder
         */
        public SearchContextInstancesRequestBuilder continuationToken(String continuationToken) {
            this.continuationToken = continuationToken;
            return this;
        }
        
        /**
         * Set limit
         * @param limit Specifies the maximum number of items in the collection to return (max: 50, default: 20) (optional)
         * @return SearchContextInstancesRequestBuilder
         */
        public SearchContextInstancesRequestBuilder limit(Long limit) {
            this.limit = limit;
            return this;
        }
        
        /**
         * Set continuationToken
         * @param continuationToken Limits results to context instances with sort values after the value specified. You can use this for pagination, however, we recommend using the &#x60;next&#x60; link we provide instead. (optional)
         * @return SearchContextInstancesRequestBuilder
         */
        public SearchContextInstancesRequestBuilder continuationToken(String continuationToken) {
            this.continuationToken = continuationToken;
            return this;
        }
        
        /**
         * Set sort
         * @param sort Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying &#x60;ts&#x60; for this value, or descending order by specifying &#x60;-ts&#x60;. (optional)
         * @return SearchContextInstancesRequestBuilder
         */
        public SearchContextInstancesRequestBuilder sort(String sort) {
            this.sort = sort;
            return this;
        }
        
        /**
         * Set filter
         * @param filter A comma-separated list of context filters. This endpoint only accepts an &#x60;applicationId&#x60; filter. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). (optional)
         * @return SearchContextInstancesRequestBuilder
         */
        public SearchContextInstancesRequestBuilder filter(String filter) {
            this.filter = filter;
            return this;
        }
        
        /**
         * Set includeTotalCount
         * @param includeTotalCount Specifies whether to include or omit the total count of matching context instances. Defaults to true. (optional)
         * @return SearchContextInstancesRequestBuilder
         */
        public SearchContextInstancesRequestBuilder includeTotalCount(Boolean includeTotalCount) {
            this.includeTotalCount = includeTotalCount;
            return this;
        }
        
        /**
         * Build call for searchContextInstances
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context instances collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            ContextInstanceSearch contextInstanceSearch = buildBodyParams();
            return searchContextInstancesCall(projectKey, environmentKey, contextInstanceSearch, limit, continuationToken, sort, filter, includeTotalCount, _callback);
        }

        private ContextInstanceSearch buildBodyParams() {
            ContextInstanceSearch contextInstanceSearch = new ContextInstanceSearch();
            contextInstanceSearch.filter(this.filter);
            contextInstanceSearch.sort(this.sort);
            contextInstanceSearch.limit(this.limit);
            contextInstanceSearch.continuationToken(this.continuationToken);
            return contextInstanceSearch;
        }

        /**
         * Execute searchContextInstances request
         * @return ContextInstances
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context instances collection response </td><td>  -  </td></tr>
         </table>
         */
        public ContextInstances execute() throws ApiException {
            ContextInstanceSearch contextInstanceSearch = buildBodyParams();
            ApiResponse<ContextInstances> localVarResp = searchContextInstancesWithHttpInfo(projectKey, environmentKey, contextInstanceSearch, limit, continuationToken, sort, filter, includeTotalCount);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute searchContextInstances request with HTTP info returned
         * @return ApiResponse&lt;ContextInstances&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context instances collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ContextInstances> executeWithHttpInfo() throws ApiException {
            ContextInstanceSearch contextInstanceSearch = buildBodyParams();
            return searchContextInstancesWithHttpInfo(projectKey, environmentKey, contextInstanceSearch, limit, continuationToken, sort, filter, includeTotalCount);
        }

        /**
         * Execute searchContextInstances request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context instances collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ContextInstances> _callback) throws ApiException {
            ContextInstanceSearch contextInstanceSearch = buildBodyParams();
            return searchContextInstancesAsync(projectKey, environmentKey, contextInstanceSearch, limit, continuationToken, sort, filter, includeTotalCount, _callback);
        }
    }

    /**
     * Search for context instances
     *  Search for context instances.  You can use either the query parameters or the request body parameters. If both are provided, there is an error.  To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). To learn more about context instances, read [Understanding context instances](https://docs.launchdarkly.com/home/contexts#understanding-context-instances). 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param contextInstanceSearch  (required)
     * @return SearchContextInstancesRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Context instances collection response </td><td>  -  </td></tr>
     </table>
     */
    public SearchContextInstancesRequestBuilder searchContextInstances(String projectKey, String environmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new SearchContextInstancesRequestBuilder(projectKey, environmentKey);
    }
    private okhttp3.Call searchContextsCall(String projectKey, String environmentKey, ContextSearch contextSearch, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = contextSearch;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/environments/{environmentKey}/contexts/search"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (continuationToken != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("continuationToken", continuationToken));
        }

        if (sort != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sort", sort));
        }

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
        }

        if (includeTotalCount != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("includeTotalCount", includeTotalCount));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call searchContextsValidateBeforeCall(String projectKey, String environmentKey, ContextSearch contextSearch, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling searchContexts(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling searchContexts(Async)");
        }

        // verify the required parameter 'contextSearch' is set
        if (contextSearch == null) {
            throw new ApiException("Missing the required parameter 'contextSearch' when calling searchContexts(Async)");
        }

        return searchContextsCall(projectKey, environmentKey, contextSearch, limit, continuationToken, sort, filter, includeTotalCount, _callback);

    }


    private ApiResponse<Contexts> searchContextsWithHttpInfo(String projectKey, String environmentKey, ContextSearch contextSearch, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount) throws ApiException {
        okhttp3.Call localVarCall = searchContextsValidateBeforeCall(projectKey, environmentKey, contextSearch, limit, continuationToken, sort, filter, includeTotalCount, null);
        Type localVarReturnType = new TypeToken<Contexts>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call searchContextsAsync(String projectKey, String environmentKey, ContextSearch contextSearch, Long limit, String continuationToken, String sort, String filter, Boolean includeTotalCount, final ApiCallback<Contexts> _callback) throws ApiException {

        okhttp3.Call localVarCall = searchContextsValidateBeforeCall(projectKey, environmentKey, contextSearch, limit, continuationToken, sort, filter, includeTotalCount, _callback);
        Type localVarReturnType = new TypeToken<Contexts>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class SearchContextsRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private String filter;
        private String sort;
        private Integer limit;
        private String continuationToken;
        private Long limit;
        private String continuationToken;
        private String sort;
        private String filter;
        private Boolean includeTotalCount;

        private SearchContextsRequestBuilder(String projectKey, String environmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Set filter
         * @param filter A collection of context filters (optional)
         * @return SearchContextsRequestBuilder
         */
        public SearchContextsRequestBuilder filter(String filter) {
            this.filter = filter;
            return this;
        }
        
        /**
         * Set sort
         * @param sort Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying &lt;code&gt;ts&lt;/code&gt; for this value, or descending order by specifying &lt;code&gt;-ts&lt;/code&gt;. (optional)
         * @return SearchContextsRequestBuilder
         */
        public SearchContextsRequestBuilder sort(String sort) {
            this.sort = sort;
            return this;
        }
        
        /**
         * Set limit
         * @param limit Specifies the maximum number of items in the collection to return (max: 50, default: 20) (optional)
         * @return SearchContextsRequestBuilder
         */
        public SearchContextsRequestBuilder limit(Integer limit) {
            this.limit = limit;
            return this;
        }
        
        /**
         * Set continuationToken
         * @param continuationToken Limits results to contexts with sort values after the value specified. You can use this for pagination, however, we recommend using the &lt;code&gt;next&lt;/code&gt; link instead, because this value is an obfuscated string. (optional)
         * @return SearchContextsRequestBuilder
         */
        public SearchContextsRequestBuilder continuationToken(String continuationToken) {
            this.continuationToken = continuationToken;
            return this;
        }
        
        /**
         * Set limit
         * @param limit Specifies the maximum number of items in the collection to return (max: 50, default: 20) (optional)
         * @return SearchContextsRequestBuilder
         */
        public SearchContextsRequestBuilder limit(Long limit) {
            this.limit = limit;
            return this;
        }
        
        /**
         * Set continuationToken
         * @param continuationToken Limits results to contexts with sort values after the value specified. You can use this for pagination, however, we recommend using the &#x60;next&#x60; link we provide instead. (optional)
         * @return SearchContextsRequestBuilder
         */
        public SearchContextsRequestBuilder continuationToken(String continuationToken) {
            this.continuationToken = continuationToken;
            return this;
        }
        
        /**
         * Set sort
         * @param sort Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying &#x60;ts&#x60; for this value, or descending order by specifying &#x60;-ts&#x60;. (optional)
         * @return SearchContextsRequestBuilder
         */
        public SearchContextsRequestBuilder sort(String sort) {
            this.sort = sort;
            return this;
        }
        
        /**
         * Set filter
         * @param filter A comma-separated list of context filters. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). (optional)
         * @return SearchContextsRequestBuilder
         */
        public SearchContextsRequestBuilder filter(String filter) {
            this.filter = filter;
            return this;
        }
        
        /**
         * Set includeTotalCount
         * @param includeTotalCount Specifies whether to include or omit the total count of matching contexts. Defaults to true. (optional)
         * @return SearchContextsRequestBuilder
         */
        public SearchContextsRequestBuilder includeTotalCount(Boolean includeTotalCount) {
            this.includeTotalCount = includeTotalCount;
            return this;
        }
        
        /**
         * Build call for searchContexts
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Contexts collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            ContextSearch contextSearch = buildBodyParams();
            return searchContextsCall(projectKey, environmentKey, contextSearch, limit, continuationToken, sort, filter, includeTotalCount, _callback);
        }

        private ContextSearch buildBodyParams() {
            ContextSearch contextSearch = new ContextSearch();
            contextSearch.filter(this.filter);
            contextSearch.sort(this.sort);
            contextSearch.limit(this.limit);
            contextSearch.continuationToken(this.continuationToken);
            return contextSearch;
        }

        /**
         * Execute searchContexts request
         * @return Contexts
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Contexts collection response </td><td>  -  </td></tr>
         </table>
         */
        public Contexts execute() throws ApiException {
            ContextSearch contextSearch = buildBodyParams();
            ApiResponse<Contexts> localVarResp = searchContextsWithHttpInfo(projectKey, environmentKey, contextSearch, limit, continuationToken, sort, filter, includeTotalCount);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute searchContexts request with HTTP info returned
         * @return ApiResponse&lt;Contexts&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Contexts collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Contexts> executeWithHttpInfo() throws ApiException {
            ContextSearch contextSearch = buildBodyParams();
            return searchContextsWithHttpInfo(projectKey, environmentKey, contextSearch, limit, continuationToken, sort, filter, includeTotalCount);
        }

        /**
         * Execute searchContexts request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Contexts collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<Contexts> _callback) throws ApiException {
            ContextSearch contextSearch = buildBodyParams();
            return searchContextsAsync(projectKey, environmentKey, contextSearch, limit, continuationToken, sort, filter, includeTotalCount, _callback);
        }
    }

    /**
     * Search for contexts
     *  Search for contexts.  You can use either the query parameters or the request body parameters. If both are provided, there is an error.  To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). To learn more about contexts, read [Understanding contexts and context kinds](https://docs.launchdarkly.com/home/contexts#understanding-contexts-and-context-kinds). 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param contextSearch  (required)
     * @return SearchContextsRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Contexts collection response </td><td>  -  </td></tr>
     </table>
     */
    public SearchContextsRequestBuilder searchContexts(String projectKey, String environmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new SearchContextsRequestBuilder(projectKey, environmentKey);
    }
}
