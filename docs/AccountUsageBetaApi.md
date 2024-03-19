# AccountUsageBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getEvaluationsUsage**](AccountUsageBetaApi.md#getEvaluationsUsage) | **GET** /api/v2/usage/evaluations/{projectKey}/{environmentKey}/{featureFlagKey} | Get evaluations usage |
| [**getEventsUsageData**](AccountUsageBetaApi.md#getEventsUsageData) | **GET** /api/v2/usage/events/{type} | Get events usage |
| [**getExperimentationKeysUsage**](AccountUsageBetaApi.md#getExperimentationKeysUsage) | **GET** /api/v2/usage/experimentation-keys | Get experimentation keys usage |
| [**getExperimentationUnitsUsage**](AccountUsageBetaApi.md#getExperimentationUnitsUsage) | **GET** /api/v2/usage/experimentation-units | Get experimentation units usage |
| [**getMauUsageByCategory**](AccountUsageBetaApi.md#getMauUsageByCategory) | **GET** /api/v2/usage/mau/bycategory | Get MAU usage by category |
| [**getMauUsageData**](AccountUsageBetaApi.md#getMauUsageData) | **GET** /api/v2/usage/mau | Get MAU usage |
| [**getStreamUsage**](AccountUsageBetaApi.md#getStreamUsage) | **GET** /api/v2/usage/streams/{source} | Get stream usage |
| [**getStreamUsageBySdkVersionData**](AccountUsageBetaApi.md#getStreamUsageBySdkVersionData) | **GET** /api/v2/usage/streams/{source}/bysdkversion | Get stream usage by SDK version |
| [**listMauSdksByType**](AccountUsageBetaApi.md#listMauSdksByType) | **GET** /api/v2/usage/mau/sdks | Get MAU SDKs by type |
| [**listSdkVersions**](AccountUsageBetaApi.md#listSdkVersions) | **GET** /api/v2/usage/streams/{source}/sdkversions | Get stream usage SDK versions |


<a name="getEvaluationsUsage"></a>
# **getEvaluationsUsage**
> SeriesListRep getEvaluationsUsage(projectKey, environmentKey, featureFlagKey).from(from).to(to).tz(tz).execute();

Get evaluations usage

Get time-series arrays of the number of times a flag is evaluated, broken down by the variation that resulted from that evaluation. The granularity of the data depends on the age of the data requested. If the requested range is within the past two hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.AccountUsageBetaApi;
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
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    String from = "from_example"; // The series of data returned starts from this timestamp. Defaults to 30 days ago.
    String to = "to_example"; // The series of data returned ends at this timestamp. Defaults to the current time.
    String tz = "tz_example"; // The timezone to use for breaks between days when returning daily data.
    try {
      SeriesListRep result = client
              .accountUsageBeta
              .getEvaluationsUsage(projectKey, environmentKey, featureFlagKey)
              .from(from)
              .to(to)
              .tz(tz)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getMetadata());
      System.out.println(result.getSeries());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getEvaluationsUsage");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<SeriesListRep> response = client
              .accountUsageBeta
              .getEvaluationsUsage(projectKey, environmentKey, featureFlagKey)
              .from(from)
              .to(to)
              .tz(tz)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getEvaluationsUsage");
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
| **featureFlagKey** | **String**| The feature flag key | |
| **from** | **String**| The series of data returned starts from this timestamp. Defaults to 30 days ago. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional] |
| **tz** | **String**| The timezone to use for breaks between days when returning daily data. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |

<a name="getEventsUsageData"></a>
# **getEventsUsageData**
> SeriesListRep getEventsUsageData(type).from(from).to(to).execute();

Get events usage

Get time-series arrays of the number of times a flag is evaluated, broken down by the variation that resulted from that evaluation. The granularity of the data depends on the age of the data requested. If the requested range is within the past two hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.AccountUsageBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String type = "type_example"; // The type of event to retrieve. Must be either `received` or `published`.
    String from = "from_example"; // The series of data returned starts from this timestamp. Defaults to 24 hours ago.
    String to = "to_example"; // The series of data returned ends at this timestamp. Defaults to the current time.
    try {
      SeriesListRep result = client
              .accountUsageBeta
              .getEventsUsageData(type)
              .from(from)
              .to(to)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getMetadata());
      System.out.println(result.getSeries());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getEventsUsageData");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<SeriesListRep> response = client
              .accountUsageBeta
              .getEventsUsageData(type)
              .from(from)
              .to(to)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getEventsUsageData");
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
| **type** | **String**| The type of event to retrieve. Must be either &#x60;received&#x60; or &#x60;published&#x60;. | |
| **from** | **String**| The series of data returned starts from this timestamp. Defaults to 24 hours ago. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |

<a name="getExperimentationKeysUsage"></a>
# **getExperimentationKeysUsage**
> SeriesIntervalsRep getExperimentationKeysUsage().from(from).to(to).execute();

Get experimentation keys usage

Get a time-series array of the number of monthly experimentation keys from your account. The granularity is always daily, with a maximum of 31 days.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.AccountUsageBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String from = "from_example"; // The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month.
    String to = "to_example"; // The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time.
    try {
      SeriesIntervalsRep result = client
              .accountUsageBeta
              .getExperimentationKeysUsage()
              .from(from)
              .to(to)
              .execute();
      System.out.println(result);
      System.out.println(result.getSeries());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getExperimentationKeysUsage");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<SeriesIntervalsRep> response = client
              .accountUsageBeta
              .getExperimentationKeysUsage()
              .from(from)
              .to(to)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getExperimentationKeysUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. | [optional] |

### Return type

[**SeriesIntervalsRep**](SeriesIntervalsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |

<a name="getExperimentationUnitsUsage"></a>
# **getExperimentationUnitsUsage**
> SeriesIntervalsRep getExperimentationUnitsUsage().from(from).to(to).execute();

Get experimentation units usage

Get a time-series array of the number of monthly experimentation units from your account. The granularity is always daily, with a maximum of 31 days.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.AccountUsageBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String from = "from_example"; // The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month.
    String to = "to_example"; // The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time.
    try {
      SeriesIntervalsRep result = client
              .accountUsageBeta
              .getExperimentationUnitsUsage()
              .from(from)
              .to(to)
              .execute();
      System.out.println(result);
      System.out.println(result.getSeries());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getExperimentationUnitsUsage");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<SeriesIntervalsRep> response = client
              .accountUsageBeta
              .getExperimentationUnitsUsage()
              .from(from)
              .to(to)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getExperimentationUnitsUsage");
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
| **from** | **String**| The series of data returned starts from this timestamp (Unix seconds). Defaults to the beginning of the current month. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp (Unix seconds). Defaults to the current time. | [optional] |

### Return type

[**SeriesIntervalsRep**](SeriesIntervalsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |

<a name="getMauUsageByCategory"></a>
# **getMauUsageByCategory**
> SeriesListRep getMauUsageByCategory().from(from).to(to).execute();

Get MAU usage by category

Get time-series arrays of the number of monthly active users (MAU) seen by LaunchDarkly from your account, broken down by the category of users. The category is either &#x60;browser&#x60;, &#x60;mobile&#x60;, or &#x60;backend&#x60;.&lt;br/&gt;&lt;br/&gt;Endpoints for retrieving monthly active users (MAU) do not return information about active context instances. After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should not rely on this endpoint. To learn more, read [Account usage metrics](https://docs.launchdarkly.com/home/billing/usage-metrics).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.AccountUsageBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String from = "from_example"; // The series of data returned starts from this timestamp. Defaults to 30 days ago.
    String to = "to_example"; // The series of data returned ends at this timestamp. Defaults to the current time.
    try {
      SeriesListRep result = client
              .accountUsageBeta
              .getMauUsageByCategory()
              .from(from)
              .to(to)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getMetadata());
      System.out.println(result.getSeries());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getMauUsageByCategory");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<SeriesListRep> response = client
              .accountUsageBeta
              .getMauUsageByCategory()
              .from(from)
              .to(to)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getMauUsageByCategory");
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
| **from** | **String**| The series of data returned starts from this timestamp. Defaults to 30 days ago. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |

<a name="getMauUsageData"></a>
# **getMauUsageData**
> SeriesListRep getMauUsageData().from(from).to(to).project(project).environment(environment).sdktype(sdktype).sdk(sdk).anonymous(anonymous).groupby(groupby).execute();

Get MAU usage

Get a time-series array of the number of monthly active users (MAU) seen by LaunchDarkly from your account. The granularity is always daily.&lt;br/&gt;&lt;br/&gt;Endpoints for retrieving monthly active users (MAU) do not return information about active context instances. After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should not rely on this endpoint. To learn more, read [Account usage metrics](https://docs.launchdarkly.com/home/billing/usage-metrics).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.AccountUsageBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String from = "from_example"; // The series of data returned starts from this timestamp. Defaults to 30 days ago.
    String to = "to_example"; // The series of data returned ends at this timestamp. Defaults to the current time.
    String project = "project_example"; // A project key to filter results to. Can be specified multiple times, one query parameter per project key, to view data for multiple projects.
    String environment = "environment_example"; // An environment key to filter results to. When using this parameter, exactly one project key must also be set. Can be specified multiple times as separate query parameters to view data for multiple environments within a single project.
    String sdktype = "sdktype_example"; // An SDK type to filter results to. Can be specified multiple times, one query parameter per SDK type. Valid values: client, server
    String sdk = "sdk_example"; // An SDK name to filter results to. Can be specified multiple times, one query parameter per SDK.
    String anonymous = "anonymous_example"; // If specified, filters results to either anonymous or nonanonymous users.
    String groupby = "groupby_example"; // If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions (for example, to group by both project and SDK). Valid values: project, environment, sdktype, sdk, anonymous
    try {
      SeriesListRep result = client
              .accountUsageBeta
              .getMauUsageData()
              .from(from)
              .to(to)
              .project(project)
              .environment(environment)
              .sdktype(sdktype)
              .sdk(sdk)
              .anonymous(anonymous)
              .groupby(groupby)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getMetadata());
      System.out.println(result.getSeries());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getMauUsageData");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<SeriesListRep> response = client
              .accountUsageBeta
              .getMauUsageData()
              .from(from)
              .to(to)
              .project(project)
              .environment(environment)
              .sdktype(sdktype)
              .sdk(sdk)
              .anonymous(anonymous)
              .groupby(groupby)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getMauUsageData");
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
| **from** | **String**| The series of data returned starts from this timestamp. Defaults to 30 days ago. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional] |
| **project** | **String**| A project key to filter results to. Can be specified multiple times, one query parameter per project key, to view data for multiple projects. | [optional] |
| **environment** | **String**| An environment key to filter results to. When using this parameter, exactly one project key must also be set. Can be specified multiple times as separate query parameters to view data for multiple environments within a single project. | [optional] |
| **sdktype** | **String**| An SDK type to filter results to. Can be specified multiple times, one query parameter per SDK type. Valid values: client, server | [optional] |
| **sdk** | **String**| An SDK name to filter results to. Can be specified multiple times, one query parameter per SDK. | [optional] |
| **anonymous** | **String**| If specified, filters results to either anonymous or nonanonymous users. | [optional] |
| **groupby** | **String**| If specified, returns data for each distinct value of the given field. Can be specified multiple times to group data by multiple dimensions (for example, to group by both project and SDK). Valid values: project, environment, sdktype, sdk, anonymous | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |

<a name="getStreamUsage"></a>
# **getStreamUsage**
> SeriesListRep getStreamUsage(source).from(from).to(to).tz(tz).execute();

Get stream usage

Get a time-series array of the number of streaming connections to LaunchDarkly in each time period. The granularity of the data depends on the age of the data requested. If the requested range is within the past two hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.AccountUsageBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String source = "source_example"; // The source of streaming connections to describe. Must be either `client` or `server`.
    String from = "from_example"; // The series of data returned starts from this timestamp. Defaults to 30 days ago.
    String to = "to_example"; // The series of data returned ends at this timestamp. Defaults to the current time.
    String tz = "tz_example"; // The timezone to use for breaks between days when returning daily data.
    try {
      SeriesListRep result = client
              .accountUsageBeta
              .getStreamUsage(source)
              .from(from)
              .to(to)
              .tz(tz)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getMetadata());
      System.out.println(result.getSeries());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getStreamUsage");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<SeriesListRep> response = client
              .accountUsageBeta
              .getStreamUsage(source)
              .from(from)
              .to(to)
              .tz(tz)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getStreamUsage");
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
| **source** | **String**| The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. | |
| **from** | **String**| The series of data returned starts from this timestamp. Defaults to 30 days ago. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional] |
| **tz** | **String**| The timezone to use for breaks between days when returning daily data. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |

<a name="getStreamUsageBySdkVersionData"></a>
# **getStreamUsageBySdkVersionData**
> SeriesListRep getStreamUsageBySdkVersionData(source).from(from).to(to).tz(tz).sdk(sdk).version(version).execute();

Get stream usage by SDK version

Get multiple series of the number of streaming connections to LaunchDarkly in each time period, separated by SDK type and version. Information about each series is in the metadata array. The granularity of the data depends on the age of the data requested. If the requested range is within the past 2 hours, minutely data is returned. If it is within the last two days, hourly data is returned. Otherwise, daily data is returned.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.AccountUsageBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String source = "source_example"; // The source of streaming connections to describe. Must be either `client` or `server`.
    String from = "from_example"; // The series of data returned starts from this timestamp. Defaults to 24 hours ago.
    String to = "to_example"; // The series of data returned ends at this timestamp. Defaults to the current time.
    String tz = "tz_example"; // The timezone to use for breaks between days when returning daily data.
    String sdk = "sdk_example"; // If included, this filters the returned series to only those that match this SDK name.
    String version = "version_example"; // If included, this filters the returned series to only those that match this SDK version.
    try {
      SeriesListRep result = client
              .accountUsageBeta
              .getStreamUsageBySdkVersionData(source)
              .from(from)
              .to(to)
              .tz(tz)
              .sdk(sdk)
              .version(version)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getMetadata());
      System.out.println(result.getSeries());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getStreamUsageBySdkVersionData");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<SeriesListRep> response = client
              .accountUsageBeta
              .getStreamUsageBySdkVersionData(source)
              .from(from)
              .to(to)
              .tz(tz)
              .sdk(sdk)
              .version(version)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#getStreamUsageBySdkVersionData");
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
| **source** | **String**| The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. | |
| **from** | **String**| The series of data returned starts from this timestamp. Defaults to 24 hours ago. | [optional] |
| **to** | **String**| The series of data returned ends at this timestamp. Defaults to the current time. | [optional] |
| **tz** | **String**| The timezone to use for breaks between days when returning daily data. | [optional] |
| **sdk** | **String**| If included, this filters the returned series to only those that match this SDK name. | [optional] |
| **version** | **String**| If included, this filters the returned series to only those that match this SDK version. | [optional] |

### Return type

[**SeriesListRep**](SeriesListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Usage response |  -  |

<a name="listMauSdksByType"></a>
# **listMauSdksByType**
> SdkListRep listMauSdksByType().from(from).to(to).sdktype(sdktype).execute();

Get MAU SDKs by type

Get a list of SDKs. These are all of the SDKs that have connected to LaunchDarkly by monthly active users (MAU) in the requested time period.&lt;br/&gt;&lt;br/&gt;Endpoints for retrieving monthly active users (MAU) do not return information about active context instances. After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should not rely on this endpoint. To learn more, read [Account usage metrics](https://docs.launchdarkly.com/home/billing/usage-metrics).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.AccountUsageBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String from = "from_example"; // The data returned starts from this timestamp. Defaults to seven days ago. The timestamp is in Unix milliseconds, for example, 1656694800000.
    String to = "to_example"; // The data returned ends at this timestamp. Defaults to the current time. The timestamp is in Unix milliseconds, for example, 1657904400000.
    String sdktype = "sdktype_example"; // The type of SDK with monthly active users (MAU) to list. Must be either `client` or `server`.
    try {
      SdkListRep result = client
              .accountUsageBeta
              .listMauSdksByType()
              .from(from)
              .to(to)
              .sdktype(sdktype)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getSdks());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#listMauSdksByType");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<SdkListRep> response = client
              .accountUsageBeta
              .listMauSdksByType()
              .from(from)
              .to(to)
              .sdktype(sdktype)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#listMauSdksByType");
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
| **from** | **String**| The data returned starts from this timestamp. Defaults to seven days ago. The timestamp is in Unix milliseconds, for example, 1656694800000. | [optional] |
| **to** | **String**| The data returned ends at this timestamp. Defaults to the current time. The timestamp is in Unix milliseconds, for example, 1657904400000. | [optional] |
| **sdktype** | **String**| The type of SDK with monthly active users (MAU) to list. Must be either &#x60;client&#x60; or &#x60;server&#x60;. | [optional] |

### Return type

[**SdkListRep**](SdkListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | MAU SDKs response |  -  |

<a name="listSdkVersions"></a>
# **listSdkVersions**
> SdkVersionListRep listSdkVersions(source).execute();

Get stream usage SDK versions

Get a list of SDK version objects, which contain an SDK name and version. These are all of the SDKs that have connected to LaunchDarkly from your account in the past 60 days.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.AccountUsageBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String source = "source_example"; // The source of streaming connections to describe. Must be either `client` or `server`.
    try {
      SdkVersionListRep result = client
              .accountUsageBeta
              .listSdkVersions(source)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getSdkVersions());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#listSdkVersions");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<SdkVersionListRep> response = client
              .accountUsageBeta
              .listSdkVersions(source)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountUsageBetaApi#listSdkVersions");
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
| **source** | **String**| The source of streaming connections to describe. Must be either &#x60;client&#x60; or &#x60;server&#x60;. | |

### Return type

[**SdkVersionListRep**](SdkVersionListRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | SDK Versions response |  -  |

