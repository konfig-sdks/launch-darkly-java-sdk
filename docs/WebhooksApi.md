# WebhooksApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNewWebhook**](WebhooksApi.md#createNewWebhook) | **POST** /api/v2/webhooks | Creates a webhook |
| [**deleteById**](WebhooksApi.md#deleteById) | **DELETE** /api/v2/webhooks/{id} | Delete webhook |
| [**getSingleById**](WebhooksApi.md#getSingleById) | **GET** /api/v2/webhooks/{id} | Get webhook |
| [**listWebhooks**](WebhooksApi.md#listWebhooks) | **GET** /api/v2/webhooks | List webhooks |
| [**updateSettingsPatch**](WebhooksApi.md#updateSettingsPatch) | **PATCH** /api/v2/webhooks/{id} | Update webhook |


<a name="createNewWebhook"></a>
# **createNewWebhook**
> Webhook createNewWebhook(webhookPost).execute();

Creates a webhook

Create a new webhook.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WebhooksApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String url = "url_example"; // The URL of the remote webhook
    Boolean sign = true; // If sign is false, the webhook does not include a signature header, and the secret can be omitted.
    List<String> tags = Arrays.asList(); // List of tags for this webhook
    String name = "name_example"; // A human-readable name for your webhook
    String secret = "secret_example"; // If sign is true, and the secret attribute is omitted, LaunchDarkly automatically generates a secret for you.
    List<StatementPost> statements = Arrays.asList();
    Boolean true = true; // Whether or not this webhook is enabled.
    try {
      Webhook result = client
              .webhooks
              .createNewWebhook(url, sign)
              .tags(tags)
              .name(name)
              .secret(secret)
              .statements(statements)
              .true(true)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getName());
      System.out.println(result.getUrl());
      System.out.println(result.getSecret());
      System.out.println(result.getStatements());
      System.out.println(result.getTrue());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksApi#createNewWebhook");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Webhook> response = client
              .webhooks
              .createNewWebhook(url, sign)
              .tags(tags)
              .name(name)
              .secret(secret)
              .statements(statements)
              .true(true)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksApi#createNewWebhook");
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
| **webhookPost** | [**WebhookPost**](WebhookPost.md)|  | |

### Return type

[**Webhook**](Webhook.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Webhook response |  -  |

<a name="deleteById"></a>
# **deleteById**
> deleteById(id).execute();

Delete webhook

Delete a webhook by ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WebhooksApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String id = "id_example"; // The ID of the webhook to delete
    try {
      client
              .webhooks
              .deleteById(id)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksApi#deleteById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .webhooks
              .deleteById(id)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksApi#deleteById");
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
| **id** | **String**| The ID of the webhook to delete | |

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

<a name="getSingleById"></a>
# **getSingleById**
> Webhook getSingleById(id).execute();

Get webhook

Get a single webhook by ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WebhooksApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String id = "id_example"; // The ID of the webhook
    try {
      Webhook result = client
              .webhooks
              .getSingleById(id)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getName());
      System.out.println(result.getUrl());
      System.out.println(result.getSecret());
      System.out.println(result.getStatements());
      System.out.println(result.getTrue());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksApi#getSingleById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Webhook> response = client
              .webhooks
              .getSingleById(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksApi#getSingleById");
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
| **id** | **String**| The ID of the webhook | |

### Return type

[**Webhook**](Webhook.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Webhook response |  -  |

<a name="listWebhooks"></a>
# **listWebhooks**
> Webhooks listWebhooks().execute();

List webhooks

Fetch a list of all webhooks.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WebhooksApi;
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
      Webhooks result = client
              .webhooks
              .listWebhooks()
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksApi#listWebhooks");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Webhooks> response = client
              .webhooks
              .listWebhooks()
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksApi#listWebhooks");
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

[**Webhooks**](Webhooks.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Webhooks response |  -  |

<a name="updateSettingsPatch"></a>
# **updateSettingsPatch**
> Webhook updateSettingsPatch(id, patchOperation).execute();

Update webhook

Update a webhook&#39;s settings. Updating webhook settings uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.WebhooksApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String id = "id_example"; // The ID of the webhook to update
    try {
      Webhook result = client
              .webhooks
              .updateSettingsPatch(id)
              .execute();
      System.out.println(result);
      System.out.println(result.getTags());
      System.out.println(result.getLinks());
      System.out.println(result.getId());
      System.out.println(result.getName());
      System.out.println(result.getUrl());
      System.out.println(result.getSecret());
      System.out.println(result.getStatements());
      System.out.println(result.getTrue());
      System.out.println(result.getAccess());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksApi#updateSettingsPatch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Webhook> response = client
              .webhooks
              .updateSettingsPatch(id)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksApi#updateSettingsPatch");
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
| **id** | **String**| The ID of the webhook to update | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**Webhook**](Webhook.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Webhook response |  -  |

