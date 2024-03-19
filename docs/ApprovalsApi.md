# ApprovalsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**applyRequestFlag**](ApprovalsApi.md#applyRequestFlag) | **POST** /api/v2/approval-requests/{id}/apply | Apply approval request |
| [**applyRequestFlag_0**](ApprovalsApi.md#applyRequestFlag_0) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id}/apply | Apply approval request for a flag |
| [**createFlagCopyRequest**](ApprovalsApi.md#createFlagCopyRequest) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests-flag-copy | Create approval request to copy flag configurations across environments |
| [**createRequestFlag**](ApprovalsApi.md#createRequestFlag) | **POST** /api/v2/approval-requests | Create approval request |
| [**createRequestFlag_0**](ApprovalsApi.md#createRequestFlag_0) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests | Create approval request for a flag |
| [**deleteApprovalRequestFlag**](ApprovalsApi.md#deleteApprovalRequestFlag) | **DELETE** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id} | Delete approval request for a flag |
| [**deleteRequest**](ApprovalsApi.md#deleteRequest) | **DELETE** /api/v2/approval-requests/{id} | Delete approval request |
| [**getRequestById**](ApprovalsApi.md#getRequestById) | **GET** /api/v2/approval-requests/{id} | Get approval request |
| [**list**](ApprovalsApi.md#list) | **GET** /api/v2/approval-requests | List approval requests |
| [**listRequestsForFlag**](ApprovalsApi.md#listRequestsForFlag) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests | List approval requests for a flag |
| [**reviewFlagRequest**](ApprovalsApi.md#reviewFlagRequest) | **POST** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id}/reviews | Review approval request for a flag |
| [**reviewRequest**](ApprovalsApi.md#reviewRequest) | **POST** /api/v2/approval-requests/{id}/reviews | Review approval request |
| [**singleRequest**](ApprovalsApi.md#singleRequest) | **GET** /api/v2/projects/{projectKey}/flags/{featureFlagKey}/environments/{environmentKey}/approval-requests/{id} | Get approval request for a flag |


<a name="applyRequestFlag"></a>
# **applyRequestFlag**
> ApprovalRequestResponse applyRequestFlag(id, postApprovalRequestApplyRequest).execute();

Apply approval request

Apply an approval request that has been approved.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApprovalsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String id = "id_example"; // The feature flag approval request ID
    String comment = "comment_example"; // Optional comment about the approval request
    try {
      ApprovalRequestResponse result = client
              .approvals
              .applyRequestFlag(id)
              .comment(comment)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getVersion());
      System.out.println(result.getCreationDate());
      System.out.println(result.getServiceKind());
      System.out.println(result.getRequestorId());
      System.out.println(result.getReviewStatus());
      System.out.println(result.getAllReviews());
      System.out.println(result.getNotifyMemberIds());
      System.out.println(result.getAppliedDate());
      System.out.println(result.getAppliedByMemberId());
      System.out.println(result.getAppliedByServiceTokenId());
      System.out.println(result.getStatus());
      System.out.println(result.getInstructions());
      System.out.println(result.getConflicts());
      System.out.println(result.getLinks());
      System.out.println(result.getExecutionDate());
      System.out.println(result.getOperatingOnId());
      System.out.println(result.getIntegrationMetadata());
      System.out.println(result.getSource());
      System.out.println(result.getCustomWorkflowMetadata());
      System.out.println(result.getResourceId());
      System.out.println(result.getApprovalSettings());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#applyRequestFlag");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ApprovalRequestResponse> response = client
              .approvals
              .applyRequestFlag(id)
              .comment(comment)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#applyRequestFlag");
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
| **id** | **String**| The feature flag approval request ID | |
| **postApprovalRequestApplyRequest** | [**PostApprovalRequestApplyRequest**](PostApprovalRequestApplyRequest.md)|  | |

### Return type

[**ApprovalRequestResponse**](ApprovalRequestResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Approval request apply response |  -  |

<a name="applyRequestFlag_0"></a>
# **applyRequestFlag_0**
> FlagConfigApprovalRequestResponse applyRequestFlag_0(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestApplyRequest).execute();

Apply approval request for a flag

Apply an approval request that has been approved.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApprovalsApi;
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
    String id = "id_example"; // The feature flag approval request ID
    String comment = "comment_example"; // Optional comment about the approval request
    try {
      FlagConfigApprovalRequestResponse result = client
              .approvals
              .applyRequestFlag_0(projectKey, featureFlagKey, environmentKey, id)
              .comment(comment)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getVersion());
      System.out.println(result.getCreationDate());
      System.out.println(result.getServiceKind());
      System.out.println(result.getRequestorId());
      System.out.println(result.getReviewStatus());
      System.out.println(result.getAllReviews());
      System.out.println(result.getNotifyMemberIds());
      System.out.println(result.getAppliedDate());
      System.out.println(result.getAppliedByMemberId());
      System.out.println(result.getAppliedByServiceTokenId());
      System.out.println(result.getStatus());
      System.out.println(result.getInstructions());
      System.out.println(result.getConflicts());
      System.out.println(result.getLinks());
      System.out.println(result.getExecutionDate());
      System.out.println(result.getOperatingOnId());
      System.out.println(result.getIntegrationMetadata());
      System.out.println(result.getSource());
      System.out.println(result.getCustomWorkflowMetadata());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#applyRequestFlag_0");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FlagConfigApprovalRequestResponse> response = client
              .approvals
              .applyRequestFlag_0(projectKey, featureFlagKey, environmentKey, id)
              .comment(comment)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#applyRequestFlag_0");
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
| **id** | **String**| The feature flag approval request ID | |
| **postApprovalRequestApplyRequest** | [**PostApprovalRequestApplyRequest**](PostApprovalRequestApplyRequest.md)|  | |

### Return type

[**FlagConfigApprovalRequestResponse**](FlagConfigApprovalRequestResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Approval request apply response |  -  |

<a name="createFlagCopyRequest"></a>
# **createFlagCopyRequest**
> FlagConfigApprovalRequestResponse createFlagCopyRequest(projectKey, featureFlagKey, environmentKey, createCopyFlagConfigApprovalRequestRequest).execute();

Create approval request to copy flag configurations across environments

Create an approval request to copy a feature flag&#39;s configuration across environments.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApprovalsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String description = "description_example"; // A brief description of your changes
    SourceFlag source = new SourceFlag();
    String projectKey = "projectKey_example"; // The project key
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    String environmentKey = "environmentKey_example"; // The environment key for the target environment
    String comment = "comment_example"; // Optional comment describing the approval request
    List<String> notifyMemberIds = Arrays.asList(); // An array of member IDs. These members are notified to review the approval request.
    List<String> notifyTeamKeys = Arrays.asList(); // An array of team keys. The members of these teams are notified to review the approval request.
    List<String> includedActions = Arrays.asList(); // Optional list of the flag changes to copy from the source environment to the target environment. You may include either <code>includedActions</code> or <code>excludedActions</code>, but not both. If neither are included, then all flag changes will be copied.
    List<String> excludedActions = Arrays.asList(); // Optional list of the flag changes NOT to copy from the source environment to the target environment. You may include either <code>includedActions</code> or <code>excludedActions</code>, but not both. If neither are included, then all flag changes will be copied.
    try {
      FlagConfigApprovalRequestResponse result = client
              .approvals
              .createFlagCopyRequest(description, source, projectKey, featureFlagKey, environmentKey)
              .comment(comment)
              .notifyMemberIds(notifyMemberIds)
              .notifyTeamKeys(notifyTeamKeys)
              .includedActions(includedActions)
              .excludedActions(excludedActions)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getVersion());
      System.out.println(result.getCreationDate());
      System.out.println(result.getServiceKind());
      System.out.println(result.getRequestorId());
      System.out.println(result.getReviewStatus());
      System.out.println(result.getAllReviews());
      System.out.println(result.getNotifyMemberIds());
      System.out.println(result.getAppliedDate());
      System.out.println(result.getAppliedByMemberId());
      System.out.println(result.getAppliedByServiceTokenId());
      System.out.println(result.getStatus());
      System.out.println(result.getInstructions());
      System.out.println(result.getConflicts());
      System.out.println(result.getLinks());
      System.out.println(result.getExecutionDate());
      System.out.println(result.getOperatingOnId());
      System.out.println(result.getIntegrationMetadata());
      System.out.println(result.getSource());
      System.out.println(result.getCustomWorkflowMetadata());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#createFlagCopyRequest");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FlagConfigApprovalRequestResponse> response = client
              .approvals
              .createFlagCopyRequest(description, source, projectKey, featureFlagKey, environmentKey)
              .comment(comment)
              .notifyMemberIds(notifyMemberIds)
              .notifyTeamKeys(notifyTeamKeys)
              .includedActions(includedActions)
              .excludedActions(excludedActions)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#createFlagCopyRequest");
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
| **environmentKey** | **String**| The environment key for the target environment | |
| **createCopyFlagConfigApprovalRequestRequest** | [**CreateCopyFlagConfigApprovalRequestRequest**](CreateCopyFlagConfigApprovalRequestRequest.md)|  | |

### Return type

[**FlagConfigApprovalRequestResponse**](FlagConfigApprovalRequestResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Approval request response |  -  |

<a name="createRequestFlag"></a>
# **createRequestFlag**
> ApprovalRequestResponse createRequestFlag(createApprovalRequestRequest).execute();

Create approval request

Create an approval request.  This endpoint currently supports creating an approval request for a flag across all environments with the following instructions:  - &#x60;addVariation&#x60; - &#x60;removeVariation&#x60; - &#x60;updateVariation&#x60; - &#x60;updateDefaultVariation&#x60;  For details on using these instructions, read [Update feature flag](https://apidocs.launchdarkly.com).  To create an approval for a flag specific to an environment, use [Create approval request for a flag](https://apidocs.launchdarkly.com). 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApprovalsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String description = "description_example"; // A brief description of the changes you're requesting
    String resourceId = "resourceId_example"; // String representation of a resource
    List<Map<String, Object>> instructions = Arrays.asList(new HashMap<>());
    String comment = "comment_example"; // Optional comment describing the approval request
    List<String> notifyMemberIds = Arrays.asList(); // An array of member IDs. These members are notified to review the approval request.
    List<String> notifyTeamKeys = Arrays.asList(); // An array of team keys. The members of these teams are notified to review the approval request.
    Map<String, Object> integrationConfig = new HashMap();
    try {
      ApprovalRequestResponse result = client
              .approvals
              .createRequestFlag(description, resourceId, instructions)
              .comment(comment)
              .notifyMemberIds(notifyMemberIds)
              .notifyTeamKeys(notifyTeamKeys)
              .integrationConfig(integrationConfig)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getVersion());
      System.out.println(result.getCreationDate());
      System.out.println(result.getServiceKind());
      System.out.println(result.getRequestorId());
      System.out.println(result.getReviewStatus());
      System.out.println(result.getAllReviews());
      System.out.println(result.getNotifyMemberIds());
      System.out.println(result.getAppliedDate());
      System.out.println(result.getAppliedByMemberId());
      System.out.println(result.getAppliedByServiceTokenId());
      System.out.println(result.getStatus());
      System.out.println(result.getInstructions());
      System.out.println(result.getConflicts());
      System.out.println(result.getLinks());
      System.out.println(result.getExecutionDate());
      System.out.println(result.getOperatingOnId());
      System.out.println(result.getIntegrationMetadata());
      System.out.println(result.getSource());
      System.out.println(result.getCustomWorkflowMetadata());
      System.out.println(result.getResourceId());
      System.out.println(result.getApprovalSettings());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#createRequestFlag");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ApprovalRequestResponse> response = client
              .approvals
              .createRequestFlag(description, resourceId, instructions)
              .comment(comment)
              .notifyMemberIds(notifyMemberIds)
              .notifyTeamKeys(notifyTeamKeys)
              .integrationConfig(integrationConfig)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#createRequestFlag");
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
| **createApprovalRequestRequest** | [**CreateApprovalRequestRequest**](CreateApprovalRequestRequest.md)|  | |

### Return type

[**ApprovalRequestResponse**](ApprovalRequestResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Approval request response |  -  |

<a name="createRequestFlag_0"></a>
# **createRequestFlag_0**
> FlagConfigApprovalRequestResponse createRequestFlag_0(projectKey, featureFlagKey, environmentKey, createFlagConfigApprovalRequestRequest).execute();

Create approval request for a flag

Create an approval request for a feature flag.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApprovalsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String description = "description_example"; // A brief description of the changes you're requesting
    List<Map<String, Object>> instructions = Arrays.asList(new HashMap<>());
    String projectKey = "projectKey_example"; // The project key
    String featureFlagKey = "featureFlagKey_example"; // The feature flag key
    String environmentKey = "environmentKey_example"; // The environment key
    String comment = "comment_example"; // Optional comment describing the approval request
    List<String> notifyMemberIds = Arrays.asList(); // An array of member IDs. These members are notified to review the approval request.
    List<String> notifyTeamKeys = Arrays.asList(); // An array of team keys. The members of these teams are notified to review the approval request.
    Long executionDate = 56L;
    String operatingOnId = "operatingOnId_example"; // The ID of a scheduled change. Include this if your <code>instructions</code> include editing or deleting a scheduled change.
    Map<String, Object> integrationConfig = new HashMap();
    try {
      FlagConfigApprovalRequestResponse result = client
              .approvals
              .createRequestFlag_0(description, instructions, projectKey, featureFlagKey, environmentKey)
              .comment(comment)
              .notifyMemberIds(notifyMemberIds)
              .notifyTeamKeys(notifyTeamKeys)
              .executionDate(executionDate)
              .operatingOnId(operatingOnId)
              .integrationConfig(integrationConfig)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getVersion());
      System.out.println(result.getCreationDate());
      System.out.println(result.getServiceKind());
      System.out.println(result.getRequestorId());
      System.out.println(result.getReviewStatus());
      System.out.println(result.getAllReviews());
      System.out.println(result.getNotifyMemberIds());
      System.out.println(result.getAppliedDate());
      System.out.println(result.getAppliedByMemberId());
      System.out.println(result.getAppliedByServiceTokenId());
      System.out.println(result.getStatus());
      System.out.println(result.getInstructions());
      System.out.println(result.getConflicts());
      System.out.println(result.getLinks());
      System.out.println(result.getExecutionDate());
      System.out.println(result.getOperatingOnId());
      System.out.println(result.getIntegrationMetadata());
      System.out.println(result.getSource());
      System.out.println(result.getCustomWorkflowMetadata());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#createRequestFlag_0");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FlagConfigApprovalRequestResponse> response = client
              .approvals
              .createRequestFlag_0(description, instructions, projectKey, featureFlagKey, environmentKey)
              .comment(comment)
              .notifyMemberIds(notifyMemberIds)
              .notifyTeamKeys(notifyTeamKeys)
              .executionDate(executionDate)
              .operatingOnId(operatingOnId)
              .integrationConfig(integrationConfig)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#createRequestFlag_0");
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
| **createFlagConfigApprovalRequestRequest** | [**CreateFlagConfigApprovalRequestRequest**](CreateFlagConfigApprovalRequestRequest.md)|  | |

### Return type

[**FlagConfigApprovalRequestResponse**](FlagConfigApprovalRequestResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Approval request response |  -  |

<a name="deleteApprovalRequestFlag"></a>
# **deleteApprovalRequestFlag**
> deleteApprovalRequestFlag(projectKey, featureFlagKey, environmentKey, id).execute();

Delete approval request for a flag

Delete an approval request for a feature flag.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApprovalsApi;
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
    String id = "id_example"; // The feature flag approval request ID
    try {
      client
              .approvals
              .deleteApprovalRequestFlag(projectKey, featureFlagKey, environmentKey, id)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#deleteApprovalRequestFlag");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .approvals
              .deleteApprovalRequestFlag(projectKey, featureFlagKey, environmentKey, id)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#deleteApprovalRequestFlag");
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
| **id** | **String**| The feature flag approval request ID | |

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

<a name="deleteRequest"></a>
# **deleteRequest**
> deleteRequest(id).execute();

Delete approval request

Delete an approval request.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApprovalsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String id = "id_example"; // The approval request ID
    try {
      client
              .approvals
              .deleteRequest(id)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#deleteRequest");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .approvals
              .deleteRequest(id)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#deleteRequest");
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
| **id** | **String**| The approval request ID | |

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

<a name="getRequestById"></a>
# **getRequestById**
> ExpandableApprovalRequestResponse getRequestById(id).expand(expand).execute();

Get approval request

Get an approval request by approval request ID.  ### Expanding approval response  LaunchDarkly supports the &#x60;expand&#x60; query param to include additional fields in the response, with the following fields:  - &#x60;flag&#x60; includes the flag the approval request belongs to - &#x60;project&#x60; includes the project the approval request belongs to - &#x60;environments&#x60; includes the environments the approval request relates to  For example, &#x60;expand&#x3D;project,flag&#x60; includes the &#x60;project&#x60; and &#x60;flag&#x60; fields in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApprovalsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String id = "id_example"; // The approval request ID
    String expand = "expand_example"; // A comma-separated list of fields to expand in the response. Supported fields are explained above.
    try {
      ExpandableApprovalRequestResponse result = client
              .approvals
              .getRequestById(id)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getVersion());
      System.out.println(result.getCreationDate());
      System.out.println(result.getServiceKind());
      System.out.println(result.getRequestorId());
      System.out.println(result.getReviewStatus());
      System.out.println(result.getAllReviews());
      System.out.println(result.getNotifyMemberIds());
      System.out.println(result.getAppliedDate());
      System.out.println(result.getAppliedByMemberId());
      System.out.println(result.getAppliedByServiceTokenId());
      System.out.println(result.getStatus());
      System.out.println(result.getInstructions());
      System.out.println(result.getConflicts());
      System.out.println(result.getLinks());
      System.out.println(result.getExecutionDate());
      System.out.println(result.getOperatingOnId());
      System.out.println(result.getIntegrationMetadata());
      System.out.println(result.getSource());
      System.out.println(result.getCustomWorkflowMetadata());
      System.out.println(result.getResourceId());
      System.out.println(result.getApprovalSettings());
      System.out.println(result.getProject());
      System.out.println(result.getEnvironments());
      System.out.println(result.getFlag());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#getRequestById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ExpandableApprovalRequestResponse> response = client
              .approvals
              .getRequestById(id)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#getRequestById");
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
| **id** | **String**| The approval request ID | |
| **expand** | **String**| A comma-separated list of fields to expand in the response. Supported fields are explained above. | [optional] |

### Return type

[**ExpandableApprovalRequestResponse**](ExpandableApprovalRequestResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Approval request response |  -  |

<a name="list"></a>
# **list**
> ExpandableApprovalRequestsResponse list().filter(filter).expand(expand).limit(limit).offset(offset).execute();

List approval requests

Get all approval requests.  ### Filtering approvals  LaunchDarkly supports the &#x60;filter&#x60; query param for filtering, with the following fields:  - &#x60;notifyMemberIds&#x60; filters for only approvals that are assigned to a member in the specified list. For example: &#x60;filter&#x3D;notifyMemberIds anyOf [\&quot;memberId1\&quot;, \&quot;memberId2\&quot;]&#x60;. - &#x60;requestorId&#x60; filters for only approvals that correspond to the ID of the member who requested the approval. For example: &#x60;filter&#x3D;requestorId equals 457034721476302714390214&#x60;. - &#x60;resourceId&#x60; filters for only approvals that correspond to the the specified resource identifier. For example: &#x60;filter&#x3D;resourceId equals proj/my-project:env/my-environment:flag/my-flag&#x60;. - &#x60;reviewStatus&#x60; filters for only approvals which correspond to the review status in the specified list. The possible values are &#x60;approved&#x60;, &#x60;declined&#x60;, and &#x60;pending&#x60;. For example: &#x60;filter&#x3D;reviewStatus anyOf [\&quot;pending\&quot;, \&quot;approved\&quot;]&#x60;. - &#x60;status&#x60; filters for only approvals which correspond to the status in the specified list. The possible values are &#x60;pending&#x60;, &#x60;scheduled&#x60;, &#x60;failed&#x60;, and &#x60;completed&#x60;. For example: &#x60;filter&#x3D;status anyOf [\&quot;pending\&quot;, \&quot;scheduled\&quot;]&#x60;.  You can also apply multiple filters at once. For example, setting &#x60;filter&#x3D;projectKey equals my-project, reviewStatus anyOf [\&quot;pending\&quot;,\&quot;approved\&quot;]&#x60; matches approval requests which correspond to the &#x60;my-project&#x60; project key, and a review status of either &#x60;pending&#x60; or &#x60;approved&#x60;.  ### Expanding approval response  LaunchDarkly supports the &#x60;expand&#x60; query param to include additional fields in the response, with the following fields:  - &#x60;flag&#x60; includes the flag the approval request belongs to - &#x60;project&#x60; includes the project the approval request belongs to - &#x60;environments&#x60; includes the environments the approval request relates to  For example, &#x60;expand&#x3D;project,flag&#x60; includes the &#x60;project&#x60; and &#x60;flag&#x60; fields in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApprovalsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String filter = "filter_example"; // A comma-separated list of filters. Each filter is of the form `field operator value`. Supported fields are explained above.
    String expand = "expand_example"; // A comma-separated list of fields to expand in the response. Supported fields are explained above.
    Long limit = 56L; // The number of approvals to return. Defaults to 20. Maximum limit is 200.
    Long offset = 56L; // Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    try {
      ExpandableApprovalRequestsResponse result = client
              .approvals
              .list()
              .filter(filter)
              .expand(expand)
              .limit(limit)
              .offset(offset)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getTotalCount());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#list");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ExpandableApprovalRequestsResponse> response = client
              .approvals
              .list()
              .filter(filter)
              .expand(expand)
              .limit(limit)
              .offset(offset)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#list");
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
| **filter** | **String**| A comma-separated list of filters. Each filter is of the form &#x60;field operator value&#x60;. Supported fields are explained above. | [optional] |
| **expand** | **String**| A comma-separated list of fields to expand in the response. Supported fields are explained above. | [optional] |
| **limit** | **Long**| The number of approvals to return. Defaults to 20. Maximum limit is 200. | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |

### Return type

[**ExpandableApprovalRequestsResponse**](ExpandableApprovalRequestsResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Approval request collection response |  -  |

<a name="listRequestsForFlag"></a>
# **listRequestsForFlag**
> FlagConfigApprovalRequestsResponse listRequestsForFlag(projectKey, featureFlagKey, environmentKey).execute();

List approval requests for a flag

Get all approval requests for a feature flag.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApprovalsApi;
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
      FlagConfigApprovalRequestsResponse result = client
              .approvals
              .listRequestsForFlag(projectKey, featureFlagKey, environmentKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#listRequestsForFlag");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FlagConfigApprovalRequestsResponse> response = client
              .approvals
              .listRequestsForFlag(projectKey, featureFlagKey, environmentKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#listRequestsForFlag");
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

[**FlagConfigApprovalRequestsResponse**](FlagConfigApprovalRequestsResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Approval request collection response |  -  |

<a name="reviewFlagRequest"></a>
# **reviewFlagRequest**
> FlagConfigApprovalRequestResponse reviewFlagRequest(projectKey, featureFlagKey, environmentKey, id, postApprovalRequestReviewRequest).execute();

Review approval request for a flag

Review an approval request by approving or denying changes.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApprovalsApi;
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
    String id = "id_example"; // The feature flag approval request ID
    String kind = "approve"; // The type of review for this approval request
    String comment = "comment_example"; // Optional comment about the approval request
    try {
      FlagConfigApprovalRequestResponse result = client
              .approvals
              .reviewFlagRequest(projectKey, featureFlagKey, environmentKey, id)
              .kind(kind)
              .comment(comment)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getVersion());
      System.out.println(result.getCreationDate());
      System.out.println(result.getServiceKind());
      System.out.println(result.getRequestorId());
      System.out.println(result.getReviewStatus());
      System.out.println(result.getAllReviews());
      System.out.println(result.getNotifyMemberIds());
      System.out.println(result.getAppliedDate());
      System.out.println(result.getAppliedByMemberId());
      System.out.println(result.getAppliedByServiceTokenId());
      System.out.println(result.getStatus());
      System.out.println(result.getInstructions());
      System.out.println(result.getConflicts());
      System.out.println(result.getLinks());
      System.out.println(result.getExecutionDate());
      System.out.println(result.getOperatingOnId());
      System.out.println(result.getIntegrationMetadata());
      System.out.println(result.getSource());
      System.out.println(result.getCustomWorkflowMetadata());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#reviewFlagRequest");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FlagConfigApprovalRequestResponse> response = client
              .approvals
              .reviewFlagRequest(projectKey, featureFlagKey, environmentKey, id)
              .kind(kind)
              .comment(comment)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#reviewFlagRequest");
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
| **id** | **String**| The feature flag approval request ID | |
| **postApprovalRequestReviewRequest** | [**PostApprovalRequestReviewRequest**](PostApprovalRequestReviewRequest.md)|  | |

### Return type

[**FlagConfigApprovalRequestResponse**](FlagConfigApprovalRequestResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Approval request review response |  -  |

<a name="reviewRequest"></a>
# **reviewRequest**
> ApprovalRequestResponse reviewRequest(id, postApprovalRequestReviewRequest).execute();

Review approval request

Review an approval request by approving or denying changes.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApprovalsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String id = "id_example"; // The approval request ID
    String kind = "approve"; // The type of review for this approval request
    String comment = "comment_example"; // Optional comment about the approval request
    try {
      ApprovalRequestResponse result = client
              .approvals
              .reviewRequest(id)
              .kind(kind)
              .comment(comment)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getVersion());
      System.out.println(result.getCreationDate());
      System.out.println(result.getServiceKind());
      System.out.println(result.getRequestorId());
      System.out.println(result.getReviewStatus());
      System.out.println(result.getAllReviews());
      System.out.println(result.getNotifyMemberIds());
      System.out.println(result.getAppliedDate());
      System.out.println(result.getAppliedByMemberId());
      System.out.println(result.getAppliedByServiceTokenId());
      System.out.println(result.getStatus());
      System.out.println(result.getInstructions());
      System.out.println(result.getConflicts());
      System.out.println(result.getLinks());
      System.out.println(result.getExecutionDate());
      System.out.println(result.getOperatingOnId());
      System.out.println(result.getIntegrationMetadata());
      System.out.println(result.getSource());
      System.out.println(result.getCustomWorkflowMetadata());
      System.out.println(result.getResourceId());
      System.out.println(result.getApprovalSettings());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#reviewRequest");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ApprovalRequestResponse> response = client
              .approvals
              .reviewRequest(id)
              .kind(kind)
              .comment(comment)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#reviewRequest");
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
| **id** | **String**| The approval request ID | |
| **postApprovalRequestReviewRequest** | [**PostApprovalRequestReviewRequest**](PostApprovalRequestReviewRequest.md)|  | |

### Return type

[**ApprovalRequestResponse**](ApprovalRequestResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Approval request review response |  -  |

<a name="singleRequest"></a>
# **singleRequest**
> FlagConfigApprovalRequestResponse singleRequest(projectKey, featureFlagKey, environmentKey, id).execute();

Get approval request for a flag

Get a single approval request for a feature flag.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApprovalsApi;
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
    String id = "id_example"; // The feature flag approval request ID
    try {
      FlagConfigApprovalRequestResponse result = client
              .approvals
              .singleRequest(projectKey, featureFlagKey, environmentKey, id)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getVersion());
      System.out.println(result.getCreationDate());
      System.out.println(result.getServiceKind());
      System.out.println(result.getRequestorId());
      System.out.println(result.getReviewStatus());
      System.out.println(result.getAllReviews());
      System.out.println(result.getNotifyMemberIds());
      System.out.println(result.getAppliedDate());
      System.out.println(result.getAppliedByMemberId());
      System.out.println(result.getAppliedByServiceTokenId());
      System.out.println(result.getStatus());
      System.out.println(result.getInstructions());
      System.out.println(result.getConflicts());
      System.out.println(result.getLinks());
      System.out.println(result.getExecutionDate());
      System.out.println(result.getOperatingOnId());
      System.out.println(result.getIntegrationMetadata());
      System.out.println(result.getSource());
      System.out.println(result.getCustomWorkflowMetadata());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#singleRequest");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FlagConfigApprovalRequestResponse> response = client
              .approvals
              .singleRequest(projectKey, featureFlagKey, environmentKey, id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApprovalsApi#singleRequest");
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
| **id** | **String**| The feature flag approval request ID | |

### Return type

[**FlagConfigApprovalRequestResponse**](FlagConfigApprovalRequestResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Approval request response |  -  |

