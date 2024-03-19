# ReleasesBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getCurrentRelease**](ReleasesBetaApi.md#getCurrentRelease) | **GET** /api/v2/flags/{projectKey}/{flagKey}/release | Get release for flag |
| [**updateActiveReleasePatch**](ReleasesBetaApi.md#updateActiveReleasePatch) | **PATCH** /api/v2/flags/{projectKey}/{flagKey}/release | Patch release for flag |


<a name="getCurrentRelease"></a>
# **getCurrentRelease**
> Release getCurrentRelease(projectKey, flagKey).execute();

Get release for flag

Get currently active release for a flag

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ReleasesBetaApi;
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
    String flagKey = "flagKey_example"; // The flag key
    try {
      Release result = client
              .releasesBeta
              .getCurrentRelease(projectKey, flagKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getName());
      System.out.println(result.getReleasePipelineKey());
      System.out.println(result.getReleasePipelineDescription());
      System.out.println(result.getPhases());
      System.out.println(result.getVersion());
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasesBetaApi#getCurrentRelease");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Release> response = client
              .releasesBeta
              .getCurrentRelease(projectKey, flagKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasesBetaApi#getCurrentRelease");
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
| **flagKey** | **String**| The flag key | |

### Return type

[**Release**](Release.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Release response |  -  |

<a name="updateActiveReleasePatch"></a>
# **updateActiveReleasePatch**
> Release updateActiveReleasePatch(projectKey, flagKey, patchOperation).execute();

Patch release for flag

Update currently active release for a flag. Updating releases requires the [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) format. To learn more, read [Updates](https://apidocs.launchdarkly.com).  You can only use this endpoint to mark a release phase complete or incomplete. To indicate which phase to update, use the array index in the &#x60;path&#x60;. For example, to mark the first phase of a release as complete, use the following request body:  &#x60;&#x60;&#x60;   [     {       \&quot;op\&quot;: \&quot;replace\&quot;,       \&quot;path\&quot;: \&quot;/phase/0/complete\&quot;,       \&quot;value\&quot;: true     }   ] &#x60;&#x60;&#x60; 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ReleasesBetaApi;
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
    String flagKey = "flagKey_example"; // The flag key
    try {
      Release result = client
              .releasesBeta
              .updateActiveReleasePatch(projectKey, flagKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getName());
      System.out.println(result.getReleasePipelineKey());
      System.out.println(result.getReleasePipelineDescription());
      System.out.println(result.getPhases());
      System.out.println(result.getVersion());
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasesBetaApi#updateActiveReleasePatch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Release> response = client
              .releasesBeta
              .updateActiveReleasePatch(projectKey, flagKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasesBetaApi#updateActiveReleasePatch");
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
| **flagKey** | **String**| The flag key | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**Release**](Release.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Release response |  -  |

