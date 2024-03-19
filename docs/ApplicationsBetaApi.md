# ApplicationsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getApplicationByKey**](ApplicationsBetaApi.md#getApplicationByKey) | **GET** /api/v2/applications/{applicationKey} | Get application by key |
| [**getApplicationVersions**](ApplicationsBetaApi.md#getApplicationVersions) | **GET** /api/v2/applications/{applicationKey}/versions | Get application versions by application key |
| [**listApplications**](ApplicationsBetaApi.md#listApplications) | **GET** /api/v2/applications | Get applications |
| [**removeApplication**](ApplicationsBetaApi.md#removeApplication) | **DELETE** /api/v2/applications/{applicationKey} | Delete application |
| [**removeVersion**](ApplicationsBetaApi.md#removeVersion) | **DELETE** /api/v2/applications/{applicationKey}/versions/{versionKey} | Delete application version |
| [**updateApplicationPatch**](ApplicationsBetaApi.md#updateApplicationPatch) | **PATCH** /api/v2/applications/{applicationKey} | Update application |
| [**updateVersionPatch**](ApplicationsBetaApi.md#updateVersionPatch) | **PATCH** /api/v2/applications/{applicationKey}/versions/{versionKey} | Update application version |


<a name="getApplicationByKey"></a>
# **getApplicationByKey**
> ApplicationRep getApplicationByKey(applicationKey).expand(expand).execute();

Get application by key

 Retrieve an application by the application key.  ### Expanding the application response  LaunchDarkly supports expanding the \&quot;Get application\&quot; response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;flags&#x60; includes details on the flags that have been evaluated by the application  For example, use &#x60;?expand&#x3D;flags&#x60; to include the &#x60;flags&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApplicationsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String applicationKey = "applicationKey_example"; // The application key
    String expand = "expand_example"; // A comma-separated list of properties that can reveal additional information in the response. Options: `flags`.
    try {
      ApplicationRep result = client
              .applicationsBeta
              .getApplicationByKey(applicationKey)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getFlags());
      System.out.println(result.getAccess());
      System.out.println(result.getLinks());
      System.out.println(result.getVersion());
      System.out.println(result.getAutoAdded());
      System.out.println(result.getCreationDate());
      System.out.println(result.getKey());
      System.out.println(result.getKind());
      System.out.println(result.getMaintainer());
      System.out.println(result.getName());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApplicationsBetaApi#getApplicationByKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ApplicationRep> response = client
              .applicationsBeta
              .getApplicationByKey(applicationKey)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApplicationsBetaApi#getApplicationByKey");
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
| **applicationKey** | **String**| The application key | |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. Options: &#x60;flags&#x60;. | [optional] |

### Return type

[**ApplicationRep**](ApplicationRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Application response |  -  |

<a name="getApplicationVersions"></a>
# **getApplicationVersions**
> ApplicationVersionsCollectionRep getApplicationVersions(applicationKey).filter(filter).limit(limit).offset(offset).sort(sort).execute();

Get application versions by application key

Get a list of versions for a specific application in an account.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApplicationsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String applicationKey = "applicationKey_example"; // The application key
    String filter = "filter_example"; // Accepts filter by `key`, `name`, `supported`, and `autoAdded`. Example: `filter=key equals 'test-key'`. To learn more about the filter syntax, read [Filtering applications and application versions](https://apidocs.launchdarkly.com)#filtering-contexts-and-context-instances).
    Long limit = 56L; // The number of versions to return. Defaults to 50.
    Long offset = 56L; // Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String sort = "sort_example"; // Accepts sorting order and fields. Fields can be comma separated. Possible fields are `creationDate`, `name`. Examples: `sort=name` sort by names ascending, `sort=-name,creationDate` sort by names descending and creationDate ascending.
    try {
      ApplicationVersionsCollectionRep result = client
              .applicationsBeta
              .getApplicationVersions(applicationKey)
              .filter(filter)
              .limit(limit)
              .offset(offset)
              .sort(sort)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
      System.out.println(result.getTotalCount());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApplicationsBetaApi#getApplicationVersions");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ApplicationVersionsCollectionRep> response = client
              .applicationsBeta
              .getApplicationVersions(applicationKey)
              .filter(filter)
              .limit(limit)
              .offset(offset)
              .sort(sort)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApplicationsBetaApi#getApplicationVersions");
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
| **applicationKey** | **String**| The application key | |
| **filter** | **String**| Accepts filter by &#x60;key&#x60;, &#x60;name&#x60;, &#x60;supported&#x60;, and &#x60;autoAdded&#x60;. Example: &#x60;filter&#x3D;key equals &#39;test-key&#39;&#x60;. To learn more about the filter syntax, read [Filtering applications and application versions](https://apidocs.launchdarkly.com)#filtering-contexts-and-context-instances). | [optional] |
| **limit** | **Long**| The number of versions to return. Defaults to 50. | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **sort** | **String**| Accepts sorting order and fields. Fields can be comma separated. Possible fields are &#x60;creationDate&#x60;, &#x60;name&#x60;. Examples: &#x60;sort&#x3D;name&#x60; sort by names ascending, &#x60;sort&#x3D;-name,creationDate&#x60; sort by names descending and creationDate ascending. | [optional] |

### Return type

[**ApplicationVersionsCollectionRep**](ApplicationVersionsCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Application versions response |  -  |

<a name="listApplications"></a>
# **listApplications**
> ApplicationCollectionRep listApplications().filter(filter).limit(limit).offset(offset).sort(sort).expand(expand).execute();

Get applications

 Get a list of applications.  ### Expanding the applications response  LaunchDarkly supports expanding the \&quot;Get applications\&quot; response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;flags&#x60; includes details on the flags that have been evaluated by the application  For example, use &#x60;?expand&#x3D;flags&#x60; to include the &#x60;flags&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApplicationsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String filter = "filter_example"; // Accepts filter by `key`, `name`, `kind`, and `autoAdded`. Example: `filter=kind anyOf ['mobile', 'server'],key equals 'test-key'`. To learn more about the filter syntax, read [Filtering applications and application versions](https://apidocs.launchdarkly.com)#filtering-contexts-and-context-instances).
    Long limit = 56L; // The number of applications to return. Defaults to 10.
    Long offset = 56L; // Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    String sort = "sort_example"; // Accepts sorting order and fields. Fields can be comma separated. Possible fields are `creationDate`, `name`. Examples: `sort=name` sort by names ascending, `sort=-name,creationDate` sort by names descending and creationDate ascending.
    String expand = "expand_example"; // A comma-separated list of properties that can reveal additional information in the response. Options: `flags`.
    try {
      ApplicationCollectionRep result = client
              .applicationsBeta
              .listApplications()
              .filter(filter)
              .limit(limit)
              .offset(offset)
              .sort(sort)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
      System.out.println(result.getTotalCount());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApplicationsBetaApi#listApplications");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ApplicationCollectionRep> response = client
              .applicationsBeta
              .listApplications()
              .filter(filter)
              .limit(limit)
              .offset(offset)
              .sort(sort)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApplicationsBetaApi#listApplications");
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
| **filter** | **String**| Accepts filter by &#x60;key&#x60;, &#x60;name&#x60;, &#x60;kind&#x60;, and &#x60;autoAdded&#x60;. Example: &#x60;filter&#x3D;kind anyOf [&#39;mobile&#39;, &#39;server&#39;],key equals &#39;test-key&#39;&#x60;. To learn more about the filter syntax, read [Filtering applications and application versions](https://apidocs.launchdarkly.com)#filtering-contexts-and-context-instances). | [optional] |
| **limit** | **Long**| The number of applications to return. Defaults to 10. | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |
| **sort** | **String**| Accepts sorting order and fields. Fields can be comma separated. Possible fields are &#x60;creationDate&#x60;, &#x60;name&#x60;. Examples: &#x60;sort&#x3D;name&#x60; sort by names ascending, &#x60;sort&#x3D;-name,creationDate&#x60; sort by names descending and creationDate ascending. | [optional] |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. Options: &#x60;flags&#x60;. | [optional] |

### Return type

[**ApplicationCollectionRep**](ApplicationCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Applications response |  -  |

<a name="removeApplication"></a>
# **removeApplication**
> removeApplication(applicationKey).execute();

Delete application

Delete an application.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApplicationsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String applicationKey = "applicationKey_example"; // The application key
    try {
      client
              .applicationsBeta
              .removeApplication(applicationKey)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling ApplicationsBetaApi#removeApplication");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .applicationsBeta
              .removeApplication(applicationKey)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling ApplicationsBetaApi#removeApplication");
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
| **applicationKey** | **String**| The application key | |

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

<a name="removeVersion"></a>
# **removeVersion**
> removeVersion(applicationKey, versionKey).execute();

Delete application version

Delete an application version.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApplicationsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String applicationKey = "applicationKey_example"; // The application key
    String versionKey = "versionKey_example"; // The application version key
    try {
      client
              .applicationsBeta
              .removeVersion(applicationKey, versionKey)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling ApplicationsBetaApi#removeVersion");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .applicationsBeta
              .removeVersion(applicationKey, versionKey)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling ApplicationsBetaApi#removeVersion");
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
| **applicationKey** | **String**| The application key | |
| **versionKey** | **String**| The application version key | |

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

<a name="updateApplicationPatch"></a>
# **updateApplicationPatch**
> ApplicationRep updateApplicationPatch(applicationKey, patchOperation).execute();

Update application

Update an application. You can update the &#x60;description&#x60; and &#x60;kind&#x60; fields. Requires a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes to the application. To learn more, read [Updates](https://apidocs.launchdarkly.com).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApplicationsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String applicationKey = "applicationKey_example"; // The application key
    try {
      ApplicationRep result = client
              .applicationsBeta
              .updateApplicationPatch(applicationKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getFlags());
      System.out.println(result.getAccess());
      System.out.println(result.getLinks());
      System.out.println(result.getVersion());
      System.out.println(result.getAutoAdded());
      System.out.println(result.getCreationDate());
      System.out.println(result.getKey());
      System.out.println(result.getKind());
      System.out.println(result.getMaintainer());
      System.out.println(result.getName());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApplicationsBetaApi#updateApplicationPatch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ApplicationRep> response = client
              .applicationsBeta
              .updateApplicationPatch(applicationKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApplicationsBetaApi#updateApplicationPatch");
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
| **applicationKey** | **String**| The application key | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**ApplicationRep**](ApplicationRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Application response |  -  |

<a name="updateVersionPatch"></a>
# **updateVersionPatch**
> ApplicationVersionRep updateVersionPatch(applicationKey, versionKey, patchOperation).execute();

Update application version

Update an application version. You can update the &#x60;supported&#x60; field. Requires a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes to the application version. To learn more, read [Updates](https://apidocs.launchdarkly.com).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.ApplicationsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String applicationKey = "applicationKey_example"; // The application key
    String versionKey = "versionKey_example"; // The application version key
    try {
      ApplicationVersionRep result = client
              .applicationsBeta
              .updateVersionPatch(applicationKey, versionKey)
              .execute();
      System.out.println(result);
      System.out.println(result.getAccess());
      System.out.println(result.getLinks());
      System.out.println(result.getVersion());
      System.out.println(result.getAutoAdded());
      System.out.println(result.getCreationDate());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getSupported());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApplicationsBetaApi#updateVersionPatch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ApplicationVersionRep> response = client
              .applicationsBeta
              .updateVersionPatch(applicationKey, versionKey)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling ApplicationsBetaApi#updateVersionPatch");
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
| **applicationKey** | **String**| The application key | |
| **versionKey** | **String**| The application version key | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**ApplicationVersionRep**](ApplicationVersionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Application version response |  -  |

