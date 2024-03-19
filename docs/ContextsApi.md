# ContextsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createOrUpdateKind**](ContextsApi.md#createOrUpdateKind) | **PUT** /api/v2/projects/{projectKey}/context-kinds/{key} | Create or update context kind |
| [**deleteContextInstance**](ContextsApi.md#deleteContextInstance) | **DELETE** /api/v2/projects/{projectKey}/environments/{environmentKey}/context-instances/{id} | Delete context instances |
| [**evaluateFlagsForContextInstance**](ContextsApi.md#evaluateFlagsForContextInstance) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/flags/evaluate | Evaluate flags for context instance |
| [**getAttributeNames**](ContextsApi.md#getAttributeNames) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/context-attributes | Get context attribute names |
| [**getByKindAndKey**](ContextsApi.md#getByKindAndKey) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/contexts/{kind}/{key} | Get contexts |
| [**getContextAttributeValues**](ContextsApi.md#getContextAttributeValues) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/context-attributes/{attributeName} | Get context attribute values |
| [**getContextInstances**](ContextsApi.md#getContextInstances) | **GET** /api/v2/projects/{projectKey}/environments/{environmentKey}/context-instances/{id} | Get context instances |
| [**listContextKindsByProject**](ContextsApi.md#listContextKindsByProject) | **GET** /api/v2/projects/{projectKey}/context-kinds | Get context kinds |
| [**searchContextInstances**](ContextsApi.md#searchContextInstances) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/context-instances/search | Search for context instances |
| [**searchContexts**](ContextsApi.md#searchContexts) | **POST** /api/v2/projects/{projectKey}/environments/{environmentKey}/contexts/search | Search for contexts |


<a name="createOrUpdateKind"></a>
# **createOrUpdateKind**
> UpsertResponseRep createOrUpdateKind(projectKey, key, upsertContextKindPayload).execute();

Create or update context kind

Create or update a context kind by key. Only the included fields will be updated.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ContextsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String name = "name_example"; // The context kind name
    String projectKey = "projectKey_example"; // The project key
    String key = "key_example"; // The context kind key
    String description = "description_example"; // The context kind description
    Integer version = 56; // The context kind version. If not specified when the context kind is created, defaults to 1.
    Boolean hideInTargeting = true; // Alias for archived.
    Boolean archived = true; // Whether the context kind is archived. Archived context kinds are unavailable for targeting.
    try {
      UpsertResponseRep result = client
              .contexts
              .createOrUpdateKind(name, projectKey, key)
              .description(description)
              .version(version)
              .hideInTargeting(hideInTargeting)
              .archived(archived)
              .execute();
      System.out.println(result);
      System.out.println(result.getStatus());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#createOrUpdateKind");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<UpsertResponseRep> response = client
              .contexts
              .createOrUpdateKind(name, projectKey, key)
              .description(description)
              .version(version)
              .hideInTargeting(hideInTargeting)
              .archived(archived)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#createOrUpdateKind");
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
| **key** | **String**| The context kind key | |
| **upsertContextKindPayload** | [**UpsertContextKindPayload**](UpsertContextKindPayload.md)|  | |

### Return type

[**UpsertResponseRep**](UpsertResponseRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context kind upsert response |  -  |

<a name="deleteContextInstance"></a>
# **deleteContextInstance**
> deleteContextInstance(projectKey, environmentKey, id).execute();

Delete context instances

Delete context instances by ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ContextsApi;
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
    String id = "id_example"; // The context instance ID
    try {
      client
              .contexts
              .deleteContextInstance(projectKey, environmentKey, id)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#deleteContextInstance");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .contexts
              .deleteContextInstance(projectKey, environmentKey, id)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#deleteContextInstance");
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
| **id** | **String**| The context instance ID | |

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

<a name="evaluateFlagsForContextInstance"></a>
# **evaluateFlagsForContextInstance**
> ContextInstanceEvaluations evaluateFlagsForContextInstance(projectKey, environmentKey, requestBody).limit(limit).offset(offset).sort(sort).filter(filter).execute();

Evaluate flags for context instance

Evaluate flags for a context instance, for example, to determine the expected flag variation. **Do not use this API instead of an SDK.** The LaunchDarkly SDKs are specialized for the tasks of evaluating feature flags in your application at scale and generating analytics events based on those evaluations. This API is not designed for that use case. Any evaluations you perform with this API will not be reflected in features such as flag statuses and flag insights. Context instances evaluated by this API will not appear in the Contexts list. To learn more, read [Comparing LaunchDarkly&#39;s SDKs and REST API](https://docs.launchdarkly.com/guide/api/comparing-sdk-rest-api).  ### Filtering   LaunchDarkly supports the &#x60;filter&#x60; query param for filtering, with the following fields:  - &#x60;query&#x60; filters for a string that matches against the flags&#39; keys and names. It is not case sensitive. For example: &#x60;filter&#x3D;query equals dark-mode&#x60;. - &#x60;tags&#x60; filters the list to flags that have all of the tags in the list. For example: &#x60;filter&#x3D;tags contains [\&quot;beta\&quot;,\&quot;q1\&quot;]&#x60;.  You can also apply multiple filters at once. For example, setting &#x60;filter&#x3D;query equals dark-mode, tags contains [\&quot;beta\&quot;,\&quot;q1\&quot;]&#x60; matches flags which match the key or name &#x60;dark-mode&#x60; and are tagged &#x60;beta&#x60; and &#x60;q1&#x60;. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ContextsApi;
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
    Long limit = 56L; // The number of feature flags to return. Defaults to -1, which returns all flags
    Long offset = 56L; // Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String sort = "sort_example"; // A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order
    String filter = "filter_example"; // A comma-separated list of filters. Each filter is of the form `field operator value`. Supported fields are explained above.
    try {
      ContextInstanceEvaluations result = client
              .contexts
              .evaluateFlagsForContextInstance(projectKey, environmentKey)
              .limit(limit)
              .offset(offset)
              .sort(sort)
              .filter(filter)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getTotalCount());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#evaluateFlagsForContextInstance");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ContextInstanceEvaluations> response = client
              .contexts
              .evaluateFlagsForContextInstance(projectKey, environmentKey)
              .limit(limit)
              .offset(offset)
              .sort(sort)
              .filter(filter)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#evaluateFlagsForContextInstance");
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
| **requestBody** | [**Map&lt;String, Object&gt;**](Object.md)|  | |
| **limit** | **Long**| The number of feature flags to return. Defaults to -1, which returns all flags | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **sort** | **String**| A comma-separated list of fields to sort by. Fields prefixed by a dash ( - ) sort in descending order | [optional] |
| **filter** | **String**| A comma-separated list of filters. Each filter is of the form &#x60;field operator value&#x60;. Supported fields are explained above. | [optional] |

### Return type

[**ContextInstanceEvaluations**](ContextInstanceEvaluations.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag evaluation collection response |  -  |

<a name="getAttributeNames"></a>
# **getAttributeNames**
> ContextAttributeNamesCollection getAttributeNames(projectKey, environmentKey).filter(filter).execute();

Get context attribute names

Get context attribute names. Returns only the first 100 attribute names per context.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ContextsApi;
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
    String filter = "filter_example"; // A comma-separated list of context filters. This endpoint only accepts `kind` filters, with the `equals` operator, and `name` filters, with the `startsWith` operator. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com).
    try {
      ContextAttributeNamesCollection result = client
              .contexts
              .getAttributeNames(projectKey, environmentKey)
              .filter(filter)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#getAttributeNames");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ContextAttributeNamesCollection> response = client
              .contexts
              .getAttributeNames(projectKey, environmentKey)
              .filter(filter)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#getAttributeNames");
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
| **filter** | **String**| A comma-separated list of context filters. This endpoint only accepts &#x60;kind&#x60; filters, with the &#x60;equals&#x60; operator, and &#x60;name&#x60; filters, with the &#x60;startsWith&#x60; operator. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). | [optional] |

### Return type

[**ContextAttributeNamesCollection**](ContextAttributeNamesCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context attribute names collection response |  -  |

<a name="getByKindAndKey"></a>
# **getByKindAndKey**
> Contexts getByKindAndKey(projectKey, environmentKey, kind, key).limit(limit).continuationToken(continuationToken).sort(sort).filter(filter).includeTotalCount(includeTotalCount).execute();

Get contexts

Get contexts based on kind and key.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ContextsApi;
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
    String kind = "kind_example"; // The context kind
    String key = "key_example"; // The context key
    Long limit = 56L; // Specifies the maximum number of items in the collection to return (max: 50, default: 20)
    String continuationToken = "continuationToken_example"; // Limits results to contexts with sort values after the value specified. You can use this for pagination, however, we recommend using the `next` link we provide instead.
    String sort = "sort_example"; // Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying `ts` for this value, or descending order by specifying `-ts`.
    String filter = "filter_example"; // A comma-separated list of context filters. This endpoint only accepts an `applicationId` filter. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com).
    Boolean includeTotalCount = true; // Specifies whether to include or omit the total count of matching contexts. Defaults to true.
    try {
      Contexts result = client
              .contexts
              .getByKindAndKey(projectKey, environmentKey, kind, key)
              .limit(limit)
              .continuationToken(continuationToken)
              .sort(sort)
              .filter(filter)
              .includeTotalCount(includeTotalCount)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getTotalCount());
      System.out.println(result.getEnvironmentId());
      System.out.println(result.getContinuationToken());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#getByKindAndKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Contexts> response = client
              .contexts
              .getByKindAndKey(projectKey, environmentKey, kind, key)
              .limit(limit)
              .continuationToken(continuationToken)
              .sort(sort)
              .filter(filter)
              .includeTotalCount(includeTotalCount)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#getByKindAndKey");
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
| **kind** | **String**| The context kind | |
| **key** | **String**| The context key | |
| **limit** | **Long**| Specifies the maximum number of items in the collection to return (max: 50, default: 20) | [optional] |
| **continuationToken** | **String**| Limits results to contexts with sort values after the value specified. You can use this for pagination, however, we recommend using the &#x60;next&#x60; link we provide instead. | [optional] |
| **sort** | **String**| Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying &#x60;ts&#x60; for this value, or descending order by specifying &#x60;-ts&#x60;. | [optional] |
| **filter** | **String**| A comma-separated list of context filters. This endpoint only accepts an &#x60;applicationId&#x60; filter. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). | [optional] |
| **includeTotalCount** | **Boolean**| Specifies whether to include or omit the total count of matching contexts. Defaults to true. | [optional] |

### Return type

[**Contexts**](Contexts.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Contexts collection response |  -  |

<a name="getContextAttributeValues"></a>
# **getContextAttributeValues**
> ContextAttributeValuesCollection getContextAttributeValues(projectKey, environmentKey, attributeName).filter(filter).execute();

Get context attribute values

Get context attribute values.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ContextsApi;
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
    String attributeName = "attributeName_example"; // The attribute name
    String filter = "filter_example"; // A comma-separated list of context filters. This endpoint only accepts `kind` filters, with the `equals` operator, and `value` filters, with the `startsWith` operator. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com).
    try {
      ContextAttributeValuesCollection result = client
              .contexts
              .getContextAttributeValues(projectKey, environmentKey, attributeName)
              .filter(filter)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#getContextAttributeValues");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ContextAttributeValuesCollection> response = client
              .contexts
              .getContextAttributeValues(projectKey, environmentKey, attributeName)
              .filter(filter)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#getContextAttributeValues");
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
| **attributeName** | **String**| The attribute name | |
| **filter** | **String**| A comma-separated list of context filters. This endpoint only accepts &#x60;kind&#x60; filters, with the &#x60;equals&#x60; operator, and &#x60;value&#x60; filters, with the &#x60;startsWith&#x60; operator. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). | [optional] |

### Return type

[**ContextAttributeValuesCollection**](ContextAttributeValuesCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context attribute values collection response |  -  |

<a name="getContextInstances"></a>
# **getContextInstances**
> ContextInstances getContextInstances(projectKey, environmentKey, id).limit(limit).continuationToken(continuationToken).sort(sort).filter(filter).includeTotalCount(includeTotalCount).execute();

Get context instances

Get context instances by ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ContextsApi;
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
    String id = "id_example"; // The context instance ID
    Long limit = 56L; // Specifies the maximum number of context instances to return (max: 50, default: 20)
    String continuationToken = "continuationToken_example"; // Limits results to context instances with sort values after the value specified. You can use this for pagination, however, we recommend using the `next` link we provide instead.
    String sort = "sort_example"; // Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying `ts` for this value, or descending order by specifying `-ts`.
    String filter = "filter_example"; // A comma-separated list of context filters. This endpoint only accepts an `applicationId` filter. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com).
    Boolean includeTotalCount = true; // Specifies whether to include or omit the total count of matching context instances. Defaults to true.
    try {
      ContextInstances result = client
              .contexts
              .getContextInstances(projectKey, environmentKey, id)
              .limit(limit)
              .continuationToken(continuationToken)
              .sort(sort)
              .filter(filter)
              .includeTotalCount(includeTotalCount)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getTotalCount());
      System.out.println(result.getEnvironmentId());
      System.out.println(result.getContinuationToken());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#getContextInstances");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ContextInstances> response = client
              .contexts
              .getContextInstances(projectKey, environmentKey, id)
              .limit(limit)
              .continuationToken(continuationToken)
              .sort(sort)
              .filter(filter)
              .includeTotalCount(includeTotalCount)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#getContextInstances");
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
| **id** | **String**| The context instance ID | |
| **limit** | **Long**| Specifies the maximum number of context instances to return (max: 50, default: 20) | [optional] |
| **continuationToken** | **String**| Limits results to context instances with sort values after the value specified. You can use this for pagination, however, we recommend using the &#x60;next&#x60; link we provide instead. | [optional] |
| **sort** | **String**| Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying &#x60;ts&#x60; for this value, or descending order by specifying &#x60;-ts&#x60;. | [optional] |
| **filter** | **String**| A comma-separated list of context filters. This endpoint only accepts an &#x60;applicationId&#x60; filter. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). | [optional] |
| **includeTotalCount** | **Boolean**| Specifies whether to include or omit the total count of matching context instances. Defaults to true. | [optional] |

### Return type

[**ContextInstances**](ContextInstances.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context instances collection response |  -  |

<a name="listContextKindsByProject"></a>
# **listContextKindsByProject**
> ContextKindsCollectionRep listContextKindsByProject(projectKey).execute();

Get context kinds

Get all context kinds for a given project.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ContextsApi;
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
      ContextKindsCollectionRep result = client
              .contexts
              .listContextKindsByProject(projectKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#listContextKindsByProject");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ContextKindsCollectionRep> response = client
              .contexts
              .listContextKindsByProject(projectKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#listContextKindsByProject");
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

[**ContextKindsCollectionRep**](ContextKindsCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context kinds collection response |  -  |

<a name="searchContextInstances"></a>
# **searchContextInstances**
> ContextInstances searchContextInstances(projectKey, environmentKey, contextInstanceSearch).limit(limit).continuationToken(continuationToken).sort(sort).filter(filter).includeTotalCount(includeTotalCount).execute();

Search for context instances

 Search for context instances.  You can use either the query parameters or the request body parameters. If both are provided, there is an error.  To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). To learn more about context instances, read [Understanding context instances](https://docs.launchdarkly.com/home/contexts#understanding-context-instances). 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ContextsApi;
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
    String filter = "filter_example"; // A collection of context instance filters
    String sort = "sort_example"; // Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying <code>ts</code> for this value, or descending order by specifying <code>-ts</code>.
    Integer limit = 56; // Specifies the maximum number of items in the collection to return (max: 50, default: 20)
    String continuationToken = "continuationToken_example"; // Limits results to context instances with sort values after the value specified. You can use this for pagination, however, we recommend using the <code>next</code> link instead, because this value is an obfuscated string.
    Long limit = 56L; // Specifies the maximum number of items in the collection to return (max: 50, default: 20)
    String continuationToken = "continuationToken_example"; // Limits results to context instances with sort values after the value specified. You can use this for pagination, however, we recommend using the `next` link we provide instead.
    String sort = "sort_example"; // Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying `ts` for this value, or descending order by specifying `-ts`.
    String filter = "filter_example"; // A comma-separated list of context filters. This endpoint only accepts an `applicationId` filter. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com).
    Boolean includeTotalCount = true; // Specifies whether to include or omit the total count of matching context instances. Defaults to true.
    try {
      ContextInstances result = client
              .contexts
              .searchContextInstances(projectKey, environmentKey)
              .filter(filter)
              .sort(sort)
              .limit(limit)
              .continuationToken(continuationToken)
              .limit(limit)
              .continuationToken(continuationToken)
              .sort(sort)
              .filter(filter)
              .includeTotalCount(includeTotalCount)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getTotalCount());
      System.out.println(result.getEnvironmentId());
      System.out.println(result.getContinuationToken());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#searchContextInstances");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ContextInstances> response = client
              .contexts
              .searchContextInstances(projectKey, environmentKey)
              .filter(filter)
              .sort(sort)
              .limit(limit)
              .continuationToken(continuationToken)
              .limit(limit)
              .continuationToken(continuationToken)
              .sort(sort)
              .filter(filter)
              .includeTotalCount(includeTotalCount)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#searchContextInstances");
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
| **contextInstanceSearch** | [**ContextInstanceSearch**](ContextInstanceSearch.md)|  | |
| **limit** | **Long**| Specifies the maximum number of items in the collection to return (max: 50, default: 20) | [optional] |
| **continuationToken** | **String**| Limits results to context instances with sort values after the value specified. You can use this for pagination, however, we recommend using the &#x60;next&#x60; link we provide instead. | [optional] |
| **sort** | **String**| Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying &#x60;ts&#x60; for this value, or descending order by specifying &#x60;-ts&#x60;. | [optional] |
| **filter** | **String**| A comma-separated list of context filters. This endpoint only accepts an &#x60;applicationId&#x60; filter. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). | [optional] |
| **includeTotalCount** | **Boolean**| Specifies whether to include or omit the total count of matching context instances. Defaults to true. | [optional] |

### Return type

[**ContextInstances**](ContextInstances.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Context instances collection response |  -  |

<a name="searchContexts"></a>
# **searchContexts**
> Contexts searchContexts(projectKey, environmentKey, contextSearch).limit(limit).continuationToken(continuationToken).sort(sort).filter(filter).includeTotalCount(includeTotalCount).execute();

Search for contexts

 Search for contexts.  You can use either the query parameters or the request body parameters. If both are provided, there is an error.  To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). To learn more about contexts, read [Understanding contexts and context kinds](https://docs.launchdarkly.com/home/contexts#understanding-contexts-and-context-kinds). 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ContextsApi;
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
    String filter = "filter_example"; // A collection of context filters
    String sort = "sort_example"; // Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying <code>ts</code> for this value, or descending order by specifying <code>-ts</code>.
    Integer limit = 56; // Specifies the maximum number of items in the collection to return (max: 50, default: 20)
    String continuationToken = "continuationToken_example"; // Limits results to contexts with sort values after the value specified. You can use this for pagination, however, we recommend using the <code>next</code> link instead, because this value is an obfuscated string.
    Long limit = 56L; // Specifies the maximum number of items in the collection to return (max: 50, default: 20)
    String continuationToken = "continuationToken_example"; // Limits results to contexts with sort values after the value specified. You can use this for pagination, however, we recommend using the `next` link we provide instead.
    String sort = "sort_example"; // Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying `ts` for this value, or descending order by specifying `-ts`.
    String filter = "filter_example"; // A comma-separated list of context filters. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com).
    Boolean includeTotalCount = true; // Specifies whether to include or omit the total count of matching contexts. Defaults to true.
    try {
      Contexts result = client
              .contexts
              .searchContexts(projectKey, environmentKey)
              .filter(filter)
              .sort(sort)
              .limit(limit)
              .continuationToken(continuationToken)
              .limit(limit)
              .continuationToken(continuationToken)
              .sort(sort)
              .filter(filter)
              .includeTotalCount(includeTotalCount)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getTotalCount());
      System.out.println(result.getEnvironmentId());
      System.out.println(result.getContinuationToken());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#searchContexts");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Contexts> response = client
              .contexts
              .searchContexts(projectKey, environmentKey)
              .filter(filter)
              .sort(sort)
              .limit(limit)
              .continuationToken(continuationToken)
              .limit(limit)
              .continuationToken(continuationToken)
              .sort(sort)
              .filter(filter)
              .includeTotalCount(includeTotalCount)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ContextsApi#searchContexts");
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
| **contextSearch** | [**ContextSearch**](ContextSearch.md)|  | |
| **limit** | **Long**| Specifies the maximum number of items in the collection to return (max: 50, default: 20) | [optional] |
| **continuationToken** | **String**| Limits results to contexts with sort values after the value specified. You can use this for pagination, however, we recommend using the &#x60;next&#x60; link we provide instead. | [optional] |
| **sort** | **String**| Specifies a field by which to sort. LaunchDarkly supports sorting by timestamp in ascending order by specifying &#x60;ts&#x60; for this value, or descending order by specifying &#x60;-ts&#x60;. | [optional] |
| **filter** | **String**| A comma-separated list of context filters. To learn more about the filter syntax, read [Filtering contexts and context instances](https://apidocs.launchdarkly.com). | [optional] |
| **includeTotalCount** | **Boolean**| Specifies whether to include or omit the total count of matching contexts. Defaults to true. | [optional] |

### Return type

[**Contexts**](Contexts.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Contexts collection response |  -  |

