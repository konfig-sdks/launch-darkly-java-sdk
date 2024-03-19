# CodeReferencesApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**asynchronouslyDeleteBranches**](CodeReferencesApi.md#asynchronouslyDeleteBranches) | **POST** /api/v2/code-refs/repositories/{repo}/branch-delete-tasks | Delete branches |
| [**createExtinction**](CodeReferencesApi.md#createExtinction) | **POST** /api/v2/code-refs/repositories/{repo}/branches/{branch}/extinction-events | Create extinction |
| [**createRepository**](CodeReferencesApi.md#createRepository) | **POST** /api/v2/code-refs/repositories | Create repository |
| [**deleteRepository**](CodeReferencesApi.md#deleteRepository) | **DELETE** /api/v2/code-refs/repositories/{repo} | Delete repository |
| [**getBranch**](CodeReferencesApi.md#getBranch) | **GET** /api/v2/code-refs/repositories/{repo}/branches/{branch} | Get branch |
| [**getRepositoryByRepo**](CodeReferencesApi.md#getRepositoryByRepo) | **GET** /api/v2/code-refs/repositories/{repo} | Get repository |
| [**getStatistics**](CodeReferencesApi.md#getStatistics) | **GET** /api/v2/code-refs/statistics | Get links to code reference repositories for each project |
| [**getStatistics_0**](CodeReferencesApi.md#getStatistics_0) | **GET** /api/v2/code-refs/statistics/{projectKey} | Get code references statistics for flags |
| [**listBranches**](CodeReferencesApi.md#listBranches) | **GET** /api/v2/code-refs/repositories/{repo}/branches | List branches |
| [**listExtinctions**](CodeReferencesApi.md#listExtinctions) | **GET** /api/v2/code-refs/extinctions | List extinctions |
| [**listRepositories**](CodeReferencesApi.md#listRepositories) | **GET** /api/v2/code-refs/repositories | List repositories |
| [**updateRepositorySettings**](CodeReferencesApi.md#updateRepositorySettings) | **PATCH** /api/v2/code-refs/repositories/{repo} | Update repository |
| [**upsertBranch**](CodeReferencesApi.md#upsertBranch) | **PUT** /api/v2/code-refs/repositories/{repo}/branches/{branch} | Upsert branch |


<a name="asynchronouslyDeleteBranches"></a>
# **asynchronouslyDeleteBranches**
> asynchronouslyDeleteBranches(repo, requestBody).execute();

Delete branches

Asynchronously delete a number of branches.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CodeReferencesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String repo = "repo_example"; // The repository name to delete branches for.
    try {
      client
              .codeReferences
              .asynchronouslyDeleteBranches(repo)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#asynchronouslyDeleteBranches");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .codeReferences
              .asynchronouslyDeleteBranches(repo)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#asynchronouslyDeleteBranches");
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
| **repo** | **String**| The repository name to delete branches for. | |
| **requestBody** | [**List&lt;String&gt;**](String.md)|  | |

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
| **200** | Action succeeded |  -  |

<a name="createExtinction"></a>
# **createExtinction**
> createExtinction(repo, branch, extinction).execute();

Create extinction

Create a new extinction.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CodeReferencesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String repo = "repo_example"; // The repository name
    String branch = "branch_example"; // The URL-encoded branch name
    try {
      client
              .codeReferences
              .createExtinction(repo, branch)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#createExtinction");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .codeReferences
              .createExtinction(repo, branch)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#createExtinction");
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
| **repo** | **String**| The repository name | |
| **branch** | **String**| The URL-encoded branch name | |
| **extinction** | [**List&lt;Extinction&gt;**](Extinction.md)|  | |

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
| **200** | Action succeeded |  -  |

<a name="createRepository"></a>
# **createRepository**
> RepositoryRep createRepository(repositoryPost).execute();

Create repository

Create a repository with the specified name.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CodeReferencesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String name = "name_example"; // The repository name
    String sourceLink = "sourceLink_example"; // A URL to access the repository
    String commitUrlTemplate = "commitUrlTemplate_example"; // A template for constructing a valid URL to view the commit
    String hunkUrlTemplate = "hunkUrlTemplate_example"; // A template for constructing a valid URL to view the hunk
    String type = "bitbucket"; // The type of repository. If not specified, the default value is <code>custom</code>.
    String defaultBranch = "defaultBranch_example"; // The repository's default branch. If not specified, the default value is <code>main</code>.
    try {
      RepositoryRep result = client
              .codeReferences
              .createRepository(name)
              .sourceLink(sourceLink)
              .commitUrlTemplate(commitUrlTemplate)
              .hunkUrlTemplate(hunkUrlTemplate)
              .type(type)
              .defaultBranch(defaultBranch)
              .execute();
      System.out.println(result);
      System.out.println(result.getVersion());
      System.out.println(result.getName());
      System.out.println(result.getSourceLink());
      System.out.println(result.getCommitUrlTemplate());
      System.out.println(result.getHunkUrlTemplate());
      System.out.println(result.getType());
      System.out.println(result.getDefaultBranch());
      System.out.println(result.getEnabled());
      System.out.println(result.getBranches());
      System.out.println(result.getLinks());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#createRepository");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<RepositoryRep> response = client
              .codeReferences
              .createRepository(name)
              .sourceLink(sourceLink)
              .commitUrlTemplate(commitUrlTemplate)
              .hunkUrlTemplate(hunkUrlTemplate)
              .type(type)
              .defaultBranch(defaultBranch)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#createRepository");
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
| **repositoryPost** | [**RepositoryPost**](RepositoryPost.md)|  | |

### Return type

[**RepositoryRep**](RepositoryRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Repository response |  -  |

<a name="deleteRepository"></a>
# **deleteRepository**
> deleteRepository(repo).execute();

Delete repository

Delete a repository with the specified name.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CodeReferencesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String repo = "repo_example"; // The repository name
    try {
      client
              .codeReferences
              .deleteRepository(repo)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#deleteRepository");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .codeReferences
              .deleteRepository(repo)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#deleteRepository");
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
| **repo** | **String**| The repository name | |

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

<a name="getBranch"></a>
# **getBranch**
> BranchRep getBranch(repo, branch).projKey(projKey).flagKey(flagKey).execute();

Get branch

Get a specific branch in a repository.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CodeReferencesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String repo = "repo_example"; // The repository name
    String branch = "branch_example"; // The url-encoded branch name
    String projKey = "projKey_example"; // Filter results to a specific project
    String flagKey = "flagKey_example"; // Filter results to a specific flag key
    try {
      BranchRep result = client
              .codeReferences
              .getBranch(repo, branch)
              .projKey(projKey)
              .flagKey(flagKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getName());
      System.out.println(result.getHead());
      System.out.println(result.getUpdateSequenceId());
      System.out.println(result.getSyncTime());
      System.out.println(result.getReferences());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#getBranch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<BranchRep> response = client
              .codeReferences
              .getBranch(repo, branch)
              .projKey(projKey)
              .flagKey(flagKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#getBranch");
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
| **repo** | **String**| The repository name | |
| **branch** | **String**| The url-encoded branch name | |
| **projKey** | **String**| Filter results to a specific project | [optional] |
| **flagKey** | **String**| Filter results to a specific flag key | [optional] |

### Return type

[**BranchRep**](BranchRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Branch response |  -  |

<a name="getRepositoryByRepo"></a>
# **getRepositoryByRepo**
> RepositoryRep getRepositoryByRepo(repo).execute();

Get repository

Get a single repository by name.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CodeReferencesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String repo = "repo_example"; // The repository name
    try {
      RepositoryRep result = client
              .codeReferences
              .getRepositoryByRepo(repo)
              .execute();
      System.out.println(result);
      System.out.println(result.getVersion());
      System.out.println(result.getName());
      System.out.println(result.getSourceLink());
      System.out.println(result.getCommitUrlTemplate());
      System.out.println(result.getHunkUrlTemplate());
      System.out.println(result.getType());
      System.out.println(result.getDefaultBranch());
      System.out.println(result.getEnabled());
      System.out.println(result.getBranches());
      System.out.println(result.getLinks());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#getRepositoryByRepo");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<RepositoryRep> response = client
              .codeReferences
              .getRepositoryByRepo(repo)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#getRepositoryByRepo");
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
| **repo** | **String**| The repository name | |

### Return type

[**RepositoryRep**](RepositoryRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Repository response |  -  |

<a name="getStatistics"></a>
# **getStatistics**
> StatisticsRoot getStatistics().execute();

Get links to code reference repositories for each project

Get links for all projects that have code references.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CodeReferencesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    try {
      StatisticsRoot result = client
              .codeReferences
              .getStatistics()
              .execute();
      System.out.println(result);
      System.out.println(result.getProjects());
      System.out.println(result.getSelf());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#getStatistics");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<StatisticsRoot> response = client
              .codeReferences
              .getStatistics()
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#getStatistics");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

### Parameters
This endpoint does not need any parameter.

### Return type

[**StatisticsRoot**](StatisticsRoot.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Statistic root response |  -  |

<a name="getStatistics_0"></a>
# **getStatistics_0**
> StatisticCollectionRep getStatistics_0(projectKey).flagKey(flagKey).execute();

Get code references statistics for flags

Get statistics about all the code references across repositories for all flags in your project that have code references in the default branch, for example, &#x60;main&#x60;. Optionally, you can include the &#x60;flagKey&#x60; query parameter to limit your request to statistics about code references for a single flag. This endpoint returns the number of references to your flag keys in your repositories, as well as a link to each repository.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CodeReferencesApi;
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
    String flagKey = "flagKey_example"; // Filter results to a specific flag key
    try {
      StatisticCollectionRep result = client
              .codeReferences
              .getStatistics_0(projectKey)
              .flagKey(flagKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getFlags());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#getStatistics_0");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<StatisticCollectionRep> response = client
              .codeReferences
              .getStatistics_0(projectKey)
              .flagKey(flagKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#getStatistics_0");
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
| **flagKey** | **String**| Filter results to a specific flag key | [optional] |

### Return type

[**StatisticCollectionRep**](StatisticCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Statistic collection response |  -  |

<a name="listBranches"></a>
# **listBranches**
> BranchCollectionRep listBranches(repo).execute();

List branches

Get a list of branches.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CodeReferencesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String repo = "repo_example"; // The repository name
    try {
      BranchCollectionRep result = client
              .codeReferences
              .listBranches(repo)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#listBranches");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<BranchCollectionRep> response = client
              .codeReferences
              .listBranches(repo)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#listBranches");
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
| **repo** | **String**| The repository name | |

### Return type

[**BranchCollectionRep**](BranchCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Branch collection response |  -  |

<a name="listExtinctions"></a>
# **listExtinctions**
> ExtinctionCollectionRep listExtinctions().repoName(repoName).branchName(branchName).projKey(projKey).flagKey(flagKey).from(from).to(to).execute();

List extinctions

Get a list of all extinctions. LaunchDarkly creates an extinction event after you remove all code references to a flag. To learn more, read [Understanding extinction events](https://docs.launchdarkly.com/home/code/code-references#understanding-extinction-events).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CodeReferencesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String repoName = "repoName_example"; // Filter results to a specific repository
    String branchName = "branchName_example"; // Filter results to a specific branch. By default, only the default branch will be queried for extinctions.
    String projKey = "projKey_example"; // Filter results to a specific project
    String flagKey = "flagKey_example"; // Filter results to a specific flag key
    Long from = 56L; // Filter results to a specific timeframe based on commit time, expressed as a Unix epoch time in milliseconds. Must be used with `to`.
    Long to = 56L; // Filter results to a specific timeframe based on commit time, expressed as a Unix epoch time in milliseconds. Must be used with `from`.
    try {
      ExtinctionCollectionRep result = client
              .codeReferences
              .listExtinctions()
              .repoName(repoName)
              .branchName(branchName)
              .projKey(projKey)
              .flagKey(flagKey)
              .from(from)
              .to(to)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#listExtinctions");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ExtinctionCollectionRep> response = client
              .codeReferences
              .listExtinctions()
              .repoName(repoName)
              .branchName(branchName)
              .projKey(projKey)
              .flagKey(flagKey)
              .from(from)
              .to(to)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#listExtinctions");
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
| **repoName** | **String**| Filter results to a specific repository | [optional] |
| **branchName** | **String**| Filter results to a specific branch. By default, only the default branch will be queried for extinctions. | [optional] |
| **projKey** | **String**| Filter results to a specific project | [optional] |
| **flagKey** | **String**| Filter results to a specific flag key | [optional] |
| **from** | **Long**| Filter results to a specific timeframe based on commit time, expressed as a Unix epoch time in milliseconds. Must be used with &#x60;to&#x60;. | [optional] |
| **to** | **Long**| Filter results to a specific timeframe based on commit time, expressed as a Unix epoch time in milliseconds. Must be used with &#x60;from&#x60;. | [optional] |

### Return type

[**ExtinctionCollectionRep**](ExtinctionCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Extinction collection response |  -  |

<a name="listRepositories"></a>
# **listRepositories**
> RepositoryCollectionRep listRepositories().withBranches(withBranches).withReferencesForDefaultBranch(withReferencesForDefaultBranch).projKey(projKey).flagKey(flagKey).execute();

List repositories

Get a list of connected repositories. Optionally, you can include branch metadata with the &#x60;withBranches&#x60; query parameter. Embed references for the default branch with &#x60;ReferencesForDefaultBranch&#x60;. You can also filter the list of code references by project key and flag key.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CodeReferencesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String withBranches = "withBranches_example"; // If set to any value, the endpoint returns repositories with associated branch data
    String withReferencesForDefaultBranch = "withReferencesForDefaultBranch_example"; // If set to any value, the endpoint returns repositories with associated branch data, as well as code references for the default git branch
    String projKey = "projKey_example"; // A LaunchDarkly project key. If provided, this filters code reference results to the specified project.
    String flagKey = "flagKey_example"; // If set to any value, the endpoint returns repositories with associated branch data, as well as code references for the default git branch
    try {
      RepositoryCollectionRep result = client
              .codeReferences
              .listRepositories()
              .withBranches(withBranches)
              .withReferencesForDefaultBranch(withReferencesForDefaultBranch)
              .projKey(projKey)
              .flagKey(flagKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#listRepositories");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<RepositoryCollectionRep> response = client
              .codeReferences
              .listRepositories()
              .withBranches(withBranches)
              .withReferencesForDefaultBranch(withReferencesForDefaultBranch)
              .projKey(projKey)
              .flagKey(flagKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#listRepositories");
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
| **withBranches** | **String**| If set to any value, the endpoint returns repositories with associated branch data | [optional] |
| **withReferencesForDefaultBranch** | **String**| If set to any value, the endpoint returns repositories with associated branch data, as well as code references for the default git branch | [optional] |
| **projKey** | **String**| A LaunchDarkly project key. If provided, this filters code reference results to the specified project. | [optional] |
| **flagKey** | **String**| If set to any value, the endpoint returns repositories with associated branch data, as well as code references for the default git branch | [optional] |

### Return type

[**RepositoryCollectionRep**](RepositoryCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Repository collection response |  -  |

<a name="updateRepositorySettings"></a>
# **updateRepositorySettings**
> RepositoryRep updateRepositorySettings(repo, patchOperation).execute();

Update repository

Update a repository&#39;s settings. Updating repository settings uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) or [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CodeReferencesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String repo = "repo_example"; // The repository name
    try {
      RepositoryRep result = client
              .codeReferences
              .updateRepositorySettings(repo)
              .execute();
      System.out.println(result);
      System.out.println(result.getVersion());
      System.out.println(result.getName());
      System.out.println(result.getSourceLink());
      System.out.println(result.getCommitUrlTemplate());
      System.out.println(result.getHunkUrlTemplate());
      System.out.println(result.getType());
      System.out.println(result.getDefaultBranch());
      System.out.println(result.getEnabled());
      System.out.println(result.getBranches());
      System.out.println(result.getLinks());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#updateRepositorySettings");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<RepositoryRep> response = client
              .codeReferences
              .updateRepositorySettings(repo)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#updateRepositorySettings");
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
| **repo** | **String**| The repository name | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**RepositoryRep**](RepositoryRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Repository response |  -  |

<a name="upsertBranch"></a>
# **upsertBranch**
> upsertBranch(repo, branch, putBranch).execute();

Upsert branch

Create a new branch if it doesn&#39;t exist, or update the branch if it already exists.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.CodeReferencesApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String name = "name_example"; // The branch name
    String head = "head_example"; // An ID representing the branch HEAD. For example, a commit SHA.
    Long syncTime = 56L;
    String repo = "repo_example"; // The repository name
    String branch = "branch_example"; // The URL-encoded branch name
    Long updateSequenceId = 56L; // An optional ID used to prevent older data from overwriting newer data. If no sequence ID is included, the newly submitted data will always be saved.
    List<ReferenceRep> references = Arrays.asList(); // An array of flag references found on the branch
    Long commitTime = 56L;
    try {
      client
              .codeReferences
              .upsertBranch(name, head, syncTime, repo, branch)
              .updateSequenceId(updateSequenceId)
              .references(references)
              .commitTime(commitTime)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#upsertBranch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .codeReferences
              .upsertBranch(name, head, syncTime, repo, branch)
              .updateSequenceId(updateSequenceId)
              .references(references)
              .commitTime(commitTime)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling CodeReferencesApi#upsertBranch");
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
| **repo** | **String**| The repository name | |
| **branch** | **String**| The URL-encoded branch name | |
| **putBranch** | [**PutBranch**](PutBranch.md)|  | |

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
| **200** | Action succeeded |  -  |

