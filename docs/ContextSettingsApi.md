# ContextSettingsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**updateSettingsForContext**](ContextSettingsApi.md#updateSettingsForContext) | **PUT** /api/v2/projects/{projectKey}/environments/{environmentKey}/contexts/{contextKind}/{contextKey}/flags/{featureFlagKey} | Update flag settings for context |


<a name="updateSettingsForContext"></a>
# **updateSettingsForContext**
> updateSettingsForContext(projectKey, environmentKey, contextKind, contextKey, featureFlagKey, valuePut).execute();

Update flag settings for context

 Enable or disable a feature flag for a context based on its context kind and key.  Omitting the &#x60;setting&#x60; attribute from the request body, or including a &#x60;setting&#x60; of &#x60;null&#x60;, erases the current setting for a context.  If you previously patched the flag, and the patch included the context&#39;s data, LaunchDarkly continues to use that data. If LaunchDarkly has never encountered the combination of the context&#39;s key and kind before, it calculates the flag values based on the context kind and key. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ContextSettingsApi;
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
    String contextKind = "contextKind_example"; // The context kind
    String contextKey = "contextKey_example"; // The context key
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    Object setting = null; // The variation value to set for the context. Must match the flag's variation type.
    String comment = "comment_example"; // Optional comment describing the change
    try {
      client
              .contextSettings
              .updateSettingsForContext(projectKey, environmentKey, contextKind, contextKey, featureFlagKey)
              .setting(setting)
              .comment(comment)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextSettingsApi#updateSettingsForContext");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .contextSettings
              .updateSettingsForContext(projectKey, environmentKey, contextKind, contextKey, featureFlagKey)
              .setting(setting)
              .comment(comment)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextSettingsApi#updateSettingsForContext");
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
| **contextKind** | **String**| The context kind | |
| **contextKey** | **String**| The context key | |
| **featureFlagKey** | **String**| The feature flag key | |
| **valuePut** | [**ValuePut**](ValuePut.md)|  | |

### Return type

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Action succeeded |  -  |

