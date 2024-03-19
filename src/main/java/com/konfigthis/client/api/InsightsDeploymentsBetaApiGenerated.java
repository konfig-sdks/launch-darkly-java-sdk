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


import com.konfigthis.client.model.DeploymentCollectionRep;
import com.konfigthis.client.model.DeploymentRep;
import com.konfigthis.client.model.PatchOperation;
import com.konfigthis.client.model.PostDeploymentEventInput;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class InsightsDeploymentsBetaApiGenerated {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public InsightsDeploymentsBetaApiGenerated() throws IllegalArgumentException {
        this(Configuration.getDefaultApiClient());
    }

    public InsightsDeploymentsBetaApiGenerated(ApiClient apiClient) throws IllegalArgumentException {
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

    private okhttp3.Call createDeploymentEventCall(PostDeploymentEventInput postDeploymentEventInput, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = postDeploymentEventInput;

        // create path and map variables
        String localVarPath = "/api/v2/engineering-insights/deployment-events";

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
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createDeploymentEventValidateBeforeCall(PostDeploymentEventInput postDeploymentEventInput, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'postDeploymentEventInput' is set
        if (postDeploymentEventInput == null) {
            throw new ApiException("Missing the required parameter 'postDeploymentEventInput' when calling createDeploymentEvent(Async)");
        }

        return createDeploymentEventCall(postDeploymentEventInput, _callback);

    }


    private ApiResponse<Void> createDeploymentEventWithHttpInfo(PostDeploymentEventInput postDeploymentEventInput) throws ApiException {
        okhttp3.Call localVarCall = createDeploymentEventValidateBeforeCall(postDeploymentEventInput, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call createDeploymentEventAsync(PostDeploymentEventInput postDeploymentEventInput, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = createDeploymentEventValidateBeforeCall(postDeploymentEventInput, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class CreateDeploymentEventRequestBuilder {
        private final String version;
        private final String projectKey;
        private final String environmentKey;
        private final String applicationKey;
        private final String eventType;
        private String applicationName;
        private String applicationKind;
        private String versionName;
        private Long eventTime;
        private Map<String, Object> eventMetadata;
        private Map<String, Object> deploymentMetadata;

        private CreateDeploymentEventRequestBuilder(String version, String projectKey, String environmentKey, String applicationKey, String eventType) {
            this.version = version;
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.applicationKey = applicationKey;
            this.eventType = eventType;
        }

        /**
         * Set applicationName
         * @param applicationName The application name. This defines how the application is displayed (optional)
         * @return CreateDeploymentEventRequestBuilder
         */
        public CreateDeploymentEventRequestBuilder applicationName(String applicationName) {
            this.applicationName = applicationName;
            return this;
        }
        
        /**
         * Set applicationKind
         * @param applicationKind The kind of application. Default: &lt;code&gt;server&lt;/code&gt; (optional)
         * @return CreateDeploymentEventRequestBuilder
         */
        public CreateDeploymentEventRequestBuilder applicationKind(String applicationKind) {
            this.applicationKind = applicationKind;
            return this;
        }
        
        /**
         * Set versionName
         * @param versionName The version name. This defines how the version is displayed (optional)
         * @return CreateDeploymentEventRequestBuilder
         */
        public CreateDeploymentEventRequestBuilder versionName(String versionName) {
            this.versionName = versionName;
            return this;
        }
        
        /**
         * Set eventTime
         * @param eventTime  (optional)
         * @return CreateDeploymentEventRequestBuilder
         */
        public CreateDeploymentEventRequestBuilder eventTime(Long eventTime) {
            this.eventTime = eventTime;
            return this;
        }
        
        /**
         * Set eventMetadata
         * @param eventMetadata A JSON object containing metadata about the event (optional)
         * @return CreateDeploymentEventRequestBuilder
         */
        public CreateDeploymentEventRequestBuilder eventMetadata(Map<String, Object> eventMetadata) {
            this.eventMetadata = eventMetadata;
            return this;
        }
        
        /**
         * Set deploymentMetadata
         * @param deploymentMetadata A JSON object containing metadata about the deployment (optional)
         * @return CreateDeploymentEventRequestBuilder
         */
        public CreateDeploymentEventRequestBuilder deploymentMetadata(Map<String, Object> deploymentMetadata) {
            this.deploymentMetadata = deploymentMetadata;
            return this;
        }
        
        /**
         * Build call for createDeploymentEvent
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            PostDeploymentEventInput postDeploymentEventInput = buildBodyParams();
            return createDeploymentEventCall(postDeploymentEventInput, _callback);
        }

        private PostDeploymentEventInput buildBodyParams() {
            PostDeploymentEventInput postDeploymentEventInput = new PostDeploymentEventInput();
            postDeploymentEventInput.version(this.version);
            postDeploymentEventInput.projectKey(this.projectKey);
            postDeploymentEventInput.environmentKey(this.environmentKey);
            postDeploymentEventInput.applicationKey(this.applicationKey);
            postDeploymentEventInput.applicationName(this.applicationName);
            if (this.applicationKind != null)
            postDeploymentEventInput.applicationKind(PostDeploymentEventInput.ApplicationKindEnum.fromValue(this.applicationKind));
            postDeploymentEventInput.versionName(this.versionName);
            if (this.eventType != null)
            postDeploymentEventInput.eventType(PostDeploymentEventInput.EventTypeEnum.fromValue(this.eventType));
            postDeploymentEventInput.eventTime(this.eventTime);
            postDeploymentEventInput.eventMetadata(this.eventMetadata);
            postDeploymentEventInput.deploymentMetadata(this.deploymentMetadata);
            return postDeploymentEventInput;
        }

        /**
         * Execute createDeploymentEvent request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
         </table>
         */
        public void execute() throws ApiException {
            PostDeploymentEventInput postDeploymentEventInput = buildBodyParams();
            createDeploymentEventWithHttpInfo(postDeploymentEventInput);
        }

        /**
         * Execute createDeploymentEvent request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            PostDeploymentEventInput postDeploymentEventInput = buildBodyParams();
            return createDeploymentEventWithHttpInfo(postDeploymentEventInput);
        }

        /**
         * Execute createDeploymentEvent request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<Void> _callback) throws ApiException {
            PostDeploymentEventInput postDeploymentEventInput = buildBodyParams();
            return createDeploymentEventAsync(postDeploymentEventInput, _callback);
        }
    }

    /**
     * Create deployment event
     * Create deployment event
     * @param postDeploymentEventInput  (required)
     * @return CreateDeploymentEventRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Created </td><td>  -  </td></tr>
     </table>
     */
    public CreateDeploymentEventRequestBuilder createDeploymentEvent(String version, String projectKey, String environmentKey, String applicationKey, String eventType) throws IllegalArgumentException {
        if (version == null) throw new IllegalArgumentException("\"version\" is required but got null");
            

        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (applicationKey == null) throw new IllegalArgumentException("\"applicationKey\" is required but got null");
            

        if (eventType == null) throw new IllegalArgumentException("\"eventType\" is required but got null");
            

        return new CreateDeploymentEventRequestBuilder(version, projectKey, environmentKey, applicationKey, eventType);
    }
    private okhttp3.Call getDeploymentByIdCall(String deploymentID, String expand, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/engineering-insights/deployments/{deploymentID}"
            .replace("{" + "deploymentID" + "}", localVarApiClient.escapeString(deploymentID.toString()));

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
    private okhttp3.Call getDeploymentByIdValidateBeforeCall(String deploymentID, String expand, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'deploymentID' is set
        if (deploymentID == null) {
            throw new ApiException("Missing the required parameter 'deploymentID' when calling getDeploymentById(Async)");
        }

        return getDeploymentByIdCall(deploymentID, expand, _callback);

    }


    private ApiResponse<DeploymentRep> getDeploymentByIdWithHttpInfo(String deploymentID, String expand) throws ApiException {
        okhttp3.Call localVarCall = getDeploymentByIdValidateBeforeCall(deploymentID, expand, null);
        Type localVarReturnType = new TypeToken<DeploymentRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getDeploymentByIdAsync(String deploymentID, String expand, final ApiCallback<DeploymentRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDeploymentByIdValidateBeforeCall(deploymentID, expand, _callback);
        Type localVarReturnType = new TypeToken<DeploymentRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetDeploymentByIdRequestBuilder {
        private final String deploymentID;
        private String expand;

        private GetDeploymentByIdRequestBuilder(String deploymentID) {
            this.deploymentID = deploymentID;
        }

        /**
         * Set expand
         * @param expand Expand properties in response. Options: &#x60;pullRequests&#x60;, &#x60;flagReferences&#x60; (optional)
         * @return GetDeploymentByIdRequestBuilder
         */
        public GetDeploymentByIdRequestBuilder expand(String expand) {
            this.expand = expand;
            return this;
        }
        
        /**
         * Build call for getDeploymentById
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Deployment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getDeploymentByIdCall(deploymentID, expand, _callback);
        }


        /**
         * Execute getDeploymentById request
         * @return DeploymentRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Deployment response </td><td>  -  </td></tr>
         </table>
         */
        public DeploymentRep execute() throws ApiException {
            ApiResponse<DeploymentRep> localVarResp = getDeploymentByIdWithHttpInfo(deploymentID, expand);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getDeploymentById request with HTTP info returned
         * @return ApiResponse&lt;DeploymentRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Deployment response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<DeploymentRep> executeWithHttpInfo() throws ApiException {
            return getDeploymentByIdWithHttpInfo(deploymentID, expand);
        }

        /**
         * Execute getDeploymentById request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Deployment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<DeploymentRep> _callback) throws ApiException {
            return getDeploymentByIdAsync(deploymentID, expand, _callback);
        }
    }

    /**
     * Get deployment
     * Get a deployment by ID.  The deployment ID is returned as part of the [List deployments](https://apidocs.launchdarkly.com) response. It is the &#x60;id&#x60; field of each element in the &#x60;items&#x60; array.  ### Expanding the deployment response  LaunchDarkly supports expanding the deployment response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;pullRequests&#x60; includes details on all of the pull requests associated with each deployment * &#x60;flagReferences&#x60; includes details on all of the references to flags in each deployment  For example, use &#x60;?expand&#x3D;pullRequests&#x60; to include the &#x60;pullRequests&#x60; field in the response. By default, this field is **not** included in the response. 
     * @param deploymentID The deployment ID (required)
     * @return GetDeploymentByIdRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Deployment response </td><td>  -  </td></tr>
     </table>
     */
    public GetDeploymentByIdRequestBuilder getDeploymentById(String deploymentID) throws IllegalArgumentException {
        if (deploymentID == null) throw new IllegalArgumentException("\"deploymentID\" is required but got null");
            

        return new GetDeploymentByIdRequestBuilder(deploymentID);
    }
    private okhttp3.Call listDeploymentsCall(String projectKey, String environmentKey, String applicationKey, Long limit, String expand, Long from, Long to, String after, String before, String kind, String status, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/engineering-insights/deployments";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (projectKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("projectKey", projectKey));
        }

        if (environmentKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("environmentKey", environmentKey));
        }

        if (applicationKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("applicationKey", applicationKey));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (expand != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("expand", expand));
        }

        if (from != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("from", from));
        }

        if (to != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("to", to));
        }

        if (after != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("after", after));
        }

        if (before != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("before", before));
        }

        if (kind != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("kind", kind));
        }

        if (status != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("status", status));
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
    private okhttp3.Call listDeploymentsValidateBeforeCall(String projectKey, String environmentKey, String applicationKey, Long limit, String expand, Long from, Long to, String after, String before, String kind, String status, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling listDeployments(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling listDeployments(Async)");
        }

        return listDeploymentsCall(projectKey, environmentKey, applicationKey, limit, expand, from, to, after, before, kind, status, _callback);

    }


    private ApiResponse<DeploymentCollectionRep> listDeploymentsWithHttpInfo(String projectKey, String environmentKey, String applicationKey, Long limit, String expand, Long from, Long to, String after, String before, String kind, String status) throws ApiException {
        okhttp3.Call localVarCall = listDeploymentsValidateBeforeCall(projectKey, environmentKey, applicationKey, limit, expand, from, to, after, before, kind, status, null);
        Type localVarReturnType = new TypeToken<DeploymentCollectionRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call listDeploymentsAsync(String projectKey, String environmentKey, String applicationKey, Long limit, String expand, Long from, Long to, String after, String before, String kind, String status, final ApiCallback<DeploymentCollectionRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = listDeploymentsValidateBeforeCall(projectKey, environmentKey, applicationKey, limit, expand, from, to, after, before, kind, status, _callback);
        Type localVarReturnType = new TypeToken<DeploymentCollectionRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ListDeploymentsRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private String applicationKey;
        private Long limit;
        private String expand;
        private Long from;
        private Long to;
        private String after;
        private String before;
        private String kind;
        private String status;

        private ListDeploymentsRequestBuilder(String projectKey, String environmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Set applicationKey
         * @param applicationKey Comma separated list of application keys (optional)
         * @return ListDeploymentsRequestBuilder
         */
        public ListDeploymentsRequestBuilder applicationKey(String applicationKey) {
            this.applicationKey = applicationKey;
            return this;
        }
        
        /**
         * Set limit
         * @param limit The number of deployments to return. Default is 20. Maximum allowed is 100. (optional)
         * @return ListDeploymentsRequestBuilder
         */
        public ListDeploymentsRequestBuilder limit(Long limit) {
            this.limit = limit;
            return this;
        }
        
        /**
         * Set expand
         * @param expand Expand properties in response. Options: &#x60;pullRequests&#x60;, &#x60;flagReferences&#x60; (optional)
         * @return ListDeploymentsRequestBuilder
         */
        public ListDeploymentsRequestBuilder expand(String expand) {
            this.expand = expand;
            return this;
        }
        
        /**
         * Set from
         * @param from Unix timestamp in milliseconds. Default value is 7 days ago. (optional)
         * @return ListDeploymentsRequestBuilder
         */
        public ListDeploymentsRequestBuilder from(Long from) {
            this.from = from;
            return this;
        }
        
        /**
         * Set to
         * @param to Unix timestamp in milliseconds. Default value is now. (optional)
         * @return ListDeploymentsRequestBuilder
         */
        public ListDeploymentsRequestBuilder to(Long to) {
            this.to = to;
            return this;
        }
        
        /**
         * Set after
         * @param after Identifier used for pagination (optional)
         * @return ListDeploymentsRequestBuilder
         */
        public ListDeploymentsRequestBuilder after(String after) {
            this.after = after;
            return this;
        }
        
        /**
         * Set before
         * @param before Identifier used for pagination (optional)
         * @return ListDeploymentsRequestBuilder
         */
        public ListDeploymentsRequestBuilder before(String before) {
            this.before = before;
            return this;
        }
        
        /**
         * Set kind
         * @param kind The deployment kind (optional)
         * @return ListDeploymentsRequestBuilder
         */
        public ListDeploymentsRequestBuilder kind(String kind) {
            this.kind = kind;
            return this;
        }
        
        /**
         * Set status
         * @param status The deployment status (optional)
         * @return ListDeploymentsRequestBuilder
         */
        public ListDeploymentsRequestBuilder status(String status) {
            this.status = status;
            return this;
        }
        
        /**
         * Build call for listDeployments
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Deployment collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return listDeploymentsCall(projectKey, environmentKey, applicationKey, limit, expand, from, to, after, before, kind, status, _callback);
        }


        /**
         * Execute listDeployments request
         * @return DeploymentCollectionRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Deployment collection response </td><td>  -  </td></tr>
         </table>
         */
        public DeploymentCollectionRep execute() throws ApiException {
            ApiResponse<DeploymentCollectionRep> localVarResp = listDeploymentsWithHttpInfo(projectKey, environmentKey, applicationKey, limit, expand, from, to, after, before, kind, status);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute listDeployments request with HTTP info returned
         * @return ApiResponse&lt;DeploymentCollectionRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Deployment collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<DeploymentCollectionRep> executeWithHttpInfo() throws ApiException {
            return listDeploymentsWithHttpInfo(projectKey, environmentKey, applicationKey, limit, expand, from, to, after, before, kind, status);
        }

        /**
         * Execute listDeployments request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Deployment collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<DeploymentCollectionRep> _callback) throws ApiException {
            return listDeploymentsAsync(projectKey, environmentKey, applicationKey, limit, expand, from, to, after, before, kind, status, _callback);
        }
    }

    /**
     * List deployments
     * Get a list of deployments  ### Expanding the deployment collection response  LaunchDarkly supports expanding the deployment collection response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;pullRequests&#x60; includes details on all of the pull requests associated with each deployment * &#x60;flagReferences&#x60; includes details on all of the references to flags in each deployment  For example, use &#x60;?expand&#x3D;pullRequests&#x60; to include the &#x60;pullRequests&#x60; field in the response. By default, this field is **not** included in the response. 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @return ListDeploymentsRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Deployment collection response </td><td>  -  </td></tr>
     </table>
     */
    public ListDeploymentsRequestBuilder listDeployments(String projectKey, String environmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new ListDeploymentsRequestBuilder(projectKey, environmentKey);
    }
    private okhttp3.Call updateDeploymentByIdCall(String deploymentID, List<PatchOperation> patchOperation, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/engineering-insights/deployments/{deploymentID}"
            .replace("{" + "deploymentID" + "}", localVarApiClient.escapeString(deploymentID.toString()));

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
    private okhttp3.Call updateDeploymentByIdValidateBeforeCall(String deploymentID, List<PatchOperation> patchOperation, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'deploymentID' is set
        if (deploymentID == null) {
            throw new ApiException("Missing the required parameter 'deploymentID' when calling updateDeploymentById(Async)");
        }

        // verify the required parameter 'patchOperation' is set
        if (patchOperation == null) {
            throw new ApiException("Missing the required parameter 'patchOperation' when calling updateDeploymentById(Async)");
        }

        return updateDeploymentByIdCall(deploymentID, patchOperation, _callback);

    }


    private ApiResponse<DeploymentRep> updateDeploymentByIdWithHttpInfo(String deploymentID, List<PatchOperation> patchOperation) throws ApiException {
        okhttp3.Call localVarCall = updateDeploymentByIdValidateBeforeCall(deploymentID, patchOperation, null);
        Type localVarReturnType = new TypeToken<DeploymentRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call updateDeploymentByIdAsync(String deploymentID, List<PatchOperation> patchOperation, final ApiCallback<DeploymentRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDeploymentByIdValidateBeforeCall(deploymentID, patchOperation, _callback);
        Type localVarReturnType = new TypeToken<DeploymentRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class UpdateDeploymentByIdRequestBuilder {
        private final String deploymentID;
        private List<PatchOperation> patchOperation;

        private UpdateDeploymentByIdRequestBuilder(String deploymentID) {
            this.deploymentID = deploymentID;
        }

        /**
         * Set patchOperation
         * @param patchOperation  (optional)
         * @return UpdateDeploymentByIdRequestBuilder
         */
        public UpdateDeploymentByIdRequestBuilder patchOperation(List<PatchOperation> patchOperation) {
            this.patchOperation = patchOperation;
            return this;
        }

        /**
         * Build call for updateDeploymentById
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Deployment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            return updateDeploymentByIdCall(deploymentID, patchOperation, _callback);
        }

        private List<PatchOperation> buildBodyParams() {
            return this.patchOperation;
        }

        /**
         * Execute updateDeploymentById request
         * @return DeploymentRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Deployment response </td><td>  -  </td></tr>
         </table>
         */
        public DeploymentRep execute() throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            ApiResponse<DeploymentRep> localVarResp = updateDeploymentByIdWithHttpInfo(deploymentID, patchOperation);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute updateDeploymentById request with HTTP info returned
         * @return ApiResponse&lt;DeploymentRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Deployment response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<DeploymentRep> executeWithHttpInfo() throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            return updateDeploymentByIdWithHttpInfo(deploymentID, patchOperation);
        }

        /**
         * Execute updateDeploymentById request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Deployment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<DeploymentRep> _callback) throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            return updateDeploymentByIdAsync(deploymentID, patchOperation, _callback);
        }
    }

    /**
     * Update deployment
     * Update a deployment by ID. Updating a deployment uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).&lt;br/&gt;&lt;br/&gt;The deployment ID is returned as part of the [List deployments](https://apidocs.launchdarkly.com) response. It is the &#x60;id&#x60; field of each element in the &#x60;items&#x60; array.
     * @param deploymentID The deployment ID (required)
     * @param patchOperation  (required)
     * @return UpdateDeploymentByIdRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Deployment response </td><td>  -  </td></tr>
     </table>
     */
    public UpdateDeploymentByIdRequestBuilder updateDeploymentById(String deploymentID) throws IllegalArgumentException {
        if (deploymentID == null) throw new IllegalArgumentException("\"deploymentID\" is required but got null");
            

        return new UpdateDeploymentByIdRequestBuilder(deploymentID);
    }
}
