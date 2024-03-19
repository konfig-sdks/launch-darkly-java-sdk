# AuditLogApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**detailEntry**](AuditLogApi.md#detailEntry) | **GET** /api/v2/auditlog/{id} | Get audit log entry |
| [**listAuditLogEntries**](AuditLogApi.md#listAuditLogEntries) | **GET** /api/v2/auditlog | List audit log entries |


<a name="detailEntry"></a>
# **detailEntry**
> AuditLogEntryRep detailEntry(id).execute();

Get audit log entry

Fetch a detailed audit log entry representation. The detailed representation includes several fields that are not present in the summary representation, including:  - &#x60;delta&#x60;: the JSON patch body that was used in the request to update the entity - &#x60;previousVersion&#x60;: a JSON representation of the previous version of the entity - &#x60;currentVersion&#x60;: a JSON representation of the current version of the entity 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.AuditLogApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String id = "id_example"; // The ID of the audit log entry
    try {
      AuditLogEntryRep result = client
              .auditLog
              .detailEntry(id)
              .execute();
      System.out.println(result);
      System.out.println(result.getTitle());
      System.out.println(result.getDescription());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getAccountId());
      System.out.println(result.getDate());
      System.out.println(result.getAccesses());
      System.out.println(result.getKind());
      System.out.println(result.getName());
      System.out.println(result.getShortDescription());
      System.out.println(result.getComment());
      System.out.println(result.getSubject());
      System.out.println(result.getMember());
      System.out.println(result.getToken());
      System.out.println(result.getApp());
      System.out.println(result.getTitleVerb());
      System.out.println(result.getTarget());
      System.out.println(result.getParent());
      System.out.println(result.getDelta());
      System.out.println(result.getTriggerBody());
      System.out.println(result.getMerge());
      System.out.println(result.getPreviousVersion());
      System.out.println(result.getCurrentVersion());
      System.out.println(result.getSubentries());
    } catch (ApiException e) {
      System.err.println("Exception when calling AuditLogApi#detailEntry");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<AuditLogEntryRep> response = client
              .auditLog
              .detailEntry(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AuditLogApi#detailEntry");
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
| **id** | **String**| The ID of the audit log entry | |

### Return type

[**AuditLogEntryRep**](AuditLogEntryRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Audit log entry response |  -  |

<a name="listAuditLogEntries"></a>
# **listAuditLogEntries**
> AuditLogEntryListingRepCollection listAuditLogEntries().before(before).after(after).q(q).limit(limit).spec(spec).execute();

List audit log entries

Get a list of all audit log entries. The query parameters let you restrict the results that return by date ranges, resource specifiers, or a full-text search query.  LaunchDarkly uses a resource specifier syntax to name resources or collections of resources. To learn more, read [Understanding the resource specifier syntax](https://docs.launchdarkly.com/home/members/role-resources#understanding-the-resource-specifier-syntax). 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.AuditLogApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    Long before = 56L; // A timestamp filter, expressed as a Unix epoch time in milliseconds.  All entries this returns occurred before the timestamp.
    Long after = 56L; // A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries this returns occurred after the timestamp.
    String q = "q_example"; // Text to search for. You can search for the full or partial name of the resource.
    Long limit = 56L; // A limit on the number of audit log entries that return. Set between 1 and 20. The default is 10.
    String spec = "spec_example"; // A resource specifier that lets you filter audit log listings by resource
    try {
      AuditLogEntryListingRepCollection result = client
              .auditLog
              .listAuditLogEntries()
              .before(before)
              .after(after)
              .q(q)
              .limit(limit)
              .spec(spec)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling AuditLogApi#listAuditLogEntries");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<AuditLogEntryListingRepCollection> response = client
              .auditLog
              .listAuditLogEntries()
              .before(before)
              .after(after)
              .q(q)
              .limit(limit)
              .spec(spec)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling AuditLogApi#listAuditLogEntries");
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
| **before** | **Long**| A timestamp filter, expressed as a Unix epoch time in milliseconds.  All entries this returns occurred before the timestamp. | [optional] |
| **after** | **Long**| A timestamp filter, expressed as a Unix epoch time in milliseconds. All entries this returns occurred after the timestamp. | [optional] |
| **q** | **String**| Text to search for. You can search for the full or partial name of the resource. | [optional] |
| **limit** | **Long**| A limit on the number of audit log entries that return. Set between 1 and 20. The default is 10. | [optional] |
| **spec** | **String**| A resource specifier that lets you filter audit log listings by resource | [optional] |

### Return type

[**AuditLogEntryListingRepCollection**](AuditLogEntryListingRepCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Audit log entries response |  -  |

