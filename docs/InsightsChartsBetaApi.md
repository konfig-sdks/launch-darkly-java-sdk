# InsightsChartsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deploymentFrequencyChartData**](InsightsChartsBetaApi.md#deploymentFrequencyChartData) | **GET** /api/v2/engineering-insights/charts/deployments/frequency | Get deployment frequency chart data |
| [**getFlagStatusChartData**](InsightsChartsBetaApi.md#getFlagStatusChartData) | **GET** /api/v2/engineering-insights/charts/flags/status | Get flag status chart data |
| [**leadTimeChartData**](InsightsChartsBetaApi.md#leadTimeChartData) | **GET** /api/v2/engineering-insights/charts/lead-time | Get lead time chart data |
| [**releaseFrequencyData**](InsightsChartsBetaApi.md#releaseFrequencyData) | **GET** /api/v2/engineering-insights/charts/releases/frequency | Get release frequency chart data |
| [**staleFlagsChartData**](InsightsChartsBetaApi.md#staleFlagsChartData) | **GET** /api/v2/engineering-insights/charts/flags/stale | Get stale flags chart data |


<a name="deploymentFrequencyChartData"></a>
# **deploymentFrequencyChartData**
> InsightsChart deploymentFrequencyChartData().projectKey(projectKey).environmentKey(environmentKey).applicationKey(applicationKey).from(from).to(to).bucketType(bucketType).bucketMs(bucketMs).groupBy(groupBy).expand(expand).execute();

Get deployment frequency chart data

Get deployment frequency chart data. Engineering insights displays deployment frequency data in the [deployment frequency metric view](https://docs.launchdarkly.com/home/engineering-insights/metrics/deployment).  ### Expanding the chart response  LaunchDarkly supports expanding the chart response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;metrics&#x60; includes details on the metrics related to deployment frequency  For example, use &#x60;?expand&#x3D;metrics&#x60; to include the &#x60;metrics&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsChartsBetaApi;
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
    String applicationKey = "applicationKey_example"; // Comma separated list of application keys
    OffsetDateTime from = OffsetDateTime.now(); // Unix timestamp in milliseconds. Default value is 7 days ago.
    OffsetDateTime to = OffsetDateTime.now(); // Unix timestamp in milliseconds. Default value is now.
    String bucketType = "bucketType_example"; // Specify type of bucket. Options: `rolling`, `hour`, `day`. Default: `rolling`.
    Long bucketMs = 56L; // Duration of intervals for x-axis in milliseconds. Default value is one day (`86400000` milliseconds).
    String groupBy = "groupBy_example"; // Options: `application`, `kind`
    String expand = "expand_example"; // Options: `metrics`
    try {
      InsightsChart result = client
              .insightsChartsBeta
              .deploymentFrequencyChartData()
              .projectKey(projectKey)
              .environmentKey(environmentKey)
              .applicationKey(applicationKey)
              .from(from)
              .to(to)
              .bucketType(bucketType)
              .bucketMs(bucketMs)
              .groupBy(groupBy)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getMetadata());
      System.out.println(result.getSeries());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsChartsBetaApi#deploymentFrequencyChartData");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<InsightsChart> response = client
              .insightsChartsBeta
              .deploymentFrequencyChartData()
              .projectKey(projectKey)
              .environmentKey(environmentKey)
              .applicationKey(applicationKey)
              .from(from)
              .to(to)
              .bucketType(bucketType)
              .bucketMs(bucketMs)
              .groupBy(groupBy)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsChartsBetaApi#deploymentFrequencyChartData");
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
| **projectKey** | **String**| The project key | [optional] |
| **environmentKey** | **String**| The environment key | [optional] |
| **applicationKey** | **String**| Comma separated list of application keys | [optional] |
| **from** | **OffsetDateTime**| Unix timestamp in milliseconds. Default value is 7 days ago. | [optional] |
| **to** | **OffsetDateTime**| Unix timestamp in milliseconds. Default value is now. | [optional] |
| **bucketType** | **String**| Specify type of bucket. Options: &#x60;rolling&#x60;, &#x60;hour&#x60;, &#x60;day&#x60;. Default: &#x60;rolling&#x60;. | [optional] |
| **bucketMs** | **Long**| Duration of intervals for x-axis in milliseconds. Default value is one day (&#x60;86400000&#x60; milliseconds). | [optional] |
| **groupBy** | **String**| Options: &#x60;application&#x60;, &#x60;kind&#x60; | [optional] |
| **expand** | **String**| Options: &#x60;metrics&#x60; | [optional] |

### Return type

[**InsightsChart**](InsightsChart.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Chart response |  -  |

<a name="getFlagStatusChartData"></a>
# **getFlagStatusChartData**
> InsightsChart getFlagStatusChartData(projectKey, environmentKey).applicationKey(applicationKey).execute();

Get flag status chart data

Get flag status chart data. To learn more, read [Using the flag status chart](https://docs.launchdarkly.com/home/engineering-insights/metrics/flag-health#using-the-flag-status-chart).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsChartsBetaApi;
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
    String applicationKey = "applicationKey_example"; // Comma separated list of application keys
    try {
      InsightsChart result = client
              .insightsChartsBeta
              .getFlagStatusChartData(projectKey, environmentKey)
              .applicationKey(applicationKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getMetadata());
      System.out.println(result.getSeries());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsChartsBetaApi#getFlagStatusChartData");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<InsightsChart> response = client
              .insightsChartsBeta
              .getFlagStatusChartData(projectKey, environmentKey)
              .applicationKey(applicationKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsChartsBetaApi#getFlagStatusChartData");
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
| **applicationKey** | **String**| Comma separated list of application keys | [optional] |

### Return type

[**InsightsChart**](InsightsChart.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Chart response |  -  |

<a name="leadTimeChartData"></a>
# **leadTimeChartData**
> InsightsChart leadTimeChartData(projectKey).environmentKey(environmentKey).applicationKey(applicationKey).from(from).to(to).bucketType(bucketType).bucketMs(bucketMs).groupBy(groupBy).expand(expand).execute();

Get lead time chart data

Get lead time chart data. The engineering insights UI displays lead time data in the [lead time metric view](https://docs.launchdarkly.com/home/engineering-insights/metrics/lead-time).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsChartsBetaApi;
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
    String applicationKey = "applicationKey_example"; // Comma separated list of application keys
    Long from = 56L; // Unix timestamp in milliseconds. Default value is 7 days ago.
    Long to = 56L; // Unix timestamp in milliseconds. Default value is now.
    String bucketType = "bucketType_example"; // Specify type of bucket. Options: `rolling`, `hour`, `day`. Default: `rolling`.
    Long bucketMs = 56L; // Duration of intervals for x-axis in milliseconds. Default value is one day (`86400000` milliseconds).
    String groupBy = "groupBy_example"; // Options: `application`, `stage`. Default: `stage`.
    String expand = "expand_example"; // Options: `metrics`, `percentiles`.
    try {
      InsightsChart result = client
              .insightsChartsBeta
              .leadTimeChartData(projectKey)
              .environmentKey(environmentKey)
              .applicationKey(applicationKey)
              .from(from)
              .to(to)
              .bucketType(bucketType)
              .bucketMs(bucketMs)
              .groupBy(groupBy)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getMetadata());
      System.out.println(result.getSeries());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsChartsBetaApi#leadTimeChartData");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<InsightsChart> response = client
              .insightsChartsBeta
              .leadTimeChartData(projectKey)
              .environmentKey(environmentKey)
              .applicationKey(applicationKey)
              .from(from)
              .to(to)
              .bucketType(bucketType)
              .bucketMs(bucketMs)
              .groupBy(groupBy)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsChartsBetaApi#leadTimeChartData");
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
| **environmentKey** | **String**| The environment key | [optional] |
| **applicationKey** | **String**| Comma separated list of application keys | [optional] |
| **from** | **Long**| Unix timestamp in milliseconds. Default value is 7 days ago. | [optional] |
| **to** | **Long**| Unix timestamp in milliseconds. Default value is now. | [optional] |
| **bucketType** | **String**| Specify type of bucket. Options: &#x60;rolling&#x60;, &#x60;hour&#x60;, &#x60;day&#x60;. Default: &#x60;rolling&#x60;. | [optional] |
| **bucketMs** | **Long**| Duration of intervals for x-axis in milliseconds. Default value is one day (&#x60;86400000&#x60; milliseconds). | [optional] |
| **groupBy** | **String**| Options: &#x60;application&#x60;, &#x60;stage&#x60;. Default: &#x60;stage&#x60;. | [optional] |
| **expand** | **String**| Options: &#x60;metrics&#x60;, &#x60;percentiles&#x60;. | [optional] |

### Return type

[**InsightsChart**](InsightsChart.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Chart response |  -  |

<a name="releaseFrequencyData"></a>
# **releaseFrequencyData**
> InsightsChart releaseFrequencyData(projectKey, environmentKey).applicationKey(applicationKey).hasExperiments(hasExperiments).global(global).groupBy(groupBy).from(from).to(to).bucketType(bucketType).bucketMs(bucketMs).expand(expand).execute();

Get release frequency chart data

Get release frequency chart data. Engineering insights displays release frequency data in the [release frequency metric view](https://docs.launchdarkly.com/home/engineering-insights/metrics/release).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsChartsBetaApi;
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
    String applicationKey = "applicationKey_example"; // Comma separated list of application keys
    Boolean hasExperiments = true; // Filter events to those associated with an experiment (`true`) or without an experiment (`false`)
    String global = "global_example"; // Filter to include or exclude global events. Default value is `include`. Options: `include`, `exclude`
    String groupBy = "groupBy_example"; // Property to group results by. Options: `impact`
    OffsetDateTime from = OffsetDateTime.now(); // Unix timestamp in milliseconds. Default value is 7 days ago.
    OffsetDateTime to = OffsetDateTime.now(); // Unix timestamp in milliseconds. Default value is now.
    String bucketType = "bucketType_example"; // Specify type of bucket. Options: `rolling`, `hour`, `day`. Default: `rolling`.
    Long bucketMs = 56L; // Duration of intervals for x-axis in milliseconds. Default value is one day (`86400000` milliseconds).
    String expand = "expand_example"; // Options: `metrics`
    try {
      InsightsChart result = client
              .insightsChartsBeta
              .releaseFrequencyData(projectKey, environmentKey)
              .applicationKey(applicationKey)
              .hasExperiments(hasExperiments)
              .global(global)
              .groupBy(groupBy)
              .from(from)
              .to(to)
              .bucketType(bucketType)
              .bucketMs(bucketMs)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getMetadata());
      System.out.println(result.getSeries());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsChartsBetaApi#releaseFrequencyData");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<InsightsChart> response = client
              .insightsChartsBeta
              .releaseFrequencyData(projectKey, environmentKey)
              .applicationKey(applicationKey)
              .hasExperiments(hasExperiments)
              .global(global)
              .groupBy(groupBy)
              .from(from)
              .to(to)
              .bucketType(bucketType)
              .bucketMs(bucketMs)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsChartsBetaApi#releaseFrequencyData");
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
| **applicationKey** | **String**| Comma separated list of application keys | [optional] |
| **hasExperiments** | **Boolean**| Filter events to those associated with an experiment (&#x60;true&#x60;) or without an experiment (&#x60;false&#x60;) | [optional] |
| **global** | **String**| Filter to include or exclude global events. Default value is &#x60;include&#x60;. Options: &#x60;include&#x60;, &#x60;exclude&#x60; | [optional] |
| **groupBy** | **String**| Property to group results by. Options: &#x60;impact&#x60; | [optional] |
| **from** | **OffsetDateTime**| Unix timestamp in milliseconds. Default value is 7 days ago. | [optional] |
| **to** | **OffsetDateTime**| Unix timestamp in milliseconds. Default value is now. | [optional] |
| **bucketType** | **String**| Specify type of bucket. Options: &#x60;rolling&#x60;, &#x60;hour&#x60;, &#x60;day&#x60;. Default: &#x60;rolling&#x60;. | [optional] |
| **bucketMs** | **Long**| Duration of intervals for x-axis in milliseconds. Default value is one day (&#x60;86400000&#x60; milliseconds). | [optional] |
| **expand** | **String**| Options: &#x60;metrics&#x60; | [optional] |

### Return type

[**InsightsChart**](InsightsChart.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Chart response |  -  |

<a name="staleFlagsChartData"></a>
# **staleFlagsChartData**
> InsightsChart staleFlagsChartData(projectKey, environmentKey).applicationKey(applicationKey).groupBy(groupBy).maintainerId(maintainerId).maintainerTeamKey(maintainerTeamKey).expand(expand).execute();

Get stale flags chart data

Get stale flags chart data. Engineering insights displays stale flags data in the [flag health metric view](https://docs.launchdarkly.com/home/engineering-insights/metrics/flag-health).  ### Expanding the chart response  LaunchDarkly supports expanding the chart response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;metrics&#x60; includes details on the metrics related to stale flags  For example, use &#x60;?expand&#x3D;metrics&#x60; to include the &#x60;metrics&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsChartsBetaApi;
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
    String applicationKey = "applicationKey_example"; // Comma separated list of application keys
    String groupBy = "groupBy_example"; // Property to group results by. Options: `maintainer`
    String maintainerId = "maintainerId_example"; // Comma-separated list of individual maintainers to filter results.
    String maintainerTeamKey = "maintainerTeamKey_example"; // Comma-separated list of team maintainer keys to filter results.
    String expand = "expand_example"; // Options: `metrics`
    try {
      InsightsChart result = client
              .insightsChartsBeta
              .staleFlagsChartData(projectKey, environmentKey)
              .applicationKey(applicationKey)
              .groupBy(groupBy)
              .maintainerId(maintainerId)
              .maintainerTeamKey(maintainerTeamKey)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getMetadata());
      System.out.println(result.getSeries());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsChartsBetaApi#staleFlagsChartData");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<InsightsChart> response = client
              .insightsChartsBeta
              .staleFlagsChartData(projectKey, environmentKey)
              .applicationKey(applicationKey)
              .groupBy(groupBy)
              .maintainerId(maintainerId)
              .maintainerTeamKey(maintainerTeamKey)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsChartsBetaApi#staleFlagsChartData");
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
| **applicationKey** | **String**| Comma separated list of application keys | [optional] |
| **groupBy** | **String**| Property to group results by. Options: &#x60;maintainer&#x60; | [optional] |
| **maintainerId** | **String**| Comma-separated list of individual maintainers to filter results. | [optional] |
| **maintainerTeamKey** | **String**| Comma-separated list of team maintainer keys to filter results. | [optional] |
| **expand** | **String**| Options: &#x60;metrics&#x60; | [optional] |

### Return type

[**InsightsChart**](InsightsChart.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Chart response |  -  |

