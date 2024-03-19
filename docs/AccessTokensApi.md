# AccessTokensApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNewToken**](AccessTokensApi.md#createNewToken) | **POST** /api/v2/tokens | Create access token |
| [**deleteById**](AccessTokensApi.md#deleteById) | **DELETE** /api/v2/tokens/{id} | Delete access token |
| [**getById**](AccessTokensApi.md#getById) | **GET** /api/v2/tokens/{id} | Get access token |
| [**list**](AccessTokensApi.md#list) | **GET** /api/v2/tokens | List access tokens |
| [**resetSecretKey**](AccessTokensApi.md#resetSecretKey) | **POST** /api/v2/tokens/{id}/reset | Reset access token |
| [**updateSettings**](AccessTokensApi.md#updateSettings) | **PATCH** /api/v2/tokens/{id} | Patch access token |


<a name="createNewToken"></a>
# **createNewToken**
> Token createNewToken(accessTokenPost).execute();

Create access token

Create a new access token.

### Example
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

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **accessTokenPost** | [**AccessTokenPost**](AccessTokenPost.md)|  | |

### Return type

[**Token**](Token.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Access token response |  -  |

<a name="deleteById"></a>
# **deleteById**
> deleteById(id).execute();

Delete access token

Delete an access token by ID.

### Example
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
    String id = "id_example"; // The ID of the access token to update
    try {
      client
              .accessTokens
              .deleteById(id)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling AccessTokensApi#deleteById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .accessTokens
              .deleteById(id)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling AccessTokensApi#deleteById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**| The ID of the access token to update | |

### Return type

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Action succeeded |  -  |

<a name="getById"></a>
# **getById**
> Token getById(id).execute();

Get access token

Get a single access token by ID.

### Example
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
    String id = "id_example"; // The ID of the access token
    try {
      Token result = client
              .accessTokens
              .getById(id)
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
      System.err.println("Exception when calling AccessTokensApi#getById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Token> response = client
              .accessTokens
              .getById(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccessTokensApi#getById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**| The ID of the access token | |

### Return type

[**Token**](Token.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Access token response |  -  |

<a name="list"></a>
# **list**
> Tokens list().showAll(showAll).limit(limit).offset(offset).execute();

List access tokens

Fetch a list of all access tokens.

### Example
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
    Boolean showAll = true; // If set to true, and the authentication access token has the 'Admin' role, personal access tokens for all members will be retrieved.
    Long limit = 56L; // The number of access tokens to return in the response. Defaults to 25.
    Long offset = 56L; // Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    try {
      Tokens result = client
              .accessTokens
              .list()
              .showAll(showAll)
              .limit(limit)
              .offset(offset)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
      System.out.println(result.getTotalCount());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccessTokensApi#list");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Tokens> response = client
              .accessTokens
              .list()
              .showAll(showAll)
              .limit(limit)
              .offset(offset)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccessTokensApi#list");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **showAll** | **Boolean**| If set to true, and the authentication access token has the &#39;Admin&#39; role, personal access tokens for all members will be retrieved. | [optional] |
| **limit** | **Long**| The number of access tokens to return in the response. Defaults to 25. | [optional] |
| **offset** | **Long**| Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |

### Return type

[**Tokens**](Tokens.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Access tokens collection response |  -  |

<a name="resetSecretKey"></a>
# **resetSecretKey**
> Token resetSecretKey(id).expiry(expiry).execute();

Reset access token

Reset an access token&#39;s secret key with an optional expiry time for the old key.

### Example
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
    String id = "id_example"; // The ID of the access token to update
    Long expiry = 56L; // An expiration time for the old token key, expressed as a Unix epoch time in milliseconds. By default, the token will expire immediately.
    try {
      Token result = client
              .accessTokens
              .resetSecretKey(id)
              .expiry(expiry)
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
      System.err.println("Exception when calling AccessTokensApi#resetSecretKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Token> response = client
              .accessTokens
              .resetSecretKey(id)
              .expiry(expiry)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccessTokensApi#resetSecretKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**| The ID of the access token to update | |
| **expiry** | **Long**| An expiration time for the old token key, expressed as a Unix epoch time in milliseconds. By default, the token will expire immediately. | [optional] |

### Return type

[**Token**](Token.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Access token response |  -  |

<a name="updateSettings"></a>
# **updateSettings**
> Token updateSettings(id, patchOperation).execute();

Patch access token

Update an access token&#39;s settings. Updating an access token uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).

### Example
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
    String id = "id_example"; // The ID of the access token to update
    try {
      Token result = client
              .accessTokens
              .updateSettings(id)
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
      System.err.println("Exception when calling AccessTokensApi#updateSettings");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Token> response = client
              .accessTokens
              .updateSettings(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccessTokensApi#updateSettings");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**| The ID of the access token to update | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**Token**](Token.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Access token response |  -  |

