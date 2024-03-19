# RelayProxyConfigurationsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNewConfig**](RelayProxyConfigurationsApi.md#createNewConfig) | **POST** /api/v2/account/relay-auto-configs | Create a new Relay Proxy config |
| [**deleteById**](RelayProxyConfigurationsApi.md#deleteById) | **DELETE** /api/v2/account/relay-auto-configs/{id} | Delete Relay Proxy config by ID |
| [**getSingleById**](RelayProxyConfigurationsApi.md#getSingleById) | **GET** /api/v2/account/relay-auto-configs/{id} | Get Relay Proxy config |
| [**list**](RelayProxyConfigurationsApi.md#list) | **GET** /api/v2/account/relay-auto-configs | List Relay Proxy configs |
| [**resetSecretKeyWithExpiry**](RelayProxyConfigurationsApi.md#resetSecretKeyWithExpiry) | **POST** /api/v2/account/relay-auto-configs/{id}/reset | Reset Relay Proxy configuration key |
| [**updateConfigPatch**](RelayProxyConfigurationsApi.md#updateConfigPatch) | **PATCH** /api/v2/account/relay-auto-configs/{id} | Update a Relay Proxy config |


<a name="createNewConfig"></a>
# **createNewConfig**
> RelayAutoConfigRep createNewConfig(relayAutoConfigPost).execute();

Create a new Relay Proxy config

Create a Relay Proxy config.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.RelayProxyConfigurationsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String name = "name_example"; // A human-friendly name for the Relay Proxy configuration
    List<Statement> policy = Arrays.asList(); // A description of what environments and projects the Relay Proxy should include or exclude. To learn more, read [Writing an inline policy](https://docs.launchdarkly.com/home/relay-proxy/automatic-configuration#writing-an-inline-policy).
    try {
      RelayAutoConfigRep result = client
              .relayProxyConfigurations
              .createNewConfig(name, policy)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getCreator());
      System.out.println(result.getAccess());
      System.out.println(result.getName());
      System.out.println(result.getPolicy());
      System.out.println(result.getFullKey());
      System.out.println(result.getDisplayKey());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLastModified());
    } catch (ApiException e) {
      System.err.println("Exception when calling RelayProxyConfigurationsApi#createNewConfig");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<RelayAutoConfigRep> response = client
              .relayProxyConfigurations
              .createNewConfig(name, policy)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling RelayProxyConfigurationsApi#createNewConfig");
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
| **relayAutoConfigPost** | [**RelayAutoConfigPost**](RelayAutoConfigPost.md)|  | |

### Return type

[**RelayAutoConfigRep**](RelayAutoConfigRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Relay auto config response |  -  |

<a name="deleteById"></a>
# **deleteById**
> deleteById(id).execute();

Delete Relay Proxy config by ID

Delete a Relay Proxy config.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.RelayProxyConfigurationsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String id = "id_example"; // The relay auto config id
    try {
      client
              .relayProxyConfigurations
              .deleteById(id)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling RelayProxyConfigurationsApi#deleteById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .relayProxyConfigurations
              .deleteById(id)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling RelayProxyConfigurationsApi#deleteById");
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
| **id** | **String**| The relay auto config id | |

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

<a name="getSingleById"></a>
# **getSingleById**
> RelayAutoConfigRep getSingleById(id).execute();

Get Relay Proxy config

Get a single Relay Proxy auto config by ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.RelayProxyConfigurationsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String id = "id_example"; // The relay auto config id
    try {
      RelayAutoConfigRep result = client
              .relayProxyConfigurations
              .getSingleById(id)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getCreator());
      System.out.println(result.getAccess());
      System.out.println(result.getName());
      System.out.println(result.getPolicy());
      System.out.println(result.getFullKey());
      System.out.println(result.getDisplayKey());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLastModified());
    } catch (ApiException e) {
      System.err.println("Exception when calling RelayProxyConfigurationsApi#getSingleById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<RelayAutoConfigRep> response = client
              .relayProxyConfigurations
              .getSingleById(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling RelayProxyConfigurationsApi#getSingleById");
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
| **id** | **String**| The relay auto config id | |

### Return type

[**RelayAutoConfigRep**](RelayAutoConfigRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Relay auto config response |  -  |

<a name="list"></a>
# **list**
> RelayAutoConfigCollectionRep list().execute();

List Relay Proxy configs

Get a list of Relay Proxy configurations in the account.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.RelayProxyConfigurationsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    try {
      RelayAutoConfigCollectionRep result = client
              .relayProxyConfigurations
              .list()
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling RelayProxyConfigurationsApi#list");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<RelayAutoConfigCollectionRep> response = client
              .relayProxyConfigurations
              .list()
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling RelayProxyConfigurationsApi#list");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**RelayAutoConfigCollectionRep**](RelayAutoConfigCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Relay auto configs collection response |  -  |

<a name="resetSecretKeyWithExpiry"></a>
# **resetSecretKeyWithExpiry**
> RelayAutoConfigRep resetSecretKeyWithExpiry(id).expiry(expiry).execute();

Reset Relay Proxy configuration key

Reset a Relay Proxy configuration&#39;s secret key with an optional expiry time for the old key.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.RelayProxyConfigurationsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String id = "id_example"; // The Relay Proxy configuration ID
    Long expiry = 56L; // An expiration time for the old Relay Proxy configuration key, expressed as a Unix epoch time in milliseconds. By default, the Relay Proxy configuration will expire immediately.
    try {
      RelayAutoConfigRep result = client
              .relayProxyConfigurations
              .resetSecretKeyWithExpiry(id)
              .expiry(expiry)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getCreator());
      System.out.println(result.getAccess());
      System.out.println(result.getName());
      System.out.println(result.getPolicy());
      System.out.println(result.getFullKey());
      System.out.println(result.getDisplayKey());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLastModified());
    } catch (ApiException e) {
      System.err.println("Exception when calling RelayProxyConfigurationsApi#resetSecretKeyWithExpiry");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<RelayAutoConfigRep> response = client
              .relayProxyConfigurations
              .resetSecretKeyWithExpiry(id)
              .expiry(expiry)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling RelayProxyConfigurationsApi#resetSecretKeyWithExpiry");
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
| **id** | **String**| The Relay Proxy configuration ID | |
| **expiry** | **Long**| An expiration time for the old Relay Proxy configuration key, expressed as a Unix epoch time in milliseconds. By default, the Relay Proxy configuration will expire immediately. | [optional] |

### Return type

[**RelayAutoConfigRep**](RelayAutoConfigRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Relay auto config response |  -  |

<a name="updateConfigPatch"></a>
# **updateConfigPatch**
> RelayAutoConfigRep updateConfigPatch(id, patchWithComment).execute();

Update a Relay Proxy config

Update a Relay Proxy configuration. Updating a configuration uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) or [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.RelayProxyConfigurationsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    List<PatchOperation> patch = Arrays.asList();
    String id = "id_example"; // The relay auto config id
    String comment = "comment_example"; // Optional comment
    try {
      RelayAutoConfigRep result = client
              .relayProxyConfigurations
              .updateConfigPatch(patch, id)
              .comment(comment)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getCreator());
      System.out.println(result.getAccess());
      System.out.println(result.getName());
      System.out.println(result.getPolicy());
      System.out.println(result.getFullKey());
      System.out.println(result.getDisplayKey());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLastModified());
    } catch (ApiException e) {
      System.err.println("Exception when calling RelayProxyConfigurationsApi#updateConfigPatch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<RelayAutoConfigRep> response = client
              .relayProxyConfigurations
              .updateConfigPatch(patch, id)
              .comment(comment)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling RelayProxyConfigurationsApi#updateConfigPatch");
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
| **id** | **String**| The relay auto config id | |
| **patchWithComment** | [**PatchWithComment**](PatchWithComment.md)|  | |

### Return type

[**RelayAutoConfigRep**](RelayAutoConfigRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Relay auto config response |  -  |

