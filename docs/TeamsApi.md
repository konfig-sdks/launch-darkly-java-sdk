# TeamsApi

All URIs are relative to *https://app.launchdarkly.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addMultipleMembersToTeam**](TeamsApi.md#addMultipleMembersToTeam) | **POST** /api/v2/teams/{teamKey}/members | Add multiple members to team |
| [**createTeam**](TeamsApi.md#createTeam) | **POST** /api/v2/teams | Create team |
| [**getByTeamKey**](TeamsApi.md#getByTeamKey) | **GET** /api/v2/teams/{teamKey} | Get team |
| [**getCustomRoles**](TeamsApi.md#getCustomRoles) | **GET** /api/v2/teams/{teamKey}/roles | Get team custom roles |
| [**getMaintainers**](TeamsApi.md#getMaintainers) | **GET** /api/v2/teams/{teamKey}/maintainers | Get team maintainers |
| [**listTeams**](TeamsApi.md#listTeams) | **GET** /api/v2/teams | List teams |
| [**removeByTeamKey**](TeamsApi.md#removeByTeamKey) | **DELETE** /api/v2/teams/{teamKey} | Delete team |
| [**updateSemanticPatch**](TeamsApi.md#updateSemanticPatch) | **PATCH** /api/v2/teams/{teamKey} | Update team |


<a name="addMultipleMembersToTeam"></a>
# **addMultipleMembersToTeam**
> TeamImportsRep addMultipleMembersToTeam(teamKey, teamsAddMultipleMembersToTeamRequest)._file(_file).execute();

Add multiple members to team

Add multiple members to an existing team by uploading a CSV file of member email addresses. Your CSV file must include email addresses in the first column. You can include data in additional columns, but LaunchDarkly ignores all data outside the first column. Headers are optional. To learn more, read [Managing team members](https://docs.launchdarkly.com/home/teams/managing#managing-team-members).  **Members are only added on a &#x60;201&#x60; response.** A &#x60;207&#x60; indicates the CSV file contains a combination of valid and invalid entries. A &#x60;207&#x60; results in no members being added to the team.  On a &#x60;207&#x60; response, if an entry contains bad input, the &#x60;message&#x60; field contains the row number as well as the reason for the error. The &#x60;message&#x60; field is omitted if the entry is valid.  Example &#x60;207&#x60; response: &#x60;&#x60;&#x60;json {   \&quot;items\&quot;: [     {       \&quot;status\&quot;: \&quot;success\&quot;,       \&quot;value\&quot;: \&quot;new-team-member@acme.com\&quot;     },     {       \&quot;message\&quot;: \&quot;Line 2: empty row\&quot;,       \&quot;status\&quot;: \&quot;error\&quot;,       \&quot;value\&quot;: \&quot;\&quot;     },     {       \&quot;message\&quot;: \&quot;Line 3: email already exists in the specified team\&quot;,       \&quot;status\&quot;: \&quot;error\&quot;,       \&quot;value\&quot;: \&quot;existing-team-member@acme.com\&quot;     },     {       \&quot;message\&quot;: \&quot;Line 4: invalid email formatting\&quot;,       \&quot;status\&quot;: \&quot;error\&quot;,       \&quot;value\&quot;: \&quot;invalid email format\&quot;     }   ] } &#x60;&#x60;&#x60;  Message | Resolution --- | --- Empty row | This line is blank. Add an email address and try again. Duplicate entry | This email address appears in the file twice. Remove the email from the file and try again. Email already exists in the specified team | This member is already on your team. Remove the email from the file and try again. Invalid formatting | This email address is not formatted correctly. Fix the formatting and try again. Email does not belong to a LaunchDarkly member | The email address doesn&#39;t belong to a LaunchDarkly account member. Invite them to LaunchDarkly, then re-add them to the team.  On a &#x60;400&#x60; response, the &#x60;message&#x60; field may contain errors specific to this endpoint.  Example &#x60;400&#x60; response: &#x60;&#x60;&#x60;json {   \&quot;code\&quot;: \&quot;invalid_request\&quot;,   \&quot;message\&quot;: \&quot;Unable to process file\&quot; } &#x60;&#x60;&#x60;  Message | Resolution --- | --- Unable to process file | LaunchDarkly could not process the file for an unspecified reason. Review your file for errors and try again. File exceeds 25mb | Break up your file into multiple files of less than 25mbs each. All emails have invalid formatting | None of the email addresses in the file are in the correct format. Fix the formatting and try again. All emails belong to existing team members | All listed members are already on this team. Populate the file with member emails that do not belong to the team and try again. File is empty | The CSV file does not contain any email addresses. Populate the file and try again. No emails belong to members of your LaunchDarkly organization | None of the email addresses belong to members of your LaunchDarkly account. Invite these members to LaunchDarkly, then re-add them to the team. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.TeamsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String teamKey = "teamKey_example"; // The team key
    File _file = new File("/path/to/file"); // CSV file containing email addresses
    try {
      TeamImportsRep result = client
              .teams
              .addMultipleMembersToTeam(teamKey)
              ._file(_file)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#addMultipleMembersToTeam");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<TeamImportsRep> response = client
              .teams
              .addMultipleMembersToTeam(teamKey)
              ._file(_file)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#addMultipleMembersToTeam");
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
| **teamKey** | **String**| The team key | |
| **teamsAddMultipleMembersToTeamRequest** | [**TeamsAddMultipleMembersToTeamRequest**](TeamsAddMultipleMembersToTeamRequest.md)|  | |
| **_file** | **File**| CSV file containing email addresses | [optional] |

### Return type

[**TeamImportsRep**](TeamImportsRep.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Team member imports response |  -  |
| **207** | Partial Success |  -  |

<a name="createTeam"></a>
# **createTeam**
> Team createTeam(teamPostInput).expand(expand).execute();

Create team

Create a team. To learn more, read [Creating a team](https://docs.launchdarkly.com/home/teams/creating).  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;Create team\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.TeamsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String key = "key_example"; // The team key
    String name = "name_example"; // A human-friendly name for the team
    String description = "description_example"; // A description of the team
    List<String> customRoleKeys = Arrays.asList(); // List of custom role keys the team will access
    List<String> memberIDs = Arrays.asList(); // A list of member IDs who belong to the team
    List<PermissionGrantInput> permissionGrants = Arrays.asList(); // A list of permission grants. Permission grants allow access to a specific action, without having to create or update a custom role.
    String expand = "expand_example"; // A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above.
    try {
      Team result = client
              .teams
              .createTeam(key, name)
              .description(description)
              .customRoleKeys(customRoleKeys)
              .memberIDs(memberIDs)
              .permissionGrants(permissionGrants)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getAccess());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLinks());
      System.out.println(result.getLastModified());
      System.out.println(result.getVersion());
      System.out.println(result.getIdpSynced());
      System.out.println(result.getRoles());
      System.out.println(result.getMembers());
      System.out.println(result.getProjects());
      System.out.println(result.getMaintainers());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#createTeam");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Team> response = client
              .teams
              .createTeam(key, name)
              .description(description)
              .customRoleKeys(customRoleKeys)
              .memberIDs(memberIDs)
              .permissionGrants(permissionGrants)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#createTeam");
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
| **teamPostInput** | [**TeamPostInput**](TeamPostInput.md)|  | |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. | [optional] |

### Return type

[**Team**](Team.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Teams response |  -  |

<a name="getByTeamKey"></a>
# **getByTeamKey**
> Team getByTeamKey(teamKey).expand(expand).execute();

Get team

Fetch a team by key.  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;Get team\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.TeamsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String teamKey = "teamKey_example"; // The team key.
    String expand = "expand_example"; // A comma-separated list of properties that can reveal additional information in the response.
    try {
      Team result = client
              .teams
              .getByTeamKey(teamKey)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getAccess());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLinks());
      System.out.println(result.getLastModified());
      System.out.println(result.getVersion());
      System.out.println(result.getIdpSynced());
      System.out.println(result.getRoles());
      System.out.println(result.getMembers());
      System.out.println(result.getProjects());
      System.out.println(result.getMaintainers());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#getByTeamKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Team> response = client
              .teams
              .getByTeamKey(teamKey)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#getByTeamKey");
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
| **teamKey** | **String**| The team key. | |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. | [optional] |

### Return type

[**Team**](Team.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Teams response |  -  |

<a name="getCustomRoles"></a>
# **getCustomRoles**
> TeamCustomRoles getCustomRoles(teamKey).limit(limit).offset(offset).execute();

Get team custom roles

Fetch the custom roles that have been assigned to the team. To learn more, read [Managing team permissions](https://docs.launchdarkly.com/home/teams/managing#managing-team-permissions).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.TeamsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String teamKey = "teamKey_example"; // The team key
    Long limit = 56L; // The number of roles to return in the response. Defaults to 20.
    Long offset = 56L; // Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    try {
      TeamCustomRoles result = client
              .teams
              .getCustomRoles(teamKey)
              .limit(limit)
              .offset(offset)
              .execute();
      System.out.println(result);
      System.out.println(result.getTotalCount());
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#getCustomRoles");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<TeamCustomRoles> response = client
              .teams
              .getCustomRoles(teamKey)
              .limit(limit)
              .offset(offset)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#getCustomRoles");
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
| **teamKey** | **String**| The team key | |
| **limit** | **Long**| The number of roles to return in the response. Defaults to 20. | [optional] |
| **offset** | **Long**| Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |

### Return type

[**TeamCustomRoles**](TeamCustomRoles.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Team roles response |  -  |

<a name="getMaintainers"></a>
# **getMaintainers**
> TeamMaintainers getMaintainers(teamKey).limit(limit).offset(offset).execute();

Get team maintainers

Fetch the maintainers that have been assigned to the team. To learn more, read [Managing team maintainers](https://docs.launchdarkly.com/home/teams/managing#managing-team-maintainers).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.TeamsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String teamKey = "teamKey_example"; // The team key
    Long limit = 56L; // The number of maintainers to return in the response. Defaults to 20.
    Long offset = 56L; // Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query `limit`.
    try {
      TeamMaintainers result = client
              .teams
              .getMaintainers(teamKey)
              .limit(limit)
              .offset(offset)
              .execute();
      System.out.println(result);
      System.out.println(result.getTotalCount());
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#getMaintainers");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<TeamMaintainers> response = client
              .teams
              .getMaintainers(teamKey)
              .limit(limit)
              .offset(offset)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#getMaintainers");
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
| **teamKey** | **String**| The team key | |
| **limit** | **Long**| The number of maintainers to return in the response. Defaults to 20. | [optional] |
| **offset** | **Long**| Where to start in the list. This is for use with pagination. For example, an offset of 10 skips the first ten items and then returns the next items in the list, up to the query &#x60;limit&#x60;. | [optional] |

### Return type

[**TeamMaintainers**](TeamMaintainers.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Team maintainers response |  -  |

<a name="listTeams"></a>
# **listTeams**
> Teams listTeams().limit(limit).offset(offset).filter(filter).expand(expand).execute();

List teams

Return a list of teams.  By default, this returns the first 20 teams. Page through this list with the &#x60;limit&#x60; parameter and by following the &#x60;first&#x60;, &#x60;prev&#x60;, &#x60;next&#x60;, and &#x60;last&#x60; links in the &#x60;_links&#x60; field that returns. If those links do not appear, the pages they refer to don&#39;t exist. For example, the &#x60;first&#x60; and &#x60;prev&#x60; links will be missing from the response on the first page, because there is no previous page and you cannot return to the first page when you are already on the first page.  ### Filtering teams  LaunchDarkly supports the following fields for filters:  - &#x60;query&#x60; is a string that matches against the teams&#39; names and keys. It is not case-sensitive.   - A request with &#x60;query:abc&#x60; returns teams with the string &#x60;abc&#x60; in their name or key. - &#x60;nomembers&#x60; is a boolean that filters the list of teams who have 0 members   - A request with &#x60;nomembers:true&#x60; returns teams that have 0 members   - A request with &#x60;nomembers:false&#x60; returns teams that have 1 or more members  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;List teams\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.TeamsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    Long limit = 56L; // The number of teams to return in the response. Defaults to 20.
    Long offset = 56L; // Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next `limit` items.
    String filter = "filter_example"; // A comma-separated list of filters. Each filter is constructed as `field:value`.
    String expand = "expand_example"; // A comma-separated list of properties that can reveal additional information in the response.
    try {
      Teams result = client
              .teams
              .listTeams()
              .limit(limit)
              .offset(offset)
              .filter(filter)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getItems());
      System.out.println(result.getLinks());
      System.out.println(result.getTotalCount());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#listTeams");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Teams> response = client
              .teams
              .listTeams()
              .limit(limit)
              .offset(offset)
              .filter(filter)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#listTeams");
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
| **limit** | **Long**| The number of teams to return in the response. Defaults to 20. | [optional] |
| **offset** | **Long**| Where to start in the list. Use this with pagination. For example, an offset of 10 skips the first ten items and returns the next &#x60;limit&#x60; items. | [optional] |
| **filter** | **String**| A comma-separated list of filters. Each filter is constructed as &#x60;field:value&#x60;. | [optional] |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. | [optional] |

### Return type

[**Teams**](Teams.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Teams collection response |  -  |

<a name="removeByTeamKey"></a>
# **removeByTeamKey**
> removeByTeamKey(teamKey).execute();

Delete team

Delete a team by key. To learn more, read [Deleting a team](https://docs.launchdarkly.com/home/teams/managing#deleting-a-team).

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.TeamsApi;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Example {
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.host = "https://app.launchdarkly.com";
    
    configuration.apiKey  = "YOUR API KEY";
    LaunchDarkly client = new LaunchDarkly(configuration);
    String teamKey = "teamKey_example"; // The team key
    try {
      client
              .teams
              .removeByTeamKey(teamKey)
              .execute();
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#removeByTeamKey");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      client
              .teams
              .removeByTeamKey(teamKey)
              .executeWithHttpInfo();
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#removeByTeamKey");
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
| **teamKey** | **String**| The team key | |

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

<a name="updateSemanticPatch"></a>
# **updateSemanticPatch**
> Team updateSemanticPatch(teamKey, teamPatchInput).expand(expand).execute();

Update team

Perform a partial update to a team. Updating a team uses the semantic patch format.  To make a semantic patch request, you must append &#x60;domain-model&#x3D;launchdarkly.semanticpatch&#x60; to your &#x60;Content-Type&#x60; header. To learn more, read [Updates using semantic patch](https://apidocs.launchdarkly.com).  ### Instructions  Semantic patch requests support the following &#x60;kind&#x60; instructions for updating teams.  &lt;details&gt; &lt;summary&gt;Click to expand instructions for &lt;strong&gt;updating teams&lt;/strong&gt;&lt;/summary&gt;  #### addCustomRoles  Adds custom roles to the team. Team members will have these custom roles granted to them.  ##### Parameters  - &#x60;values&#x60;: List of custom role keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addCustomRoles\&quot;,     \&quot;values\&quot;: [ \&quot;example-custom-role\&quot; ]   }] } &#x60;&#x60;&#x60;  #### removeCustomRoles  Removes custom roles from the team. The app will no longer grant these custom roles to the team members.  ##### Parameters  - &#x60;values&#x60;: List of custom role keys.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeCustomRoles\&quot;,     \&quot;values\&quot;: [ \&quot;example-custom-role\&quot; ]   }] } &#x60;&#x60;&#x60;  #### addMembers  Adds members to the team.  ##### Parameters  - &#x60;values&#x60;: List of member IDs to add.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addMembers\&quot;,     \&quot;values\&quot;: [ \&quot;1234a56b7c89d012345e678f\&quot;, \&quot;507f1f77bcf86cd799439011\&quot; ]   }] } &#x60;&#x60;&#x60;  #### removeMembers  Removes members from the team.  ##### Parameters  - &#x60;values&#x60;: List of member IDs to remove.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removeMembers\&quot;,     \&quot;values\&quot;: [ \&quot;1234a56b7c89d012345e678f\&quot;, \&quot;507f1f77bcf86cd799439011\&quot; ]   }] } &#x60;&#x60;&#x60;  #### replaceMembers  Replaces the existing members of the team with the new members.  ##### Parameters  - &#x60;values&#x60;: List of member IDs of the new members.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;replaceMembers\&quot;,     \&quot;values\&quot;: [ \&quot;1234a56b7c89d012345e678f\&quot;, \&quot;507f1f77bcf86cd799439011\&quot; ]   }] } &#x60;&#x60;&#x60;  #### addPermissionGrants  Adds permission grants to members for the team. For example, a permission grant could allow a member to act as a team maintainer. A permission grant may have either an &#x60;actionSet&#x60; or a list of &#x60;actions&#x60; but not both at the same time. The members do not have to be team members to have a permission grant for the team.  ##### Parameters  - &#x60;actionSet&#x60;: Name of the action set. - &#x60;actions&#x60;: List of actions. - &#x60;memberIDs&#x60;: List of member IDs.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;addPermissionGrants\&quot;,     \&quot;actions\&quot;: [ \&quot;updateTeamName\&quot;, \&quot;updateTeamDescription\&quot; ],     \&quot;memberIDs\&quot;: [ \&quot;1234a56b7c89d012345e678f\&quot;, \&quot;507f1f77bcf86cd799439011\&quot; ]   }] } &#x60;&#x60;&#x60;  #### removePermissionGrants  Removes permission grants from members for the team. A permission grant may have either an &#x60;actionSet&#x60; or a list of &#x60;actions&#x60; but not both at the same time. The &#x60;actionSet&#x60; and &#x60;actions&#x60; must match an existing permission grant.  ##### Parameters  - &#x60;actionSet&#x60;: Name of the action set. - &#x60;actions&#x60;: List of actions. - &#x60;memberIDs&#x60;: List of member IDs.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;removePermissionGrants\&quot;,     \&quot;actions\&quot;: [ \&quot;updateTeamName\&quot;, \&quot;updateTeamDescription\&quot; ],     \&quot;memberIDs\&quot;: [ \&quot;1234a56b7c89d012345e678f\&quot;, \&quot;507f1f77bcf86cd799439011\&quot; ]   }] } &#x60;&#x60;&#x60;  #### updateDescription  Updates the description of the team.  ##### Parameters  - &#x60;value&#x60;: The new description.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateDescription\&quot;,     \&quot;value\&quot;: \&quot;Updated team description\&quot;   }] } &#x60;&#x60;&#x60;  #### updateName  Updates the name of the team.  ##### Parameters  - &#x60;value&#x60;: The new name.  Here&#39;s an example:  &#x60;&#x60;&#x60;json {   \&quot;instructions\&quot;: [{     \&quot;kind\&quot;: \&quot;updateName\&quot;,     \&quot;value\&quot;: \&quot;Updated team name\&quot;   }] } &#x60;&#x60;&#x60;  &lt;/details&gt;  ### Expanding the teams response LaunchDarkly supports four fields for expanding the \&quot;Update team\&quot; response. By default, these fields are **not** included in the response.  To expand the response, append the &#x60;expand&#x60; query parameter and add a comma-separated list with any of the following fields:  * &#x60;members&#x60; includes the total count of members that belong to the team. * &#x60;roles&#x60; includes a paginated list of the custom roles that you have assigned to the team. * &#x60;projects&#x60; includes a paginated list of the projects that the team has any write access to. * &#x60;maintainers&#x60; includes a paginated list of the maintainers that you have assigned to the team.  For example, &#x60;expand&#x3D;members,roles&#x60; includes the &#x60;members&#x60; and &#x60;roles&#x60; fields in the response. 

### Example
```java
import com.konfigthis.client.ApiClient;
import com.konfigthis.client.ApiException;
import com.konfigthis.client.ApiResponse;
import com.konfigthis.client.LaunchDarkly;
import com.konfigthis.client.Configuration;
import com.konfigthis.client.auth.*;
import com.konfigthis.client.model.*;
import com.konfigthis.client.api.TeamsApi;
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
    String teamKey = "teamKey_example"; // The team key
    String comment = "comment_example"; // Optional comment describing the update
    String expand = "expand_example"; // A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above.
    try {
      Team result = client
              .teams
              .updateSemanticPatch(instructions, teamKey)
              .comment(comment)
              .expand(expand)
              .execute();
      System.out.println(result);
      System.out.println(result.getDescription());
      System.out.println(result.getKey());
      System.out.println(result.getName());
      System.out.println(result.getAccess());
      System.out.println(result.getCreationDate());
      System.out.println(result.getLinks());
      System.out.println(result.getLastModified());
      System.out.println(result.getVersion());
      System.out.println(result.getIdpSynced());
      System.out.println(result.getRoles());
      System.out.println(result.getMembers());
      System.out.println(result.getProjects());
      System.out.println(result.getMaintainers());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#updateSemanticPatch");
      System.err.println("Status code: " + e.getStatusCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }

    // Use .executeWithHttpInfo() to retrieve HTTP Status Code, Headers and Request
    try {
      ApiResponse<Team> response = client
              .teams
              .updateSemanticPatch(instructions, teamKey)
              .comment(comment)
              .expand(expand)
              .executeWithHttpInfo();
      System.out.println(response.getResponseBody());
      System.out.println(response.getResponseHeaders());
      System.out.println(response.getStatusCode());
      System.out.println(response.getRoundTripTime());
      System.out.println(response.getRequest());
    } catch (ApiException e) {
      System.err.println("Exception when calling TeamsApi#updateSemanticPatch");
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
| **teamKey** | **String**| The team key | |
| **teamPatchInput** | [**TeamPatchInput**](TeamPatchInput.md)|  | |
| **expand** | **String**| A comma-separated list of properties that can reveal additional information in the response. Supported fields are explained above. | [optional] |

### Return type

[**Team**](Team.md)

### Authorization

[ApiKey](../README.md#ApiKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Teams response |  -  |

