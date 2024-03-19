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


import com.konfigthis.client.model.Environment;
import com.konfigthis.client.model.EnvironmentPost;
import com.konfigthis.client.model.Environments;
import com.konfigthis.client.model.PatchOperation;
import com.konfigthis.client.model.SourceEnv;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class EnvironmentsApiGenerated {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public EnvironmentsApiGenerated() throws IllegalArgumentException {
        this(Configuration.getDefaultApiClient());
    }

    public EnvironmentsApiGenerated(ApiClient apiClient) throws IllegalArgumentException {
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

    private okhttp3.Call createNewEnvironmentCall(String projectKey, EnvironmentPost environmentPost, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = environmentPost;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/environments"
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
    private okhttp3.Call createNewEnvironmentValidateBeforeCall(String projectKey, EnvironmentPost environmentPost, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling createNewEnvironment(Async)");
        }

        // verify the required parameter 'environmentPost' is set
        if (environmentPost == null) {
            throw new ApiException("Missing the required parameter 'environmentPost' when calling createNewEnvironment(Async)");
        }

        return createNewEnvironmentCall(projectKey, environmentPost, _callback);

    }


    private ApiResponse<Environment> createNewEnvironmentWithHttpInfo(String projectKey, EnvironmentPost environmentPost) throws ApiException {
        okhttp3.Call localVarCall = createNewEnvironmentValidateBeforeCall(projectKey, environmentPost, null);
        Type localVarReturnType = new TypeToken<Environment>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call createNewEnvironmentAsync(String projectKey, EnvironmentPost environmentPost, final ApiCallback<Environment> _callback) throws ApiException {

        okhttp3.Call localVarCall = createNewEnvironmentValidateBeforeCall(projectKey, environmentPost, _callback);
        Type localVarReturnType = new TypeToken<Environment>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class CreateNewEnvironmentRequestBuilder {
        private final String name;
        private final String key;
        private final String color;
        private final String projectKey;
        private List<String> tags;
        private Integer defaultTtl;
        private Boolean secureMode;
        private Boolean defaultTrackEvents;
        private Boolean confirmChanges;
        private Boolean requireComments;
        private SourceEnv source;
        private Boolean critical;

        private CreateNewEnvironmentRequestBuilder(String name, String key, String color, String projectKey) {
            this.name = name;
            this.key = key;
            this.color = color;
            this.projectKey = projectKey;
        }

        /**
         * Set tags
         * @param tags Tags to apply to the new environment (optional)
         * @return CreateNewEnvironmentRequestBuilder
         */
        public CreateNewEnvironmentRequestBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }
        
        /**
         * Set defaultTtl
         * @param defaultTtl The default time (in minutes) that the PHP SDK can cache feature flag rules locally (optional)
         * @return CreateNewEnvironmentRequestBuilder
         */
        public CreateNewEnvironmentRequestBuilder defaultTtl(Integer defaultTtl) {
            this.defaultTtl = defaultTtl;
            return this;
        }
        
        /**
         * Set secureMode
         * @param secureMode Ensures that one end user of the client-side SDK cannot inspect the variations for another end user (optional)
         * @return CreateNewEnvironmentRequestBuilder
         */
        public CreateNewEnvironmentRequestBuilder secureMode(Boolean secureMode) {
            this.secureMode = secureMode;
            return this;
        }
        
        /**
         * Set defaultTrackEvents
         * @param defaultTrackEvents Enables tracking detailed information for new flags by default (optional)
         * @return CreateNewEnvironmentRequestBuilder
         */
        public CreateNewEnvironmentRequestBuilder defaultTrackEvents(Boolean defaultTrackEvents) {
            this.defaultTrackEvents = defaultTrackEvents;
            return this;
        }
        
        /**
         * Set confirmChanges
         * @param confirmChanges Requires confirmation for all flag and segment changes via the UI in this environment (optional)
         * @return CreateNewEnvironmentRequestBuilder
         */
        public CreateNewEnvironmentRequestBuilder confirmChanges(Boolean confirmChanges) {
            this.confirmChanges = confirmChanges;
            return this;
        }
        
        /**
         * Set requireComments
         * @param requireComments Requires comments for all flag and segment changes via the UI in this environment (optional)
         * @return CreateNewEnvironmentRequestBuilder
         */
        public CreateNewEnvironmentRequestBuilder requireComments(Boolean requireComments) {
            this.requireComments = requireComments;
            return this;
        }
        
        /**
         * Set source
         * @param source  (optional)
         * @return CreateNewEnvironmentRequestBuilder
         */
        public CreateNewEnvironmentRequestBuilder source(SourceEnv source) {
            this.source = source;
            return this;
        }
        
        /**
         * Set critical
         * @param critical Whether the environment is critical (optional)
         * @return CreateNewEnvironmentRequestBuilder
         */
        public CreateNewEnvironmentRequestBuilder critical(Boolean critical) {
            this.critical = critical;
            return this;
        }
        
        /**
         * Build call for createNewEnvironment
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            EnvironmentPost environmentPost = buildBodyParams();
            return createNewEnvironmentCall(projectKey, environmentPost, _callback);
        }

        private EnvironmentPost buildBodyParams() {
            EnvironmentPost environmentPost = new EnvironmentPost();
            environmentPost.tags(this.tags);
            environmentPost.name(this.name);
            environmentPost.key(this.key);
            environmentPost.color(this.color);
            environmentPost.defaultTtl(this.defaultTtl);
            environmentPost.secureMode(this.secureMode);
            environmentPost.defaultTrackEvents(this.defaultTrackEvents);
            environmentPost.confirmChanges(this.confirmChanges);
            environmentPost.requireComments(this.requireComments);
            environmentPost.source(this.source);
            environmentPost.critical(this.critical);
            return environmentPost;
        }

        /**
         * Execute createNewEnvironment request
         * @return Environment
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public Environment execute() throws ApiException {
            EnvironmentPost environmentPost = buildBodyParams();
            ApiResponse<Environment> localVarResp = createNewEnvironmentWithHttpInfo(projectKey, environmentPost);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute createNewEnvironment request with HTTP info returned
         * @return ApiResponse&lt;Environment&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Environment> executeWithHttpInfo() throws ApiException {
            EnvironmentPost environmentPost = buildBodyParams();
            return createNewEnvironmentWithHttpInfo(projectKey, environmentPost);
        }

        /**
         * Execute createNewEnvironment request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<Environment> _callback) throws ApiException {
            EnvironmentPost environmentPost = buildBodyParams();
            return createNewEnvironmentAsync(projectKey, environmentPost, _callback);
        }
    }

    /**
     * Create environment
     * &gt; ### Approval settings &gt; &gt; The &#x60;approvalSettings&#x60; key is only returned when the Flag Approvals feature is enabled. &gt; &gt; You cannot update approval settings when creating new environments. Update approval settings with the PATCH Environment API.  Create a new environment in a specified project with a given name, key, swatch color, and default TTL. 
     * @param projectKey The project key (required)
     * @param environmentPost  (required)
     * @return CreateNewEnvironmentRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Environment response </td><td>  -  </td></tr>
     </table>
     */
    public CreateNewEnvironmentRequestBuilder createNewEnvironment(String name, String key, String color, String projectKey) throws IllegalArgumentException {
        if (name == null) throw new IllegalArgumentException("\"name\" is required but got null");
            

        if (key == null) throw new IllegalArgumentException("\"key\" is required but got null");
            

        if (color == null) throw new IllegalArgumentException("\"color\" is required but got null");
            

        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        return new CreateNewEnvironmentRequestBuilder(name, key, color, projectKey);
    }
    private okhttp3.Call getByProjectAndKeyCall(String projectKey, String environmentKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/environments/{environmentKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()));

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
    private okhttp3.Call getByProjectAndKeyValidateBeforeCall(String projectKey, String environmentKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getByProjectAndKey(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getByProjectAndKey(Async)");
        }

        return getByProjectAndKeyCall(projectKey, environmentKey, _callback);

    }


    private ApiResponse<Environment> getByProjectAndKeyWithHttpInfo(String projectKey, String environmentKey) throws ApiException {
        okhttp3.Call localVarCall = getByProjectAndKeyValidateBeforeCall(projectKey, environmentKey, null);
        Type localVarReturnType = new TypeToken<Environment>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getByProjectAndKeyAsync(String projectKey, String environmentKey, final ApiCallback<Environment> _callback) throws ApiException {

        okhttp3.Call localVarCall = getByProjectAndKeyValidateBeforeCall(projectKey, environmentKey, _callback);
        Type localVarReturnType = new TypeToken<Environment>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetByProjectAndKeyRequestBuilder {
        private final String projectKey;
        private final String environmentKey;

        private GetByProjectAndKeyRequestBuilder(String projectKey, String environmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Build call for getByProjectAndKey
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getByProjectAndKeyCall(projectKey, environmentKey, _callback);
        }


        /**
         * Execute getByProjectAndKey request
         * @return Environment
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public Environment execute() throws ApiException {
            ApiResponse<Environment> localVarResp = getByProjectAndKeyWithHttpInfo(projectKey, environmentKey);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getByProjectAndKey request with HTTP info returned
         * @return ApiResponse&lt;Environment&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Environment> executeWithHttpInfo() throws ApiException {
            return getByProjectAndKeyWithHttpInfo(projectKey, environmentKey);
        }

        /**
         * Execute getByProjectAndKey request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<Environment> _callback) throws ApiException {
            return getByProjectAndKeyAsync(projectKey, environmentKey, _callback);
        }
    }

    /**
     * Get environment
     * &gt; ### Approval settings &gt; &gt; The &#x60;approvalSettings&#x60; key is only returned when the Flag Approvals feature is enabled.  Get an environment given a project and key. 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @return GetByProjectAndKeyRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
     </table>
     */
    public GetByProjectAndKeyRequestBuilder getByProjectAndKey(String projectKey, String environmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new GetByProjectAndKeyRequestBuilder(projectKey, environmentKey);
    }
    private okhttp3.Call listEnvironmentsCall(String projectKey, Long limit, Long offset, String filter, String sort, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/environments"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()));

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

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
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
    private okhttp3.Call listEnvironmentsValidateBeforeCall(String projectKey, Long limit, Long offset, String filter, String sort, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling listEnvironments(Async)");
        }

        return listEnvironmentsCall(projectKey, limit, offset, filter, sort, _callback);

    }


    private ApiResponse<Environments> listEnvironmentsWithHttpInfo(String projectKey, Long limit, Long offset, String filter, String sort) throws ApiException {
        okhttp3.Call localVarCall = listEnvironmentsValidateBeforeCall(projectKey, limit, offset, filter, sort, null);
        Type localVarReturnType = new TypeToken<Environments>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call listEnvironmentsAsync(String projectKey, Long limit, Long offset, String filter, String sort, final ApiCallback<Environments> _callback) throws ApiException {

        okhttp3.Call localVarCall = listEnvironmentsValidateBeforeCall(projectKey, limit, offset, filter, sort, _callback);
        Type localVarReturnType = new TypeToken<Environments>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ListEnvironmentsRequestBuilder {
        private final String projectKey;
        private Long limit;
        private Long offset;
        private String filter;
        private String sort;

        private ListEnvironmentsRequestBuilder(String projectKey) {
            this.projectKey = projectKey;
        }

        /**
         * Set limit
         * @param limit The number of environments to return in the response. Defaults to 20. (optional)
         * @return ListEnvironmentsRequestBuilder
         */
        public ListEnvironmentsRequestBuilder limit(Long limit) {
            this.limit = limit;
            return this;
        }
        
        /**
         * Set offset
         * @param offset Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
         * @return ListEnvironmentsRequestBuilder
         */
        public ListEnvironmentsRequestBuilder offset(Long offset) {
            this.offset = offset;
            return this;
        }
        
        /**
         * Set filter
         * @param filter A comma-separated list of filters. Each filter is of the form &#x60;field:value&#x60;. (optional)
         * @return ListEnvironmentsRequestBuilder
         */
        public ListEnvironmentsRequestBuilder filter(String filter) {
            this.filter = filter;
            return this;
        }
        
        /**
         * Set sort
         * @param sort A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order. (optional)
         * @return ListEnvironmentsRequestBuilder
         */
        public ListEnvironmentsRequestBuilder sort(String sort) {
            this.sort = sort;
            return this;
        }
        
        /**
         * Build call for listEnvironments
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environments collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return listEnvironmentsCall(projectKey, limit, offset, filter, sort, _callback);
        }


        /**
         * Execute listEnvironments request
         * @return Environments
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environments collection response </td><td>  -  </td></tr>
         </table>
         */
        public Environments execute() throws ApiException {
            ApiResponse<Environments> localVarResp = listEnvironmentsWithHttpInfo(projectKey, limit, offset, filter, sort);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute listEnvironments request with HTTP info returned
         * @return ApiResponse&lt;Environments&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environments collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Environments> executeWithHttpInfo() throws ApiException {
            return listEnvironmentsWithHttpInfo(projectKey, limit, offset, filter, sort);
        }

        /**
         * Execute listEnvironments request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environments collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<Environments> _callback) throws ApiException {
            return listEnvironmentsAsync(projectKey, limit, offset, filter, sort, _callback);
        }
    }

    /**
     * List environments
     * Return a list of environments for the specified project.  By default, this returns the first 20 environments. Page through this list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the &#x60;_links&#x60; field that returns. If those links do not appear, the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page, because there is no previous page and you cannot return to the first page when you are already on the first page.  ### Filtering environments  LaunchDarkly supports two fields for filters: - &#x60;query&#x60; is a string that matches against the environments&#39; names and keys. It is not case sensitive. - &#x60;tags&#x60; is a &#x60;+&#x60;-separated list of environment tags. It filters the list of environments that have all of the tags in the list.  For example, the filter &#x60;filter&#x3D;query:abc,tags:tag-1+tag-2&#x60; matches environments with the string &#x60;abc&#x60; in their name or key and also are tagged with &#x60;tag-1&#x60; and &#x60;tag-2&#x60;. The filter is not case-sensitive.  The documented values for &#x60;filter&#x60; query parameters are prior to URL encoding. For example, the &#x60;+&#x60; in &#x60;filter&#x3D;tags:tag-1+tag-2&#x60; must be encoded to &#x60;%2B&#x60;.  ### Sorting environments  LaunchDarkly supports the following fields for sorting:  - &#x60;createdOn&#x60; sorts by the creation date of the environment. - &#x60;critical&#x60; sorts by whether the environments are marked as critical. - &#x60;name&#x60; sorts by environment name.  For example, &#x60;sort&#x3D;name&#x60; sorts the response by environment name in ascending order. 
     * @param projectKey The project key (required)
     * @return ListEnvironmentsRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Environments collection response </td><td>  -  </td></tr>
     </table>
     */
    public ListEnvironmentsRequestBuilder listEnvironments(String projectKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        return new ListEnvironmentsRequestBuilder(projectKey);
    }
    private okhttp3.Call removeByEnvironmentKeyCall(String projectKey, String environmentKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/environments/{environmentKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()));

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
    private okhttp3.Call removeByEnvironmentKeyValidateBeforeCall(String projectKey, String environmentKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling removeByEnvironmentKey(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling removeByEnvironmentKey(Async)");
        }

        return removeByEnvironmentKeyCall(projectKey, environmentKey, _callback);

    }


    private ApiResponse<Void> removeByEnvironmentKeyWithHttpInfo(String projectKey, String environmentKey) throws ApiException {
        okhttp3.Call localVarCall = removeByEnvironmentKeyValidateBeforeCall(projectKey, environmentKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call removeByEnvironmentKeyAsync(String projectKey, String environmentKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = removeByEnvironmentKeyValidateBeforeCall(projectKey, environmentKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class RemoveByEnvironmentKeyRequestBuilder {
        private final String projectKey;
        private final String environmentKey;

        private RemoveByEnvironmentKeyRequestBuilder(String projectKey, String environmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Build call for removeByEnvironmentKey
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
            return removeByEnvironmentKeyCall(projectKey, environmentKey, _callback);
        }


        /**
         * Execute removeByEnvironmentKey request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public void execute() throws ApiException {
            removeByEnvironmentKeyWithHttpInfo(projectKey, environmentKey);
        }

        /**
         * Execute removeByEnvironmentKey request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            return removeByEnvironmentKeyWithHttpInfo(projectKey, environmentKey);
        }

        /**
         * Execute removeByEnvironmentKey request (asynchronously)
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
            return removeByEnvironmentKeyAsync(projectKey, environmentKey, _callback);
        }
    }

    /**
     * Delete environment
     * Delete a environment by key.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @return RemoveByEnvironmentKeyRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
     </table>
     */
    public RemoveByEnvironmentKeyRequestBuilder removeByEnvironmentKey(String projectKey, String environmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new RemoveByEnvironmentKeyRequestBuilder(projectKey, environmentKey);
    }
    private okhttp3.Call resetMobileSdkKeyCall(String projectKey, String environmentKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/environments/{environmentKey}/mobileKey"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()));

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
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call resetMobileSdkKeyValidateBeforeCall(String projectKey, String environmentKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling resetMobileSdkKey(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling resetMobileSdkKey(Async)");
        }

        return resetMobileSdkKeyCall(projectKey, environmentKey, _callback);

    }


    private ApiResponse<Environment> resetMobileSdkKeyWithHttpInfo(String projectKey, String environmentKey) throws ApiException {
        okhttp3.Call localVarCall = resetMobileSdkKeyValidateBeforeCall(projectKey, environmentKey, null);
        Type localVarReturnType = new TypeToken<Environment>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call resetMobileSdkKeyAsync(String projectKey, String environmentKey, final ApiCallback<Environment> _callback) throws ApiException {

        okhttp3.Call localVarCall = resetMobileSdkKeyValidateBeforeCall(projectKey, environmentKey, _callback);
        Type localVarReturnType = new TypeToken<Environment>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ResetMobileSdkKeyRequestBuilder {
        private final String projectKey;
        private final String environmentKey;

        private ResetMobileSdkKeyRequestBuilder(String projectKey, String environmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Build call for resetMobileSdkKey
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return resetMobileSdkKeyCall(projectKey, environmentKey, _callback);
        }


        /**
         * Execute resetMobileSdkKey request
         * @return Environment
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public Environment execute() throws ApiException {
            ApiResponse<Environment> localVarResp = resetMobileSdkKeyWithHttpInfo(projectKey, environmentKey);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute resetMobileSdkKey request with HTTP info returned
         * @return ApiResponse&lt;Environment&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Environment> executeWithHttpInfo() throws ApiException {
            return resetMobileSdkKeyWithHttpInfo(projectKey, environmentKey);
        }

        /**
         * Execute resetMobileSdkKey request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<Environment> _callback) throws ApiException {
            return resetMobileSdkKeyAsync(projectKey, environmentKey, _callback);
        }
    }

    /**
     * Reset environment mobile SDK key
     * Reset an environment&#39;s mobile key. The optional expiry for the old key is deprecated for this endpoint, so the old key will always expire immediately.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @return ResetMobileSdkKeyRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
     </table>
     */
    public ResetMobileSdkKeyRequestBuilder resetMobileSdkKey(String projectKey, String environmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new ResetMobileSdkKeyRequestBuilder(projectKey, environmentKey);
    }
    private okhttp3.Call resetSdkKeyCall(String projectKey, String environmentKey, Long expiry, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/environments/{environmentKey}/apiKey"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (expiry != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("expiry", expiry));
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
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call resetSdkKeyValidateBeforeCall(String projectKey, String environmentKey, Long expiry, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling resetSdkKey(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling resetSdkKey(Async)");
        }

        return resetSdkKeyCall(projectKey, environmentKey, expiry, _callback);

    }


    private ApiResponse<Environment> resetSdkKeyWithHttpInfo(String projectKey, String environmentKey, Long expiry) throws ApiException {
        okhttp3.Call localVarCall = resetSdkKeyValidateBeforeCall(projectKey, environmentKey, expiry, null);
        Type localVarReturnType = new TypeToken<Environment>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call resetSdkKeyAsync(String projectKey, String environmentKey, Long expiry, final ApiCallback<Environment> _callback) throws ApiException {

        okhttp3.Call localVarCall = resetSdkKeyValidateBeforeCall(projectKey, environmentKey, expiry, _callback);
        Type localVarReturnType = new TypeToken<Environment>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ResetSdkKeyRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private Long expiry;

        private ResetSdkKeyRequestBuilder(String projectKey, String environmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Set expiry
         * @param expiry The time at which you want the old SDK key to expire, in UNIX milliseconds. By default, the key expires immediately. During the period between this call and the time when the old SDK key expires, both the old SDK key and the new SDK key will work. (optional)
         * @return ResetSdkKeyRequestBuilder
         */
        public ResetSdkKeyRequestBuilder expiry(Long expiry) {
            this.expiry = expiry;
            return this;
        }
        
        /**
         * Build call for resetSdkKey
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return resetSdkKeyCall(projectKey, environmentKey, expiry, _callback);
        }


        /**
         * Execute resetSdkKey request
         * @return Environment
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public Environment execute() throws ApiException {
            ApiResponse<Environment> localVarResp = resetSdkKeyWithHttpInfo(projectKey, environmentKey, expiry);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute resetSdkKey request with HTTP info returned
         * @return ApiResponse&lt;Environment&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Environment> executeWithHttpInfo() throws ApiException {
            return resetSdkKeyWithHttpInfo(projectKey, environmentKey, expiry);
        }

        /**
         * Execute resetSdkKey request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<Environment> _callback) throws ApiException {
            return resetSdkKeyAsync(projectKey, environmentKey, expiry, _callback);
        }
    }

    /**
     * Reset environment SDK key
     * Reset an environment&#39;s SDK key with an optional expiry time for the old key.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @return ResetSdkKeyRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
     </table>
     */
    public ResetSdkKeyRequestBuilder resetSdkKey(String projectKey, String environmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new ResetSdkKeyRequestBuilder(projectKey, environmentKey);
    }
    private okhttp3.Call updateEnvironmentPatchCall(String projectKey, String environmentKey, List<PatchOperation> patchOperation, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/environments/{environmentKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()));

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
    private okhttp3.Call updateEnvironmentPatchValidateBeforeCall(String projectKey, String environmentKey, List<PatchOperation> patchOperation, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling updateEnvironmentPatch(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling updateEnvironmentPatch(Async)");
        }

        // verify the required parameter 'patchOperation' is set
        if (patchOperation == null) {
            throw new ApiException("Missing the required parameter 'patchOperation' when calling updateEnvironmentPatch(Async)");
        }

        return updateEnvironmentPatchCall(projectKey, environmentKey, patchOperation, _callback);

    }


    private ApiResponse<Environment> updateEnvironmentPatchWithHttpInfo(String projectKey, String environmentKey, List<PatchOperation> patchOperation) throws ApiException {
        okhttp3.Call localVarCall = updateEnvironmentPatchValidateBeforeCall(projectKey, environmentKey, patchOperation, null);
        Type localVarReturnType = new TypeToken<Environment>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call updateEnvironmentPatchAsync(String projectKey, String environmentKey, List<PatchOperation> patchOperation, final ApiCallback<Environment> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateEnvironmentPatchValidateBeforeCall(projectKey, environmentKey, patchOperation, _callback);
        Type localVarReturnType = new TypeToken<Environment>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class UpdateEnvironmentPatchRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private List<PatchOperation> patchOperation;

        private UpdateEnvironmentPatchRequestBuilder(String projectKey, String environmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Set patchOperation
         * @param patchOperation  (optional)
         * @return UpdateEnvironmentPatchRequestBuilder
         */
        public UpdateEnvironmentPatchRequestBuilder patchOperation(List<PatchOperation> patchOperation) {
            this.patchOperation = patchOperation;
            return this;
        }

        /**
         * Build call for updateEnvironmentPatch
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            return updateEnvironmentPatchCall(projectKey, environmentKey, patchOperation, _callback);
        }

        private List<PatchOperation> buildBodyParams() {
            return this.patchOperation;
        }

        /**
         * Execute updateEnvironmentPatch request
         * @return Environment
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public Environment execute() throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            ApiResponse<Environment> localVarResp = updateEnvironmentPatchWithHttpInfo(projectKey, environmentKey, patchOperation);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute updateEnvironmentPatch request with HTTP info returned
         * @return ApiResponse&lt;Environment&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Environment> executeWithHttpInfo() throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            return updateEnvironmentPatchWithHttpInfo(projectKey, environmentKey, patchOperation);
        }

        /**
         * Execute updateEnvironmentPatch request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<Environment> _callback) throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            return updateEnvironmentPatchAsync(projectKey, environmentKey, patchOperation, _callback);
        }
    }

    /**
     * Update environment
     *  Update an environment. Updating an environment uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).  To update fields in the environment object that are arrays, set the &#x60;path&#x60; to the name of the field and then append &#x60;/&lt;array index&gt;&#x60;. Using &#x60;/0&#x60; appends to the beginning of the array.  ### Approval settings  This request only returns the &#x60;approvalSettings&#x60; key if the [Flag Approvals](https://docs.launchdarkly.com/home/feature-workflows/approvals) feature is enabled.  Only the &#x60;canReviewOwnRequest&#x60;, &#x60;canApplyDeclinedChanges&#x60;, &#x60;minNumApprovals&#x60;, &#x60;required&#x60; and &#x60;requiredApprovalTagsfields&#x60; are editable.  If you try to patch the environment by setting both &#x60;required&#x60; and &#x60;requiredApprovalTags&#x60;, the request fails and an error appears. You can specify either required approvals for all flags in an environment or those with specific tags, but not both. 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param patchOperation  (required)
     * @return UpdateEnvironmentPatchRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Environment response </td><td>  -  </td></tr>
     </table>
     */
    public UpdateEnvironmentPatchRequestBuilder updateEnvironmentPatch(String projectKey, String environmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new UpdateEnvironmentPatchRequestBuilder(projectKey, environmentKey);
    }
}
