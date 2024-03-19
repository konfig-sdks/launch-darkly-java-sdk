# TeamsBetaApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**updateMultipleTeamsSemanticPatch**](TeamsBetaApi.md#updateMultipleTeamsSemanticPatch) | **PATCH** /api/v2/teams | Update teams |


<a name="updateMultipleTeamsSemanticPatch"></a>
# **updateMultipleTeamsSemanticPatch**
> BulkEditTeamsRep updateMultipleTeamsSemanticPatch(teamsPatchInput).execute();

Update teams

Perform a partial update to multiple teams. Updating teams uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://apidocs.launchdarkly.com).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating teams.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating teams&lt;/strong&gt;&lt;/summary&gt;  #### addMembersToTeams  Add the members to teams.  ##### Parameters  - &#x60;memberIDs&#x60;: List of member IDs to add. - &#x60;teamKeys&#x60;: List of teams to update.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addMembersToTeams\&quot;,     \&quot;memberIDs\&quot;: [       \&quot;1234a56b7c89d012345e678f\&quot;     ],     \&quot;teamKeys\&quot;: [       \&quot;example-team-1\&quot;,       \&quot;example-team-2\&quot;     ]   }] } &#x60;&#x60;&#x60;  #### addAllMembersToTeams  Add all members to the team. Members that match any of the filters are **excluded** from the update.  ##### Parameters  - &#x60;teamKeys&#x60;: List of teams to update. - &#x60;filterLastSeen&#x60;: (Optional) A JSON object with one of the following formats:   - &#x60;{\&quot;never\&quot;: true}&#x60; - Members that have never been active, such as those who have not accepted their invitation to LaunchDarkly, or have not logged in after being provisioned via SCIM.   - &#x60;{\&quot;noData\&quot;: true}&#x60; - Members that have not been active since LaunchDarkly began recording last seen timestamps.   - &#x60;{\&quot;before\&quot;: 1608672063611}&#x60; - Members that have not been active since the provided value, which should be a timestamp in Unix epoch milliseconds. - &#x60;filterQuery&#x60;: (Optional) A string that matches against the members&#39; emails and names. It is not case sensitive. - &#x60;filterRoles&#x60;: (Optional) A &#x60;|&#x60; separated list of roles and custom roles. For the purposes of this filtering, &#x60;Owner&#x60; counts as &#x60;Admin&#x60;. - &#x60;filterTeamKey&#x60;: (Optional) A string that matches against the key of the team the members belong to. It is not case sensitive. - &#x60;ignoredMemberIDs&#x60;: (Optional) A list of member IDs.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addAllMembersToTeams\&quot;,     \&quot;teamKeys\&quot;: [       \&quot;example-team-1\&quot;,       \&quot;example-team-2\&quot;     ],     \&quot;filterLastSeen\&quot;: { \&quot;never\&quot;: true }   }] } &#x60;&#x60;&#x60;  &lt;/details&gt; 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.TeamsBetaApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    List<Map<String, Object>> instructions = Arrays.asList(new HashMap<>());
    String comment = "comment_example"; // Optional comment describing the update
    try {
      BulkEditTeamsRep result = client
              .teamsBeta
              .updateMultipleTeamsSemanticPatch(instructions)
              .comment(comment)
              .execute();
      System.out.println(result);
      System.out.println(result.getMemberIDs());
      System.out.println(result.getTeamKeys());
      System.out.println(result.getErrors());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsBetaApi#updateMultipleTeamsSemanticPatch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<BulkEditTeamsRep> response = client
              .teamsBeta
              .updateMultipleTeamsSemanticPatch(instructions)
              .comment(comment)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsBetaApi#updateMultipleTeamsSemanticPatch");
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
| **teamsPatchInput** | [**TeamsPatchInput**](TeamsPatchInput.md)|  | |

### Return type

[**BulkEditTeamsRep**](BulkEditTeamsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Teams response |  -  |

