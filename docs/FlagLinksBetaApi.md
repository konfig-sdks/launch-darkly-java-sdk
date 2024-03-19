# FlagLinksBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createFlagLink**](FlagLinksBetaApi.md#createFlagLink) | **POST** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey} | Create flag link |
| [**deleteFlagLink**](FlagLinksBetaApi.md#deleteFlagLink) | **DELETE** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey}/{id} | Delete flag link |
| [**listLinks**](FlagLinksBetaApi.md#listLinks) | **GET** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey} | List flag links |
| [**updateFlagLink**](FlagLinksBetaApi.md#updateFlagLink) | **PATCH** /api/v2/flag-links/projects/{projectKey}/flags/{featureFlagKey}/{id} | Update flag link |


<a name="createFlagLink"></a>
# **createFlagLink**
> FlagLinkRep createFlagLink(projectKey, featureFlagKey, flagLinkPost).execute();

Create flag link

Create a new flag link. Flag links let you reference external resources and associate them with your flags.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FlagLinksBetaApi;
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
    String title = "title_example"; // The title of the flag link
    String description = "description_example"; // The description of the flag link
    String key = "key_example"; // The flag link key
    String integrationKey = "integrationKey_example"; // The integration key for an integration whose <code>manifest.json</code> includes the <code>flagLink</code> capability, if this is a flag link for an existing integration. Do not include for URL flag links.
    Long timestamp = 56L;
    String deepLink = "deepLink_example"; // The URL for the external resource you are linking the flag to
    Map<String, String> metadata = new HashMap(); // The metadata required by this integration in order to create a flag link, if this is a flag link for an existing integration. Defined in the integration's <code>manifest.json</code> file under <code>flagLink</code>.
    try {
      FlagLinkRep result = client
              .flagLinksBeta
              .createFlagLink(projectKey, featureFlagKey)
              .title(title)
              .description(description)
              .key(key)
              .integrationKey(integrationKey)
              .timestamp(timestamp)
              .deepLink(deepLink)
              .metadata(metadata)
              .execute();
      System.out.println(result);
      System.out.println(result.getTitle());
      System.out.println(result.getDescription());
      System.out.println(result.getLinks());
      System.out.println(result.getKey());
      System.out.println(result.getIntegrationKey());
      System.out.println(result.getId());
      System.out.println(result.getDeepLink());
      System.out.println(result.getTimestamp());
      System.out.println(result.getMetadata());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getMember());
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagLinksBetaApi#createFlagLink");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FlagLinkRep> response = client
              .flagLinksBeta
              .createFlagLink(projectKey, featureFlagKey)
              .title(title)
              .description(description)
              .key(key)
              .integrationKey(integrationKey)
              .timestamp(timestamp)
              .deepLink(deepLink)
              .metadata(metadata)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagLinksBetaApi#createFlagLink");
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
| **flagLinkPost** | [**FlagLinkPost**](FlagLinkPost.md)|  | |

### Return type

[**FlagLinkRep**](FlagLinkRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Flag link response |  -  |

<a name="deleteFlagLink"></a>
# **deleteFlagLink**
> deleteFlagLink(projectKey, featureFlagKey, id).execute();

Delete flag link

Delete a flag link by ID or key.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FlagLinksBetaApi;
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
    String id = "id_example"; // The flag link ID or Key
    try {
      client
              .flagLinksBeta
              .deleteFlagLink(projectKey, featureFlagKey, id)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagLinksBetaApi#deleteFlagLink");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .flagLinksBeta
              .deleteFlagLink(projectKey, featureFlagKey, id)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagLinksBetaApi#deleteFlagLink");
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
| **id** | **String**| The flag link ID or Key | |

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

<a name="listLinks"></a>
# **listLinks**
> FlagLinkCollectionRep listLinks(projectKey, featureFlagKey).execute();

List flag links

Get a list of all flag links.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FlagLinksBetaApi;
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
      FlagLinkCollectionRep result = client
              .flagLinksBeta
              .listLinks(projectKey, featureFlagKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagLinksBetaApi#listLinks");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FlagLinkCollectionRep> response = client
              .flagLinksBeta
              .listLinks(projectKey, featureFlagKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagLinksBetaApi#listLinks");
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

[**FlagLinkCollectionRep**](FlagLinkCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag link collection response |  -  |

<a name="updateFlagLink"></a>
# **updateFlagLink**
> FlagLinkRep updateFlagLink(projectKey, featureFlagKey, id, patchOperation).execute();

Update flag link

Update a flag link. Updating a flag link uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.FlagLinksBetaApi;
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
    String id = "id_example"; // The flag link ID
    try {
      FlagLinkRep result = client
              .flagLinksBeta
              .updateFlagLink(projectKey, featureFlagKey, id)
              .execute();
      System.out.println(result);
      System.out.println(result.getTitle());
      System.out.println(result.getDescription());
      System.out.println(result.getLinks());
      System.out.println(result.getKey());
      System.out.println(result.getIntegrationKey());
      System.out.println(result.getId());
      System.out.println(result.getDeepLink());
      System.out.println(result.getTimestamp());
      System.out.println(result.getMetadata());
      System.out.println(result.getCreatedAt());
      System.out.println(result.getMember());
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagLinksBetaApi#updateFlagLink");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<FlagLinkRep> response = client
              .flagLinksBeta
              .updateFlagLink(projectKey, featureFlagKey, id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling FlagLinksBetaApi#updateFlagLink");
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
| **id** | **String**| The flag link ID | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**FlagLinkRep**](FlagLinkRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Flag link response |  -  |

