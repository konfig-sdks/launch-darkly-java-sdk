# OAuth2ClientsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createClient**](OAuth2ClientsApi.md#createClient) | **POST** /api/v2/oauth/clients | Create a LaunchDarkly OAuth 2.0 client |
| [**deleteClientById**](OAuth2ClientsApi.md#deleteClientById) | **DELETE** /api/v2/oauth/clients/{clientId} | Delete OAuth 2.0 client |
| [**getClientById**](OAuth2ClientsApi.md#getClientById) | **GET** /api/v2/oauth/clients/{clientId} | Get client by ID |
| [**list**](OAuth2ClientsApi.md#list) | **GET** /api/v2/oauth/clients | Get clients |
| [**updateClientById**](OAuth2ClientsApi.md#updateClientById) | **PATCH** /api/v2/oauth/clients/{clientId} | Patch client by ID |


<a name="createClient"></a>
# **createClient**
> Client createClient(oauthClientPost).execute();

Create a LaunchDarkly OAuth 2.0 client

Create (register) a LaunchDarkly OAuth2 client. OAuth2 clients allow you to build custom integrations using LaunchDarkly as your identity provider.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.OAuth2ClientsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String description = "description_example"; // Description of your OAuth 2.0 client.
    String name = "name_example"; // The name of your new LaunchDarkly OAuth 2.0 client.
    String redirectUri = "redirectUri_example"; // The redirect URI for your new OAuth 2.0 application. This should be an absolute URL conforming with the standard HTTPS protocol.
    try {
      Client result = client
              .oAuth2Clients
              .createClient()
              .description(description)
              .name(name)
              .redirectUri(redirectUri)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getLinks());
      System.out.println(result.getName());
      System.out.println(result.getAccountId());
      System.out.println(result.getClientId());
      System.out.println(result.getClientSecret());
      System.out.println(result.getRedirectUri());
      System.out.println(result.getCreationDate());
    } catch (ApiException e) {
      System.err.println("Exception when calling OAuth2ClientsApi#createClient");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Client> response = client
              .oAuth2Clients
              .createClient()
              .description(description)
              .name(name)
              .redirectUri(redirectUri)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling OAuth2ClientsApi#createClient");
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
| **oauthClientPost** | [**OauthClientPost**](OauthClientPost.md)|  | |

### Return type

[**Client**](Client.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | OAuth 2.0 client response |  -  |

<a name="deleteClientById"></a>
# **deleteClientById**
> deleteClientById(clientId).execute();

Delete OAuth 2.0 client

Delete an existing OAuth 2.0 client by unique client ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.OAuth2ClientsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String clientId = "clientId_example"; // The client ID
    try {
      client
              .oAuth2Clients
              .deleteClientById(clientId)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling OAuth2ClientsApi#deleteClientById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .oAuth2Clients
              .deleteClientById(clientId)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling OAuth2ClientsApi#deleteClientById");
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
| **clientId** | **String**| The client ID | |

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

<a name="getClientById"></a>
# **getClientById**
> Client getClientById(clientId).execute();

Get client by ID

Get a registered OAuth 2.0 client by unique client ID.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.OAuth2ClientsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String clientId = "clientId_example"; // The client ID
    try {
      Client result = client
              .oAuth2Clients
              .getClientById(clientId)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getLinks());
      System.out.println(result.getName());
      System.out.println(result.getAccountId());
      System.out.println(result.getClientId());
      System.out.println(result.getClientSecret());
      System.out.println(result.getRedirectUri());
      System.out.println(result.getCreationDate());
    } catch (ApiException e) {
      System.err.println("Exception when calling OAuth2ClientsApi#getClientById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Client> response = client
              .oAuth2Clients
              .getClientById(clientId)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling OAuth2ClientsApi#getClientById");
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
| **clientId** | **String**| The client ID | |

### Return type

[**Client**](Client.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OAuth 2.0 client response |  -  |

<a name="list"></a>
# **list**
> ClientCollection list().execute();

Get clients

Get all OAuth 2.0 clients registered by your account.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.OAuth2ClientsApi;
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
      ClientCollection result = client
              .oAuth2Clients
              .list()
              .execute();
      System.out.println(result);
      System.out.println(result.getLinks());
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling OAuth2ClientsApi#list");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<ClientCollection> response = client
              .oAuth2Clients
              .list()
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling OAuth2ClientsApi#list");
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

[**ClientCollection**](ClientCollection.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OAuth 2.0 client collection response |  -  |

<a name="updateClientById"></a>
# **updateClientById**
> Client updateClientById(clientId, patchOperation).execute();

Patch client by ID

Patch an existing OAuth 2.0 client by client ID. Updating an OAuth2 client uses a [JSON patch](https://datatracker.ietf.org/doc/html/rfc6902) representation of the desired changes. To learn more, read [Updates](https://apidocs.launchdarkly.com). Only &#x60;name&#x60;, &#x60;description&#x60;, and &#x60;redirectUri&#x60; may be patched.

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.OAuth2ClientsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String clientId = "clientId_example"; // The client ID
    try {
      Client result = client
              .oAuth2Clients
              .updateClientById(clientId)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getLinks());
      System.out.println(result.getName());
      System.out.println(result.getAccountId());
      System.out.println(result.getClientId());
      System.out.println(result.getClientSecret());
      System.out.println(result.getRedirectUri());
      System.out.println(result.getCreationDate());
    } catch (ApiException e) {
      System.err.println("Exception when calling OAuth2ClientsApi#updateClientById");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Client> response = client
              .oAuth2Clients
              .updateClientById(clientId)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling OAuth2ClientsApi#updateClientById");
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
| **clientId** | **String**| The client ID | |
| **patchOperation** | [**List&lt;PatchOperation&gt;**](PatchOperation.md)|  | |

### Return type

[**Client**](Client.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OAuth 2.0 client response |  -  |

