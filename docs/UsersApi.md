# UsersApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteByProjectEnvironmentKey**](UsersApi.md#deleteByProjectEnvironmentKey) | **DELETE** /api/v2/users/{projectKey}/{environmentKey}/{userKey} | Delete user |
| [**getUserByKey**](UsersApi.md#getUserByKey) | **GET** /api/v2/users/{projectKey}/{environmentKey}/{userKey} | Get user |
| [**listEnvironmentUsers**](UsersApi.md#listEnvironmentUsers) | **GET** /api/v2/users/{projectKey}/{environmentKey} | List users |
| [**searchUsers**](UsersApi.md#searchUsers) | **GET** /api/v2/user-search/{projectKey}/{environmentKey} | Find users |


<a name="deleteByProjectEnvironmentKey"></a>
# **deleteByProjectEnvironmentKey**
> deleteByProjectEnvironmentKey(projectKey, environmentKey, userKey).execute();

Delete user

&gt; ### Use contexts instead &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Delete context instances](https://apidocs.launchdarkly.com) instead of this endpoint.  Delete a user by key. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.UsersApi;
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
      client
              .users
              .deleteByProjectEnvironmentKey(projectKey, environmentKey, userKey)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#deleteByProjectEnvironmentKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .users
              .deleteByProjectEnvironmentKey(projectKey, environmentKey, userKey)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#deleteByProjectEnvironmentKey");
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

<a name="getUserByKey"></a>
# **getUserByKey**
> UserRecord getUserByKey(projectKey, environmentKey, userKey).execute();

Get user

&gt; ### Use contexts instead &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Get context instances](https://apidocs.launchdarkly.com) instead of this endpoint.  Get a user by key. The &#x60;user&#x60; object contains all attributes sent in &#x60;variation&#x60; calls for that key. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.UsersApi;
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
      UserRecord result = client
              .users
              .getUserByKey(projectKey, environmentKey, userKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getLastPing());
      System.out.println(result.getEnvironmentId());
      System.out.println(result.getOwnerId());
      System.out.println(result.getUser());
      System.out.println(result.getSortValue());
      System.out.println(result.getLinks());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#getUserByKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<UserRecord> response = client
              .users
              .getUserByKey(projectKey, environmentKey, userKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#getUserByKey");
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

[**UserRecord**](UserRecord.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | User response |  -  |

<a name="listEnvironmentUsers"></a>
# **listEnvironmentUsers**
> UsersRep listEnvironmentUsers(projectKey, environmentKey).limit(limit).searchAfter(searchAfter).execute();

List users

&gt; ### Use contexts instead &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Search for contexts](https://apidocs.launchdarkly.com) instead of this endpoint.  List all users in the environment. Includes the total count of users. This is useful for exporting all users in the system for further analysis.  Each page displays users up to a set &#x60;limit&#x60;. The default is 20. To page through, follow the &#x60;next&#x60; link in the &#x60;_links&#x60; object. To learn more, read [Representations](https://apidocs.launchdarkly.com). 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.UsersApi;
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
    Long limit = 56L; // The number of elements to return per page
    String searchAfter = "searchAfter_example"; // Limits results to users with sort values after the value you specify. You can use this for pagination, but we recommend using the `next` link we provide instead.
    try {
      UsersRep result = client
              .users
              .listEnvironmentUsers(projectKey, environmentKey)
              .limit(limit)
              .searchAfter(searchAfter)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getTotalCount());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#listEnvironmentUsers");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<UsersRep> response = client
              .users
              .listEnvironmentUsers(projectKey, environmentKey)
              .limit(limit)
              .searchAfter(searchAfter)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#listEnvironmentUsers");
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
| **limit** | **Long**| The number of elements to return per page | [optional] |
| **searchAfter** | **String**| Limits results to users with sort values after the value you specify. You can use this for pagination, but we recommend using the &#x60;next&#x60; link we provide instead. | [optional] |

### Return type

[**UsersRep**](UsersRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Users collection response |  -  |

<a name="searchUsers"></a>
# **searchUsers**
> Users searchUsers(projectKey, environmentKey).q(q).limit(limit).offset(offset).after(after).sort(sort).searchAfter(searchAfter).filter(filter).execute();

Find users

&gt; ### Use contexts instead &gt; &gt; After you have upgraded your LaunchDarkly SDK to use contexts instead of users, you should use [Search for context instances](https://apidocs.launchdarkly.com) instead of this endpoint.  Search users in LaunchDarkly based on their last active date, a user attribute filter set, or a search query.  An example user attribute filter set is &#x60;filter&#x3D;firstName:Anna,activeTrial:false&#x60;. This matches users that have the user attribute &#x60;firstName&#x60; set to &#x60;Anna&#x60;, that also have the attribute &#x60;activeTrial&#x60; set to &#x60;false&#x60;.  To paginate through results, follow the &#x60;next&#x60; link in the &#x60;_links&#x60; object. To learn more, read [Representations](https://apidocs.launchdarkly.com). 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.UsersApi;
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
    String q = "q_example"; // Full-text search for users based on name, first name, last name, e-mail address, or key
    Long limit = 56L; // Specifies the maximum number of items in the collection to return (max: 50, default: 20)
    Long offset = 56L; // Deprecated, use `searchAfter` instead. Specifies the first item to return in the collection.
    Long after = 56L; // A Unix epoch time in milliseconds specifying the maximum last time a user requested a feature flag from LaunchDarkly
    String sort = "sort_example"; // Specifies a field by which to sort. LaunchDarkly supports the `userKey` and `lastSeen` fields. Fields prefixed by a dash ( - ) sort in descending order.
    String searchAfter = "searchAfter_example"; // Limits results to users with sort values after the value you specify. You can use this for pagination, but we recommend using the `next` link we provide instead.
    String filter = "filter_example"; // A comma-separated list of user attribute filters. Each filter is in the form of attributeKey:attributeValue
    try {
      Users result = client
              .users
              .searchUsers(projectKey, environmentKey)
              .q(q)
              .limit(limit)
              .offset(offset)
              .after(after)
              .sort(sort)
              .searchAfter(searchAfter)
              .filter(filter)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getTotalCount());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#searchUsers");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Users> response = client
              .users
              .searchUsers(projectKey, environmentKey)
              .q(q)
              .limit(limit)
              .offset(offset)
              .after(after)
              .sort(sort)
              .searchAfter(searchAfter)
              .filter(filter)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling UsersApi#searchUsers");
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
| **q** | **String**| Full-text search for users based on name, first name, last name, e-mail address, or key | [optional] |
| **limit** | **Long**| Specifies the maximum number of items in the collection to return (max: 50, default: 20) | [optional] |
| **offset** | **Long**| Deprecated, use &#x60;searchAfter&#x60; instead. Specifies the first item to return in the collection. | [optional] |
| **after** | **Long**| A Unix epoch time in milliseconds specifying the maximum last time a user requested a feature flag from LaunchDarkly | [optional] |
| **sort** | **String**| Specifies a field by which to sort. LaunchDarkly supports the &#x60;userKey&#x60; and &#x60;lastSeen&#x60; fields. Fields prefixed by a dash ( - ) sort in descending order. | [optional] |
| **searchAfter** | **String**| Limits results to users with sort values after the value you specify. You can use this for pagination, but we recommend using the &#x60;next&#x60; link we provide instead. | [optional] |
| **filter** | **String**| A comma-separated list of user attribute filters. Each filter is in the form of attributeKey:attributeValue | [optional] |

### Return type

[**Users**](Users.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Users collection response |  -  |

