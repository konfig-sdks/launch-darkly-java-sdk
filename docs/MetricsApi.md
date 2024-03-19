# MetricsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNewMetric**](MetricsApi.md#createNewMetric) | **POST** /api/v2/metrics/{projectKey} | Create metric |
| [**deleteByProjectAndMetricKey**](MetricsApi.md#deleteByProjectAndMetricKey) | **DELETE** /api/v2/metrics/{projectKey}/{metricKey} | Delete metric |
| [**getSingleMetric**](MetricsApi.md#getSingleMetric) | **GET** /api/v2/metrics/{projectKey}/{metricKey} | Get metric |
| [**listForProject**](MetricsApi.md#listForProject) | **GET** /api/v2/metrics/{projectKey} | List metrics |
| [**updateByJsonPatch**](MetricsApi.md#updateByJsonPatch) | **PATCH** /api/v2/metrics/{projectKey}/{metricKey} | Update metric |


<a name="createNewMetric"></a>
# **createNewMetric**
> MetricRep createNewMetric(projectKey, metricPost).execute();

Create metric

Create a new metric in the specified project. The expected &#x60;POST&#x60; body differs depending on the specified &#x60;kind&#x60; property.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.MetricsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String key = "key_example"; // A unique key to reference the metric
    String kind = "pageview"; // The kind of event your metric will track
    String projectKey = "projectKey_example"; // The project key
    List<String> tags = Arrays.asList(); // Tags for the metric
    String description = "description_example"; // Description of the metric
    String name = "name_example"; // A human-friendly name for the metric
    String selector = "selector_example"; // One or more CSS selectors. Required for click metrics only.
    List<UrlPost> urls = Arrays.asList(); // One or more target URLs. Required for click and pageview metrics only.
    Boolean isActive = true; // Whether the metric is active. Set to <code>true</code> to record click or pageview metrics. Not applicable for custom metrics.
    Boolean isNumeric = true; // Whether to track numeric changes in value against a baseline (<code>true</code>) or to track a conversion when an end user takes an action (<code>false</code>). Required for custom metrics only.
    String unit = "unit_example"; // The unit of measure. Applicable for numeric custom metrics only.
    String eventKey = "eventKey_example"; // The event key to use in your code. Required for custom conversion/binary and custom numeric metrics only.
    String successCriteria = "HigherThanBaseline"; // Success criteria. Required for custom numeric metrics, optional for custom conversion metrics.
    List<String> randomizationUnits = Arrays.asList(); // An array of randomization units allowed for this metric
    String unitAggregationType = "average"; // The method in which multiple unit event values are aggregated
    try {
      MetricRep result = client
              .metrics
              .createNewMetric(key, kind, projectKey)
              .tags(tags)
              .description(description)
              .name(name)
              .selector(selector)
              .urls(urls)
              .isActive(isActive)
              .isNumeric(isNumeric)
              .unit(unit)
              .eventKey(eventKey)
              .successCriteria(successCriteria)
              .randomizationUnits(randomizationUnits)
              .unitAggregationType(unitAggregationType)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getDescription());
      System.out.println(result.getExperimentCount());
      System.out.println(result.getMetricGroupCount());
      System.out.println(result.getId());
      System.out.println(result.getVersionId());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getKind());
      System.out.println(result.getAttachedFlagCount());
      System.out.println(result.getLinks());
      System.out.println(result.getSite());
      System.out.println(result.getAccess());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLastModified());
      System.out.println(result.getMaintainerId());
      System.out.println(result.getMaintainer());
      System.out.println(result.getIsNumeric());
      System.out.println(result.getSuccessCriteria());
      System.out.println(result.getUnit());
      System.out.println(result.getEventKey());
      System.out.println(result.getRandomizationUnits());
      System.out.println(result.getUnitAggregationType());
      System.out.println(result.getAnalysisType());
      System.out.println(result.getPercentileValue());
      System.out.println(result.getEventDefault());
      System.out.println(result.getExperiments());
      System.out.println(result.getMetricGroups());
      System.out.println(result.getIsActive());
      System.out.println(result.getAttachedFeatures());
      System.out.println(result.getVersion());
      System.out.println(result.getSelector());
      System.out.println(result.getUrls());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsApi#createNewMetric");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<MetricRep> response = client
              .metrics
              .createNewMetric(key, kind, projectKey)
              .tags(tags)
              .description(description)
              .name(name)
              .selector(selector)
              .urls(urls)
              .isActive(isActive)
              .isNumeric(isNumeric)
              .unit(unit)
              .eventKey(eventKey)
              .successCriteria(successCriteria)
              .randomizationUnits(randomizationUnits)
              .unitAggregationType(unitAggregationType)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsApi#createNewMetric");
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
| **metricPost** | [**MetricPost**](MetricPost.md)|  | |

### Return type

[**MetricRep**](MetricRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Metric response |  -  |

<a name="deleteByProjectAndMetricKey"></a>
# **deleteByProjectAndMetricKey**
> deleteByProjectAndMetricKey(projectKey, metricKey).execute();

Delete metric

Delete a metric by key.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.MetricsApi;
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
    String metricKey = "metricKey_example"; // The metric key
    try {
      client
              .metrics
              .deleteByProjectAndMetricKey(projectKey, metricKey)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsApi#deleteByProjectAndMetricKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .metrics
              .deleteByProjectAndMetricKey(projectKey, metricKey)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsApi#deleteByProjectAndMetricKey");
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
| **metricKey** | **String**| The metric key | |

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

<a name="getSingleMetric"></a>
# **getSingleMetric**
> MetricRep getSingleMetric(projectKey, metricKey).expand(expand).versionId(versionId).execute();

Get metric

Get information for a single metric from the specific project.  ### Expanding the metric response LaunchDarkly supports four fields for expanding the \&quot;Get metric\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  - &#x60;experiments&#x60; includes all experiments from the specific project that use the metric - &#x60;experimentCount&#x60; includes the number of experiments from the specific project that use the metric - &#x60;metricGroups&#x60; includes all metric groups from the specific project that use the metric - &#x60;metricGroupCount&#x60; includes the number of metric groups from the specific project that use the metric  For example, &#x60;expand&#x3D;experiments&#x60; includes the &#x60;experiments&#x60; field in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.MetricsApi;
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
    String metricKey = "metricKey_example"; // The metric key
    String expand = "expand_example"; // A comma-separated list of properties that can reveal additional information in the response.
    String versionId = "versionId_example"; // The specific version ID of the metric
    try {
      MetricRep result = client
              .metrics
              .getSingleMetric(projectKey, metricKey)
              .expand(expand)
              .versionId(versionId)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getDescription());
      System.out.println(result.getExperimentCount());
      System.out.println(result.getMetricGroupCount());
      System.out.println(result.getId());
      System.out.println(result.getVersionId());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getKind());
      System.out.println(result.getAttachedFlagCount());
      System.out.println(result.getLinks());
      System.out.println(result.getSite());
      System.out.println(result.getAccess());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLastModified());
      System.out.println(result.getMaintainerId());
      System.out.println(result.getMaintainer());
      System.out.println(result.getIsNumeric());
      System.out.println(result.getSuccessCriteria());
      System.out.println(result.getUnit());
      System.out.println(result.getEventKey());
      System.out.println(result.getRandomizationUnits());
      System.out.println(result.getUnitAggregationType());
      System.out.println(result.getAnalysisType());
      System.out.println(result.getPercentileValue());
      System.out.println(result.getEventDefault());
      System.out.println(result.getExperiments());
      System.out.println(result.getMetricGroups());
      System.out.println(result.getIsActive());
      System.out.println(result.getAttachedFeatures());
      System.out.println(result.getVersion());
      System.out.println(result.getSelector());
      System.out.println(result.getUrls());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsApi#getSingleMetric");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<MetricRep> response = client
              .metrics
              .getSingleMetric(projectKey, metricKey)
              .expand(expand)
              .versionId(versionId)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsApi#getSingleMetric");
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
| **metricKey** | **String**| The metric key | |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. | [optional] |
| **versionId** | **String**| The specific version ID of the metric | [optional] |

### Return type

[**MetricRep**](MetricRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Metric response |  -  |

<a name="listForProject"></a>
# **listForProject**
> MetricCollectionRep listForProject(projectKey).expand(expand).execute();

List metrics

Get a list of all metrics for the specified project.  ### Expanding the metric list response LaunchDarkly supports expanding the \&quot;List metrics\&quot; response. By default, the expandable field is **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add the following supported field:  - &#x60;experimentCount&#x60; includes the number of experiments from the specific project that use the metric  For example, &#x60;expand&#x3D;experimentCount&#x60; includes the &#x60;experimentCount&#x60; field for each metric in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.MetricsApi;
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
      MetricCollectionRep result = client
              .metrics
              .listForProject(projectKey)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsApi#listForProject");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<MetricCollectionRep> response = client
              .metrics
              .listForProject(projectKey)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsApi#listForProject");
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

[**MetricCollectionRep**](MetricCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Metrics collection response |  -  |

<a name="updateByJsonPatch"></a>
# **updateByJsonPatch**
> MetricRep updateByJsonPatch(projectKey, metricKey, patchOperation).execute();

Update metric

Patch a metric by key. Updating a metric uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.MetricsApi;
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
    String metricKey = "metricKey_example"; // The metric key
    try {
      MetricRep result = client
              .metrics
              .updateByJsonPatch(projectKey, metricKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getDescription());
      System.out.println(result.getExperimentCount());
      System.out.println(result.getMetricGroupCount());
      System.out.println(result.getId());
      System.out.println(result.getVersionId());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getKind());
      System.out.println(result.getAttachedFlagCount());
      System.out.println(result.getLinks());
      System.out.println(result.getSite());
      System.out.println(result.getAccess());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLastModified());
      System.out.println(result.getMaintainerId());
      System.out.println(result.getMaintainer());
      System.out.println(result.getIsNumeric());
      System.out.println(result.getSuccessCriteria());
      System.out.println(result.getUnit());
      System.out.println(result.getEventKey());
      System.out.println(result.getRandomizationUnits());
      System.out.println(result.getUnitAggregationType());
      System.out.println(result.getAnalysisType());
      System.out.println(result.getPercentileValue());
      System.out.println(result.getEventDefault());
      System.out.println(result.getExperiments());
      System.out.println(result.getMetricGroups());
      System.out.println(result.getIsActive());
      System.out.println(result.getAttachedFeatures());
      System.out.println(result.getVersion());
      System.out.println(result.getSelector());
      System.out.println(result.getUrls());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsApi#updateByJsonPatch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<MetricRep> response = client
              .metrics
              .updateByJsonPatch(projectKey, metricKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling MetricsApi#updateByJsonPatch");
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
| **metricKey** | **String**| The metric key | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**MetricRep**](MetricRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Metric response |  -  |

