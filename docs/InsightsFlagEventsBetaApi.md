# InsightsFlagEventsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**listFlagEvents**](InsightsFlagEventsBetaApi.md#listFlagEvents) | **GET** /api/v2/engineering-insights/flag-events | List flag events |


<a name="listFlagEvents"></a>
# **listFlagEvents**
> FlagEventCollectionRep listFlagEvents(projectKey, environmentKey).applicationKey(applicationKey).query(query).impactSize(impactSize).hasExperiments(hasExperiments).global(global).expand(expand).limit(limit).from(from).to(to).after(after).before(before).execute();

List flag events

Get a list of flag events  ### Expanding the flag event collection response  LaunchDarkly supports expanding the flag event collection response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;experiments&#x60; includes details on all of the experiments run on each flag  For example, use &#x60;?expand&#x3D;experiments&#x60; to include the &#x60;experiments&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsFlagEventsBetaApi;
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
    String query = "query_example"; // Filter events by flag key
    String impactSize = "impactSize_example"; // Filter events by impact size. A small impact created a less than 20% change in the proportion of end users receiving one or more flag variations. A medium impact created between a 20%-80% change. A large impact created a more than 80% change. Options: `none`, `small`, `medium`, `large`
    Boolean hasExperiments = true; // Filter events to those associated with an experiment (`true`) or without an experiment (`false`)
    String global = "global_example"; // Filter to include or exclude global events. Default value is `include`. Options: `include`, `exclude`
    String expand = "expand_example"; // Expand properties in response. Options: `experiments`
    Long limit = 56L; // The number of deployments to return. Default is 20. Maximum allowed is 100.
    Long from = 56L; // Unix timestamp in milliseconds. Default value is 7 days ago.
    Long to = 56L; // Unix timestamp in milliseconds. Default value is now.
    String after = "after_example"; // Identifier used for pagination
    String before = "before_example"; // Identifier used for pagination
    try {
      FlagEventCollectionRep result = client
              .insightsFlagEventsBeta
              .listFlagEvents(projectKey, environmentKey)
              .applicationKey(applicationKey)
              .query(query)
              .impactSize(impactSize)
              .hasExperiments(hasExperiments)
              .global(global)
              .expand(expand)
              .limit(limit)
              .from(from)
              .to(to)
              .after(after)
              .before(before)
              .execute();
      System.out.println(result);
      System.out.println(result.getTotalCount());
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsFlagEventsBetaApi#listFlagEvents");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FlagEventCollectionRep> response = client
              .insightsFlagEventsBeta
              .listFlagEvents(projectKey, environmentKey)
              .applicationKey(applicationKey)
              .query(query)
              .impactSize(impactSize)
              .hasExperiments(hasExperiments)
              .global(global)
              .expand(expand)
              .limit(limit)
              .from(from)
              .to(to)
              .after(after)
              .before(before)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsFlagEventsBetaApi#listFlagEvents");
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
| **query** | **String**| Filter events by flag key | [optional] |
| **impactSize** | **String**| Filter events by impact size. A small impact created a less than 20% change in the proportion of end users receiving one or more flag variations. A medium impact created between a 20%-80% change. A large impact created a more than 80% change. Options: &#x60;none&#x60;, &#x60;small&#x60;, &#x60;medium&#x60;, &#x60;large&#x60; | [optional] |
| **hasExperiments** | **Boolean**| Filter events to those associated with an experiment (&#x60;true&#x60;) or without an experiment (&#x60;false&#x60;) | [optional] |
| **global** | **String**| Filter to include or exclude global events. Default value is &#x60;include&#x60;. Options: &#x60;include&#x60;, &#x60;exclude&#x60; | [optional] |
| **expand** | **String**| Expand properties in response. Options: &#x60;experiments&#x60; | [optional] |
| **limit** | **Long**| The number of deployments to return. Default is 20. Maximum allowed is 100. | [optional] |
| **from** | **Long**| Unix timestamp in milliseconds. Default value is 7 days ago. | [optional] |
| **to** | **Long**| Unix timestamp in milliseconds. Default value is now. | [optional] |
| **after** | **String**| Identifier used for pagination | [optional] |
| **before** | **String**| Identifier used for pagination | [optional] |

### Return type

[**FlagEventCollectionRep**](FlagEventCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag event collection response |  -  |

