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


import com.konfigthis.client.model.ExpiringUserTargetGetResponse;
import com.konfigthis.client.model.ExpiringUserTargetPatchResponse;
import com.konfigthis.client.model.InstructionUserRequest;
import com.konfigthis.client.model.PatchUsersRequest;
import com.konfigthis.client.model.UserFlagSetting;
import com.konfigthis.client.model.UserFlagSettings;
import com.konfigthis.client.model.ValuePut;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class UserSettingsApiGenerated {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public UserSettingsApiGenerated() throws IllegalArgumentException {
        this(Configuration.getDefaultApiClient());
    }

    public UserSettingsApiGenerated(ApiClient apiClient) throws IllegalArgumentException {
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

    private okhttp3.Call getUserExpiringFlagTargetsCall(String projectKey, String userKey, String environmentKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/users/{projectKey}/{userKey}/expiring-user-targets/{environmentKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "userKey" + "}", localVarApiClient.escapeString(userKey.toString()))
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

    @Deprecated
    @SuppressWarnings("rawtypes")
    private okhttp3.Call getUserExpiringFlagTargetsValidateBeforeCall(String projectKey, String userKey, String environmentKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling getUserExpiringFlagTargets(Async)");
        }

        // verify the required parameter 'userKey' is set
        if (userKey == null) {
            throw new ApiException("Missing the required parameter 'userKey' when calling getUserExpiringFlagTargets(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling getUserExpiringFlagTargets(Async)");
        }

        return getUserExpiringFlagTargetsCall(projectKey, userKey, environmentKey, _callback);

    }


    private ApiResponse<ExpiringUserTargetGetResponse> getUserExpiringFlagTargetsWithHttpInfo(String projectKey, String userKey, String environmentKey) throws ApiException {
        okhttp3.Call localVarCall = getUserExpiringFlagTargetsValidateBeforeCall(projectKey, userKey, environmentKey, null);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetGetResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call getUserExpiringFlagTargetsAsync(String projectKey, String userKey, String environmentKey, final ApiCallback<ExpiringUserTargetGetResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = getUserExpiringFlagTargetsValidateBeforeCall(projectKey, userKey, environmentKey, _callback);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetGetResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class GetUserExpiringFlagTargetsRequestBuilder {
        private final String projectKey;
        private final String userKey;
        private final String environmentKey;

        private GetUserExpiringFlagTargetsRequestBuilder(String projectKey, String userKey, String environmentKey) {
            this.projectKey = projectKey;
            this.userKey = userKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Build call for getUserExpiringFlagTargets
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return getUserExpiringFlagTargetsCall(projectKey, userKey, environmentKey, _callback);
        }


        /**
         * Execute getUserExpiringFlagTargets request
         * @return ExpiringUserTargetGetResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public ExpiringUserTargetGetResponse execute() throws ApiException {
            ApiResponse<ExpiringUserTargetGetResponse> localVarResp = getUserExpiringFlagTargetsWithHttpInfo(projectKey, userKey, environmentKey);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute getUserExpiringFlagTargets request with HTTP info returned
         * @return ApiResponse&lt;ExpiringUserTargetGetResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public ApiResponse<ExpiringUserTargetGetResponse> executeWithHttpInfo() throws ApiException {
            return getUserExpiringFlagTargetsWithHttpInfo(projectKey, userKey, environmentKey);
        }

        /**
         * Execute getUserExpiringFlagTargets request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public okhttp3.Call executeAsync(final ApiCallback<ExpiringUserTargetGetResponse> _callback) throws ApiException {
            return getUserExpiringFlagTargetsAsync(projectKey, userKey, environmentKey, _callback);
        }
    }

    /**
     * Get expiring dates on flags for user
     * Get a list of flags for which the given user is scheduled for removal.
     * @param projectKey The project key (required)
     * @param userKey The user key (required)
     * @param environmentKey The environment key (required)
     * @return GetUserExpiringFlagTargetsRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public GetUserExpiringFlagTargetsRequestBuilder getUserExpiringFlagTargets(String projectKey, String userKey, String environmentKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (userKey == null) throw new IllegalArgumentException("\"userKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new GetUserExpiringFlagTargetsRequestBuilder(projectKey, userKey, environmentKey);
    }
    private okhttp3.Call listFlagSettingsForUserCall(String projectKey, String environmentKey, String userKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/users/{projectKey}/{environmentKey}/{userKey}/flags"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
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

    @Deprecated
    @SuppressWarnings("rawtypes")
    private okhttp3.Call listFlagSettingsForUserValidateBeforeCall(String projectKey, String environmentKey, String userKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling listFlagSettingsForUser(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling listFlagSettingsForUser(Async)");
        }

        // verify the required parameter 'userKey' is set
        if (userKey == null) {
            throw new ApiException("Missing the required parameter 'userKey' when calling listFlagSettingsForUser(Async)");
        }

        return listFlagSettingsForUserCall(projectKey, environmentKey, userKey, _callback);

    }


    private ApiResponse<UserFlagSettings> listFlagSettingsForUserWithHttpInfo(String projectKey, String environmentKey, String userKey) throws ApiException {
        okhttp3.Call localVarCall = listFlagSettingsForUserValidateBeforeCall(projectKey, environmentKey, userKey, null);
        Type localVarReturnType = new TypeToken<UserFlagSettings>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call listFlagSettingsForUserAsync(String projectKey, String environmentKey, String userKey, final ApiCallback<UserFlagSettings> _callback) throws ApiException {

        okhttp3.Call localVarCall = listFlagSettingsForUserValidateBeforeCall(projectKey, environmentKey, userKey, _callback);
        Type localVarReturnType = new TypeToken<UserFlagSettings>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class ListFlagSettingsForUserRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private final String userKey;

        private ListFlagSettingsForUserRequestBuilder(String projectKey, String environmentKey, String userKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.userKey = userKey;
        }

        /**
         * Build call for listFlagSettingsForUser
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> User flag settings collection response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return listFlagSettingsForUserCall(projectKey, environmentKey, userKey, _callback);
        }


        /**
         * Execute listFlagSettingsForUser request
         * @return UserFlagSettings
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> User flag settings collection response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public UserFlagSettings execute() throws ApiException {
            ApiResponse<UserFlagSettings> localVarResp = listFlagSettingsForUserWithHttpInfo(projectKey, environmentKey, userKey);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute listFlagSettingsForUser request with HTTP info returned
         * @return ApiResponse&lt;UserFlagSettings&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> User flag settings collection response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public ApiResponse<UserFlagSettings> executeWithHttpInfo() throws ApiException {
            return listFlagSettingsForUserWithHttpInfo(projectKey, environmentKey, userKey);
        }

        /**
         * Execute listFlagSettingsForUser request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> User flag settings collection response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public okhttp3.Call executeAsync(final ApiCallback<UserFlagSettings> _callback) throws ApiException {
            return listFlagSettingsForUserAsync(projectKey, environmentKey, userKey, _callback);
        }
    }

    /**
     * List flag settings for user
     * Get the current flag settings for a given user. &lt;br /&gt;&lt;br /&gt;The &#x60;_value&#x60; is the flag variation that the user receives. The &#x60;setting&#x60; indicates whether you&#39;ve explicitly targeted a user to receive a particular variation. For example, if you have turned off a feature flag for a user, this setting will be &#x60;false&#x60;. The example response indicates that the user &#x60;Abbie_Braun&#x60; has the &#x60;sort.order&#x60; flag enabled and the &#x60;alternate.page&#x60; flag disabled, and that the user has not been explicitly targeted to receive a particular variation.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param userKey The user key (required)
     * @return ListFlagSettingsForUserRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> User flag settings collection response </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public ListFlagSettingsForUserRequestBuilder listFlagSettingsForUser(String projectKey, String environmentKey, String userKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (userKey == null) throw new IllegalArgumentException("\"userKey\" is required but got null");
            

        return new ListFlagSettingsForUserRequestBuilder(projectKey, environmentKey, userKey);
    }
    private okhttp3.Call singleFlagSettingCall(String projectKey, String environmentKey, String userKey, String featureFlagKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/users/{projectKey}/{environmentKey}/{userKey}/flags/{featureFlagKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "userKey" + "}", localVarApiClient.escapeString(userKey.toString()))
            .replace("{" + "featureFlagKey" + "}", localVarApiClient.escapeString(featureFlagKey.toString()));

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

    @Deprecated
    @SuppressWarnings("rawtypes")
    private okhttp3.Call singleFlagSettingValidateBeforeCall(String projectKey, String environmentKey, String userKey, String featureFlagKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling singleFlagSetting(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling singleFlagSetting(Async)");
        }

        // verify the required parameter 'userKey' is set
        if (userKey == null) {
            throw new ApiException("Missing the required parameter 'userKey' when calling singleFlagSetting(Async)");
        }

        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling singleFlagSetting(Async)");
        }

        return singleFlagSettingCall(projectKey, environmentKey, userKey, featureFlagKey, _callback);

    }


    private ApiResponse<UserFlagSetting> singleFlagSettingWithHttpInfo(String projectKey, String environmentKey, String userKey, String featureFlagKey) throws ApiException {
        okhttp3.Call localVarCall = singleFlagSettingValidateBeforeCall(projectKey, environmentKey, userKey, featureFlagKey, null);
        Type localVarReturnType = new TypeToken<UserFlagSetting>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call singleFlagSettingAsync(String projectKey, String environmentKey, String userKey, String featureFlagKey, final ApiCallback<UserFlagSetting> _callback) throws ApiException {

        okhttp3.Call localVarCall = singleFlagSettingValidateBeforeCall(projectKey, environmentKey, userKey, featureFlagKey, _callback);
        Type localVarReturnType = new TypeToken<UserFlagSetting>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class SingleFlagSettingRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private final String userKey;
        private final String featureFlagKey;

        private SingleFlagSettingRequestBuilder(String projectKey, String environmentKey, String userKey, String featureFlagKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.userKey = userKey;
            this.featureFlagKey = featureFlagKey;
        }

        /**
         * Build call for singleFlagSetting
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> User flag settings response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            return singleFlagSettingCall(projectKey, environmentKey, userKey, featureFlagKey, _callback);
        }


        /**
         * Execute singleFlagSetting request
         * @return UserFlagSetting
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> User flag settings response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public UserFlagSetting execute() throws ApiException {
            ApiResponse<UserFlagSetting> localVarResp = singleFlagSettingWithHttpInfo(projectKey, environmentKey, userKey, featureFlagKey);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute singleFlagSetting request with HTTP info returned
         * @return ApiResponse&lt;UserFlagSetting&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> User flag settings response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public ApiResponse<UserFlagSetting> executeWithHttpInfo() throws ApiException {
            return singleFlagSettingWithHttpInfo(projectKey, environmentKey, userKey, featureFlagKey);
        }

        /**
         * Execute singleFlagSetting request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> User flag settings response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public okhttp3.Call executeAsync(final ApiCallback<UserFlagSetting> _callback) throws ApiException {
            return singleFlagSettingAsync(projectKey, environmentKey, userKey, featureFlagKey, _callback);
        }
    }

    /**
     * Get flag setting for user
     * Get a single flag setting for a user by flag key. &lt;br /&gt;&lt;br /&gt;The &#x60;_value&#x60; is the flag variation that the user receives. The &#x60;setting&#x60; indicates whether you&#39;ve explicitly targeted a user to receive a particular variation. For example, if you have turned off a feature flag for a user, this setting will be &#x60;false&#x60;. The example response indicates that the user &#x60;Abbie_Braun&#x60; has the &#x60;sort.order&#x60; flag enabled.
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param userKey The user key (required)
     * @param featureFlagKey The feature flag key (required)
     * @return SingleFlagSettingRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> User flag settings response </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public SingleFlagSettingRequestBuilder singleFlagSetting(String projectKey, String environmentKey, String userKey, String featureFlagKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (userKey == null) throw new IllegalArgumentException("\"userKey\" is required but got null");
            

        if (featureFlagKey == null) throw new IllegalArgumentException("\"featureFlagKey\" is required but got null");
            

        return new SingleFlagSettingRequestBuilder(projectKey, environmentKey, userKey, featureFlagKey);
    }
    private okhttp3.Call updateExpiringUserTargetCall(String projectKey, String userKey, String environmentKey, PatchUsersRequest patchUsersRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = patchUsersRequest;

        // create path and map variables
        String localVarPath = "/api/v2/users/{projectKey}/{userKey}/expiring-user-targets/{environmentKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "userKey" + "}", localVarApiClient.escapeString(userKey.toString()))
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

    @Deprecated
    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateExpiringUserTargetValidateBeforeCall(String projectKey, String userKey, String environmentKey, PatchUsersRequest patchUsersRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling updateExpiringUserTarget(Async)");
        }

        // verify the required parameter 'userKey' is set
        if (userKey == null) {
            throw new ApiException("Missing the required parameter 'userKey' when calling updateExpiringUserTarget(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling updateExpiringUserTarget(Async)");
        }

        // verify the required parameter 'patchUsersRequest' is set
        if (patchUsersRequest == null) {
            throw new ApiException("Missing the required parameter 'patchUsersRequest' when calling updateExpiringUserTarget(Async)");
        }

        return updateExpiringUserTargetCall(projectKey, userKey, environmentKey, patchUsersRequest, _callback);

    }


    private ApiResponse<ExpiringUserTargetPatchResponse> updateExpiringUserTargetWithHttpInfo(String projectKey, String userKey, String environmentKey, PatchUsersRequest patchUsersRequest) throws ApiException {
        okhttp3.Call localVarCall = updateExpiringUserTargetValidateBeforeCall(projectKey, userKey, environmentKey, patchUsersRequest, null);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetPatchResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    private okhttp3.Call updateExpiringUserTargetAsync(String projectKey, String userKey, String environmentKey, PatchUsersRequest patchUsersRequest, final ApiCallback<ExpiringUserTargetPatchResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateExpiringUserTargetValidateBeforeCall(projectKey, userKey, environmentKey, patchUsersRequest, _callback);
        Type localVarReturnType = new TypeToken<ExpiringUserTargetPatchResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    public class UpdateExpiringUserTargetRequestBuilder {
        private final List<InstructionUserRequest> instructions;
        private final String projectKey;
        private final String userKey;
        private final String environmentKey;
        private String comment;

        private UpdateExpiringUserTargetRequestBuilder(List<InstructionUserRequest> instructions, String projectKey, String userKey, String environmentKey) {
            this.instructions = instructions;
            this.projectKey = projectKey;
            this.userKey = userKey;
            this.environmentKey = environmentKey;
        }

        /**
         * Set comment
         * @param comment Optional comment describing the change (optional)
         * @return UpdateExpiringUserTargetRequestBuilder
         */
        public UpdateExpiringUserTargetRequestBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }
        
        /**
         * Build call for updateExpiringUserTarget
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            PatchUsersRequest patchUsersRequest = buildBodyParams();
            return updateExpiringUserTargetCall(projectKey, userKey, environmentKey, patchUsersRequest, _callback);
        }

        private PatchUsersRequest buildBodyParams() {
            PatchUsersRequest patchUsersRequest = new PatchUsersRequest();
            patchUsersRequest.comment(this.comment);
            patchUsersRequest.instructions(this.instructions);
            return patchUsersRequest;
        }

        /**
         * Execute updateExpiringUserTarget request
         * @return ExpiringUserTargetPatchResponse
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public ExpiringUserTargetPatchResponse execute() throws ApiException {
            PatchUsersRequest patchUsersRequest = buildBodyParams();
            ApiResponse<ExpiringUserTargetPatchResponse> localVarResp = updateExpiringUserTargetWithHttpInfo(projectKey, userKey, environmentKey, patchUsersRequest);
            return localVarResp.getResponseBody();
        }

        /**
         * Execute updateExpiringUserTarget request with HTTP info returned
         * @return ApiResponse&lt;ExpiringUserTargetPatchResponse&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public ApiResponse<ExpiringUserTargetPatchResponse> executeWithHttpInfo() throws ApiException {
            PatchUsersRequest patchUsersRequest = buildBodyParams();
            return updateExpiringUserTargetWithHttpInfo(projectKey, userKey, environmentKey, patchUsersRequest);
        }

        /**
         * Execute updateExpiringUserTarget request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public okhttp3.Call executeAsync(final ApiCallback<ExpiringUserTargetPatchResponse> _callback) throws ApiException {
            PatchUsersRequest patchUsersRequest = buildBodyParams();
            return updateExpiringUserTargetAsync(projectKey, userKey, environmentKey, patchUsersRequest, _callback);
        }
    }

    /**
     * Update expiring user target for flags
     * Schedule the specified user for removal from individual targeting on one or more flags. The user must already be individually targeted for each flag.  You can add, update, or remove a scheduled removal date. You can only schedule a user for removal on a single variation per flag.  Updating an expiring target uses the semantic patch format. To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://apidocs.launchdarkly.com).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating expiring user targets.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating expiring user targets&lt;/strong&gt;&lt;/summary&gt;  #### addExpireUserTargetDate  Adds a date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;flagKey&#x60;: The flag key * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag.  #### updateExpireUserTargetDate  Updates the date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;flagKey&#x60;: The flag key * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag. * &#x60;version&#x60;: The version of the expiring user target to update. If included, update will fail if version doesn&#39;t match current version of the expiring user target.  #### removeExpireUserTargetDate  Removes the scheduled removal of the user from the flag&#39;s individual targeting. The user will remain part of the flag&#39;s individual targeting until explicitly removed, or until another removal is scheduled.  ##### Parameters  * &#x60;flagKey&#x60;: The flag key * &#x60;variationId&#x60;: ID of a variation on the flag  &lt;/details&gt; 
     * @param projectKey The project key (required)
     * @param userKey The user key (required)
     * @param environmentKey The environment key (required)
     * @param patchUsersRequest  (required)
     * @return UpdateExpiringUserTargetRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Expiring user target response </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public UpdateExpiringUserTargetRequestBuilder updateExpiringUserTarget(List<InstructionUserRequest> instructions, String projectKey, String userKey, String environmentKey) throws IllegalArgumentException {
        if (instructions == null) throw new IllegalArgumentException("\"instructions\" is required but got null");
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (userKey == null) throw new IllegalArgumentException("\"userKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        return new UpdateExpiringUserTargetRequestBuilder(instructions, projectKey, userKey, environmentKey);
    }
    private okhttp3.Call updateFlagSettingsForUserCall(String projectKey, String environmentKey, String userKey, String featureFlagKey, ValuePut valuePut, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = valuePut;

        // create path and map variables
        String localVarPath = "/api/v2/users/{projectKey}/{environmentKey}/{userKey}/flags/{featureFlagKey}"
            .replace("{" + "projectKey" + "}", localVarApiClient.escapeString(projectKey.toString()))
            .replace("{" + "environmentKey" + "}", localVarApiClient.escapeString(environmentKey.toString()))
            .replace("{" + "userKey" + "}", localVarApiClient.escapeString(userKey.toString()))
            .replace("{" + "featureFlagKey" + "}", localVarApiClient.escapeString(featureFlagKey.toString()));

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

    @Deprecated
    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateFlagSettingsForUserValidateBeforeCall(String projectKey, String environmentKey, String userKey, String featureFlagKey, ValuePut valuePut, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'projectKey' is set
        if (projectKey == null) {
            throw new ApiException("Missing the required parameter 'projectKey' when calling updateFlagSettingsForUser(Async)");
        }

        // verify the required parameter 'environmentKey' is set
        if (environmentKey == null) {
            throw new ApiException("Missing the required parameter 'environmentKey' when calling updateFlagSettingsForUser(Async)");
        }

        // verify the required parameter 'userKey' is set
        if (userKey == null) {
            throw new ApiException("Missing the required parameter 'userKey' when calling updateFlagSettingsForUser(Async)");
        }

        // verify the required parameter 'featureFlagKey' is set
        if (featureFlagKey == null) {
            throw new ApiException("Missing the required parameter 'featureFlagKey' when calling updateFlagSettingsForUser(Async)");
        }

        // verify the required parameter 'valuePut' is set
        if (valuePut == null) {
            throw new ApiException("Missing the required parameter 'valuePut' when calling updateFlagSettingsForUser(Async)");
        }

        return updateFlagSettingsForUserCall(projectKey, environmentKey, userKey, featureFlagKey, valuePut, _callback);

    }


    private ApiResponse<Void> updateFlagSettingsForUserWithHttpInfo(String projectKey, String environmentKey, String userKey, String featureFlagKey, ValuePut valuePut) throws ApiException {
        okhttp3.Call localVarCall = updateFlagSettingsForUserValidateBeforeCall(projectKey, environmentKey, userKey, featureFlagKey, valuePut, null);
        return localVarApiClient.execute(localVarCall);
    }

    private okhttp3.Call updateFlagSettingsForUserAsync(String projectKey, String environmentKey, String userKey, String featureFlagKey, ValuePut valuePut, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateFlagSettingsForUserValidateBeforeCall(projectKey, environmentKey, userKey, featureFlagKey, valuePut, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }

    public class UpdateFlagSettingsForUserRequestBuilder {
        private final String projectKey;
        private final String environmentKey;
        private final String userKey;
        private final String featureFlagKey;
        private Object setting;
        private String comment;

        private UpdateFlagSettingsForUserRequestBuilder(String projectKey, String environmentKey, String userKey, String featureFlagKey) {
            this.projectKey = projectKey;
            this.environmentKey = environmentKey;
            this.userKey = userKey;
            this.featureFlagKey = featureFlagKey;
        }

        /**
         * Set setting
         * @param setting The variation value to set for the context. Must match the flag&#39;s variation type. (optional)
         * @return UpdateFlagSettingsForUserRequestBuilder
         */
        public UpdateFlagSettingsForUserRequestBuilder setting(Object setting) {
            this.setting = setting;
            return this;
        }
        
        /**
         * Set comment
         * @param comment Optional comment describing the change (optional)
         * @return UpdateFlagSettingsForUserRequestBuilder
         */
        public UpdateFlagSettingsForUserRequestBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }
        
        /**
         * Build call for updateFlagSettingsForUser
         * @param _callback ApiCallback API callback
         * @return Call to execute
         * @throws ApiException If fail to serialize the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public okhttp3.Call buildCall(final ApiCallback _callback) throws ApiException {
            ValuePut valuePut = buildBodyParams();
            return updateFlagSettingsForUserCall(projectKey, environmentKey, userKey, featureFlagKey, valuePut, _callback);
        }

        private ValuePut buildBodyParams() {
            ValuePut valuePut = new ValuePut();
            valuePut.setting(this.setting);
            valuePut.comment(this.comment);
            return valuePut;
        }

        /**
         * Execute updateFlagSettingsForUser request
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public void execute() throws ApiException {
            ValuePut valuePut = buildBodyParams();
            updateFlagSettingsForUserWithHttpInfo(projectKey, environmentKey, userKey, featureFlagKey, valuePut);
        }

        /**
         * Execute updateFlagSettingsForUser request with HTTP info returned
         * @return ApiResponse&lt;Void&gt;
         * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public ApiResponse<Void> executeWithHttpInfo() throws ApiException {
            ValuePut valuePut = buildBodyParams();
            return updateFlagSettingsForUserWithHttpInfo(projectKey, environmentKey, userKey, featureFlagKey, valuePut);
        }

        /**
         * Execute updateFlagSettingsForUser request (asynchronously)
         * @param _callback The callback to be executed when the API call finishes
         * @return The request call
         * @throws ApiException If fail to process the API call, e.g. serializing the request body object
         * @http.response.details
         <table summary="Response Details" border="1">
            <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
            <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
         </table>
         * @deprecated
         */
        @Deprecated
        public okhttp3.Call executeAsync(final ApiCallback<Void> _callback) throws ApiException {
            ValuePut valuePut = buildBodyParams();
            return updateFlagSettingsForUserAsync(projectKey, environmentKey, userKey, featureFlagKey, valuePut, _callback);
        }
    }

    /**
     * Update flag settings for user
     * Enable or disable a feature flag for a user based on their key.  Omitting the &#x60;setting&#x60; attribute from the request body, or including a &#x60;setting&#x60; of &#x60;null&#x60;, erases the current setting for a user.  If you previously patched the flag, and the patch included the user&#39;s data, LaunchDarkly continues to use that data. If LaunchDarkly has never encountered the user&#39;s key before, it calculates the flag values based on the user key alone. 
     * @param projectKey The project key (required)
     * @param environmentKey The environment key (required)
     * @param userKey The user key (required)
     * @param featureFlagKey The feature flag key (required)
     * @param valuePut  (required)
     * @return UpdateFlagSettingsForUserRequestBuilder
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Action succeeded </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public UpdateFlagSettingsForUserRequestBuilder updateFlagSettingsForUser(String projectKey, String environmentKey, String userKey, String featureFlagKey) throws IllegalArgumentException {
        if (projectKey == null) throw new IllegalArgumentException("\"projectKey\" is required but got null");
            

        if (environmentKey == null) throw new IllegalArgumentException("\"environmentKey\" is required but got null");
            

        if (userKey == null) throw new IllegalArgumentException("\"userKey\" is required but got null");
            

        if (featureFlagKey == null) throw new IllegalArgumentException("\"featureFlagKey\" is required but got null");
            

        return new UpdateFlagSettingsForUserRequestBuilder(projectKey, environmentKey, userKey, featureFlagKey);
    }
}
