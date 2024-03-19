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


import com.konfigthis.client.model.ApplicationCollectionRep;
import com.konfigthis.client.model.ApplicationRep;
import com.konfigthis.client.model.ApplicationVersionRep;
import com.konfigthis.client.model.ApplicationVersionsCollectionRep;
import com.konfigthis.client.model.PatchOperation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class ApplicationsBetaApiGenerated {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public ApplicationsBetaApiGenerated() throws IllegalArgumentException {
        this(Configuration.getDefaultApiClient());
    }

    public ApplicationsBetaApiGenerated(ApiClient apiClient) throws IllegalArgumentException {
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

    private okhttp3.Call getApplicationByKeyCall(String applicationKey, String expand, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/applications/{applicationKey}"
            .replace("{" + "applicationKey" + "}", localVarApiClient.escapeString(applicationKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (expand != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("expand", expand));
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
    private okhttp3.Call getApplicationByKeyValidateBeforeCall(String applicationKey, String expand, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'applicationKey' is set
        if (applicationKey == null) {
            throw new ApiException("Missing the required parameter 'applicationKey' when calling getApplicationByKey(Async)");
        }

        return getApplicationByKeyCall(applicationKey, expand, _callback);

    }


    private ApiResponse<ApplicationRep> getApplicationByKeyWithHttpInfo(String applicationKey, String expand) throws ApiException {
        okhttp3.Call localVarCall = getApplicationByKeyValidateBeforeCall(applicationKey, expand, null);
        Type localVarReturnType = new TypeToken<ApplicationRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getApplicationByKeyAsync(String applicationKey, String expand, final ApiCallback<ApplicationRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getApplicationByKeyValidateBeforeCall(applicationKey, expand, _callback);
        Type localVarReturnType = new TypeToken<ApplicationRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetApplicationByKeyRequestBuilder {
        private final String applicationKey;
        private String expand;

        private GetApplicationByKeyRequestBuilder(String applicationKey) {
            this.applicationKey = applicationKey;
        }

        /**
         * Set expand
         * @param expand A comma-separated list of properties that can reveal additional information in the response. Options: &#x60;flags&#x60;. (optional)
         * @return GetApplicationByKeyRequestBuilder
         */
        public GetApplicationByKeyRequestBuilder expand(String expand) {
            this.expand = expand;
            return this;
        }
        
        /**
         * Build call for getApplicationByKey
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getApplicationByKeyCall(applicationKey, expand, _callback);
        }


        /**
         * Execute getApplicationByKey request
         * @return ApplicationRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application response </td><td>  -  </td></tr>
         </table>
         */
        public ApplicationRep execute() throws ApiException {
            ApiResponse<ApplicationRep> localVarResp = getApplicationByKeyWithHttpInfo(applicationKey, expand);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getApplicationByKey request with HTTP info returned
         * @return ApiResponse&lt;ApplicationRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ApplicationRep> executeWithHttpInfo() throws ApiException {
            return getApplicationByKeyWithHttpInfo(applicationKey, expand);
        }

        /**
         * Execute getApplicationByKey request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ApplicationRep> _callback) throws ApiException {
            return getApplicationByKeyAsync(applicationKey, expand, _callback);
        }
    }

    /**
     * Get application by key
     *  Retrieve an application by the application key.  ### Expanding the application response  LaunchDarkly supports expanding the \&quot;Get application\&quot; response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;flags&#x60; includes details on the flags that have been evaluated by the application  For example, use &#x60;?expand&#x3D;flags&#x60; to include the &#x60;flags&#x60; field in the response. By default, this field is **not** included in the response. 
     * @param applicationKey The application key (required)
     * @return GetApplicationByKeyRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Application response </td><td>  -  </td></tr>
     </table>
     */
    public GetApplicationByKeyRequestBuilder getApplicationByKey(String applicationKey) throws IllegalArgumentException {
        if (applicationKey == null) throw new IllegalArgumentException("\"applicationKey\" is required but got null");
            

        return new GetApplicationByKeyRequestBuilder(applicationKey);
    }
    private okhttp3.Call getApplicationVersionsCall(String applicationKey, String filter, Long limit, Long offset, String sort, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/applications/{applicationKey}/versions"
            .replace("{" + "applicationKey" + "}", localVarApiClient.escapeString(applicationKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        if (sort != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sort", sort));
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
    private okhttp3.Call getApplicationVersionsValidateBeforeCall(String applicationKey, String filter, Long limit, Long offset, String sort, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'applicationKey' is set
        if (applicationKey == null) {
            throw new ApiException("Missing the required parameter 'applicationKey' when calling getApplicationVersions(Async)");
        }

        return getApplicationVersionsCall(applicationKey, filter, limit, offset, sort, _callback);

    }


    private ApiResponse<ApplicationVersionsCollectionRep> getApplicationVersionsWithHttpInfo(String applicationKey, String filter, Long limit, Long offset, String sort) throws ApiException {
        okhttp3.Call localVarCall = getApplicationVersionsValidateBeforeCall(applicationKey, filter, limit, offset, sort, null);
        Type localVarReturnType = new TypeToken<ApplicationVersionsCollectionRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getApplicationVersionsAsync(String applicationKey, String filter, Long limit, Long offset, String sort, final ApiCallback<ApplicationVersionsCollectionRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getApplicationVersionsValidateBeforeCall(applicationKey, filter, limit, offset, sort, _callback);
        Type localVarReturnType = new TypeToken<ApplicationVersionsCollectionRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetApplicationVersionsRequestBuilder {
        private final String applicationKey;
        private String filter;
        private Long limit;
        private Long offset;
        private String sort;

        private GetApplicationVersionsRequestBuilder(String applicationKey) {
            this.applicationKey = applicationKey;
        }

        /**
         * Set filter
         * @param filter Accepts filter by &#x60;key&#x60;, &#x60;name&#x60;, &#x60;supported&#x60;, and &#x60;autoAdded&#x60;. Example: &#x60;filter&#x3D;key equals &#39;test-key&#39;&#x60;. To learn more about the filter syntax, read [Filtering applications and application versions](https://apidocs.launchdarkly.com)#filtering-contexts-and-context-instances). (optional)
         * @return GetApplicationVersionsRequestBuilder
         */
        public GetApplicationVersionsRequestBuilder filter(String filter) {
            this.filter = filter;
            return this;
        }
        
        /**
         * Set limit
         * @param limit The number of versions to return. Defaults to 50. (optional)
         * @return GetApplicationVersionsRequestBuilder
         */
        public GetApplicationVersionsRequestBuilder limit(Long limit) {
            this.limit = limit;
            return this;
        }
        
        /**
         * Set offset
         * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
         * @return GetApplicationVersionsRequestBuilder
         */
        public GetApplicationVersionsRequestBuilder offset(Long offset) {
            this.offset = offset;
            return this;
        }
        
        /**
         * Set sort
         * @param sort Accepts sorting order and fields. Fields can be comma separated. Possible fields are &#x60;creationDate&#x60;, &#x60;name&#x60;. Examples: &#x60;sort&#x3D;name&#x60; sort by names ascending, &#x60;sort&#x3D;-name,creationDate&#x60; sort by names descending and creationDate ascending. (optional)
         * @return GetApplicationVersionsRequestBuilder
         */
        public GetApplicationVersionsRequestBuilder sort(String sort) {
            this.sort = sort;
            return this;
        }
        
        /**
         * Build call for getApplicationVersions
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application versions response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getApplicationVersionsCall(applicationKey, filter, limit, offset, sort, _callback);
        }


        /**
         * Execute getApplicationVersions request
         * @return ApplicationVersionsCollectionRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application versions response </td><td>  -  </td></tr>
         </table>
         */
        public ApplicationVersionsCollectionRep execute() throws ApiException {
            ApiResponse<ApplicationVersionsCollectionRep> localVarResp = getApplicationVersionsWithHttpInfo(applicationKey, filter, limit, offset, sort);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getApplicationVersions request with HTTP info returned
         * @return ApiResponse&lt;ApplicationVersionsCollectionRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application versions response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ApplicationVersionsCollectionRep> executeWithHttpInfo() throws ApiException {
            return getApplicationVersionsWithHttpInfo(applicationKey, filter, limit, offset, sort);
        }

        /**
         * Execute getApplicationVersions request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application versions response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ApplicationVersionsCollectionRep> _callback) throws ApiException {
            return getApplicationVersionsAsync(applicationKey, filter, limit, offset, sort, _callback);
        }
    }

    /**
     * Get application versions by application key
     * Get a list of versions for a specific application in an account.
     * @param applicationKey The application key (required)
     * @return GetApplicationVersionsRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Application versions response </td><td>  -  </td></tr>
     </table>
     */
    public GetApplicationVersionsRequestBuilder getApplicationVersions(String applicationKey) throws IllegalArgumentException {
        if (applicationKey == null) throw new IllegalArgumentException("\"applicationKey\" is required but got null");
            

        return new GetApplicationVersionsRequestBuilder(applicationKey);
    }
    private okhttp3.Call listApplicationsCall(String filter, Long limit, Long offset, String sort, String expand, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/applications";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        if (sort != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sort", sort));
        }

        if (expand != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("expand", expand));
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
    private okhttp3.Call listApplicationsValidateBeforeCall(String filter, Long limit, Long offset, String sort, String expand, final ApiCallback _callback) throws ApiException {
        return listApplicationsCall(filter, limit, offset, sort, expand, _callback);

    }


    private ApiResponse<ApplicationCollectionRep> listApplicationsWithHttpInfo(String filter, Long limit, Long offset, String sort, String expand) throws ApiException {
        okhttp3.Call localVarCall = listApplicationsValidateBeforeCall(filter, limit, offset, sort, expand, null);
        Type localVarReturnType = new TypeToken<ApplicationCollectionRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call listApplicationsAsync(String filter, Long limit, Long offset, String sort, String expand, final ApiCallback<ApplicationCollectionRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = listApplicationsValidateBeforeCall(filter, limit, offset, sort, expand, _callback);
        Type localVarReturnType = new TypeToken<ApplicationCollectionRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ListApplicationsRequestBuilder {
        private String filter;
        private Long limit;
        private Long offset;
        private String sort;
        private String expand;

        private ListApplicationsRequestBuilder() {
        }

        /**
         * Set filter
         * @param filter Accepts filter by &#x60;key&#x60;, &#x60;name&#x60;, &#x60;kind&#x60;, and &#x60;autoAdded&#x60;. Example: &#x60;filter&#x3D;kind anyOf [&#39;mobile&#39;, &#39;server&#39;],key equals &#39;test-key&#39;&#x60;. To learn more about the filter syntax, read [Filtering applications and application versions](https://apidocs.launchdarkly.com)#filtering-contexts-and-context-instances). (optional)
         * @return ListApplicationsRequestBuilder
         */
        public ListApplicationsRequestBuilder filter(String filter) {
            this.filter = filter;
            return this;
        }
        
        /**
         * Set limit
         * @param limit The number of applications to return. Defaults to 10. (optional)
         * @return ListApplicationsRequestBuilder
         */
        public ListApplicationsRequestBuilder limit(Long limit) {
            this.limit = limit;
            return this;
        }
        
        /**
         * Set offset
         * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
         * @return ListApplicationsRequestBuilder
         */
        public ListApplicationsRequestBuilder offset(Long offset) {
            this.offset = offset;
            return this;
        }
        
        /**
         * Set sort
         * @param sort Accepts sorting order and fields. Fields can be comma separated. Possible fields are &#x60;creationDate&#x60;, &#x60;name&#x60;. Examples: &#x60;sort&#x3D;name&#x60; sort by names ascending, &#x60;sort&#x3D;-name,creationDate&#x60; sort by names descending and creationDate ascending. (optional)
         * @return ListApplicationsRequestBuilder
         */
        public ListApplicationsRequestBuilder sort(String sort) {
            this.sort = sort;
            return this;
        }
        
        /**
         * Set expand
         * @param expand A comma-separated list of properties that can reveal additional information in the response. Options: &#x60;flags&#x60;. (optional)
         * @return ListApplicationsRequestBuilder
         */
        public ListApplicationsRequestBuilder expand(String expand) {
            this.expand = expand;
            return this;
        }
        
        /**
         * Build call for listApplications
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Applications response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return listApplicationsCall(filter, limit, offset, sort, expand, _callback);
        }


        /**
         * Execute listApplications request
         * @return ApplicationCollectionRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Applications response </td><td>  -  </td></tr>
         </table>
         */
        public ApplicationCollectionRep execute() throws ApiException {
            ApiResponse<ApplicationCollectionRep> localVarResp = listApplicationsWithHttpInfo(filter, limit, offset, sort, expand);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute listApplications request with HTTP info returned
         * @return ApiResponse&lt;ApplicationCollectionRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Applications response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ApplicationCollectionRep> executeWithHttpInfo() throws ApiException {
            return listApplicationsWithHttpInfo(filter, limit, offset, sort, expand);
        }

        /**
         * Execute listApplications request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Applications response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ApplicationCollectionRep> _callback) throws ApiException {
            return listApplicationsAsync(filter, limit, offset, sort, expand, _callback);
        }
    }

    /**
     * Get applications
     *  Get a list of applications.  ### Expanding the applications response  LaunchDarkly supports expanding the \&quot;Get applications\&quot; response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;flags&#x60; includes details on the flags that have been evaluated by the application  For example, use &#x60;?expand&#x3D;flags&#x60; to include the &#x60;flags&#x60; field in the response. By default, this field is **not** included in the response. 
     * @return ListApplicationsRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Applications response </td><td>  -  </td></tr>
     </table>
     */
    public ListApplicationsRequestBuilder listApplications() throws IllegalArgumentException {
        return new ListApplicationsRequestBuilder();
    }
    private okhttp3.Call removeApplicationCall(String applicationKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/applications/{applicationKey}"
            .replace("{" + "applicationKey" + "}", localVarApiClient.escapeString(applicationKey.toString()));

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
    private okhttp3.Call removeApplicationValidateBeforeCall(String applicationKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'applicationKey' is set
        if (applicationKey == null) {
            throw new ApiException("Missing the required parameter 'applicationKey' when calling removeApplication(Async)");
        }

        return removeApplicationCall(applicationKey, _callback);

    }


    private ApiResponse<Void> removeApplicationWithHttpInfo(String applicationKey) throws ApiException {
        okhttp3.Call localVarCall = removeApplicationValidateBeforeCall(applicationKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call removeApplicationAsync(String applicationKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = removeApplicationValidateBeforeCall(applicationKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class RemoveApplicationRequestBuilder {
        private final String applicationKey;

        private RemoveApplicationRequestBuilder(String applicationKey) {
            this.applicationKey = applicationKey;
        }

        /**
         * Build call for removeApplication
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
            return removeApplicationCall(applicationKey, _callback);
        }


        /**
         * Execute removeApplication request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public void execute() throws ApiException {
            removeApplicationWithHttpInfo(applicationKey);
        }

        /**
         * Execute removeApplication request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            return removeApplicationWithHttpInfo(applicationKey);
        }

        /**
         * Execute removeApplication request (asynchronously)
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
            return removeApplicationAsync(applicationKey, _callback);
        }
    }

    /**
     * Delete application
     * Delete an application.
     * @param applicationKey The application key (required)
     * @return RemoveApplicationRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
     </table>
     */
    public RemoveApplicationRequestBuilder removeApplication(String applicationKey) throws IllegalArgumentException {
        if (applicationKey == null) throw new IllegalArgumentException("\"applicationKey\" is required but got null");
            

        return new RemoveApplicationRequestBuilder(applicationKey);
    }
    private okhttp3.Call removeVersionCall(String applicationKey, String versionKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/applications/{applicationKey}/versions/{versionKey}"
            .replace("{" + "applicationKey" + "}", localVarApiClient.escapeString(applicationKey.toString()))
            .replace("{" + "versionKey" + "}", localVarApiClient.escapeString(versionKey.toString()));

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
    private okhttp3.Call removeVersionValidateBeforeCall(String applicationKey, String versionKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'applicationKey' is set
        if (applicationKey == null) {
            throw new ApiException("Missing the required parameter 'applicationKey' when calling removeVersion(Async)");
        }

        // verify the required parameter 'versionKey' is set
        if (versionKey == null) {
            throw new ApiException("Missing the required parameter 'versionKey' when calling removeVersion(Async)");
        }

        return removeVersionCall(applicationKey, versionKey, _callback);

    }


    private ApiResponse<Void> removeVersionWithHttpInfo(String applicationKey, String versionKey) throws ApiException {
        okhttp3.Call localVarCall = removeVersionValidateBeforeCall(applicationKey, versionKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call removeVersionAsync(String applicationKey, String versionKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = removeVersionValidateBeforeCall(applicationKey, versionKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class RemoveVersionRequestBuilder {
        private final String applicationKey;
        private final String versionKey;

        private RemoveVersionRequestBuilder(String applicationKey, String versionKey) {
            this.applicationKey = applicationKey;
            this.versionKey = versionKey;
        }

        /**
         * Build call for removeVersion
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
            return removeVersionCall(applicationKey, versionKey, _callback);
        }


        /**
         * Execute removeVersion request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public void execute() throws ApiException {
            removeVersionWithHttpInfo(applicationKey, versionKey);
        }

        /**
         * Execute removeVersion request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            return removeVersionWithHttpInfo(applicationKey, versionKey);
        }

        /**
         * Execute removeVersion request (asynchronously)
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
            return removeVersionAsync(applicationKey, versionKey, _callback);
        }
    }

    /**
     * Delete application version
     * Delete an application version.
     * @param applicationKey The application key (required)
     * @param versionKey The application version key (required)
     * @return RemoveVersionRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
     </table>
     */
    public RemoveVersionRequestBuilder removeVersion(String applicationKey, String versionKey) throws IllegalArgumentException {
        if (applicationKey == null) throw new IllegalArgumentException("\"applicationKey\" is required but got null");
            

        if (versionKey == null) throw new IllegalArgumentException("\"versionKey\" is required but got null");
            

        return new RemoveVersionRequestBuilder(applicationKey, versionKey);
    }
    private okhttp3.Call updateApplicationPatchCall(String applicationKey, List<PatchOperation> patchOperation, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = patchOperation;

        // create path and map variables
        String localVarPath = "/api/v2/applications/{applicationKey}"
            .replace("{" + "applicationKey" + "}", localVarApiClient.escapeString(applicationKey.toString()));

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
        return localVarApiClient.buildCall(basePath, localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateApplicationPatchValidateBeforeCall(String applicationKey, List<PatchOperation> patchOperation, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'applicationKey' is set
        if (applicationKey == null) {
            throw new ApiException("Missing the required parameter 'applicationKey' when calling updateApplicationPatch(Async)");
        }

        // verify the required parameter 'patchOperation' is set
        if (patchOperation == null) {
            throw new ApiException("Missing the required parameter 'patchOperation' when calling updateApplicationPatch(Async)");
        }

        return updateApplicationPatchCall(applicationKey, patchOperation, _callback);

    }


    private ApiResponse<ApplicationRep> updateApplicationPatchWithHttpInfo(String applicationKey, List<PatchOperation> patchOperation) throws ApiException {
        okhttp3.Call localVarCall = updateApplicationPatchValidateBeforeCall(applicationKey, patchOperation, null);
        Type localVarReturnType = new TypeToken<ApplicationRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call updateApplicationPatchAsync(String applicationKey, List<PatchOperation> patchOperation, final ApiCallback<ApplicationRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateApplicationPatchValidateBeforeCall(applicationKey, patchOperation, _callback);
        Type localVarReturnType = new TypeToken<ApplicationRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class UpdateApplicationPatchRequestBuilder {
        private final String applicationKey;
        private List<PatchOperation> patchOperation;

        private UpdateApplicationPatchRequestBuilder(String applicationKey) {
            this.applicationKey = applicationKey;
        }

        /**
         * Set patchOperation
         * @param patchOperation  (optional)
         * @return UpdateApplicationPatchRequestBuilder
         */
        public UpdateApplicationPatchRequestBuilder patchOperation(List<PatchOperation> patchOperation) {
            this.patchOperation = patchOperation;
            return this;
        }

        /**
         * Build call for updateApplicationPatch
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            return updateApplicationPatchCall(applicationKey, patchOperation, _callback);
        }

        private List<PatchOperation> buildBodyParams() {
            return this.patchOperation;
        }

        /**
         * Execute updateApplicationPatch request
         * @return ApplicationRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application response </td><td>  -  </td></tr>
         </table>
         */
        public ApplicationRep execute() throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            ApiResponse<ApplicationRep> localVarResp = updateApplicationPatchWithHttpInfo(applicationKey, patchOperation);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute updateApplicationPatch request with HTTP info returned
         * @return ApiResponse&lt;ApplicationRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ApplicationRep> executeWithHttpInfo() throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            return updateApplicationPatchWithHttpInfo(applicationKey, patchOperation);
        }

        /**
         * Execute updateApplicationPatch request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ApplicationRep> _callback) throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            return updateApplicationPatchAsync(applicationKey, patchOperation, _callback);
        }
    }

    /**
     * Update application
     * Update an application. You can update the &#x60;description&#x60; and &#x60;kind&#x60; fields. Requires a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes to the application. To learn more, read [Updates](https://apidocs.launchdarkly.com).
     * @param applicationKey The application key (required)
     * @param patchOperation  (required)
     * @return UpdateApplicationPatchRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Application response </td><td>  -  </td></tr>
     </table>
     */
    public UpdateApplicationPatchRequestBuilder updateApplicationPatch(String applicationKey) throws IllegalArgumentException {
        if (applicationKey == null) throw new IllegalArgumentException("\"applicationKey\" is required but got null");
            

        return new UpdateApplicationPatchRequestBuilder(applicationKey);
    }
    private okhttp3.Call updateVersionPatchCall(String applicationKey, String versionKey, List<PatchOperation> patchOperation, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = patchOperation;

        // create path and map variables
        String localVarPath = "/api/v2/applications/{applicationKey}/versions/{versionKey}"
            .replace("{" + "applicationKey" + "}", localVarApiClient.escapeString(applicationKey.toString()))
            .replace("{" + "versionKey" + "}", localVarApiClient.escapeString(versionKey.toString()));

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
        return localVarApiClient.buildCall(basePath, localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateVersionPatchValidateBeforeCall(String applicationKey, String versionKey, List<PatchOperation> patchOperation, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'applicationKey' is set
        if (applicationKey == null) {
            throw new ApiException("Missing the required parameter 'applicationKey' when calling updateVersionPatch(Async)");
        }

        // verify the required parameter 'versionKey' is set
        if (versionKey == null) {
            throw new ApiException("Missing the required parameter 'versionKey' when calling updateVersionPatch(Async)");
        }

        // verify the required parameter 'patchOperation' is set
        if (patchOperation == null) {
            throw new ApiException("Missing the required parameter 'patchOperation' when calling updateVersionPatch(Async)");
        }

        return updateVersionPatchCall(applicationKey, versionKey, patchOperation, _callback);

    }


    private ApiResponse<ApplicationVersionRep> updateVersionPatchWithHttpInfo(String applicationKey, String versionKey, List<PatchOperation> patchOperation) throws ApiException {
        okhttp3.Call localVarCall = updateVersionPatchValidateBeforeCall(applicationKey, versionKey, patchOperation, null);
        Type localVarReturnType = new TypeToken<ApplicationVersionRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call updateVersionPatchAsync(String applicationKey, String versionKey, List<PatchOperation> patchOperation, final ApiCallback<ApplicationVersionRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateVersionPatchValidateBeforeCall(applicationKey, versionKey, patchOperation, _callback);
        Type localVarReturnType = new TypeToken<ApplicationVersionRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class UpdateVersionPatchRequestBuilder {
        private final String applicationKey;
        private final String versionKey;
        private List<PatchOperation> patchOperation;

        private UpdateVersionPatchRequestBuilder(String applicationKey, String versionKey) {
            this.applicationKey = applicationKey;
            this.versionKey = versionKey;
        }

        /**
         * Set patchOperation
         * @param patchOperation  (optional)
         * @return UpdateVersionPatchRequestBuilder
         */
        public UpdateVersionPatchRequestBuilder patchOperation(List<PatchOperation> patchOperation) {
            this.patchOperation = patchOperation;
            return this;
        }

        /**
         * Build call for updateVersionPatch
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application version response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            return updateVersionPatchCall(applicationKey, versionKey, patchOperation, _callback);
        }

        private List<PatchOperation> buildBodyParams() {
            return this.patchOperation;
        }

        /**
         * Execute updateVersionPatch request
         * @return ApplicationVersionRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application version response </td><td>  -  </td></tr>
         </table>
         */
        public ApplicationVersionRep execute() throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            ApiResponse<ApplicationVersionRep> localVarResp = updateVersionPatchWithHttpInfo(applicationKey, versionKey, patchOperation);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute updateVersionPatch request with HTTP info returned
         * @return ApiResponse&lt;ApplicationVersionRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application version response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ApplicationVersionRep> executeWithHttpInfo() throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            return updateVersionPatchWithHttpInfo(applicationKey, versionKey, patchOperation);
        }

        /**
         * Execute updateVersionPatch request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Application version response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ApplicationVersionRep> _callback) throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            return updateVersionPatchAsync(applicationKey, versionKey, patchOperation, _callback);
        }
    }

    /**
     * Update application version
     * Update an application version. You can update the &#x60;supported&#x60; field. Requires a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes to the application version. To learn more, read [Updates](https://apidocs.launchdarkly.com).
     * @param applicationKey The application key (required)
     * @param versionKey The application version key (required)
     * @param patchOperation  (required)
     * @return UpdateVersionPatchRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Application version response </td><td>  -  </td></tr>
     </table>
     */
    public UpdateVersionPatchRequestBuilder updateVersionPatch(String applicationKey, String versionKey) throws IllegalArgumentException {
        if (applicationKey == null) throw new IllegalArgumentException("\"applicationKey\" is required but got null");
            

        if (versionKey == null) throw new IllegalArgumentException("\"versionKey\" is required but got null");
            

        return new UpdateVersionPatchRequestBuilder(applicationKey, versionKey);
    }
}
