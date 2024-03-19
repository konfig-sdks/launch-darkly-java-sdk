# UsersBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getAllInUseUserAttributes**](UsersBetaApi.md#getAllInUseUserAttributes) | **GET** /api/v2/user-attributes/{projectKey}/{environmentKey} | Get user attribute names |


<a name="getAllInUseUserAttributes"></a>
# **getAllInUseUserAttributes**
> UserAttributeNamesRep getAllInUseUserAttributes(projectKey, environmentKey).execute();

Get user attribute names

&gt; ### Use contexts instead &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Get context attribute names](https://apidocs.launchdarkly.com) instead of this endpoint.  Get all in-use user attributes in the specified environment. The set of in-use attributes typically consists of all attributes seen within the past 30 days. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.UsersBetaApi;
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
      UserAttributeNamesRep result = client
              .usersBeta
              .getAllInUseUserAttributes(projectKey, environmentKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getPrivate());
      System.out.println(result.getCustom());
      System.out.println(result.getStandard());
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersBetaApi#getAllInUseUserAttributes");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<UserAttributeNamesRep> response = client
              .usersBeta
              .getAllInUseUserAttributes(projectKey, environmentKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersBetaApi#getAllInUseUserAttributes");
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

[**UserAttributeNamesRep**](UserAttributeNamesRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | User attribute names response |  -  |

