# SegmentsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getBigSegmentExportInfo**](SegmentsBetaApi.md#getBigSegmentExportInfo) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/exports/{exportID} | Get big segment export |
| [**getImportInfo**](SegmentsBetaApi.md#getImportInfo) | **GET** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/imports/{importID} | Get big segment import |
| [**startBigSegmentExport**](SegmentsBetaApi.md#startBigSegmentExport) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/exports | Create big segment export |
| [**startBigSegmentImport**](SegmentsBetaApi.md#startBigSegmentImport) | **POST** /api/v2/segments/{projectKey}/{environmentKey}/{segmentKey}/imports | Create big segment import |


<a name="getBigSegmentExportInfo"></a>
# **getBigSegmentExportInfo**
> Export getBigSegmentExportInfo(projectKey, environmentKey, segmentKey, exportID).execute();

Get big segment export

Returns information about a big segment export process. This is an export for a synced segment or a list-based segment that can include more than 15,000 entries.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.SegmentsBetaApi;
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
    String segmentKey = "segmentKey_example"; // The segment key
    String exportID = "exportID_example"; // The export ID
    try {
      Export result = client
              .segmentsBeta
              .getBigSegmentExportInfo(projectKey, environmentKey, segmentKey, exportID)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getSegmentKey());
      System.out.println(result.getCreationTime());
      System.out.println(result.getStatus());
      System.out.println(result.getSizeBytes());
      System.out.println(result.getSize());
      System.out.println(result.getInitiator());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsBetaApi#getBigSegmentExportInfo");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Export> response = client
              .segmentsBeta
              .getBigSegmentExportInfo(projectKey, environmentKey, segmentKey, exportID)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsBetaApi#getBigSegmentExportInfo");
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
| **segmentKey** | **String**| The segment key | |
| **exportID** | **String**| The export ID | |

### Return type

[**Export**](Export.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Segment export response |  -  |

<a name="getImportInfo"></a>
# **getImportInfo**
> ModelImport getImportInfo(projectKey, environmentKey, segmentKey, importID).execute();

Get big segment import

Returns information about a big segment import process. This is the import of a list-based segment that can include more than 15,000 entries.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.SegmentsBetaApi;
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
    String segmentKey = "segmentKey_example"; // The segment key
    String importID = "importID_example"; // The import ID
    try {
      ModelImport result = client
              .segmentsBeta
              .getImportInfo(projectKey, environmentKey, segmentKey, importID)
              .execute();
      System.out.println(result);
      System.out.println(result.getId());
      System.out.println(result.getSegmentKey());
      System.out.println(result.getCreationTime());
      System.out.println(result.getMode());
      System.out.println(result.getStatus());
      System.out.println(result.getFiles());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsBetaApi#getImportInfo");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ModelImport> response = client
              .segmentsBeta
              .getImportInfo(projectKey, environmentKey, segmentKey, importID)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsBetaApi#getImportInfo");
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
| **segmentKey** | **String**| The segment key | |
| **importID** | **String**| The import ID | |

### Return type

[**ModelImport**](ModelImport.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Segment import response |  -  |

<a name="startBigSegmentExport"></a>
# **startBigSegmentExport**
> startBigSegmentExport(projectKey, environmentKey, segmentKey).execute();

Create big segment export

Starts a new export process for a big segment. This is an export for a synced segment or a list-based segment that can include more than 15,000 entries.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.SegmentsBetaApi;
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
    String segmentKey = "segmentKey_example"; // The segment key
    try {
      client
              .segmentsBeta
              .startBigSegmentExport(projectKey, environmentKey, segmentKey)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsBetaApi#startBigSegmentExport");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .segmentsBeta
              .startBigSegmentExport(projectKey, environmentKey, segmentKey)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsBetaApi#startBigSegmentExport");
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
| **segmentKey** | **String**| The segment key | |

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
| **200** | Action succeeded |  -  |

<a name="startBigSegmentImport"></a>
# **startBigSegmentImport**
> startBigSegmentImport(projectKey, environmentKey, segmentKey, segmentsBetaStartBigSegmentImportRequest)._file(_file).mode(mode).execute();

Create big segment import

Start a new import process for a big segment. This is an import for a list-based segment that can include more than 15,000 entries.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.SegmentsBetaApi;
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
    String segmentKey = "segmentKey_example"; // The segment key
    File _file = new File("/path/to/file"); // CSV file containing keys
    String mode = "mode_example"; // Import mode. Use either `merge` or `replace`
    try {
      client
              .segmentsBeta
              .startBigSegmentImport(projectKey, environmentKey, segmentKey)
              ._file(_file)
              .mode(mode)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsBetaApi#startBigSegmentImport");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .segmentsBeta
              .startBigSegmentImport(projectKey, environmentKey, segmentKey)
              ._file(_file)
              .mode(mode)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling SegmentsBetaApi#startBigSegmentImport");
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
| **segmentKey** | **String**| The segment key | |
| **segmentsBetaStartBigSegmentImportRequest** | [**SegmentsBetaStartBigSegmentImportRequest**](SegmentsBetaStartBigSegmentImportRequest.md)|  | |
| **_file** | **File**| CSV file containing keys | [optional] |
| **mode** | **String**| Import mode. Use either &#x60;merge&#x60; or &#x60;replace&#x60; | [optional] |

### Return type

null (empty response body)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Import request submitted successfully |  -  |

