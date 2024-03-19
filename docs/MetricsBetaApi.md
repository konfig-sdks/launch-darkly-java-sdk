# MetricsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createMetricGroup**](MetricsBetaApi.md#createMetricGroup) | **POST** /api/v2/projects/{projectKey}/metric-groups | Create metric group |
| [**deleteMetricGroup**](MetricsBetaApi.md#deleteMetricGroup) | **DELETE** /api/v2/projects/{projectKey}/metric-groups/{metricGroupKey} | Delete metric group |
| [**getMetricGroupDetails**](MetricsBetaApi.md#getMetricGroupDetails) | **GET** /api/v2/projects/{projectKey}/metric-groups/{metricGroupKey} | Get metric group |
| [**listMetricGroups**](MetricsBetaApi.md#listMetricGroups) | **GET** /api/v2/projects/{projectKey}/metric-groups | List metric groups |
| [**updateMetricGroupByKey**](MetricsBetaApi.md#updateMetricGroupByKey) | **PATCH** /api/v2/projects/{projectKey}/metric-groups/{metricGroupKey} | Patch metric group |


<a name="createMetricGroup"></a>
# **createMetricGroup**
> MetricGroupRep createMetricGroup(projectKey, metricGroupPost).execute();

Create metric group

Create a new metric group in the specified project

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.MetricsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    List<String> tags = Arrays.asList(); // Tags for the metric group
    String key = "key_example"; // A unique key to reference the metric group
    String name = "name_example"; // A human-friendly name for the metric group
    String kind = "funnel"; // The type of the metric group
    String maintainerId = "maintainerId_example"; // The ID of the member who maintains this metric group
    List<MetricInMetricGroupInput> metrics = Arrays.asList(); // An ordered list of the metrics in this metric group
    String projectKey = "projectKey_example"; // The project key
    String description = "description_example"; // Description of the metric group
    try {
      MetricGroupRep result = client
              .metricsBeta
              .createMetricGroup(tags, key, name, kind, maintainerId, metrics, projectKey)
              .description(description)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getKind());
      System.out.println(result.getLinks());
      System.out.println(result.getAccess());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLastModified());
      System.out.println(result.getMaintainer());
      System.out.println(result.getMetrics());
      System.out.println(result.getVersion());
      System.out.println(result.getExperiments());
      System.out.println(result.getExperimentCount());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsBetaApi#createMetricGroup");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<MetricGroupRep> response = client
              .metricsBeta
              .createMetricGroup(tags, key, name, kind, maintainerId, metrics, projectKey)
              .description(description)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsBetaApi#createMetricGroup");
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
| **metricGroupPost** | [**MetricGroupPost**](MetricGroupPost.md)|  | |

### Return type

[**MetricGroupRep**](MetricGroupRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Metric group response |  -  |

<a name="deleteMetricGroup"></a>
# **deleteMetricGroup**
> deleteMetricGroup(projectKey, metricGroupKey).execute();

Delete metric group

Delete a metric group by key.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.MetricsBetaApi;
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
    String metricGroupKey = "metricGroupKey_example"; // The metric group key
    try {
      client
              .metricsBeta
              .deleteMetricGroup(projectKey, metricGroupKey)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsBetaApi#deleteMetricGroup");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .metricsBeta
              .deleteMetricGroup(projectKey, metricGroupKey)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsBetaApi#deleteMetricGroup");
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
| **metricGroupKey** | **String**| The metric group key | |

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

<a name="getMetricGroupDetails"></a>
# **getMetricGroupDetails**
> MetricGroupRep getMetricGroupDetails(projectKey, metricGroupKey).expand(expand).execute();

Get metric group

Get information for a single metric group from the specific project.  ### Expanding the metric group response LaunchDarkly supports two fields for expanding the \&quot;Get metric group\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with either or both of the following fields:  - &#x60;experiments&#x60; includes all experiments from the specific project that use the metric group - &#x60;experimentCount&#x60; includes the number of experiments from the specific project that use the metric group  For example, &#x60;expand&#x3D;experiments&#x60; includes the &#x60;experiments&#x60; field in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.MetricsBetaApi;
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
    String metricGroupKey = "metricGroupKey_example"; // The metric group key
    String expand = "expand_example"; // A comma-separated list of properties that can reveal additional information in the response.
    try {
      MetricGroupRep result = client
              .metricsBeta
              .getMetricGroupDetails(projectKey, metricGroupKey)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getKind());
      System.out.println(result.getLinks());
      System.out.println(result.getAccess());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLastModified());
      System.out.println(result.getMaintainer());
      System.out.println(result.getMetrics());
      System.out.println(result.getVersion());
      System.out.println(result.getExperiments());
      System.out.println(result.getExperimentCount());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsBetaApi#getMetricGroupDetails");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<MetricGroupRep> response = client
              .metricsBeta
              .getMetricGroupDetails(projectKey, metricGroupKey)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsBetaApi#getMetricGroupDetails");
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
| **metricGroupKey** | **String**| The metric group key | |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. | [optional] |

### Return type

[**MetricGroupRep**](MetricGroupRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Metric group response |  -  |

<a name="listMetricGroups"></a>
# **listMetricGroups**
> MetricGroupCollectionRep listMetricGroups(projectKey).expand(expand).execute();

List metric groups

Get a list of all metric groups for the specified project.  ### Expanding the metric groups response LaunchDarkly supports one field for expanding the \&quot;Get metric groups\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with the following field:  - &#x60;experiments&#x60; includes all experiments from the specific project that use the metric group  For example, &#x60;expand&#x3D;experiments&#x60; includes the &#x60;experiments&#x60; field in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.MetricsBetaApi;
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
    String expand = "expand_example"; // A comma-separated list of properties that can reveal additional information in the response.
    try {
      MetricGroupCollectionRep result = client
              .metricsBeta
              .listMetricGroups(projectKey)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsBetaApi#listMetricGroups");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<MetricGroupCollectionRep> response = client
              .metricsBeta
              .listMetricGroups(projectKey)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsBetaApi#listMetricGroups");
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
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. | [optional] |

### Return type

[**MetricGroupCollectionRep**](MetricGroupCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Metric group collection response |  -  |

<a name="updateMetricGroupByKey"></a>
# **updateMetricGroupByKey**
> MetricGroupRep updateMetricGroupByKey(projectKey, metricGroupKey, patchOperation).execute();

Patch metric group

Patch a metric group by key. Updating a metric group uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.MetricsBetaApi;
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
    String metricGroupKey = "metricGroupKey_example"; // The metric group key
    try {
      MetricGroupRep result = client
              .metricsBeta
              .updateMetricGroupByKey(projectKey, metricGroupKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getKind());
      System.out.println(result.getLinks());
      System.out.println(result.getAccess());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLastModified());
      System.out.println(result.getMaintainer());
      System.out.println(result.getMetrics());
      System.out.println(result.getVersion());
      System.out.println(result.getExperiments());
      System.out.println(result.getExperimentCount());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsBetaApi#updateMetricGroupByKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<MetricGroupRep> response = client
              .metricsBeta
              .updateMetricGroupByKey(projectKey, metricGroupKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsBetaApi#updateMetricGroupByKey");
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
| **metricGroupKey** | **String**| The metric group key | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**MetricGroupRep**](MetricGroupRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Metric group response |  -  |

