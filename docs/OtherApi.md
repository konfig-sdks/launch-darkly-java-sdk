# OtherApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getIpList**](OtherApi.md#getIpList) | **GET** /api/v2/public-ip-list | Gets the public IP list |
| [**getOpenapiSpec**](OtherApi.md#getOpenapiSpec) | **GET** /api/v2/openapi.json | Gets the OpenAPI spec in json |
| [**getResourceCategories**](OtherApi.md#getResourceCategories) | **GET** /api/v2 | Root resource |
| [**getVersionInformation**](OtherApi.md#getVersionInformation) | **GET** /api/v2/versions | Get version information |


<a name="getIpList"></a>
# **getIpList**
> IpList getIpList().execute();

Gets the public IP list

Get a list of IP ranges the LaunchDarkly service uses. You can use this list to allow LaunchDarkly through your firewall. We post upcoming changes to this list in advance on our [status page](https://status.launchdarkly.com/). &lt;br /&gt;&lt;br /&gt;In the sandbox, click &#39;Try it&#39; and enter any string in the &#39;Authorization&#39; field to test this endpoint.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.OtherApi;
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
      IpList result = client
              .other
              .getIpList()
              .execute();
      System.out.println(result);
      System.out.println(result.getAddresses());
      System.out.println(result.getOutboundAddresses());
    } catch (ApiException e) {
      System.err.println("Exception when calling OtherApi#getIpList");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<IpList> response = client
              .other
              .getIpList()
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling OtherApi#getIpList");
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

[**IpList**](IpList.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Public IP response |  -  |

<a name="getOpenapiSpec"></a>
# **getOpenapiSpec**
> getOpenapiSpec().execute();

Gets the OpenAPI spec in json

Get the latest version of the OpenAPI specification for LaunchDarkly&#39;s API in JSON format. In the sandbox, click &#39;Try it&#39; and enter any string in the &#39;Authorization&#39; field to test this endpoint.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.OtherApi;
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
      client
              .other
              .getOpenapiSpec()
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling OtherApi#getOpenapiSpec");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .other
              .getOpenapiSpec()
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling OtherApi#getOpenapiSpec");
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

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OpenAPI Spec |  -  |

<a name="getResourceCategories"></a>
# **getResourceCategories**
> RootResponse getResourceCategories().execute();

Root resource

Get all of the resource categories the API supports. In the sandbox, click &#39;Try it&#39; and enter any string in the &#39;Authorization&#39; field to test this endpoint.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.OtherApi;
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
      RootResponse result = client
              .other
              .getResourceCategories()
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling OtherApi#getResourceCategories");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<RootResponse> response = client
              .other
              .getResourceCategories()
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling OtherApi#getResourceCategories");
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

[**RootResponse**](RootResponse.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Root response |  -  |

<a name="getVersionInformation"></a>
# **getVersionInformation**
> VersionsRep getVersionInformation().execute();

Get version information

Get the latest API version, the list of valid API versions in ascending order, and the version being used for this request. These are all in the external, date-based format.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.OtherApi;
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
      VersionsRep result = client
              .other
              .getVersionInformation()
              .execute();
      System.out.println(result);
      System.out.println(result.getValidVersions());
      System.out.println(result.getLatestVersion());
      System.out.println(result.getCurrentVersion());
      System.out.println(result.getBeta());
    } catch (ApiException e) {
      System.err.println("Exception when calling OtherApi#getVersionInformation");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<VersionsRep> response = client
              .other
              .getVersionInformation()
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling OtherApi#getVersionInformation");
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

[**VersionsRep**](VersionsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Versions information response |  -  |

