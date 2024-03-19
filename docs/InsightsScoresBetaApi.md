# InsightsScoresBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createInsightGroup**](InsightsScoresBetaApi.md#createInsightGroup) | **POST** /api/v2/engineering-insights/insights/group | Create insight group |
| [**deleteInsightGroup**](InsightsScoresBetaApi.md#deleteInsightGroup) | **DELETE** /api/v2/engineering-insights/insights/groups/{insightGroupKey} | Delete insight group |
| [**expandGroupInsightScores**](InsightsScoresBetaApi.md#expandGroupInsightScores) | **GET** /api/v2/engineering-insights/insights/groups/{insightGroupKey} | Get insight group |
| [**getInsightScores**](InsightsScoresBetaApi.md#getInsightScores) | **GET** /api/v2/engineering-insights/insights/scores | Get insight scores |
| [**listGroupInsightScores**](InsightsScoresBetaApi.md#listGroupInsightScores) | **GET** /api/v2/engineering-insights/insights/groups | List insight groups |
| [**updateInsightGroupPatch**](InsightsScoresBetaApi.md#updateInsightGroupPatch) | **PATCH** /api/v2/engineering-insights/insights/groups/{insightGroupKey} | Patch insight group |


<a name="createInsightGroup"></a>
# **createInsightGroup**
> InsightGroup createInsightGroup(postInsightGroupParams).execute();

Create insight group

Create insight group

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsScoresBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String name = "name_example"; // The name of the insight group
    String key = "key_example"; // The key of the insight group
    String projectKey = "projectKey_example"; // The projectKey to be associated with the insight group
    String environmentKey = "environmentKey_example"; // The environmentKey to be associated with the insight group
    List<String> applicationKeys = Arrays.asList(); // The application keys to associate with the insight group. If not provided, the insight group will include data from all applications.
    try {
      InsightGroup result = client
              .insightsScoresBeta
              .createInsightGroup(name, key, projectKey, environmentKey)
              .applicationKeys(applicationKeys)
              .execute();
      System.out.println(result);
      System.out.println(result.getEnvironment());
      System.out.println(result.getScores());
      System.out.println(result.getScoreMetadata());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getProjectKey());
      System.out.println(result.getEnvironmentKey());
      System.out.println(result.getApplicationKeys());
      System.out.println(result.getCreatedAt());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#createInsightGroup");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<InsightGroup> response = client
              .insightsScoresBeta
              .createInsightGroup(name, key, projectKey, environmentKey)
              .applicationKeys(applicationKeys)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#createInsightGroup");
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
| **postInsightGroupParams** | [**PostInsightGroupParams**](PostInsightGroupParams.md)|  | |

### Return type

[**InsightGroup**](InsightGroup.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |

<a name="deleteInsightGroup"></a>
# **deleteInsightGroup**
> deleteInsightGroup(insightGroupKey).execute();

Delete insight group

Delete insight group

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsScoresBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String insightGroupKey = "insightGroupKey_example"; // The insight group key
    try {
      client
              .insightsScoresBeta
              .deleteInsightGroup(insightGroupKey)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#deleteInsightGroup");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .insightsScoresBeta
              .deleteInsightGroup(insightGroupKey)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#deleteInsightGroup");
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
| **insightGroupKey** | **String**| The insight group key | |

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

<a name="expandGroupInsightScores"></a>
# **expandGroupInsightScores**
> InsightGroup expandGroupInsightScores(insightGroupKey).expand(expand).execute();

Get insight group

Get insight group  ### Expanding the insight group response  LaunchDarkly supports expanding the insight group response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;scores&#x60; includes details on all of the scores used in the engineering insights metrics views for this group * &#x60;environment&#x60; includes details on each environment associated with this group  For example, use &#x60;?expand&#x3D;scores&#x60; to include the &#x60;scores&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsScoresBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String insightGroupKey = "insightGroupKey_example"; // The insight group key
    String expand = "expand_example"; // Options: `scores`, `environment`
    try {
      InsightGroup result = client
              .insightsScoresBeta
              .expandGroupInsightScores(insightGroupKey)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getEnvironment());
      System.out.println(result.getScores());
      System.out.println(result.getScoreMetadata());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getProjectKey());
      System.out.println(result.getEnvironmentKey());
      System.out.println(result.getApplicationKeys());
      System.out.println(result.getCreatedAt());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#expandGroupInsightScores");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<InsightGroup> response = client
              .insightsScoresBeta
              .expandGroupInsightScores(insightGroupKey)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#expandGroupInsightScores");
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
| **insightGroupKey** | **String**| The insight group key | |
| **expand** | **String**| Options: &#x60;scores&#x60;, &#x60;environment&#x60; | [optional] |

### Return type

[**InsightGroup**](InsightGroup.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Insight group response |  -  |

<a name="getInsightScores"></a>
# **getInsightScores**
> InsightScores getInsightScores(projectKey, environmentKey).applicationKey(applicationKey).execute();

Get insight scores

Return insights scores, based on the given parameters. This data is also used in engineering insights metrics views.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsScoresBetaApi;
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
      InsightScores result = client
              .insightsScoresBeta
              .getInsightScores(projectKey, environmentKey)
              .applicationKey(applicationKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getPeriod());
      System.out.println(result.getLastPeriod());
      System.out.println(result.getScores());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#getInsightScores");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<InsightScores> response = client
              .insightsScoresBeta
              .getInsightScores(projectKey, environmentKey)
              .applicationKey(applicationKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#getInsightScores");
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

[**InsightScores**](InsightScores.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Insight score response |  -  |

<a name="listGroupInsightScores"></a>
# **listGroupInsightScores**
> InsightGroupCollection listGroupInsightScores().limit(limit).offset(offset).sort(sort).query(query).expand(expand).execute();

List insight groups

List groups for which you are collecting insights  ### Expanding the insight groups collection response  LaunchDarkly supports expanding the insight groups collection response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;scores&#x60; includes details on all of the scores used in the engineering insights metrics views for each group * &#x60;environment&#x60; includes details on each environment associated with each group * &#x60;metadata&#x60; includes counts of the number of insight groups with particular indicators, such as \&quot;execellent,\&quot; \&quot;good,\&quot; \&quot;fair,\&quot; and so on.  For example, use &#x60;?expand&#x3D;scores&#x60; to include the &#x60;scores&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsScoresBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    Long limit = 56L; // The number of insight groups to return. Default is 20. Must be between 1 and 20 inclusive.
    Long offset = 56L; // Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String sort = "sort_example"; // Sort flag list by field. Prefix field with <code>-</code> to sort in descending order. Allowed fields: name
    String query = "query_example"; // Filter list of insights groups by name.
    String expand = "expand_example"; // Options: `scores`, `environment`, `metadata`
    try {
      InsightGroupCollection result = client
              .insightsScoresBeta
              .listGroupInsightScores()
              .limit(limit)
              .offset(offset)
              .sort(sort)
              .query(query)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getTotalCount());
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
      System.out.println(result.getMetadata());
      System.out.println(result.getScoreMetadata());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#listGroupInsightScores");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<InsightGroupCollection> response = client
              .insightsScoresBeta
              .listGroupInsightScores()
              .limit(limit)
              .offset(offset)
              .sort(sort)
              .query(query)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#listGroupInsightScores");
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
| **limit** | **Long**| The number of insight groups to return. Default is 20. Must be between 1 and 20 inclusive. | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **sort** | **String**| Sort flag list by field. Prefix field with &lt;code&gt;-&lt;/code&gt; to sort in descending order. Allowed fields: name | [optional] |
| **query** | **String**| Filter list of insights groups by name. | [optional] |
| **expand** | **String**| Options: &#x60;scores&#x60;, &#x60;environment&#x60;, &#x60;metadata&#x60; | [optional] |

### Return type

[**InsightGroupCollection**](InsightGroupCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Insight groups collection response |  -  |

<a name="updateInsightGroupPatch"></a>
# **updateInsightGroupPatch**
> InsightGroup updateInsightGroupPatch(insightGroupKey, patchOperation).execute();

Patch insight group

Update an insight group. Updating an insight group uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsScoresBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String insightGroupKey = "insightGroupKey_example"; // The insight group key
    try {
      InsightGroup result = client
              .insightsScoresBeta
              .updateInsightGroupPatch(insightGroupKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getEnvironment());
      System.out.println(result.getScores());
      System.out.println(result.getScoreMetadata());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getProjectKey());
      System.out.println(result.getEnvironmentKey());
      System.out.println(result.getApplicationKeys());
      System.out.println(result.getCreatedAt());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#updateInsightGroupPatch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<InsightGroup> response = client
              .insightsScoresBeta
              .updateInsightGroupPatch(insightGroupKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsScoresBetaApi#updateInsightGroupPatch");
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
| **insightGroupKey** | **String**| The insight group key | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**InsightGroup**](InsightGroup.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Insight group response |  -  |

