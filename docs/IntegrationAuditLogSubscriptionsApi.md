# IntegrationAuditLogSubscriptionsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createSubscription**](IntegrationAuditLogSubscriptionsApi.md#createSubscription) | **POST** /api/v2/integrations/{integrationKey} | Create audit log subscription |
| [**deleteSubscription**](IntegrationAuditLogSubscriptionsApi.md#deleteSubscription) | **DELETE** /api/v2/integrations/{integrationKey}/{id} | Delete audit log subscription |
| [**getById**](IntegrationAuditLogSubscriptionsApi.md#getById) | **GET** /api/v2/integrations/{integrationKey}/{id} | Get audit log subscription by ID |
| [**listByIntegration**](IntegrationAuditLogSubscriptionsApi.md#listByIntegration) | **GET** /api/v2/integrations/{integrationKey} | Get audit log subscriptions by integration |
| [**updateSubscription**](IntegrationAuditLogSubscriptionsApi.md#updateSubscription) | **PATCH** /api/v2/integrations/{integrationKey}/{id} | Update audit log subscription |


<a name="createSubscription"></a>
# **createSubscription**
> Integration createSubscription(integrationKey, subscriptionPost).execute();

Create audit log subscription

Create an audit log subscription.&lt;br /&gt;&lt;br /&gt;For each subscription, you must specify the set of resources you wish to subscribe to audit log notifications for. You can describe these resources using a custom role policy. To learn more, read [Custom role concepts](https://docs.launchdarkly.com/home/members/role-concepts).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationAuditLogSubscriptionsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String name = "name_example"; // A human-friendly name for your audit log subscription.
    Map<String, Object> config = new HashMap(); // The unique set of fields required to configure an audit log subscription integration of this type. Refer to the <code>formVariables</code> field in the corresponding <code>manifest.json</code> at https://github.com/launchdarkly/integration-framework/tree/main/integrations for a full list of fields for the integration you wish to configure.
    String integrationKey = "integrationKey_example"; // The integration key
    List<String> tags = Arrays.asList(); // An array of tags for this subscription.
    List<StatementPost> statements = Arrays.asList();
    Boolean true = true; // Whether or not you want your subscription to actively send events.
    String url = "url_example"; // Slack webhook receiver URL. Only necessary for legacy Slack webhook integrations.
    String apiKey = "apiKey_example"; // Datadog API key. Only necessary for legacy Datadog webhook integrations.
    try {
      Integration result = client
              .integrationAuditLogSubscriptions
              .createSubscription(name, config, integrationKey)
              .tags(tags)
              .statements(statements)
              .true(true)
              .url(url)
              .apiKey(apiKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getKind());
      System.out.println(result.getName());
      System.out.println(result.getConfig());
      System.out.println(result.getStatements());
      System.out.println(result.getTrue());
      System.out.println(result.getAccess());
      System.out.println(result.getStatus());
      System.out.println(result.getUrl());
      System.out.println(result.getApiKey());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationAuditLogSubscriptionsApi#createSubscription");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Integration> response = client
              .integrationAuditLogSubscriptions
              .createSubscription(name, config, integrationKey)
              .tags(tags)
              .statements(statements)
              .true(true)
              .url(url)
              .apiKey(apiKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationAuditLogSubscriptionsApi#createSubscription");
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
| **integrationKey** | **String**| The integration key | |
| **subscriptionPost** | [**SubscriptionPost**](SubscriptionPost.md)|  | |

### Return type

[**Integration**](Integration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Integration response |  -  |

<a name="deleteSubscription"></a>
# **deleteSubscription**
> deleteSubscription(integrationKey, id).execute();

Delete audit log subscription

Delete an audit log subscription.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationAuditLogSubscriptionsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String integrationKey = "integrationKey_example"; // The integration key
    String id = "id_example"; // The subscription ID
    try {
      client
              .integrationAuditLogSubscriptions
              .deleteSubscription(integrationKey, id)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationAuditLogSubscriptionsApi#deleteSubscription");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .integrationAuditLogSubscriptions
              .deleteSubscription(integrationKey, id)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationAuditLogSubscriptionsApi#deleteSubscription");
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
| **integrationKey** | **String**| The integration key | |
| **id** | **String**| The subscription ID | |

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
> Integration getById(integrationKey, id).execute();

Get audit log subscription by ID

Get an audit log subscription by ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationAuditLogSubscriptionsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String integrationKey = "integrationKey_example"; // The integration key
    String id = "id_example"; // The subscription ID
    try {
      Integration result = client
              .integrationAuditLogSubscriptions
              .getById(integrationKey, id)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getKind());
      System.out.println(result.getName());
      System.out.println(result.getConfig());
      System.out.println(result.getStatements());
      System.out.println(result.getTrue());
      System.out.println(result.getAccess());
      System.out.println(result.getStatus());
      System.out.println(result.getUrl());
      System.out.println(result.getApiKey());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationAuditLogSubscriptionsApi#getById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Integration> response = client
              .integrationAuditLogSubscriptions
              .getById(integrationKey, id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationAuditLogSubscriptionsApi#getById");
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
| **integrationKey** | **String**| The integration key | |
| **id** | **String**| The subscription ID | |

### Return type

[**Integration**](Integration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Integration response |  -  |

<a name="listByIntegration"></a>
# **listByIntegration**
> Integrations listByIntegration(integrationKey).execute();

Get audit log subscriptions by integration

Get all audit log subscriptions associated with a given integration.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationAuditLogSubscriptionsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String integrationKey = "integrationKey_example"; // The integration key
    try {
      Integrations result = client
              .integrationAuditLogSubscriptions
              .listByIntegration(integrationKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
      System.out.println(result.getKey());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationAuditLogSubscriptionsApi#listByIntegration");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Integrations> response = client
              .integrationAuditLogSubscriptions
              .listByIntegration(integrationKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationAuditLogSubscriptionsApi#listByIntegration");
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
| **integrationKey** | **String**| The integration key | |

### Return type

[**Integrations**](Integrations.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Integrations collection response |  -  |

<a name="updateSubscription"></a>
# **updateSubscription**
> Integration updateSubscription(integrationKey, id, patchOperation).execute();

Update audit log subscription

Update an audit log subscription configuration. Updating an audit log subscription uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.IntegrationAuditLogSubscriptionsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String integrationKey = "integrationKey_example"; // The integration key
    String id = "id_example"; // The ID of the audit log subscription
    try {
      Integration result = client
              .integrationAuditLogSubscriptions
              .updateSubscription(integrationKey, id)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getKind());
      System.out.println(result.getName());
      System.out.println(result.getConfig());
      System.out.println(result.getStatements());
      System.out.println(result.getTrue());
      System.out.println(result.getAccess());
      System.out.println(result.getStatus());
      System.out.println(result.getUrl());
      System.out.println(result.getApiKey());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationAuditLogSubscriptionsApi#updateSubscription");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Integration> response = client
              .integrationAuditLogSubscriptions
              .updateSubscription(integrationKey, id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling IntegrationAuditLogSubscriptionsApi#updateSubscription");
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
| **integrationKey** | **String**| The integration key | |
| **id** | **String**| The ID of the audit log subscription | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**Integration**](Integration.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Integration response |  -  |

