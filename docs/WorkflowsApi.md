# WorkflowsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createWorkflow**](WorkflowsApi.md#createWorkflow) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows | Create workflow |
| [**deleteFromFeatureFlag**](WorkflowsApi.md#deleteFromFeatureFlag) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows/{workflowId} | Delete workflow |
| [**getCustomWorkflowById**](WorkflowsApi.md#getCustomWorkflowById) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows/{workflowId} | Get custom workflow |
| [**getFeatureFlagEnvironmentsWorkflows**](WorkflowsApi.md#getFeatureFlagEnvironmentsWorkflows) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/workflows | Get workflows |


<a name="createWorkflow"></a>
# **createWorkflow**
> CustomWorkflowOutput createWorkflow(projectKey, featureFlagKey, environmentKey, customWorkflowInput).templateKey(templateKey).dryRun(dryRun).execute();

Create workflow

Create a workflow for a feature flag. You can create a workflow directly, or you can apply a template to create a new workflow.  ### Creating a workflow  You can use the create workflow endpoint to create a workflow directly by adding a &#x60;stages&#x60; array to the request body.  For each stage, define the &#x60;name&#x60;, &#x60;conditions&#x60; when the stage should be executed, and &#x60;action&#x60; that describes the stage.  &lt;details&gt; &lt;summary&gt;Click to expand example&lt;/summary&gt;  _Example request body_ &#x60;&#x60;&#x60;json {   \&quot;name\&quot;: \&quot;Progressive rollout starting in two days\&quot;,   \&quot;description\&quot;: \&quot;Turn flag targeting on and increase feature rollout in 10% increments each day\&quot;,   \&quot;stages\&quot;: [     {       \&quot;name\&quot;: \&quot;10% rollout on day 1\&quot;,       \&quot;conditions\&quot;: [         {           \&quot;kind\&quot;: \&quot;schedule\&quot;,           \&quot;scheduleKind\&quot;: \&quot;relative\&quot;, // or \&quot;absolute\&quot;               //  If \&quot;scheduleKind\&quot; is \&quot;absolute\&quot;, set \&quot;executionDate\&quot;;               // \&quot;waitDuration\&quot; and \&quot;waitDurationUnit\&quot; will be ignored           \&quot;waitDuration\&quot;: 2,           \&quot;waitDurationUnit\&quot;: \&quot;calendarDay\&quot;         },         {           \&quot;kind\&quot;: \&quot;ld-approval\&quot;,           \&quot;notifyMemberIds\&quot;: [ \&quot;507f1f77bcf86cd799439011\&quot; ],           \&quot;notifyTeamKeys\&quot;: [ \&quot;team-key-123abc\&quot; ]         }       ],       \&quot;action\&quot;: {         \&quot;instructions\&quot;: [           {             \&quot;kind\&quot;: \&quot;turnFlagOn\&quot;           },           {             \&quot;kind\&quot;: \&quot;updateFallthroughVariationOrRollout\&quot;,             \&quot;rolloutWeights\&quot;: {               \&quot;452f5fb5-7320-4ba3-81a1-8f4324f79d49\&quot;: 90000,               \&quot;fc15f6a4-05d3-4aa4-a997-446be461345d\&quot;: 10000             }           }         ]       }     }   ] } &#x60;&#x60;&#x60; &lt;/details&gt;  ### Creating a workflow by applying a workflow template  You can also create a workflow by applying a workflow template. If you pass a valid workflow template key as the &#x60;templateKey&#x60; query parameter with the request, the API will attempt to create a new workflow with the stages defined in the workflow template with the corresponding key.  #### Applicability of stages Templates are created in the context of a particular flag in a particular environment in a particular project. However, because workflows created from a template can be applied to any project, environment, and flag, some steps of the workflow may need to be updated in order to be applicable for the target resource.  You can pass a &#x60;dryRun&#x60; query parameter to tell the API to return a report of which steps of the workflow template are applicable in the target project/environment/flag, and which will need to be updated. When the &#x60;dryRun&#x60; query parameter is present the response body includes a &#x60;meta&#x60; property that holds a list of parameters that could potentially be inapplicable for the target resource. Each of these parameters will include a &#x60;valid&#x60; field. You will need to update any invalid parameters in order to create the new workflow. You can do this using the &#x60;parameters&#x60; property, which overrides the workflow template parameters.  #### Overriding template parameters You can use the &#x60;parameters&#x60; property in the request body to tell the API to override the specified workflow template parameters with new values that are specific to your target project/environment/flag.  &lt;details&gt; &lt;summary&gt;Click to expand example&lt;/summary&gt;  _Example request body_ &#x60;&#x60;&#x60;json {  \&quot;name\&quot;: \&quot;workflow created from my-template\&quot;,  \&quot;description\&quot;: \&quot;description of my workflow\&quot;,  \&quot;parameters\&quot;: [   {    \&quot;_id\&quot;: \&quot;62cf2bc4cadbeb7697943f3b\&quot;,    \&quot;path\&quot;: \&quot;/clauses/0/values\&quot;,    \&quot;default\&quot;: {     \&quot;value\&quot;: [\&quot;updated-segment\&quot;]    }   },   {    \&quot;_id\&quot;: \&quot;62cf2bc4cadbeb7697943f3d\&quot;,    \&quot;path\&quot;: \&quot;/variationId\&quot;,    \&quot;default\&quot;: {     \&quot;value\&quot;: \&quot;abcd1234-abcd-1234-abcd-1234abcd12\&quot;    }   }  ] } &#x60;&#x60;&#x60; &lt;/details&gt;  If there are any steps in the template that are not applicable to the target resource, the workflow will not be created, and the &#x60;meta&#x60; property will be included in the response body detailing which parameters need to be updated. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WorkflowsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String name = "name_example"; // The workflow name
    String projectKey = "projectKey_example"; // The project key
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    String environmentKey = "environmentKey_example"; // The environment key
    String description = "description_example"; // The workflow description
    String maintainerId = "maintainerId_example";
    List<StageInput> stages = Arrays.asList(); // A list of the workflow stages
    String templateKey = "templateKey_example"; // The template key
    String templateKey = "templateKey_example"; // The template key to apply as a starting point for the new workflow
    Boolean dryRun = true; // Whether to call the endpoint in dry-run mode
    try {
      CustomWorkflowOutput result = client
              .workflows
              .createWorkflow(name, projectKey, featureFlagKey, environmentKey)
              .description(description)
              .maintainerId(maintainerId)
              .stages(stages)
              .templateKey(templateKey)
              .templateKey(templateKey)
              .dryRun(dryRun)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getVersion());
      System.out.println(result.getConflicts());
      System.out.println(result.getCreationDate());
      System.out.println(result.getMaintainerId());
      System.out.println(result.getLinks());
      System.out.println(result.getName());
      System.out.println(result.getKind());
      System.out.println(result.getStages());
      System.out.println(result.getExecution());
      System.out.println(result.getMeta());
      System.out.println(result.getTemplateKey());
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowsApi#createWorkflow");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<CustomWorkflowOutput> response = client
              .workflows
              .createWorkflow(name, projectKey, featureFlagKey, environmentKey)
              .description(description)
              .maintainerId(maintainerId)
              .stages(stages)
              .templateKey(templateKey)
              .templateKey(templateKey)
              .dryRun(dryRun)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowsApi#createWorkflow");
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
| **customWorkflowInput** | [**CustomWorkflowInput**](CustomWorkflowInput.md)|  | |
| **templateKey** | **String**| The template key to apply as a starting point for the new workflow | [optional] |
| **dryRun** | **Boolean**| Whether to call the endpoint in dry-run mode | [optional] |

### Return type

[**CustomWorkflowOutput**](CustomWorkflowOutput.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Workflow response |  -  |

<a name="deleteFromFeatureFlag"></a>
# **deleteFromFeatureFlag**
> deleteFromFeatureFlag(projectKey, featureFlagKey, environmentKey, workflowId).execute();

Delete workflow

Delete a workflow from a feature flag.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WorkflowsApi;
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
    String workflowId = "workflowId_example"; // The workflow id
    try {
      client
              .workflows
              .deleteFromFeatureFlag(projectKey, featureFlagKey, environmentKey, workflowId)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowsApi#deleteFromFeatureFlag");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .workflows
              .deleteFromFeatureFlag(projectKey, featureFlagKey, environmentKey, workflowId)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowsApi#deleteFromFeatureFlag");
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
| **workflowId** | **String**| The workflow id | |

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

<a name="getCustomWorkflowById"></a>
# **getCustomWorkflowById**
> CustomWorkflowOutput getCustomWorkflowById(projectKey, featureFlagKey, environmentKey, workflowId).execute();

Get custom workflow

Get a specific workflow by ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WorkflowsApi;
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
    String workflowId = "workflowId_example"; // The workflow ID
    try {
      CustomWorkflowOutput result = client
              .workflows
              .getCustomWorkflowById(projectKey, featureFlagKey, environmentKey, workflowId)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getVersion());
      System.out.println(result.getConflicts());
      System.out.println(result.getCreationDate());
      System.out.println(result.getMaintainerId());
      System.out.println(result.getLinks());
      System.out.println(result.getName());
      System.out.println(result.getKind());
      System.out.println(result.getStages());
      System.out.println(result.getExecution());
      System.out.println(result.getMeta());
      System.out.println(result.getTemplateKey());
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowsApi#getCustomWorkflowById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<CustomWorkflowOutput> response = client
              .workflows
              .getCustomWorkflowById(projectKey, featureFlagKey, environmentKey, workflowId)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowsApi#getCustomWorkflowById");
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
| **workflowId** | **String**| The workflow ID | |

### Return type

[**CustomWorkflowOutput**](CustomWorkflowOutput.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Workflow response |  -  |

<a name="getFeatureFlagEnvironmentsWorkflows"></a>
# **getFeatureFlagEnvironmentsWorkflows**
> CustomWorkflowsListingOutput getFeatureFlagEnvironmentsWorkflows(projectKey, featureFlagKey, environmentKey).status(status).sort(sort).execute();

Get workflows

Display workflows associated with a feature flag.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WorkflowsApi;
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
    String status = "status_example"; // Filter results by workflow status. Valid status filters are `active`, `completed`, and `failed`.
    String sort = "sort_example"; // A field to sort the items by. Prefix field by a dash ( - ) to sort in descending order. This endpoint supports sorting by `creationDate` or `stopDate`.
    try {
      CustomWorkflowsListingOutput result = client
              .workflows
              .getFeatureFlagEnvironmentsWorkflows(projectKey, featureFlagKey, environmentKey)
              .status(status)
              .sort(sort)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowsApi#getFeatureFlagEnvironmentsWorkflows");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<CustomWorkflowsListingOutput> response = client
              .workflows
              .getFeatureFlagEnvironmentsWorkflows(projectKey, featureFlagKey, environmentKey)
              .status(status)
              .sort(sort)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling WorkflowsApi#getFeatureFlagEnvironmentsWorkflows");
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
| **status** | **String**| Filter results by workflow status. Valid status filters are &#x60;active&#x60;, &#x60;completed&#x60;, and &#x60;failed&#x60;. | [optional] |
| **sort** | **String**| A field to sort the items by. Prefix field by a dash ( - ) to sort in descending order. This endpoint supports sorting by &#x60;creationDate&#x60; or &#x60;stopDate&#x60;. | [optional] |

### Return type

[**CustomWorkflowsListingOutput**](CustomWorkflowsListingOutput.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Workflows collection response |  -  |

