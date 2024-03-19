# IntegrationsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createPersistentStoreIntegration**](IntegrationsBetaApi.md#createPersistentStoreIntegration) | **POST** /api/v2/integration-capabilities/big-segment-store/{projectKey}/{environmentKey}/{integrationKey} | Create big segment store integration |
| [**deleteBigSegmentStoreIntegration**](IntegrationsBetaApi.md#deleteBigSegmentStoreIntegration) | **DELETE** /api/v2/integration-capabilities/big-segment-store/{projectKey}/{environmentKey}/{integrationKey}/{integrationId} | Delete big segment store integration |
| [**getBigSegmentStoreIntegrationById**](IntegrationsBetaApi.md#getBigSegmentStoreIntegrationById) | **GET** /api/v2/integration-capabilities/big-segment-store/{projectKey}/{environmentKey}/{integrationKey}/{integrationId} | Get big segment store integration by ID |
| [**listBigSegmentStoreIntegrations**](IntegrationsBetaApi.md#listBigSegmentStoreIntegrations) | **GET** /api/v2/integration-capabilities/big-segment-store | List all big segment store integrations |
| [**updateBigSegmentStore**](IntegrationsBetaApi.md#updateBigSegmentStore) | **PATCH** /api/v2/integration-capabilities/big-segment-store/{projectKey}/{environmentKey}/{integrationKey}/{integrationId} | Update big segment store integration |


<a name="createPersistentStoreIntegration"></a>
# **createPersistentStoreIntegration**
> BigSegmentStoreIntegration createPersistentStoreIntegration(projectKey, environmentKey, integrationKey, integrationDeliveryConfigurationPost).execute();

Create big segment store integration

 Create a persistent store integration.  If you are using server-side SDKs, segments synced from external tools and larger list-based segments require a persistent store within your infrastructure. LaunchDarkly keeps the persistent store up to date and consults it during flag evaluation.  You can use either Redis or DynamoDB as your persistent store. When you create a persistent store integration, the fields in the &#x60;config&#x60; object in the request vary depending on which persistent store you use.  If you are using Redis to create your persistent store integration, you will need to know:  * Your Redis host * Your Redis port * Your Redis username * Your Redis password * Whether or not LaunchDarkly should connect using TLS  If you are using DynamoDB to create your persistent store integration, you will need to know:  * Your DynamoDB table name. The table must have the following schema:   * Partition key: &#x60;namespace&#x60; (string)   * Sort key: &#x60;key&#x60; (string) * Your DynamoDB Amazon Web Services (AWS) region. * Your AWS role Amazon Resource Name (ARN). This is the role that LaunchDarkly will assume to manage your DynamoDB table. * The External ID you specified when creating your Amazon Resource Name (ARN).  To learn more, read [Segment configuration](https://docs.launchdarkly.com/home/segments/big-segment-configuration). 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationsBetaApi;
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
    String integrationKey = "integrationKey_example"; // The integration key, either `redis` or `dynamodb`
    List<String> tags = Arrays.asList(); // Tags to associate with the integration
    Boolean true = true; // Whether the integration configuration is active. Default value is false.
    String name = "name_example"; // Name to identify the integration
    try {
      BigSegmentStoreIntegration result = client
              .integrationsBeta
              .createPersistentStoreIntegration(config, projectKey, environmentKey, integrationKey)
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
      System.out.println(result.getStatus());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#createPersistentStoreIntegration");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<BigSegmentStoreIntegration> response = client
              .integrationsBeta
              .createPersistentStoreIntegration(config, projectKey, environmentKey, integrationKey)
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
      System.err.println("Exception when calling IntegrationsBetaApi#createPersistentStoreIntegration");
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
| **integrationKey** | **String**| The integration key, either &#x60;redis&#x60; or &#x60;dynamodb&#x60; | |
| **integrationDeliveryConfigurationPost** | [**IntegrationDeliveryConfigurationPost**](IntegrationDeliveryConfigurationPost.md)|  | |

### Return type

[**BigSegmentStoreIntegration**](BigSegmentStoreIntegration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Big segment store response |  -  |

<a name="deleteBigSegmentStoreIntegration"></a>
# **deleteBigSegmentStoreIntegration**
> deleteBigSegmentStoreIntegration(projectKey, environmentKey, integrationKey, integrationId).execute();

Delete big segment store integration

Delete a persistent store integration. Each integration uses either Redis or DynamoDB.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationsBetaApi;
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
    String integrationKey = "integrationKey_example"; // The integration key, either `redis` or `dynamodb`
    String integrationId = "integrationId_example"; // The integration ID
    try {
      client
              .integrationsBeta
              .deleteBigSegmentStoreIntegration(projectKey, environmentKey, integrationKey, integrationId)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#deleteBigSegmentStoreIntegration");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .integrationsBeta
              .deleteBigSegmentStoreIntegration(projectKey, environmentKey, integrationKey, integrationId)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#deleteBigSegmentStoreIntegration");
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
| **integrationKey** | **String**| The integration key, either &#x60;redis&#x60; or &#x60;dynamodb&#x60; | |
| **integrationId** | **String**| The integration ID | |

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
| **204** | Action completed successfully |  -  |

<a name="getBigSegmentStoreIntegrationById"></a>
# **getBigSegmentStoreIntegrationById**
> BigSegmentStoreIntegration getBigSegmentStoreIntegrationById(projectKey, environmentKey, integrationKey, integrationId).execute();

Get big segment store integration by ID

Get a big segment store integration by ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationsBetaApi;
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
    String integrationKey = "integrationKey_example"; // The integration key, either `redis` or `dynamodb`
    String integrationId = "integrationId_example"; // The integration ID
    try {
      BigSegmentStoreIntegration result = client
              .integrationsBeta
              .getBigSegmentStoreIntegrationById(projectKey, environmentKey, integrationKey, integrationId)
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
      System.out.println(result.getStatus());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#getBigSegmentStoreIntegrationById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<BigSegmentStoreIntegration> response = client
              .integrationsBeta
              .getBigSegmentStoreIntegrationById(projectKey, environmentKey, integrationKey, integrationId)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#getBigSegmentStoreIntegrationById");
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
| **integrationKey** | **String**| The integration key, either &#x60;redis&#x60; or &#x60;dynamodb&#x60; | |
| **integrationId** | **String**| The integration ID | |

### Return type

[**BigSegmentStoreIntegration**](BigSegmentStoreIntegration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Big segment store response |  -  |

<a name="listBigSegmentStoreIntegrations"></a>
# **listBigSegmentStoreIntegrations**
> BigSegmentStoreIntegrationCollection listBigSegmentStoreIntegrations().execute();

List all big segment store integrations

List all big segment store integrations.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationsBetaApi;
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
      BigSegmentStoreIntegrationCollection result = client
              .integrationsBeta
              .listBigSegmentStoreIntegrations()
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#listBigSegmentStoreIntegrations");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<BigSegmentStoreIntegrationCollection> response = client
              .integrationsBeta
              .listBigSegmentStoreIntegrations()
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#listBigSegmentStoreIntegrations");
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

[**BigSegmentStoreIntegrationCollection**](BigSegmentStoreIntegrationCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Big segment store collection response |  -  |

<a name="updateBigSegmentStore"></a>
# **updateBigSegmentStore**
> BigSegmentStoreIntegration updateBigSegmentStore(projectKey, environmentKey, integrationKey, integrationId, patchOperation).execute();

Update big segment store integration

Update a big segment store integration. Updating a big segment store requires a [JSON Patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationsBetaApi;
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
    String integrationKey = "integrationKey_example"; // The integration key, either `redis` or `dynamodb`
    String integrationId = "integrationId_example"; // The integration ID
    try {
      BigSegmentStoreIntegration result = client
              .integrationsBeta
              .updateBigSegmentStore(projectKey, environmentKey, integrationKey, integrationId)
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
      System.out.println(result.getStatus());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#updateBigSegmentStore");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<BigSegmentStoreIntegration> response = client
              .integrationsBeta
              .updateBigSegmentStore(projectKey, environmentKey, integrationKey, integrationId)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationsBetaApi#updateBigSegmentStore");
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
| **integrationKey** | **String**| The integration key, either &#x60;redis&#x60; or &#x60;dynamodb&#x60; | |
| **integrationId** | **String**| The integration ID | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**BigSegmentStoreIntegration**](BigSegmentStoreIntegration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Big segment store response |  -  |

