# InsightsDeploymentsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createDeploymentEvent**](InsightsDeploymentsBetaApi.md#createDeploymentEvent) | **POST** /api/v2/engineering-insights/deployment-events | Create deployment event |
| [**getDeploymentById**](InsightsDeploymentsBetaApi.md#getDeploymentById) | **GET** /api/v2/engineering-insights/deployments/{deploymentID} | Get deployment |
| [**listDeployments**](InsightsDeploymentsBetaApi.md#listDeployments) | **GET** /api/v2/engineering-insights/deployments | List deployments |
| [**updateDeploymentById**](InsightsDeploymentsBetaApi.md#updateDeploymentById) | **PATCH** /api/v2/engineering-insights/deployments/{deploymentID} | Update deployment |


<a name="createDeploymentEvent"></a>
# **createDeploymentEvent**
> createDeploymentEvent(postDeploymentEventInput).execute();

Create deployment event

Create deployment event

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsDeploymentsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String version = "version_example"; // The application version. You can set the application version to any string that includes only letters, numbers, periods (<code>.</code>), hyphens (<code>-</code>), or underscores (<code>_</code>).<br/><br/>We recommend setting the application version to at least the first seven characters of the SHA or to the tag of the GitHub commit for this deployment.
    String projectKey = "projectKey_example"; // The project key
    String environmentKey = "environmentKey_example"; // The environment key
    String applicationKey = "applicationKey_example"; // The application key. This defines the granularity at which you want to view your insights metrics. Typically it is the name of one of the GitHub repositories that you use in this project.<br/><br/>LaunchDarkly automatically creates a new application each time you send a unique application key.
    String eventType = "started"; // The event type
    String applicationName = "applicationName_example"; // The application name. This defines how the application is displayed
    String applicationKind = "server"; // The kind of application. Default: <code>server</code>
    String versionName = "versionName_example"; // The version name. This defines how the version is displayed
    Long eventTime = 56L;
    Map<String, Object> eventMetadata = new HashMap(); // A JSON object containing metadata about the event
    Map<String, Object> deploymentMetadata = new HashMap(); // A JSON object containing metadata about the deployment
    try {
      client
              .insightsDeploymentsBeta
              .createDeploymentEvent(version, projectKey, environmentKey, applicationKey, eventType)
              .applicationName(applicationName)
              .applicationKind(applicationKind)
              .versionName(versionName)
              .eventTime(eventTime)
              .eventMetadata(eventMetadata)
              .deploymentMetadata(deploymentMetadata)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsDeploymentsBetaApi#createDeploymentEvent");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .insightsDeploymentsBeta
              .createDeploymentEvent(version, projectKey, environmentKey, applicationKey, eventType)
              .applicationName(applicationName)
              .applicationKind(applicationKind)
              .versionName(versionName)
              .eventTime(eventTime)
              .eventMetadata(eventMetadata)
              .deploymentMetadata(deploymentMetadata)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsDeploymentsBetaApi#createDeploymentEvent");
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
| **postDeploymentEventInput** | [**PostDeploymentEventInput**](PostDeploymentEventInput.md)|  | |

### Return type

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |

<a name="getDeploymentById"></a>
# **getDeploymentById**
> DeploymentRep getDeploymentById(deploymentID).expand(expand).execute();

Get deployment

Get a deployment by ID.  The deployment ID is returned as part of the [List deployments](https://apidocs.launchdarkly.com) response. It is the &#x60;id&#x60; field of each element in the &#x60;items&#x60; array.  ### Expanding the deployment response  LaunchDarkly supports expanding the deployment response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;pullRequests&#x60; includes details on all of the pull requests associated with each deployment * &#x60;flagReferences&#x60; includes details on all of the references to flags in each deployment  For example, use &#x60;?expand&#x3D;pullRequests&#x60; to include the &#x60;pullRequests&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsDeploymentsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String deploymentID = "deploymentID_example"; // The deployment ID
    String expand = "expand_example"; // Expand properties in response. Options: `pullRequests`, `flagReferences`
    try {
      DeploymentRep result = client
              .insightsDeploymentsBeta
              .getDeploymentById(deploymentID)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getApplicationKey());
      System.out.println(result.getApplicationVersion());
      System.out.println(result.getStartedAt());
      System.out.println(result.getEndedAt());
      System.out.println(result.getDurationMs());
      System.out.println(result.getStatus());
      System.out.println(result.getKind());
      System.out.println(result.getActive());
      System.out.println(result.getMetadata());
      System.out.println(result.getArchived());
      System.out.println(result.getEnvironmentKey());
      System.out.println(result.getNumberOfContributors());
      System.out.println(result.getNumberOfPullRequests());
      System.out.println(result.getLinesAdded());
      System.out.println(result.getLinesDeleted());
      System.out.println(result.getLeadTime());
      System.out.println(result.getPullRequests());
      System.out.println(result.getFlagReferences());
      System.out.println(result.getLeadTimeStages());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsDeploymentsBetaApi#getDeploymentById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<DeploymentRep> response = client
              .insightsDeploymentsBeta
              .getDeploymentById(deploymentID)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsDeploymentsBetaApi#getDeploymentById");
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
| **deploymentID** | **String**| The deployment ID | |
| **expand** | **String**| Expand properties in response. Options: &#x60;pullRequests&#x60;, &#x60;flagReferences&#x60; | [optional] |

### Return type

[**DeploymentRep**](DeploymentRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Deployment response |  -  |

<a name="listDeployments"></a>
# **listDeployments**
> DeploymentCollectionRep listDeployments(projectKey, environmentKey).applicationKey(applicationKey).limit(limit).expand(expand).from(from).to(to).after(after).before(before).kind(kind).status(status).execute();

List deployments

Get a list of deployments  ### Expanding the deployment collection response  LaunchDarkly supports expanding the deployment collection response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;pullRequests&#x60; includes details on all of the pull requests associated with each deployment * &#x60;flagReferences&#x60; includes details on all of the references to flags in each deployment  For example, use &#x60;?expand&#x3D;pullRequests&#x60; to include the &#x60;pullRequests&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsDeploymentsBetaApi;
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
    String applicationKey = "applicationKey_example"; // Comma separated list of application keys
    Long limit = 56L; // The number of deployments to return. Default is 20. Maximum allowed is 100.
    String expand = "expand_example"; // Expand properties in response. Options: `pullRequests`, `flagReferences`
    Long from = 56L; // Unix timestamp in milliseconds. Default value is 7 days ago.
    Long to = 56L; // Unix timestamp in milliseconds. Default value is now.
    String after = "after_example"; // Identifier used for pagination
    String before = "before_example"; // Identifier used for pagination
    String kind = "kind_example"; // The deployment kind
    String status = "status_example"; // The deployment status
    try {
      DeploymentCollectionRep result = client
              .insightsDeploymentsBeta
              .listDeployments(projectKey, environmentKey)
              .applicationKey(applicationKey)
              .limit(limit)
              .expand(expand)
              .from(from)
              .to(to)
              .after(after)
              .before(before)
              .kind(kind)
              .status(status)
              .execute();
      System.out.println(result);
      System.out.println(result.getTotalCount());
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsDeploymentsBetaApi#listDeployments");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<DeploymentCollectionRep> response = client
              .insightsDeploymentsBeta
              .listDeployments(projectKey, environmentKey)
              .applicationKey(applicationKey)
              .limit(limit)
              .expand(expand)
              .from(from)
              .to(to)
              .after(after)
              .before(before)
              .kind(kind)
              .status(status)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsDeploymentsBetaApi#listDeployments");
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
| **applicationKey** | **String**| Comma separated list of application keys | [optional] |
| **limit** | **Long**| The number of deployments to return. Default is 20. Maximum allowed is 100. | [optional] |
| **expand** | **String**| Expand properties in response. Options: &#x60;pullRequests&#x60;, &#x60;flagReferences&#x60; | [optional] |
| **from** | **Long**| Unix timestamp in milliseconds. Default value is 7 days ago. | [optional] |
| **to** | **Long**| Unix timestamp in milliseconds. Default value is now. | [optional] |
| **after** | **String**| Identifier used for pagination | [optional] |
| **before** | **String**| Identifier used for pagination | [optional] |
| **kind** | **String**| The deployment kind | [optional] |
| **status** | **String**| The deployment status | [optional] |

### Return type

[**DeploymentCollectionRep**](DeploymentCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Deployment collection response |  -  |

<a name="updateDeploymentById"></a>
# **updateDeploymentById**
> DeploymentRep updateDeploymentById(deploymentID, patchOperation).execute();

Update deployment

Update a deployment by ID. Updating a deployment uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).&lt;br/&gt;&lt;br/&gt;The deployment ID is returned as part of the [List deployments](https://apidocs.launchdarkly.com) response. It is the &#x60;id&#x60; field of each element in the &#x60;items&#x60; array.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsDeploymentsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String deploymentID = "deploymentID_example"; // The deployment ID
    try {
      DeploymentRep result = client
              .insightsDeploymentsBeta
              .updateDeploymentById(deploymentID)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getApplicationKey());
      System.out.println(result.getApplicationVersion());
      System.out.println(result.getStartedAt());
      System.out.println(result.getEndedAt());
      System.out.println(result.getDurationMs());
      System.out.println(result.getStatus());
      System.out.println(result.getKind());
      System.out.println(result.getActive());
      System.out.println(result.getMetadata());
      System.out.println(result.getArchived());
      System.out.println(result.getEnvironmentKey());
      System.out.println(result.getNumberOfContributors());
      System.out.println(result.getNumberOfPullRequests());
      System.out.println(result.getLinesAdded());
      System.out.println(result.getLinesDeleted());
      System.out.println(result.getLeadTime());
      System.out.println(result.getPullRequests());
      System.out.println(result.getFlagReferences());
      System.out.println(result.getLeadTimeStages());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsDeploymentsBetaApi#updateDeploymentById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<DeploymentRep> response = client
              .insightsDeploymentsBeta
              .updateDeploymentById(deploymentID)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsDeploymentsBetaApi#updateDeploymentById");
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
| **deploymentID** | **String**| The deployment ID | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**DeploymentRep**](DeploymentRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Deployment response |  -  |

