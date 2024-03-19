

# BigSegmentStoreIntegration


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**tags** | **List&lt;String&gt;** | List of tags for this configuration |  |
|**version** | **Integer** | Version of the current configuration |  |
|**links** | [**BigSegmentStoreIntegrationLinks**](BigSegmentStoreIntegrationLinks.md) |  |  |
|**id** | **String** | The integration ID |  |
|**integrationKey** | [**IntegrationKeyEnum**](#IntegrationKeyEnum) | The integration key |  |
|**projectKey** | **String** | The project key |  |
|**environmentKey** | **String** | The environment key |  |
|**config** | **Map&lt;String, Object&gt;** |  |  |
|**true** | **Boolean** | Whether the configuration is turned on |  [optional] |
|**name** | **String** | Name of the configuration |  |
|**access** | [**Access**](Access.md) |  |  [optional] |
|**status** | [**BigSegmentStoreStatus**](BigSegmentStoreStatus.md) |  |  |



## Enum: IntegrationKeyEnum

| Name | Value |
|---- | -----|
| REDIS | &quot;redis&quot; |
| DYNAMODB | &quot;dynamodb&quot; |



