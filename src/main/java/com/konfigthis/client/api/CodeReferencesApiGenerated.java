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


import com.konfigthis.client.model.BranchCollectionRep;
import com.konfigthis.client.model.BranchRep;
import com.konfigthis.client.model.Extinction;
import com.konfigthis.client.model.ExtinctionCollectionRep;
import com.konfigthis.client.model.PatchOperation;
import com.konfigthis.client.model.PutBranch;
import com.konfigthis.client.model.ReferenceRep;
import com.konfigthis.client.model.RepositoryCollectionRep;
import com.konfigthis.client.model.RepositoryPost;
import com.konfigthis.client.model.RepositoryRep;
import com.konfigthis.client.model.StatisticCollectionRep;
import com.konfigthis.client.model.StatisticsRoot;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class CodeReferencesApiGenerated {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public CodeReferencesApiGenerated() throws IllegalArgumentException {
        this(Configuration.getDefaultApiClient());
    }

    public CodeReferencesApiGenerated(ApiClient apiClient) throws IllegalArgumentException {
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

    private okhttp3.Call asynchronouslyDeleteBranchesCall(String repo, List<String> requestBody, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/code-refs/repositories/{repo}/branch-delete-tasks"
            .replace("{" + "repo" + "}", localVarApiClient.escapeString(repo.toString()));

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
    private okhttp3.Call asynchronouslyDeleteBranchesValidateBeforeCall(String repo, List<String> requestBody, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'repo' is set
        if (repo == null) {
            throw new ApiException("Missing the required parameter 'repo' when calling asynchronouslyDeleteBranches(Async)");
        }

        // verify the required parameter 'requestBody' is set
        if (requestBody == null) {
            throw new ApiException("Missing the required parameter 'requestBody' when calling asynchronouslyDeleteBranches(Async)");
        }

        return asynchronouslyDeleteBranchesCall(repo, requestBody, _callback);

    }


    private ApiResponse<Void> asynchronouslyDeleteBranchesWithHttpInfo(String repo, List<String> requestBody) throws ApiException {
        okhttp3.Call localVarCall = asynchronouslyDeleteBranchesValidateBeforeCall(repo, requestBody, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call asynchronouslyDeleteBranchesAsync(String repo, List<String> requestBody, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = asynchronouslyDeleteBranchesValidateBeforeCall(repo, requestBody, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class AsynchronouslyDeleteBranchesRequestBuilder {
        private final String repo;
        private List<String> requestBody;

        private AsynchronouslyDeleteBranchesRequestBuilder(String repo) {
            this.repo = repo;
        }

        /**
         * Set requestBody
         * @param requestBody  (optional)
         * @return AsynchronouslyDeleteBranchesRequestBuilder
         */
        public AsynchronouslyDeleteBranchesRequestBuilder requestBody(List<String> requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        /**
         * Build call for asynchronouslyDeleteBranches
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            List<String> requestBody = buildBodyParams();
            return asynchronouslyDeleteBranchesCall(repo, requestBody, _callback);
        }

        private List<String> buildBodyParams() {
            return this.requestBody;
        }

        /**
         * Execute asynchronouslyDeleteBranches request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public void execute() throws ApiException {
            List<String> requestBody = buildBodyParams();
            asynchronouslyDeleteBranchesWithHttpInfo(repo, requestBody);
        }

        /**
         * Execute asynchronouslyDeleteBranches request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            List<String> requestBody = buildBodyParams();
            return asynchronouslyDeleteBranchesWithHttpInfo(repo, requestBody);
        }

        /**
         * Execute asynchronouslyDeleteBranches request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<Void> _callback) throws ApiException {
            List<String> requestBody = buildBodyParams();
            return asynchronouslyDeleteBranchesAsync(repo, requestBody, _callback);
        }
    }

    /**
     * Delete branches
     * Asynchronously delete a number of branches.
     * @param repo The repository name to delete branches for. (required)
     * @param requestBody  (required)
     * @return AsynchronouslyDeleteBranchesRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Action succeeded </td><td>  -  </td></tr>
     </table>
     */
    public AsynchronouslyDeleteBranchesRequestBuilder asynchronouslyDeleteBranches(String repo) throws IllegalArgumentException {
        if (repo == null) throw new IllegalArgumentException("\"repo\" is required but got null");
            

        return new AsynchronouslyDeleteBranchesRequestBuilder(repo);
    }
    private okhttp3.Call createExtinctionCall(String repo, String branch, List<Extinction> extinction, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = extinction;

        // create path and map variables
        String localVarPath = "/api/v2/code-refs/repositories/{repo}/branches/{branch}/extinction-events"
            .replace("{" + "repo" + "}", localVarApiClient.escapeString(repo.toString()))
            .replace("{" + "branch" + "}", localVarApiClient.escapeString(branch.toString()));

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
    private okhttp3.Call createExtinctionValidateBeforeCall(String repo, String branch, List<Extinction> extinction, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'repo' is set
        if (repo == null) {
            throw new ApiException("Missing the required parameter 'repo' when calling createExtinction(Async)");
        }

        // verify the required parameter 'branch' is set
        if (branch == null) {
            throw new ApiException("Missing the required parameter 'branch' when calling createExtinction(Async)");
        }

        // verify the required parameter 'extinction' is set
        if (extinction == null) {
            throw new ApiException("Missing the required parameter 'extinction' when calling createExtinction(Async)");
        }

        return createExtinctionCall(repo, branch, extinction, _callback);

    }


    private ApiResponse<Void> createExtinctionWithHttpInfo(String repo, String branch, List<Extinction> extinction) throws ApiException {
        okhttp3.Call localVarCall = createExtinctionValidateBeforeCall(repo, branch, extinction, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call createExtinctionAsync(String repo, String branch, List<Extinction> extinction, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = createExtinctionValidateBeforeCall(repo, branch, extinction, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class CreateExtinctionRequestBuilder {
        private final String repo;
        private final String branch;
        private List<Extinction> extinction;

        private CreateExtinctionRequestBuilder(String repo, String branch) {
            this.repo = repo;
            this.branch = branch;
        }

        /**
         * Set extinction
         * @param extinction  (optional)
         * @return CreateExtinctionRequestBuilder
         */
        public CreateExtinctionRequestBuilder extinction(List<Extinction> extinction) {
            this.extinction = extinction;
            return this;
        }

        /**
         * Build call for createExtinction
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            List<Extinction> extinction = buildBodyParams();
            return createExtinctionCall(repo, branch, extinction, _callback);
        }

        private List<Extinction> buildBodyParams() {
            return this.extinction;
        }

        /**
         * Execute createExtinction request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public void execute() throws ApiException {
            List<Extinction> extinction = buildBodyParams();
            createExtinctionWithHttpInfo(repo, branch, extinction);
        }

        /**
         * Execute createExtinction request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            List<Extinction> extinction = buildBodyParams();
            return createExtinctionWithHttpInfo(repo, branch, extinction);
        }

        /**
         * Execute createExtinction request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<Void> _callback) throws ApiException {
            List<Extinction> extinction = buildBodyParams();
            return createExtinctionAsync(repo, branch, extinction, _callback);
        }
    }

    /**
     * Create extinction
     * Create a new extinction.
     * @param repo The repository name (required)
     * @param branch The URL-encoded branch name (required)
     * @param extinction  (required)
     * @return CreateExtinctionRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Action succeeded </td><td>  -  </td></tr>
     </table>
     */
    public CreateExtinctionRequestBuilder createExtinction(String repo, String branch) throws IllegalArgumentException {
        if (repo == null) throw new IllegalArgumentException("\"repo\" is required but got null");
            

        if (branch == null) throw new IllegalArgumentException("\"branch\" is required but got null");
            

        return new CreateExtinctionRequestBuilder(repo, branch);
    }
    private okhttp3.Call createRepositoryCall(RepositoryPost repositoryPost, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = repositoryPost;

        // create path and map variables
        String localVarPath = "/api/v2/code-refs/repositories";

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
    private okhttp3.Call createRepositoryValidateBeforeCall(RepositoryPost repositoryPost, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'repositoryPost' is set
        if (repositoryPost == null) {
            throw new ApiException("Missing the required parameter 'repositoryPost' when calling createRepository(Async)");
        }

        return createRepositoryCall(repositoryPost, _callback);

    }


    private ApiResponse<RepositoryRep> createRepositoryWithHttpInfo(RepositoryPost repositoryPost) throws ApiException {
        okhttp3.Call localVarCall = createRepositoryValidateBeforeCall(repositoryPost, null);
        Type localVarReturnType = new TypeToken<RepositoryRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call createRepositoryAsync(RepositoryPost repositoryPost, final ApiCallback<RepositoryRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = createRepositoryValidateBeforeCall(repositoryPost, _callback);
        Type localVarReturnType = new TypeToken<RepositoryRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class CreateRepositoryRequestBuilder {
        private final String name;
        private String sourceLink;
        private String commitUrlTemplate;
        private String hunkUrlTemplate;
        private String type;
        private String defaultBranch;

        private CreateRepositoryRequestBuilder(String name) {
            this.name = name;
        }

        /**
         * Set sourceLink
         * @param sourceLink A URL to access the repository (optional)
         * @return CreateRepositoryRequestBuilder
         */
        public CreateRepositoryRequestBuilder sourceLink(String sourceLink) {
            this.sourceLink = sourceLink;
            return this;
        }
        
        /**
         * Set commitUrlTemplate
         * @param commitUrlTemplate A template for constructing a valid URL to view the commit (optional)
         * @return CreateRepositoryRequestBuilder
         */
        public CreateRepositoryRequestBuilder commitUrlTemplate(String commitUrlTemplate) {
            this.commitUrlTemplate = commitUrlTemplate;
            return this;
        }
        
        /**
         * Set hunkUrlTemplate
         * @param hunkUrlTemplate A template for constructing a valid URL to view the hunk (optional)
         * @return CreateRepositoryRequestBuilder
         */
        public CreateRepositoryRequestBuilder hunkUrlTemplate(String hunkUrlTemplate) {
            this.hunkUrlTemplate = hunkUrlTemplate;
            return this;
        }
        
        /**
         * Set type
         * @param type The type of repository. If not specified, the default value is &lt;code&gt;custom&lt;/code&gt;. (optional)
         * @return CreateRepositoryRequestBuilder
         */
        public CreateRepositoryRequestBuilder type(String type) {
            this.type = type;
            return this;
        }
        
        /**
         * Set defaultBranch
         * @param defaultBranch The repository&#39;s default branch. If not specified, the default value is &lt;code&gt;main&lt;/code&gt;. (optional)
         * @return CreateRepositoryRequestBuilder
         */
        public CreateRepositoryRequestBuilder defaultBranch(String defaultBranch) {
            this.defaultBranch = defaultBranch;
            return this;
        }
        
        /**
         * Build call for createRepository
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            RepositoryPost repositoryPost = buildBodyParams();
            return createRepositoryCall(repositoryPost, _callback);
        }

        private RepositoryPost buildBodyParams() {
            RepositoryPost repositoryPost = new RepositoryPost();
            repositoryPost.name(this.name);
            repositoryPost.sourceLink(this.sourceLink);
            repositoryPost.commitUrlTemplate(this.commitUrlTemplate);
            repositoryPost.hunkUrlTemplate(this.hunkUrlTemplate);
            if (this.type != null)
            repositoryPost.type(RepositoryPost.TypeEnum.fromValue(this.type));
            repositoryPost.defaultBranch(this.defaultBranch);
            return repositoryPost;
        }

        /**
         * Execute createRepository request
         * @return RepositoryRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository response </td><td>  -  </td></tr>
         </table>
         */
        public RepositoryRep execute() throws ApiException {
            RepositoryPost repositoryPost = buildBodyParams();
            ApiResponse<RepositoryRep> localVarResp = createRepositoryWithHttpInfo(repositoryPost);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute createRepository request with HTTP info returned
         * @return ApiResponse&lt;RepositoryRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<RepositoryRep> executeWithHttpInfo() throws ApiException {
            RepositoryPost repositoryPost = buildBodyParams();
            return createRepositoryWithHttpInfo(repositoryPost);
        }

        /**
         * Execute createRepository request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<RepositoryRep> _callback) throws ApiException {
            RepositoryPost repositoryPost = buildBodyParams();
            return createRepositoryAsync(repositoryPost, _callback);
        }
    }

    /**
     * Create repository
     * Create a repository with the specified name.
     * @param repositoryPost  (required)
     * @return CreateRepositoryRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Repository response </td><td>  -  </td></tr>
     </table>
     */
    public CreateRepositoryRequestBuilder createRepository(String name) throws IllegalArgumentException {
        if (name == null) throw new IllegalArgumentException("\"name\" is required but got null");
            

        return new CreateRepositoryRequestBuilder(name);
    }
    private okhttp3.Call deleteRepositoryCall(String repo, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/code-refs/repositories/{repo}"
            .replace("{" + "repo" + "}", localVarApiClient.escapeString(repo.toString()));

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
    private okhttp3.Call deleteRepositoryValidateBeforeCall(String repo, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'repo' is set
        if (repo == null) {
            throw new ApiException("Missing the required parameter 'repo' when calling deleteRepository(Async)");
        }

        return deleteRepositoryCall(repo, _callback);

    }


    private ApiResponse<Void> deleteRepositoryWithHttpInfo(String repo) throws ApiException {
        okhttp3.Call localVarCall = deleteRepositoryValidateBeforeCall(repo, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call deleteRepositoryAsync(String repo, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteRepositoryValidateBeforeCall(repo, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class DeleteRepositoryRequestBuilder {
        private final String repo;

        private DeleteRepositoryRequestBuilder(String repo) {
            this.repo = repo;
        }

        /**
         * Build call for deleteRepository
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
            return deleteRepositoryCall(repo, _callback);
        }


        /**
         * Execute deleteRepository request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public void execute() throws ApiException {
            deleteRepositoryWithHttpInfo(repo);
        }

        /**
         * Execute deleteRepository request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            return deleteRepositoryWithHttpInfo(repo);
        }

        /**
         * Execute deleteRepository request (asynchronously)
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
            return deleteRepositoryAsync(repo, _callback);
        }
    }

    /**
     * Delete repository
     * Delete a repository with the specified name.
     * @param repo The repository name (required)
     * @return DeleteRepositoryRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
     </table>
     */
    public DeleteRepositoryRequestBuilder deleteRepository(String repo) throws IllegalArgumentException {
        if (repo == null) throw new IllegalArgumentException("\"repo\" is required but got null");
            

        return new DeleteRepositoryRequestBuilder(repo);
    }
    private okhttp3.Call getBranchCall(String repo, String branch, String projKey, String flagKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/code-refs/repositories/{repo}/branches/{branch}"
            .replace("{" + "repo" + "}", localVarApiClient.escapeString(repo.toString()))
            .replace("{" + "branch" + "}", localVarApiClient.escapeString(branch.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (projKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("projKey", projKey));
        }

        if (flagKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("flagKey", flagKey));
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
    private okhttp3.Call getBranchValidateBeforeCall(String repo, String branch, String projKey, String flagKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'repo' is set
        if (repo == null) {
            throw new ApiException("Missing the required parameter 'repo' when calling getBranch(Async)");
        }

        // verify the required parameter 'branch' is set
        if (branch == null) {
            throw new ApiException("Missing the required parameter 'branch' when calling getBranch(Async)");
        }

        return getBranchCall(repo, branch, projKey, flagKey, _callback);

    }


    private ApiResponse<BranchRep> getBranchWithHttpInfo(String repo, String branch, String projKey, String flagKey) throws ApiException {
        okhttp3.Call localVarCall = getBranchValidateBeforeCall(repo, branch, projKey, flagKey, null);
        Type localVarReturnType = new TypeToken<BranchRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getBranchAsync(String repo, String branch, String projKey, String flagKey, final ApiCallback<BranchRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getBranchValidateBeforeCall(repo, branch, projKey, flagKey, _callback);
        Type localVarReturnType = new TypeToken<BranchRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetBranchRequestBuilder {
        private final String repo;
        private final String branch;
        private String projKey;
        private String flagKey;

        private GetBranchRequestBuilder(String repo, String branch) {
            this.repo = repo;
            this.branch = branch;
        }

        /**
         * Set projKey
         * @param projKey Filter results to a specific project (optional)
         * @return GetBranchRequestBuilder
         */
        public GetBranchRequestBuilder projKey(String projKey) {
            this.projKey = projKey;
            return this;
        }
        
        /**
         * Set flagKey
         * @param flagKey Filter results to a specific flag key (optional)
         * @return GetBranchRequestBuilder
         */
        public GetBranchRequestBuilder flagKey(String flagKey) {
            this.flagKey = flagKey;
            return this;
        }
        
        /**
         * Build call for getBranch
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Branch response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getBranchCall(repo, branch, projKey, flagKey, _callback);
        }


        /**
         * Execute getBranch request
         * @return BranchRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Branch response </td><td>  -  </td></tr>
         </table>
         */
        public BranchRep execute() throws ApiException {
            ApiResponse<BranchRep> localVarResp = getBranchWithHttpInfo(repo, branch, projKey, flagKey);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getBranch request with HTTP info returned
         * @return ApiResponse&lt;BranchRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Branch response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<BranchRep> executeWithHttpInfo() throws ApiException {
            return getBranchWithHttpInfo(repo, branch, projKey, flagKey);
        }

        /**
         * Execute getBranch request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Branch response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<BranchRep> _callback) throws ApiException {
            return getBranchAsync(repo, branch, projKey, flagKey, _callback);
        }
    }

    /**
     * Get branch
     * Get a specific branch in a repository.
     * @param repo The repository name (required)
     * @param branch The url-encoded branch name (required)
     * @return GetBranchRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Branch response </td><td>  -  </td></tr>
     </table>
     */
    public GetBranchRequestBuilder getBranch(String repo, String branch) throws IllegalArgumentException {
        if (repo == null) throw new IllegalArgumentException("\"repo\" is required but got null");
            

        if (branch == null) throw new IllegalArgumentException("\"branch\" is required but got null");
            

        return new GetBranchRequestBuilder(repo, branch);
    }
    private okhttp3.Call getRepositoryByRepoCall(String repo, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/code-refs/repositories/{repo}"
            .replace("{" + "repo" + "}", localVarApiClient.escapeString(repo.toString()));

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
    private okhttp3.Call getRepositoryByRepoValidateBeforeCall(String repo, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'repo' is set
        if (repo == null) {
            throw new ApiException("Missing the required parameter 'repo' when calling getRepositoryByRepo(Async)");
        }

        return getRepositoryByRepoCall(repo, _callback);

    }


    private ApiResponse<RepositoryRep> getRepositoryByRepoWithHttpInfo(String repo) throws ApiException {
        okhttp3.Call localVarCall = getRepositoryByRepoValidateBeforeCall(repo, null);
        Type localVarReturnType = new TypeToken<RepositoryRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getRepositoryByRepoAsync(String repo, final ApiCallback<RepositoryRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getRepositoryByRepoValidateBeforeCall(repo, _callback);
        Type localVarReturnType = new TypeToken<RepositoryRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetRepositoryByRepoRequestBuilder {
        private final String repo;

        private GetRepositoryByRepoRequestBuilder(String repo) {
            this.repo = repo;
        }

        /**
         * Build call for getRepositoryByRepo
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getRepositoryByRepoCall(repo, _callback);
        }


        /**
         * Execute getRepositoryByRepo request
         * @return RepositoryRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository response </td><td>  -  </td></tr>
         </table>
         */
        public RepositoryRep execute() throws ApiException {
            ApiResponse<RepositoryRep> localVarResp = getRepositoryByRepoWithHttpInfo(repo);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getRepositoryByRepo request with HTTP info returned
         * @return ApiResponse&lt;RepositoryRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<RepositoryRep> executeWithHttpInfo() throws ApiException {
            return getRepositoryByRepoWithHttpInfo(repo);
        }

        /**
         * Execute getRepositoryByRepo request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<RepositoryRep> _callback) throws ApiException {
            return getRepositoryByRepoAsync(repo, _callback);
        }
    }

    /**
     * Get repository
     * Get a single repository by name.
     * @param repo The repository name (required)
     * @return GetRepositoryByRepoRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Repository response </td><td>  -  </td></tr>
     </table>
     */
    public GetRepositoryByRepoRequestBuilder getRepositoryByRepo(String repo) throws IllegalArgumentException {
        if (repo == null) throw new IllegalArgumentException("\"repo\" is required but got null");
            

        return new GetRepositoryByRepoRequestBuilder(repo);
    }
    private okhttp3.Call getStatisticsCall(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/code-refs/statistics";

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
    private okhttp3.Call getStatisticsValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return getStatisticsCall(_callback);

    }


    private ApiResponse<StatisticsRoot> getStatisticsWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getStatisticsValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<StatisticsRoot>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getStatisticsAsync(final ApiCallback<StatisticsRoot> _callback) throws ApiException {

        okhttp3.Call localVarCall = getStatisticsValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<StatisticsRoot>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetStatisticsRequestBuilder {

        private GetStatisticsRequestBuilder() {
        }

        /**
         * Build call for getStatistics
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Statistic root response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getStatisticsCall(_callback);
        }


        /**
         * Execute getStatistics request
         * @return StatisticsRoot
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Statistic root response </td><td>  -  </td></tr>
         </table>
         */
        public StatisticsRoot execute() throws ApiException {
            ApiResponse<StatisticsRoot> localVarResp = getStatisticsWithHttpInfo();
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getStatistics request with HTTP info returned
         * @return ApiResponse&lt;StatisticsRoot&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Statistic root response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<StatisticsRoot> executeWithHttpInfo() throws ApiException {
            return getStatisticsWithHttpInfo();
        }

        /**
         * Execute getStatistics request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Statistic root response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<StatisticsRoot> _callback) throws ApiException {
            return getStatisticsAsync(_callback);
        }
    }

    /**
     * Get links to code reference repositories for each project
     * Get links for all projects that have code references.
     * @return GetStatisticsRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Statistic root response </td><td>  -  </td></tr>
     </table>
     */
    public GetStatisticsRequestBuilder getStatistics() throws IllegalArgumentException {
        return new GetStatisticsRequestBuilder();
    }
    private okhttp3.Call getStatistics_0Call(String projectKey, String flagKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/code-refs/statistics/{projectKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (flagKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("flagKey", flagKey));
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
    private okhttp3.Call getStatistics_0ValidateBeforeCall(String projectKey, String flagKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getStatistics_0(Async)");
        }

        return getStatistics_0Call(projectKey, flagKey, _callback);

    }


    private ApiResponse<StatisticCollectionRep> getStatistics_0WithHttpInfo(String projectKey, String flagKey) throws ApiException {
        okhttp3.Call localVarCall = getStatistics_0ValidateBeforeCall(projectKey, flagKey, null);
        Type localVarReturnType = new TypeToken<StatisticCollectionRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getStatistics_0Async(String projectKey, String flagKey, final ApiCallback<StatisticCollectionRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = getStatistics_0ValidateBeforeCall(projectKey, flagKey, _callback);
        Type localVarReturnType = new TypeToken<StatisticCollectionRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetStatistics0RequestBuilder {
        private final String projectKey;
        private String flagKey;

        private GetStatistics0RequestBuilder(String projectKey) {
            this.projectKey = projectKey;
        }

        /**
         * Set flagKey
         * @param flagKey Filter results to a specific flag key (optional)
         * @return GetStatistics0RequestBuilder
         */
        public GetStatistics0RequestBuilder flagKey(String flagKey) {
            this.flagKey = flagKey;
            return this;
        }
        
        /**
         * Build call for getStatistics_0
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Statistic collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getStatistics_0Call(projectKey, flagKey, _callback);
        }


        /**
         * Execute getStatistics_0 request
         * @return StatisticCollectionRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Statistic collection response </td><td>  -  </td></tr>
         </table>
         */
        public StatisticCollectionRep execute() throws ApiException {
            ApiResponse<StatisticCollectionRep> localVarResp = getStatistics_0WithHttpInfo(projectKey, flagKey);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getStatistics_0 request with HTTP info returned
         * @return ApiResponse&lt;StatisticCollectionRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Statistic collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<StatisticCollectionRep> executeWithHttpInfo() throws ApiException {
            return getStatistics_0WithHttpInfo(projectKey, flagKey);
        }

        /**
         * Execute getStatistics_0 request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Statistic collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<StatisticCollectionRep> _callback) throws ApiException {
            return getStatistics_0Async(projectKey, flagKey, _callback);
        }
    }

    /**
     * Get code references statistics for flags
     * Get statistics about all the code references across repositories for all flags in your project that have code references in the default branch, for example, &#x60;main&#x60;. Optionally, you can include the &#x60;flagKey&#x60; query parameter to limit your request to statistics about code references for a single flag. This endpoint returns the number of references to your flag keys in your repositories, as well as a link to each repository.
     * @param projectKey The project key (required)
     * @return GetStatistics0RequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Statistic collection response </td><td>  -  </td></tr>
     </table>
     */
    public GetStatistics0RequestBuilder getStatistics_0(String projectKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        return new GetStatistics0RequestBuilder(projectKey);
    }
    private okhttp3.Call listBranchesCall(String repo, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/code-refs/repositories/{repo}/branches"
            .replace("{" + "repo" + "}", localVarApiClient.escapeString(repo.toString()));

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
    private okhttp3.Call listBranchesValidateBeforeCall(String repo, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'repo' is set
        if (repo == null) {
            throw new ApiException("Missing the required parameter 'repo' when calling listBranches(Async)");
        }

        return listBranchesCall(repo, _callback);

    }


    private ApiResponse<BranchCollectionRep> listBranchesWithHttpInfo(String repo) throws ApiException {
        okhttp3.Call localVarCall = listBranchesValidateBeforeCall(repo, null);
        Type localVarReturnType = new TypeToken<BranchCollectionRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call listBranchesAsync(String repo, final ApiCallback<BranchCollectionRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = listBranchesValidateBeforeCall(repo, _callback);
        Type localVarReturnType = new TypeToken<BranchCollectionRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ListBranchesRequestBuilder {
        private final String repo;

        private ListBranchesRequestBuilder(String repo) {
            this.repo = repo;
        }

        /**
         * Build call for listBranches
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Branch collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return listBranchesCall(repo, _callback);
        }


        /**
         * Execute listBranches request
         * @return BranchCollectionRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Branch collection response </td><td>  -  </td></tr>
         </table>
         */
        public BranchCollectionRep execute() throws ApiException {
            ApiResponse<BranchCollectionRep> localVarResp = listBranchesWithHttpInfo(repo);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute listBranches request with HTTP info returned
         * @return ApiResponse&lt;BranchCollectionRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Branch collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<BranchCollectionRep> executeWithHttpInfo() throws ApiException {
            return listBranchesWithHttpInfo(repo);
        }

        /**
         * Execute listBranches request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Branch collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<BranchCollectionRep> _callback) throws ApiException {
            return listBranchesAsync(repo, _callback);
        }
    }

    /**
     * List branches
     * Get a list of branches.
     * @param repo The repository name (required)
     * @return ListBranchesRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Branch collection response </td><td>  -  </td></tr>
     </table>
     */
    public ListBranchesRequestBuilder listBranches(String repo) throws IllegalArgumentException {
        if (repo == null) throw new IllegalArgumentException("\"repo\" is required but got null");
            

        return new ListBranchesRequestBuilder(repo);
    }
    private okhttp3.Call listExtinctionsCall(String repoName, String branchName, String projKey, String flagKey, Long from, Long to, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/code-refs/extinctions";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (repoName != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("repoName", repoName));
        }

        if (branchName != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("branchName", branchName));
        }

        if (projKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("projKey", projKey));
        }

        if (flagKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("flagKey", flagKey));
        }

        if (from != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("from", from));
        }

        if (to != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("to", to));
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
    private okhttp3.Call listExtinctionsValidateBeforeCall(String repoName, String branchName, String projKey, String flagKey, Long from, Long to, final ApiCallback _callback) throws ApiException {
        return listExtinctionsCall(repoName, branchName, projKey, flagKey, from, to, _callback);

    }


    private ApiResponse<ExtinctionCollectionRep> listExtinctionsWithHttpInfo(String repoName, String branchName, String projKey, String flagKey, Long from, Long to) throws ApiException {
        okhttp3.Call localVarCall = listExtinctionsValidateBeforeCall(repoName, branchName, projKey, flagKey, from, to, null);
        Type localVarReturnType = new TypeToken<ExtinctionCollectionRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call listExtinctionsAsync(String repoName, String branchName, String projKey, String flagKey, Long from, Long to, final ApiCallback<ExtinctionCollectionRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = listExtinctionsValidateBeforeCall(repoName, branchName, projKey, flagKey, from, to, _callback);
        Type localVarReturnType = new TypeToken<ExtinctionCollectionRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ListExtinctionsRequestBuilder {
        private String repoName;
        private String branchName;
        private String projKey;
        private String flagKey;
        private Long from;
        private Long to;

        private ListExtinctionsRequestBuilder() {
        }

        /**
         * Set repoName
         * @param repoName Filter results to a specific repository (optional)
         * @return ListExtinctionsRequestBuilder
         */
        public ListExtinctionsRequestBuilder repoName(String repoName) {
            this.repoName = repoName;
            return this;
        }
        
        /**
         * Set branchName
         * @param branchName Filter results to a specific branch. By default, only the default branch will be queried for extinctions. (optional)
         * @return ListExtinctionsRequestBuilder
         */
        public ListExtinctionsRequestBuilder branchName(String branchName) {
            this.branchName = branchName;
            return this;
        }
        
        /**
         * Set projKey
         * @param projKey Filter results to a specific project (optional)
         * @return ListExtinctionsRequestBuilder
         */
        public ListExtinctionsRequestBuilder projKey(String projKey) {
            this.projKey = projKey;
            return this;
        }
        
        /**
         * Set flagKey
         * @param flagKey Filter results to a specific flag key (optional)
         * @return ListExtinctionsRequestBuilder
         */
        public ListExtinctionsRequestBuilder flagKey(String flagKey) {
            this.flagKey = flagKey;
            return this;
        }
        
        /**
         * Set from
         * @param from Filter results to a specific timeframe based on commit time, expressed as a Unix epoch time in milliseconds. Must be used with &#x60;to&#x60;. (optional)
         * @return ListExtinctionsRequestBuilder
         */
        public ListExtinctionsRequestBuilder from(Long from) {
            this.from = from;
            return this;
        }
        
        /**
         * Set to
         * @param to Filter results to a specific timeframe based on commit time, expressed as a Unix epoch time in milliseconds. Must be used with &#x60;from&#x60;. (optional)
         * @return ListExtinctionsRequestBuilder
         */
        public ListExtinctionsRequestBuilder to(Long to) {
            this.to = to;
            return this;
        }
        
        /**
         * Build call for listExtinctions
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Extinction collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return listExtinctionsCall(repoName, branchName, projKey, flagKey, from, to, _callback);
        }


        /**
         * Execute listExtinctions request
         * @return ExtinctionCollectionRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Extinction collection response </td><td>  -  </td></tr>
         </table>
         */
        public ExtinctionCollectionRep execute() throws ApiException {
            ApiResponse<ExtinctionCollectionRep> localVarResp = listExtinctionsWithHttpInfo(repoName, branchName, projKey, flagKey, from, to);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute listExtinctions request with HTTP info returned
         * @return ApiResponse&lt;ExtinctionCollectionRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Extinction collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ExtinctionCollectionRep> executeWithHttpInfo() throws ApiException {
            return listExtinctionsWithHttpInfo(repoName, branchName, projKey, flagKey, from, to);
        }

        /**
         * Execute listExtinctions request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Extinction collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ExtinctionCollectionRep> _callback) throws ApiException {
            return listExtinctionsAsync(repoName, branchName, projKey, flagKey, from, to, _callback);
        }
    }

    /**
     * List extinctions
     * Get a list of all extinctions. LaunchDarkly creates an extinction event after you remove all code references to a flag. To learn more, read [Understanding extinction events](https://docs.launchdarkly.com/home/code/code-references#understanding-extinction-events).
     * @return ListExtinctionsRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Extinction collection response </td><td>  -  </td></tr>
     </table>
     */
    public ListExtinctionsRequestBuilder listExtinctions() throws IllegalArgumentException {
        return new ListExtinctionsRequestBuilder();
    }
    private okhttp3.Call listRepositoriesCall(String withBranches, String withReferencesForDefaultBranch, String projKey, String flagKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/code-refs/repositories";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (withBranches != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("withBranches", withBranches));
        }

        if (withReferencesForDefaultBranch != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("withReferencesForDefaultBranch", withReferencesForDefaultBranch));
        }

        if (projKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("projKey", projKey));
        }

        if (flagKey != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("flagKey", flagKey));
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
    private okhttp3.Call listRepositoriesValidateBeforeCall(String withBranches, String withReferencesForDefaultBranch, String projKey, String flagKey, final ApiCallback _callback) throws ApiException {
        return listRepositoriesCall(withBranches, withReferencesForDefaultBranch, projKey, flagKey, _callback);

    }


    private ApiResponse<RepositoryCollectionRep> listRepositoriesWithHttpInfo(String withBranches, String withReferencesForDefaultBranch, String projKey, String flagKey) throws ApiException {
        okhttp3.Call localVarCall = listRepositoriesValidateBeforeCall(withBranches, withReferencesForDefaultBranch, projKey, flagKey, null);
        Type localVarReturnType = new TypeToken<RepositoryCollectionRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call listRepositoriesAsync(String withBranches, String withReferencesForDefaultBranch, String projKey, String flagKey, final ApiCallback<RepositoryCollectionRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = listRepositoriesValidateBeforeCall(withBranches, withReferencesForDefaultBranch, projKey, flagKey, _callback);
        Type localVarReturnType = new TypeToken<RepositoryCollectionRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ListRepositoriesRequestBuilder {
        private String withBranches;
        private String withReferencesForDefaultBranch;
        private String projKey;
        private String flagKey;

        private ListRepositoriesRequestBuilder() {
        }

        /**
         * Set withBranches
         * @param withBranches If set to any value, the endpoint returns repositories with associated branch data (optional)
         * @return ListRepositoriesRequestBuilder
         */
        public ListRepositoriesRequestBuilder withBranches(String withBranches) {
            this.withBranches = withBranches;
            return this;
        }
        
        /**
         * Set withReferencesForDefaultBranch
         * @param withReferencesForDefaultBranch If set to any value, the endpoint returns repositories with associated branch data, as well as code references for the default git branch (optional)
         * @return ListRepositoriesRequestBuilder
         */
        public ListRepositoriesRequestBuilder withReferencesForDefaultBranch(String withReferencesForDefaultBranch) {
            this.withReferencesForDefaultBranch = withReferencesForDefaultBranch;
            return this;
        }
        
        /**
         * Set projKey
         * @param projKey A LaunchDarkly project key. If provided, this filters code reference results to the specified project. (optional)
         * @return ListRepositoriesRequestBuilder
         */
        public ListRepositoriesRequestBuilder projKey(String projKey) {
            this.projKey = projKey;
            return this;
        }
        
        /**
         * Set flagKey
         * @param flagKey If set to any value, the endpoint returns repositories with associated branch data, as well as code references for the default git branch (optional)
         * @return ListRepositoriesRequestBuilder
         */
        public ListRepositoriesRequestBuilder flagKey(String flagKey) {
            this.flagKey = flagKey;
            return this;
        }
        
        /**
         * Build call for listRepositories
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return listRepositoriesCall(withBranches, withReferencesForDefaultBranch, projKey, flagKey, _callback);
        }


        /**
         * Execute listRepositories request
         * @return RepositoryCollectionRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository collection response </td><td>  -  </td></tr>
         </table>
         */
        public RepositoryCollectionRep execute() throws ApiException {
            ApiResponse<RepositoryCollectionRep> localVarResp = listRepositoriesWithHttpInfo(withBranches, withReferencesForDefaultBranch, projKey, flagKey);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute listRepositories request with HTTP info returned
         * @return ApiResponse&lt;RepositoryCollectionRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<RepositoryCollectionRep> executeWithHttpInfo() throws ApiException {
            return listRepositoriesWithHttpInfo(withBranches, withReferencesForDefaultBranch, projKey, flagKey);
        }

        /**
         * Execute listRepositories request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<RepositoryCollectionRep> _callback) throws ApiException {
            return listRepositoriesAsync(withBranches, withReferencesForDefaultBranch, projKey, flagKey, _callback);
        }
    }

    /**
     * List repositories
     * Get a list of connected repositories. Optionally, you can include branch metadata with the &#x60;withBranches&#x60; query parameter. Embed references for the default branch with &#x60;ReferencesForDefaultBranch&#x60;. You can also filter the list of code references by project key and flag key.
     * @return ListRepositoriesRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Repository collection response </td><td>  -  </td></tr>
     </table>
     */
    public ListRepositoriesRequestBuilder listRepositories() throws IllegalArgumentException {
        return new ListRepositoriesRequestBuilder();
    }
    private okhttp3.Call updateRepositorySettingsCall(String repo, List<PatchOperation> patchOperation, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/code-refs/repositories/{repo}"
            .replace("{" + "repo" + "}", localVarApiClient.escapeString(repo.toString()));

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
    private okhttp3.Call updateRepositorySettingsValidateBeforeCall(String repo, List<PatchOperation> patchOperation, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'repo' is set
        if (repo == null) {
            throw new ApiException("Missing the required parameter 'repo' when calling updateRepositorySettings(Async)");
        }

        // verify the required parameter 'patchOperation' is set
        if (patchOperation == null) {
            throw new ApiException("Missing the required parameter 'patchOperation' when calling updateRepositorySettings(Async)");
        }

        return updateRepositorySettingsCall(repo, patchOperation, _callback);

    }


    private ApiResponse<RepositoryRep> updateRepositorySettingsWithHttpInfo(String repo, List<PatchOperation> patchOperation) throws ApiException {
        okhttp3.Call localVarCall = updateRepositorySettingsValidateBeforeCall(repo, patchOperation, null);
        Type localVarReturnType = new TypeToken<RepositoryRep>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call updateRepositorySettingsAsync(String repo, List<PatchOperation> patchOperation, final ApiCallback<RepositoryRep> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateRepositorySettingsValidateBeforeCall(repo, patchOperation, _callback);
        Type localVarReturnType = new TypeToken<RepositoryRep>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class UpdateRepositorySettingsRequestBuilder {
        private final String repo;
        private List<PatchOperation> patchOperation;

        private UpdateRepositorySettingsRequestBuilder(String repo) {
            this.repo = repo;
        }

        /**
         * Set patchOperation
         * @param patchOperation  (optional)
         * @return UpdateRepositorySettingsRequestBuilder
         */
        public UpdateRepositorySettingsRequestBuilder patchOperation(List<PatchOperation> patchOperation) {
            this.patchOperation = patchOperation;
            return this;
        }

        /**
         * Build call for updateRepositorySettings
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            return updateRepositorySettingsCall(repo, patchOperation, _callback);
        }

        private List<PatchOperation> buildBodyParams() {
            return this.patchOperation;
        }

        /**
         * Execute updateRepositorySettings request
         * @return RepositoryRep
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository response </td><td>  -  </td></tr>
         </table>
         */
        public RepositoryRep execute() throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            ApiResponse<RepositoryRep> localVarResp = updateRepositorySettingsWithHttpInfo(repo, patchOperation);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute updateRepositorySettings request with HTTP info returned
         * @return ApiResponse&lt;RepositoryRep&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<RepositoryRep> executeWithHttpInfo() throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            return updateRepositorySettingsWithHttpInfo(repo, patchOperation);
        }

        /**
         * Execute updateRepositorySettings request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Repository response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<RepositoryRep> _callback) throws ApiException {
            List<PatchOperation> patchOperation = buildBodyParams();
            return updateRepositorySettingsAsync(repo, patchOperation, _callback);
        }
    }

    /**
     * Update repository
     * Update a repository&#39;s settings. Updating repository settings uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) or [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).
     * @param repo The repository name (required)
     * @param patchOperation  (required)
     * @return UpdateRepositorySettingsRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Repository response </td><td>  -  </td></tr>
     </table>
     */
    public UpdateRepositorySettingsRequestBuilder updateRepositorySettings(String repo) throws IllegalArgumentException {
        if (repo == null) throw new IllegalArgumentException("\"repo\" is required but got null");
            

        return new UpdateRepositorySettingsRequestBuilder(repo);
    }
    private okhttp3.Call upsertBranchCall(String repo, String branch, PutBranch putBranch, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = putBranch;

        // create path and map variables
        String localVarPath = "/api/v2/code-refs/repositories/{repo}/branches/{branch}"
            .replace("{" + "repo" + "}", localVarApiClient.escapeString(repo.toString()))
            .replace("{" + "branch" + "}", localVarApiClient.escapeString(branch.toString()));

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
    private okhttp3.Call upsertBranchValidateBeforeCall(String repo, String branch, PutBranch putBranch, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'repo' is set
        if (repo == null) {
            throw new ApiException("Missing the required parameter 'repo' when calling upsertBranch(Async)");
        }

        // verify the required parameter 'branch' is set
        if (branch == null) {
            throw new ApiException("Missing the required parameter 'branch' when calling upsertBranch(Async)");
        }

        // verify the required parameter 'putBranch' is set
        if (putBranch == null) {
            throw new ApiException("Missing the required parameter 'putBranch' when calling upsertBranch(Async)");
        }

        return upsertBranchCall(repo, branch, putBranch, _callback);

    }


    private ApiResponse<Void> upsertBranchWithHttpInfo(String repo, String branch, PutBranch putBranch) throws ApiException {
        okhttp3.Call localVarCall = upsertBranchValidateBeforeCall(repo, branch, putBranch, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call upsertBranchAsync(String repo, String branch, PutBranch putBranch, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = upsertBranchValidateBeforeCall(repo, branch, putBranch, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class UpsertBranchRequestBuilder {
        private final String name;
        private final String head;
        private final Long syncTime;
        private final String repo;
        private final String branch;
        private Long updateSequenceId;
        private List<ReferenceRep> references;
        private Long commitTime;

        private UpsertBranchRequestBuilder(String name, String head, Long syncTime, String repo, String branch) {
            this.name = name;
            this.head = head;
            this.syncTime = syncTime;
            this.repo = repo;
            this.branch = branch;
        }

        /**
         * Set updateSequenceId
         * @param updateSequenceId An optional ID used to prevent older data from overwriting newer data. If no sequence ID is included, the newly submitted data will always be saved. (optional)
         * @return UpsertBranchRequestBuilder
         */
        public UpsertBranchRequestBuilder updateSequenceId(Long updateSequenceId) {
            this.updateSequenceId = updateSequenceId;
            return this;
        }
        
        /**
         * Set references
         * @param references An array of flag references found on the branch (optional)
         * @return UpsertBranchRequestBuilder
         */
        public UpsertBranchRequestBuilder references(List<ReferenceRep> references) {
            this.references = references;
            return this;
        }
        
        /**
         * Set commitTime
         * @param commitTime  (optional)
         * @return UpsertBranchRequestBuilder
         */
        public UpsertBranchRequestBuilder commitTime(Long commitTime) {
            this.commitTime = commitTime;
            return this;
        }
        
        /**
         * Build call for upsertBranch
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            PutBranch putBranch = buildBodyParams();
            return upsertBranchCall(repo, branch, putBranch, _callback);
        }

        private PutBranch buildBodyParams() {
            PutBranch putBranch = new PutBranch();
            putBranch.name(this.name);
            putBranch.head(this.head);
            putBranch.updateSequenceId(this.updateSequenceId);
            putBranch.syncTime(this.syncTime);
            putBranch.references(this.references);
            putBranch.commitTime(this.commitTime);
            return putBranch;
        }

        /**
         * Execute upsertBranch request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public void execute() throws ApiException {
            PutBranch putBranch = buildBodyParams();
            upsertBranchWithHttpInfo(repo, branch, putBranch);
        }

        /**
         * Execute upsertBranch request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            PutBranch putBranch = buildBodyParams();
            return upsertBranchWithHttpInfo(repo, branch, putBranch);
        }

        /**
         * Execute upsertBranch request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<Void> _callback) throws ApiException {
            PutBranch putBranch = buildBodyParams();
            return upsertBranchAsync(repo, branch, putBranch, _callback);
        }
    }

    /**
     * Upsert branch
     * Create a new branch if it doesn&#39;t exist, or update the branch if it already exists.
     * @param repo The repository name (required)
     * @param branch The URL-encoded branch name (required)
     * @param putBranch  (required)
     * @return UpsertBranchRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Action succeeded </td><td>  -  </td></tr>
     </table>
     */
    public UpsertBranchRequestBuilder upsertBranch(String name, String head, Long syncTime, String repo, String branch) throws IllegalArgumentException {
        if (name == null) throw new IllegalArgumentException("\"name\" is required but got null");
            

        if (head == null) throw new IllegalArgumentException("\"head\" is required but got null");
            

        if (syncTime == null) throw new IllegalArgumentException("\"syncTime\" is required but got null");
        if (repo == null) throw new IllegalArgumentException("\"repo\" is required but got null");
            

        if (branch == null) throw new IllegalArgumentException("\"branch\" is required but got null");
            

        return new UpsertBranchRequestBuilder(name, head, syncTime, repo, branch);
    }
}
