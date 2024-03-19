# ScheduledChangesApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createWorkflow**](ScheduledChangesApi.md#createWorkflow) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes | Create scheduled changes workflow |
| [**deleteWorkflow**](ScheduledChangesApi.md#deleteWorkflow) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes/{id} | Delete scheduled changes workflow |
| [**getById**](ScheduledChangesApi.md#getById) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes/{id} | Get a scheduled change |
| [**listChanges**](ScheduledChangesApi.md#listChanges) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes | List scheduled changes |
| [**updateWorkflow**](ScheduledChangesApi.md#updateWorkflow) | **PATCH** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/scheduled-changes/{id} | Update scheduled changes workflow |


<a name="createWorkflow"></a>
# **createWorkflow**
> FeatureFlagScheduledChange createWorkflow(projectKey, featureFlagKey, environmentKey, postFlagScheduledChangesInput).ignoreConflicts(ignoreConflicts).execute();

Create scheduled changes workflow

Create scheduled changes for a feature flag. If the &#x60;ignoreConficts&#x60; query parameter is false and there are conflicts between these instructions and existing scheduled changes, the request will fail. If the parameter is true and there are conflicts, the request will succeed.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ScheduledChangesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    Long executionDate = 56L;
    List<Map<String, Object>> instructions = Arrays.asList(new HashMap<>());
    String projectKey = "projectKey_example"; // The project key
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    String environmentKey = "environmentKey_example"; // The environment key
    String comment = "comment_example"; // Optional comment describing the scheduled changes
    Boolean ignoreConflicts = true; // Whether to succeed (`true`) or fail (`false`) when these instructions conflict with existing scheduled changes
    try {
      FeatureFlagScheduledChange result = client
              .scheduledChanges
              .createWorkflow(executionDate, instructions, projectKey, featureFlagKey, environmentKey)
              .comment(comment)
              .ignoreConflicts(ignoreConflicts)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getCreationDate());
      System.out.println(result.getMaintainerId());
      System.out.println(result.getVersion());
      System.out.println(result.getExecutionDate());
      System.out.println(result.getInstructions());
      System.out.println(result.getConflicts());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling ScheduledChangesApi#createWorkflow");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FeatureFlagScheduledChange> response = client
              .scheduledChanges
              .createWorkflow(executionDate, instructions, projectKey, featureFlagKey, environmentKey)
              .comment(comment)
              .ignoreConflicts(ignoreConflicts)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ScheduledChangesApi#createWorkflow");
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
| **postFlagScheduledChangesInput** | [**PostFlagScheduledChangesInput**](PostFlagScheduledChangesInput.md)|  | |
| **ignoreConflicts** | **Boolean**| Whether to succeed (&#x60;true&#x60;) or fail (&#x60;false&#x60;) when these instructions conflict with existing scheduled changes | [optional] |

### Return type

[**FeatureFlagScheduledChange**](FeatureFlagScheduledChange.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Scheduled changes response |  -  |

<a name="deleteWorkflow"></a>
# **deleteWorkflow**
> deleteWorkflow(projectKey, featureFlagKey, environmentKey, id).execute();

Delete scheduled changes workflow

Delete a scheduled changes workflow.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ScheduledChangesApi;
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
    String id = "id_example"; // The scheduled change id
    try {
      client
              .scheduledChanges
              .deleteWorkflow(projectKey, featureFlagKey, environmentKey, id)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling ScheduledChangesApi#deleteWorkflow");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .scheduledChanges
              .deleteWorkflow(projectKey, featureFlagKey, environmentKey, id)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling ScheduledChangesApi#deleteWorkflow");
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
| **id** | **String**| The scheduled change id | |

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

<a name="getById"></a>
# **getById**
> FeatureFlagScheduledChange getById(projectKey, featureFlagKey, environmentKey, id).execute();

Get a scheduled change

Get a scheduled change that will be applied to the feature flag by ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ScheduledChangesApi;
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
    String id = "id_example"; // The scheduled change id
    try {
      FeatureFlagScheduledChange result = client
              .scheduledChanges
              .getById(projectKey, featureFlagKey, environmentKey, id)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getCreationDate());
      System.out.println(result.getMaintainerId());
      System.out.println(result.getVersion());
      System.out.println(result.getExecutionDate());
      System.out.println(result.getInstructions());
      System.out.println(result.getConflicts());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling ScheduledChangesApi#getById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FeatureFlagScheduledChange> response = client
              .scheduledChanges
              .getById(projectKey, featureFlagKey, environmentKey, id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ScheduledChangesApi#getById");
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
| **id** | **String**| The scheduled change id | |

### Return type

[**FeatureFlagScheduledChange**](FeatureFlagScheduledChange.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Scheduled changes response |  -  |

<a name="listChanges"></a>
# **listChanges**
> FeatureFlagScheduledChanges listChanges(projectKey, featureFlagKey, environmentKey).execute();

List scheduled changes

Get a list of scheduled changes that will be applied to the feature flag.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ScheduledChangesApi;
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
      FeatureFlagScheduledChanges result = client
              .scheduledChanges
              .listChanges(projectKey, featureFlagKey, environmentKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling ScheduledChangesApi#listChanges");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FeatureFlagScheduledChanges> response = client
              .scheduledChanges
              .listChanges(projectKey, featureFlagKey, environmentKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ScheduledChangesApi#listChanges");
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

[**FeatureFlagScheduledChanges**](FeatureFlagScheduledChanges.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Scheduled changes collection response |  -  |

<a name="updateWorkflow"></a>
# **updateWorkflow**
> FeatureFlagScheduledChange updateWorkflow(projectKey, featureFlagKey, environmentKey, id, flagScheduledChangesInput).ignoreConflicts(ignoreConflicts).execute();

Update scheduled changes workflow

 Update a scheduled change, overriding existing instructions with the new ones. Updating a scheduled change uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://apidocs.launchdarkly.com).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating scheduled changes.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating scheduled changes&lt;/strong&gt;&lt;/summary&gt;  #### deleteScheduledChange  Removes the scheduled change.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{ \&quot;kind\&quot;: \&quot;deleteScheduledChange\&quot; }] } &#x60;&#x60;&#x60;  #### replaceScheduledChangesInstructions  Removes the existing scheduled changes and replaces them with the new instructions.  ##### Parameters  - &#x60;value&#x60;: An array of the new actions to perform when the execution date for these scheduled changes arrives. Supported scheduled actions are &#x60;turnFlagOn&#x60; and &#x60;turnFlagOff&#x60;.  Here&#39;s an example that replaces the scheduled changes with new instructions to turn flag targeting off:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceScheduledChangesInstructions\&quot;,       \&quot;value\&quot;: [ {\&quot;kind\&quot;: \&quot;turnFlagOff\&quot;} ]     }   ] } &#x60;&#x60;&#x60;  #### updateScheduledChangesExecutionDate  Updates the execution date for the scheduled changes.  ##### Parameters  - &#x60;value&#x60;: the new execution date, in Unix milliseconds.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;updateScheduledChangesExecutionDate\&quot;,       \&quot;value\&quot;: 1754092860000     }   ] } &#x60;&#x60;&#x60;  &lt;/details&gt; 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ScheduledChangesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    List<Map<String, Object>> instructions = Arrays.asList(new HashMap<>());
    String projectKey = "projectKey_example"; // The project key
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    String environmentKey = "environmentKey_example"; // The environment key
    String id = "id_example"; // The scheduled change ID
    String comment = "comment_example"; // Optional comment describing the update to the scheduled changes
    Boolean ignoreConflicts = true; // Whether to succeed (`true`) or fail (`false`) when these new instructions conflict with existing scheduled changes
    try {
      FeatureFlagScheduledChange result = client
              .scheduledChanges
              .updateWorkflow(instructions, projectKey, featureFlagKey, environmentKey, id)
              .comment(comment)
              .ignoreConflicts(ignoreConflicts)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getCreationDate());
      System.out.println(result.getMaintainerId());
      System.out.println(result.getVersion());
      System.out.println(result.getExecutionDate());
      System.out.println(result.getInstructions());
      System.out.println(result.getConflicts());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling ScheduledChangesApi#updateWorkflow");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FeatureFlagScheduledChange> response = client
              .scheduledChanges
              .updateWorkflow(instructions, projectKey, featureFlagKey, environmentKey, id)
              .comment(comment)
              .ignoreConflicts(ignoreConflicts)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ScheduledChangesApi#updateWorkflow");
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
| **id** | **String**| The scheduled change ID | |
| **flagScheduledChangesInput** | [**FlagScheduledChangesInput**](FlagScheduledChangesInput.md)|  | |
| **ignoreConflicts** | **Boolean**| Whether to succeed (&#x60;true&#x60;) or fail (&#x60;false&#x60;) when these new instructions conflict with existing scheduled changes | [optional] |

### Return type

[**FeatureFlagScheduledChange**](FeatureFlagScheduledChange.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Scheduled changes response |  -  |

