# ProjectsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNewProject**](ProjectsApi.md#createNewProject) | **POST** /api/v2/projects | Create project |
| [**deleteByProjectKey**](ProjectsApi.md#deleteByProjectKey) | **DELETE** /api/v2/projects/{projectKey} | Delete project |
| [**getFlagDefaults**](ProjectsApi.md#getFlagDefaults) | **GET** /api/v2/projects/{projectKey}/flag-defaults | Get flag defaults for project |
| [**listProjectsDefault**](ProjectsApi.md#listProjectsDefault) | **GET** /api/v2/projects | List projects |
| [**singleByProjectKey**](ProjectsApi.md#singleByProjectKey) | **GET** /api/v2/projects/{projectKey} | Get project |
| [**updateFlagDefault**](ProjectsApi.md#updateFlagDefault) | **PATCH** /api/v2/projects/{projectKey}/flag-defaults | Update flag default for project |
| [**updateFlagDefaultsForProject**](ProjectsApi.md#updateFlagDefaultsForProject) | **PUT** /api/v2/projects/{projectKey}/flag-defaults | Create or update flag defaults for project |
| [**updateProjectPatch**](ProjectsApi.md#updateProjectPatch) | **PATCH** /api/v2/projects/{projectKey} | Update project |


<a name="createNewProject"></a>
# **createNewProject**
> ProjectRep createNewProject(projectPost).execute();

Create project

Create a new project with the given key and name. Project keys must be unique within an account.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProjectsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String name = "name_example"; // A human-friendly name for the project.
    String key = "key_example"; // A unique key used to reference the project in your code.
    List<String> tags = Arrays.asList(); // Tags for the project
    Boolean includeInSnippetByDefault = true; // Whether or not flags created in this project are made available to the client-side JavaScript SDK by default.
    DefaultClientSideAvailabilityPost defaultClientSideAvailability = new DefaultClientSideAvailabilityPost();
    List<EnvironmentPost> environments = Arrays.asList(); // Creates the provided environments for this project. If omitted default environments will be created instead.
    try {
      ProjectRep result = client
              .projects
              .createNewProject(name, key)
              .tags(tags)
              .includeInSnippetByDefault(includeInSnippetByDefault)
              .defaultClientSideAvailability(defaultClientSideAvailability)
              .environments(environments)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getKey());
      System.out.println(result.getIncludeInSnippetByDefault());
      System.out.println(result.getDefaultClientSideAvailability());
      System.out.println(result.getName());
      System.out.println(result.getAccess());
      System.out.println(result.getDefaultReleasePipelineKey());
      System.out.println(result.getEnvironments());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#createNewProject");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ProjectRep> response = client
              .projects
              .createNewProject(name, key)
              .tags(tags)
              .includeInSnippetByDefault(includeInSnippetByDefault)
              .defaultClientSideAvailability(defaultClientSideAvailability)
              .environments(environments)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#createNewProject");
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
| **projectPost** | [**ProjectPost**](ProjectPost.md)|  | |

### Return type

[**ProjectRep**](ProjectRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Project response |  -  |

<a name="deleteByProjectKey"></a>
# **deleteByProjectKey**
> deleteByProjectKey(projectKey).execute();

Delete project

Delete a project by key. Use this endpoint with caution. Deleting a project will delete all associated environments and feature flags. You cannot delete the last project in an account.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProjectsApi;
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
    try {
      client
              .projects
              .deleteByProjectKey(projectKey)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#deleteByProjectKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .projects
              .deleteByProjectKey(projectKey)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#deleteByProjectKey");
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

<a name="getFlagDefaults"></a>
# **getFlagDefaults**
> FlagDefaultsRep getFlagDefaults(projectKey).execute();

Get flag defaults for project

Get the flag defaults for a specific project.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProjectsApi;
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
    try {
      FlagDefaultsRep result = client
              .projects
              .getFlagDefaults(projectKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getKey());
      System.out.println(result.getTemporary());
      System.out.println(result.getDefaultClientSideAvailability());
      System.out.println(result.getBooleanDefaults());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#getFlagDefaults");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FlagDefaultsRep> response = client
              .projects
              .getFlagDefaults(projectKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#getFlagDefaults");
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

### Return type

[**FlagDefaultsRep**](FlagDefaultsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag defaults response |  -  |

<a name="listProjectsDefault"></a>
# **listProjectsDefault**
> Projects listProjectsDefault().limit(limit).offset(offset).filter(filter).sort(sort).expand(expand).execute();

List projects

Return a list of projects.  By default, this returns the first 20 projects. Page through this list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the &#x60;_links&#x60; field that returns. If those links do not appear, the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page, because there is no previous page and you cannot return to the first page when you are already on the first page.  ### Filtering projects  LaunchDarkly supports two fields for filters: - &#x60;query&#x60; is a string that matches against the projects&#39; names and keys. It is not case sensitive. - &#x60;tags&#x60; is a &#x60;+&#x60;-separated list of project tags. It filters the list of projects that have all of the tags in the list.  For example, the filter &#x60;filter&#x3D;query:abc,tags:tag-1+tag-2&#x60; matches projects with the string &#x60;abc&#x60; in their name or key and also are tagged with &#x60;tag-1&#x60; and &#x60;tag-2&#x60;. The filter is not case-sensitive.  The documented values for &#x60;filter&#x60; query parameters are prior to URL encoding. For example, the &#x60;+&#x60; in &#x60;filter&#x3D;tags:tag-1+tag-2&#x60; must be encoded to &#x60;%2B&#x60;.  ### Sorting projects  LaunchDarkly supports two fields for sorting: - &#x60;name&#x60; sorts by project name. - &#x60;createdOn&#x60; sorts by the creation date of the project.  For example, &#x60;sort&#x3D;name&#x60; sorts the response by project name in ascending order.  ### Expanding the projects response  LaunchDarkly supports one field for expanding the \&quot;List projects\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with the &#x60;environments&#x60; field.  &#x60;Environments&#x60; includes a paginated list of the project environments. * &#x60;environments&#x60; includes a paginated list of the project environments.  For example, &#x60;expand&#x3D;environments&#x60; includes the &#x60;environments&#x60; field for each project in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProjectsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    Long limit = 56L; // The number of projects to return in the response. Defaults to 20.
    Long offset = 56L; // Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next `limit` items.
    String filter = "filter_example"; // A comma-separated list of filters. Each filter is constructed as `field:value`.
    String sort = "sort_example"; // A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order.
    String expand = "expand_example"; // A comma-separated list of properties that can reveal additional information in the response.
    try {
      Projects result = client
              .projects
              .listProjectsDefault()
              .limit(limit)
              .offset(offset)
              .filter(filter)
              .sort(sort)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
      System.out.println(result.getTotalCount());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#listProjectsDefault");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Projects> response = client
              .projects
              .listProjectsDefault()
              .limit(limit)
              .offset(offset)
              .filter(filter)
              .sort(sort)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#listProjectsDefault");
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
| **limit** | **Long**| The number of projects to return in the response. Defaults to 20. | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next &#x60;limit&#x60; items. | [optional] |
| **filter** | **String**| A comma-separated list of filters. Each filter is constructed as &#x60;field:value&#x60;. | [optional] |
| **sort** | **String**| A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order. | [optional] |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. | [optional] |

### Return type

[**Projects**](Projects.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Project collection response |  -  |

<a name="singleByProjectKey"></a>
# **singleByProjectKey**
> Project singleByProjectKey(projectKey).expand(expand).execute();

Get project

Get a single project by key.  ### Expanding the project response  LaunchDarkly supports one field for expanding the \&quot;Get project\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields: * &#x60;environments&#x60; includes a paginated list of the project environments.  For example, &#x60;expand&#x3D;environments&#x60; includes the &#x60;environments&#x60; field for the project in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProjectsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String projectKey = "projectKey_example"; // The project key.
    String expand = "expand_example"; // A comma-separated list of properties that can reveal additional information in the response.
    try {
      Project result = client
              .projects
              .singleByProjectKey(projectKey)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getKey());
      System.out.println(result.getIncludeInSnippetByDefault());
      System.out.println(result.getDefaultClientSideAvailability());
      System.out.println(result.getName());
      System.out.println(result.getAccess());
      System.out.println(result.getDefaultReleasePipelineKey());
      System.out.println(result.getEnvironments());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#singleByProjectKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Project> response = client
              .projects
              .singleByProjectKey(projectKey)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#singleByProjectKey");
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
| **projectKey** | **String**| The project key. | |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. | [optional] |

### Return type

[**Project**](Project.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Project response |  -  |

<a name="updateFlagDefault"></a>
# **updateFlagDefault**
> UpsertPayloadRep updateFlagDefault(projectKey, patchOperation).execute();

Update flag default for project

Update a flag default. Updating a flag default uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) or [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProjectsApi;
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
    try {
      UpsertPayloadRep result = client
              .projects
              .updateFlagDefault(projectKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getTemporary());
      System.out.println(result.getBooleanDefaults());
      System.out.println(result.getDefaultClientSideAvailability());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#updateFlagDefault");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<UpsertPayloadRep> response = client
              .projects
              .updateFlagDefault(projectKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#updateFlagDefault");
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
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**UpsertPayloadRep**](UpsertPayloadRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag default response |  -  |

<a name="updateFlagDefaultsForProject"></a>
# **updateFlagDefaultsForProject**
> UpsertPayloadRep updateFlagDefaultsForProject(projectKey, upsertFlagDefaultsPayload).execute();

Create or update flag defaults for project

Create or update flag defaults for a project.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProjectsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    List<String> tags = Arrays.asList(); // A list of default tags for each flag
    Boolean temporary = true; // Whether the flag should be temporary by default
    BooleanFlagDefaults booleanDefaults = new BooleanFlagDefaults();
    DefaultClientSideAvailability defaultClientSideAvailability = new DefaultClientSideAvailability();
    String projectKey = "projectKey_example"; // The project key
    try {
      UpsertPayloadRep result = client
              .projects
              .updateFlagDefaultsForProject(tags, temporary, booleanDefaults, defaultClientSideAvailability, projectKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getTemporary());
      System.out.println(result.getBooleanDefaults());
      System.out.println(result.getDefaultClientSideAvailability());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#updateFlagDefaultsForProject");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<UpsertPayloadRep> response = client
              .projects
              .updateFlagDefaultsForProject(tags, temporary, booleanDefaults, defaultClientSideAvailability, projectKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#updateFlagDefaultsForProject");
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
| **upsertFlagDefaultsPayload** | [**UpsertFlagDefaultsPayload**](UpsertFlagDefaultsPayload.md)|  | |

### Return type

[**UpsertPayloadRep**](UpsertPayloadRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag default response |  -  |

<a name="updateProjectPatch"></a>
# **updateProjectPatch**
> ProjectRep updateProjectPatch(projectKey, patchOperation).execute();

Update project

Update a project. Updating a project uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).&lt;br/&gt;&lt;br/&gt;To add an element to the project fields that are arrays, set the &#x60;path&#x60; to the name of the field and then append &#x60;/&lt;array index&gt;&#x60;. Use &#x60;/0&#x60; to add to the beginning of the array. Use &#x60;/-&#x60; to add to the end of the array.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ProjectsApi;
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
    try {
      ProjectRep result = client
              .projects
              .updateProjectPatch(projectKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getKey());
      System.out.println(result.getIncludeInSnippetByDefault());
      System.out.println(result.getDefaultClientSideAvailability());
      System.out.println(result.getName());
      System.out.println(result.getAccess());
      System.out.println(result.getDefaultReleasePipelineKey());
      System.out.println(result.getEnvironments());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#updateProjectPatch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ProjectRep> response = client
              .projects
              .updateProjectPatch(projectKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#updateProjectPatch");
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
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**ProjectRep**](ProjectRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Project response |  -  |

