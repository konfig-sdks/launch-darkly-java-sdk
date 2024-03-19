# WorkflowTemplatesApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createFeatureFlagTemplate**](WorkflowTemplatesApi.md#createFeatureFlagTemplate) | **POST** /api/v2/templates | Create workflow template |
| [**deleteTemplate**](WorkflowTemplatesApi.md#deleteTemplate) | **DELETE** /api/v2/templates/{templateKey} | Delete workflow template |
| [**list**](WorkflowTemplatesApi.md#list) | **GET** /api/v2/templates | Get workflow templates |


<a name="createFeatureFlagTemplate"></a>
# **createFeatureFlagTemplate**
> WorkflowTemplateOutput createFeatureFlagTemplate(createWorkflowTemplateInput).execute();

Create workflow template

Create a template for a feature flag workflow

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WorkflowTemplatesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String key = "key_example";
    String description = "description_example";
    String name = "name_example";
    String workflowId = "workflowId_example";
    List<StageInput> stages = Arrays.asList();
    String projectKey = "projectKey_example";
    String environmentKey = "environmentKey_example";
    String flagKey = "flagKey_example";
    try {
      WorkflowTemplateOutput result = client
              .workflowTemplates
              .createFeatureFlagTemplate(key)
              .description(description)
              .name(name)
              .workflowId(workflowId)
              .stages(stages)
              .projectKey(projectKey)
              .environmentKey(environmentKey)
              .flagKey(flagKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getCreationDate());
      System.out.println(result.getOwnerId());
      System.out.println(result.getMaintainerId());
      System.out.println(result.getLinks());
      System.out.println(result.getStages());
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowTemplatesApi#createFeatureFlagTemplate");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<WorkflowTemplateOutput> response = client
              .workflowTemplates
              .createFeatureFlagTemplate(key)
              .description(description)
              .name(name)
              .workflowId(workflowId)
              .stages(stages)
              .projectKey(projectKey)
              .environmentKey(environmentKey)
              .flagKey(flagKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowTemplatesApi#createFeatureFlagTemplate");
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
| **createWorkflowTemplateInput** | [**CreateWorkflowTemplateInput**](CreateWorkflowTemplateInput.md)|  | |

### Return type

[**WorkflowTemplateOutput**](WorkflowTemplateOutput.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Workflow template response JSON |  -  |

<a name="deleteTemplate"></a>
# **deleteTemplate**
> deleteTemplate(templateKey).execute();

Delete workflow template

Delete a workflow template

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WorkflowTemplatesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String templateKey = "templateKey_example"; // The template key
    try {
      client
              .workflowTemplates
              .deleteTemplate(templateKey)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowTemplatesApi#deleteTemplate");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .workflowTemplates
              .deleteTemplate(templateKey)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowTemplatesApi#deleteTemplate");
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
| **templateKey** | **String**| The template key | |

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
| **204** | Action completed successfully |  -  |

<a name="list"></a>
# **list**
> WorkflowTemplatesListingOutputRep list().summary(summary).search(search).execute();

Get workflow templates

Get workflow templates belonging to an account, or can optionally return templates_endpoints.workflowTemplateSummariesListingOutputRep when summary query param is true

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WorkflowTemplatesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    Boolean summary = true; // Whether the entire template object or just a summary should be returned
    String search = "search_example"; // The substring in either the name or description of a template
    try {
      WorkflowTemplatesListingOutputRep result = client
              .workflowTemplates
              .list()
              .summary(summary)
              .search(search)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowTemplatesApi#list");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<WorkflowTemplatesListingOutputRep> response = client
              .workflowTemplates
              .list()
              .summary(summary)
              .search(search)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowTemplatesApi#list");
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
| **summary** | **Boolean**| Whether the entire template object or just a summary should be returned | [optional] |
| **search** | **String**| The substring in either the name or description of a template | [optional] |

### Return type

[**WorkflowTemplatesListingOutputRep**](WorkflowTemplatesListingOutputRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Workflow templates list response JSON |  -  |

