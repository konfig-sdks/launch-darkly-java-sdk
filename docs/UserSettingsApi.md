# UserSettingsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getUserExpiringFlagTargets**](UserSettingsApi.md#getUserExpiringFlagTargets) | **GET** /api/v2/users/{projectKey}/{userKey}/expiring-user-targets/{environmentKey} | Get expiring dates on flags for user |
| [**listFlagSettingsForUser**](UserSettingsApi.md#listFlagSettingsForUser) | **GET** /api/v2/users/{projectKey}/{environmentKey}/{userKey}/flags | List flag settings for user |
| [**singleFlagSetting**](UserSettingsApi.md#singleFlagSetting) | **GET** /api/v2/users/{projectKey}/{environmentKey}/{userKey}/flags/{featureFlagKey} | Get flag setting for user |
| [**updateExpiringUserTarget**](UserSettingsApi.md#updateExpiringUserTarget) | **PATCH** /api/v2/users/{projectKey}/{userKey}/expiring-user-targets/{environmentKey} | Update expiring user target for flags |
| [**updateFlagSettingsForUser**](UserSettingsApi.md#updateFlagSettingsForUser) | **PUT** /api/v2/users/{projectKey}/{environmentKey}/{userKey}/flags/{featureFlagKey} | Update flag settings for user |


<a name="getUserExpiringFlagTargets"></a>
# **getUserExpiringFlagTargets**
> ExpiringUserTargetGetResponse getUserExpiringFlagTargets(projectKey, userKey, environmentKey).execute();

Get expiring dates on flags for user

Get a list of flags for which the given user is scheduled for removal.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.UserSettingsApi;
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
    String userKey = "userKey_example"; // The user key
    String environmentKey = "environmentKey_example"; // The environment key
    try {
      ExpiringUserTargetGetResponse result = client
              .userSettings
              .getUserExpiringFlagTargets(projectKey, userKey, environmentKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling UserSettingsApi#getUserExpiringFlagTargets");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ExpiringUserTargetGetResponse> response = client
              .userSettings
              .getUserExpiringFlagTargets(projectKey, userKey, environmentKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling UserSettingsApi#getUserExpiringFlagTargets");
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
| **userKey** | **String**| The user key | |
| **environmentKey** | **String**| The environment key | |

### Return type

[**ExpiringUserTargetGetResponse**](ExpiringUserTargetGetResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Expiring user target response |  -  |

<a name="listFlagSettingsForUser"></a>
# **listFlagSettingsForUser**
> UserFlagSettings listFlagSettingsForUser(projectKey, environmentKey, userKey).execute();

List flag settings for user

Get the current flag settings for a given user. &lt;br /&gt;&lt;br /&gt;The &#x60;_value&#x60; is the flag variation that the user receives. The &#x60;setting&#x60; indicates whether you&#39;ve explicitly targeted a user to receive a particular variation. For example, if you have turned off a feature flag for a user, this setting will be &#x60;false&#x60;. The example response indicates that the user &#x60;Abbie_Braun&#x60; has the &#x60;sort.order&#x60; flag enabled and the &#x60;alternate.page&#x60; flag disabled, and that the user has not been explicitly targeted to receive a particular variation.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.UserSettingsApi;
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
    String userKey = "userKey_example"; // The user key
    try {
      UserFlagSettings result = client
              .userSettings
              .listFlagSettingsForUser(projectKey, environmentKey, userKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling UserSettingsApi#listFlagSettingsForUser");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<UserFlagSettings> response = client
              .userSettings
              .listFlagSettingsForUser(projectKey, environmentKey, userKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling UserSettingsApi#listFlagSettingsForUser");
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
| **userKey** | **String**| The user key | |

### Return type

[**UserFlagSettings**](UserFlagSettings.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | User flag settings collection response |  -  |

<a name="singleFlagSetting"></a>
# **singleFlagSetting**
> UserFlagSetting singleFlagSetting(projectKey, environmentKey, userKey, featureFlagKey).execute();

Get flag setting for user

Get a single flag setting for a user by flag key. &lt;br /&gt;&lt;br /&gt;The &#x60;_value&#x60; is the flag variation that the user receives. The &#x60;setting&#x60; indicates whether you&#39;ve explicitly targeted a user to receive a particular variation. For example, if you have turned off a feature flag for a user, this setting will be &#x60;false&#x60;. The example response indicates that the user &#x60;Abbie_Braun&#x60; has the &#x60;sort.order&#x60; flag enabled.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.UserSettingsApi;
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
    String userKey = "userKey_example"; // The user key
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    try {
      UserFlagSetting result = client
              .userSettings
              .singleFlagSetting(projectKey, environmentKey, userKey, featureFlagKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getValue());
      System.out.println(result.getSetting());
      System.out.println(result.getReason());
    } catch (ApiException e) {
      System.err.println("Exception when calling UserSettingsApi#singleFlagSetting");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<UserFlagSetting> response = client
              .userSettings
              .singleFlagSetting(projectKey, environmentKey, userKey, featureFlagKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling UserSettingsApi#singleFlagSetting");
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
| **userKey** | **String**| The user key | |
| **featureFlagKey** | **String**| The feature flag key | |

### Return type

[**UserFlagSetting**](UserFlagSetting.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | User flag settings response |  -  |

<a name="updateExpiringUserTarget"></a>
# **updateExpiringUserTarget**
> ExpiringUserTargetPatchResponse updateExpiringUserTarget(projectKey, userKey, environmentKey, patchUsersRequest).execute();

Update expiring user target for flags

Schedule the specified user for removal from individual targeting on one or more flags. The user must already be individually targeted for each flag.  You can add, update, or remove a scheduled removal date. You can only schedule a user for removal on a single variation per flag.  Updating an expiring target uses the semantic patch format. To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://apidocs.launchdarkly.com).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating expiring user targets.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating expiring user targets&lt;/strong&gt;&lt;/summary&gt;  #### addExpireUserTargetDate  Adds a date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;flagKey&#x60;: The flag key * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag.  #### updateExpireUserTargetDate  Updates the date and time that LaunchDarkly will remove the user from the flag&#39;s individual targeting.  ##### Parameters  * &#x60;flagKey&#x60;: The flag key * &#x60;variationId&#x60;: ID of a variation on the flag * &#x60;value&#x60;: The time, in Unix milliseconds, when LaunchDarkly should remove the user from individual targeting for this flag. * &#x60;version&#x60;: The version of the expiring user target to update. If included, update will fail if version doesn&#39;t match current version of the expiring user target.  #### removeExpireUserTargetDate  Removes the scheduled removal of the user from the flag&#39;s individual targeting. The user will remain part of the flag&#39;s individual targeting until explicitly removed, or until another removal is scheduled.  ##### Parameters  * &#x60;flagKey&#x60;: The flag key * &#x60;variationId&#x60;: ID of a variation on the flag  &lt;/details&gt; 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.UserSettingsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    List<InstructionUserRequest> instructions = Arrays.asList(); // The instructions to perform when updating
    String projectKey = "projectKey_example"; // The project key
    String userKey = "userKey_example"; // The user key
    String environmentKey = "environmentKey_example"; // The environment key
    String comment = "comment_example"; // Optional comment describing the change
    try {
      ExpiringUserTargetPatchResponse result = client
              .userSettings
              .updateExpiringUserTarget(instructions, projectKey, userKey, environmentKey)
              .comment(comment)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
      System.out.println(result.getTotalInstructions());
      System.out.println(result.getSuccessfulInstructions());
      System.out.println(result.getFailedInstructions());
      System.out.println(result.getErrors());
    } catch (ApiException e) {
      System.err.println("Exception when calling UserSettingsApi#updateExpiringUserTarget");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ExpiringUserTargetPatchResponse> response = client
              .userSettings
              .updateExpiringUserTarget(instructions, projectKey, userKey, environmentKey)
              .comment(comment)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling UserSettingsApi#updateExpiringUserTarget");
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
| **userKey** | **String**| The user key | |
| **environmentKey** | **String**| The environment key | |
| **patchUsersRequest** | [**PatchUsersRequest**](PatchUsersRequest.md)|  | |

### Return type

[**ExpiringUserTargetPatchResponse**](ExpiringUserTargetPatchResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Expiring user target response |  -  |

<a name="updateFlagSettingsForUser"></a>
# **updateFlagSettingsForUser**
> updateFlagSettingsForUser(projectKey, environmentKey, userKey, featureFlagKey, valuePut).execute();

Update flag settings for user

Enable or disable a feature flag for a user based on their key.  Omitting the &#x60;setting&#x60; attribute from the request body, or including a &#x60;setting&#x60; of &#x60;null&#x60;, erases the current setting for a user.  If you previously patched the flag, and the patch included the user&#39;s data, LaunchDarkly continues to use that data. If LaunchDarkly has never encountered the user&#39;s key before, it calculates the flag values based on the user key alone. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.UserSettingsApi;
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
    String userKey = "userKey_example"; // The user key
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    Object setting = null; // The variation value to set for the context. Must match the flag's variation type.
    String comment = "comment_example"; // Optional comment describing the change
    try {
      client
              .userSettings
              .updateFlagSettingsForUser(projectKey, environmentKey, userKey, featureFlagKey)
              .setting(setting)
              .comment(comment)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling UserSettingsApi#updateFlagSettingsForUser");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .userSettings
              .updateFlagSettingsForUser(projectKey, environmentKey, userKey, featureFlagKey)
              .setting(setting)
              .comment(comment)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling UserSettingsApi#updateFlagSettingsForUser");
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
| **userKey** | **String**| The user key | |
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

