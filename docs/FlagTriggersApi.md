# FlagTriggersApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createTriggerWorkflow**](FlagTriggersApi.md#createTriggerWorkflow) | **POST** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey} | Create flag trigger |
| [**deleteById**](FlagTriggersApi.md#deleteById) | **DELETE** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey}/{id} | Delete flag trigger |
| [**getTriggerById**](FlagTriggersApi.md#getTriggerById) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey}/{id} | Get flag trigger by ID |
| [**listTriggerWorkflows**](FlagTriggersApi.md#listTriggerWorkflows) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey} | List flag triggers |
| [**updateTriggerWorkflowPatch**](FlagTriggersApi.md#updateTriggerWorkflowPatch) | **PATCH** /api/v2/flags/{projectKey}/{featureFlagKey}/triggers/{environmentKey}/{id} | Update flag trigger |


<a name="createTriggerWorkflow"></a>
# **createTriggerWorkflow**
> TriggerWorkflowRep createTriggerWorkflow(projectKey, environmentKey, featureFlagKey, triggerPost).execute();

Create flag trigger

Create a new flag trigger.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FlagTriggersApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String integrationKey = "integrationKey_example"; // The unique identifier of the integration for your trigger. Use <code>generic-trigger</code> for integrations not explicitly supported.
    String projectKey = "projectKey_example"; // The project key
    String environmentKey = "environmentKey_example"; // The environment key
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    String comment = "comment_example"; // Optional comment describing the trigger
    List<Map<String, Object>> instructions = Arrays.asList(new HashMap<>()); // The action to perform when triggering. This should be an array with a single object that looks like <code>{\\\"kind\\\": \\\"flag_action\\\"}</code>. Supported flag actions are <code>turnFlagOn</code> and <code>turnFlagOff</code>.
    try {
      TriggerWorkflowRep result = client
              .flagTriggers
              .createTriggerWorkflow(integrationKey, projectKey, environmentKey, featureFlagKey)
              .comment(comment)
              .instructions(instructions)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getVersion());
      System.out.println(result.getCreationDate());
      System.out.println(result.getMaintainerId());
      System.out.println(result.getMaintainer());
      System.out.println(result.getEnabled());
      System.out.println(result.getIntegrationKey());
      System.out.println(result.getInstructions());
      System.out.println(result.getLastTriggeredAt());
      System.out.println(result.getRecentTriggerBodies());
      System.out.println(result.getTriggerCount());
      System.out.println(result.getTriggerURL());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagTriggersApi#createTriggerWorkflow");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<TriggerWorkflowRep> response = client
              .flagTriggers
              .createTriggerWorkflow(integrationKey, projectKey, environmentKey, featureFlagKey)
              .comment(comment)
              .instructions(instructions)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagTriggersApi#createTriggerWorkflow");
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
| **featureFlagKey** | **String**| The feature flag key | |
| **triggerPost** | [**TriggerPost**](TriggerPost.md)|  | |

### Return type

[**TriggerWorkflowRep**](TriggerWorkflowRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Flag trigger response |  -  |

<a name="deleteById"></a>
# **deleteById**
> deleteById(projectKey, environmentKey, featureFlagKey, id).execute();

Delete flag trigger

Delete a flag trigger by ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FlagTriggersApi;
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
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    String id = "id_example"; // The flag trigger ID
    try {
      client
              .flagTriggers
              .deleteById(projectKey, environmentKey, featureFlagKey, id)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagTriggersApi#deleteById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .flagTriggers
              .deleteById(projectKey, environmentKey, featureFlagKey, id)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagTriggersApi#deleteById");
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
| **featureFlagKey** | **String**| The feature flag key | |
| **id** | **String**| The flag trigger ID | |

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

<a name="getTriggerById"></a>
# **getTriggerById**
> TriggerWorkflowRep getTriggerById(projectKey, featureFlagKey, environmentKey, id).execute();

Get flag trigger by ID

Get a flag trigger by ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FlagTriggersApi;
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
    String id = "id_example"; // The flag trigger ID
    try {
      TriggerWorkflowRep result = client
              .flagTriggers
              .getTriggerById(projectKey, featureFlagKey, environmentKey, id)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getVersion());
      System.out.println(result.getCreationDate());
      System.out.println(result.getMaintainerId());
      System.out.println(result.getMaintainer());
      System.out.println(result.getEnabled());
      System.out.println(result.getIntegrationKey());
      System.out.println(result.getInstructions());
      System.out.println(result.getLastTriggeredAt());
      System.out.println(result.getRecentTriggerBodies());
      System.out.println(result.getTriggerCount());
      System.out.println(result.getTriggerURL());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagTriggersApi#getTriggerById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<TriggerWorkflowRep> response = client
              .flagTriggers
              .getTriggerById(projectKey, featureFlagKey, environmentKey, id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagTriggersApi#getTriggerById");
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
| **id** | **String**| The flag trigger ID | |

### Return type

[**TriggerWorkflowRep**](TriggerWorkflowRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag trigger response |  -  |

<a name="listTriggerWorkflows"></a>
# **listTriggerWorkflows**
> TriggerWorkflowCollectionRep listTriggerWorkflows(projectKey, environmentKey, featureFlagKey).execute();

List flag triggers

Get a list of all flag triggers.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FlagTriggersApi;
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
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    try {
      TriggerWorkflowCollectionRep result = client
              .flagTriggers
              .listTriggerWorkflows(projectKey, environmentKey, featureFlagKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagTriggersApi#listTriggerWorkflows");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<TriggerWorkflowCollectionRep> response = client
              .flagTriggers
              .listTriggerWorkflows(projectKey, environmentKey, featureFlagKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagTriggersApi#listTriggerWorkflows");
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
| **featureFlagKey** | **String**| The feature flag key | |

### Return type

[**TriggerWorkflowCollectionRep**](TriggerWorkflowCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag trigger collection response |  -  |

<a name="updateTriggerWorkflowPatch"></a>
# **updateTriggerWorkflowPatch**
> TriggerWorkflowRep updateTriggerWorkflowPatch(projectKey, environmentKey, featureFlagKey, id, flagTriggerInput).execute();

Update flag trigger

Update a flag trigger. Updating a flag trigger uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://apidocs.launchdarkly.com).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating flag triggers.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating flag triggers&lt;/strong&gt;&lt;/summary&gt;  #### replaceTriggerActionInstructions  Removes the existing trigger action and replaces it with the new instructions.  ##### Parameters  - &#x60;value&#x60;: An array of the new &#x60;kind&#x60;s of actions to perform when triggering. Supported flag actions are &#x60;turnFlagOn&#x60; and &#x60;turnFlagOff&#x60;.  Here&#39;s an example that replaces the existing action with new instructions to turn flag targeting off:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [     {       \&quot;kind\&quot;: \&quot;replaceTriggerActionInstructions\&quot;,       \&quot;value\&quot;: [ {\&quot;kind\&quot;: \&quot;turnFlagOff\&quot;} ]     }   ] } &#x60;&#x60;&#x60;  #### cycleTriggerUrl  Generates a new URL for this trigger. You must update any clients using the trigger to use this new URL.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{ \&quot;kind\&quot;: \&quot;cycleTriggerUrl\&quot; }] } &#x60;&#x60;&#x60;  #### disableTrigger  Disables the trigger. This saves the trigger configuration, but the trigger stops running. To re-enable, use &#x60;enableTrigger&#x60;.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{ \&quot;kind\&quot;: \&quot;disableTrigger\&quot; }] } &#x60;&#x60;&#x60;  #### enableTrigger  Enables the trigger. If you previously disabled the trigger, it begins running again.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{ \&quot;kind\&quot;: \&quot;enableTrigger\&quot; }] } &#x60;&#x60;&#x60;  &lt;/details&gt; 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FlagTriggersApi;
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
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    String id = "id_example"; // The flag trigger ID
    String comment = "comment_example"; // Optional comment describing the update
    List<Map<String, Object>> instructions = Arrays.asList(new HashMap<>()); // The instructions to perform when updating. This should be an array with objects that look like <code>{\\\"kind\\\": \\\"trigger_action\\\"}</code>.
    try {
      TriggerWorkflowRep result = client
              .flagTriggers
              .updateTriggerWorkflowPatch(projectKey, environmentKey, featureFlagKey, id)
              .comment(comment)
              .instructions(instructions)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getVersion());
      System.out.println(result.getCreationDate());
      System.out.println(result.getMaintainerId());
      System.out.println(result.getMaintainer());
      System.out.println(result.getEnabled());
      System.out.println(result.getIntegrationKey());
      System.out.println(result.getInstructions());
      System.out.println(result.getLastTriggeredAt());
      System.out.println(result.getRecentTriggerBodies());
      System.out.println(result.getTriggerCount());
      System.out.println(result.getTriggerURL());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagTriggersApi#updateTriggerWorkflowPatch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<TriggerWorkflowRep> response = client
              .flagTriggers
              .updateTriggerWorkflowPatch(projectKey, environmentKey, featureFlagKey, id)
              .comment(comment)
              .instructions(instructions)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagTriggersApi#updateTriggerWorkflowPatch");
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
| **featureFlagKey** | **String**| The feature flag key | |
| **id** | **String**| The flag trigger ID | |
| **flagTriggerInput** | [**FlagTriggerInput**](FlagTriggerInput.md)|  | |

### Return type

[**TriggerWorkflowRep**](TriggerWorkflowRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag trigger response |  -  |

