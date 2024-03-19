# EnvironmentsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNewEnvironment**](EnvironmentsApi.md#createNewEnvironment) | **POST** /api/v2/projects/{projectKey}/environments | Create environment |
| [**getByProjectAndKey**](EnvironmentsApi.md#getByProjectAndKey) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey} | Get environment |
| [**listEnvironments**](EnvironmentsApi.md#listEnvironments) | **GET** /api/v2/projects/{projectKey}/environments | List environments |
| [**removeByEnvironmentKey**](EnvironmentsApi.md#removeByEnvironmentKey) | **DELETE** /api/v2/projects/{projectKey}/environments/{environmentKey} | Delete environment |
| [**resetMobileSdkKey**](EnvironmentsApi.md#resetMobileSdkKey) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/mobileKey | Reset environment mobile SDK key |
| [**resetSdkKey**](EnvironmentsApi.md#resetSdkKey) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/apiKey | Reset environment SDK key |
| [**updateEnvironmentPatch**](EnvironmentsApi.md#updateEnvironmentPatch) | **PATCH** /api/v2/projects/{projectKey}/environments/{environmentKey} | Update environment |


<a name="createNewEnvironment"></a>
# **createNewEnvironment**
> Environment createNewEnvironment(projectKey, environmentPost).execute();

Create environment

&gt; ### Approval settings &gt; &gt; The &#x60;approvalSettings&#x60; key is only returned when the Flag Approvals feature is enabled. &gt; &gt; You cannot update approval settings when creating new environments. Update approval settings with the PATCH Environment API.  Create a new environment in a specified project with a given name, key, swatch color, and default TTL. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.EnvironmentsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String name = "name_example"; // A human-friendly name for the new environment
    String key = "key_example"; // A project-unique key for the new environment
    String color = "color_example"; // A color to indicate this environment in the UI
    String projectKey = "projectKey_example"; // The project key
    List<String> tags = Arrays.asList(); // Tags to apply to the new environment
    Integer defaultTtl = 56; // The default time (in minutes) that the PHP SDK can cache feature flag rules locally
    Boolean secureMode = true; // Ensures that one end user of the client-side SDK cannot inspect the variations for another end user
    Boolean defaultTrackEvents = true; // Enables tracking detailed information for new flags by default
    Boolean confirmChanges = true; // Requires confirmation for all flag and segment changes via the UI in this environment
    Boolean requireComments = true; // Requires comments for all flag and segment changes via the UI in this environment
    SourceEnv source = new SourceEnv();
    Boolean critical = true; // Whether the environment is critical
    try {
      Environment result = client
              .environments
              .createNewEnvironment(name, key, color, projectKey)
              .tags(tags)
              .defaultTtl(defaultTtl)
              .secureMode(secureMode)
              .defaultTrackEvents(defaultTrackEvents)
              .confirmChanges(confirmChanges)
              .requireComments(requireComments)
              .source(source)
              .critical(critical)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getApiKey());
      System.out.println(result.getMobileKey());
      System.out.println(result.getColor());
      System.out.println(result.getDefaultTtl());
      System.out.println(result.getSecureMode());
      System.out.println(result.getDefaultTrackEvents());
      System.out.println(result.getRequireComments());
      System.out.println(result.getConfirmChanges());
      System.out.println(result.getApprovalSettings());
      System.out.println(result.getCritical());
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentsApi#createNewEnvironment");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Environment> response = client
              .environments
              .createNewEnvironment(name, key, color, projectKey)
              .tags(tags)
              .defaultTtl(defaultTtl)
              .secureMode(secureMode)
              .defaultTrackEvents(defaultTrackEvents)
              .confirmChanges(confirmChanges)
              .requireComments(requireComments)
              .source(source)
              .critical(critical)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentsApi#createNewEnvironment");
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
| **environmentPost** | [**EnvironmentPost**](EnvironmentPost.md)|  | |

### Return type

[**Environment**](Environment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Environment response |  -  |

<a name="getByProjectAndKey"></a>
# **getByProjectAndKey**
> Environment getByProjectAndKey(projectKey, environmentKey).execute();

Get environment

&gt; ### Approval settings &gt; &gt; The &#x60;approvalSettings&#x60; key is only returned when the Flag Approvals feature is enabled.  Get an environment given a project and key. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.EnvironmentsApi;
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
      Environment result = client
              .environments
              .getByProjectAndKey(projectKey, environmentKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getApiKey());
      System.out.println(result.getMobileKey());
      System.out.println(result.getColor());
      System.out.println(result.getDefaultTtl());
      System.out.println(result.getSecureMode());
      System.out.println(result.getDefaultTrackEvents());
      System.out.println(result.getRequireComments());
      System.out.println(result.getConfirmChanges());
      System.out.println(result.getApprovalSettings());
      System.out.println(result.getCritical());
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentsApi#getByProjectAndKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Environment> response = client
              .environments
              .getByProjectAndKey(projectKey, environmentKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentsApi#getByProjectAndKey");
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

[**Environment**](Environment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Environment response |  -  |

<a name="listEnvironments"></a>
# **listEnvironments**
> Environments listEnvironments(projectKey).limit(limit).offset(offset).filter(filter).sort(sort).execute();

List environments

Return a list of environments for the specified project.  By default, this returns the first 20 environments. Page through this list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the &#x60;_links&#x60; field that returns. If those links do not appear, the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page, because there is no previous page and you cannot return to the first page when you are already on the first page.  ### Filtering environments  LaunchDarkly supports two fields for filters: - &#x60;query&#x60; is a string that matches against the environments&#39; names and keys. It is not case sensitive. - &#x60;tags&#x60; is a &#x60;+&#x60;-separated list of environment tags. It filters the list of environments that have all of the tags in the list.  For example, the filter &#x60;filter&#x3D;query:abc,tags:tag-1+tag-2&#x60; matches environments with the string &#x60;abc&#x60; in their name or key and also are tagged with &#x60;tag-1&#x60; and &#x60;tag-2&#x60;. The filter is not case-sensitive.  The documented values for &#x60;filter&#x60; query parameters are prior to URL encoding. For example, the &#x60;+&#x60; in &#x60;filter&#x3D;tags:tag-1+tag-2&#x60; must be encoded to &#x60;%2B&#x60;.  ### Sorting environments  LaunchDarkly supports the following fields for sorting:  - &#x60;createdOn&#x60; sorts by the creation date of the environment. - &#x60;critical&#x60; sorts by whether the environments are marked as critical. - &#x60;name&#x60; sorts by environment name.  For example, &#x60;sort&#x3D;name&#x60; sorts the response by environment name in ascending order. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.EnvironmentsApi;
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
    Long limit = 56L; // The number of environments to return in the response. Defaults to 20.
    Long offset = 56L; // Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String filter = "filter_example"; // A comma-separated list of filters. Each filter is of the form `field:value`.
    String sort = "sort_example"; // A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order.
    try {
      Environments result = client
              .environments
              .listEnvironments(projectKey)
              .limit(limit)
              .offset(offset)
              .filter(filter)
              .sort(sort)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getTotalCount());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentsApi#listEnvironments");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Environments> response = client
              .environments
              .listEnvironments(projectKey)
              .limit(limit)
              .offset(offset)
              .filter(filter)
              .sort(sort)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentsApi#listEnvironments");
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
| **limit** | **Long**| The number of environments to return in the response. Defaults to 20. | [optional] |
| **offset** | **Long**| Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **filter** | **String**| A comma-separated list of filters. Each filter is of the form &#x60;field:value&#x60;. | [optional] |
| **sort** | **String**| A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order. | [optional] |

### Return type

[**Environments**](Environments.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Environments collection response |  -  |

<a name="removeByEnvironmentKey"></a>
# **removeByEnvironmentKey**
> removeByEnvironmentKey(projectKey, environmentKey).execute();

Delete environment

Delete a environment by key.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.EnvironmentsApi;
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
      client
              .environments
              .removeByEnvironmentKey(projectKey, environmentKey)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentsApi#removeByEnvironmentKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .environments
              .removeByEnvironmentKey(projectKey, environmentKey)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentsApi#removeByEnvironmentKey");
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

<a name="resetMobileSdkKey"></a>
# **resetMobileSdkKey**
> Environment resetMobileSdkKey(projectKey, environmentKey).execute();

Reset environment mobile SDK key

Reset an environment&#39;s mobile key. The optional expiry for the old key is deprecated for this endpoint, so the old key will always expire immediately.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.EnvironmentsApi;
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
      Environment result = client
              .environments
              .resetMobileSdkKey(projectKey, environmentKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getApiKey());
      System.out.println(result.getMobileKey());
      System.out.println(result.getColor());
      System.out.println(result.getDefaultTtl());
      System.out.println(result.getSecureMode());
      System.out.println(result.getDefaultTrackEvents());
      System.out.println(result.getRequireComments());
      System.out.println(result.getConfirmChanges());
      System.out.println(result.getApprovalSettings());
      System.out.println(result.getCritical());
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentsApi#resetMobileSdkKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Environment> response = client
              .environments
              .resetMobileSdkKey(projectKey, environmentKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentsApi#resetMobileSdkKey");
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

[**Environment**](Environment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Environment response |  -  |

<a name="resetSdkKey"></a>
# **resetSdkKey**
> Environment resetSdkKey(projectKey, environmentKey).expiry(expiry).execute();

Reset environment SDK key

Reset an environment&#39;s SDK key with an optional expiry time for the old key.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.EnvironmentsApi;
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
    Long expiry = 56L; // The time at which you want the old SDK key to expire, in UNIX milliseconds. By default, the key expires immediately. During the period between this call and the time when the old SDK key expires, both the old SDK key and the new SDK key will work.
    try {
      Environment result = client
              .environments
              .resetSdkKey(projectKey, environmentKey)
              .expiry(expiry)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getApiKey());
      System.out.println(result.getMobileKey());
      System.out.println(result.getColor());
      System.out.println(result.getDefaultTtl());
      System.out.println(result.getSecureMode());
      System.out.println(result.getDefaultTrackEvents());
      System.out.println(result.getRequireComments());
      System.out.println(result.getConfirmChanges());
      System.out.println(result.getApprovalSettings());
      System.out.println(result.getCritical());
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentsApi#resetSdkKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Environment> response = client
              .environments
              .resetSdkKey(projectKey, environmentKey)
              .expiry(expiry)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentsApi#resetSdkKey");
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
| **expiry** | **Long**| The time at which you want the old SDK key to expire, in UNIX milliseconds. By default, the key expires immediately. During the period between this call and the time when the old SDK key expires, both the old SDK key and the new SDK key will work. | [optional] |

### Return type

[**Environment**](Environment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Environment response |  -  |

<a name="updateEnvironmentPatch"></a>
# **updateEnvironmentPatch**
> Environment updateEnvironmentPatch(projectKey, environmentKey, patchOperation).execute();

Update environment

 Update an environment. Updating an environment uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).  To update fields in the environment object that are arrays, set the &#x60;path&#x60; to the name of the field and then append &#x60;/&lt;array index&gt;&#x60;. Using &#x60;/0&#x60; appends to the beginning of the array.  ### Approval settings  This request only returns the &#x60;approvalSettings&#x60; key if the [Flag Approvals](https://docs.launchdarkly.com/home/feature-workflows/approvals) feature is enabled.  Only the &#x60;canReviewOwnRequest&#x60;, &#x60;canApplyDeclinedChanges&#x60;, &#x60;minNumApprovals&#x60;, &#x60;required&#x60; and &#x60;requiredApprovalTagsfields&#x60; are editable.  If you try to patch the environment by setting both &#x60;required&#x60; and &#x60;requiredApprovalTags&#x60;, the request fails and an error appears. You can specify either required approvals for all flags in an environment or those with specific tags, but not both. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.EnvironmentsApi;
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
      Environment result = client
              .environments
              .updateEnvironmentPatch(projectKey, environmentKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getApiKey());
      System.out.println(result.getMobileKey());
      System.out.println(result.getColor());
      System.out.println(result.getDefaultTtl());
      System.out.println(result.getSecureMode());
      System.out.println(result.getDefaultTrackEvents());
      System.out.println(result.getRequireComments());
      System.out.println(result.getConfirmChanges());
      System.out.println(result.getApprovalSettings());
      System.out.println(result.getCritical());
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentsApi#updateEnvironmentPatch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Environment> response = client
              .environments
              .updateEnvironmentPatch(projectKey, environmentKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling EnvironmentsApi#updateEnvironmentPatch");
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
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**Environment**](Environment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Environment response |  -  |

