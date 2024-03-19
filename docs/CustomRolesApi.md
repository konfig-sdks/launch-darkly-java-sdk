# CustomRolesApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNewRole**](CustomRolesApi.md#createNewRole) | **POST** /api/v2/roles | Create custom role |
| [**deleteRoleByCustomKey**](CustomRolesApi.md#deleteRoleByCustomKey) | **DELETE** /api/v2/roles/{customRoleKey} | Delete custom role |
| [**getByCustomKey**](CustomRolesApi.md#getByCustomKey) | **GET** /api/v2/roles/{customRoleKey} | Get custom role |
| [**listCustomRoles**](CustomRolesApi.md#listCustomRoles) | **GET** /api/v2/roles | List custom roles |
| [**updateSingleCustomRole**](CustomRolesApi.md#updateSingleCustomRole) | **PATCH** /api/v2/roles/{customRoleKey} | Update custom role |


<a name="createNewRole"></a>
# **createNewRole**
> CustomRole createNewRole(customRolePost).execute();

Create custom role

Create a new custom role

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CustomRolesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String name = "name_example"; // A human-friendly name for the custom role
    String key = "key_example"; // The custom role key
    List<StatementPost> policy = Arrays.asList();
    String description = "description_example"; // Description of custom role
    String basePermissions = "basePermissions_example";
    try {
      CustomRole result = client
              .customRoles
              .createNewRole(name, key, policy)
              .description(description)
              .basePermissions(basePermissions)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getLinks());
      System.out.println(result.getAccess());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getPolicy());
      System.out.println(result.getBasePermissions());
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomRolesApi#createNewRole");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<CustomRole> response = client
              .customRoles
              .createNewRole(name, key, policy)
              .description(description)
              .basePermissions(basePermissions)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomRolesApi#createNewRole");
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
| **customRolePost** | [**CustomRolePost**](CustomRolePost.md)|  | |

### Return type

[**CustomRole**](CustomRole.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Custom role response |  -  |

<a name="deleteRoleByCustomKey"></a>
# **deleteRoleByCustomKey**
> deleteRoleByCustomKey(customRoleKey).execute();

Delete custom role

Delete a custom role by key

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CustomRolesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String customRoleKey = "customRoleKey_example"; // The custom role key
    try {
      client
              .customRoles
              .deleteRoleByCustomKey(customRoleKey)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomRolesApi#deleteRoleByCustomKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .customRoles
              .deleteRoleByCustomKey(customRoleKey)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomRolesApi#deleteRoleByCustomKey");
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
| **customRoleKey** | **String**| The custom role key | |

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

<a name="getByCustomKey"></a>
# **getByCustomKey**
> CustomRole getByCustomKey(customRoleKey).execute();

Get custom role

Get a single custom role by key or ID

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CustomRolesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String customRoleKey = "customRoleKey_example"; // The custom role key or ID
    try {
      CustomRole result = client
              .customRoles
              .getByCustomKey(customRoleKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getLinks());
      System.out.println(result.getAccess());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getPolicy());
      System.out.println(result.getBasePermissions());
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomRolesApi#getByCustomKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<CustomRole> response = client
              .customRoles
              .getByCustomKey(customRoleKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomRolesApi#getByCustomKey");
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
| **customRoleKey** | **String**| The custom role key or ID | |

### Return type

[**CustomRole**](CustomRole.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Custom role response |  -  |

<a name="listCustomRoles"></a>
# **listCustomRoles**
> CustomRoles listCustomRoles().execute();

List custom roles

Get a complete list of custom roles. Custom roles let you create flexible policies providing fine-grained access control to everything in LaunchDarkly, from feature flags to goals, environments, and teams. With custom roles, it&#39;s possible to enforce access policies that meet your exact workflow needs. Custom roles are available to customers on our enterprise plans. If you&#39;re interested in learning more about our enterprise plans, contact sales@launchdarkly.com.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CustomRolesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    try {
      CustomRoles result = client
              .customRoles
              .listCustomRoles()
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomRolesApi#listCustomRoles");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<CustomRoles> response = client
              .customRoles
              .listCustomRoles()
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomRolesApi#listCustomRoles");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**CustomRoles**](CustomRoles.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Custom roles collection response |  -  |

<a name="updateSingleCustomRole"></a>
# **updateSingleCustomRole**
> CustomRole updateSingleCustomRole(customRoleKey, patchWithComment).execute();

Update custom role

Update a single custom role. Updating a custom role uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) or [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).&lt;br/&gt;&lt;br/&gt;To add an element to the &#x60;policy&#x60; array, set the &#x60;path&#x60; to &#x60;/policy&#x60; and then append &#x60;/&lt;array index&gt;&#x60;. Use &#x60;/0&#x60; to add to the beginning of the array. Use &#x60;/-&#x60; to add to the end of the array.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CustomRolesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    List<PatchOperation> patch = Arrays.asList();
    String customRoleKey = "customRoleKey_example"; // The custom role key
    String comment = "comment_example"; // Optional comment
    try {
      CustomRole result = client
              .customRoles
              .updateSingleCustomRole(patch, customRoleKey)
              .comment(comment)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getLinks());
      System.out.println(result.getAccess());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getPolicy());
      System.out.println(result.getBasePermissions());
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomRolesApi#updateSingleCustomRole");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<CustomRole> response = client
              .customRoles
              .updateSingleCustomRole(patch, customRoleKey)
              .comment(comment)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CustomRolesApi#updateSingleCustomRole");
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
| **customRoleKey** | **String**| The custom role key | |
| **patchWithComment** | [**PatchWithComment**](PatchWithComment.md)|  | |

### Return type

[**CustomRole**](CustomRole.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Custom role response |  -  |

