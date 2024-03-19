# TagsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**list**](TagsApi.md#list) | **GET** /api/v2/tags | List tags |


<a name="list"></a>
# **list**
> TagCollection list().kind(kind).pre(pre).archived(archived).execute();

List tags

Get a list of tags.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.TagsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String kind = "kind_example"; // Fetch tags associated with the specified resource type. Options are `flag`, `project`, `environment`, `segment`. Returns all types by default.
    String pre = "pre_example"; // Return tags with the specified prefix
    Boolean archived = true; // Whether or not to return archived flags
    try {
      TagCollection result = client
              .tags
              .list()
              .kind(kind)
              .pre(pre)
              .archived(archived)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
      System.out.println(result.getTotalCount());
    } catch (ApiException e) {
      System.err.println("Exception when calling TagsApi#list");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<TagCollection> response = client
              .tags
              .list()
              .kind(kind)
              .pre(pre)
              .archived(archived)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling TagsApi#list");
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
| **kind** | **String**| Fetch tags associated with the specified resource type. Options are &#x60;flag&#x60;, &#x60;project&#x60;, &#x60;environment&#x60;, &#x60;segment&#x60;. Returns all types by default. | [optional] |
| **pre** | **String**| Return tags with the specified prefix | [optional] |
| **archived** | **Boolean**| Whether or not to return archived flags | [optional] |

### Return type

[**TagCollection**](TagCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Tag collection response |  -  |

