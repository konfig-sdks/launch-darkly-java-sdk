# InsightsRepositoriesBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**associateRepositoriesAndProjects**](InsightsRepositoriesBetaApi.md#associateRepositoriesAndProjects) | **PUT** /api/v2/engineering-insights/repositories/projects | Associate repositories with projects |
| [**listRepositories**](InsightsRepositoriesBetaApi.md#listRepositories) | **GET** /api/v2/engineering-insights/repositories | List repositories |
| [**removeRepositoryProjectAssociation**](InsightsRepositoriesBetaApi.md#removeRepositoryProjectAssociation) | **DELETE** /api/v2/engineering-insights/repositories/{repositoryKey}/projects/{projectKey} | Remove repository project association |


<a name="associateRepositoriesAndProjects"></a>
# **associateRepositoriesAndProjects**
> InsightsRepositoryProjectCollection associateRepositoriesAndProjects(insightsRepositoryProjectMappings).execute();

Associate repositories with projects

Associate repositories with projects

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsRepositoriesBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    List<InsightsRepositoryProject> mappings = Arrays.asList();
    try {
      InsightsRepositoryProjectCollection result = client
              .insightsRepositoriesBeta
              .associateRepositoriesAndProjects(mappings)
              .execute();
      System.out.println(result);
      System.out.println(result.getTotalCount());
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsRepositoriesBetaApi#associateRepositoriesAndProjects");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<InsightsRepositoryProjectCollection> response = client
              .insightsRepositoriesBeta
              .associateRepositoriesAndProjects(mappings)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsRepositoriesBetaApi#associateRepositoriesAndProjects");
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
| **insightsRepositoryProjectMappings** | [**InsightsRepositoryProjectMappings**](InsightsRepositoryProjectMappings.md)|  | |

### Return type

[**InsightsRepositoryProjectCollection**](InsightsRepositoryProjectCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Repositories projects response |  -  |

<a name="listRepositories"></a>
# **listRepositories**
> InsightsRepositoryCollection listRepositories().expand(expand).execute();

List repositories

Get a list of repositories  ### Expanding the repository collection response  LaunchDarkly supports expanding the repository collection response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;projects&#x60; includes details on all of the LaunchDarkly projects associated with each repository  For example, use &#x60;?expand&#x3D;projects&#x60; to include the &#x60;projects&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsRepositoriesBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String expand = "expand_example"; // Expand properties in response. Options: `projects`
    try {
      InsightsRepositoryCollection result = client
              .insightsRepositoriesBeta
              .listRepositories()
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getTotalCount());
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsRepositoriesBetaApi#listRepositories");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<InsightsRepositoryCollection> response = client
              .insightsRepositoriesBeta
              .listRepositories()
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsRepositoriesBetaApi#listRepositories");
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
| **expand** | **String**| Expand properties in response. Options: &#x60;projects&#x60; | [optional] |

### Return type

[**InsightsRepositoryCollection**](InsightsRepositoryCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Repository collection response |  -  |

<a name="removeRepositoryProjectAssociation"></a>
# **removeRepositoryProjectAssociation**
> removeRepositoryProjectAssociation(repositoryKey, projectKey).execute();

Remove repository project association

Remove repository project association

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsRepositoriesBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String repositoryKey = "repositoryKey_example"; // The repository key
    String projectKey = "projectKey_example"; // The project key
    try {
      client
              .insightsRepositoriesBeta
              .removeRepositoryProjectAssociation(repositoryKey, projectKey)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsRepositoriesBetaApi#removeRepositoryProjectAssociation");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .insightsRepositoriesBeta
              .removeRepositoryProjectAssociation(repositoryKey, projectKey)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsRepositoriesBetaApi#removeRepositoryProjectAssociation");
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
| **repositoryKey** | **String**| The repository key | |
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

