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


import com.konfigthis.client.model.BigSegmentTarget;
import com.konfigthis.client.model.ContextInstanceSegmentMemberships;
import com.konfigthis.client.model.ExpiringTargetGetResponse;
import com.konfigthis.client.model.ExpiringTargetPatchResponse;
import com.konfigthis.client.model.ExpiringUserTargetGetResponse;
import com.konfigthis.client.model.ExpiringUserTargetPatchResponse;
import com.konfigthis.client.model.PatchOperation;
import com.konfigthis.client.model.PatchSegmentExpiringTargetInputRep;
import com.konfigthis.client.model.PatchSegmentExpiringTargetInstruction;
import com.konfigthis.client.model.PatchSegmentInstruction;
import com.konfigthis.client.model.PatchSegmentRequest;
import com.konfigthis.client.model.PatchWithComment;
import com.konfigthis.client.model.SegmentBody;
import com.konfigthis.client.model.SegmentUserList;
import com.konfigthis.client.model.SegmentUserState;
import com.konfigthis.client.model.UserSegment;
import com.konfigthis.client.model.UserSegments;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class SegmentsApiGenerated {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public SegmentsApiGenerated() throws IllegalArgumentException {
        this(Configuration.getDefaultApiClient());
    }

    public SegmentsApiGenerated(ApiClient apiClient) throws IllegalArgumentException {
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

    private okhttp3.Call createSegmentCall(String projectKey, String environmentKey, SegmentBody segmentBody, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = segmentBody;

        // create path and map variables
        String localVarPath = "/api/v2/segments/{projectKey}/{environmentKey}"
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
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createSegmentValidateBeforeCall(String projectKey, String environmentKey, SegmentBody segmentBody, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling createSegment(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling createSegment(Async)");
        }

        // verify the required parameter 'segmentBody' is set
        if (segmentBody == null) {
            throw new ApiException("Missing the required parameter 'segmentBody' when calling createSegment(Async)");
        }

        return createSegmentCall(projectKey, environmentKey, segmentBody, _callback);

    }


    private ApiResponse<UserSegment> createSegmentWithHttpInfo(String projectKey, String environmentKey, SegmentBody segmentBody) throws ApiException {
        okhttp3.Call localVarCall = createSegmentValidateBeforeCall(projectKey, environmentKey, segmentBody, null);
        Type localVarReturnType = new TypeToken<UserSegment>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call createSegmentAsync(String projectKey, String environmentKey, SegmentBody segmentBody, final ApiCallback<UserSegment> _callback) throws ApiException {

        okhttp3.Call localVarCall = createSegmentValidateBeforeCall(projectKey, environmentKey, segmentBody, _callback);
        Type localVarReturnType = new TypeToken<UserSegment>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class CreateSegmentRequestBuilder {
        private final String name;
        private final String key;
        private final String projectKey;
        private final String environmentKey;
        private List<String> tags;
        private String description;
        private Boolean unbounded;
        private String unboundedContextKind;

        private CreateSegmentRequestBuilder(String name, String key, String projectKey, String environmentKey) {
            this.name = name;
            this.key = key;
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Set tags
         * @param tags Tags for the segment (optional)
         * @return CreateSegmentRequestBuilder
         */
        public CreateSegmentRequestBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }
        
        /**
         * Set description
         * @param description A description of the segment&#39;s purpose (optional)
         * @return CreateSegmentRequestBuilder
         */
        public CreateSegmentRequestBuilder description(String description) {
            this.description = description;
            return this;
        }
        
        /**
         * Set unbounded
         * @param unbounded Whether to create a standard segment (&lt;code&gt;false&lt;/code&gt;) or a big segment (&lt;code&gt;true&lt;/code&gt;). Standard segments include rule-based and smaller list-based segments. Big segments include larger list-based segments and synced segments. Only use a big segment if you need to add more than 15,000 individual targets. (optional)
         * @return CreateSegmentRequestBuilder
         */
        public CreateSegmentRequestBuilder unbounded(Boolean unbounded) {
            this.unbounded = unbounded;
            return this;
        }
        
        /**
         * Set unboundedContextKind
         * @param unboundedContextKind For big segments, the targeted context kind. (optional)
         * @return CreateSegmentRequestBuilder
         */
        public CreateSegmentRequestBuilder unboundedContextKind(String unboundedContextKind) {
            this.unboundedContextKind = unboundedContextKind;
            return this;
        }
        
        /**
         * Build call for createSegment
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Segment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            SegmentBody segmentBody = buildBodyParams();
            return createSegmentCall(projectKey, environmentKey, segmentBody, _callback);
        }

        private SegmentBody buildBodyParams() {
            SegmentBody segmentBody = new SegmentBody();
            segmentBody.tags(this.tags);
            segmentBody.description(this.description);
            segmentBody.name(this.name);
            segmentBody.key(this.key);
            segmentBody.unbounded(this.unbounded);
            segmentBody.unboundedContextKind(this.unboundedContextKind);
            return segmentBody;
        }

        /**
         * Execute createSegment request
         * @return UserSegment
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Segment response </td><td>  -  </td></tr>
         </table>
         */
        public UserSegment execute() throws ApiException {
            SegmentBody segmentBody = buildBodyParams();
            ApiResponse<UserSegment> localVarResp = createSegmentWithHttpInfo(projectKey, environmentKey, segmentBody);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute createSegment request with HTTP info returned
         * @return ApiResponse&lt;UserSegment&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Segment response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<UserSegment> executeWithHttpInfo() throws ApiException {
            SegmentBody segmentBody = buildBodyParams();
            return createSegmentWithHttpInfo(projectKey, environmentKey, segmentBody);
        }

        /**
         * Execute createSegment request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 201 </td><td> Segment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<UserSegment> _callback) throws ApiException {
            SegmentBody segmentBody = buildBodyParams();
            return createSegmentAsync(projectKey, environmentKey, segmentBody, _callback);
        }
    }

    /**
     * Create segment
     * Create a new segment.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param segmentBody  (required)
     * @return CreateSegmentRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Segment response </td><td>  -  </td></tr>
     </table>
     */
    public CreateSegmentRequestBuilder createSegment(String name, String key, String projectKey, String environmentKey) throws IllegalArgumentException {
        if (name == null) throw new IllegalArgumentException("\"name\" is required but got null");
            

        if (key == null) throw new IllegalArgumentException("\"key\" is required but got null");
            

        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new CreateSegmentRequestBuilder(name, key, projectKey, environmentKey);
    }
    private okhttp3.Call evaluateSegmentMembershipsCall(String projectKey, String environmentKey, Map<String, Object> requestBody, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/projects/{projectKey}/environments/{environmentKey}/segments/evaluate"
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
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call evaluateSegmentMembershipsValidateBeforeCall(String projectKey, String environmentKey, Map<String, Object> requestBody, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling evaluateSegmentMemberships(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling evaluateSegmentMemberships(Async)");
        }

        // verify the required parameter 'requestBody' is set
        if (requestBody == null) {
            throw new ApiException("Missing the required parameter 'requestBody' when calling evaluateSegmentMemberships(Async)");
        }

        return evaluateSegmentMembershipsCall(projectKey, environmentKey, requestBody, _callback);

    }


    private ApiResponse<ContextInstanceSegmentMemberships> evaluateSegmentMembershipsWithHttpInfo(String projectKey, String environmentKey, Map<String, Object> requestBody) throws ApiException {
        okhttp3.Call localVarCall = evaluateSegmentMembershipsValidateBeforeCall(projectKey, environmentKey, requestBody, null);
        Type localVarReturnType = new TypeToken<ContextInstanceSegmentMemberships>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call evaluateSegmentMembershipsAsync(String projectKey, String environmentKey, Map<String, Object> requestBody, final ApiCallback<ContextInstanceSegmentMemberships> _callback) throws ApiException {

        okhttp3.Call localVarCall = evaluateSegmentMembershipsValidateBeforeCall(projectKey, environmentKey, requestBody, _callback);
        Type localVarReturnType = new TypeToken<ContextInstanceSegmentMemberships>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class EvaluateSegmentMembershipsRequestBuilder {
        private final String projectKey;
        private final String environmentKey;

        private EvaluateSegmentMembershipsRequestBuilder(String projectKey, String environmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Build call for evaluateSegmentMemberships
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context instance segment membership collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            Map<String, Object> requestBody = buildBodyParams();
            return evaluateSegmentMembershipsCall(projectKey, environmentKey, requestBody, _callback);
        }

        private Map<String, Object> buildBodyParams() {
            Map<String, Object> requestBody = new HashMap<String, Object>();
            return requestBody;
        }

        /**
         * Execute evaluateSegmentMemberships request
         * @return ContextInstanceSegmentMemberships
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context instance segment membership collection response </td><td>  -  </td></tr>
         </table>
         */
        public ContextInstanceSegmentMemberships execute() throws ApiException {
            Map<String, Object> requestBody = buildBodyParams();
            ApiResponse<ContextInstanceSegmentMemberships> localVarResp = evaluateSegmentMembershipsWithHttpInfo(projectKey, environmentKey, requestBody);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute evaluateSegmentMemberships request with HTTP info returned
         * @return ApiResponse&lt;ContextInstanceSegmentMemberships&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context instance segment membership collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ContextInstanceSegmentMemberships> executeWithHttpInfo() throws ApiException {
            Map<String, Object> requestBody = buildBodyParams();
            return evaluateSegmentMembershipsWithHttpInfo(projectKey, environmentKey, requestBody);
        }

        /**
         * Execute evaluateSegmentMemberships request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Context instance segment membership collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ContextInstanceSegmentMemberships> _callback) throws ApiException {
            Map<String, Object> requestBody = buildBodyParams();
            return evaluateSegmentMembershipsAsync(projectKey, environmentKey, requestBody, _callback);
        }
    }

    /**
     * List segment memberships for context instance
     * For a given context instance with attributes, get membership details for all segments. In the request body, pass in the context instance.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param requestBody  (required)
     * @return EvaluateSegmentMembershipsRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Context instance segment membership collection response </td><td>  -  </td></tr>
     </table>
     */
    public EvaluateSegmentMembershipsRequestBuilder evaluateSegmentMemberships(String projectKey, String environmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new EvaluateSegmentMembershipsRequestBuilder(projectKey, environmentKey);
    }
    private okhttp3.Call getContextMembershipCall(String projectKey, String environmentKey, String segmentKey, String contextKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/contexts/{contextKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "segmentKey" + "}", localVarApiClient.escapeString(segmentKey.toString()))
            .replace("{" + "contextKey" + "}", localVarApiClient.escapeString(contextKey.toString()));

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
    private okhttp3.Call getContextMembershipValidateBeforeCall(String projectKey, String environmentKey, String segmentKey, String contextKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getContextMembership(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getContextMembership(Async)");
        }

        // verify the required parameter 'segmentKey' is set
        if (segmentKey == null) {
            throw new ApiException("Missing the required parameter 'segmentKey' when calling getContextMembership(Async)");
        }

        // verify the required parameter 'contextKey' is set
        if (contextKey == null) {
            throw new ApiException("Missing the required parameter 'contextKey' when calling getContextMembership(Async)");
        }

        return getContextMembershipCall(projectKey, environmentKey, segmentKey, contextKey, _callback);

    }


    private ApiResponse<BigSegmentTarget> getContextMembershipWithHttpInfo(String projectKey, String environmentKey, String segmentKey, String contextKey) throws ApiException {
        okhttp3.Call localVarCall = getContextMembershipValidateBeforeCall(projectKey, environmentKey, segmentKey, contextKey, null);
        Type localVarReturnType = new TypeToken<BigSegmentTarget>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getContextMembershipAsync(String projectKey, String environmentKey, String segmentKey, String contextKey, final ApiCallback<BigSegmentTarget> _callback) throws ApiException {

        okhttp3.Call localVarCall = getContextMembershipValidateBeforeCall(projectKey, environmentKey, segmentKey, contextKey, _callback);
        Type localVarReturnType = new TypeToken<BigSegmentTarget>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetContextMembershipRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private final String segmentKey;
        private final String contextKey;

        private GetContextMembershipRequestBuilder(String projectKey, String environmentKey, String segmentKey, String contextKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.segmentKey = segmentKey;
            this.contextKey = contextKey;
        }

        /**
         * Build call for getContextMembership
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment membership for context response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getContextMembershipCall(projectKey, environmentKey, segmentKey, contextKey, _callback);
        }


        /**
         * Execute getContextMembership request
         * @return BigSegmentTarget
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment membership for context response </td><td>  -  </td></tr>
         </table>
         */
        public BigSegmentTarget execute() throws ApiException {
            ApiResponse<BigSegmentTarget> localVarResp = getContextMembershipWithHttpInfo(projectKey, environmentKey, segmentKey, contextKey);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getContextMembership request with HTTP info returned
         * @return ApiResponse&lt;BigSegmentTarget&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment membership for context response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<BigSegmentTarget> executeWithHttpInfo() throws ApiException {
            return getContextMembershipWithHttpInfo(projectKey, environmentKey, segmentKey, contextKey);
        }

        /**
         * Execute getContextMembership request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment membership for context response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<BigSegmentTarget> _callback) throws ApiException {
            return getContextMembershipAsync(projectKey, environmentKey, segmentKey, contextKey, _callback);
        }
    }

    /**
     * Get big segment membership for context
     * Get the membership status (included/excluded) for a given context in this big segment. Big segments include larger list-based segments and synced segments. This operation does not support standard segments.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param segmentKey The segment key (required)
     * @param contextKey The context key (required)
     * @return GetContextMembershipRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment membership for context response </td><td>  -  </td></tr>
     </table>
     */
    public GetContextMembershipRequestBuilder getContextMembership(String projectKey, String environmentKey, String segmentKey, String contextKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (segmentKey == null) throw new IllegalArgumentException("\"segmentKey\" is required but got null");
            

        if (contextKey == null) throw new IllegalArgumentException("\"contextKey\" is required but got null");
            

        return new GetContextMembershipRequestBuilder(projectKey, environmentKey, segmentKey, contextKey);
    }
    private okhttp3.Call getExpiringTargetsCall(String projectKey, String environmentKey, String segmentKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/segments/{projectKey}/{segmentKey}/expiring-targets/{environmentKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "segmentKey" + "}", localVarApiClient.escapeString(segmentKey.toString()));

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
    private okhttp3.Call getExpiringTargetsValidateBeforeCall(String projectKey, String environmentKey, String segmentKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getExpiringTargets(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getExpiringTargets(Async)");
        }

        // verify the required parameter 'segmentKey' is set
        if (segmentKey == null) {
            throw new ApiException("Missing the required parameter 'segmentKey' when calling getExpiringTargets(Async)");
        }

        return getExpiringTargetsCall(projectKey, environmentKey, segmentKey, _callback);

    }


    private ApiResponse<ExpiringTargetGetResponse> getExpiringTargetsWithHttpInfo(String projectKey, String environmentKey, String segmentKey) throws ApiException {
        okhttp3.Call localVarCall = getExpiringTargetsValidateBeforeCall(projectKey, environmentKey, segmentKey, null);
        Type localVarReturnType = new TypeToken<ExpiringTargetGetResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getExpiringTargetsAsync(String projectKey, String environmentKey, String segmentKey, final ApiCallback<ExpiringTargetGetResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = getExpiringTargetsValidateBeforeCall(projectKey, environmentKey, segmentKey, _callback);
        Type localVarReturnType = new TypeToken<ExpiringTargetGetResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetExpiringTargetsRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private final String segmentKey;

        private GetExpiringTargetsRequestBuilder(String projectKey, String environmentKey, String segmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.segmentKey = segmentKey;
        }

        /**
         * Build call for getExpiringTargets
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring context target response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getExpiringTargetsCall(projectKey, environmentKey, segmentKey, _callback);
        }


        /**
         * Execute getExpiringTargets request
         * @return ExpiringTargetGetResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring context target response </td><td>  -  </td></tr>
         </table>
         */
        public ExpiringTargetGetResponse execute() throws ApiException {
            ApiResponse<ExpiringTargetGetResponse> localVarResp = getExpiringTargetsWithHttpInfo(projectKey, environmentKey, segmentKey);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getExpiringTargets request with HTTP info returned
         * @return ApiResponse&lt;ExpiringTargetGetResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring context target response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ExpiringTargetGetResponse> executeWithHttpInfo() throws ApiException {
            return getExpiringTargetsWithHttpInfo(projectKey, environmentKey, segmentKey);
        }

        /**
         * Execute getExpiringTargets request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring context target response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ExpiringTargetGetResponse> _callback) throws ApiException {
            return getExpiringTargetsAsync(projectKey, environmentKey, segmentKey, _callback);
        }
    }

    /**
     * Get expiring targets for segment
     * Get a list of a segment&#39;s context targets that are scheduled for removal.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param segmentKey The segment key (required)
     * @return GetExpiringTargetsRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring context target response </td><td>  -  </td></tr>
     </table>
     */
    public GetExpiringTargetsRequestBuilder getExpiringTargets(String projectKey, String environmentKey, String segmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (segmentKey == null) throw new IllegalArgumentException("\"segmentKey\" is required but got null");
            

        return new GetExpiringTargetsRequestBuilder(projectKey, environmentKey, segmentKey);
    }
    private okhttp3.Call getExpiringUserTargetsCall(String projectKey, String environmentKey, String segmentKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/segments/{projectKey}/{segmentKey}/expiring-user-targets/{environmentKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "segmentKey" + "}", localVarApiClient.escapeString(segmentKey.toString()));

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
    private okhttp3.Call getExpiringUserTargetsValidateBeforeCall(String projectKey, String environmentKey, String segmentKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getExpiringUserTargets(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getExpiringUserTargets(Async)");
        }

        // verify the required parameter 'segmentKey' is set
        if (segmentKey == null) {
            throw new ApiException("Missing the required parameter 'segmentKey' when calling getExpiringUserTargets(Async)");
        }

        return getExpiringUserTargetsCall(projectKey, environmentKey, segmentKey, _callback);

    }


    private ApiResponse<ExpiringUserTargetGetResponse> getExpiringUserTargetsWithHttpInfo(String projectKey, String environmentKey, String segmentKey) throws ApiException {
        okhttp3.Call localVarCall = getExpiringUserTargetsValidateBeforeCall(projectKey, environmentKey, segmentKey, null);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetGetResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getExpiringUserTargetsAsync(String projectKey, String environmentKey, String segmentKey, final ApiCallback<ExpiringUserTargetGetResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = getExpiringUserTargetsValidateBeforeCall(projectKey, environmentKey, segmentKey, _callback);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetGetResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetExpiringUserTargetsRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private final String segmentKey;

        private GetExpiringUserTargetsRequestBuilder(String projectKey, String environmentKey, String segmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.segmentKey = segmentKey;
        }

        /**
         * Build call for getExpiringUserTargets
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getExpiringUserTargetsCall(projectKey, environmentKey, segmentKey, _callback);
        }


        /**
         * Execute getExpiringUserTargets request
         * @return ExpiringUserTargetGetResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         */
        public ExpiringUserTargetGetResponse execute() throws ApiException {
            ApiResponse<ExpiringUserTargetGetResponse> localVarResp = getExpiringUserTargetsWithHttpInfo(projectKey, environmentKey, segmentKey);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getExpiringUserTargets request with HTTP info returned
         * @return ApiResponse&lt;ExpiringUserTargetGetResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ExpiringUserTargetGetResponse> executeWithHttpInfo() throws ApiException {
            return getExpiringUserTargetsWithHttpInfo(projectKey, environmentKey, segmentKey);
        }

        /**
         * Execute getExpiringUserTargets request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ExpiringUserTargetGetResponse> _callback) throws ApiException {
            return getExpiringUserTargetsAsync(projectKey, environmentKey, segmentKey, _callback);
        }
    }

    /**
     * Get expiring user targets for segment
     * &gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Get expiring targets for segment](https://apidocs.launchdarkly.com) instead of this endpoint. To learn more, read [Contexts](https://docs.launchdarkly.com/home/contexts).  Get a list of a segment&#39;s user targets that are scheduled for removal. 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param segmentKey The segment key (required)
     * @return GetExpiringUserTargetsRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
     </table>
     */
    public GetExpiringUserTargetsRequestBuilder getExpiringUserTargets(String projectKey, String environmentKey, String segmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (segmentKey == null) throw new IllegalArgumentException("\"segmentKey\" is required but got null");
            

        return new GetExpiringUserTargetsRequestBuilder(projectKey, environmentKey, segmentKey);
    }
    private okhttp3.Call getSegmentListCall(String projectKey, String environmentKey, Long limit, Long offset, String sort, String filter, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/segments/{projectKey}/{environmentKey}"
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
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getSegmentListValidateBeforeCall(String projectKey, String environmentKey, Long limit, Long offset, String sort, String filter, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getSegmentList(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getSegmentList(Async)");
        }

        return getSegmentListCall(projectKey, environmentKey, limit, offset, sort, filter, _callback);

    }


    private ApiResponse<UserSegments> getSegmentListWithHttpInfo(String projectKey, String environmentKey, Long limit, Long offset, String sort, String filter) throws ApiException {
        okhttp3.Call localVarCall = getSegmentListValidateBeforeCall(projectKey, environmentKey, limit, offset, sort, filter, null);
        Type localVarReturnType = new TypeToken<UserSegments>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getSegmentListAsync(String projectKey, String environmentKey, Long limit, Long offset, String sort, String filter, final ApiCallback<UserSegments> _callback) throws ApiException {

        okhttp3.Call localVarCall = getSegmentListValidateBeforeCall(projectKey, environmentKey, limit, offset, sort, filter, _callback);
        Type localVarReturnType = new TypeToken<UserSegments>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetSegmentListRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private Long limit;
        private Long offset;
        private String sort;
        private String filter;

        private GetSegmentListRequestBuilder(String projectKey, String environmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Set limit
         * @param limit The number of segments to return. Defaults to 50. (optional)
         * @return GetSegmentListRequestBuilder
         */
        public GetSegmentListRequestBuilder limit(Long limit) {
            this.limit = limit;
            return this;
        }
        
        /**
         * Set offset
         * @param offset Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. (optional)
         * @return GetSegmentListRequestBuilder
         */
        public GetSegmentListRequestBuilder offset(Long offset) {
            this.offset = offset;
            return this;
        }
        
        /**
         * Set sort
         * @param sort Accepts sorting order and fields. Fields can be comma separated. Possible fields are &#39;creationDate&#39;, &#39;name&#39;, &#39;lastModified&#39;. Example: &#x60;sort&#x3D;name&#x60; sort by names ascending or &#x60;sort&#x3D;-name,creationDate&#x60; sort by names descending and creationDate ascending. (optional)
         * @return GetSegmentListRequestBuilder
         */
        public GetSegmentListRequestBuilder sort(String sort) {
            this.sort = sort;
            return this;
        }
        
        /**
         * Set filter
         * @param filter Accepts filter by kind, query, tags, unbounded, or external. To filter by kind or query, use the &#x60;equals&#x60; operator. To filter by tags, use the &#x60;anyOf&#x60; operator. Query is a &#39;fuzzy&#39; search across segment key, name, and description. Example: &#x60;filter&#x3D;tags anyOf [&#39;enterprise&#39;, &#39;beta&#39;],query equals &#39;toggle&#39;&#x60; returns segments with &#39;toggle&#39; in their key, name, or description that also have &#39;enterprise&#39; or &#39;beta&#39; as a tag. To filter by unbounded, use the &#x60;equals&#x60; operator. Example: &#x60;filter&#x3D;unbounded equals true&#x60;. To filter by external, use the &#x60;exists&#x60; operator. Example: &#x60;filter&#x3D;external exists true&#x60;. (optional)
         * @return GetSegmentListRequestBuilder
         */
        public GetSegmentListRequestBuilder filter(String filter) {
            this.filter = filter;
            return this;
        }
        
        /**
         * Build call for getSegmentList
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getSegmentListCall(projectKey, environmentKey, limit, offset, sort, filter, _callback);
        }


        /**
         * Execute getSegmentList request
         * @return UserSegments
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment collection response </td><td>  -  </td></tr>
         </table>
         */
        public UserSegments execute() throws ApiException {
            ApiResponse<UserSegments> localVarResp = getSegmentListWithHttpInfo(projectKey, environmentKey, limit, offset, sort, filter);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getSegmentList request with HTTP info returned
         * @return ApiResponse&lt;UserSegments&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment collection response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<UserSegments> executeWithHttpInfo() throws ApiException {
            return getSegmentListWithHttpInfo(projectKey, environmentKey, limit, offset, sort, filter);
        }

        /**
         * Execute getSegmentList request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment collection response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<UserSegments> _callback) throws ApiException {
            return getSegmentListAsync(projectKey, environmentKey, limit, offset, sort, filter, _callback);
        }
    }

    /**
     * List segments
     * Get a list of all segments in the given project.&lt;br/&gt;&lt;br/&gt;Segments can be rule-based, list-based, or synced. Big segments include larger list-based segments and synced segments. Some fields in the response only apply to big segments.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @return GetSegmentListRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment collection response </td><td>  -  </td></tr>
     </table>
     */
    public GetSegmentListRequestBuilder getSegmentList(String projectKey, String environmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new GetSegmentListRequestBuilder(projectKey, environmentKey);
    }
    private okhttp3.Call getUserMembershipStatusCall(String projectKey, String environmentKey, String segmentKey, String userKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/users/{userKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "segmentKey" + "}", localVarApiClient.escapeString(segmentKey.toString()))
            .replace("{" + "userKey" + "}", localVarApiClient.escapeString(userKey.toString()));

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
    private okhttp3.Call getUserMembershipStatusValidateBeforeCall(String projectKey, String environmentKey, String segmentKey, String userKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getUserMembershipStatus(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getUserMembershipStatus(Async)");
        }

        // verify the required parameter 'segmentKey' is set
        if (segmentKey == null) {
            throw new ApiException("Missing the required parameter 'segmentKey' when calling getUserMembershipStatus(Async)");
        }

        // verify the required parameter 'userKey' is set
        if (userKey == null) {
            throw new ApiException("Missing the required parameter 'userKey' when calling getUserMembershipStatus(Async)");
        }

        return getUserMembershipStatusCall(projectKey, environmentKey, segmentKey, userKey, _callback);

    }


    private ApiResponse<BigSegmentTarget> getUserMembershipStatusWithHttpInfo(String projectKey, String environmentKey, String segmentKey, String userKey) throws ApiException {
        okhttp3.Call localVarCall = getUserMembershipStatusValidateBeforeCall(projectKey, environmentKey, segmentKey, userKey, null);
        Type localVarReturnType = new TypeToken<BigSegmentTarget>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getUserMembershipStatusAsync(String projectKey, String environmentKey, String segmentKey, String userKey, final ApiCallback<BigSegmentTarget> _callback) throws ApiException {

        okhttp3.Call localVarCall = getUserMembershipStatusValidateBeforeCall(projectKey, environmentKey, segmentKey, userKey, _callback);
        Type localVarReturnType = new TypeToken<BigSegmentTarget>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetUserMembershipStatusRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private final String segmentKey;
        private final String userKey;

        private GetUserMembershipStatusRequestBuilder(String projectKey, String environmentKey, String segmentKey, String userKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.segmentKey = segmentKey;
            this.userKey = userKey;
        }

        /**
         * Build call for getUserMembershipStatus
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment membership for user response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getUserMembershipStatusCall(projectKey, environmentKey, segmentKey, userKey, _callback);
        }


        /**
         * Execute getUserMembershipStatus request
         * @return BigSegmentTarget
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment membership for user response </td><td>  -  </td></tr>
         </table>
         */
        public BigSegmentTarget execute() throws ApiException {
            ApiResponse<BigSegmentTarget> localVarResp = getUserMembershipStatusWithHttpInfo(projectKey, environmentKey, segmentKey, userKey);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getUserMembershipStatus request with HTTP info returned
         * @return ApiResponse&lt;BigSegmentTarget&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment membership for user response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<BigSegmentTarget> executeWithHttpInfo() throws ApiException {
            return getUserMembershipStatusWithHttpInfo(projectKey, environmentKey, segmentKey, userKey);
        }

        /**
         * Execute getUserMembershipStatus request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment membership for user response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<BigSegmentTarget> _callback) throws ApiException {
            return getUserMembershipStatusAsync(projectKey, environmentKey, segmentKey, userKey, _callback);
        }
    }

    /**
     * Get big segment membership for user
     * &gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Get expiring targets for segment](https://apidocs.launchdarkly.com) instead of this endpoint. To learn more, read [Contexts](https://docs.launchdarkly.com/home/contexts).  Get the membership status (included/excluded) for a given user in this big segment. This operation does not support standard segments. 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param segmentKey The segment key (required)
     * @param userKey The user key (required)
     * @return GetUserMembershipStatusRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment membership for user response </td><td>  -  </td></tr>
     </table>
     */
    public GetUserMembershipStatusRequestBuilder getUserMembershipStatus(String projectKey, String environmentKey, String segmentKey, String userKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (segmentKey == null) throw new IllegalArgumentException("\"segmentKey\" is required but got null");
            

        if (userKey == null) throw new IllegalArgumentException("\"userKey\" is required but got null");
            

        return new GetUserMembershipStatusRequestBuilder(projectKey, environmentKey, segmentKey, userKey);
    }
    private okhttp3.Call removeSegmentCall(String projectKey, String environmentKey, String segmentKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "segmentKey" + "}", localVarApiClient.escapeString(segmentKey.toString()));

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
    private okhttp3.Call removeSegmentValidateBeforeCall(String projectKey, String environmentKey, String segmentKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling removeSegment(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling removeSegment(Async)");
        }

        // verify the required parameter 'segmentKey' is set
        if (segmentKey == null) {
            throw new ApiException("Missing the required parameter 'segmentKey' when calling removeSegment(Async)");
        }

        return removeSegmentCall(projectKey, environmentKey, segmentKey, _callback);

    }


    private ApiResponse<Void> removeSegmentWithHttpInfo(String projectKey, String environmentKey, String segmentKey) throws ApiException {
        okhttp3.Call localVarCall = removeSegmentValidateBeforeCall(projectKey, environmentKey, segmentKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call removeSegmentAsync(String projectKey, String environmentKey, String segmentKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = removeSegmentValidateBeforeCall(projectKey, environmentKey, segmentKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class RemoveSegmentRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private final String segmentKey;

        private RemoveSegmentRequestBuilder(String projectKey, String environmentKey, String segmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.segmentKey = segmentKey;
        }

        /**
         * Build call for removeSegment
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
            return removeSegmentCall(projectKey, environmentKey, segmentKey, _callback);
        }


        /**
         * Execute removeSegment request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public void execute() throws ApiException {
            removeSegmentWithHttpInfo(projectKey, environmentKey, segmentKey);
        }

        /**
         * Execute removeSegment request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            return removeSegmentWithHttpInfo(projectKey, environmentKey, segmentKey);
        }

        /**
         * Execute removeSegment request (asynchronously)
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
            return removeSegmentAsync(projectKey, environmentKey, segmentKey, _callback);
        }
    }

    /**
     * Delete segment
     * Delete a segment.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param segmentKey The segment key (required)
     * @return RemoveSegmentRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
     </table>
     */
    public RemoveSegmentRequestBuilder removeSegment(String projectKey, String environmentKey, String segmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (segmentKey == null) throw new IllegalArgumentException("\"segmentKey\" is required but got null");
            

        return new RemoveSegmentRequestBuilder(projectKey, environmentKey, segmentKey);
    }
    private okhttp3.Call singleSegmentByKeyCall(String projectKey, String environmentKey, String segmentKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "segmentKey" + "}", localVarApiClient.escapeString(segmentKey.toString()));

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
    private okhttp3.Call singleSegmentByKeyValidateBeforeCall(String projectKey, String environmentKey, String segmentKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling singleSegmentByKey(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling singleSegmentByKey(Async)");
        }

        // verify the required parameter 'segmentKey' is set
        if (segmentKey == null) {
            throw new ApiException("Missing the required parameter 'segmentKey' when calling singleSegmentByKey(Async)");
        }

        return singleSegmentByKeyCall(projectKey, environmentKey, segmentKey, _callback);

    }


    private ApiResponse<UserSegment> singleSegmentByKeyWithHttpInfo(String projectKey, String environmentKey, String segmentKey) throws ApiException {
        okhttp3.Call localVarCall = singleSegmentByKeyValidateBeforeCall(projectKey, environmentKey, segmentKey, null);
        Type localVarReturnType = new TypeToken<UserSegment>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call singleSegmentByKeyAsync(String projectKey, String environmentKey, String segmentKey, final ApiCallback<UserSegment> _callback) throws ApiException {

        okhttp3.Call localVarCall = singleSegmentByKeyValidateBeforeCall(projectKey, environmentKey, segmentKey, _callback);
        Type localVarReturnType = new TypeToken<UserSegment>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class SingleSegmentByKeyRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private final String segmentKey;

        private SingleSegmentByKeyRequestBuilder(String projectKey, String environmentKey, String segmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.segmentKey = segmentKey;
        }

        /**
         * Build call for singleSegmentByKey
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return singleSegmentByKeyCall(projectKey, environmentKey, segmentKey, _callback);
        }


        /**
         * Execute singleSegmentByKey request
         * @return UserSegment
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
         </table>
         */
        public UserSegment execute() throws ApiException {
            ApiResponse<UserSegment> localVarResp = singleSegmentByKeyWithHttpInfo(projectKey, environmentKey, segmentKey);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute singleSegmentByKey request with HTTP info returned
         * @return ApiResponse&lt;UserSegment&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<UserSegment> executeWithHttpInfo() throws ApiException {
            return singleSegmentByKeyWithHttpInfo(projectKey, environmentKey, segmentKey);
        }

        /**
         * Execute singleSegmentByKey request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<UserSegment> _callback) throws ApiException {
            return singleSegmentByKeyAsync(projectKey, environmentKey, segmentKey, _callback);
        }
    }

    /**
     * Get segment
     * Get a single segment by key.&lt;br/&gt;&lt;br/&gt;Segments can be rule-based, list-based, or synced. Big segments include larger list-based segments and synced segments. Some fields in the response only apply to big segments.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param segmentKey The segment key (required)
     * @return SingleSegmentByKeyRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
     </table>
     */
    public SingleSegmentByKeyRequestBuilder singleSegmentByKey(String projectKey, String environmentKey, String segmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (segmentKey == null) throw new IllegalArgumentException("\"segmentKey\" is required but got null");
            

        return new SingleSegmentByKeyRequestBuilder(projectKey, environmentKey, segmentKey);
    }
    private okhttp3.Call updateContextTargetsCall(String projectKey, String environmentKey, String segmentKey, SegmentUserState segmentUserState, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = segmentUserState;

        // create path and map variables
        String localVarPath = "/api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/contexts"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "segmentKey" + "}", localVarApiClient.escapeString(segmentKey.toString()));

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
    private okhttp3.Call updateContextTargetsValidateBeforeCall(String projectKey, String environmentKey, String segmentKey, SegmentUserState segmentUserState, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling updateContextTargets(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling updateContextTargets(Async)");
        }

        // verify the required parameter 'segmentKey' is set
        if (segmentKey == null) {
            throw new ApiException("Missing the required parameter 'segmentKey' when calling updateContextTargets(Async)");
        }

        // verify the required parameter 'segmentUserState' is set
        if (segmentUserState == null) {
            throw new ApiException("Missing the required parameter 'segmentUserState' when calling updateContextTargets(Async)");
        }

        return updateContextTargetsCall(projectKey, environmentKey, segmentKey, segmentUserState, _callback);

    }


    private ApiResponse<Void> updateContextTargetsWithHttpInfo(String projectKey, String environmentKey, String segmentKey, SegmentUserState segmentUserState) throws ApiException {
        okhttp3.Call localVarCall = updateContextTargetsValidateBeforeCall(projectKey, environmentKey, segmentKey, segmentUserState, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call updateContextTargetsAsync(String projectKey, String environmentKey, String segmentKey, SegmentUserState segmentUserState, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateContextTargetsValidateBeforeCall(projectKey, environmentKey, segmentKey, segmentUserState, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class UpdateContextTargetsRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private final String segmentKey;
        private SegmentUserList included;
        private SegmentUserList excluded;

        private UpdateContextTargetsRequestBuilder(String projectKey, String environmentKey, String segmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.segmentKey = segmentKey;
        }

        /**
         * Set included
         * @param included  (optional)
         * @return UpdateContextTargetsRequestBuilder
         */
        public UpdateContextTargetsRequestBuilder included(SegmentUserList included) {
            this.included = included;
            return this;
        }
        
        /**
         * Set excluded
         * @param excluded  (optional)
         * @return UpdateContextTargetsRequestBuilder
         */
        public UpdateContextTargetsRequestBuilder excluded(SegmentUserList excluded) {
            this.excluded = excluded;
            return this;
        }
        
        /**
         * Build call for updateContextTargets
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
            SegmentUserState segmentUserState = buildBodyParams();
            return updateContextTargetsCall(projectKey, environmentKey, segmentKey, segmentUserState, _callback);
        }

        private SegmentUserState buildBodyParams() {
            SegmentUserState segmentUserState = new SegmentUserState();
            segmentUserState.included(this.included);
            segmentUserState.excluded(this.excluded);
            return segmentUserState;
        }

        /**
         * Execute updateContextTargets request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public void execute() throws ApiException {
            SegmentUserState segmentUserState = buildBodyParams();
            updateContextTargetsWithHttpInfo(projectKey, environmentKey, segmentKey, segmentUserState);
        }

        /**
         * Execute updateContextTargets request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            SegmentUserState segmentUserState = buildBodyParams();
            return updateContextTargetsWithHttpInfo(projectKey, environmentKey, segmentKey, segmentUserState);
        }

        /**
         * Execute updateContextTargets request (asynchronously)
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
            SegmentUserState segmentUserState = buildBodyParams();
            return updateContextTargetsAsync(projectKey, environmentKey, segmentKey, segmentUserState, _callback);
        }
    }

    /**
     * Update context targets on a big segment
     * Update context targets included or excluded in a big segment. Big segments include larger list-based segments and synced segments. This operation does not support standard segments.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param segmentKey The segment key (required)
     * @param segmentUserState  (required)
     * @return UpdateContextTargetsRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
     </table>
     */
    public UpdateContextTargetsRequestBuilder updateContextTargets(String projectKey, String environmentKey, String segmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (segmentKey == null) throw new IllegalArgumentException("\"segmentKey\" is required but got null");
            

        return new UpdateContextTargetsRequestBuilder(projectKey, environmentKey, segmentKey);
    }
    private okhttp3.Call updateExpiringTargetsForSegmentCall(String projectKey, String environmentKey, String segmentKey, PatchSegmentExpiringTargetInputRep patchSegmentExpiringTargetInputRep, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = patchSegmentExpiringTargetInputRep;

        // create path and map variables
        String localVarPath = "/api/v2/segments/{projectKey}/{segmentKey}/expiring-targets/{environmentKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "segmentKey" + "}", localVarApiClient.escapeString(segmentKey.toString()));

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
    private okhttp3.Call updateExpiringTargetsForSegmentValidateBeforeCall(String projectKey, String environmentKey, String segmentKey, PatchSegmentExpiringTargetInputRep patchSegmentExpiringTargetInputRep, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling updateExpiringTargetsForSegment(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling updateExpiringTargetsForSegment(Async)");
        }

        // verify the required parameter 'segmentKey' is set
        if (segmentKey == null) {
            throw new ApiException("Missing the required parameter 'segmentKey' when calling updateExpiringTargetsForSegment(Async)");
        }

        // verify the required parameter 'patchSegmentExpiringTargetInputRep' is set
        if (patchSegmentExpiringTargetInputRep == null) {
            throw new ApiException("Missing the required parameter 'patchSegmentExpiringTargetInputRep' when calling updateExpiringTargetsForSegment(Async)");
        }

        return updateExpiringTargetsForSegmentCall(projectKey, environmentKey, segmentKey, patchSegmentExpiringTargetInputRep, _callback);

    }


    private ApiResponse<ExpiringTargetPatchResponse> updateExpiringTargetsForSegmentWithHttpInfo(String projectKey, String environmentKey, String segmentKey, PatchSegmentExpiringTargetInputRep patchSegmentExpiringTargetInputRep) throws ApiException {
        okhttp3.Call localVarCall = updateExpiringTargetsForSegmentValidateBeforeCall(projectKey, environmentKey, segmentKey, patchSegmentExpiringTargetInputRep, null);
        Type localVarReturnType = new TypeToken<ExpiringTargetPatchResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call updateExpiringTargetsForSegmentAsync(String projectKey, String environmentKey, String segmentKey, PatchSegmentExpiringTargetInputRep patchSegmentExpiringTargetInputRep, final ApiCallback<ExpiringTargetPatchResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateExpiringTargetsForSegmentValidateBeforeCall(projectKey, environmentKey, segmentKey, patchSegmentExpiringTargetInputRep, _callback);
        Type localVarReturnType = new TypeToken<ExpiringTargetPatchResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class UpdateExpiringTargetsForSegmentRequestBuilder {
        private final List<PatchSegmentExpiringTargetInstruction> instructions;
        private final String projectKey;
        private final String environmentKey;
        private final String segmentKey;
        private String comment;

        private UpdateExpiringTargetsForSegmentRequestBuilder(List<PatchSegmentExpiringTargetInstruction> instructions, String projectKey, String environmentKey, String segmentKey) {
            this.instructions = instructions;
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.segmentKey = segmentKey;
        }

        /**
         * Set comment
         * @param comment Optional description of changes (optional)
         * @return UpdateExpiringTargetsForSegmentRequestBuilder
         */
        public UpdateExpiringTargetsForSegmentRequestBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }
        
        /**
         * Build call for updateExpiringTargetsForSegment
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring  target response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            PatchSegmentExpiringTargetInputRep patchSegmentExpiringTargetInputRep = buildBodyParams();
            return updateExpiringTargetsForSegmentCall(projectKey, environmentKey, segmentKey, patchSegmentExpiringTargetInputRep, _callback);
        }

        private PatchSegmentExpiringTargetInputRep buildBodyParams() {
            PatchSegmentExpiringTargetInputRep patchSegmentExpiringTargetInputRep = new PatchSegmentExpiringTargetInputRep();
            patchSegmentExpiringTargetInputRep.comment(this.comment);
            patchSegmentExpiringTargetInputRep.instructions(this.instructions);
            return patchSegmentExpiringTargetInputRep;
        }

        /**
         * Execute updateExpiringTargetsForSegment request
         * @return ExpiringTargetPatchResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring  target response </td><td>  -  </td></tr>
         </table>
         */
        public ExpiringTargetPatchResponse execute() throws ApiException {
            PatchSegmentExpiringTargetInputRep patchSegmentExpiringTargetInputRep = buildBodyParams();
            ApiResponse<ExpiringTargetPatchResponse> localVarResp = updateExpiringTargetsForSegmentWithHttpInfo(projectKey, environmentKey, segmentKey, patchSegmentExpiringTargetInputRep);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute updateExpiringTargetsForSegment request with HTTP info returned
         * @return ApiResponse&lt;ExpiringTargetPatchResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring  target response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ExpiringTargetPatchResponse> executeWithHttpInfo() throws ApiException {
            PatchSegmentExpiringTargetInputRep patchSegmentExpiringTargetInputRep = buildBodyParams();
            return updateExpiringTargetsForSegmentWithHttpInfo(projectKey, environmentKey, segmentKey, patchSegmentExpiringTargetInputRep);
        }

        /**
         * Execute updateExpiringTargetsForSegment request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring  target response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ExpiringTargetPatchResponse> _callback) throws ApiException {
            PatchSegmentExpiringTargetInputRep patchSegmentExpiringTargetInputRep = buildBodyParams();
            return updateExpiringTargetsForSegmentAsync(projectKey, environmentKey, segmentKey, patchSegmentExpiringTargetInputRep, _callback);
        }
    }

    /**
     * Update expiring targets for segment
     *  Update expiring context targets for a segment. Updating a context target expiration uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://apidocs.launchdarkly.com).  If the request is well-formed but any of its instructions failed to process, this operation returns status code &#x60;200&#x60;. In this case, the response &#x60;errors&#x60; array will be non-empty.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating expiring context targets.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating expiring context targets&lt;/strong&gt;&lt;/summary&gt;  #### addExpiringTarget  Schedules a date and time when LaunchDarkly will remove a context from segment targeting. The segment must already have the context as an individual target.  ##### Parameters  - &#x60;targetType&#x60;: The type of individual target for this context. Must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;contextKey&#x60;: The context key. - &#x60;contextKind&#x60;: The kind of context being targeted. - &#x60;value&#x60;: The date when the context should expire from the segment targeting, in Unix milliseconds.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addExpiringTarget\&quot;,     \&quot;targetType\&quot;: \&quot;included\&quot;,     \&quot;contextKey\&quot;: \&quot;user-key-123abc\&quot;,     \&quot;contextKind\&quot;: \&quot;user\&quot;,     \&quot;value\&quot;: 1754092860000   }] } &#x60;&#x60;&#x60;  #### updateExpiringTarget  Updates the date and time when LaunchDarkly will remove a context from segment targeting.  ##### Parameters  - &#x60;targetType&#x60;: The type of individual target for this context. Must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;contextKey&#x60;: The context key. - &#x60;contextKind&#x60;: The kind of context being targeted. - &#x60;value&#x60;: The new date when the context should expire from the segment targeting, in Unix milliseconds. - &#x60;version&#x60;: (Optional) The version of the expiring target to update. If included, update will fail if version doesn&#39;t match current version of the expiring target.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateExpiringTarget\&quot;,     \&quot;targetType\&quot;: \&quot;included\&quot;,     \&quot;contextKey\&quot;: \&quot;user-key-123abc\&quot;,     \&quot;contextKind\&quot;: \&quot;user\&quot;,     \&quot;value\&quot;: 1754179260000   }] } &#x60;&#x60;&#x60;  #### removeExpiringTarget  Removes the scheduled expiration for the context in the segment.  ##### Parameters  - &#x60;targetType&#x60;: The type of individual target for this context. Must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;contextKey&#x60;: The context key. - &#x60;contextKind&#x60;: The kind of context being targeted.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeExpiringTarget\&quot;,     \&quot;targetType\&quot;: \&quot;included\&quot;,     \&quot;contextKey\&quot;: \&quot;user-key-123abc\&quot;,     \&quot;contextKind\&quot;: \&quot;user\&quot;,   }] } &#x60;&#x60;&#x60;  &lt;/details&gt; 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param segmentKey The segment key (required)
     * @param patchSegmentExpiringTargetInputRep  (required)
     * @return UpdateExpiringTargetsForSegmentRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring  target response </td><td>  -  </td></tr>
     </table>
     */
    public UpdateExpiringTargetsForSegmentRequestBuilder updateExpiringTargetsForSegment(List<PatchSegmentExpiringTargetInstruction> instructions, String projectKey, String environmentKey, String segmentKey) throws IllegalArgumentException {
        if (instructions == null) throw new IllegalArgumentException("\"instructions\" is required but got null");
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (segmentKey == null) throw new IllegalArgumentException("\"segmentKey\" is required but got null");
            

        return new UpdateExpiringTargetsForSegmentRequestBuilder(instructions, projectKey, environmentKey, segmentKey);
    }
    private okhttp3.Call updateExpiringTargetsForSegment_0Call(String projectKey, String environmentKey, String segmentKey, PatchSegmentRequest patchSegmentRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = patchSegmentRequest;

        // create path and map variables
        String localVarPath = "/api/v2/segments/{projectKey}/{segmentKey}/expiring-user-targets/{environmentKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "segmentKey" + "}", localVarApiClient.escapeString(segmentKey.toString()));

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
    private okhttp3.Call updateExpiringTargetsForSegment_0ValidateBeforeCall(String projectKey, String environmentKey, String segmentKey, PatchSegmentRequest patchSegmentRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling updateExpiringTargetsForSegment_0(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling updateExpiringTargetsForSegment_0(Async)");
        }

        // verify the required parameter 'segmentKey' is set
        if (segmentKey == null) {
            throw new ApiException("Missing the required parameter 'segmentKey' when calling updateExpiringTargetsForSegment_0(Async)");
        }

        // verify the required parameter 'patchSegmentRequest' is set
        if (patchSegmentRequest == null) {
            throw new ApiException("Missing the required parameter 'patchSegmentRequest' when calling updateExpiringTargetsForSegment_0(Async)");
        }

        return updateExpiringTargetsForSegment_0Call(projectKey, environmentKey, segmentKey, patchSegmentRequest, _callback);

    }


    private ApiResponse<ExpiringUserTargetPatchResponse> updateExpiringTargetsForSegment_0WithHttpInfo(String projectKey, String environmentKey, String segmentKey, PatchSegmentRequest patchSegmentRequest) throws ApiException {
        okhttp3.Call localVarCall = updateExpiringTargetsForSegment_0ValidateBeforeCall(projectKey, environmentKey, segmentKey, patchSegmentRequest, null);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetPatchResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call updateExpiringTargetsForSegment_0Async(String projectKey, String environmentKey, String segmentKey, PatchSegmentRequest patchSegmentRequest, final ApiCallback<ExpiringUserTargetPatchResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateExpiringTargetsForSegment_0ValidateBeforeCall(projectKey, environmentKey, segmentKey, patchSegmentRequest, _callback);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetPatchResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class UpdateExpiringTargetsForSegment0RequestBuilder {
        private final List<PatchSegmentInstruction> instructions;
        private final String projectKey;
        private final String environmentKey;
        private final String segmentKey;
        private String comment;

        private UpdateExpiringTargetsForSegment0RequestBuilder(List<PatchSegmentInstruction> instructions, String projectKey, String environmentKey, String segmentKey) {
            this.instructions = instructions;
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.segmentKey = segmentKey;
        }

        /**
         * Set comment
         * @param comment Optional description of changes (optional)
         * @return UpdateExpiringTargetsForSegment0RequestBuilder
         */
        public UpdateExpiringTargetsForSegment0RequestBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }
        
        /**
         * Build call for updateExpiringTargetsForSegment_0
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            PatchSegmentRequest patchSegmentRequest = buildBodyParams();
            return updateExpiringTargetsForSegment_0Call(projectKey, environmentKey, segmentKey, patchSegmentRequest, _callback);
        }

        private PatchSegmentRequest buildBodyParams() {
            PatchSegmentRequest patchSegmentRequest = new PatchSegmentRequest();
            patchSegmentRequest.comment(this.comment);
            patchSegmentRequest.instructions(this.instructions);
            return patchSegmentRequest;
        }

        /**
         * Execute updateExpiringTargetsForSegment_0 request
         * @return ExpiringUserTargetPatchResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         */
        public ExpiringUserTargetPatchResponse execute() throws ApiException {
            PatchSegmentRequest patchSegmentRequest = buildBodyParams();
            ApiResponse<ExpiringUserTargetPatchResponse> localVarResp = updateExpiringTargetsForSegment_0WithHttpInfo(projectKey, environmentKey, segmentKey, patchSegmentRequest);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute updateExpiringTargetsForSegment_0 request with HTTP info returned
         * @return ApiResponse&lt;ExpiringUserTargetPatchResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<ExpiringUserTargetPatchResponse> executeWithHttpInfo() throws ApiException {
            PatchSegmentRequest patchSegmentRequest = buildBodyParams();
            return updateExpiringTargetsForSegment_0WithHttpInfo(projectKey, environmentKey, segmentKey, patchSegmentRequest);
        }

        /**
         * Execute updateExpiringTargetsForSegment_0 request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<ExpiringUserTargetPatchResponse> _callback) throws ApiException {
            PatchSegmentRequest patchSegmentRequest = buildBodyParams();
            return updateExpiringTargetsForSegment_0Async(projectKey, environmentKey, segmentKey, patchSegmentRequest, _callback);
        }
    }

    /**
     * Update expiring user targets for segment
     *  &gt; ### Contexts are now available &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Update expiring targets for segment](https://apidocs.launchdarkly.com) instead of this endpoint. To learn more, read [Contexts](https://docs.launchdarkly.com/home/contexts).  Update expiring user targets for a segment. Updating a user target expiration uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://apidocs.launchdarkly.com).  If the request is well-formed but any of its instructions failed to process, this operation returns status code &#x60;200&#x60;. In this case, the response &#x60;errors&#x60; array will be non-empty.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating expiring user targets.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating expiring user targets&lt;/strong&gt;&lt;/summary&gt;  #### addExpireUserTargetDate  Schedules a date and time when LaunchDarkly will remove a user from segment targeting.  ##### Parameters  - &#x60;targetType&#x60;: A segment&#39;s target type, must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;userKey&#x60;: The user key. - &#x60;value&#x60;: The date when the user should expire from the segment targeting, in Unix milliseconds.  #### updateExpireUserTargetDate  Updates the date and time when LaunchDarkly will remove a user from segment targeting.  ##### Parameters  - &#x60;targetType&#x60;: A segment&#39;s target type, must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;userKey&#x60;: The user key. - &#x60;value&#x60;: The new date when the user should expire from the segment targeting, in Unix milliseconds. - &#x60;version&#x60;: The segment version.  #### removeExpireUserTargetDate  Removes the scheduled expiration for the user in the segment.  ##### Parameters  - &#x60;targetType&#x60;: A segment&#39;s target type, must be either &#x60;included&#x60; or &#x60;excluded&#x60;. - &#x60;userKey&#x60;: The user key.  &lt;/details&gt; 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param segmentKey The segment key (required)
     * @param patchSegmentRequest  (required)
     * @return UpdateExpiringTargetsForSegment0RequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
     </table>
     */
    public UpdateExpiringTargetsForSegment0RequestBuilder updateExpiringTargetsForSegment_0(List<PatchSegmentInstruction> instructions, String projectKey, String environmentKey, String segmentKey) throws IllegalArgumentException {
        if (instructions == null) throw new IllegalArgumentException("\"instructions\" is required but got null");
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (segmentKey == null) throw new IllegalArgumentException("\"segmentKey\" is required but got null");
            

        return new UpdateExpiringTargetsForSegment0RequestBuilder(instructions, projectKey, environmentKey, segmentKey);
    }
    private okhttp3.Call updateSemanticPatchCall(String projectKey, String environmentKey, String segmentKey, PatchWithComment patchWithComment, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = patchWithComment;

        // create path and map variables
        String localVarPath = "/api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "segmentKey" + "}", localVarApiClient.escapeString(segmentKey.toString()));

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
    private okhttp3.Call updateSemanticPatchValidateBeforeCall(String projectKey, String environmentKey, String segmentKey, PatchWithComment patchWithComment, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling updateSemanticPatch(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling updateSemanticPatch(Async)");
        }

        // verify the required parameter 'segmentKey' is set
        if (segmentKey == null) {
            throw new ApiException("Missing the required parameter 'segmentKey' when calling updateSemanticPatch(Async)");
        }

        // verify the required parameter 'patchWithComment' is set
        if (patchWithComment == null) {
            throw new ApiException("Missing the required parameter 'patchWithComment' when calling updateSemanticPatch(Async)");
        }

        return updateSemanticPatchCall(projectKey, environmentKey, segmentKey, patchWithComment, _callback);

    }


    private ApiResponse<UserSegment> updateSemanticPatchWithHttpInfo(String projectKey, String environmentKey, String segmentKey, PatchWithComment patchWithComment) throws ApiException {
        okhttp3.Call localVarCall = updateSemanticPatchValidateBeforeCall(projectKey, environmentKey, segmentKey, patchWithComment, null);
        Type localVarReturnType = new TypeToken<UserSegment>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call updateSemanticPatchAsync(String projectKey, String environmentKey, String segmentKey, PatchWithComment patchWithComment, final ApiCallback<UserSegment> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateSemanticPatchValidateBeforeCall(projectKey, environmentKey, segmentKey, patchWithComment, _callback);
        Type localVarReturnType = new TypeToken<UserSegment>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class UpdateSemanticPatchRequestBuilder {
        private final List<PatchOperation> patch;
        private final String projectKey;
        private final String environmentKey;
        private final String segmentKey;
        private String comment;

        private UpdateSemanticPatchRequestBuilder(List<PatchOperation> patch, String projectKey, String environmentKey, String segmentKey) {
            this.patch = patch;
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.segmentKey = segmentKey;
        }

        /**
         * Set comment
         * @param comment Optional comment (optional)
         * @return UpdateSemanticPatchRequestBuilder
         */
        public UpdateSemanticPatchRequestBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }
        
        /**
         * Build call for updateSemanticPatch
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            PatchWithComment patchWithComment = buildBodyParams();
            return updateSemanticPatchCall(projectKey, environmentKey, segmentKey, patchWithComment, _callback);
        }

        private PatchWithComment buildBodyParams() {
            PatchWithComment patchWithComment = new PatchWithComment();
            patchWithComment.patch(this.patch);
            patchWithComment.comment(this.comment);
            return patchWithComment;
        }

        /**
         * Execute updateSemanticPatch request
         * @return UserSegment
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
         </table>
         */
        public UserSegment execute() throws ApiException {
            PatchWithComment patchWithComment = buildBodyParams();
            ApiResponse<UserSegment> localVarResp = updateSemanticPatchWithHttpInfo(projectKey, environmentKey, segmentKey, patchWithComment);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute updateSemanticPatch request with HTTP info returned
         * @return ApiResponse&lt;UserSegment&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<UserSegment> executeWithHttpInfo() throws ApiException {
            PatchWithComment patchWithComment = buildBodyParams();
            return updateSemanticPatchWithHttpInfo(projectKey, environmentKey, segmentKey, patchWithComment);
        }

        /**
         * Execute updateSemanticPatch request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
         </table>
         */
        public okhttp3.Call executeAsync(final ApiCallback<UserSegment> _callback) throws ApiException {
            PatchWithComment patchWithComment = buildBodyParams();
            return updateSemanticPatchAsync(projectKey, environmentKey, segmentKey, patchWithComment, _callback);
        }
    }

    /**
     * Patch segment
     * Update a segment. The request body must be a valid semantic patch, JSON patch, or JSON merge patch. To learn more the different formats, read [Updates](https://apidocs.launchdarkly.com).  ### Using semantic patches on a segment  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://apidocs.launchdarkly.com).  The body of a semantic patch request for updating segments requires an &#x60;environmentKey&#x60; in addition to &#x60;instructions&#x60; and an optional &#x60;comment&#x60;. The body of the request takes the following properties:  * &#x60;comment&#x60; (string): (Optional) A description of the update. * &#x60;environmentKey&#x60; (string): (Required) The key of the LaunchDarkly environment. * &#x60;instructions&#x60; (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a &#x60;kind&#x60; property that indicates the instruction. If the action requires parameters, you must include those parameters as additional fields in the object.  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating segments.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating segments&lt;/strong&gt;&lt;/summary&gt;  #### addIncludedTargets  Adds context keys to the individual context targets included in the segment for the specified &#x60;contextKind&#x60;. Returns an error if this causes the same context key to be both included and excluded.  ##### Parameters  - &#x60;contextKind&#x60;: The context kind the targets should be added to. - &#x60;values&#x60;: List of keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addIncludedTargets\&quot;,     \&quot;contextKind\&quot;: \&quot;org\&quot;,     \&quot;values\&quot;: [ \&quot;org-key-123abc\&quot;, \&quot;org-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### addIncludedUsers  Adds user keys to the individual user targets included in the segment. Returns an error if this causes the same user key to be both included and excluded. If you are working with contexts, use &#x60;addIncludedTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addIncludedUsers\&quot;,     \&quot;values\&quot;: [ \&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### addExcludedTargets  Adds context keys to the individual context targets excluded in the segment for the specified &#x60;contextKind&#x60;. Returns an error if this causes the same context key to be both included and excluded.  ##### Parameters  - &#x60;contextKind&#x60;: The context kind the targets should be added to. - &#x60;values&#x60;: List of keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addExcludedTargets\&quot;,     \&quot;contextKind\&quot;: \&quot;org\&quot;,     \&quot;values\&quot;: [ \&quot;org-key-123abc\&quot;, \&quot;org-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### addExcludedUsers  Adds user keys to the individual user targets excluded from the segment. Returns an error if this causes the same user key to be both included and excluded. If you are working with contexts, use &#x60;addExcludedTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addExcludedUsers\&quot;,     \&quot;values\&quot;: [ \&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### removeIncludedTargets  Removes context keys from the individual context targets included in the segment for the specified &#x60;contextKind&#x60;.  ##### Parameters  - &#x60;contextKind&#x60;: The context kind the targets should be removed from. - &#x60;values&#x60;: List of keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeIncludedTargets\&quot;,     \&quot;contextKind\&quot;: \&quot;org\&quot;,     \&quot;values\&quot;: [ \&quot;org-key-123abc\&quot;, \&quot;org-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### removeIncludedUsers  Removes user keys from the individual user targets included in the segment. If you are working with contexts, use &#x60;removeIncludedTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeIncludedUsers\&quot;,     \&quot;values\&quot;: [ \&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### removeExcludedTargets  Removes context keys from the individual context targets excluded from the segment for the specified &#x60;contextKind&#x60;.  ##### Parameters  - &#x60;contextKind&#x60;: The context kind the targets should be removed from. - &#x60;values&#x60;: List of keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeExcludedTargets\&quot;,     \&quot;contextKind\&quot;: \&quot;org\&quot;,     \&quot;values\&quot;: [ \&quot;org-key-123abc\&quot;, \&quot;org-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### removeExcludedUsers  Removes user keys from the individual user targets excluded from the segment. If you are working with contexts, use &#x60;removeExcludedTargets&#x60; instead of this instruction.  ##### Parameters  - &#x60;values&#x60;: List of user keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeExcludedUsers\&quot;,     \&quot;values\&quot;: [ \&quot;user-key-123abc\&quot;, \&quot;user-key-456def\&quot; ]   }] } &#x60;&#x60;&#x60;  #### updateName  Updates the name of the segment.  ##### Parameters  - &#x60;value&#x60;: Name of the segment.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateName\&quot;,     \&quot;value\&quot;: \&quot;Updated segment name\&quot;   }] } &#x60;&#x60;&#x60;  &lt;/details&gt;  ## Using JSON patches on a segment  If you do not include the header described above, you can use a [JSON patch](https://apidocs.launchdarkly.com) or [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) representation of the desired changes.  For example, to update the description for a segment with a JSON patch, use the following request body:  &#x60;&#x60;&#x60;json {   \&quot;patch\&quot;: [     {       \&quot;op\&quot;: \&quot;replace\&quot;,       \&quot;path\&quot;: \&quot;/description\&quot;,       \&quot;value\&quot;: \&quot;new description\&quot;     }   ] } &#x60;&#x60;&#x60;  To update fields in the segment that are arrays, set the &#x60;path&#x60; to the name of the field and then append &#x60;/&lt;array index&gt;&#x60;. Use &#x60;/0&#x60; to add the new entry to the beginning of the array. Use &#x60;/-&#x60; to add the new entry to the end of the array.  For example, to add a rule to a segment, use the following request body:  &#x60;&#x60;&#x60;json {   \&quot;patch\&quot;:[     {       \&quot;op\&quot;: \&quot;add\&quot;,       \&quot;path\&quot;: \&quot;/rules/0\&quot;,       \&quot;value\&quot;: {         \&quot;clauses\&quot;: [{ \&quot;contextKind\&quot;: \&quot;user\&quot;, \&quot;attribute\&quot;: \&quot;email\&quot;, \&quot;op\&quot;: \&quot;endsWith\&quot;, \&quot;values\&quot;: [\&quot;.edu\&quot;], \&quot;negate\&quot;: false }]       }     }   ] } &#x60;&#x60;&#x60;  To add or remove targets from segments, we recommend using semantic patch. Semantic patch for segments includes specific instructions for adding and removing both included and excluded targets. 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param segmentKey The segment key (required)
     * @param patchWithComment  (required)
     * @return UpdateSemanticPatchRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Segment response </td><td>  -  </td></tr>
     </table>
     */
    public UpdateSemanticPatchRequestBuilder updateSemanticPatch(List<PatchOperation> patch, String projectKey, String environmentKey, String segmentKey) throws IllegalArgumentException {
        if (patch == null) throw new IllegalArgumentException("\"patch\" is required but got null");
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (segmentKey == null) throw new IllegalArgumentException("\"segmentKey\" is required but got null");
            

        return new UpdateSemanticPatchRequestBuilder(patch, projectKey, environmentKey, segmentKey);
    }
    private okhttp3.Call updateUserContextTargetsCall(String projectKey, String environmentKey, String segmentKey, SegmentUserState segmentUserState, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = segmentUserState;

        // create path and map variables
        String localVarPath = "/api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/users"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "segmentKey" + "}", localVarApiClient.escapeString(segmentKey.toString()));

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
    private okhttp3.Call updateUserContextTargetsValidateBeforeCall(String projectKey, String environmentKey, String segmentKey, SegmentUserState segmentUserState, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling updateUserContextTargets(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling updateUserContextTargets(Async)");
        }

        // verify the required parameter 'segmentKey' is set
        if (segmentKey == null) {
            throw new ApiException("Missing the required parameter 'segmentKey' when calling updateUserContextTargets(Async)");
        }

        // verify the required parameter 'segmentUserState' is set
        if (segmentUserState == null) {
            throw new ApiException("Missing the required parameter 'segmentUserState' when calling updateUserContextTargets(Async)");
        }

        return updateUserContextTargetsCall(projectKey, environmentKey, segmentKey, segmentUserState, _callback);

    }


    private ApiResponse<Void> updateUserContextTargetsWithHttpInfo(String projectKey, String environmentKey, String segmentKey, SegmentUserState segmentUserState) throws ApiException {
        okhttp3.Call localVarCall = updateUserContextTargetsValidateBeforeCall(projectKey, environmentKey, segmentKey, segmentUserState, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call updateUserContextTargetsAsync(String projectKey, String environmentKey, String segmentKey, SegmentUserState segmentUserState, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateUserContextTargetsValidateBeforeCall(projectKey, environmentKey, segmentKey, segmentUserState, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class UpdateUserContextTargetsRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private final String segmentKey;
        private SegmentUserList included;
        private SegmentUserList excluded;

        private UpdateUserContextTargetsRequestBuilder(String projectKey, String environmentKey, String segmentKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.segmentKey = segmentKey;
        }

        /**
         * Set included
         * @param included  (optional)
         * @return UpdateUserContextTargetsRequestBuilder
         */
        public UpdateUserContextTargetsRequestBuilder included(SegmentUserList included) {
            this.included = included;
            return this;
        }
        
        /**
         * Set excluded
         * @param excluded  (optional)
         * @return UpdateUserContextTargetsRequestBuilder
         */
        public UpdateUserContextTargetsRequestBuilder excluded(SegmentUserList excluded) {
            this.excluded = excluded;
            return this;
        }
        
        /**
         * Build call for updateUserContextTargets
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
            SegmentUserState segmentUserState = buildBodyParams();
            return updateUserContextTargetsCall(projectKey, environmentKey, segmentKey, segmentUserState, _callback);
        }

        private SegmentUserState buildBodyParams() {
            SegmentUserState segmentUserState = new SegmentUserState();
            segmentUserState.included(this.included);
            segmentUserState.excluded(this.excluded);
            return segmentUserState;
        }

        /**
         * Execute updateUserContextTargets request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public void execute() throws ApiException {
            SegmentUserState segmentUserState = buildBodyParams();
            updateUserContextTargetsWithHttpInfo(projectKey, environmentKey, segmentKey, segmentUserState);
        }

        /**
         * Execute updateUserContextTargets request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         */
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            SegmentUserState segmentUserState = buildBodyParams();
            return updateUserContextTargetsWithHttpInfo(projectKey, environmentKey, segmentKey, segmentUserState);
        }

        /**
         * Execute updateUserContextTargets request (asynchronously)
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
            SegmentUserState segmentUserState = buildBodyParams();
            return updateUserContextTargetsAsync(projectKey, environmentKey, segmentKey, segmentUserState, _callback);
        }
    }

    /**
     * Update user context targets on a big segment
     * Update user context targets included or excluded in a big segment. Big segments include larger list-based segments and synced segments. This operation does not support standard segments.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param segmentKey The segment key (required)
     * @param segmentUserState  (required)
     * @return UpdateUserContextTargetsRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
     </table>
     */
    public UpdateUserContextTargetsRequestBuilder updateUserContextTargets(String projectKey, String environmentKey, String segmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (segmentKey == null) throw new IllegalArgumentException("\"segmentKey\" is required but got null");
            

        return new UpdateUserContextTargetsRequestBuilder(projectKey, environmentKey, segmentKey);
    }
}
