# IntegrationDeliveryConfigurationsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createDeliveryConfiguration**](IntegrationDeliveryConfigurationsBetaApi.md#createDeliveryConfiguration) | **POST** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey} | Create delivery configuration |
| [**deleteDeliveryConfiguration**](IntegrationDeliveryConfigurationsBetaApi.md#deleteDeliveryConfiguration) | **DELETE** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id} | Delete delivery configuration |
| [**getById**](IntegrationDeliveryConfigurationsBetaApi.md#getById) | **GET** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id} | Get delivery configuration by ID |
| [**getDeliveryConfigurationsByEnvironment**](IntegrationDeliveryConfigurationsBetaApi.md#getDeliveryConfigurationsByEnvironment) | **GET** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey} | Get delivery configurations by environment |
| [**listDeliveryConfigurations**](IntegrationDeliveryConfigurationsBetaApi.md#listDeliveryConfigurations) | **GET** /api/v2/integration-capabilities/featureStore | List all delivery configurations |
| [**updateDeliveryConfiguration**](IntegrationDeliveryConfigurationsBetaApi.md#updateDeliveryConfiguration) | **PATCH** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id} | Update delivery configuration |
| [**validateDeliveryConfiguration**](IntegrationDeliveryConfigurationsBetaApi.md#validateDeliveryConfiguration) | **POST** /api/v2/integration-capabilities/featureStore/{projectKey}/{environmentKey}/{integrationKey}/{id}/validate | Validate delivery configuration |


<a name="createDeliveryConfiguration"></a>
# **createDeliveryConfiguration**
> IntegrationDeliveryConfiguration createDeliveryConfiguration(projectKey, environmentKey, integrationKey, integrationDeliveryConfigurationPost).execute();

Create delivery configuration

Create a delivery configuration.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationDeliveryConfigurationsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    Map<String, Object> config = new HashMap();
    String projectKey = "projectKey_example"; // The project key
    String environmentKey = "environmentKey_example"; // The environment key
    String integrationKey = "integrationKey_example"; // The integration key
    List<String> tags = Arrays.asList(); // Tags to associate with the integration
    Boolean true = true; // Whether the integration configuration is active. Default value is false.
    String name = "name_example"; // Name to identify the integration
    try {
      IntegrationDeliveryConfiguration result = client
              .integrationDeliveryConfigurationsBeta
              .createDeliveryConfiguration(config, projectKey, environmentKey, integrationKey)
              .tags(tags)
              .true(true)
              .name(name)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getVersion());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getIntegrationKey());
      System.out.println(result.getProjectKey());
      System.out.println(result.getEnvironmentKey());
      System.out.println(result.getConfig());
      System.out.println(result.getTrue());
      System.out.println(result.getName());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#createDeliveryConfiguration");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<IntegrationDeliveryConfiguration> response = client
              .integrationDeliveryConfigurationsBeta
              .createDeliveryConfiguration(config, projectKey, environmentKey, integrationKey)
              .tags(tags)
              .true(true)
              .name(name)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#createDeliveryConfiguration");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **integrationKey** | **String**| The integration key | |
| **integrationDeliveryConfigurationPost** | [**IntegrationDeliveryConfigurationPost**](IntegrationDeliveryConfigurationPost.md)|  | |

### Return type

[**IntegrationDeliveryConfiguration**](IntegrationDeliveryConfiguration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Integration delivery configuration response |  -  |

<a name="deleteDeliveryConfiguration"></a>
# **deleteDeliveryConfiguration**
> deleteDeliveryConfiguration(projectKey, environmentKey, integrationKey, id).execute();

Delete delivery configuration

Delete a delivery configuration.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationDeliveryConfigurationsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String projectKey = "projectKey_example"; // The project key
    String environmentKey = "environmentKey_example"; // The environment key
    String integrationKey = "integrationKey_example"; // The integration key
    String id = "id_example"; // The configuration ID
    try {
      client
              .integrationDeliveryConfigurationsBeta
              .deleteDeliveryConfiguration(projectKey, environmentKey, integrationKey, id)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#deleteDeliveryConfiguration");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .integrationDeliveryConfigurationsBeta
              .deleteDeliveryConfiguration(projectKey, environmentKey, integrationKey, id)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#deleteDeliveryConfiguration");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **integrationKey** | **String**| The integration key | |
| **id** | **String**| The configuration ID | |

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
> IntegrationDeliveryConfiguration getById(projectKey, environmentKey, integrationKey, id).execute();

Get delivery configuration by ID

Get delivery configuration by ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationDeliveryConfigurationsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String projectKey = "projectKey_example"; // The project key
    String environmentKey = "environmentKey_example"; // The environment key
    String integrationKey = "integrationKey_example"; // The integration key
    String id = "id_example"; // The configuration ID
    try {
      IntegrationDeliveryConfiguration result = client
              .integrationDeliveryConfigurationsBeta
              .getById(projectKey, environmentKey, integrationKey, id)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getVersion());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getIntegrationKey());
      System.out.println(result.getProjectKey());
      System.out.println(result.getEnvironmentKey());
      System.out.println(result.getConfig());
      System.out.println(result.getTrue());
      System.out.println(result.getName());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#getById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<IntegrationDeliveryConfiguration> response = client
              .integrationDeliveryConfigurationsBeta
              .getById(projectKey, environmentKey, integrationKey, id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#getById");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **integrationKey** | **String**| The integration key | |
| **id** | **String**| The configuration ID | |

### Return type

[**IntegrationDeliveryConfiguration**](IntegrationDeliveryConfiguration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Integration delivery configuration response |  -  |

<a name="getDeliveryConfigurationsByEnvironment"></a>
# **getDeliveryConfigurationsByEnvironment**
> IntegrationDeliveryConfigurationCollection getDeliveryConfigurationsByEnvironment(projectKey, environmentKey).execute();

Get delivery configurations by environment

Get delivery configurations by environment.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationDeliveryConfigurationsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String projectKey = "projectKey_example"; // The project key
    String environmentKey = "environmentKey_example"; // The environment key
    try {
      IntegrationDeliveryConfigurationCollection result = client
              .integrationDeliveryConfigurationsBeta
              .getDeliveryConfigurationsByEnvironment(projectKey, environmentKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#getDeliveryConfigurationsByEnvironment");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<IntegrationDeliveryConfigurationCollection> response = client
              .integrationDeliveryConfigurationsBeta
              .getDeliveryConfigurationsByEnvironment(projectKey, environmentKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#getDeliveryConfigurationsByEnvironment");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |

### Return type

[**IntegrationDeliveryConfigurationCollection**](IntegrationDeliveryConfigurationCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Integration delivery configuration collection response |  -  |

<a name="listDeliveryConfigurations"></a>
# **listDeliveryConfigurations**
> IntegrationDeliveryConfigurationCollection listDeliveryConfigurations().execute();

List all delivery configurations

List all delivery configurations.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationDeliveryConfigurationsBetaApi;
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
      IntegrationDeliveryConfigurationCollection result = client
              .integrationDeliveryConfigurationsBeta
              .listDeliveryConfigurations()
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#listDeliveryConfigurations");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<IntegrationDeliveryConfigurationCollection> response = client
              .integrationDeliveryConfigurationsBeta
              .listDeliveryConfigurations()
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#listDeliveryConfigurations");
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

[**IntegrationDeliveryConfigurationCollection**](IntegrationDeliveryConfigurationCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Integration delivery configuration collection response |  -  |

<a name="updateDeliveryConfiguration"></a>
# **updateDeliveryConfiguration**
> IntegrationDeliveryConfiguration updateDeliveryConfiguration(projectKey, environmentKey, integrationKey, id, patchOperation).execute();

Update delivery configuration

Update an integration delivery configuration. Updating an integration delivery configuration uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationDeliveryConfigurationsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String projectKey = "projectKey_example"; // The project key
    String environmentKey = "environmentKey_example"; // The environment key
    String integrationKey = "integrationKey_example"; // The integration key
    String id = "id_example"; // The configuration ID
    try {
      IntegrationDeliveryConfiguration result = client
              .integrationDeliveryConfigurationsBeta
              .updateDeliveryConfiguration(projectKey, environmentKey, integrationKey, id)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getVersion());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getIntegrationKey());
      System.out.println(result.getProjectKey());
      System.out.println(result.getEnvironmentKey());
      System.out.println(result.getConfig());
      System.out.println(result.getTrue());
      System.out.println(result.getName());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#updateDeliveryConfiguration");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<IntegrationDeliveryConfiguration> response = client
              .integrationDeliveryConfigurationsBeta
              .updateDeliveryConfiguration(projectKey, environmentKey, integrationKey, id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#updateDeliveryConfiguration");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **integrationKey** | **String**| The integration key | |
| **id** | **String**| The configuration ID | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**IntegrationDeliveryConfiguration**](IntegrationDeliveryConfiguration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Integration delivery configuration response |  -  |

<a name="validateDeliveryConfiguration"></a>
# **validateDeliveryConfiguration**
> IntegrationDeliveryConfigurationResponse validateDeliveryConfiguration(projectKey, environmentKey, integrationKey, id).execute();

Validate delivery configuration

Validate the saved delivery configuration, using the &#x60;validationRequest&#x60; in the integration&#39;s &#x60;manifest.json&#x60; file.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationDeliveryConfigurationsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String projectKey = "projectKey_example"; // The project key
    String environmentKey = "environmentKey_example"; // The environment key
    String integrationKey = "integrationKey_example"; // The integration key
    String id = "id_example"; // The configuration ID
    try {
      IntegrationDeliveryConfigurationResponse result = client
              .integrationDeliveryConfigurationsBeta
              .validateDeliveryConfiguration(projectKey, environmentKey, integrationKey, id)
              .execute();
      System.out.println(result);
      System.out.println(result.getStatusCode());
      System.out.println(result.getError());
      System.out.println(result.getTimestamp());
      System.out.println(result.getResponseBody());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#validateDeliveryConfiguration");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<IntegrationDeliveryConfigurationResponse> response = client
              .integrationDeliveryConfigurationsBeta
              .validateDeliveryConfiguration(projectKey, environmentKey, integrationKey, id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationDeliveryConfigurationsBetaApi#validateDeliveryConfiguration");
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
| **projectKey** | **String**| The project key | |
| **environmentKey** | **String**| The environment key | |
| **integrationKey** | **String**| The integration key | |
| **id** | **String**| The configuration ID | |

### Return type

[**IntegrationDeliveryConfigurationResponse**](IntegrationDeliveryConfigurationResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Integration delivery configuration validation response |  -  |

