# InsightsPullRequestsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**listPullRequests**](InsightsPullRequestsBetaApi.md#listPullRequests) | **GET** /api/v2/engineering-insights/pull-requests | List pull requests |


<a name="listPullRequests"></a>
# **listPullRequests**
> PullRequestCollectionRep listPullRequests(projectKey).environmentKey(environmentKey).applicationKey(applicationKey).status(status).query(query).limit(limit).expand(expand).sort(sort).from(from).to(to).after(after).before(before).execute();

List pull requests

Get a list of pull requests  ### Expanding the pull request collection response  LaunchDarkly supports expanding the pull request collection response to include additional fields.  To expand the response, append the &#x60;expand&#x60; query parameter and include the following:  * &#x60;deployments&#x60; includes details on all of the deployments associated with each pull request * &#x60;flagReferences&#x60; includes details on all of the references to flags in each pull request * &#x60;leadTime&#x60; includes details about the lead time of the pull request for each stage  For example, use &#x60;?expand&#x3D;deployments&#x60; to include the &#x60;deployments&#x60; field in the response. By default, this field is **not** included in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.InsightsPullRequestsBetaApi;
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
    String environmentKey = "environmentKey_example"; // Required if you are using the <code>sort</code> parameter's <code>leadTime</code> option to sort pull requests.
    String applicationKey = "applicationKey_example"; // Filter the results to pull requests deployed to a comma separated list of applications
    String status = "status_example"; // Filter results to pull requests with the given status. Options: `open`, `merged`, `closed`, `deployed`.
    String query = "query_example"; // Filter list of pull requests by title or author
    Long limit = 56L; // The number of pull requests to return. Default is 20. Maximum allowed is 100.
    String expand = "expand_example"; // Expand properties in response. Options: `deployments`, `flagReferences`, `leadTime`.
    String sort = "sort_example"; // Sort results. Requires the `environmentKey` to be set. Options: `leadTime` (asc) and `-leadTime` (desc). When query option is excluded, default sort is by created or merged date.
    OffsetDateTime from = OffsetDateTime.now(); // Unix timestamp in milliseconds. Default value is 7 days ago.
    OffsetDateTime to = OffsetDateTime.now(); // Unix timestamp in milliseconds. Default value is now.
    String after = "after_example"; // Identifier used for pagination
    String before = "before_example"; // Identifier used for pagination
    try {
      PullRequestCollectionRep result = client
              .insightsPullRequestsBeta
              .listPullRequests(projectKey)
              .environmentKey(environmentKey)
              .applicationKey(applicationKey)
              .status(status)
              .query(query)
              .limit(limit)
              .expand(expand)
              .sort(sort)
              .from(from)
              .to(to)
              .after(after)
              .before(before)
              .execute();
      System.out.println(result);
      System.out.println(result.getTotalCount());
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsPullRequestsBetaApi#listPullRequests");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<PullRequestCollectionRep> response = client
              .insightsPullRequestsBeta
              .listPullRequests(projectKey)
              .environmentKey(environmentKey)
              .applicationKey(applicationKey)
              .status(status)
              .query(query)
              .limit(limit)
              .expand(expand)
              .sort(sort)
              .from(from)
              .to(to)
              .after(after)
              .before(before)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling InsightsPullRequestsBetaApi#listPullRequests");
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
| **environmentKey** | **String**| Required if you are using the &lt;code&gt;sort&lt;/code&gt; parameter&#39;s &lt;code&gt;leadTime&lt;/code&gt; option to sort pull requests. | [optional] |
| **applicationKey** | **String**| Filter the results to pull requests deployed to a comma separated list of applications | [optional] |
| **status** | **String**| Filter results to pull requests with the given status. Options: &#x60;open&#x60;, &#x60;merged&#x60;, &#x60;closed&#x60;, &#x60;deployed&#x60;. | [optional] |
| **query** | **String**| Filter list of pull requests by title or author | [optional] |
| **limit** | **Long**| The number of pull requests to return. Default is 20. Maximum allowed is 100. | [optional] |
| **expand** | **String**| Expand properties in response. Options: &#x60;deployments&#x60;, &#x60;flagReferences&#x60;, &#x60;leadTime&#x60;. | [optional] |
| **sort** | **String**| Sort results. Requires the &#x60;environmentKey&#x60; to be set. Options: &#x60;leadTime&#x60; (asc) and &#x60;-leadTime&#x60; (desc). When query option is excluded, default sort is by created or merged date. | [optional] |
| **from** | **OffsetDateTime**| Unix timestamp in milliseconds. Default value is 7 days ago. | [optional] |
| **to** | **OffsetDateTime**| Unix timestamp in milliseconds. Default value is now. | [optional] |
| **after** | **String**| Identifier used for pagination | [optional] |
| **before** | **String**| Identifier used for pagination | [optional] |

### Return type

[**PullRequestCollectionRep**](PullRequestCollectionRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Pull request collection response |  -  |

