# ReleasePipelinesBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNewPipeline**](ReleasePipelinesBetaApi.md#createNewPipeline) | **POST** /api/v2/projects/{projectKey}/release-pipelines | Create a release pipeline |
| [**deletePipeline**](ReleasePipelinesBetaApi.md#deletePipeline) | **DELETE** /api/v2/projects/{projectKey}/release-pipelines/{pipelineKey} | Delete release pipeline |
| [**getAllReleasePipelines**](ReleasePipelinesBetaApi.md#getAllReleasePipelines) | **GET** /api/v2/projects/{projectKey}/release-pipelines | Get all release pipelines |
| [**getByPipeKey**](ReleasePipelinesBetaApi.md#getByPipeKey) | **GET** /api/v2/projects/{projectKey}/release-pipelines/{pipelineKey} | Get release pipeline by key |
| [**updatePipelinePatch**](ReleasePipelinesBetaApi.md#updatePipelinePatch) | **PATCH** /api/v2/projects/{projectKey}/release-pipelines/{pipelineKey} | Update a release pipeline |


<a name="createNewPipeline"></a>
# **createNewPipeline**
> ReleasePipeline createNewPipeline(projectKey, createReleasePipelineInput).execute();

Create a release pipeline

Creates a new release pipeline.  The first release pipeline you create is automatically set as the default release pipeline for your project. To change the default release pipeline, use the [Update project](https://apidocs.launchdarkly.com) API to set the &#x60;defaultReleasePipelineKey&#x60;.  You can create up to 20 release pipelines per project. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ReleasePipelinesBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String key = "key_example"; // The unique identifier of this release pipeline
    String name = "name_example"; // The name of the release pipeline
    List<CreatePhaseInput> phases = Arrays.asList(); // A logical grouping of one or more environments that share attributes for rolling out changes
    String projectKey = "projectKey_example"; // The project key
    List<String> tags = Arrays.asList(); // A list of tags for this release pipeline
    String description = "description_example"; // The release pipeline description
    try {
      ReleasePipeline result = client
              .releasePipelinesBeta
              .createNewPipeline(key, name, phases, projectKey)
              .tags(tags)
              .description(description)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getDescription());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getPhases());
      System.out.println(result.getVersion());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePipelinesBetaApi#createNewPipeline");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ReleasePipeline> response = client
              .releasePipelinesBeta
              .createNewPipeline(key, name, phases, projectKey)
              .tags(tags)
              .description(description)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePipelinesBetaApi#createNewPipeline");
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
| **createReleasePipelineInput** | [**CreateReleasePipelineInput**](CreateReleasePipelineInput.md)|  | |

### Return type

[**ReleasePipeline**](ReleasePipeline.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Release pipeline response |  -  |

<a name="deletePipeline"></a>
# **deletePipeline**
> deletePipeline(projectKey, pipelineKey).execute();

Delete release pipeline

Deletes a release pipeline.  You cannot delete the default release pipeline.  If you want to delete a release pipeline that is currently the default, create a second release pipeline and set it as the default. Then delete the first release pipeline. To change the default release pipeline, use the [Update project](https://apidocs.launchdarkly.com) API to set the &#x60;defaultReleasePipelineKey&#x60;. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ReleasePipelinesBetaApi;
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
    String pipelineKey = "pipelineKey_example"; // The release pipeline key
    try {
      client
              .releasePipelinesBeta
              .deletePipeline(projectKey, pipelineKey)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePipelinesBetaApi#deletePipeline");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .releasePipelinesBeta
              .deletePipeline(projectKey, pipelineKey)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePipelinesBetaApi#deletePipeline");
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
| **pipelineKey** | **String**| The release pipeline key | |

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

<a name="getAllReleasePipelines"></a>
# **getAllReleasePipelines**
> ReleasePipelineCollection getAllReleasePipelines(projectKey).filter(filter).limit(limit).offset(offset).execute();

Get all release pipelines

Get all release pipelines for a project.  ### Filtering release pipelines  LaunchDarkly supports the following fields for filters:  - &#x60;query&#x60; is a string that matches against the release pipeline &#x60;key&#x60;, &#x60;name&#x60;, and &#x60;description&#x60;. It is not case sensitive. For example: &#x60;?filter&#x3D;query:examplePipeline&#x60;. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ReleasePipelinesBetaApi;
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
    String filter = "filter_example"; // A comma-separated list of filters. Each filter is of the form field:value. Read the endpoint description for a full list of available filter fields.
    Long limit = 56L; // The maximum number of items to return. Defaults to 20.
    Long offset = 56L; // Where to start in the list. Defaults to 0. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    try {
      ReleasePipelineCollection result = client
              .releasePipelinesBeta
              .getAllReleasePipelines(projectKey)
              .filter(filter)
              .limit(limit)
              .offset(offset)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getTotalCount());
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePipelinesBetaApi#getAllReleasePipelines");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ReleasePipelineCollection> response = client
              .releasePipelinesBeta
              .getAllReleasePipelines(projectKey)
              .filter(filter)
              .limit(limit)
              .offset(offset)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePipelinesBetaApi#getAllReleasePipelines");
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
| **filter** | **String**| A comma-separated list of filters. Each filter is of the form field:value. Read the endpoint description for a full list of available filter fields. | [optional] |
| **limit** | **Long**| The maximum number of items to return. Defaults to 20. | [optional] |
| **offset** | **Long**| Where to start in the list. Defaults to 0. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |

### Return type

[**ReleasePipelineCollection**](ReleasePipelineCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Release pipeline collection |  -  |

<a name="getByPipeKey"></a>
# **getByPipeKey**
> ReleasePipeline getByPipeKey(projectKey, pipelineKey).execute();

Get release pipeline by key

Get a release pipeline by key

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ReleasePipelinesBetaApi;
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
    String pipelineKey = "pipelineKey_example"; // The release pipeline key
    try {
      ReleasePipeline result = client
              .releasePipelinesBeta
              .getByPipeKey(projectKey, pipelineKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getDescription());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getPhases());
      System.out.println(result.getVersion());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePipelinesBetaApi#getByPipeKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ReleasePipeline> response = client
              .releasePipelinesBeta
              .getByPipeKey(projectKey, pipelineKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePipelinesBetaApi#getByPipeKey");
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
| **pipelineKey** | **String**| The release pipeline key | |

### Return type

[**ReleasePipeline**](ReleasePipeline.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Release pipeline response |  -  |

<a name="updatePipelinePatch"></a>
# **updatePipelinePatch**
> ReleasePipeline updatePipelinePatch(projectKey, pipelineKey).execute();

Update a release pipeline

Updates a release pipeline. Updating a release pipeline uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ReleasePipelinesBetaApi;
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
    String pipelineKey = "pipelineKey_example"; // The release pipeline key
    try {
      ReleasePipeline result = client
              .releasePipelinesBeta
              .updatePipelinePatch(projectKey, pipelineKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getDescription());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getPhases());
      System.out.println(result.getVersion());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePipelinesBetaApi#updatePipelinePatch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ReleasePipeline> response = client
              .releasePipelinesBeta
              .updatePipelinePatch(projectKey, pipelineKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ReleasePipelinesBetaApi#updatePipelinePatch");
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
| **pipelineKey** | **String**| The release pipeline key | |

### Return type

[**ReleasePipeline**](ReleasePipeline.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Release pipeline response |  -  |

