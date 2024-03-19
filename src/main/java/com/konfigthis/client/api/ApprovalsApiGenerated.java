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


import com.konfigthis.client.model.ApprovalRequestResponse;
import com.konfigthis.client.model.CreateApprovalRequestRequest;
import com.konfigthis.client.model.CreateCopyFlagConfigApprovalRequestRequest;
import com.konfigthis.client.model.CreateFlagConfigApprovalRequestRequest;
import com.konfigthis.client.model.ExpandableApprovalRequestResponse;
import com.konfigthis.client.model.ExpandableApprovalRequestsResponse;
import com.konfigthis.client.model.FlagConfigApprovalRequestResponse;
import com.konfigthis.client.model.FlagConfigApprovalRequestsResponse;
import com.konfigthis.client.model.PostApprovalRequestApplyRequest;
import com.konfigthis.client.model.PostApprovalRequestReviewRequest;
import com.konfigthis.client.model.SourceFlag;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class ApprovalsApiGenerated {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public ApprovalsApiGenerated() throws IllegalArgumentException {
        this(Configuration.getDefaultApiClient());
    }

    public ApprovalsApiGenerated(ApiClient apiClient) throws IllegalArgumentException {
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

    private okhttp3.Call applyRequestFlagCall(String id, PostApprovalRequestApplyRequest postApprovalRequestApplyRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = postApprovalRequestApplyRequest;

        // create path and map variables
        String localVarPath = "/api/v2/approval-requests/{id}/apply"
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
    private okhttp3.Call applyRequestFlagValidateBeforeCall(String id, PostApprovalRequestApplyRequest postApprovalRequestApplyRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling applyRequestFlag(Async)");
        }

        // verify the required parameter 'postApprovalRequestApplyRequest' is set
        if (postApprovalRequestApplyRequest == null) {
            throw new ApiException("Missing the required parameter 'postApprovalRequestApplyRequest' when calling applyRequestFlag(Async)");
        }

        return applyRequestFlagCall(id, postApprovalRequestApplyRequest, _callback);

    }


    private ApiResponse<ApprovalRequestResponse> applyRequestFlagWithHttpInfo(String id, PostApprovalRequestApplyRequest postApprovalRequestApplyRequest) throws ApiException {
        okhttp3.Call localVarCall = applyRequestFlagValidateBeforeCall(id, postApprovalRequestApplyRequest, null);
        Type localVarReturnType = new TypeToken<ApprovalRequestResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call applyRequestFlagAsync(String id, PostApprovalRequestApplyRequest postApprovalRequestApplyRequest, final ApiCallback<ApprovalRequestResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = applyRequestFlagValidateBeforeCall(id, postApprovalRequestApplyRequest, _callback);
        Type localVarReturnType = new TypeToken<ApprovalRequestResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ApplyRequestFlagRequestBuilder {
        private final String id;
        private String comment;

        private ApplyRequestFlagRequestBuilder(String id) {
            this.id = id;
        }

        /**
         * Set comment
         * @param comment Optional comment about the approval request (optional)
         * @return ApplyRequestFlagRequestBuilder
         */
        public ApplyRequestFlagRequestBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }
        
        /**
         * Build call for applyRequestFlag
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request apply response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            PostApprovalRequestApplyRequest postApprovalRequestApplyRequest = buildBodyParams();
            return applyRequestFlagCall(id, postApprovalRequestApplyRequest, _callback);
        }

        private PostApprovalRequestApplyRequest buildBodyParams() {
            PostApprovalRequestApplyRequest postApprovalRequestApplyRequest = new PostApprovalRequestApplyRequest();
            postApprovalRequestApplyRequest.comment(this.comment);
            return postApprovalRequestApplyRequest;
        }

        /**
         * Execute applyRequestFlag request
         * @return ApprovalRequestResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request apply response </td><td>  -  </td></tr>
         </table>
         */
        public ApprovalRequestResponse execute() throws ApiException {
            PostApprovalRequestApplyRequest postApprovalRequestApplyRequest = buildBodyParams();
            ApiResponse<ApprovalRequestResponse> localVarResp = applyRequestFlagWithHttpInfo(id, postApprovalRequestApplyRequest);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute applyRequestFlag request with HTTP info returned
         * @return ApiResponse&lt;ApprovalRequestResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request apply response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ApprovalRequestResponse> executeWithHttpInfo() throws ApiException {
            PostApprovalRequestApplyRequest postApprovalRequestApplyRequest = buildBodyParams();
            return applyRequestFlagWithHttpInfo(id, postApprovalRequestApplyRequest);
        }

        /**
         * Execute applyRequestFlag request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request apply response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ApprovalRequestResponse> _callback) throws ApiException {
            PostApprovalRequestApplyRequest postApprovalRequestApplyRequest = buildBodyParams();
            return applyRequestFlagAsync(id, postApprovalRequestApplyRequest, _callback);
        }
    }

    /**
     * Apply approval request
     * Apply an approval request that has been approved.
     * @param id The feature flag approval request ID (required)
     * @param postApprovalRequestApplyRequest  (required)
     * @return ApplyRequestFlagRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Approval request apply response </td><td>  -  </td></tr>
     </table>
     */
    public ApplyRequestFlagRequestBuilder applyRequestFlag(String id) throws IllegalArgumentException {
        if (id == null) throw new IllegalArgumentException("\"id\" is required but got null");
            

        return new ApplyRequestFlagRequestBuilder(id);
    }
    private okhttp3.Call applyRequestFlag_0Call(String projectKey, String featureFlagKey, String environmentKey, String id, PostApprovalRequestApplyRequest postApprovalRequestApplyRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = postApprovalRequestApplyRequest;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id}/apply"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "featureFlagKey" + "}", localVarApiClient.escapeString(featureFlagKey.toString()))
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
    private okhttp3.Call applyRequestFlag_0ValidateBeforeCall(String projectKey, String featureFlagKey, String environmentKey, String id, PostApprovalRequestApplyRequest postApprovalRequestApplyRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling applyRequestFlag_0(Async)");
        }

        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling applyRequestFlag_0(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling applyRequestFlag_0(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling applyRequestFlag_0(Async)");
        }

        // verify the required parameter 'postApprovalRequestApplyRequest' is set
        if (postApprovalRequestApplyRequest == null) {
            throw new ApiException("Missing the required parameter 'postApprovalRequestApplyRequest' when calling applyRequestFlag_0(Async)");
        }

        return applyRequestFlag_0Call(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestApplyRequest, _callback);

    }


    private ApiResponse<FlagConfigApprovalRequestResponse> applyRequestFlag_0WithHttpInfo(String projectKey, String featureFlagKey, String environmentKey, String id, PostApprovalRequestApplyRequest postApprovalRequestApplyRequest) throws ApiException {
        okhttp3.Call localVarCall = applyRequestFlag_0ValidateBeforeCall(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestApplyRequest, null);
        Type localVarReturnType = new TypeToken<FlagConfigApprovalRequestResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call applyRequestFlag_0Async(String projectKey, String featureFlagKey, String environmentKey, String id, PostApprovalRequestApplyRequest postApprovalRequestApplyRequest, final ApiCallback<FlagConfigApprovalRequestResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = applyRequestFlag_0ValidateBeforeCall(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestApplyRequest, _callback);
        Type localVarReturnType = new TypeToken<FlagConfigApprovalRequestResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ApplyRequestFlag0RequestBuilder {
        private final String projectKey;
        private final String featureFlagKey;
        private final String environmentKey;
        private final String id;
        private String comment;

        private ApplyRequestFlag0RequestBuilder(String projectKey, String featureFlagKey, String environmentKey, String id) {
            this.projectKey = projectKey;
            this.featureFlagKey = featureFlagKey;
            this.environmentKey = environmentKey;
            this.id = id;
        }

        /**
         * Set comment
         * @param comment Optional comment about the approval request (optional)
         * @return ApplyRequestFlag0RequestBuilder
         */
        public ApplyRequestFlag0RequestBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }
        
        /**
         * Build call for applyRequestFlag_0
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request apply response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            PostApprovalRequestApplyRequest postApprovalRequestApplyRequest = buildBodyParams();
            return applyRequestFlag_0Call(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestApplyRequest, _callback);
        }

        private PostApprovalRequestApplyRequest buildBodyParams() {
            PostApprovalRequestApplyRequest postApprovalRequestApplyRequest = new PostApprovalRequestApplyRequest();
            postApprovalRequestApplyRequest.comment(this.comment);
            return postApprovalRequestApplyRequest;
        }

        /**
         * Execute applyRequestFlag_0 request
         * @return FlagConfigApprovalRequestResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request apply response </td><td>  -  </td></tr>
         </table>
         */
        public FlagConfigApprovalRequestResponse execute() throws ApiException {
            PostApprovalRequestApplyRequest postApprovalRequestApplyRequest = buildBodyParams();
            ApiResponse<FlagConfigApprovalRequestResponse> localVarResp = applyRequestFlag_0WithHttpInfo(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestApplyRequest);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute applyRequestFlag_0 request with HTTP info returned
         * @return ApiResponse&lt;FlagConfigApprovalRequestResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request apply response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<FlagConfigApprovalRequestResponse> executeWithHttpInfo() throws ApiException {
            PostApprovalRequestApplyRequest postApprovalRequestApplyRequest = buildBodyParams();
            return applyRequestFlag_0WithHttpInfo(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestApplyRequest);
        }

        /**
         * Execute applyRequestFlag_0 request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request apply response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<FlagConfigApprovalRequestResponse> _callback) throws ApiException {
            PostApprovalRequestApplyRequest postApprovalRequestApplyRequest = buildBodyParams();
            return applyRequestFlag_0Async(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestApplyRequest, _callback);
        }
    }

    /**
     * Apply approval request for a flag
     * Apply an approval request that has been approved.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param environmentKey The environment key (required)
     * @param id The feature flag approval request ID (required)
     * @param postApprovalRequestApplyRequest  (required)
     * @return ApplyRequestFlag0RequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Approval request apply response </td><td>  -  </td></tr>
     </table>
     */
    public ApplyRequestFlag0RequestBuilder applyRequestFlag_0(String projectKey, String featureFlagKey, String environmentKey, String id) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (featureFlagKey == null) throw new IllegalArgumentException("\"featureFlagKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (id == null) throw new IllegalArgumentException("\"id\" is required but got null");
            

        return new ApplyRequestFlag0RequestBuilder(projectKey, featureFlagKey, environmentKey, id);
    }
    private okhttp3.Call createFlagCopyRequestCall(String projectKey, String featureFlagKey, String environmentKey, CreateCopyFlagConfigApprovalRequestRequest createCopyFlagConfigApprovalRequestRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createCopyFlagConfigApprovalRequestRequest;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests-flag-copy"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "featureFlagKey" + "}", localVarApiClient.escapeString(featureFlagKey.toString()))
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
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createFlagCopyRequestValidateBeforeCall(String projectKey, String featureFlagKey, String environmentKey, CreateCopyFlagConfigApprovalRequestRequest createCopyFlagConfigApprovalRequestRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling createFlagCopyRequest(Async)");
        }

        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling createFlagCopyRequest(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling createFlagCopyRequest(Async)");
        }

        // verify the required parameter 'createCopyFlagConfigApprovalRequestRequest' is set
        if (createCopyFlagConfigApprovalRequestRequest == null) {
            throw new ApiException("Missing the required parameter 'createCopyFlagConfigApprovalRequestRequest' when calling createFlagCopyRequest(Async)");
        }

        return createFlagCopyRequestCall(projectKey, featureFlagKey, environmentKey, createCopyFlagConfigApprovalRequestRequest, _callback);

    }


    private ApiResponse<FlagConfigApprovalRequestResponse> createFlagCopyRequestWithHttpInfo(String projectKey, String featureFlagKey, String environmentKey, CreateCopyFlagConfigApprovalRequestRequest createCopyFlagConfigApprovalRequestRequest) throws ApiException {
        okhttp3.Call localVarCall = createFlagCopyRequestValidateBeforeCall(projectKey, featureFlagKey, environmentKey, createCopyFlagConfigApprovalRequestRequest, null);
        Type localVarReturnType = new TypeToken<FlagConfigApprovalRequestResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call createFlagCopyRequestAsync(String projectKey, String featureFlagKey, String environmentKey, CreateCopyFlagConfigApprovalRequestRequest createCopyFlagConfigApprovalRequestRequest, final ApiCallback<FlagConfigApprovalRequestResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = createFlagCopyRequestValidateBeforeCall(projectKey, featureFlagKey, environmentKey, createCopyFlagConfigApprovalRequestRequest, _callback);
        Type localVarReturnType = new TypeToken<FlagConfigApprovalRequestResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class CreateFlagCopyRequestRequestBuilder {
        private final String description;
        private final SourceFlag source;
        private final String projectKey;
        private final String featureFlagKey;
        private final String environmentKey;
        private String comment;
        private List<String> notifyMemberIds;
        private List<String> notifyTeamKeys;
        private List<String> includedActions;
        private List<String> excludedActions;

        private CreateFlagCopyRequestRequestBuilder(String description, SourceFlag source, String projectKey, String featureFlagKey, String environmentKey) {
            this.description = description;
            this.source = source;
            this.projectKey = projectKey;
            this.featureFlagKey = featureFlagKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Set comment
         * @param comment Optional comment describing the approval request (optional)
         * @return CreateFlagCopyRequestRequestBuilder
         */
        public CreateFlagCopyRequestRequestBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }
        
        /**
         * Set notifyMemberIds
         * @param notifyMemberIds An array of member IDs. These members are notified to review the approval request. (optional)
         * @return CreateFlagCopyRequestRequestBuilder
         */
        public CreateFlagCopyRequestRequestBuilder notifyMemberIds(List<String> notifyMemberIds) {
            this.notifyMemberIds = notifyMemberIds;
            return this;
        }
        
        /**
         * Set notifyTeamKeys
         * @param notifyTeamKeys An array of team keys. The members of these teams are notified to review the approval request. (optional)
         * @return CreateFlagCopyRequestRequestBuilder
         */
        public CreateFlagCopyRequestRequestBuilder notifyTeamKeys(List<String> notifyTeamKeys) {
            this.notifyTeamKeys = notifyTeamKeys;
            return this;
        }
        
        /**
         * Set includedActions
         * @param includedActions Optional list of the flag changes to copy from the source environment to the target environment. You may include either &lt;code&gt;includedActions&lt;/code&gt; or &lt;code&gt;excludedActions&lt;/code&gt;, but not both. If neither are included, then all flag changes will be copied. (optional)
         * @return CreateFlagCopyRequestRequestBuilder
         */
        public CreateFlagCopyRequestRequestBuilder includedActions(List<String> includedActions) {
            this.includedActions = includedActions;
            return this;
        }
        
        /**
         * Set excludedActions
         * @param excludedActions Optional list of the flag changes NOT to copy from the source environment to the target environment. You may include either &lt;code&gt;includedActions&lt;/code&gt; or &lt;code&gt;excludedActions&lt;/code&gt;, but not both. If neither are included, then all flag changes will be copied. (optional)
         * @return CreateFlagCopyRequestRequestBuilder
         */
        public CreateFlagCopyRequestRequestBuilder excludedActions(List<String> excludedActions) {
            this.excludedActions = excludedActions;
            return this;
        }
        
        /**
         * Build call for createFlagCopyRequest
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            CreateCopyFlagConfigApprovalRequestRequest createCopyFlagConfigApprovalRequestRequest = buildBodyParams();
            return createFlagCopyRequestCall(projectKey, featureFlagKey, environmentKey, createCopyFlagConfigApprovalRequestRequest, _callback);
        }

        private CreateCopyFlagConfigApprovalRequestRequest buildBodyParams() {
            CreateCopyFlagConfigApprovalRequestRequest createCopyFlagConfigApprovalRequestRequest = new CreateCopyFlagConfigApprovalRequestRequest();
            createCopyFlagConfigApprovalRequestRequest.description(this.description);
            createCopyFlagConfigApprovalRequestRequest.comment(this.comment);
            createCopyFlagConfigApprovalRequestRequest.notifyMemberIds(this.notifyMemberIds);
            createCopyFlagConfigApprovalRequestRequest.notifyTeamKeys(this.notifyTeamKeys);
            createCopyFlagConfigApprovalRequestRequest.source(this.source);
            if (this.includedActions != null)
            createCopyFlagConfigApprovalRequestRequest.includedActions(CreateCopyFlagConfigApprovalRequestRequest.IncludedActionsEnum.fromValue(this.includedActions));
            if (this.excludedActions != null)
            createCopyFlagConfigApprovalRequestRequest.excludedActions(CreateCopyFlagConfigApprovalRequestRequest.ExcludedActionsEnum.fromValue(this.excludedActions));
            return createCopyFlagConfigApprovalRequestRequest;
        }

        /**
         * Execute createFlagCopyRequest request
         * @return FlagConfigApprovalRequestResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public FlagConfigApprovalRequestResponse execute() throws ApiException {
            CreateCopyFlagConfigApprovalRequestRequest createCopyFlagConfigApprovalRequestRequest = buildBodyParams();
            ApiResponse<FlagConfigApprovalRequestResponse> localVarResp = createFlagCopyRequestWithHttpInfo(projectKey, featureFlagKey, environmentKey, createCopyFlagConfigApprovalRequestRequest);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute createFlagCopyRequest request with HTTP info returned
         * @return ApiResponse&lt;FlagConfigApprovalRequestResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<FlagConfigApprovalRequestResponse> executeWithHttpInfo() throws ApiException {
            CreateCopyFlagConfigApprovalRequestRequest createCopyFlagConfigApprovalRequestRequest = buildBodyParams();
            return createFlagCopyRequestWithHttpInfo(projectKey, featureFlagKey, environmentKey, createCopyFlagConfigApprovalRequestRequest);
        }

        /**
         * Execute createFlagCopyRequest request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<FlagConfigApprovalRequestResponse> _callback) throws ApiException {
            CreateCopyFlagConfigApprovalRequestRequest createCopyFlagConfigApprovalRequestRequest = buildBodyParams();
            return createFlagCopyRequestAsync(projectKey, featureFlagKey, environmentKey, createCopyFlagConfigApprovalRequestRequest, _callback);
        }
    }

    /**
     * Create approval request to copy flag configurations across environments
     * Create an approval request to copy a feature flag&#39;s configuration across environments.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param environmentKey The environment key for the target environment (required)
     * @param createCopyFlagConfigApprovalRequestRequest  (required)
     * @return CreateFlagCopyRequestRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Approval request response </td><td>  -  </td></tr>
     </table>
     */
    public CreateFlagCopyRequestRequestBuilder createFlagCopyRequest(String description, SourceFlag source, String projectKey, String featureFlagKey, String environmentKey) throws IllegalArgumentException {
        if (description == null) throw new IllegalArgumentException("\"description\" is required but got null");
            

        if (source == null) throw new IllegalArgumentException("\"source\" is required but got null");
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (featureFlagKey == null) throw new IllegalArgumentException("\"featureFlagKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new CreateFlagCopyRequestRequestBuilder(description, source, projectKey, featureFlagKey, environmentKey);
    }
    private okhttp3.Call createRequestFlagCall(CreateApprovalRequestRequest createApprovalRequestRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createApprovalRequestRequest;

        // create path and map variables
        String localVarPath = "/api/v2/approval-requests";

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
    private okhttp3.Call createRequestFlagValidateBeforeCall(CreateApprovalRequestRequest createApprovalRequestRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'createApprovalRequestRequest' is set
        if (createApprovalRequestRequest == null) {
            throw new ApiException("Missing the required parameter 'createApprovalRequestRequest' when calling createRequestFlag(Async)");
        }

        return createRequestFlagCall(createApprovalRequestRequest, _callback);

    }


    private ApiResponse<ApprovalRequestResponse> createRequestFlagWithHttpInfo(CreateApprovalRequestRequest createApprovalRequestRequest) throws ApiException {
        okhttp3.Call localVarCall = createRequestFlagValidateBeforeCall(createApprovalRequestRequest, null);
        Type localVarReturnType = new TypeToken<ApprovalRequestResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call createRequestFlagAsync(CreateApprovalRequestRequest createApprovalRequestRequest, final ApiCallback<ApprovalRequestResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = createRequestFlagValidateBeforeCall(createApprovalRequestRequest, _callback);
        Type localVarReturnType = new TypeToken<ApprovalRequestResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class CreateRequestFlagRequestBuilder {
        private final String description;
        private final String resourceId;
        private final List<Map<String, Object>> instructions;
        private String comment;
        private List<String> notifyMemberIds;
        private List<String> notifyTeamKeys;
        private Map<String, Object> integrationConfig;

        private CreateRequestFlagRequestBuilder(String description, String resourceId, List<Map<String, Object>> instructions) {
            this.description = description;
            this.resourceId = resourceId;
            this.instructions = instructions;
        }

        /**
         * Set comment
         * @param comment Optional comment describing the approval request (optional)
         * @return CreateRequestFlagRequestBuilder
         */
        public CreateRequestFlagRequestBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }
        
        /**
         * Set notifyMemberIds
         * @param notifyMemberIds An array of member IDs. These members are notified to review the approval request. (optional)
         * @return CreateRequestFlagRequestBuilder
         */
        public CreateRequestFlagRequestBuilder notifyMemberIds(List<String> notifyMemberIds) {
            this.notifyMemberIds = notifyMemberIds;
            return this;
        }
        
        /**
         * Set notifyTeamKeys
         * @param notifyTeamKeys An array of team keys. The members of these teams are notified to review the approval request. (optional)
         * @return CreateRequestFlagRequestBuilder
         */
        public CreateRequestFlagRequestBuilder notifyTeamKeys(List<String> notifyTeamKeys) {
            this.notifyTeamKeys = notifyTeamKeys;
            return this;
        }
        
        /**
         * Set integrationConfig
         * @param integrationConfig  (optional)
         * @return CreateRequestFlagRequestBuilder
         */
        public CreateRequestFlagRequestBuilder integrationConfig(Map<String, Object> integrationConfig) {
            this.integrationConfig = integrationConfig;
            return this;
        }
        
        /**
         * Build call for createRequestFlag
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            CreateApprovalRequestRequest createApprovalRequestRequest = buildBodyParams();
            return createRequestFlagCall(createApprovalRequestRequest, _callback);
        }

        private CreateApprovalRequestRequest buildBodyParams() {
            CreateApprovalRequestRequest createApprovalRequestRequest = new CreateApprovalRequestRequest();
            createApprovalRequestRequest.description(this.description);
            createApprovalRequestRequest.resourceId(this.resourceId);
            createApprovalRequestRequest.comment(this.comment);
            createApprovalRequestRequest.instructions(this.instructions);
            createApprovalRequestRequest.notifyMemberIds(this.notifyMemberIds);
            createApprovalRequestRequest.notifyTeamKeys(this.notifyTeamKeys);
            createApprovalRequestRequest.integrationConfig(this.integrationConfig);
            return createApprovalRequestRequest;
        }

        /**
         * Execute createRequestFlag request
         * @return ApprovalRequestResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public ApprovalRequestResponse execute() throws ApiException {
            CreateApprovalRequestRequest createApprovalRequestRequest = buildBodyParams();
            ApiResponse<ApprovalRequestResponse> localVarResp = createRequestFlagWithHttpInfo(createApprovalRequestRequest);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute createRequestFlag request with HTTP info returned
         * @return ApiResponse&lt;ApprovalRequestResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ApprovalRequestResponse> executeWithHttpInfo() throws ApiException {
            CreateApprovalRequestRequest createApprovalRequestRequest = buildBodyParams();
            return createRequestFlagWithHttpInfo(createApprovalRequestRequest);
        }

        /**
         * Execute createRequestFlag request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ApprovalRequestResponse> _callback) throws ApiException {
            CreateApprovalRequestRequest createApprovalRequestRequest = buildBodyParams();
            return createRequestFlagAsync(createApprovalRequestRequest, _callback);
        }
    }

    /**
     * Create approval request
     * Create an approval request.  This endpoint currently supports creating an approval request for a flag across all environments with the following instructions:  - &#x60;addVariation&#x60; - &#x60;removeVariation&#x60; - &#x60;updateVariation&#x60; - &#x60;updateDefaultVariation&#x60;  For details on using these instructions, read [Update feature flag](https://apidocs.launchdarkly.com).  To create an approval for a flag specific to an environment, use [Create approval request for a flag](https://apidocs.launchdarkly.com). 
     * @param createApprovalRequestRequest  (required)
     * @return CreateRequestFlagRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Approval request response </td><td>  -  </td></tr>
     </table>
     */
    public CreateRequestFlagRequestBuilder createRequestFlag(String description, String resourceId, List<Map<String, Object>> instructions) throws IllegalArgumentException {
        if (description == null) throw new IllegalArgumentException("\"description\" is required but got null");
            

        if (resourceId == null) throw new IllegalArgumentException("\"resourceId\" is required but got null");
            

        if (instructions == null) throw new IllegalArgumentException("\"instructions\" is required but got null");
        return new CreateRequestFlagRequestBuilder(description, resourceId, instructions);
    }
    private okhttp3.Call createRequestFlag_0Call(String projectKey, String featureFlagKey, String environmentKey, CreateFlagConfigApprovalRequestRequest createFlagConfigApprovalRequestRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createFlagConfigApprovalRequestRequest;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "featureFlagKey" + "}", localVarApiClient.escapeString(featureFlagKey.toString()))
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
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createRequestFlag_0ValidateBeforeCall(String projectKey, String featureFlagKey, String environmentKey, CreateFlagConfigApprovalRequestRequest createFlagConfigApprovalRequestRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling createRequestFlag_0(Async)");
        }

        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling createRequestFlag_0(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling createRequestFlag_0(Async)");
        }

        // verify the required parameter 'createFlagConfigApprovalRequestRequest' is set
        if (createFlagConfigApprovalRequestRequest == null) {
            throw new ApiException("Missing the required parameter 'createFlagConfigApprovalRequestRequest' when calling createRequestFlag_0(Async)");
        }

        return createRequestFlag_0Call(projectKey, featureFlagKey, environmentKey, createFlagConfigApprovalRequestRequest, _callback);

    }


    private ApiResponse<FlagConfigApprovalRequestResponse> createRequestFlag_0WithHttpInfo(String projectKey, String featureFlagKey, String environmentKey, CreateFlagConfigApprovalRequestRequest createFlagConfigApprovalRequestRequest) throws ApiException {
        okhttp3.Call localVarCall = createRequestFlag_0ValidateBeforeCall(projectKey, featureFlagKey, environmentKey, createFlagConfigApprovalRequestRequest, null);
        Type localVarReturnType = new TypeToken<FlagConfigApprovalRequestResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call createRequestFlag_0Async(String projectKey, String featureFlagKey, String environmentKey, CreateFlagConfigApprovalRequestRequest createFlagConfigApprovalRequestRequest, final ApiCallback<FlagConfigApprovalRequestResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = createRequestFlag_0ValidateBeforeCall(projectKey, featureFlagKey, environmentKey, createFlagConfigApprovalRequestRequest, _callback);
        Type localVarReturnType = new TypeToken<FlagConfigApprovalRequestResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class CreateRequestFlag0RequestBuilder {
        private final String description;
        private final List<Map<String, Object>> instructions;
        private final String projectKey;
        private final String featureFlagKey;
        private final String environmentKey;
        private String comment;
        private List<String> notifyMemberIds;
        private List<String> notifyTeamKeys;
        private Long executionDate;
        private String operatingOnId;
        private Map<String, Object> integrationConfig;

        private CreateRequestFlag0RequestBuilder(String description, List<Map<String, Object>> instructions, String projectKey, String featureFlagKey, String environmentKey) {
            this.description = description;
            this.instructions = instructions;
            this.projectKey = projectKey;
            this.featureFlagKey = featureFlagKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Set comment
         * @param comment Optional comment describing the approval request (optional)
         * @return CreateRequestFlag0RequestBuilder
         */
        public CreateRequestFlag0RequestBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }
        
        /**
         * Set notifyMemberIds
         * @param notifyMemberIds An array of member IDs. These members are notified to review the approval request. (optional)
         * @return CreateRequestFlag0RequestBuilder
         */
        public CreateRequestFlag0RequestBuilder notifyMemberIds(List<String> notifyMemberIds) {
            this.notifyMemberIds = notifyMemberIds;
            return this;
        }
        
        /**
         * Set notifyTeamKeys
         * @param notifyTeamKeys An array of team keys. The members of these teams are notified to review the approval request. (optional)
         * @return CreateRequestFlag0RequestBuilder
         */
        public CreateRequestFlag0RequestBuilder notifyTeamKeys(List<String> notifyTeamKeys) {
            this.notifyTeamKeys = notifyTeamKeys;
            return this;
        }
        
        /**
         * Set executionDate
         * @param executionDate  (optional)
         * @return CreateRequestFlag0RequestBuilder
         */
        public CreateRequestFlag0RequestBuilder executionDate(Long executionDate) {
            this.executionDate = executionDate;
            return this;
        }
        
        /**
         * Set operatingOnId
         * @param operatingOnId The ID of a scheduled change. Include this if your &lt;code&gt;instructions&lt;/code&gt; include editing or deleting a scheduled change. (optional)
         * @return CreateRequestFlag0RequestBuilder
         */
        public CreateRequestFlag0RequestBuilder operatingOnId(String operatingOnId) {
            this.operatingOnId = operatingOnId;
            return this;
        }
        
        /**
         * Set integrationConfig
         * @param integrationConfig  (optional)
         * @return CreateRequestFlag0RequestBuilder
         */
        public CreateRequestFlag0RequestBuilder integrationConfig(Map<String, Object> integrationConfig) {
            this.integrationConfig = integrationConfig;
            return this;
        }
        
        /**
         * Build call for createRequestFlag_0
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            CreateFlagConfigApprovalRequestRequest createFlagConfigApprovalRequestRequest = buildBodyParams();
            return createRequestFlag_0Call(projectKey, featureFlagKey, environmentKey, createFlagConfigApprovalRequestRequest, _callback);
        }

        private CreateFlagConfigApprovalRequestRequest buildBodyParams() {
            CreateFlagConfigApprovalRequestRequest createFlagConfigApprovalRequestRequest = new CreateFlagConfigApprovalRequestRequest();
            createFlagConfigApprovalRequestRequest.description(this.description);
            createFlagConfigApprovalRequestRequest.comment(this.comment);
            createFlagConfigApprovalRequestRequest.instructions(this.instructions);
            createFlagConfigApprovalRequestRequest.notifyMemberIds(this.notifyMemberIds);
            createFlagConfigApprovalRequestRequest.notifyTeamKeys(this.notifyTeamKeys);
            createFlagConfigApprovalRequestRequest.executionDate(this.executionDate);
            createFlagConfigApprovalRequestRequest.operatingOnId(this.operatingOnId);
            createFlagConfigApprovalRequestRequest.integrationConfig(this.integrationConfig);
            return createFlagConfigApprovalRequestRequest;
        }

        /**
         * Execute createRequestFlag_0 request
         * @return FlagConfigApprovalRequestResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public FlagConfigApprovalRequestResponse execute() throws ApiException {
            CreateFlagConfigApprovalRequestRequest createFlagConfigApprovalRequestRequest = buildBodyParams();
            ApiResponse<FlagConfigApprovalRequestResponse> localVarResp = createRequestFlag_0WithHttpInfo(projectKey, featureFlagKey, environmentKey, createFlagConfigApprovalRequestRequest);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute createRequestFlag_0 request with HTTP info returned
         * @return ApiResponse&lt;FlagConfigApprovalRequestResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<FlagConfigApprovalRequestResponse> executeWithHttpInfo() throws ApiException {
            CreateFlagConfigApprovalRequestRequest createFlagConfigApprovalRequestRequest = buildBodyParams();
            return createRequestFlag_0WithHttpInfo(projectKey, featureFlagKey, environmentKey, createFlagConfigApprovalRequestRequest);
        }

        /**
         * Execute createRequestFlag_0 request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<FlagConfigApprovalRequestResponse> _callback) throws ApiException {
            CreateFlagConfigApprovalRequestRequest createFlagConfigApprovalRequestRequest = buildBodyParams();
            return createRequestFlag_0Async(projectKey, featureFlagKey, environmentKey, createFlagConfigApprovalRequestRequest, _callback);
        }
    }

    /**
     * Create approval request for a flag
     * Create an approval request for a feature flag.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param environmentKey The environment key (required)
     * @param createFlagConfigApprovalRequestRequest  (required)
     * @return CreateRequestFlag0RequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Approval request response </td><td>  -  </td></tr>
     </table>
     */
    public CreateRequestFlag0RequestBuilder createRequestFlag_0(String description, List<Map<String, Object>> instructions, String projectKey, String featureFlagKey, String environmentKey) throws IllegalArgumentException {
        if (description == null) throw new IllegalArgumentException("\"description\" is required but got null");
            

        if (instructions == null) throw new IllegalArgumentException("\"instructions\" is required but got null");
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (featureFlagKey == null) throw new IllegalArgumentException("\"featureFlagKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new CreateRequestFlag0RequestBuilder(description, instructions, projectKey, featureFlagKey, environmentKey);
    }
    private okhttp3.Call deleteApprovalRequestFlagCall(String projectKey, String featureFlagKey, String environmentKey, String id, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "featureFlagKey" + "}", localVarApiClient.escapeString(featureFlagKey.toString()))
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
    private okhttp3.Call deleteApprovalRequestFlagValidateBeforeCall(String projectKey, String featureFlagKey, String environmentKey, String id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling deleteApprovalRequestFlag(Async)");
        }

        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling deleteApprovalRequestFlag(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling deleteApprovalRequestFlag(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling deleteApprovalRequestFlag(Async)");
        }

        return deleteApprovalRequestFlagCall(projectKey, featureFlagKey, environmentKey, id, _callback);

    }


    private ApiResponse<Void> deleteApprovalRequestFlagWithHttpInfo(String projectKey, String featureFlagKey, String environmentKey, String id) throws ApiException {
        okhttp3.Call localVarCall = deleteApprovalRequestFlagValidateBeforeCall(projectKey, featureFlagKey, environmentKey, id, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call deleteApprovalRequestFlagAsync(String projectKey, String featureFlagKey, String environmentKey, String id, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteApprovalRequestFlagValidateBeforeCall(projectKey, featureFlagKey, environmentKey, id, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class DeleteApprovalRequestFlagRequestBuilder {
        private final String projectKey;
        private final String featureFlagKey;
        private final String environmentKey;
        private final String id;

        private DeleteApprovalRequestFlagRequestBuilder(String projectKey, String featureFlagKey, String environmentKey, String id) {
            this.projectKey = projectKey;
            this.featureFlagKey = featureFlagKey;
            this.environmentKey = environmentKey;
            this.id = id;
        }

        /**
         * Build call for deleteApprovalRequestFlag
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
            return deleteApprovalRequestFlagCall(projectKey, featureFlagKey, environmentKey, id, _callback);
        }


        /**
         * Execute deleteApprovalRequestFlag request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public void execute() throws ApiException {
            deleteApprovalRequestFlagWithHttpInfo(projectKey, featureFlagKey, environmentKey, id);
        }

        /**
         * Execute deleteApprovalRequestFlag request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            return deleteApprovalRequestFlagWithHttpInfo(projectKey, featureFlagKey, environmentKey, id);
        }

        /**
         * Execute deleteApprovalRequestFlag request (asynchronously)
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
            return deleteApprovalRequestFlagAsync(projectKey, featureFlagKey, environmentKey, id, _callback);
        }
    }

    /**
     * Delete approval request for a flag
     * Delete an approval request for a feature flag.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param environmentKey The environment key (required)
     * @param id The feature flag approval request ID (required)
     * @return DeleteApprovalRequestFlagRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
     </table>
     */
    public DeleteApprovalRequestFlagRequestBuilder deleteApprovalRequestFlag(String projectKey, String featureFlagKey, String environmentKey, String id) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (featureFlagKey == null) throw new IllegalArgumentException("\"featureFlagKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (id == null) throw new IllegalArgumentException("\"id\" is required but got null");
            

        return new DeleteApprovalRequestFlagRequestBuilder(projectKey, featureFlagKey, environmentKey, id);
    }
    private okhttp3.Call deleteRequestCall(String id, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/approval-requests/{id}"
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
    private okhttp3.Call deleteRequestValidateBeforeCall(String id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling deleteRequest(Async)");
        }

        return deleteRequestCall(id, _callback);

    }


    private ApiResponse<Void> deleteRequestWithHttpInfo(String id) throws ApiException {
        okhttp3.Call localVarCall = deleteRequestValidateBeforeCall(id, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call deleteRequestAsync(String id, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteRequestValidateBeforeCall(id, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class DeleteRequestRequestBuilder {
        private final String id;

        private DeleteRequestRequestBuilder(String id) {
            this.id = id;
        }

        /**
         * Build call for deleteRequest
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
            return deleteRequestCall(id, _callback);
        }


        /**
         * Execute deleteRequest request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public void execute() throws ApiException {
            deleteRequestWithHttpInfo(id);
        }

        /**
         * Execute deleteRequest request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            return deleteRequestWithHttpInfo(id);
        }

        /**
         * Execute deleteRequest request (asynchronously)
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
            return deleteRequestAsync(id, _callback);
        }
    }

    /**
     * Delete approval request
     * Delete an approval request.
     * @param id The approval request ID (required)
     * @return DeleteRequestRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
     </table>
     */
    public DeleteRequestRequestBuilder deleteRequest(String id) throws IllegalArgumentException {
        if (id == null) throw new IllegalArgumentException("\"id\" is required but got null");
            

        return new DeleteRequestRequestBuilder(id);
    }
    private okhttp3.Call getRequestByIdCall(String id, String expand, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/approval-requests/{id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

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
    private okhttp3.Call getRequestByIdValidateBeforeCall(String id, String expand, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getRequestById(Async)");
        }

        return getRequestByIdCall(id, expand, _callback);

    }


    private ApiResponse<ExpandableApprovalRequestResponse> getRequestByIdWithHttpInfo(String id, String expand) throws ApiException {
        okhttp3.Call localVarCall = getRequestByIdValidateBeforeCall(id, expand, null);
        Type localVarReturnType = new TypeToken<ExpandableApprovalRequestResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getRequestByIdAsync(String id, String expand, final ApiCallback<ExpandableApprovalRequestResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = getRequestByIdValidateBeforeCall(id, expand, _callback);
        Type localVarReturnType = new TypeToken<ExpandableApprovalRequestResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetRequestByIdRequestBuilder {
        private final String id;
        private String expand;

        private GetRequestByIdRequestBuilder(String id) {
            this.id = id;
        }

        /**
         * Set expand
         * @param expand A comma-separated list of fields to expand in the response. Supported fields are explained above. (optional)
         * @return GetRequestByIdRequestBuilder
         */
        public GetRequestByIdRequestBuilder expand(String expand) {
            this.expand = expand;
            return this;
        }
        
        /**
         * Build call for getRequestById
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getRequestByIdCall(id, expand, _callback);
        }


        /**
         * Execute getRequestById request
         * @return ExpandableApprovalRequestResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public ExpandableApprovalRequestResponse execute() throws ApiException {
            ApiResponse<ExpandableApprovalRequestResponse> localVarResp = getRequestByIdWithHttpInfo(id, expand);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getRequestById request with HTTP info returned
         * @return ApiResponse&lt;ExpandableApprovalRequestResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ExpandableApprovalRequestResponse> executeWithHttpInfo() throws ApiException {
            return getRequestByIdWithHttpInfo(id, expand);
        }

        /**
         * Execute getRequestById request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ExpandableApprovalRequestResponse> _callback) throws ApiException {
            return getRequestByIdAsync(id, expand, _callback);
        }
    }

    /**
     * Get approval request
     * Get an approval request by approval request ID.  ### Expanding approval response  LaunchDarkly supports the &#x60;expand&#x60; query param to include additional fields in the response, with the following fields:  - &#x60;flag&#x60; includes the flag the approval request belongs to - &#x60;project&#x60; includes the project the approval request belongs to - &#x60;environments&#x60; includes the environments the approval request relates to  For example, &#x60;expand&#x3D;project,flag&#x60; includes the &#x60;project&#x60; and &#x60;flag&#x60; fields in the response. 
     * @param id The approval request ID (required)
     * @return GetRequestByIdRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Approval request response </td><td>  -  </td></tr>
     </table>
     */
    public GetRequestByIdRequestBuilder getRequestById(String id) throws IllegalArgumentException {
        if (id == null) throw new IllegalArgumentException("\"id\" is required but got null");
            

        return new GetRequestByIdRequestBuilder(id);
    }
    private okhttp3.Call listCall(String filter, String expand, Long limit, Long offset, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/approval-requests";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
        }

        if (expand != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("expand", expand));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
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
    private okhttp3.Call listValidateBeforeCall(String filter, String expand, Long limit, Long offset, final ApiCallback _callback) throws ApiException {
        return listCall(filter, expand, limit, offset, _callback);

    }


    private ApiResponse<ExpandableApprovalRequestsResponse> listWithHttpInfo(String filter, String expand, Long limit, Long offset) throws ApiException {
        okhttp3.Call localVarCall = listValidateBeforeCall(filter, expand, limit, offset, null);
        Type localVarReturnType = new TypeToken<ExpandableApprovalRequestsResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call listAsync(String filter, String expand, Long limit, Long offset, final ApiCallback<ExpandableApprovalRequestsResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = listValidateBeforeCall(filter, expand, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<ExpandableApprovalRequestsResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ListRequestBuilder {
        private String filter;
        private String expand;
        private Long limit;
        private Long offset;

        private ListRequestBuilder() {
        }

        /**
         * Set filter
         * @param filter A comma-separated list of filters. Each filter is of the form &#x60;field operator value&#x60;. Supported fields are explained above. (optional)
         * @return ListRequestBuilder
         */
        public ListRequestBuilder filter(String filter) {
            this.filter = filter;
            return this;
        }
        
        /**
         * Set expand
         * @param expand A comma-separated list of fields to expand in the response. Supported fields are explained above. (optional)
         * @return ListRequestBuilder
         */
        public ListRequestBuilder expand(String expand) {
            this.expand = expand;
            return this;
        }
        
        /**
         * Set limit
         * @param limit The number of approvals to return. Defaults to 20. Maximum limit is 200. (optional)
         * @return ListRequestBuilder
         */
        public ListRequestBuilder limit(Long limit) {
            this.limit = limit;
            return this;
        }
        
        /**
         * Set offset
         * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
         * @return ListRequestBuilder
         */
        public ListRequestBuilder offset(Long offset) {
            this.offset = offset;
            return this;
        }
        
        /**
         * Build call for list
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return listCall(filter, expand, limit, offset, _callback);
        }


        /**
         * Execute list request
         * @return ExpandableApprovalRequestsResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request collection response </td><td>  -  </td></tr>
         </table>
         */
        public ExpandableApprovalRequestsResponse execute() throws ApiException {
            ApiResponse<ExpandableApprovalRequestsResponse> localVarResp = listWithHttpInfo(filter, expand, limit, offset);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute list request with HTTP info returned
         * @return ApiResponse&lt;ExpandableApprovalRequestsResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ExpandableApprovalRequestsResponse> executeWithHttpInfo() throws ApiException {
            return listWithHttpInfo(filter, expand, limit, offset);
        }

        /**
         * Execute list request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ExpandableApprovalRequestsResponse> _callback) throws ApiException {
            return listAsync(filter, expand, limit, offset, _callback);
        }
    }

    /**
     * List approval requests
     * Get all approval requests.  ### Filtering approvals  LaunchDarkly supports the &#x60;filter&#x60; query param for filtering, with the following fields:  - &#x60;notifyMemberIds&#x60; filters for only approvals that are assigned to a member in the specified list. For example: &#x60;filter&#x3D;notifyMemberIds anyOf [\&quot;memberId1\&quot;, \&quot;memberId2\&quot;]&#x60;. - &#x60;requestorId&#x60; filters for only approvals that correspond to the ID of the member who requested the approval. For example: &#x60;filter&#x3D;requestorId equals 457034721476302714390214&#x60;. - &#x60;resourceId&#x60; filters for only approvals that correspond to the the specified resource identifier. For example: &#x60;filter&#x3D;resourceId equals proj/my-project:env/my-environment:flag/my-flag&#x60;. - &#x60;reviewStatus&#x60; filters for only approvals which correspond to the review status in the specified list. The possible values are &#x60;approved&#x60;, &#x60;declined&#x60;, and &#x60;pending&#x60;. For example: &#x60;filter&#x3D;reviewStatus anyOf [\&quot;pending\&quot;, \&quot;approved\&quot;]&#x60;. - &#x60;status&#x60; filters for only approvals which correspond to the status in the specified list. The possible values are &#x60;pending&#x60;, &#x60;scheduled&#x60;, &#x60;failed&#x60;, and &#x60;completed&#x60;. For example: &#x60;filter&#x3D;status anyOf [\&quot;pending\&quot;, \&quot;scheduled\&quot;]&#x60;.  You can also apply multiple filters at once. For example, setting &#x60;filter&#x3D;projectKey equals my-project, reviewStatus anyOf [\&quot;pending\&quot;,\&quot;approved\&quot;]&#x60; matches approval requests which correspond to the &#x60;my-project&#x60; project key, and a review status of either &#x60;pending&#x60; or &#x60;approved&#x60;.  ### Expanding approval response  LaunchDarkly supports the &#x60;expand&#x60; query param to include additional fields in the response, with the following fields:  - &#x60;flag&#x60; includes the flag the approval request belongs to - &#x60;project&#x60; includes the project the approval request belongs to - &#x60;environments&#x60; includes the environments the approval request relates to  For example, &#x60;expand&#x3D;project,flag&#x60; includes the &#x60;project&#x60; and &#x60;flag&#x60; fields in the response. 
     * @return ListRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Approval request collection response </td><td>  -  </td></tr>
     </table>
     */
    public ListRequestBuilder list() throws IllegalArgumentException {
        return new ListRequestBuilder();
    }
    private okhttp3.Call listRequestsForFlagCall(String projectKey, String featureFlagKey, String environmentKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "featureFlagKey" + "}", localVarApiClient.escapeString(featureFlagKey.toString()))
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
    private okhttp3.Call listRequestsForFlagValidateBeforeCall(String projectKey, String featureFlagKey, String environmentKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling listRequestsForFlag(Async)");
        }

        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling listRequestsForFlag(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling listRequestsForFlag(Async)");
        }

        return listRequestsForFlagCall(projectKey, featureFlagKey, environmentKey, _callback);

    }


    private ApiResponse<FlagConfigApprovalRequestsResponse> listRequestsForFlagWithHttpInfo(String projectKey, String featureFlagKey, String environmentKey) throws ApiException {
        okhttp3.Call localVarCall = listRequestsForFlagValidateBeforeCall(projectKey, featureFlagKey, environmentKey, null);
        Type localVarReturnType = new TypeToken<FlagConfigApprovalRequestsResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call listRequestsForFlagAsync(String projectKey, String featureFlagKey, String environmentKey, final ApiCallback<FlagConfigApprovalRequestsResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = listRequestsForFlagValidateBeforeCall(projectKey, featureFlagKey, environmentKey, _callback);
        Type localVarReturnType = new TypeToken<FlagConfigApprovalRequestsResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ListRequestsForFlagRequestBuilder {
        private final String projectKey;
        private final String featureFlagKey;
        private final String environmentKey;

        private ListRequestsForFlagRequestBuilder(String projectKey, String featureFlagKey, String environmentKey) {
            this.projectKey = projectKey;
            this.featureFlagKey = featureFlagKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Build call for listRequestsForFlag
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return listRequestsForFlagCall(projectKey, featureFlagKey, environmentKey, _callback);
        }


        /**
         * Execute listRequestsForFlag request
         * @return FlagConfigApprovalRequestsResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request collection response </td><td>  -  </td></tr>
         </table>
         */
        public FlagConfigApprovalRequestsResponse execute() throws ApiException {
            ApiResponse<FlagConfigApprovalRequestsResponse> localVarResp = listRequestsForFlagWithHttpInfo(projectKey, featureFlagKey, environmentKey);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute listRequestsForFlag request with HTTP info returned
         * @return ApiResponse&lt;FlagConfigApprovalRequestsResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<FlagConfigApprovalRequestsResponse> executeWithHttpInfo() throws ApiException {
            return listRequestsForFlagWithHttpInfo(projectKey, featureFlagKey, environmentKey);
        }

        /**
         * Execute listRequestsForFlag request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<FlagConfigApprovalRequestsResponse> _callback) throws ApiException {
            return listRequestsForFlagAsync(projectKey, featureFlagKey, environmentKey, _callback);
        }
    }

    /**
     * List approval requests for a flag
     * Get all approval requests for a feature flag.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param environmentKey The environment key (required)
     * @return ListRequestsForFlagRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Approval request collection response </td><td>  -  </td></tr>
     </table>
     */
    public ListRequestsForFlagRequestBuilder listRequestsForFlag(String projectKey, String featureFlagKey, String environmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (featureFlagKey == null) throw new IllegalArgumentException("\"featureFlagKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new ListRequestsForFlagRequestBuilder(projectKey, featureFlagKey, environmentKey);
    }
    private okhttp3.Call reviewFlagRequestCall(String projectKey, String featureFlagKey, String environmentKey, String id, PostApprovalRequestReviewRequest postApprovalRequestReviewRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = postApprovalRequestReviewRequest;

        // create path and map variables
        String localVarPath = "/api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id}/reviews"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "featureFlagKey" + "}", localVarApiClient.escapeString(featureFlagKey.toString()))
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
    private okhttp3.Call reviewFlagRequestValidateBeforeCall(String projectKey, String featureFlagKey, String environmentKey, String id, PostApprovalRequestReviewRequest postApprovalRequestReviewRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling reviewFlagRequest(Async)");
        }

        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling reviewFlagRequest(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling reviewFlagRequest(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling reviewFlagRequest(Async)");
        }

        // verify the required parameter 'postApprovalRequestReviewRequest' is set
        if (postApprovalRequestReviewRequest == null) {
            throw new ApiException("Missing the required parameter 'postApprovalRequestReviewRequest' when calling reviewFlagRequest(Async)");
        }

        return reviewFlagRequestCall(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestReviewRequest, _callback);

    }


    private ApiResponse<FlagConfigApprovalRequestResponse> reviewFlagRequestWithHttpInfo(String projectKey, String featureFlagKey, String environmentKey, String id, PostApprovalRequestReviewRequest postApprovalRequestReviewRequest) throws ApiException {
        okhttp3.Call localVarCall = reviewFlagRequestValidateBeforeCall(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestReviewRequest, null);
        Type localVarReturnType = new TypeToken<FlagConfigApprovalRequestResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call reviewFlagRequestAsync(String projectKey, String featureFlagKey, String environmentKey, String id, PostApprovalRequestReviewRequest postApprovalRequestReviewRequest, final ApiCallback<FlagConfigApprovalRequestResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = reviewFlagRequestValidateBeforeCall(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestReviewRequest, _callback);
        Type localVarReturnType = new TypeToken<FlagConfigApprovalRequestResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ReviewFlagRequestRequestBuilder {
        private final String projectKey;
        private final String featureFlagKey;
        private final String environmentKey;
        private final String id;
        private String kind;
        private String comment;

        private ReviewFlagRequestRequestBuilder(String projectKey, String featureFlagKey, String environmentKey, String id) {
            this.projectKey = projectKey;
            this.featureFlagKey = featureFlagKey;
            this.environmentKey = environmentKey;
            this.id = id;
        }

        /**
         * Set kind
         * @param kind The type of review for this approval request (optional)
         * @return ReviewFlagRequestRequestBuilder
         */
        public ReviewFlagRequestRequestBuilder kind(String kind) {
            this.kind = kind;
            return this;
        }
        
        /**
         * Set comment
         * @param comment Optional comment about the approval request (optional)
         * @return ReviewFlagRequestRequestBuilder
         */
        public ReviewFlagRequestRequestBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }
        
        /**
         * Build call for reviewFlagRequest
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request review response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            PostApprovalRequestReviewRequest postApprovalRequestReviewRequest = buildBodyParams();
            return reviewFlagRequestCall(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestReviewRequest, _callback);
        }

        private PostApprovalRequestReviewRequest buildBodyParams() {
            PostApprovalRequestReviewRequest postApprovalRequestReviewRequest = new PostApprovalRequestReviewRequest();
            if (this.kind != null)
            postApprovalRequestReviewRequest.kind(PostApprovalRequestReviewRequest.KindEnum.fromValue(this.kind));
            postApprovalRequestReviewRequest.comment(this.comment);
            return postApprovalRequestReviewRequest;
        }

        /**
         * Execute reviewFlagRequest request
         * @return FlagConfigApprovalRequestResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request review response </td><td>  -  </td></tr>
         </table>
         */
        public FlagConfigApprovalRequestResponse execute() throws ApiException {
            PostApprovalRequestReviewRequest postApprovalRequestReviewRequest = buildBodyParams();
            ApiResponse<FlagConfigApprovalRequestResponse> localVarResp = reviewFlagRequestWithHttpInfo(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestReviewRequest);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute reviewFlagRequest request with HTTP info returned
         * @return ApiResponse&lt;FlagConfigApprovalRequestResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request review response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<FlagConfigApprovalRequestResponse> executeWithHttpInfo() throws ApiException {
            PostApprovalRequestReviewRequest postApprovalRequestReviewRequest = buildBodyParams();
            return reviewFlagRequestWithHttpInfo(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestReviewRequest);
        }

        /**
         * Execute reviewFlagRequest request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request review response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<FlagConfigApprovalRequestResponse> _callback) throws ApiException {
            PostApprovalRequestReviewRequest postApprovalRequestReviewRequest = buildBodyParams();
            return reviewFlagRequestAsync(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestReviewRequest, _callback);
        }
    }

    /**
     * Review approval request for a flag
     * Review an approval request by approving or denying changes.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param environmentKey The environment key (required)
     * @param id The feature flag approval request ID (required)
     * @param postApprovalRequestReviewRequest  (required)
     * @return ReviewFlagRequestRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Approval request review response </td><td>  -  </td></tr>
     </table>
     */
    public ReviewFlagRequestRequestBuilder reviewFlagRequest(String projectKey, String featureFlagKey, String environmentKey, String id) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (featureFlagKey == null) throw new IllegalArgumentException("\"featureFlagKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (id == null) throw new IllegalArgumentException("\"id\" is required but got null");
            

        return new ReviewFlagRequestRequestBuilder(projectKey, featureFlagKey, environmentKey, id);
    }
    private okhttp3.Call reviewRequestCall(String id, PostApprovalRequestReviewRequest postApprovalRequestReviewRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = postApprovalRequestReviewRequest;

        // create path and map variables
        String localVarPath = "/api/v2/approval-requests/{id}/reviews"
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
    private okhttp3.Call reviewRequestValidateBeforeCall(String id, PostApprovalRequestReviewRequest postApprovalRequestReviewRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling reviewRequest(Async)");
        }

        // verify the required parameter 'postApprovalRequestReviewRequest' is set
        if (postApprovalRequestReviewRequest == null) {
            throw new ApiException("Missing the required parameter 'postApprovalRequestReviewRequest' when calling reviewRequest(Async)");
        }

        return reviewRequestCall(id, postApprovalRequestReviewRequest, _callback);

    }


    private ApiResponse<ApprovalRequestResponse> reviewRequestWithHttpInfo(String id, PostApprovalRequestReviewRequest postApprovalRequestReviewRequest) throws ApiException {
        okhttp3.Call localVarCall = reviewRequestValidateBeforeCall(id, postApprovalRequestReviewRequest, null);
        Type localVarReturnType = new TypeToken<ApprovalRequestResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call reviewRequestAsync(String id, PostApprovalRequestReviewRequest postApprovalRequestReviewRequest, final ApiCallback<ApprovalRequestResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = reviewRequestValidateBeforeCall(id, postApprovalRequestReviewRequest, _callback);
        Type localVarReturnType = new TypeToken<ApprovalRequestResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ReviewRequestRequestBuilder {
        private final String id;
        private String kind;
        private String comment;

        private ReviewRequestRequestBuilder(String id) {
            this.id = id;
        }

        /**
         * Set kind
         * @param kind The type of review for this approval request (optional)
         * @return ReviewRequestRequestBuilder
         */
        public ReviewRequestRequestBuilder kind(String kind) {
            this.kind = kind;
            return this;
        }
        
        /**
         * Set comment
         * @param comment Optional comment about the approval request (optional)
         * @return ReviewRequestRequestBuilder
         */
        public ReviewRequestRequestBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }
        
        /**
         * Build call for reviewRequest
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request review response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            PostApprovalRequestReviewRequest postApprovalRequestReviewRequest = buildBodyParams();
            return reviewRequestCall(id, postApprovalRequestReviewRequest, _callback);
        }

        private PostApprovalRequestReviewRequest buildBodyParams() {
            PostApprovalRequestReviewRequest postApprovalRequestReviewRequest = new PostApprovalRequestReviewRequest();
            if (this.kind != null)
            postApprovalRequestReviewRequest.kind(PostApprovalRequestReviewRequest.KindEnum.fromValue(this.kind));
            postApprovalRequestReviewRequest.comment(this.comment);
            return postApprovalRequestReviewRequest;
        }

        /**
         * Execute reviewRequest request
         * @return ApprovalRequestResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request review response </td><td>  -  </td></tr>
         </table>
         */
        public ApprovalRequestResponse execute() throws ApiException {
            PostApprovalRequestReviewRequest postApprovalRequestReviewRequest = buildBodyParams();
            ApiResponse<ApprovalRequestResponse> localVarResp = reviewRequestWithHttpInfo(id, postApprovalRequestReviewRequest);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute reviewRequest request with HTTP info returned
         * @return ApiResponse&lt;ApprovalRequestResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request review response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ApprovalRequestResponse> executeWithHttpInfo() throws ApiException {
            PostApprovalRequestReviewRequest postApprovalRequestReviewRequest = buildBodyParams();
            return reviewRequestWithHttpInfo(id, postApprovalRequestReviewRequest);
        }

        /**
         * Execute reviewRequest request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request review response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ApprovalRequestResponse> _callback) throws ApiException {
            PostApprovalRequestReviewRequest postApprovalRequestReviewRequest = buildBodyParams();
            return reviewRequestAsync(id, postApprovalRequestReviewRequest, _callback);
        }
    }

    /**
     * Review approval request
     * Review an approval request by approving or denying changes.
     * @param id The approval request ID (required)
     * @param postApprovalRequestReviewRequest  (required)
     * @return ReviewRequestRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Approval request review response </td><td>  -  </td></tr>
     </table>
     */
    public ReviewRequestRequestBuilder reviewRequest(String id) throws IllegalArgumentException {
        if (id == null) throw new IllegalArgumentException("\"id\" is required but got null");
            

        return new ReviewRequestRequestBuilder(id);
    }
    private okhttp3.Call singleRequestCall(String projectKey, String featureFlagKey, String environmentKey, String id, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "featureFlagKey" + "}", localVarApiClient.escapeString(featureFlagKey.toString()))
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
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call singleRequestValidateBeforeCall(String projectKey, String featureFlagKey, String environmentKey, String id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling singleRequest(Async)");
        }

        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling singleRequest(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling singleRequest(Async)");
        }

        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling singleRequest(Async)");
        }

        return singleRequestCall(projectKey, featureFlagKey, environmentKey, id, _callback);

    }


    private ApiResponse<FlagConfigApprovalRequestResponse> singleRequestWithHttpInfo(String projectKey, String featureFlagKey, String environmentKey, String id) throws ApiException {
        okhttp3.Call localVarCall = singleRequestValidateBeforeCall(projectKey, featureFlagKey, environmentKey, id, null);
        Type localVarReturnType = new TypeToken<FlagConfigApprovalRequestResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call singleRequestAsync(String projectKey, String featureFlagKey, String environmentKey, String id, final ApiCallback<FlagConfigApprovalRequestResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = singleRequestValidateBeforeCall(projectKey, featureFlagKey, environmentKey, id, _callback);
        Type localVarReturnType = new TypeToken<FlagConfigApprovalRequestResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class SingleRequestRequestBuilder {
        private final String projectKey;
        private final String featureFlagKey;
        private final String environmentKey;
        private final String id;

        private SingleRequestRequestBuilder(String projectKey, String featureFlagKey, String environmentKey, String id) {
            this.projectKey = projectKey;
            this.featureFlagKey = featureFlagKey;
            this.environmentKey = environmentKey;
            this.id = id;
        }

        /**
         * Build call for singleRequest
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return singleRequestCall(projectKey, featureFlagKey, environmentKey, id, _callback);
        }


        /**
         * Execute singleRequest request
         * @return FlagConfigApprovalRequestResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public FlagConfigApprovalRequestResponse execute() throws ApiException {
            ApiResponse<FlagConfigApprovalRequestResponse> localVarResp = singleRequestWithHttpInfo(projectKey, featureFlagKey, environmentKey, id);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute singleRequest request with HTTP info returned
         * @return ApiResponse&lt;FlagConfigApprovalRequestResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<FlagConfigApprovalRequestResponse> executeWithHttpInfo() throws ApiException {
            return singleRequestWithHttpInfo(projectKey, featureFlagKey, environmentKey, id);
        }

        /**
         * Execute singleRequest request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Approval request response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<FlagConfigApprovalRequestResponse> _callback) throws ApiException {
            return singleRequestAsync(projectKey, featureFlagKey, environmentKey, id, _callback);
        }
    }

    /**
     * Get approval request for a flag
     * Get a single approval request for a feature flag.
     * @param projectKey The project key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param environmentKey The environment key (required)
     * @param id The feature flag approval request ID (required)
     * @return SingleRequestRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Approval request response </td><td>  -  </td></tr>
     </table>
     */
    public SingleRequestRequestBuilder singleRequest(String projectKey, String featureFlagKey, String environmentKey, String id) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (featureFlagKey == null) throw new IllegalArgumentException("\"featureFlagKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (id == null) throw new IllegalArgumentException("\"id\" is required but got null");
            

        return new SingleRequestRequestBuilder(projectKey, featureFlagKey, environmentKey, id);
    }
}
