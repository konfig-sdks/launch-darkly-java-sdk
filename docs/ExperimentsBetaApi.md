# ExperimentsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createIteration**](ExperimentsBetaApi.md#createIteration) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey}/iterations | Create iteration |
| [**createNew**](ExperimentsBetaApi.md#createNew) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments | Create experiment |
| [**getDetails**](ExperimentsBetaApi.md#getDetails) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey} | Get experiment |
| [**getExperimentMetricResults**](ExperimentsBetaApi.md#getExperimentMetricResults) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey}/metrics/{metricKey}/results | Get experiment results |
| [**getExperimentationSettings**](ExperimentsBetaApi.md#getExperimentationSettings) | **GET** /api/v2/projects/{projectKey}/experimentation-settings | Get experimentation settings |
| [**getLegacyExperimentResults**](ExperimentsBetaApi.md#getLegacyExperimentResults) | **GET** /api/v2/flags/{projectKey}/{featureFlagKey}/experiments/{environmentKey}/{metricKey} | Get legacy experiment results (deprecated) |
| [**getResultsForMetricGroup**](ExperimentsBetaApi.md#getResultsForMetricGroup) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey}/metric-groups/{metricGroupKey}/results | Get experiment results for metric group |
| [**listExperimentsInEnvironment**](ExperimentsBetaApi.md#listExperimentsInEnvironment) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments | Get experiments |
| [**updateExperimentationSettings**](ExperimentsBetaApi.md#updateExperimentationSettings) | **PUT** /api/v2/projects/{projectKey}/experimentation-settings | Update experimentation settings |
| [**updateSemanticPatch**](ExperimentsBetaApi.md#updateSemanticPatch) | **PATCH** /api/v2/projects/{projectKey}/environments/{environmentKey}/experiments/{experimentKey} | Patch experiment |


<a name="createIteration"></a>
# **createIteration**
> IterationRep createIteration(projectKey, environmentKey, experimentKey, iterationInput).execute();

Create iteration

Create an experiment iteration.  Experiment iterations let you record experiments in individual blocks of time. Initially, iterations are created with a status of &#x60;not_started&#x60; and appear in the &#x60;draftIteration&#x60; field of an experiment. To start or stop an iteration, [update the experiment](https://apidocs.launchdarkly.com)#operation/patchExperiment) with the &#x60;startIteration&#x60; or &#x60;stopIteration&#x60; instruction.   To learn more, read [Starting experiment iterations](https://docs.launchdarkly.com/home/creating-experiments#starting-experiment-iterations). 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ExperimentsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String hypothesis = "hypothesis_example"; // The expected outcome of this experiment
    List<MetricInput> metrics = Arrays.asList();
    List<TreatmentInput> treatments = Arrays.asList();
    Map<String, FlagInput> flags = new HashMap();
    String projectKey = "projectKey_example"; // The project key
    String environmentKey = "environmentKey_example"; // The environment key
    String experimentKey = "experimentKey_example"; // The experiment key
    Boolean canReshuffleTraffic = true; // Whether to allow the experiment to reassign traffic to different variations when you increase or decrease the traffic in your experiment audience (true) or keep all traffic assigned to its initial variation (false). Defaults to true.
    String primarySingleMetricKey = "primarySingleMetricKey_example"; // The key of the primary metric for this experiment. Either <code>primarySingleMetricKey</code> or <code>primaryFunnelKey</code> must be present.
    String primaryFunnelKey = "primaryFunnelKey_example"; // The key of the primary funnel group for this experiment. Either <code>primarySingleMetricKey</code> or <code>primaryFunnelKey</code> must be present.
    String randomizationUnit = "randomizationUnit_example"; // The unit of randomization for this iteration. Defaults to user.
    try {
      IterationRep result = client
              .experimentsBeta
              .createIteration(hypothesis, metrics, treatments, flags, projectKey, environmentKey, experimentKey)
              .canReshuffleTraffic(canReshuffleTraffic)
              .primarySingleMetricKey(primarySingleMetricKey)
              .primaryFunnelKey(primaryFunnelKey)
              .randomizationUnit(randomizationUnit)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getHypothesis());
      System.out.println(result.getStatus());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getStartedAt());
      System.out.println(result.getEndedAt());
      System.out.println(result.getWinningTreatmentId());
      System.out.println(result.getWinningReason());
      System.out.println(result.getCanReshuffleTraffic());
      System.out.println(result.getFlags());
      System.out.println(result.getPrimaryMetric());
      System.out.println(result.getPrimarySingleMetric());
      System.out.println(result.getPrimaryFunnel());
      System.out.println(result.getRandomizationUnit());
      System.out.println(result.getTreatments());
      System.out.println(result.getSecondaryMetrics());
      System.out.println(result.getMetrics());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#createIteration");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<IterationRep> response = client
              .experimentsBeta
              .createIteration(hypothesis, metrics, treatments, flags, projectKey, environmentKey, experimentKey)
              .canReshuffleTraffic(canReshuffleTraffic)
              .primarySingleMetricKey(primarySingleMetricKey)
              .primaryFunnelKey(primaryFunnelKey)
              .randomizationUnit(randomizationUnit)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#createIteration");
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
| **experimentKey** | **String**| The experiment key | |
| **iterationInput** | [**IterationInput**](IterationInput.md)|  | |

### Return type

[**IterationRep**](IterationRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Iteration response |  -  |

<a name="createNew"></a>
# **createNew**
> Experiment createNew(projectKey, environmentKey, experimentPost).execute();

Create experiment

Create an experiment.  To run this experiment, you&#39;ll need to [create an iteration](https://apidocs.launchdarkly.com)#operation/createIteration) and then [update the experiment](https://apidocs.launchdarkly.com)#operation/patchExperiment) with the &#x60;startIteration&#x60; instruction.  To learn more, read [Creating experiments](https://docs.launchdarkly.com/home/creating-experiments). 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ExperimentsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String name = "name_example"; // The experiment name
    String key = "key_example"; // The experiment key
    IterationInput iteration = new IterationInput();
    String projectKey = "projectKey_example"; // The project key
    String environmentKey = "environmentKey_example"; // The environment key
    String description = "description_example"; // The experiment description
    String maintainerId = "maintainerId_example"; // The ID of the member who maintains this experiment
    try {
      Experiment result = client
              .experimentsBeta
              .createNew(name, key, iteration, projectKey, environmentKey)
              .description(description)
              .maintainerId(maintainerId)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getMaintainerId());
      System.out.println(result.getCreationDate());
      System.out.println(result.getEnvironmentKey());
      System.out.println(result.getArchivedDate());
      System.out.println(result.getLinks());
      System.out.println(result.getCurrentIteration());
      System.out.println(result.getDraftIteration());
      System.out.println(result.getPreviousIterations());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#createNew");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Experiment> response = client
              .experimentsBeta
              .createNew(name, key, iteration, projectKey, environmentKey)
              .description(description)
              .maintainerId(maintainerId)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#createNew");
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
| **experimentPost** | [**ExperimentPost**](ExperimentPost.md)|  | |

### Return type

[**Experiment**](Experiment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Experiment response |  -  |

<a name="getDetails"></a>
# **getDetails**
> Experiment getDetails(projectKey, environmentKey, experimentKey).expand(expand).execute();

Get experiment

Get details about an experiment.  ### Expanding the experiment response  LaunchDarkly supports four fields for expanding the \&quot;Get experiment\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  - &#x60;previousIterations&#x60; includes all iterations prior to the current iteration. By default only the current iteration is included in the response. - &#x60;draftIteration&#x60; includes the iteration which has not been started yet, if any. - &#x60;secondaryMetrics&#x60; includes secondary metrics. By default only the primary metric is included in the response. - &#x60;treatments&#x60; includes all treatment and parameter details. By default treatment data is not included in the response.  For example, &#x60;expand&#x3D;draftIteration,treatments&#x60; includes the &#x60;draftIteration&#x60; and &#x60;treatments&#x60; fields in the response. If fields that you request with the &#x60;expand&#x60; query parameter are empty, they are not included in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ExperimentsBetaApi;
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
    String experimentKey = "experimentKey_example"; // The experiment key
    String expand = "expand_example"; // A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above.
    try {
      Experiment result = client
              .experimentsBeta
              .getDetails(projectKey, environmentKey, experimentKey)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getMaintainerId());
      System.out.println(result.getCreationDate());
      System.out.println(result.getEnvironmentKey());
      System.out.println(result.getArchivedDate());
      System.out.println(result.getLinks());
      System.out.println(result.getCurrentIteration());
      System.out.println(result.getDraftIteration());
      System.out.println(result.getPreviousIterations());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getDetails");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Experiment> response = client
              .experimentsBeta
              .getDetails(projectKey, environmentKey, experimentKey)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getDetails");
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
| **experimentKey** | **String**| The experiment key | |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. | [optional] |

### Return type

[**Experiment**](Experiment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experiment response |  -  |

<a name="getExperimentMetricResults"></a>
# **getExperimentMetricResults**
> ExperimentBayesianResultsRep getExperimentMetricResults(projectKey, environmentKey, experimentKey, metricKey).iterationId(iterationId).expand(expand).execute();

Get experiment results

Get results from an experiment for a particular metric.  LaunchDarkly supports one field for expanding the \&quot;Get experiment results\&quot; response. By default, this field is **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter with the following field: * &#x60;traffic&#x60; includes the total count of units for each treatment.  For example, &#x60;expand&#x3D;traffic&#x60; includes the &#x60;traffic&#x60; field for the project in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ExperimentsBetaApi;
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
    String experimentKey = "experimentKey_example"; // The experiment key
    String metricKey = "metricKey_example"; // The metric key
    String iterationId = "iterationId_example"; // The iteration ID
    String expand = "expand_example"; // A comma-separated list of fields to expand in the response. Supported fields are explained above.
    try {
      ExperimentBayesianResultsRep result = client
              .experimentsBeta
              .getExperimentMetricResults(projectKey, environmentKey, experimentKey, metricKey)
              .iterationId(iterationId)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getTreatmentResults());
      System.out.println(result.getMetricSeen());
      System.out.println(result.getProbabilityOfMismatch());
      System.out.println(result.getResults());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getExperimentMetricResults");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ExperimentBayesianResultsRep> response = client
              .experimentsBeta
              .getExperimentMetricResults(projectKey, environmentKey, experimentKey, metricKey)
              .iterationId(iterationId)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getExperimentMetricResults");
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
| **experimentKey** | **String**| The experiment key | |
| **metricKey** | **String**| The metric key | |
| **iterationId** | **String**| The iteration ID | [optional] |
| **expand** | **String**| A comma-separated list of fields to expand in the response. Supported fields are explained above. | [optional] |

### Return type

[**ExperimentBayesianResultsRep**](ExperimentBayesianResultsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experiment results response |  -  |

<a name="getExperimentationSettings"></a>
# **getExperimentationSettings**
> RandomizationSettingsRep getExperimentationSettings(projectKey).execute();

Get experimentation settings

Get current experimentation settings for the given project

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ExperimentsBetaApi;
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
    try {
      RandomizationSettingsRep result = client
              .experimentsBeta
              .getExperimentationSettings(projectKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getProjectId());
      System.out.println(result.getProjectKey());
      System.out.println(result.getRandomizationUnits());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getExperimentationSettings");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<RandomizationSettingsRep> response = client
              .experimentsBeta
              .getExperimentationSettings(projectKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getExperimentationSettings");
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

### Return type

[**RandomizationSettingsRep**](RandomizationSettingsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experimentation settings response |  -  |

<a name="getLegacyExperimentResults"></a>
# **getLegacyExperimentResults**
> ExperimentResults getLegacyExperimentResults(projectKey, featureFlagKey, environmentKey, metricKey).from(from).to(to).execute();

Get legacy experiment results (deprecated)

Get detailed experiment result data for legacy experiments.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ExperimentsBetaApi;
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
    String metricKey = "metricKey_example"; // The metric key
    Long from = 56L; // A timestamp denoting the start of the data collection period, expressed as a Unix epoch time in milliseconds.
    Long to = 56L; // A timestamp denoting the end of the data collection period, expressed as a Unix epoch time in milliseconds.
    try {
      ExperimentResults result = client
              .experimentsBeta
              .getLegacyExperimentResults(projectKey, featureFlagKey, environmentKey, metricKey)
              .from(from)
              .to(to)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getMetadata());
      System.out.println(result.getTotals());
      System.out.println(result.getSeries());
      System.out.println(result.getStats());
      System.out.println(result.getGranularity());
      System.out.println(result.getMetricSeen());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getLegacyExperimentResults");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ExperimentResults> response = client
              .experimentsBeta
              .getLegacyExperimentResults(projectKey, featureFlagKey, environmentKey, metricKey)
              .from(from)
              .to(to)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getLegacyExperimentResults");
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
| **metricKey** | **String**| The metric key | |
| **from** | **Long**| A timestamp denoting the start of the data collection period, expressed as a Unix epoch time in milliseconds. | [optional] |
| **to** | **Long**| A timestamp denoting the end of the data collection period, expressed as a Unix epoch time in milliseconds. | [optional] |

### Return type

[**ExperimentResults**](ExperimentResults.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experiment results response |  -  |

<a name="getResultsForMetricGroup"></a>
# **getResultsForMetricGroup**
> MetricGroupResultsRep getResultsForMetricGroup(projectKey, environmentKey, experimentKey, metricGroupKey).iterationId(iterationId).execute();

Get experiment results for metric group

Get results from an experiment for a particular metric group.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ExperimentsBetaApi;
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
    String experimentKey = "experimentKey_example"; // The experiment key
    String metricGroupKey = "metricGroupKey_example"; // The metric group key
    String iterationId = "iterationId_example"; // The iteration ID
    try {
      MetricGroupResultsRep result = client
              .experimentsBeta
              .getResultsForMetricGroup(projectKey, environmentKey, experimentKey, metricGroupKey)
              .iterationId(iterationId)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getMetrics());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getResultsForMetricGroup");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<MetricGroupResultsRep> response = client
              .experimentsBeta
              .getResultsForMetricGroup(projectKey, environmentKey, experimentKey, metricGroupKey)
              .iterationId(iterationId)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#getResultsForMetricGroup");
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
| **experimentKey** | **String**| The experiment key | |
| **metricGroupKey** | **String**| The metric group key | |
| **iterationId** | **String**| The iteration ID | [optional] |

### Return type

[**MetricGroupResultsRep**](MetricGroupResultsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experiment results response for metric group |  -  |

<a name="listExperimentsInEnvironment"></a>
# **listExperimentsInEnvironment**
> ExperimentCollectionRep listExperimentsInEnvironment(projectKey, environmentKey).limit(limit).offset(offset).filter(filter).expand(expand).lifecycleState(lifecycleState).execute();

Get experiments

Get details about all experiments in an environment.  ### Filtering experiments  LaunchDarkly supports the &#x60;filter&#x60; query param for filtering, with the following fields:  - &#x60;flagKey&#x60; filters for only experiments that use the flag with the given key. - &#x60;metricKey&#x60; filters for only experiments that use the metric with the given key. - &#x60;status&#x60; filters for only experiments with an iteration with the given status. An iteration can have the status &#x60;not_started&#x60;, &#x60;running&#x60; or &#x60;stopped&#x60;.  For example, &#x60;filter&#x3D;flagKey:my-flag,status:running,metricKey:page-load-ms&#x60; filters for experiments for the given flag key and the given metric key which have a currently running iteration.  ### Expanding the experiments response  LaunchDarkly supports four fields for expanding the \&quot;Get experiments\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  - &#x60;previousIterations&#x60; includes all iterations prior to the current iteration. By default only the current iteration is included in the response. - &#x60;draftIteration&#x60; includes the iteration which has not been started yet, if any. - &#x60;secondaryMetrics&#x60; includes secondary metrics. By default only the primary metric is included in the response. - &#x60;treatments&#x60; includes all treatment and parameter details. By default treatment data is not included in the response.  For example, &#x60;expand&#x3D;draftIteration,treatments&#x60; includes the &#x60;draftIteration&#x60; and &#x60;treatments&#x60; fields in the response. If fields that you request with the &#x60;expand&#x60; query parameter are empty, they are not included in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ExperimentsBetaApi;
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
    Long limit = 56L; // The maximum number of experiments to return. Defaults to 20.
    Long offset = 56L; // Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String filter = "filter_example"; // A comma-separated list of filters. Each filter is of the form `field:value`. Supported fields are explained above.
    String expand = "expand_example"; // A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above.
    String lifecycleState = "lifecycleState_example"; // A comma-separated list of experiment archived states. Supports `archived`, `active`, or both. Defaults to `active` experiments.
    try {
      ExperimentCollectionRep result = client
              .experimentsBeta
              .listExperimentsInEnvironment(projectKey, environmentKey)
              .limit(limit)
              .offset(offset)
              .filter(filter)
              .expand(expand)
              .lifecycleState(lifecycleState)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getTotalCount());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#listExperimentsInEnvironment");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ExperimentCollectionRep> response = client
              .experimentsBeta
              .listExperimentsInEnvironment(projectKey, environmentKey)
              .limit(limit)
              .offset(offset)
              .filter(filter)
              .expand(expand)
              .lifecycleState(lifecycleState)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#listExperimentsInEnvironment");
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
| **limit** | **Long**| The maximum number of experiments to return. Defaults to 20. | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **filter** | **String**| A comma-separated list of filters. Each filter is of the form &#x60;field:value&#x60;. Supported fields are explained above. | [optional] |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. | [optional] |
| **lifecycleState** | **String**| A comma-separated list of experiment archived states. Supports &#x60;archived&#x60;, &#x60;active&#x60;, or both. Defaults to &#x60;active&#x60; experiments. | [optional] |

### Return type

[**ExperimentCollectionRep**](ExperimentCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experiment collection response |  -  |

<a name="updateExperimentationSettings"></a>
# **updateExperimentationSettings**
> RandomizationSettingsRep updateExperimentationSettings(projectKey, randomizationSettingsPut).execute();

Update experimentation settings

Update experimentation settings for the given project

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ExperimentsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    List<RandomizationUnitInput> randomizationUnits = Arrays.asList(); // An array of randomization units allowed for this project.
    String projectKey = "projectKey_example"; // The project key
    try {
      RandomizationSettingsRep result = client
              .experimentsBeta
              .updateExperimentationSettings(randomizationUnits, projectKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getProjectId());
      System.out.println(result.getProjectKey());
      System.out.println(result.getRandomizationUnits());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#updateExperimentationSettings");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<RandomizationSettingsRep> response = client
              .experimentsBeta
              .updateExperimentationSettings(randomizationUnits, projectKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#updateExperimentationSettings");
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
| **randomizationSettingsPut** | [**RandomizationSettingsPut**](RandomizationSettingsPut.md)|  | |

### Return type

[**RandomizationSettingsRep**](RandomizationSettingsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experimentation settings response |  -  |

<a name="updateSemanticPatch"></a>
# **updateSemanticPatch**
> Experiment updateSemanticPatch(projectKey, environmentKey, experimentKey, experimentPatchInput).execute();

Patch experiment

Update an experiment. Updating an experiment uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://apidocs.launchdarkly.com).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating experiments.  #### updateName  Updates the experiment name.  ##### Parameters  - &#x60;value&#x60;: The new name.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateName\&quot;,     \&quot;value\&quot;: \&quot;Example updated experiment name\&quot;   }] } &#x60;&#x60;&#x60;  #### updateDescription  Updates the experiment description.  ##### Parameters  - &#x60;value&#x60;: The new description.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateDescription\&quot;,     \&quot;value\&quot;: \&quot;Example updated description\&quot;   }] } &#x60;&#x60;&#x60;  #### startIteration  Starts a new iteration for this experiment. You must [create a new iteration](https://apidocs.launchdarkly.com)#operation/createIteration) before calling this instruction.  An iteration may not be started until it meets the following criteria:  * Its associated flag is toggled on and is not archived * Its &#x60;randomizationUnit&#x60; is set * At least one of its &#x60;treatments&#x60; has a non-zero &#x60;allocationPercent&#x60;  ##### Parameters  - &#x60;changeJustification&#x60;: The reason for starting a new iteration. Required when you call &#x60;startIteration&#x60; on an already running experiment, otherwise optional.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;startIteration\&quot;,     \&quot;changeJustification\&quot;: \&quot;It&#39;s time to start a new iteration\&quot;   }] } &#x60;&#x60;&#x60;  #### stopIteration  Stops the current iteration for this experiment.  ##### Parameters  - &#x60;winningTreatmentId&#x60;: The ID of the winning treatment. Treatment IDs are returned as part of the [Get experiment](https://apidocs.launchdarkly.com)#operation/getExperiment) response. They are the &#x60;_id&#x60; of each element in the &#x60;treatments&#x60; array. - &#x60;winningReason&#x60;: The reason for the winner  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;stopIteration\&quot;,     \&quot;winningTreatmentId\&quot;: \&quot;3a548ec2-72ac-4e59-8518-5c24f5609ccf\&quot;,     \&quot;winningReason\&quot;: \&quot;Example reason to stop the iteration\&quot;   }] } &#x60;&#x60;&#x60;  #### archiveExperiment  Archives this experiment. Archived experiments are hidden by default in the LaunchDarkly user interface. You cannot start new iterations for archived experiments.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{ \&quot;kind\&quot;: \&quot;archiveExperiment\&quot; }] } &#x60;&#x60;&#x60;  #### restoreExperiment  Restores an archived experiment. After restoring an experiment, you can start new iterations for it again.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{ \&quot;kind\&quot;: \&quot;restoreExperiment\&quot; }] } &#x60;&#x60;&#x60; 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ExperimentsBetaApi;
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
    String environmentKey = "environmentKey_example"; // The environment key
    String experimentKey = "experimentKey_example"; // The experiment key
    String comment = "comment_example"; // Optional comment describing the update
    try {
      Experiment result = client
              .experimentsBeta
              .updateSemanticPatch(instructions, projectKey, environmentKey, experimentKey)
              .comment(comment)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getId());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getMaintainerId());
      System.out.println(result.getCreationDate());
      System.out.println(result.getEnvironmentKey());
      System.out.println(result.getArchivedDate());
      System.out.println(result.getLinks());
      System.out.println(result.getCurrentIteration());
      System.out.println(result.getDraftIteration());
      System.out.println(result.getPreviousIterations());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#updateSemanticPatch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Experiment> response = client
              .experimentsBeta
              .updateSemanticPatch(instructions, projectKey, environmentKey, experimentKey)
              .comment(comment)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ExperimentsBetaApi#updateSemanticPatch");
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
| **experimentKey** | **String**| The experiment key | |
| **experimentPatchInput** | [**ExperimentPatchInput**](ExperimentPatchInput.md)|  | |

### Return type

[**Experiment**](Experiment.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Experiment response |  -  |

