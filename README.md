<div align="left">

[![Visit Launchdarkly](./header.png)](https://launchdarkly.com)

# [Launchdarkly](https://launchdarkly.com)

# Overview

## Authentication

LaunchDarkly's REST API uses the HTTPS protocol with a minimum TLS version of 1.2.

All REST API resources are authenticated with either [personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens), or session cookies. Other authentication mechanisms are not supported. You can manage personal access tokens on your [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.

LaunchDarkly also has SDK keys, mobile keys, and client-side IDs that are used by our server-side SDKs, mobile SDKs, and JavaScript-based SDKs, respectively. **These keys cannot be used to access our REST API**. These keys are environment-specific, and can only perform read-only operations such as fetching feature flag settings.

| Auth mechanism                                                                                  | Allowed resources                                                                                     | Use cases                                          |
| ----------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | -------------------------------------------------- |
| [Personal or service access tokens](https://docs.launchdarkly.com/home/account-security/api-access-tokens) | Can be customized on a per-token basis                                                                | Building scripts, custom integrations, data export. |
| SDK keys                                                                                        | Can only access read-only resources specific to server-side SDKs. Restricted to a single environment. | Server-side SDKs                     |
| Mobile keys                                                                                     | Can only access read-only resources specific to mobile SDKs, and only for flags marked available to mobile keys. Restricted to a single environment.           | Mobile SDKs                                        |
| Client-side ID                                                                                  | Can only access read-only resources specific to JavaScript-based client-side SDKs, and only for flags marked available to client-side. Restricted to a single environment.           | Client-side JavaScript                             |

> #### Keep your access tokens and SDK keys private
>
> Access tokens should _never_ be exposed in untrusted contexts. Never put an access token in client-side JavaScript, or embed it in a mobile application. LaunchDarkly has special mobile keys that you can embed in mobile apps. If you accidentally expose an access token or SDK key, you can reset it from your [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.
>
> The client-side ID is safe to embed in untrusted contexts. It's designed for use in client-side JavaScript.

### Authentication using request header

The preferred way to authenticate with the API is by adding an `Authorization` header containing your access token to your requests. The value of the `Authorization` header must be your access token.

Manage personal access tokens from the [**Account settings**](https://app.launchdarkly.com/settings/tokens) page.

### Authentication using session cookie

For testing purposes, you can make API calls directly from your web browser. If you are logged in to the LaunchDarkly application, the API will use your existing session to authenticate calls.

If you have a [role](https://docs.launchdarkly.com/home/team/built-in-roles) other than Admin, or have a [custom role](https://docs.launchdarkly.com/home/team/custom-roles) defined, you may not have permission to perform some API calls. You will receive a `401` response code in that case.

> ### Modifying the Origin header causes an error
>
> LaunchDarkly validates that the Origin header for any API request authenticated by a session cookie matches the expected Origin header. The expected Origin header is `https://app.launchdarkly.com`.
>
> If the Origin header does not match what's expected, LaunchDarkly returns an error. This error can prevent the LaunchDarkly app from working correctly.
>
> Any browser extension that intentionally changes the Origin header can cause this problem. For example, the `Allow-Control-Allow-Origin: *` Chrome extension changes the Origin header to `http://evil.com` and causes the app to fail.
>
> To prevent this error, do not modify your Origin header.
>
> LaunchDarkly does not require origin matching when authenticating with an access token, so this issue does not affect normal API usage.

## Representations

All resources expect and return JSON response bodies. Error responses also send a JSON body. To learn more about the error format of the API, read [Errors](https://apidocs.launchdarkly.com).

In practice this means that you always get a response with a `Content-Type` header set to `application/json`.

In addition, request bodies for `PATCH`, `POST`, and `PUT` requests must be encoded as JSON with a `Content-Type` header set to `application/json`.

### Summary and detailed representations

When you fetch a list of resources, the response includes only the most important attributes of each resource. This is a _summary representation_ of the resource. When you fetch an individual resource, such as a single feature flag, you receive a _detailed representation_ of the resource.

The best way to find a detailed representation is to follow links. Every summary representation includes a link to its detailed representation.

### Expanding responses

Sometimes the detailed representation of a resource does not include all of the attributes of the resource by default. If this is the case, the request method will clearly document this and describe which attributes you can include in an expanded response.

To include the additional attributes, append the `expand` request parameter to your request and add a comma-separated list of the attributes to include. For example, when you append `?expand=members,roles` to the [Get team](https://apidocs.launchdarkly.com) endpoint, the expanded response includes both of these attributes.

### Links and addressability

The best way to navigate the API is by following links. These are attributes in representations that link to other resources. The API always uses the same format for links:

- Links to other resources within the API are encapsulated in a `_links` object
- If the resource has a corresponding link to HTML content on the site, it is stored in a special `_site` link

Each link has two attributes:

- An `href`, which contains the URL
- A `type`, which describes the content type

For example, a feature resource might return the following:

```json
{
  "_links": {
    "parent": {
      "href": "/api/features",
      "type": "application/json"
    },
    "self": {
      "href": "/api/features/sort.order",
      "type": "application/json"
    }
  },
  "_site": {
    "href": "/features/sort.order",
    "type": "text/html"
  }
}
```

From this, you can navigate to the parent collection of features by following the `parent` link, or navigate to the site page for the feature by following the `_site` link.

Collections are always represented as a JSON object with an `items` attribute containing an array of representations. Like all other representations, collections have `_links` defined at the top level.

Paginated collections include `first`, `last`, `next`, and `prev` links containing a URL with the respective set of elements in the collection.

## Updates

Resources that accept partial updates use the `PATCH` verb. Most resources support the [JSON patch](https://apidocs.launchdarkly.com) format. Some resources also support the [JSON merge patch](https://apidocs.launchdarkly.com) format, and some resources support the [semantic patch](https://apidocs.launchdarkly.com) format, which is a way to specify the modifications to perform as a set of executable instructions. Each resource supports optional [comments](https://apidocs.launchdarkly.com) that you can submit with updates. Comments appear in outgoing webhooks, the audit log, and other integrations.

When a resource supports both JSON patch and semantic patch, we document both in the request method. However, the specific request body fields and descriptions included in our documentation only match one type of patch or the other.

### Updates using JSON patch

[JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) is a way to specify the modifications to perform on a resource. JSON patch uses paths and a limited set of operations to describe how to transform the current state of the resource into a new state. JSON patch documents are always arrays, where each element contains an operation, a path to the field to update, and the new value.

For example, in this feature flag representation:

```json
{
    "name": "New recommendations engine",
    "key": "engine.enable",
    "description": "This is the description",
    ...
}
```
You can change the feature flag's description with the following patch document:

```json
[{ "op": "replace", "path": "/description", "value": "This is the new description" }]
```

You can specify multiple modifications to perform in a single request. You can also test that certain preconditions are met before applying the patch:

```json
[
  { "op": "test", "path": "/version", "value": 10 },
  { "op": "replace", "path": "/description", "value": "The new description" }
]
```

The above patch request tests whether the feature flag's `version` is `10`, and if so, changes the feature flag's description.

Attributes that are not editable, such as a resource's `_links`, have names that start with an underscore.

### Updates using JSON merge patch

[JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) is another format for specifying the modifications to perform on a resource. JSON merge patch is less expressive than JSON patch. However, in many cases it is simpler to construct a merge patch document. For example, you can change a feature flag's description with the following merge patch document:

```json
{
  "description": "New flag description"
}
```

### Updates using semantic patch

Some resources support the semantic patch format. A semantic patch is a way to specify the modifications to perform on a resource as a set of executable instructions.

Semantic patch allows you to be explicit about intent using precise, custom instructions. In many cases, you can define semantic patch instructions independently of the current state of the resource. This can be useful when defining a change that may be applied at a future date.

To make a semantic patch request, you must append `domain-model=launchdarkly.semanticpatch` to your `Content-Type` header.

Here's how:

```
Content-Type: application/json; domain-model=launchdarkly.semanticpatch
```

If you call a semantic patch resource without this header, you will receive a `400` response because your semantic patch will be interpreted as a JSON patch.

The body of a semantic patch request takes the following properties:

* `comment` (string): (Optional) A description of the update.
* `environmentKey` (string): (Required for some resources only) The environment key.
* `instructions` (array): (Required) A list of actions the update should perform. Each action in the list must be an object with a `kind` property that indicates the instruction. If the instruction requires parameters, you must include those parameters as additional fields in the object. The documentation for each resource that supports semantic patch includes the available instructions and any additional parameters.

For example:

```json
{
  "comment": "optional comment",
  "instructions": [ {"kind": "turnFlagOn"} ]
}
```

If any instruction in the patch encounters an error, the endpoint returns an error and will not change the resource. In general, each instruction silently does nothing if the resource is already in the state you request.

### Updates with comments

You can submit optional comments with `PATCH` changes.

To submit a comment along with a JSON patch document, use the following format:

```json
{
  "comment": "This is a comment string",
  "patch": [{ "op": "replace", "path": "/description", "value": "The new description" }]
}
```

To submit a comment along with a JSON merge patch document, use the following format:

```json
{
  "comment": "This is a comment string",
  "merge": { "description": "New flag description" }
}
```

To submit a comment along with a semantic patch, use the following format:

```json
{
  "comment": "This is a comment string",
  "instructions": [ {"kind": "turnFlagOn"} ]
}
```

## Errors

The API always returns errors in a common format. Here's an example:

```json
{
  "code": "invalid_request",
  "message": "A feature with that key already exists",
  "id": "30ce6058-87da-11e4-b116-123b93f75cba"
}
```

The `code` indicates the general class of error. The `message` is a human-readable explanation of what went wrong. The `id` is a unique identifier. Use it when you're working with LaunchDarkly Support to debug a problem with a specific API call.

### HTTP status error response codes

| Code | Definition        | Description                                                                                       | Possible Solution                                                |
| ---- | ----------------- | ------------------------------------------------------------------------------------------- | ---------------------------------------------------------------- |
| 400  | Invalid request       | The request cannot be understood.                                    | Ensure JSON syntax in request body is correct.                   |
| 401  | Invalid access token      | Requestor is unauthorized or does not have permission for this API call.                                                | Ensure your API access token is valid and has the appropriate permissions.                                     |
| 403  | Forbidden         | Requestor does not have access to this resource.                                                | Ensure that the account member or access token has proper permissions set. |
| 404  | Invalid resource identifier | The requested resource is not valid. | Ensure that the resource is correctly identified by ID or key. |
| 405  | Method not allowed | The request method is not allowed on this resource. | Ensure that the HTTP verb is correct. |
| 409  | Conflict          | The API request can not be completed because it conflicts with a concurrent API request. | Retry your request.                                              |
| 422  | Unprocessable entity | The API request can not be completed because the update description can not be understood. | Ensure that the request body is correct for the type of patch you are using, either JSON patch or semantic patch.
| 429  | Too many requests | Read [Rate limiting](https://apidocs.launchdarkly.com).                                               | Wait and try again later.                                        |

## CORS

The LaunchDarkly API supports Cross Origin Resource Sharing (CORS) for AJAX requests from any origin. If an `Origin` header is given in a request, it will be echoed as an explicitly allowed origin. Otherwise the request returns a wildcard, `Access-Control-Allow-Origin: *`. For more information on CORS, read the [CORS W3C Recommendation](http://www.w3.org/TR/cors). Example CORS headers might look like:

```http
Access-Control-Allow-Headers: Accept, Content-Type, Content-Length, Accept-Encoding, Authorization
Access-Control-Allow-Methods: OPTIONS, GET, DELETE, PATCH
Access-Control-Allow-Origin: *
Access-Control-Max-Age: 300
```

You can make authenticated CORS calls just as you would make same-origin calls, using either [token or session-based authentication](https://apidocs.launchdarkly.com). If you are using session authentication, you should set the `withCredentials` property for your `xhr` request to `true`. You should never expose your access tokens to untrusted entities.

## Rate limiting

We use several rate limiting strategies to ensure the availability of our APIs. Rate-limited calls to our APIs return a `429` status code. Calls to our APIs include headers indicating the current rate limit status. The specific headers returned depend on the API route being called. The limits differ based on the route, authentication mechanism, and other factors. Routes that are not rate limited may not contain any of the headers described below.

> ### Rate limiting and SDKs
>
> LaunchDarkly SDKs are never rate limited and do not use the API endpoints defined here. LaunchDarkly uses a different set of approaches, including streaming/server-sent events and a global CDN, to ensure availability to the routes used by LaunchDarkly SDKs.

### Global rate limits

Authenticated requests are subject to a global limit. This is the maximum number of calls that your account can make to the API per ten seconds. All service and personal access tokens on the account share this limit, so exceeding the limit with one access token will impact other tokens. Calls that are subject to global rate limits may return the headers below:

| Header name                    | Description                                                                      |
| ------------------------------ | -------------------------------------------------------------------------------- |
| `X-Ratelimit-Global-Remaining` | The maximum number of requests the account is permitted to make per ten seconds. |
| `X-Ratelimit-Reset`            | The time at which the current rate limit window resets in epoch milliseconds.    |

We do not publicly document the specific number of calls that can be made globally. This limit may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limit.

### Route-level rate limits

Some authenticated routes have custom rate limits. These also reset every ten seconds. Any service or personal access tokens hitting the same route share this limit, so exceeding the limit with one access token may impact other tokens. Calls that are subject to route-level rate limits return the headers below:

| Header name                   | Description                                                                                           |
| ----------------------------- | ----------------------------------------------------------------------------------------------------- |
| `X-Ratelimit-Route-Remaining` | The maximum number of requests to the current route the account is permitted to make per ten seconds. |
| `X-Ratelimit-Reset`           | The time at which the current rate limit window resets in epoch milliseconds.                         |

A _route_ represents a specific URL pattern and verb. For example, the [Delete environment](https://apidocs.launchdarkly.com) endpoint is considered a single route, and each call to delete an environment counts against your route-level rate limit for that route.

We do not publicly document the specific number of calls that an account can make to each endpoint per ten seconds. These limits may change, and we encourage clients to program against the specification, relying on the two headers defined above, rather than hardcoding to the current limits.

### IP-based rate limiting

We also employ IP-based rate limiting on some API routes. If you hit an IP-based rate limit, your API response will include a `Retry-After` header indicating how long to wait before re-trying the call. Clients must wait at least `Retry-After` seconds before making additional calls to our API, and should employ jitter and backoff strategies to avoid triggering rate limits again.

## OpenAPI (Swagger) and client libraries

We have a [complete OpenAPI (Swagger) specification](https://app.launchdarkly.com/api/v2/openapi.json) for our API.

We auto-generate multiple client libraries based on our OpenAPI specification. To learn more, visit the [collection of client libraries on GitHub](https://github.com/search?q=topic%3Alaunchdarkly-api+org%3Alaunchdarkly&type=Repositories). You can also use this specification to generate client libraries to interact with our REST API in your language of choice.

Our OpenAPI specification is supported by several API-based tools such as Postman and Insomnia. In many cases, you can directly import our specification to explore our APIs.

## Method overriding

Some firewalls and HTTP clients restrict the use of verbs other than `GET` and `POST`. In those environments, our API endpoints that use `DELETE`, `PATCH`, and `PUT` verbs are inaccessible.

To avoid this issue, our API supports the `X-HTTP-Method-Override` header, allowing clients to "tunnel" `DELETE`, `PATCH`, and `PUT` requests using a `POST` request.

For example, to call a `PATCH` endpoint using a `POST` request, you can include `X-HTTP-Method-Override:PATCH` as a header.

## Beta resources

We sometimes release new API resources in **beta** status before we release them with general availability.

Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.

We try to promote resources into general availability as quickly as possible. This happens after sufficient testing and when we're satisfied that we no longer need to make backwards-incompatible changes.

We mark beta resources with a "Beta" callout in our documentation, pictured below:

> ### This feature is in beta
>
> To use this feature, pass in a header including the `LD-API-Version` key with value set to `beta`. Use this header with each call. To learn more, read [Beta resources](https://apidocs.launchdarkly.com).
>
> Resources that are in beta are still undergoing testing and development. They may change without notice, including becoming backwards incompatible.

### Using beta resources

To use a beta resource, you must include a header in the request. If you call a beta resource without this header, you receive a `403` response.

Use this header:

```
LD-API-Version: beta
```

## Federal environments

The version of LaunchDarkly that is available on domains controlled by the United States government is different from the version of LaunchDarkly available to the general public. If you are an employee or contractor for a United States federal agency and use LaunchDarkly in your work, you likely use the federal instance of LaunchDarkly.

If you are working in the federal instance of LaunchDarkly, the base URI for each request is `https://app.launchdarkly.us`. In the "Try it" sandbox for each request, click the request path to view the complete resource path for the federal environment.

To learn more, read [LaunchDarkly in federal environments](https://docs.launchdarkly.com/home/advanced/federal).

## Versioning

We try hard to keep our REST API backwards compatible, but we occasionally have to make backwards-incompatible changes in the process of shipping new features. These breaking changes can cause unexpected behavior if you don't prepare for them accordingly.

Updates to our REST API include support for the latest features in LaunchDarkly. We also release a new version of our REST API every time we make a breaking change. We provide simultaneous support for multiple API versions so you can migrate from your current API version to a new version at your own pace.

### Setting the API version per request

You can set the API version on a specific request by sending an `LD-API-Version` header, as shown in the example below:

```
LD-API-Version: 20220603
```

The header value is the version number of the API version you would like to request. The number for each version corresponds to the date the version was released in `yyyymmdd` format. In the example above the version `20220603` corresponds to June 03, 2022.

### Setting the API version per access token

When you create an access token, you must specify a specific version of the API to use. This ensures that integrations using this token cannot be broken by version changes.

Tokens created before versioning was released have their version set to `20160426`, which is the version of the API that existed before the current versioning scheme, so that they continue working the same way they did before versioning.

If you would like to upgrade your integration to use a new API version, you can explicitly set the header described above.

> ### Best practice: Set the header for every client or integration
>
> We recommend that you set the API version header explicitly in any client or integration you build.
>
> Only rely on the access token API version during manual testing.

### API version changelog

|<div style="width:75px">Version</div> | Changes | End of life (EOL)
|---|---|---|
| `20220603` | <ul><li>Changed the [list projects](https://apidocs.launchdarkly.com) return value:<ul><li>Response is now paginated with a default limit of `20`.</li><li>Added support for filter and sort.</li><li>The project `environments` field is now expandable. This field is omitted by default.</li></ul></li><li>Changed the [get project](https://apidocs.launchdarkly.com) return value:<ul><li>The `environments` field is now expandable. This field is omitted by default.</li></ul></li></ul> | Current |
| `20210729` | <ul><li>Changed the [create approval request](https://apidocs.launchdarkly.com) return value. It now returns HTTP Status Code `201` instead of `200`.</li><li> Changed the [get users](https://apidocs.launchdarkly.com) return value. It now returns a user record, not a user. </li><li>Added additional optional fields to environment, segments, flags, members, and segments, including the ability to create big segments. </li><li> Added default values for flag variations when new environments are created. </li><li>Added filtering and pagination for getting flags and members, including `limit`, `number`, `filter`, and `sort` query parameters. </li><li>Added endpoints for expiring user targets for flags and segments, scheduled changes, access tokens, Relay Proxy configuration, integrations and subscriptions, and approvals. </li></ul> | 2023-06-03 |
| `20191212` | <ul><li>[List feature flags](https://apidocs.launchdarkly.com) now defaults to sending summaries of feature flag configurations, equivalent to setting the query parameter `summary=true`. Summaries omit flag targeting rules and individual user targets from the payload. </li><li> Added endpoints for flags, flag status, projects, environments, audit logs, members, users, custom roles, segments, usage, streams, events, and data export. </li></ul> | 2022-07-29 |
| `20160426` | <ul><li>Initial versioning of API. Tokens created before versioning have their version set to this.</li></ul> | 2020-12-12 |


</div>

## Requirements

Building the API client library requires:

1. Java 1.8+
2. Maven (3.8.3+)/Gradle (7.2+)

If you are adding this library to an Android Application or Library:

3. Android 8.0+ (API Level 26+)

## Installation<a id="installation"></a>
<div align="center">
  <a href="https://konfigthis.com/sdk-sign-up?company=LaunchDarkly&language=Java">
    <img src="https://raw.githubusercontent.com/konfig-dev/brand-assets/HEAD/cta-images/java-cta.png" width="70%">
  </a>
</div>

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.konfigthis</groupId>
  <artifactId>launch-darkly-java-sdk</artifactId>
  <version>2.0</version>
  <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your `build.gradle`:

```groovy
// build.gradle
repositories {
  mavenCentral()
}

dependencies {
   implementation "com.konfigthis:launch-darkly-java-sdk:2.0"
}
```

### Android users

Make sure your `build.gradle` file as a `minSdk` version of at least 26:
```groovy
// build.gradle
android {
    defaultConfig {
        minSdk 26
    }
}
```

Also make sure your library or application has internet permissions in your `AndroidManifest.xml`:

```xml
<!--AndroidManifest.xml-->
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
    <uses-permission android:name="android.permission.INTERNET"/>
</manifest>
```

### Others

At first generate the JAR by executing:

```shell
mvn clean package
```

Then manually install the following JARs:

* `target/launch-darkly-java-sdk-2.0.jar`
* `target/lib/*.jar`

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.AccessTokensApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String description = "description_example"; // A description for the access token
    String name = "name_example"; // A human-friendly name for the access token
    String role = "reader"; // Built-in role for the token
    List<String> customRoleIds = Arrays.asList(); // A list of custom role IDs to use as access limits for the access token
    List<StatementPost> inlineRole = Arrays.asList(); // A JSON array of statements represented as JSON objects with three attributes: effect, resources, actions. May be used in place of a built-in or custom role.
    Boolean serviceToken = true; // Whether the token is a service token https://docs.launchdarkly.com/home/account-security/api-access-tokens#service-tokens
    Integer defaultApiVersion = 56; // The default API version for this token
    try {
      Token result = client
              .accessTokens
              .createNewToken()
              .description(description)
              .name(name)
              .role(role)
              .customRoleIds(customRoleIds)
              .inlineRole(inlineRole)
              .serviceToken(serviceToken)
              .defaultApiVersion(defaultApiVersion)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getOwnerId());
      System.out.println(result.getMemberId());
      System.out.println(result.getMember());
      System.out.println(result.getName());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLastModified());
      System.out.println(result.getCustomRoleIds());
      System.out.println(result.getInlineRole());
      System.out.println(result.getRole());
      System.out.println(result.getToken());
      System.out.println(result.getServiceToken());
      System.out.println(result.getLinks());
      System.out.println(result.getDefaultApiVersion());
      System.out.println(result.getLastUsed());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccessTokensApi#createNewToken");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Token> response = client
              .accessTokens
              .createNewToken()
              .description(description)
              .name(name)
              .role(role)
              .customRoleIds(customRoleIds)
              .inlineRole(inlineRole)
              .serviceToken(serviceToken)
              .defaultApiVersion(defaultApiVersion)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccessTokensApi#createNewToken");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://app.launchdarkly.com*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*AccessTokensApi* | [**createNewToken**](docs/AccessTokensApi.md#createNewToken) | **POST** /api/v2/tokens | Create access token
*AccessTokensApi* | [**deleteById**](docs/AccessTokensApi.md#deleteById) | **DELETE** /api/v2/tokens/{id} | Delete access token
*AccessTokensApi* | [**getById**](docs/AccessTokensApi.md#getById) | **GET** /api/v2/tokens/{id} | Get access token
*AccessTokensApi* | [**list**](docs/AccessTokensApi.md#list) | **GET** /api/v2/tokens | List access tokens
*AccessTokensApi* | [**resetSecretKey**](docs/AccessTokensApi.md#resetSecretKey) | **POST** /api/v2/tokens/{id}/reset | Reset access token
*AccessTokensApi* | [**updateSettings**](docs/AccessTokensApi.md#updateSettings) | **PATCH** /api/v2/tokens/{id} | Patch access token
*AccountMembersApi* | [**addToTeams**](docs/AccountMembersApi.md#addToTeams) | **POST** /api/v2/members/{id}/teams | Add a member to teams
*AccountMembersApi* | [**deleteById**](docs/AccountMembersApi.md#deleteById) | **DELETE** /api/v2/members/{id} | Delete account member
*AccountMembersApi* | [**getById**](docs/AccountMembersApi.md#getById) | **GET** /api/v2/members/{id} | Get account member
*AccountMembersApi* | [**inviteNewMembers**](docs/AccountMembersApi.md#inviteNewMembers) | **POST** /api/v2/members | Invite new members
*AccountMembersApi* | [**listMembers**](docs/AccountMembersApi.md#listMembers) | **GET** /api/v2/members | List account members
*AccountMembersApi* | [**updateMemberPatch**](docs/AccountMembersApi.md#updateMemberPatch) | **PATCH** /api/v2/members/{id} | Modify an account member
*AccountMembersBetaApi* | [**modifyMembersRoles**](docs/AccountMembersBetaApi.md#modifyMembersRoles) | **PATCH** /api/v2/members | Modify account members
*AccountUsageBetaApi* | [**getEvaluationsUsage**](docs/AccountUsageBetaApi.md#getEvaluationsUsage) | **GET** /api/v2/usage/evaluations/{projectKey}/{environmentKey}/{featureFlagKey} | Get evaluations usage
*AccountUsageBetaApi* | [**getEventsUsageData**](docs/AccountUsageBetaApi.md#getEventsUsageData) | **GET** /api/v2/usage/events/{type} | Get events usage
*AccountUsageBetaApi* | [**getExperimentationKeysUsage**](docs/AccountUsageBetaApi.md#getExperimentationKeysUsage) | **GET** /api/v2/usage/experimentation-keys | Get experimentation keys usage
*AccountUsageBetaApi* | [**getExperimentationUnitsUsage**](docs/AccountUsageBetaApi.md#getExperimentationUnitsUsage) | **GET** /api/v2/usage/experimentation-units | Get experimentation units usage
*AccountUsageBetaApi* | [**getMauUsageByCategory**](docs/AccountUsageBetaApi.md#getMauUsageByCategory) | **GET** /api/v2/usage/mau/bycategory | Get MAU usage by category
*AccountUsageBetaApi* | [**getMauUsageData**](docs/AccountUsageBetaApi.md#getMauUsageData) | **GET** /api/v2/usage/mau | Get MAU usage
*AccountUsageBetaApi* | [**getStreamUsage**](docs/AccountUsageBetaApi.md#getStreamUsage) | **GET** /api/v2/usage/streams/{source} | Get stream usage
*AccountUsageBetaApi* | [**getStreamUsageBySdkVersionData**](docs/AccountUsageBetaApi.md#getStreamUsageBySdkVersionData) | **GET** /api/v2/usage/streams/{source}/bysdkversion | Get stream usage by SDK version
*AccountUsageBetaApi* | [**listMauSdksByType**](docs/AccountUsageBetaApi.md#listMauSdksByType) | **GET** /api/v2/usage/mau/sdks | Get MAU SDKs by type
*AccountUsageBetaApi* | [**listSdkVersions**](docs/AccountUsageBetaApi.md#listSdkVersions) | **GET** /api/v2/usage/streams/{source}/sdkversions | Get stream usage SDK versions
*ApplicationsBetaApi* | [**getApplicationByKey**](docs/ApplicationsBetaApi.md#getApplicationByKey) | **GET** /api/v2/applications/{applicationKey} | Get application by key
*ApplicationsBetaApi* | [**getApplicationVersions**](docs/ApplicationsBetaApi.md#getApplicationVersions) | **GET** /api/v2/applications/{applicationKey}/versions | Get application versions by application key
*ApplicationsBetaApi* | [**listApplications**](docs/ApplicationsBetaApi.md#listApplications) | **GET** /api/v2/applications | Get applications
*ApplicationsBetaApi* | [**removeApplication**](docs/ApplicationsBetaApi.md#removeApplication) | **DELETE** /api/v2/applications/{applicationKey} | Delete application
*ApplicationsBetaApi* | [**removeVersion**](docs/ApplicationsBetaApi.md#removeVersion) | **DELETE** /api/v2/applications/{applicationKey}/versions/{versionKey} | Delete application version
*ApplicationsBetaApi* | [**updateApplicationPatch**](docs/ApplicationsBetaApi.md#updateApplicationPatch) | **PATCH** /api/v2/applications/{applicationKey} | Update application
*ApplicationsBetaApi* | [**updateVersionPatch**](docs/ApplicationsBetaApi.md#updateVersionPatch) | **PATCH** /api/v2/applications/{applicationKey}/versions/{versionKey} | Update application version
*ApprovalsApi* | [**applyRequestFlag**](docs/ApprovalsApi.md#applyRequestFlag) | **POST** /api/v2/approval-requests/{id}/apply | Apply approval request
*ApprovalsApi* | [**applyRequestFlag_0**](docs/ApprovalsApi.md#applyRequestFlag_0) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id}/apply | Apply approval request for a flag
*ApprovalsApi* | [**createFlagCopyRequest**](docs/ApprovalsApi.md#createFlagCopyRequest) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests-flag-copy | Create approval request to copy flag configurations across environments
*ApprovalsApi* | [**createRequestFlag**](docs/ApprovalsApi.md#createRequestFlag) | **POST** /api/v2/approval-requests | Create approval request
*ApprovalsApi* | [**createRequestFlag_0**](docs/ApprovalsApi.md#createRequestFlag_0) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests | Create approval request for a flag
*ApprovalsApi* | [**deleteApprovalRequestFlag**](docs/ApprovalsApi.md#deleteApprovalRequestFlag) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id} | Delete approval request for a flag
*ApprovalsApi* | [**deleteRequest**](docs/ApprovalsApi.md#deleteRequest) | **DELETE** /api/v2/approval-requests/{id} | Delete approval request
*ApprovalsApi* | [**getRequestById**](docs/ApprovalsApi.md#getRequestById) | **GET** /api/v2/approval-requests/{id} | Get approval request
*ApprovalsApi* | [**list**](docs/ApprovalsApi.md#list) | **GET** /api/v2/approval-requests | List approval requests
*ApprovalsApi* | [**listRequestsForFlag**](docs/ApprovalsApi.md#listRequestsForFlag) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests | List approval requests for a flag
*ApprovalsApi* | [**reviewFlagRequest**](docs/ApprovalsApi.md#reviewFlagRequest) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id}/reviews | Review approval request for a flag
*ApprovalsApi* | [**reviewRequest**](docs/ApprovalsApi.md#reviewRequest) | **POST** /api/v2/approval-requests/{id}/reviews | Review approval request
*ApprovalsApi* | [**singleRequest**](docs/ApprovalsApi.md#singleRequest) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id} | Get approval request for a flag
*AuditLogApi* | [**detailEntry**](docs/AuditLogApi.md#detailEntry) | **GET** /api/v2/auditlog/{id} | Get audit log entry
*AuditLogApi* | [**listAuditLogEntries**](docs/AuditLogApi.md#listAuditLogEntries) | **GET** /api/v2/auditlog | List audit log entries
*CodeReferencesApi* | [**asynchronouslyDeleteBranches**](docs/CodeReferencesApi.md#asynchronouslyDeleteBranches) | **POST** /api/v2/code-refs/repositories/{repo}/branch-delete-tasks | Delete branches
*CodeReferencesApi* | [**createExtinction**](docs/CodeReferencesApi.md#createExtinction) | **POST** /api/v2/code-refs/repositories/{repo}/branches/{branch}/extinction-events | Create extinction
*CodeReferencesApi* | [**createRepository**](docs/CodeReferencesApi.md#createRepository) | **POST** /api/v2/code-refs/repositories | Create repository
*CodeReferencesApi* | [**deleteRepository**](docs/CodeReferencesApi.md#deleteRepository) | **DELETE** /api/v2/code-refs/repositories/{repo} | Delete repository
*CodeReferencesApi* | [**getBranch**](docs/CodeReferencesApi.md#getBranch) | **GET** /api/v2/code-refs/repositories/{repo}/branches/{branch} | Get branch
*CodeReferencesApi* | [**getRepositoryByRepo**](docs/CodeReferencesApi.md#getRepositoryByRepo) | **GET** /api/v2/code-refs/repositories/{repo} | Get repository
*CodeReferencesApi* | [**getStatistics**](docs/CodeReferencesApi.md#getStatistics) | **GET** /api/v2/code-refs/statistics | Get links to code reference repositories for each project
*CodeReferencesApi* | [**getStatistics_0**](docs/CodeReferencesApi.md#getStatistics_0) | **GET** /api/v2/code-refs/statistics/{projectKey} | Get code references statistics for flags
*CodeReferencesApi* | [**listBranches**](docs/CodeReferencesApi.md#listBranches) | **GET** /api/v2/code-refs/repositories/{repo}/branches | List branches
*CodeReferencesApi* | [**listExtinctions**](docs/CodeReferencesApi.md#listExtinctions) | **GET** /api/v2/code-refs/extinctions | List extinctions
*CodeReferencesApi* | [**listRepositories**](docs/CodeReferencesApi.md#listRepositories) | **GET** /api/v2/code-refs/repositories | List repositories
*CodeReferencesApi* | [**updateRepositorySettings**](docs/CodeReferencesApi.md#updateRepositorySettings) | **PATCH** /api/v2/code-refs/repositories/{repo} | Update repository
*CodeReferencesApi* | [**upsertBranch**](docs/CodeReferencesApi.md#upsertBranch) | **PUT** /api/v2/code-refs/repositories/{repo}/branches/{branch} | Upsert branch
*ContextSettingsApi* | [**updateSettingsForContext**](docs/ContextSettingsApi.md#updateSettingsForContext) | **PUT** /api/v2/projects/{projectKey}/environments/{environmentKey}/contexts/{contextKind}/{contextKey}/flags/{featureFlagKey} | Update flag settings for context
*ContextsApi* | [**createOrUpdateKind**](docs/ContextsApi.md#createOrUpdateKind) | **PUT** /api/v2/projects/{projectKey}/context-kinds/{key} | Create or update context kind
*ContextsApi* | [**deleteContextInstance**](docs/ContextsApi.md#deleteContextInstance) | **DELETE** /api/v2/projects/{projectKey}/environments/{environmentKey}/context-instances/{id} | Delete context instances
*ContextsApi* | [**evaluateFlagsForContextInstance**](docs/ContextsApi.md#evaluateFlagsForContextInstance) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/flags/evaluate | Evaluate flags for context instance
*ContextsApi* | [**getAttributeNames**](docs/ContextsApi.md#getAttributeNames) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/context-attributes | Get context attribute names
*ContextsApi* | [**getByKindAndKey**](docs/ContextsApi.md#getByKindAndKey) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/contexts/{kind}/{key} | Get contexts
*ContextsApi* | [**getContextAttributeValues**](docs/ContextsApi.md#getContextAttributeValues) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/context-attributes/{attributeName} | Get context attribute values
*ContextsApi* | [**getContextInstances**](docs/ContextsApi.md#getContextInstances) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/context-instances/{id} | Get context instances
*ContextsApi* | [**listContextKindsByProject**](docs/ContextsApi.md#listContextKindsByProject) | **GET** /api/v2/projects/{projectKey}/context-kinds | Get context kinds
*ContextsApi* | [**searchContextInstances**](docs/ContextsApi.md#searchContextInstances) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/context-instances/search | Search for context instances
*ContextsApi* | [**searchContexts**](docs/ContextsApi.md#searchContexts) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/contexts/search | Search for contexts
*CustomRolesApi* | [**createNewRole**](docs/CustomRolesApi.md#createNewRole) | **POST** /api/v2/roles | Create custom role
*CustomRolesApi* | [**deleteRoleByCustomKey**](docs/CustomRolesApi.md#deleteRoleByCustomKey) | **DELETE** /api/v2/roles/{customRoleKey} | Delete custom role
*CustomRolesApi* | [**getByCustomKey**](docs/CustomRolesApi.md#getByCustomKey) | **GET** /api/v2/roles/{customRoleKey} | Get custom role
*CustomRolesApi* | [**listCustomRoles**](docs/CustomRolesApi.md#listCustomRoles) | **GET** /api/v2/roles | List custom roles
*CustomRolesApi* | [**updateSingleCustomRole**](docs/CustomRolesApi.md#updateSingleCustomRole) | **PATCH** /api/v2/roles/{customRoleKey} | Update custom role
*DataExportDestinationsApi* | [**createDestination**](docs/DataExportDestinationsApi.md#createDestination) | **POST** /api/v2/destinations/{projectKey}/{environmentKey} | Create Data Export destination
*DataExportDestinationsApi* | [**deleteById**](docs/DataExportDestinationsApi.md#deleteById) | **DELETE** /api/v2/destinations/{projectKey}/{environmentKey}/{id} | Delete Data Export destination
*DataExportDestinationsApi* | [**getAll**](docs/DataExportDestinationsApi.md#getAll) | **GET** /api/v2/destinations | List destinations
*DataExportDestinationsApi* | [**getSingleById**](docs/DataExportDestinationsApi.md#getSingleById) | **GET** /api/v2/destinations/{projectKey}/{environmentKey}/{id} | Get destination
*DataExportDestinationsApi* | [**updateDestinationPatch**](docs/DataExportDestinationsApi.md#updateDestinationPatch) | **PATCH** /api/v2/destinations/{projectKey}/{environmentKey}/{id} | Update Data Export destination
*EnvironmentsApi* | [**createNewEnvironment**](docs/EnvironmentsApi.md#createNewEnvironment) | **POST** /api/v2/projects/{projectKey}/environments | Create environment
*EnvironmentsApi* | [**getByProjectAndKey**](docs/EnvironmentsApi.md#getByProjectAndKey) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey} | Get environment
*EnvironmentsApi* | [**listEnvironments**](docs/EnvironmentsApi.md#listEnvironments) | **GET** /api/v2/projects/{projectKey}/environments | List environments
*EnvironmentsApi* | [**removeByEnvironmentKey**](docs/EnvironmentsApi.md#removeByEnvironmentKey) | **DELETE** /api/v2/projects/{projectKey}/environments/{environmentKey} | Delete environment
*EnvironmentsApi* | [**resetMobileSdkKey**](docs/EnvironmentsApi.md#resetMobileSdkKey) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/mobileKey | Reset environment mobile SDK key
*EnvironmentsApi* | [**resetSdkKey**](docs/EnvironmentsApi.md#resetSdkKey) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/apiKey | Reset environment SDK key
*EnvironmentsApi* | [**updateEnvironmentPatch**](docs/EnvironmentsApi.md#updateEnvironmentPatch) | **PATCH** /api/v2/projects/{projectKey}/environments/{environmentKey} | Update environment
*ExperimentsBetaApi* | [**createIteration**](docs/ExperimentsBetaApi.md#createIteration) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey}/iterations | Create iteration
*ExperimentsBetaApi* | [**createNew**](docs/ExperimentsBetaApi.md#createNew) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments | Create experiment
*ExperimentsBetaApi* | [**getDetails**](docs/ExperimentsBetaApi.md#getDetails) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey} | Get experiment
*ExperimentsBetaApi* | [**getExperimentMetricResults**](docs/ExperimentsBetaApi.md#getExperimentMetricResults) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey}/metrics/{metricKey}/results | Get experiment results
*ExperimentsBetaApi* | [**getExperimentationSettings**](docs/ExperimentsBetaApi.md#getExperimentationSettings) | **GET** /api/v2/projects/{projectKey}/experimentation-settings | Get experimentation settings
*ExperimentsBetaApi* | [**getLegacyExperimentResults**](docs/ExperimentsBetaApi.md#getLegacyExperimentResults) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/experiments/{environmentKey}/{metricKey} | Get legacy experiment results (deprecated)
*ExperimentsBetaApi* | [**getResultsForMetricGroup**](docs/ExperimentsBetaApi.md#getResultsForMetricGroup) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey}/metric-groups/{metricGroupKey}/results | Get experiment results for metric group
*ExperimentsBetaApi* | [**listExperimentsInEnvironment**](docs/ExperimentsBetaApi.md#listExperimentsInEnvironment) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments | Get experiments
*ExperimentsBetaApi* | [**updateExperimentationSettings**](docs/ExperimentsBetaApi.md#updateExperimentationSettings) | **PUT** /api/v2/projects/{projectKey}/experimentation-settings | Update experimentation settings
*ExperimentsBetaApi* | [**updateSemanticPatch**](docs/ExperimentsBetaApi.md#updateSemanticPatch) | **PATCH** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey} | Patch experiment
*FeatureFlagsApi* | [**acrossEnvironments**](docs/FeatureFlagsApi.md#acrossEnvironments) | **GET** /api/v2/flag-status/{projectKey}/{featureFlagKey} | Get flag status across environments
*FeatureFlagsApi* | [**copyFlagSettings**](docs/FeatureFlagsApi.md#copyFlagSettings) | **POST** /api/v2/flags/{projectKey}/{featureFlagKey}/copy | Copy feature flag
*FeatureFlagsApi* | [**createFeatureFlag**](docs/FeatureFlagsApi.md#createFeatureFlag) | **POST** /api/v2/flags/{projectKey} | Create a feature flag
*FeatureFlagsApi* | [**deleteFlag**](docs/FeatureFlagsApi.md#deleteFlag) | **DELETE** /api/v2/flags/{projectKey}/{featureFlagKey} | Delete feature flag
*FeatureFlagsApi* | [**getContextInstanceSegmentsMembershipByEnv**](docs/FeatureFlagsApi.md#getContextInstanceSegmentsMembershipByEnv) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/expiring-targets/{environmentKey} | Get expiring context targets for feature flag
*FeatureFlagsApi* | [**getStatus**](docs/FeatureFlagsApi.md#getStatus) | **GET** /api/v2/flag-statuses/{projectKey}/{environmentKey}/{featureFlagKey} | Get feature flag status
*FeatureFlagsApi* | [**list**](docs/FeatureFlagsApi.md#list) | **GET** /api/v2/flags/{projectKey} | List feature flags
*FeatureFlagsApi* | [**listExpiringUserTargets**](docs/FeatureFlagsApi.md#listExpiringUserTargets) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/expiring-user-targets/{environmentKey} | Get expiring user targets for feature flag
*FeatureFlagsApi* | [**listFlagStatuses**](docs/FeatureFlagsApi.md#listFlagStatuses) | **GET** /api/v2/flag-statuses/{projectKey}/{environmentKey} | List feature flag statuses
*FeatureFlagsApi* | [**singleFlagByKey**](docs/FeatureFlagsApi.md#singleFlagByKey) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey} | Get feature flag
*FeatureFlagsApi* | [**updateExpiringContextTargets**](docs/FeatureFlagsApi.md#updateExpiringContextTargets) | **PATCH** /api/v2/flags/{projectKey}/{featureFlagKey}/expiring-targets/{environmentKey} | Update expiring context targets on feature flag
*FeatureFlagsApi* | [**updateExpiringUserTargets**](docs/FeatureFlagsApi.md#updateExpiringUserTargets) | **PATCH** /api/v2/flags/{projectKey}/{featureFlagKey}/expiring-user-targets/{environmentKey} | Update expiring user targets on feature flag
*FeatureFlagsApi* | [**updateFeatureFlag**](docs/FeatureFlagsApi.md#updateFeatureFlag) | **PATCH** /api/v2/flags/{projectKey}/{featureFlagKey} | Update feature flag
*FeatureFlagsBetaApi* | [**getMigrationSafetyIssues**](docs/FeatureFlagsBetaApi.md#getMigrationSafetyIssues) | **POST** /api/v2/projects/{projectKey}/flags/{flagKey}/environments/{environmentKey}/migration-safety-issues | Get migration safety issues
*FeatureFlagsBetaApi* | [**listDependentFlags**](docs/FeatureFlagsBetaApi.md#listDependentFlags) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/dependent-flags | List dependent feature flags
*FeatureFlagsBetaApi* | [**listDependentFlagsByEnv**](docs/FeatureFlagsBetaApi.md#listDependentFlagsByEnv) | **GET** /api/v2/flags/{projectKey}/{environmentKey}/{featureFlagKey}/dependent-flags | List dependent feature flags by environment
*FlagLinksBetaApi* | [**createFlagLink**](docs/FlagLinksBetaApi.md#createFlagLink) | **POST** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey} | Create flag link
*FlagLinksBetaApi* | [**deleteFlagLink**](docs/FlagLinksBetaApi.md#deleteFlagLink) | **DELETE** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey}/{id} | Delete flag link
*FlagLinksBetaApi* | [**listLinks**](docs/FlagLinksBetaApi.md#listLinks) | **GET** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey} | List flag links
*FlagLinksBetaApi* | [**updateFlagLink**](docs/FlagLinksBetaApi.md#updateFlagLink) | **PATCH** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey}/{id} | Update flag link
*FlagTriggersApi* | [**createTriggerWorkflow**](docs/FlagTriggersApi.md#createTriggerWorkflow) | **POST** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey} | Create flag trigger
*FlagTriggersApi* | [**deleteById**](docs/FlagTriggersApi.md#deleteById) | **DELETE** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey}/{id} | Delete flag trigger
*FlagTriggersApi* | [**getTriggerById**](docs/FlagTriggersApi.md#getTriggerById) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey}/{id} | Get flag trigger by ID
*FlagTriggersApi* | [**listTriggerWorkflows**](docs/FlagTriggersApi.md#listTriggerWorkflows) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey} | List flag triggers
*FlagTriggersApi* | [**updateTriggerWorkflowPatch**](docs/FlagTriggersApi.md#updateTriggerWorkflowPatch) | **PATCH** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey}/{id} | Update flag trigger
*FollowFlagsApi* | [**flagFollowersList**](docs/FollowFlagsApi.md#flagFollowersList) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/followers | Get followers of a flag in a project and environment
*FollowFlagsApi* | [**getAllFlagFollowers**](docs/FollowFlagsApi.md#getAllFlagFollowers) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/followers | Get followers of all flags in a given project and environment
*FollowFlagsApi* | [**memberFollower**](docs/FollowFlagsApi.md#memberFollower) | **PUT** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/followers/{memberId} | Add a member as a follower of a flag in a project and environment
*FollowFlagsApi* | [**removeFollower**](docs/FollowFlagsApi.md#removeFollower) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/followers/{memberId} | Remove a member as a follower of a flag in a project and environment
*InsightsChartsBetaApi* | [**deploymentFrequencyChartData**](docs/InsightsChartsBetaApi.md#deploymentFrequencyChartData) | **GET** /api/v2/engineering-insights/charts/deployments/frequency | Get deployment frequency chart data
*InsightsChartsBetaApi* | [**getFlagStatusChartData**](docs/InsightsChartsBetaApi.md#getFlagStatusChartData) | **GET** /api/v2/engineering-insights/charts/flags/status | Get flag status chart data
*InsightsChartsBetaApi* | [**leadTimeChartData**](docs/InsightsChartsBetaApi.md#leadTimeChartData) | **GET** /api/v2/engineering-insights/charts/lead-time | Get lead time chart data
*InsightsChartsBetaApi* | [**releaseFrequencyData**](docs/InsightsChartsBetaApi.md#releaseFrequencyData) | **GET** /api/v2/engineering-insights/charts/releases/frequency | Get release frequency chart data
*InsightsChartsBetaApi* | [**staleFlagsChartData**](docs/InsightsChartsBetaApi.md#staleFlagsChartData) | **GET** /api/v2/engineering-insights/charts/flags/stale | Get stale flags chart data
*InsightsDeploymentsBetaApi* | [**createDeploymentEvent**](docs/InsightsDeploymentsBetaApi.md#createDeploymentEvent) | **POST** /api/v2/engineering-insights/deployment-events | Create deployment event
*InsightsDeploymentsBetaApi* | [**getDeploymentById**](docs/InsightsDeploymentsBetaApi.md#getDeploymentById) | **GET** /api/v2/engineering-insights/deployments/{deploymentID} | Get deployment
*InsightsDeploymentsBetaApi* | [**listDeployments**](docs/InsightsDeploymentsBetaApi.md#listDeployments) | **GET** /api/v2/engineering-insights/deployments | List deployments
*InsightsDeploymentsBetaApi* | [**updateDeploymentById**](docs/InsightsDeploymentsBetaApi.md#updateDeploymentById) | **PATCH** /api/v2/engineering-insights/deployments/{deploymentID} | Update deployment
*InsightsFlagEventsBetaApi* | [**listFlagEvents**](docs/InsightsFlagEventsBetaApi.md#listFlagEvents) | **GET** /api/v2/engineering-insights/flag-events | List flag events
*InsightsPullRequestsBetaApi* | [**listPullRequests**](docs/InsightsPullRequestsBetaApi.md#listPullRequests) | **GET** /api/v2/engineering-insights/pull-requests | List pull requests
*InsightsRepositoriesBetaApi* | [**associateRepositoriesAndProjects**](docs/InsightsRepositoriesBetaApi.md#associateRepositoriesAndProjects) | **PUT** /api/v2/engineering-insights/repositories/projects | Associate repositories with projects
*InsightsRepositoriesBetaApi* | [**listRepositories**](docs/InsightsRepositoriesBetaApi.md#listRepositories) | **GET** /api/v2/engineering-insights/repositories | List repositories
*InsightsRepositoriesBetaApi* | [**removeRepositoryProjectAssociation**](docs/InsightsRepositoriesBetaApi.md#removeRepositoryProjectAssociation) | **DELETE** /api/v2/engineering-insights/repositories/{repositoryKey}/projects/{projectKey} | Remove repository project association
*InsightsScoresBetaApi* | [**createInsightGroup**](docs/InsightsScoresBetaApi.md#createInsightGroup) | **POST** /api/v2/engineering-insights/insights/group | Create insight group
*InsightsScoresBetaApi* | [**deleteInsightGroup**](docs/InsightsScoresBetaApi.md#deleteInsightGroup) | **DELETE** /api/v2/engineering-insights/insights/groups/{insightGroupKey} | Delete insight group
*InsightsScoresBetaApi* | [**expandGroupInsightScores**](docs/InsightsScoresBetaApi.md#expandGroupInsightScores) | **GET** /api/v2/engineering-insights/insights/groups/{insightGroupKey} | Get insight group
*InsightsScoresBetaApi* | [**getInsightScores**](docs/InsightsScoresBetaApi.md#getInsightScores) | **GET** /api/v2/engineering-insights/insights/scores | Get insight scores
*InsightsScoresBetaApi* | [**listGroupInsightScores**](docs/InsightsScoresBetaApi.md#listGroupInsightScores) | **GET** /api/v2/engineering-insights/insights/groups | List insight groups
*InsightsScoresBetaApi* | [**updateInsightGroupPatch**](docs/InsightsScoresBetaApi.md#updateInsightGroupPatch) | **PATCH** /api/v2/engineering-insights/insights/groups/{insightGroupKey} | Patch insight group
*IntegrationAuditLogSubscriptionsApi* | [**createSubscription**](docs/IntegrationAuditLogSubscriptionsApi.md#createSubscription) | **POST** /api/v2/integrations/{integrationKey} | Create audit log subscription
*IntegrationAuditLogSubscriptionsApi* | [**deleteSubscription**](docs/IntegrationAuditLogSubscriptionsApi.md#deleteSubscription) | **DELETE** /api/v2/integrations/{integrationKey}/{id} | Delete audit log subscription
*IntegrationAuditLogSubscriptionsApi* | [**getById**](docs/IntegrationAuditLogSubscriptionsApi.md#getById) | **GET** /api/v2/integrations/{integrationKey}/{id} | Get audit log subscription by ID
*IntegrationAuditLogSubscriptionsApi* | [**listByIntegration**](docs/IntegrationAuditLogSubscriptionsApi.md#listByIntegration) | **GET** /api/v2/integrations/{integrationKey} | Get audit log subscriptions by integration
*IntegrationAuditLogSubscriptionsApi* | [**updateSubscription**](docs/IntegrationAuditLogSubscriptionsApi.md#updateSubscription) | **PATCH** /api/v2/integrations/{integrationKey}/{id} | Update audit log subscription
*IntegrationDeliveryConfigurationsBetaApi* | [**createDeliveryConfiguration**](docs/IntegrationDeliveryConfigurationsBetaApi.md#createDeliveryConfiguration) | **POST** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey} | Create delivery configuration
*IntegrationDeliveryConfigurationsBetaApi* | [**deleteDeliveryConfiguration**](docs/IntegrationDeliveryConfigurationsBetaApi.md#deleteDeliveryConfiguration) | **DELETE** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id} | Delete delivery configuration
*IntegrationDeliveryConfigurationsBetaApi* | [**getById**](docs/IntegrationDeliveryConfigurationsBetaApi.md#getById) | **GET** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id} | Get delivery configuration by ID
*IntegrationDeliveryConfigurationsBetaApi* | [**getDeliveryConfigurationsByEnvironment**](docs/IntegrationDeliveryConfigurationsBetaApi.md#getDeliveryConfigurationsByEnvironment) | **GET** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey} | Get delivery configurations by environment
*IntegrationDeliveryConfigurationsBetaApi* | [**listDeliveryConfigurations**](docs/IntegrationDeliveryConfigurationsBetaApi.md#listDeliveryConfigurations) | **GET** /api/v2/integration-capabilities/featureStore | List all delivery configurations
*IntegrationDeliveryConfigurationsBetaApi* | [**updateDeliveryConfiguration**](docs/IntegrationDeliveryConfigurationsBetaApi.md#updateDeliveryConfiguration) | **PATCH** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id} | Update delivery configuration
*IntegrationDeliveryConfigurationsBetaApi* | [**validateDeliveryConfiguration**](docs/IntegrationDeliveryConfigurationsBetaApi.md#validateDeliveryConfiguration) | **POST** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id}/validate | Validate delivery configuration
*IntegrationsBetaApi* | [**createPersistentStoreIntegration**](docs/IntegrationsBetaApi.md#createPersistentStoreIntegration) | **POST** /api/v2/integration-capabilities/big-segment-store/{projectKey}/{environmentKey}/{integrationKey} | Create big segment store integration
*IntegrationsBetaApi* | [**deleteBigSegmentStoreIntegration**](docs/IntegrationsBetaApi.md#deleteBigSegmentStoreIntegration) | **DELETE** /api/v2/integration-capabilities/big-segment-store/{projectKey}/{environmentKey}/{integrationKey}/{integrationId} | Delete big segment store integration
*IntegrationsBetaApi* | [**getBigSegmentStoreIntegrationById**](docs/IntegrationsBetaApi.md#getBigSegmentStoreIntegrationById) | **GET** /api/v2/integration-capabilities/big-segment-store/{projectKey}/{environmentKey}/{integrationKey}/{integrationId} | Get big segment store integration by ID
*IntegrationsBetaApi* | [**listBigSegmentStoreIntegrations**](docs/IntegrationsBetaApi.md#listBigSegmentStoreIntegrations) | **GET** /api/v2/integration-capabilities/big-segment-store | List all big segment store integrations
*IntegrationsBetaApi* | [**updateBigSegmentStore**](docs/IntegrationsBetaApi.md#updateBigSegmentStore) | **PATCH** /api/v2/integration-capabilities/big-segment-store/{projectKey}/{environmentKey}/{integrationKey}/{integrationId} | Update big segment store integration
*MetricsApi* | [**createNewMetric**](docs/MetricsApi.md#createNewMetric) | **POST** /api/v2/metrics/{projectKey} | Create metric
*MetricsApi* | [**deleteByProjectAndMetricKey**](docs/MetricsApi.md#deleteByProjectAndMetricKey) | **DELETE** /api/v2/metrics/{projectKey}/{metricKey} | Delete metric
*MetricsApi* | [**getSingleMetric**](docs/MetricsApi.md#getSingleMetric) | **GET** /api/v2/metrics/{projectKey}/{metricKey} | Get metric
*MetricsApi* | [**listForProject**](docs/MetricsApi.md#listForProject) | **GET** /api/v2/metrics/{projectKey} | List metrics
*MetricsApi* | [**updateByJsonPatch**](docs/MetricsApi.md#updateByJsonPatch) | **PATCH** /api/v2/metrics/{projectKey}/{metricKey} | Update metric
*MetricsBetaApi* | [**createMetricGroup**](docs/MetricsBetaApi.md#createMetricGroup) | **POST** /api/v2/projects/{projectKey}/metric-groups | Create metric group
*MetricsBetaApi* | [**deleteMetricGroup**](docs/MetricsBetaApi.md#deleteMetricGroup) | **DELETE** /api/v2/projects/{projectKey}/metric-groups/{metricGroupKey} | Delete metric group
*MetricsBetaApi* | [**getMetricGroupDetails**](docs/MetricsBetaApi.md#getMetricGroupDetails) | **GET** /api/v2/projects/{projectKey}/metric-groups/{metricGroupKey} | Get metric group
*MetricsBetaApi* | [**listMetricGroups**](docs/MetricsBetaApi.md#listMetricGroups) | **GET** /api/v2/projects/{projectKey}/metric-groups | List metric groups
*MetricsBetaApi* | [**updateMetricGroupByKey**](docs/MetricsBetaApi.md#updateMetricGroupByKey) | **PATCH** /api/v2/projects/{projectKey}/metric-groups/{metricGroupKey} | Patch metric group
*OAuth2ClientsApi* | [**createClient**](docs/OAuth2ClientsApi.md#createClient) | **POST** /api/v2/oauth/clients | Create a LaunchDarkly OAuth 2.0 client
*OAuth2ClientsApi* | [**deleteClientById**](docs/OAuth2ClientsApi.md#deleteClientById) | **DELETE** /api/v2/oauth/clients/{clientId} | Delete OAuth 2.0 client
*OAuth2ClientsApi* | [**getClientById**](docs/OAuth2ClientsApi.md#getClientById) | **GET** /api/v2/oauth/clients/{clientId} | Get client by ID
*OAuth2ClientsApi* | [**list**](docs/OAuth2ClientsApi.md#list) | **GET** /api/v2/oauth/clients | Get clients
*OAuth2ClientsApi* | [**updateClientById**](docs/OAuth2ClientsApi.md#updateClientById) | **PATCH** /api/v2/oauth/clients/{clientId} | Patch client by ID
*OtherApi* | [**getIpList**](docs/OtherApi.md#getIpList) | **GET** /api/v2/public-ip-list | Gets the public IP list
*OtherApi* | [**getOpenapiSpec**](docs/OtherApi.md#getOpenapiSpec) | **GET** /api/v2/openapi.json | Gets the OpenAPI spec in json
*OtherApi* | [**getResourceCategories**](docs/OtherApi.md#getResourceCategories) | **GET** /api/v2 | Root resource
*OtherApi* | [**getVersionInformation**](docs/OtherApi.md#getVersionInformation) | **GET** /api/v2/versions | Get version information
*ProjectsApi* | [**createNewProject**](docs/ProjectsApi.md#createNewProject) | **POST** /api/v2/projects | Create project
*ProjectsApi* | [**deleteByProjectKey**](docs/ProjectsApi.md#deleteByProjectKey) | **DELETE** /api/v2/projects/{projectKey} | Delete project
*ProjectsApi* | [**getFlagDefaults**](docs/ProjectsApi.md#getFlagDefaults) | **GET** /api/v2/projects/{projectKey}/flag-defaults | Get flag defaults for project
*ProjectsApi* | [**listProjectsDefault**](docs/ProjectsApi.md#listProjectsDefault) | **GET** /api/v2/projects | List projects
*ProjectsApi* | [**singleByProjectKey**](docs/ProjectsApi.md#singleByProjectKey) | **GET** /api/v2/projects/{projectKey} | Get project
*ProjectsApi* | [**updateFlagDefault**](docs/ProjectsApi.md#updateFlagDefault) | **PATCH** /api/v2/projects/{projectKey}/flag-defaults | Update flag default for project
*ProjectsApi* | [**updateFlagDefaultsForProject**](docs/ProjectsApi.md#updateFlagDefaultsForProject) | **PUT** /api/v2/projects/{projectKey}/flag-defaults | Create or update flag defaults for project
*ProjectsApi* | [**updateProjectPatch**](docs/ProjectsApi.md#updateProjectPatch) | **PATCH** /api/v2/projects/{projectKey} | Update project
*RelayProxyConfigurationsApi* | [**createNewConfig**](docs/RelayProxyConfigurationsApi.md#createNewConfig) | **POST** /api/v2/account/relay-auto-configs | Create a new Relay Proxy config
*RelayProxyConfigurationsApi* | [**deleteById**](docs/RelayProxyConfigurationsApi.md#deleteById) | **DELETE** /api/v2/account/relay-auto-configs/{id} | Delete Relay Proxy config by ID
*RelayProxyConfigurationsApi* | [**getSingleById**](docs/RelayProxyConfigurationsApi.md#getSingleById) | **GET** /api/v2/account/relay-auto-configs/{id} | Get Relay Proxy config
*RelayProxyConfigurationsApi* | [**list**](docs/RelayProxyConfigurationsApi.md#list) | **GET** /api/v2/account/relay-auto-configs | List Relay Proxy configs
*RelayProxyConfigurationsApi* | [**resetSecretKeyWithExpiry**](docs/RelayProxyConfigurationsApi.md#resetSecretKeyWithExpiry) | **POST** /api/v2/account/relay-auto-configs/{id}/reset | Reset Relay Proxy configuration key
*RelayProxyConfigurationsApi* | [**updateConfigPatch**](docs/RelayProxyConfigurationsApi.md#updateConfigPatch) | **PATCH** /api/v2/account/relay-auto-configs/{id} | Update a Relay Proxy config
*ReleasePipelinesBetaApi* | [**createNewPipeline**](docs/ReleasePipelinesBetaApi.md#createNewPipeline) | **POST** /api/v2/projects/{projectKey}/release-pipelines | Create a release pipeline
*ReleasePipelinesBetaApi* | [**deletePipeline**](docs/ReleasePipelinesBetaApi.md#deletePipeline) | **DELETE** /api/v2/projects/{projectKey}/release-pipelines/{pipelineKey} | Delete release pipeline
*ReleasePipelinesBetaApi* | [**getAllReleasePipelines**](docs/ReleasePipelinesBetaApi.md#getAllReleasePipelines) | **GET** /api/v2/projects/{projectKey}/release-pipelines | Get all release pipelines
*ReleasePipelinesBetaApi* | [**getByPipeKey**](docs/ReleasePipelinesBetaApi.md#getByPipeKey) | **GET** /api/v2/projects/{projectKey}/release-pipelines/{pipelineKey} | Get release pipeline by key
*ReleasePipelinesBetaApi* | [**updatePipelinePatch**](docs/ReleasePipelinesBetaApi.md#updatePipelinePatch) | **PATCH** /api/v2/projects/{projectKey}/release-pipelines/{pipelineKey} | Update a release pipeline
*ReleasesBetaApi* | [**getCurrentRelease**](docs/ReleasesBetaApi.md#getCurrentRelease) | **GET** /api/v2/flags/{projectKey}/{flagKey}/release | Get release for flag
*ReleasesBetaApi* | [**updateActiveReleasePatch**](docs/ReleasesBetaApi.md#updateActiveReleasePatch) | **PATCH** /api/v2/flags/{projectKey}/{flagKey}/release | Patch release for flag
*ScheduledChangesApi* | [**createWorkflow**](docs/ScheduledChangesApi.md#createWorkflow) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes | Create scheduled changes workflow
*ScheduledChangesApi* | [**deleteWorkflow**](docs/ScheduledChangesApi.md#deleteWorkflow) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes/{id} | Delete scheduled changes workflow
*ScheduledChangesApi* | [**getById**](docs/ScheduledChangesApi.md#getById) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes/{id} | Get a scheduled change
*ScheduledChangesApi* | [**listChanges**](docs/ScheduledChangesApi.md#listChanges) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes | List scheduled changes
*ScheduledChangesApi* | [**updateWorkflow**](docs/ScheduledChangesApi.md#updateWorkflow) | **PATCH** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes/{id} | Update scheduled changes workflow
*SegmentsApi* | [**createSegment**](docs/SegmentsApi.md#createSegment) | **POST** /api/v2/segments/{projectKey}/{environmentKey} | Create segment
*SegmentsApi* | [**evaluateSegmentMemberships**](docs/SegmentsApi.md#evaluateSegmentMemberships) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/segments/evaluate | List segment memberships for context instance
*SegmentsApi* | [**getContextMembership**](docs/SegmentsApi.md#getContextMembership) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/contexts/{contextKey} | Get big segment membership for context
*SegmentsApi* | [**getExpiringTargets**](docs/SegmentsApi.md#getExpiringTargets) | **GET** /api/v2/segments/{projectKey}/{segmentKey}/expiring-targets/{environmentKey} | Get expiring targets for segment
*SegmentsApi* | [**getExpiringUserTargets**](docs/SegmentsApi.md#getExpiringUserTargets) | **GET** /api/v2/segments/{projectKey}/{segmentKey}/expiring-user-targets/{environmentKey} | Get expiring user targets for segment
*SegmentsApi* | [**getSegmentList**](docs/SegmentsApi.md#getSegmentList) | **GET** /api/v2/segments/{projectKey}/{environmentKey} | List segments
*SegmentsApi* | [**getUserMembershipStatus**](docs/SegmentsApi.md#getUserMembershipStatus) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/users/{userKey} | Get big segment membership for user
*SegmentsApi* | [**removeSegment**](docs/SegmentsApi.md#removeSegment) | **DELETE** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey} | Delete segment
*SegmentsApi* | [**singleSegmentByKey**](docs/SegmentsApi.md#singleSegmentByKey) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey} | Get segment
*SegmentsApi* | [**updateContextTargets**](docs/SegmentsApi.md#updateContextTargets) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/contexts | Update context targets on a big segment
*SegmentsApi* | [**updateExpiringTargetsForSegment**](docs/SegmentsApi.md#updateExpiringTargetsForSegment) | **PATCH** /api/v2/segments/{projectKey}/{segmentKey}/expiring-targets/{environmentKey} | Update expiring targets for segment
*SegmentsApi* | [**updateExpiringTargetsForSegment_0**](docs/SegmentsApi.md#updateExpiringTargetsForSegment_0) | **PATCH** /api/v2/segments/{projectKey}/{segmentKey}/expiring-user-targets/{environmentKey} | Update expiring user targets for segment
*SegmentsApi* | [**updateSemanticPatch**](docs/SegmentsApi.md#updateSemanticPatch) | **PATCH** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey} | Patch segment
*SegmentsApi* | [**updateUserContextTargets**](docs/SegmentsApi.md#updateUserContextTargets) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/users | Update user context targets on a big segment
*SegmentsBetaApi* | [**getBigSegmentExportInfo**](docs/SegmentsBetaApi.md#getBigSegmentExportInfo) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/exports/{exportID} | Get big segment export
*SegmentsBetaApi* | [**getImportInfo**](docs/SegmentsBetaApi.md#getImportInfo) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/imports/{importID} | Get big segment import
*SegmentsBetaApi* | [**startBigSegmentExport**](docs/SegmentsBetaApi.md#startBigSegmentExport) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/exports | Create big segment export
*SegmentsBetaApi* | [**startBigSegmentImport**](docs/SegmentsBetaApi.md#startBigSegmentImport) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/imports | Create big segment import
*TagsApi* | [**list**](docs/TagsApi.md#list) | **GET** /api/v2/tags | List tags
*TeamsApi* | [**addMultipleMembersToTeam**](docs/TeamsApi.md#addMultipleMembersToTeam) | **POST** /api/v2/teams/{teamKey}/members | Add multiple members to team
*TeamsApi* | [**createTeam**](docs/TeamsApi.md#createTeam) | **POST** /api/v2/teams | Create team
*TeamsApi* | [**getByTeamKey**](docs/TeamsApi.md#getByTeamKey) | **GET** /api/v2/teams/{teamKey} | Get team
*TeamsApi* | [**getCustomRoles**](docs/TeamsApi.md#getCustomRoles) | **GET** /api/v2/teams/{teamKey}/roles | Get team custom roles
*TeamsApi* | [**getMaintainers**](docs/TeamsApi.md#getMaintainers) | **GET** /api/v2/teams/{teamKey}/maintainers | Get team maintainers
*TeamsApi* | [**listTeams**](docs/TeamsApi.md#listTeams) | **GET** /api/v2/teams | List teams
*TeamsApi* | [**removeByTeamKey**](docs/TeamsApi.md#removeByTeamKey) | **DELETE** /api/v2/teams/{teamKey} | Delete team
*TeamsApi* | [**updateSemanticPatch**](docs/TeamsApi.md#updateSemanticPatch) | **PATCH** /api/v2/teams/{teamKey} | Update team
*TeamsBetaApi* | [**updateMultipleTeamsSemanticPatch**](docs/TeamsBetaApi.md#updateMultipleTeamsSemanticPatch) | **PATCH** /api/v2/teams | Update teams
*UserSettingsApi* | [**getUserExpiringFlagTargets**](docs/UserSettingsApi.md#getUserExpiringFlagTargets) | **GET** /api/v2/users/{projectKey}/{userKey}/expiring-user-targets/{environmentKey} | Get expiring dates on flags for user
*UserSettingsApi* | [**listFlagSettingsForUser**](docs/UserSettingsApi.md#listFlagSettingsForUser) | **GET** /api/v2/users/{projectKey}/{environmentKey}/{userKey}/flags | List flag settings for user
*UserSettingsApi* | [**singleFlagSetting**](docs/UserSettingsApi.md#singleFlagSetting) | **GET** /api/v2/users/{projectKey}/{environmentKey}/{userKey}/flags/{featureFlagKey} | Get flag setting for user
*UserSettingsApi* | [**updateExpiringUserTarget**](docs/UserSettingsApi.md#updateExpiringUserTarget) | **PATCH** /api/v2/users/{projectKey}/{userKey}/expiring-user-targets/{environmentKey} | Update expiring user target for flags
*UserSettingsApi* | [**updateFlagSettingsForUser**](docs/UserSettingsApi.md#updateFlagSettingsForUser) | **PUT** /api/v2/users/{projectKey}/{environmentKey}/{userKey}/flags/{featureFlagKey} | Update flag settings for user
*UsersApi* | [**deleteByProjectEnvironmentKey**](docs/UsersApi.md#deleteByProjectEnvironmentKey) | **DELETE** /api/v2/users/{projectKey}/{environmentKey}/{userKey} | Delete user
*UsersApi* | [**getUserByKey**](docs/UsersApi.md#getUserByKey) | **GET** /api/v2/users/{projectKey}/{environmentKey}/{userKey} | Get user
*UsersApi* | [**listEnvironmentUsers**](docs/UsersApi.md#listEnvironmentUsers) | **GET** /api/v2/users/{projectKey}/{environmentKey} | List users
*UsersApi* | [**searchUsers**](docs/UsersApi.md#searchUsers) | **GET** /api/v2/user-search/{projectKey}/{environmentKey} | Find users
*UsersBetaApi* | [**getAllInUseUserAttributes**](docs/UsersBetaApi.md#getAllInUseUserAttributes) | **GET** /api/v2/user-attributes/{projectKey}/{environmentKey} | Get user attribute names
*WebhooksApi* | [**createNewWebhook**](docs/WebhooksApi.md#createNewWebhook) | **POST** /api/v2/webhooks | Creates a webhook
*WebhooksApi* | [**deleteById**](docs/WebhooksApi.md#deleteById) | **DELETE** /api/v2/webhooks/{id} | Delete webhook
*WebhooksApi* | [**getSingleById**](docs/WebhooksApi.md#getSingleById) | **GET** /api/v2/webhooks/{id} | Get webhook
*WebhooksApi* | [**listWebhooks**](docs/WebhooksApi.md#listWebhooks) | **GET** /api/v2/webhooks | List webhooks
*WebhooksApi* | [**updateSettingsPatch**](docs/WebhooksApi.md#updateSettingsPatch) | **PATCH** /api/v2/webhooks/{id} | Update webhook
*WorkflowTemplatesApi* | [**createFeatureFlagTemplate**](docs/WorkflowTemplatesApi.md#createFeatureFlagTemplate) | **POST** /api/v2/templates | Create workflow template
*WorkflowTemplatesApi* | [**deleteTemplate**](docs/WorkflowTemplatesApi.md#deleteTemplate) | **DELETE** /api/v2/templates/{templateKey} | Delete workflow template
*WorkflowTemplatesApi* | [**list**](docs/WorkflowTemplatesApi.md#list) | **GET** /api/v2/templates | Get workflow templates
*WorkflowsApi* | [**createWorkflow**](docs/WorkflowsApi.md#createWorkflow) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows | Create workflow
*WorkflowsApi* | [**deleteFromFeatureFlag**](docs/WorkflowsApi.md#deleteFromFeatureFlag) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows/{workflowId} | Delete workflow
*WorkflowsApi* | [**getCustomWorkflowById**](docs/WorkflowsApi.md#getCustomWorkflowById) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows/{workflowId} | Get custom workflow
*WorkflowsApi* | [**getFeatureFlagEnvironmentsWorkflows**](docs/WorkflowsApi.md#getFeatureFlagEnvironmentsWorkflows) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows | Get workflows


## Documentation for Models

 - [Access](docs/Access.md)
 - [AccessAllowedReason](docs/AccessAllowedReason.md)
 - [AccessAllowedRep](docs/AccessAllowedRep.md)
 - [AccessDenied](docs/AccessDenied.md)
 - [AccessDeniedReason](docs/AccessDeniedReason.md)
 - [AccessTokenPost](docs/AccessTokenPost.md)
 - [ActionInput](docs/ActionInput.md)
 - [ActionOutput](docs/ActionOutput.md)
 - [ApplicationCollectionRep](docs/ApplicationCollectionRep.md)
 - [ApplicationFlagCollectionRep](docs/ApplicationFlagCollectionRep.md)
 - [ApplicationRep](docs/ApplicationRep.md)
 - [ApplicationVersionRep](docs/ApplicationVersionRep.md)
 - [ApplicationVersionsCollectionRep](docs/ApplicationVersionsCollectionRep.md)
 - [ApprovalRequestResponse](docs/ApprovalRequestResponse.md)
 - [ApprovalSettings](docs/ApprovalSettings.md)
 - [Audience](docs/Audience.md)
 - [AudiencePost](docs/AudiencePost.md)
 - [AuditLogEntryListingRep](docs/AuditLogEntryListingRep.md)
 - [AuditLogEntryListingRepCollection](docs/AuditLogEntryListingRepCollection.md)
 - [AuditLogEntryRep](docs/AuditLogEntryRep.md)
 - [AuthorizedAppDataRep](docs/AuthorizedAppDataRep.md)
 - [BigSegmentStoreIntegration](docs/BigSegmentStoreIntegration.md)
 - [BigSegmentStoreIntegrationCollection](docs/BigSegmentStoreIntegrationCollection.md)
 - [BigSegmentStoreIntegrationCollectionLinks](docs/BigSegmentStoreIntegrationCollectionLinks.md)
 - [BigSegmentStoreIntegrationLinks](docs/BigSegmentStoreIntegrationLinks.md)
 - [BigSegmentStoreStatus](docs/BigSegmentStoreStatus.md)
 - [BigSegmentTarget](docs/BigSegmentTarget.md)
 - [BooleanDefaults](docs/BooleanDefaults.md)
 - [BooleanFlagDefaults](docs/BooleanFlagDefaults.md)
 - [BranchCollectionRep](docs/BranchCollectionRep.md)
 - [BranchRep](docs/BranchRep.md)
 - [BulkEditMembersRep](docs/BulkEditMembersRep.md)
 - [BulkEditTeamsRep](docs/BulkEditTeamsRep.md)
 - [Clause](docs/Clause.md)
 - [Client](docs/Client.md)
 - [ClientCollection](docs/ClientCollection.md)
 - [ClientSideAvailability](docs/ClientSideAvailability.md)
 - [ClientSideAvailabilityPost](docs/ClientSideAvailabilityPost.md)
 - [CompletedBy](docs/CompletedBy.md)
 - [ConditionInput](docs/ConditionInput.md)
 - [ConditionOutput](docs/ConditionOutput.md)
 - [ConfidenceIntervalRep](docs/ConfidenceIntervalRep.md)
 - [Conflict](docs/Conflict.md)
 - [ConflictOutput](docs/ConflictOutput.md)
 - [ContextAttributeName](docs/ContextAttributeName.md)
 - [ContextAttributeNames](docs/ContextAttributeNames.md)
 - [ContextAttributeNamesCollection](docs/ContextAttributeNamesCollection.md)
 - [ContextAttributeValue](docs/ContextAttributeValue.md)
 - [ContextAttributeValues](docs/ContextAttributeValues.md)
 - [ContextAttributeValuesCollection](docs/ContextAttributeValuesCollection.md)
 - [ContextInstanceEvaluation](docs/ContextInstanceEvaluation.md)
 - [ContextInstanceEvaluationReason](docs/ContextInstanceEvaluationReason.md)
 - [ContextInstanceEvaluations](docs/ContextInstanceEvaluations.md)
 - [ContextInstanceRecord](docs/ContextInstanceRecord.md)
 - [ContextInstanceSearch](docs/ContextInstanceSearch.md)
 - [ContextInstanceSegmentMembership](docs/ContextInstanceSegmentMembership.md)
 - [ContextInstanceSegmentMemberships](docs/ContextInstanceSegmentMemberships.md)
 - [ContextInstances](docs/ContextInstances.md)
 - [ContextKindRep](docs/ContextKindRep.md)
 - [ContextKindsCollectionRep](docs/ContextKindsCollectionRep.md)
 - [ContextRecord](docs/ContextRecord.md)
 - [ContextSearch](docs/ContextSearch.md)
 - [Contexts](docs/Contexts.md)
 - [CopiedFromEnv](docs/CopiedFromEnv.md)
 - [CreateApprovalRequestRequest](docs/CreateApprovalRequestRequest.md)
 - [CreateCopyFlagConfigApprovalRequestRequest](docs/CreateCopyFlagConfigApprovalRequestRequest.md)
 - [CreateFlagConfigApprovalRequestRequest](docs/CreateFlagConfigApprovalRequestRequest.md)
 - [CreatePhaseInput](docs/CreatePhaseInput.md)
 - [CreateReleasePipelineInput](docs/CreateReleasePipelineInput.md)
 - [CreateWorkflowTemplateInput](docs/CreateWorkflowTemplateInput.md)
 - [CredibleIntervalRep](docs/CredibleIntervalRep.md)
 - [CustomProperty](docs/CustomProperty.md)
 - [CustomRole](docs/CustomRole.md)
 - [CustomRolePost](docs/CustomRolePost.md)
 - [CustomRoles](docs/CustomRoles.md)
 - [CustomWorkflowInput](docs/CustomWorkflowInput.md)
 - [CustomWorkflowMeta](docs/CustomWorkflowMeta.md)
 - [CustomWorkflowOutput](docs/CustomWorkflowOutput.md)
 - [CustomWorkflowStageMeta](docs/CustomWorkflowStageMeta.md)
 - [CustomWorkflowsListingOutput](docs/CustomWorkflowsListingOutput.md)
 - [DefaultClientSideAvailability](docs/DefaultClientSideAvailability.md)
 - [DefaultClientSideAvailabilityPost](docs/DefaultClientSideAvailabilityPost.md)
 - [Defaults](docs/Defaults.md)
 - [DependentExperimentRep](docs/DependentExperimentRep.md)
 - [DependentFlag](docs/DependentFlag.md)
 - [DependentFlagEnvironment](docs/DependentFlagEnvironment.md)
 - [DependentFlagsByEnvironment](docs/DependentFlagsByEnvironment.md)
 - [DependentMetricGroupRep](docs/DependentMetricGroupRep.md)
 - [DependentMetricGroupRepWithMetrics](docs/DependentMetricGroupRepWithMetrics.md)
 - [DependentMetricOrMetricGroupRep](docs/DependentMetricOrMetricGroupRep.md)
 - [DeploymentCollectionRep](docs/DeploymentCollectionRep.md)
 - [DeploymentRep](docs/DeploymentRep.md)
 - [Destination](docs/Destination.md)
 - [DestinationPost](docs/DestinationPost.md)
 - [Destinations](docs/Destinations.md)
 - [Distribution](docs/Distribution.md)
 - [Environment](docs/Environment.md)
 - [EnvironmentPost](docs/EnvironmentPost.md)
 - [EnvironmentSummary](docs/EnvironmentSummary.md)
 - [Environments](docs/Environments.md)
 - [EvaluationReason](docs/EvaluationReason.md)
 - [EvaluationsSummary](docs/EvaluationsSummary.md)
 - [ExecutionOutput](docs/ExecutionOutput.md)
 - [ExpandableApprovalRequestResponse](docs/ExpandableApprovalRequestResponse.md)
 - [ExpandableApprovalRequestsResponse](docs/ExpandableApprovalRequestsResponse.md)
 - [ExpandedFlagRep](docs/ExpandedFlagRep.md)
 - [Experiment](docs/Experiment.md)
 - [ExperimentAllocationRep](docs/ExperimentAllocationRep.md)
 - [ExperimentBayesianResultsRep](docs/ExperimentBayesianResultsRep.md)
 - [ExperimentCollectionRep](docs/ExperimentCollectionRep.md)
 - [ExperimentEnabledPeriodRep](docs/ExperimentEnabledPeriodRep.md)
 - [ExperimentEnvironmentSettingRep](docs/ExperimentEnvironmentSettingRep.md)
 - [ExperimentInfoRep](docs/ExperimentInfoRep.md)
 - [ExperimentMetadataRep](docs/ExperimentMetadataRep.md)
 - [ExperimentPatchInput](docs/ExperimentPatchInput.md)
 - [ExperimentPost](docs/ExperimentPost.md)
 - [ExperimentResults](docs/ExperimentResults.md)
 - [ExperimentStatsRep](docs/ExperimentStatsRep.md)
 - [ExperimentTimeSeriesSlice](docs/ExperimentTimeSeriesSlice.md)
 - [ExperimentTimeSeriesVariationSlice](docs/ExperimentTimeSeriesVariationSlice.md)
 - [ExperimentTotalsRep](docs/ExperimentTotalsRep.md)
 - [ExpiringTarget](docs/ExpiringTarget.md)
 - [ExpiringTargetError](docs/ExpiringTargetError.md)
 - [ExpiringTargetGetResponse](docs/ExpiringTargetGetResponse.md)
 - [ExpiringTargetPatchResponse](docs/ExpiringTargetPatchResponse.md)
 - [ExpiringUserTargetGetResponse](docs/ExpiringUserTargetGetResponse.md)
 - [ExpiringUserTargetItem](docs/ExpiringUserTargetItem.md)
 - [ExpiringUserTargetPatchResponse](docs/ExpiringUserTargetPatchResponse.md)
 - [Export](docs/Export.md)
 - [Extinction](docs/Extinction.md)
 - [ExtinctionCollectionRep](docs/ExtinctionCollectionRep.md)
 - [FailureReasonRep](docs/FailureReasonRep.md)
 - [FeatureFlag](docs/FeatureFlag.md)
 - [FeatureFlagBody](docs/FeatureFlagBody.md)
 - [FeatureFlagConfig](docs/FeatureFlagConfig.md)
 - [FeatureFlagScheduledChange](docs/FeatureFlagScheduledChange.md)
 - [FeatureFlagScheduledChanges](docs/FeatureFlagScheduledChanges.md)
 - [FeatureFlagStatus](docs/FeatureFlagStatus.md)
 - [FeatureFlagStatusAcrossEnvironments](docs/FeatureFlagStatusAcrossEnvironments.md)
 - [FeatureFlagStatuses](docs/FeatureFlagStatuses.md)
 - [FeatureFlags](docs/FeatureFlags.md)
 - [FileRep](docs/FileRep.md)
 - [FlagConfigApprovalRequestResponse](docs/FlagConfigApprovalRequestResponse.md)
 - [FlagConfigApprovalRequestsResponse](docs/FlagConfigApprovalRequestsResponse.md)
 - [FlagConfigEvaluation](docs/FlagConfigEvaluation.md)
 - [FlagConfigMigrationSettingsRep](docs/FlagConfigMigrationSettingsRep.md)
 - [FlagCopyConfigEnvironment](docs/FlagCopyConfigEnvironment.md)
 - [FlagCopyConfigPost](docs/FlagCopyConfigPost.md)
 - [FlagDefaultsRep](docs/FlagDefaultsRep.md)
 - [FlagEventCollectionRep](docs/FlagEventCollectionRep.md)
 - [FlagEventExperiment](docs/FlagEventExperiment.md)
 - [FlagEventExperimentCollection](docs/FlagEventExperimentCollection.md)
 - [FlagEventExperimentIteration](docs/FlagEventExperimentIteration.md)
 - [FlagEventImpactRep](docs/FlagEventImpactRep.md)
 - [FlagEventMemberRep](docs/FlagEventMemberRep.md)
 - [FlagEventRep](docs/FlagEventRep.md)
 - [FlagFollowersByProjEnvGetRep](docs/FlagFollowersByProjEnvGetRep.md)
 - [FlagFollowersGetRep](docs/FlagFollowersGetRep.md)
 - [FlagInput](docs/FlagInput.md)
 - [FlagLinkCollectionRep](docs/FlagLinkCollectionRep.md)
 - [FlagLinkMember](docs/FlagLinkMember.md)
 - [FlagLinkPost](docs/FlagLinkPost.md)
 - [FlagLinkRep](docs/FlagLinkRep.md)
 - [FlagListingRep](docs/FlagListingRep.md)
 - [FlagMigrationSettingsRep](docs/FlagMigrationSettingsRep.md)
 - [FlagReferenceCollectionRep](docs/FlagReferenceCollectionRep.md)
 - [FlagReferenceRep](docs/FlagReferenceRep.md)
 - [FlagRep](docs/FlagRep.md)
 - [FlagScheduledChangesInput](docs/FlagScheduledChangesInput.md)
 - [FlagSempatch](docs/FlagSempatch.md)
 - [FlagStatusRep](docs/FlagStatusRep.md)
 - [FlagSummary](docs/FlagSummary.md)
 - [FlagTriggerInput](docs/FlagTriggerInput.md)
 - [FollowFlagMember](docs/FollowFlagMember.md)
 - [FollowersPerFlag](docs/FollowersPerFlag.md)
 - [HunkRep](docs/HunkRep.md)
 - [InitiatorRep](docs/InitiatorRep.md)
 - [InsightGroup](docs/InsightGroup.md)
 - [InsightGroupCollection](docs/InsightGroupCollection.md)
 - [InsightGroupCollectionMetadata](docs/InsightGroupCollectionMetadata.md)
 - [InsightGroupCollectionScoreMetadata](docs/InsightGroupCollectionScoreMetadata.md)
 - [InsightGroupScores](docs/InsightGroupScores.md)
 - [InsightGroupsCountByIndicator](docs/InsightGroupsCountByIndicator.md)
 - [InsightPeriod](docs/InsightPeriod.md)
 - [InsightScores](docs/InsightScores.md)
 - [InsightsChart](docs/InsightsChart.md)
 - [InsightsChartBounds](docs/InsightsChartBounds.md)
 - [InsightsChartMetadata](docs/InsightsChartMetadata.md)
 - [InsightsChartMetric](docs/InsightsChartMetric.md)
 - [InsightsChartSeries](docs/InsightsChartSeries.md)
 - [InsightsChartSeriesDataPoint](docs/InsightsChartSeriesDataPoint.md)
 - [InsightsChartSeriesMetadata](docs/InsightsChartSeriesMetadata.md)
 - [InsightsChartSeriesMetadataAxis](docs/InsightsChartSeriesMetadataAxis.md)
 - [InsightsMetricIndicatorRange](docs/InsightsMetricIndicatorRange.md)
 - [InsightsMetricScore](docs/InsightsMetricScore.md)
 - [InsightsMetricTierDefinition](docs/InsightsMetricTierDefinition.md)
 - [InsightsRepository](docs/InsightsRepository.md)
 - [InsightsRepositoryCollection](docs/InsightsRepositoryCollection.md)
 - [InsightsRepositoryProject](docs/InsightsRepositoryProject.md)
 - [InsightsRepositoryProjectCollection](docs/InsightsRepositoryProjectCollection.md)
 - [InsightsRepositoryProjectMappings](docs/InsightsRepositoryProjectMappings.md)
 - [InstructionUserRequest](docs/InstructionUserRequest.md)
 - [Integration](docs/Integration.md)
 - [IntegrationDeliveryConfiguration](docs/IntegrationDeliveryConfiguration.md)
 - [IntegrationDeliveryConfigurationCollection](docs/IntegrationDeliveryConfigurationCollection.md)
 - [IntegrationDeliveryConfigurationCollectionLinks](docs/IntegrationDeliveryConfigurationCollectionLinks.md)
 - [IntegrationDeliveryConfigurationLinks](docs/IntegrationDeliveryConfigurationLinks.md)
 - [IntegrationDeliveryConfigurationPost](docs/IntegrationDeliveryConfigurationPost.md)
 - [IntegrationDeliveryConfigurationResponse](docs/IntegrationDeliveryConfigurationResponse.md)
 - [IntegrationMetadata](docs/IntegrationMetadata.md)
 - [IntegrationStatus](docs/IntegrationStatus.md)
 - [IntegrationStatusRep](docs/IntegrationStatusRep.md)
 - [IntegrationSubscriptionStatusRep](docs/IntegrationSubscriptionStatusRep.md)
 - [Integrations](docs/Integrations.md)
 - [IpList](docs/IpList.md)
 - [IterationInput](docs/IterationInput.md)
 - [IterationRep](docs/IterationRep.md)
 - [LastSeenMetadata](docs/LastSeenMetadata.md)
 - [LeadTimeStagesRep](docs/LeadTimeStagesRep.md)
 - [LegacyExperimentRep](docs/LegacyExperimentRep.md)
 - [Link](docs/Link.md)
 - [MaintainerRep](docs/MaintainerRep.md)
 - [MaintainerTeam](docs/MaintainerTeam.md)
 - [Member](docs/Member.md)
 - [MemberDataRep](docs/MemberDataRep.md)
 - [MemberImportItem](docs/MemberImportItem.md)
 - [MemberPermissionGrantSummaryRep](docs/MemberPermissionGrantSummaryRep.md)
 - [MemberSummary](docs/MemberSummary.md)
 - [MemberTeamSummaryRep](docs/MemberTeamSummaryRep.md)
 - [MemberTeamsPostInput](docs/MemberTeamsPostInput.md)
 - [Members](docs/Members.md)
 - [MembersPatchInput](docs/MembersPatchInput.md)
 - [MetricCollectionRep](docs/MetricCollectionRep.md)
 - [MetricEventDefaultRep](docs/MetricEventDefaultRep.md)
 - [MetricGroupCollectionRep](docs/MetricGroupCollectionRep.md)
 - [MetricGroupPost](docs/MetricGroupPost.md)
 - [MetricGroupRep](docs/MetricGroupRep.md)
 - [MetricGroupResultsRep](docs/MetricGroupResultsRep.md)
 - [MetricInGroupRep](docs/MetricInGroupRep.md)
 - [MetricInGroupResultsRep](docs/MetricInGroupResultsRep.md)
 - [MetricInMetricGroupInput](docs/MetricInMetricGroupInput.md)
 - [MetricInput](docs/MetricInput.md)
 - [MetricListingRep](docs/MetricListingRep.md)
 - [MetricPost](docs/MetricPost.md)
 - [MetricRep](docs/MetricRep.md)
 - [MetricSeen](docs/MetricSeen.md)
 - [MetricV2Rep](docs/MetricV2Rep.md)
 - [MigrationSafetyIssueRep](docs/MigrationSafetyIssueRep.md)
 - [MigrationSettingsPost](docs/MigrationSettingsPost.md)
 - [ModelImport](docs/ModelImport.md)
 - [Modification](docs/Modification.md)
 - [MultiEnvironmentDependentFlag](docs/MultiEnvironmentDependentFlag.md)
 - [MultiEnvironmentDependentFlags](docs/MultiEnvironmentDependentFlags.md)
 - [NewMemberForm](docs/NewMemberForm.md)
 - [OauthClientPost](docs/OauthClientPost.md)
 - [ParameterDefault](docs/ParameterDefault.md)
 - [ParameterRep](docs/ParameterRep.md)
 - [ParentResourceRep](docs/ParentResourceRep.md)
 - [PatchFlagsRequest](docs/PatchFlagsRequest.md)
 - [PatchOperation](docs/PatchOperation.md)
 - [PatchSegmentExpiringTargetInputRep](docs/PatchSegmentExpiringTargetInputRep.md)
 - [PatchSegmentExpiringTargetInstruction](docs/PatchSegmentExpiringTargetInstruction.md)
 - [PatchSegmentInstruction](docs/PatchSegmentInstruction.md)
 - [PatchSegmentRequest](docs/PatchSegmentRequest.md)
 - [PatchUsersRequest](docs/PatchUsersRequest.md)
 - [PatchWithComment](docs/PatchWithComment.md)
 - [PermissionGrantInput](docs/PermissionGrantInput.md)
 - [Phase](docs/Phase.md)
 - [PostApprovalRequestApplyRequest](docs/PostApprovalRequestApplyRequest.md)
 - [PostApprovalRequestReviewRequest](docs/PostApprovalRequestReviewRequest.md)
 - [PostDeploymentEventInput](docs/PostDeploymentEventInput.md)
 - [PostFlagScheduledChangesInput](docs/PostFlagScheduledChangesInput.md)
 - [PostInsightGroupParams](docs/PostInsightGroupParams.md)
 - [Prerequisite](docs/Prerequisite.md)
 - [Project](docs/Project.md)
 - [ProjectPost](docs/ProjectPost.md)
 - [ProjectRep](docs/ProjectRep.md)
 - [ProjectSummary](docs/ProjectSummary.md)
 - [ProjectSummaryCollection](docs/ProjectSummaryCollection.md)
 - [Projects](docs/Projects.md)
 - [PullRequestCollectionRep](docs/PullRequestCollectionRep.md)
 - [PullRequestLeadTimeRep](docs/PullRequestLeadTimeRep.md)
 - [PullRequestRep](docs/PullRequestRep.md)
 - [PutBranch](docs/PutBranch.md)
 - [RandomizationSettingsPut](docs/RandomizationSettingsPut.md)
 - [RandomizationSettingsRep](docs/RandomizationSettingsRep.md)
 - [RandomizationUnitInput](docs/RandomizationUnitInput.md)
 - [RandomizationUnitRep](docs/RandomizationUnitRep.md)
 - [RecentTriggerBody](docs/RecentTriggerBody.md)
 - [ReferenceRep](docs/ReferenceRep.md)
 - [RelativeDifferenceRep](docs/RelativeDifferenceRep.md)
 - [RelayAutoConfigCollectionRep](docs/RelayAutoConfigCollectionRep.md)
 - [RelayAutoConfigPost](docs/RelayAutoConfigPost.md)
 - [RelayAutoConfigRep](docs/RelayAutoConfigRep.md)
 - [Release](docs/Release.md)
 - [ReleasePhase](docs/ReleasePhase.md)
 - [ReleasePipeline](docs/ReleasePipeline.md)
 - [ReleasePipelineCollection](docs/ReleasePipelineCollection.md)
 - [RepositoryCollectionRep](docs/RepositoryCollectionRep.md)
 - [RepositoryPost](docs/RepositoryPost.md)
 - [RepositoryRep](docs/RepositoryRep.md)
 - [ResourceAccess](docs/ResourceAccess.md)
 - [ResourceIDResponse](docs/ResourceIDResponse.md)
 - [ResourceId](docs/ResourceId.md)
 - [ReviewOutput](docs/ReviewOutput.md)
 - [ReviewResponse](docs/ReviewResponse.md)
 - [Rollout](docs/Rollout.md)
 - [RootResponse](docs/RootResponse.md)
 - [Rule](docs/Rule.md)
 - [RuleClause](docs/RuleClause.md)
 - [SdkListRep](docs/SdkListRep.md)
 - [SdkVersionListRep](docs/SdkVersionListRep.md)
 - [SdkVersionRep](docs/SdkVersionRep.md)
 - [SegmentBody](docs/SegmentBody.md)
 - [SegmentMetadata](docs/SegmentMetadata.md)
 - [SegmentTarget](docs/SegmentTarget.md)
 - [SegmentUserList](docs/SegmentUserList.md)
 - [SegmentUserState](docs/SegmentUserState.md)
 - [SegmentsBetaStartBigSegmentImportRequest](docs/SegmentsBetaStartBigSegmentImportRequest.md)
 - [Series](docs/Series.md)
 - [SeriesIntervalsRep](docs/SeriesIntervalsRep.md)
 - [SeriesListRep](docs/SeriesListRep.md)
 - [SlicedResultsRep](docs/SlicedResultsRep.md)
 - [SourceEnv](docs/SourceEnv.md)
 - [SourceFlag](docs/SourceFlag.md)
 - [StageInput](docs/StageInput.md)
 - [StageOutput](docs/StageOutput.md)
 - [Statement](docs/Statement.md)
 - [StatementPost](docs/StatementPost.md)
 - [StatisticCollectionRep](docs/StatisticCollectionRep.md)
 - [StatisticRep](docs/StatisticRep.md)
 - [StatisticsRoot](docs/StatisticsRoot.md)
 - [StoreIntegrationError](docs/StoreIntegrationError.md)
 - [SubjectDataRep](docs/SubjectDataRep.md)
 - [SubscriptionPost](docs/SubscriptionPost.md)
 - [TagCollection](docs/TagCollection.md)
 - [Target](docs/Target.md)
 - [TargetResourceRep](docs/TargetResourceRep.md)
 - [Team](docs/Team.md)
 - [TeamCustomRole](docs/TeamCustomRole.md)
 - [TeamCustomRoles](docs/TeamCustomRoles.md)
 - [TeamImportsRep](docs/TeamImportsRep.md)
 - [TeamMaintainers](docs/TeamMaintainers.md)
 - [TeamMembers](docs/TeamMembers.md)
 - [TeamPatchInput](docs/TeamPatchInput.md)
 - [TeamPostInput](docs/TeamPostInput.md)
 - [TeamProjects](docs/TeamProjects.md)
 - [Teams](docs/Teams.md)
 - [TeamsAddMultipleMembersToTeamRequest](docs/TeamsAddMultipleMembersToTeamRequest.md)
 - [TeamsPatchInput](docs/TeamsPatchInput.md)
 - [TimestampRep](docs/TimestampRep.md)
 - [Token](docs/Token.md)
 - [TokenSummary](docs/TokenSummary.md)
 - [Tokens](docs/Tokens.md)
 - [TreatmentInput](docs/TreatmentInput.md)
 - [TreatmentParameterInput](docs/TreatmentParameterInput.md)
 - [TreatmentRep](docs/TreatmentRep.md)
 - [TreatmentResultRep](docs/TreatmentResultRep.md)
 - [TriggerPost](docs/TriggerPost.md)
 - [TriggerWorkflowCollectionRep](docs/TriggerWorkflowCollectionRep.md)
 - [TriggerWorkflowRep](docs/TriggerWorkflowRep.md)
 - [UpsertContextKindPayload](docs/UpsertContextKindPayload.md)
 - [UpsertFlagDefaultsPayload](docs/UpsertFlagDefaultsPayload.md)
 - [UpsertPayloadRep](docs/UpsertPayloadRep.md)
 - [UpsertResponseRep](docs/UpsertResponseRep.md)
 - [UrlPost](docs/UrlPost.md)
 - [User](docs/User.md)
 - [UserAttributeNamesRep](docs/UserAttributeNamesRep.md)
 - [UserFlagSetting](docs/UserFlagSetting.md)
 - [UserFlagSettings](docs/UserFlagSettings.md)
 - [UserRecord](docs/UserRecord.md)
 - [UserSegment](docs/UserSegment.md)
 - [UserSegmentRule](docs/UserSegmentRule.md)
 - [UserSegments](docs/UserSegments.md)
 - [Users](docs/Users.md)
 - [UsersRep](docs/UsersRep.md)
 - [ValuePut](docs/ValuePut.md)
 - [Variation](docs/Variation.md)
 - [VariationEvalSummary](docs/VariationEvalSummary.md)
 - [VariationOrRolloutRep](docs/VariationOrRolloutRep.md)
 - [VariationSummary](docs/VariationSummary.md)
 - [VersionsRep](docs/VersionsRep.md)
 - [Webhook](docs/Webhook.md)
 - [WebhookPost](docs/WebhookPost.md)
 - [Webhooks](docs/Webhooks.md)
 - [WeightedVariation](docs/WeightedVariation.md)
 - [WorkflowTemplateMetadata](docs/WorkflowTemplateMetadata.md)
 - [WorkflowTemplateOutput](docs/WorkflowTemplateOutput.md)
 - [WorkflowTemplateParameter](docs/WorkflowTemplateParameter.md)
 - [WorkflowTemplatesListingOutputRep](docs/WorkflowTemplatesListingOutputRep.md)


## Author
This Java package is automatically generated by [Konfig](https://konfigthis.com)
