# DataExportDestinationsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createDestination**](DataExportDestinationsApi.md#createDestination) | **POST** /api/v2/destinations/{projectKey}/{environmentKey} | Create Data Export destination |
| [**deleteById**](DataExportDestinationsApi.md#deleteById) | **DELETE** /api/v2/destinations/{projectKey}/{environmentKey}/{id} | Delete Data Export destination |
| [**getAll**](DataExportDestinationsApi.md#getAll) | **GET** /api/v2/destinations | List destinations |
| [**getSingleById**](DataExportDestinationsApi.md#getSingleById) | **GET** /api/v2/destinations/{projectKey}/{environmentKey}/{id} | Get destination |
| [**updateDestinationPatch**](DataExportDestinationsApi.md#updateDestinationPatch) | **PATCH** /api/v2/destinations/{projectKey}/{environmentKey}/{id} | Update Data Export destination |


<a name="createDestination"></a>
# **createDestination**
> Destination createDestination(projectKey, environmentKey, destinationPost).execute();

Create Data Export destination

 Create a new Data Export destination.  In the &#x60;config&#x60; request body parameter, the fields required depend on the type of Data Export destination.  &lt;details&gt; &lt;summary&gt;Click to expand &lt;code&gt;config&lt;/code&gt; parameter details&lt;/summary&gt;  #### Azure Event Hubs  To create a Data Export destination with a &#x60;kind&#x60; of &#x60;azure-event-hubs&#x60;, the &#x60;config&#x60; object requires the following fields:  * &#x60;namespace&#x60;: The Event Hub Namespace name * &#x60;name&#x60;: The Event Hub name * &#x60;policyName&#x60;: The shared access signature policy name. You can find your policy name in the settings of your Azure Event Hubs Namespace. * &#x60;policyKey&#x60;: The shared access signature key. You can find your policy key in the settings of your Azure Event Hubs Namespace.  #### Google Cloud Pub/Sub  To create a Data Export destination with a &#x60;kind&#x60; of &#x60;google-pubsub&#x60;, the &#x60;config&#x60; object requires the following fields:  * &#x60;project&#x60;: The Google PubSub project ID for the project to publish to * &#x60;topic&#x60;: The Google PubSub topic ID for the topic to publish to  #### Amazon Kinesis  To create a Data Export destination with a &#x60;kind&#x60; of &#x60;kinesis&#x60;, the &#x60;config&#x60; object requires the following fields:  * &#x60;region&#x60;: The Kinesis stream&#39;s AWS region key * &#x60;roleArn&#x60;: The Amazon Resource Name (ARN) of the AWS role that will be writing to Kinesis * &#x60;streamName&#x60;: The name of the Kinesis stream that LaunchDarkly is sending events to. This is not the ARN of the stream.  #### mParticle  To create a Data Export destination with a &#x60;kind&#x60; of &#x60;mparticle&#x60;, the &#x60;config&#x60; object requires the following fields:  * &#x60;apiKey&#x60;: The mParticle API key * &#x60;secret&#x60;: The mParticle API secret * &#x60;userIdentity&#x60;: The type of identifier you use to identify your end users in mParticle * &#x60;anonymousUserIdentity&#x60;: The type of identifier you use to identify your anonymous end users in mParticle  #### Segment  To create a Data Export destination with a &#x60;kind&#x60; of &#x60;segment&#x60;, the &#x60;config&#x60; object requires the following fields:  * &#x60;writeKey&#x60;: The Segment write key. This is used to authenticate LaunchDarkly&#39;s calls to Segment.  &lt;/details&gt; 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.DataExportDestinationsApi;
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
    String name = "name_example"; // A human-readable name for your Data Export destination
    String kind = "google-pubsub"; // The type of Data Export destination
    Object config = null; // An object with the configuration parameters required for the destination type
    Boolean true = true; // Whether the export is on. Displayed as the integration status in the LaunchDarkly UI.
    try {
      Destination result = client
              .dataExportDestinations
              .createDestination(projectKey, environmentKey)
              .name(name)
              .kind(kind)
              .config(config)
              .true(true)
              .execute();
      System.out.println(result);
      System.out.println(result.getVersion());
      System.out.println(result.getId());
      System.out.println(result.getLinks());
      System.out.println(result.getName());
      System.out.println(result.getKind());
      System.out.println(result.getConfig());
      System.out.println(result.getTrue());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#createDestination");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Destination> response = client
              .dataExportDestinations
              .createDestination(projectKey, environmentKey)
              .name(name)
              .kind(kind)
              .config(config)
              .true(true)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#createDestination");
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
| **destinationPost** | [**DestinationPost**](DestinationPost.md)|  | |

### Return type

[**Destination**](Destination.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Destination response |  -  |

<a name="deleteById"></a>
# **deleteById**
> deleteById(projectKey, environmentKey, id).execute();

Delete Data Export destination

Delete a Data Export destination by ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.DataExportDestinationsApi;
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
    String id = "id_example"; // The Data Export destination ID
    try {
      client
              .dataExportDestinations
              .deleteById(projectKey, environmentKey, id)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#deleteById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .dataExportDestinations
              .deleteById(projectKey, environmentKey, id)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#deleteById");
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
| **id** | **String**| The Data Export destination ID | |

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
| **204** | Destination response |  -  |

<a name="getAll"></a>
# **getAll**
> Destinations getAll().execute();

List destinations

Get a list of Data Export destinations configured across all projects and environments.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.DataExportDestinationsApi;
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
      Destinations result = client
              .dataExportDestinations
              .getAll()
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#getAll");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Destinations> response = client
              .dataExportDestinations
              .getAll()
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#getAll");
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

[**Destinations**](Destinations.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Destination collection response |  -  |

<a name="getSingleById"></a>
# **getSingleById**
> Destination getSingleById(projectKey, environmentKey, id).execute();

Get destination

Get a single Data Export destination by ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.DataExportDestinationsApi;
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
    String id = "id_example"; // The Data Export destination ID
    try {
      Destination result = client
              .dataExportDestinations
              .getSingleById(projectKey, environmentKey, id)
              .execute();
      System.out.println(result);
      System.out.println(result.getVersion());
      System.out.println(result.getId());
      System.out.println(result.getLinks());
      System.out.println(result.getName());
      System.out.println(result.getKind());
      System.out.println(result.getConfig());
      System.out.println(result.getTrue());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#getSingleById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Destination> response = client
              .dataExportDestinations
              .getSingleById(projectKey, environmentKey, id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#getSingleById");
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
| **id** | **String**| The Data Export destination ID | |

### Return type

[**Destination**](Destination.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Destination response |  -  |

<a name="updateDestinationPatch"></a>
# **updateDestinationPatch**
> Destination updateDestinationPatch(projectKey, environmentKey, id, patchOperation).execute();

Update Data Export destination

Update a Data Export destination. Updating a destination uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) or [JSON merge patch](https://datatracker.ietf.org/doc/html/rfc7386) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.DataExportDestinationsApi;
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
    String id = "id_example"; // The Data Export destination ID
    try {
      Destination result = client
              .dataExportDestinations
              .updateDestinationPatch(projectKey, environmentKey, id)
              .execute();
      System.out.println(result);
      System.out.println(result.getVersion());
      System.out.println(result.getId());
      System.out.println(result.getLinks());
      System.out.println(result.getName());
      System.out.println(result.getKind());
      System.out.println(result.getConfig());
      System.out.println(result.getTrue());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#updateDestinationPatch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Destination> response = client
              .dataExportDestinations
              .updateDestinationPatch(projectKey, environmentKey, id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling DataExportDestinationsApi#updateDestinationPatch");
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
| **id** | **String**| The Data Export destination ID | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**Destination**](Destination.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Destination response |  -  |

