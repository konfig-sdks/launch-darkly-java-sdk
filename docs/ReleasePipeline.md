

# ReleasePipeline


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**tags** | **List&lt;String&gt;** | A list of the release pipeline&#39;s tags |  [optional] |
|**description** | **String** | The release pipeline description |  [optional] |
|**createdAt** | **OffsetDateTime** | Timestamp of when the release pipeline was created |  |
|**key** | **String** | The release pipeline key |  |
|**name** | **String** | The release pipeline name |  |
|**phases** | [**List&lt;Phase&gt;**](Phase.md) | An ordered list of the release pipeline phases. Each phase is a logical grouping of one or more environments that share attributes for rolling out changes. |  |
|**version** | **Integer** | The release pipeline version |  [optional] |
|**access** | [**Access**](Access.md) |  |  [optional] |



