# FollowFlagsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**flagFollowersList**](FollowFlagsApi.md#flagFollowersList) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/followers | Get followers of a flag in a project and environment |
| [**getAllFlagFollowers**](FollowFlagsApi.md#getAllFlagFollowers) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/followers | Get followers of all flags in a given project and environment |
| [**memberFollower**](FollowFlagsApi.md#memberFollower) | **PUT** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/followers/{memberId} | Add a member as a follower of a flag in a project and environment |
| [**removeFollower**](FollowFlagsApi.md#removeFollower) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/followers/{memberId} | Remove a member as a follower of a flag in a project and environment |


<a name="flagFollowersList"></a>
# **flagFollowersList**
> FlagFollowersGetRep flagFollowersList(projectKey, featureFlagKey, environmentKey).execute();

Get followers of a flag in a project and environment

Get a list of members following a flag in a project and environment

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FollowFlagsApi;
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
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    String environmentKey = "environmentKey_example"; // The environment key
    try {
      FlagFollowersGetRep result = client
              .followFlags
              .flagFollowersList(projectKey, featureFlagKey, environmentKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling FollowFlagsApi#flagFollowersList");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FlagFollowersGetRep> response = client
              .followFlags
              .flagFollowersList(projectKey, featureFlagKey, environmentKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FollowFlagsApi#flagFollowersList");
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
| **featureFlagKey** | **String**| The feature flag key | |
| **environmentKey** | **String**| The environment key | |

### Return type

[**FlagFollowersGetRep**](FlagFollowersGetRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag followers response |  -  |

<a name="getAllFlagFollowers"></a>
# **getAllFlagFollowers**
> FlagFollowersByProjEnvGetRep getAllFlagFollowers(projectKey, environmentKey).execute();

Get followers of all flags in a given project and environment

Get followers of all flags in a given environment and project

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FollowFlagsApi;
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
      FlagFollowersByProjEnvGetRep result = client
              .followFlags
              .getAllFlagFollowers(projectKey, environmentKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling FollowFlagsApi#getAllFlagFollowers");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FlagFollowersByProjEnvGetRep> response = client
              .followFlags
              .getAllFlagFollowers(projectKey, environmentKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FollowFlagsApi#getAllFlagFollowers");
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

[**FlagFollowersByProjEnvGetRep**](FlagFollowersByProjEnvGetRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flags and flag followers response |  -  |

<a name="memberFollower"></a>
# **memberFollower**
> memberFollower(projectKey, featureFlagKey, environmentKey, memberId).execute();

Add a member as a follower of a flag in a project and environment

Add a member as a follower to a flag in a project and environment

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FollowFlagsApi;
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
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    String environmentKey = "environmentKey_example"; // The environment key
    String memberId = "memberId_example"; // The memberId of the member to add as a follower of the flag. Reader roles can only add themselves.
    try {
      client
              .followFlags
              .memberFollower(projectKey, featureFlagKey, environmentKey, memberId)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling FollowFlagsApi#memberFollower");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .followFlags
              .memberFollower(projectKey, featureFlagKey, environmentKey, memberId)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling FollowFlagsApi#memberFollower");
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
| **featureFlagKey** | **String**| The feature flag key | |
| **environmentKey** | **String**| The environment key | |
| **memberId** | **String**| The memberId of the member to add as a follower of the flag. Reader roles can only add themselves. | |

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

<a name="removeFollower"></a>
# **removeFollower**
> removeFollower(projectKey, featureFlagKey, environmentKey, memberId).execute();

Remove a member as a follower of a flag in a project and environment

Remove a member as a follower to a flag in a project and environment

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FollowFlagsApi;
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
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    String environmentKey = "environmentKey_example"; // The environment key
    String memberId = "memberId_example"; // The memberId of the member to remove as a follower of the flag. Reader roles can only remove themselves.
    try {
      client
              .followFlags
              .removeFollower(projectKey, featureFlagKey, environmentKey, memberId)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling FollowFlagsApi#removeFollower");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .followFlags
              .removeFollower(projectKey, featureFlagKey, environmentKey, memberId)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling FollowFlagsApi#removeFollower");
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
| **featureFlagKey** | **String**| The feature flag key | |
| **environmentKey** | **String**| The environment key | |
| **memberId** | **String**| The memberId of the member to remove as a follower of the flag. Reader roles can only remove themselves. | |

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

