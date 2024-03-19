# FeatureFlagsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getMigrationSafetyIssues**](FeatureFlagsBetaApi.md#getMigrationSafetyIssues) | **POST** /api/v2/projects/{projectKey}/flags/{flagKey}/environments/{environmentKey}/migration-safety-issues | Get migration safety issues |
| [**listDependentFlags**](FeatureFlagsBetaApi.md#listDependentFlags) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/dependent-flags | List dependent feature flags |
| [**listDependentFlagsByEnv**](FeatureFlagsBetaApi.md#listDependentFlagsByEnv) | **GET** /api/v2/flags/{projectKey}/{environmentKey}/{featureFlagKey}/dependent-flags | List dependent feature flags by environment |


<a name="getMigrationSafetyIssues"></a>
# **getMigrationSafetyIssues**
> List&lt;MigrationSafetyIssueRep&gt; getMigrationSafetyIssues(projectKey, flagKey, environmentKey, flagSempatch).execute();

Get migration safety issues

Returns the migration safety issues that are associated with the POSTed flag patch. The patch must use the semantic patch format for updating feature flags.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FeatureFlagsBetaApi;
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
    String flagKey = "flagKey_example"; // The migration flag key
    String environmentKey = "environmentKey_example"; // The environment key
    String comment = "comment_example";
    try {
      List<MigrationSafetyIssueRep> result = client
              .featureFlagsBeta
              .getMigrationSafetyIssues(instructions, projectKey, flagKey, environmentKey)
              .comment(comment)
              .execute();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsBetaApi#getMigrationSafetyIssues");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<List<MigrationSafetyIssueRep>> response = client
              .featureFlagsBeta
              .getMigrationSafetyIssues(instructions, projectKey, flagKey, environmentKey)
              .comment(comment)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsBetaApi#getMigrationSafetyIssues");
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
| **flagKey** | **String**| The migration flag key | |
| **environmentKey** | **String**| The environment key | |
| **flagSempatch** | [**FlagSempatch**](FlagSempatch.md)|  | |

### Return type

[**List&lt;MigrationSafetyIssueRep&gt;**](MigrationSafetyIssueRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Migration safety issues found |  -  |
| **204** | No safety issues found |  -  |

<a name="listDependentFlags"></a>
# **listDependentFlags**
> MultiEnvironmentDependentFlags listDependentFlags(projectKey, featureFlagKey).execute();

List dependent feature flags

&gt; ### Flag prerequisites is an Enterprise feature &gt; &gt; Flag prerequisites is available to customers on an Enterprise plan. To learn more, [read about our pricing](https://launchdarkly.com/pricing/). To upgrade your plan, [contact Sales](https://launchdarkly.com/contact-sales/).  List dependent flags across all environments for the flag specified in the path parameters. A dependent flag is a flag that uses another flag as a prerequisite. To learn more, read [Flag prerequisites](https://docs.launchdarkly.com/home/targeting-flags/prerequisites). 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FeatureFlagsBetaApi;
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
    try {
      MultiEnvironmentDependentFlags result = client
              .featureFlagsBeta
              .listDependentFlags(projectKey, featureFlagKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
      System.out.println(result.getSite());
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsBetaApi#listDependentFlags");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<MultiEnvironmentDependentFlags> response = client
              .featureFlagsBeta
              .listDependentFlags(projectKey, featureFlagKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsBetaApi#listDependentFlags");
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

### Return type

[**MultiEnvironmentDependentFlags**](MultiEnvironmentDependentFlags.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Multi environment dependent flags collection response |  -  |

<a name="listDependentFlagsByEnv"></a>
# **listDependentFlagsByEnv**
> DependentFlagsByEnvironment listDependentFlagsByEnv(projectKey, environmentKey, featureFlagKey).execute();

List dependent feature flags by environment

&gt; ### Flag prerequisites is an Enterprise feature &gt; &gt; Flag prerequisites is available to customers on an Enterprise plan. To learn more, [read about our pricing](https://launchdarkly.com/pricing/). To upgrade your plan, [contact Sales](https://launchdarkly.com/contact-sales/).  List dependent flags across all environments for the flag specified in the path parameters. A dependent flag is a flag that uses another flag as a prerequisite. To learn more, read [Flag prerequisites](https://docs.launchdarkly.com/home/targeting-flags/prerequisites). 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FeatureFlagsBetaApi;
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
      DependentFlagsByEnvironment result = client
              .featureFlagsBeta
              .listDependentFlagsByEnv(projectKey, environmentKey, featureFlagKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
      System.out.println(result.getSite());
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsBetaApi#listDependentFlagsByEnv");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<DependentFlagsByEnvironment> response = client
              .featureFlagsBeta
              .listDependentFlagsByEnv(projectKey, environmentKey, featureFlagKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FeatureFlagsBetaApi#listDependentFlagsByEnv");
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

[**DependentFlagsByEnvironment**](DependentFlagsByEnvironment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Dependent flags collection response |  -  |

