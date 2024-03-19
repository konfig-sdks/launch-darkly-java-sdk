

# MetricGroupPost


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**tags** | **List&lt;String&gt;** | Tags for the metric group |  |
|**description** | **String** | Description of the metric group |  [optional] |
|**key** | **String** | A unique key to reference the metric group |  |
|**name** | **String** | A human-friendly name for the metric group |  |
|**kind** | [**KindEnum**](#KindEnum) | The type of the metric group |  |
|**maintainerId** | **String** | The ID of the member who maintains this metric group |  |
|**metrics** | [**List&lt;MetricInMetricGroupInput&gt;**](MetricInMetricGroupInput.md) | An ordered list of the metrics in this metric group |  |



## Enum: KindEnum

| Name | Value |
|---- | -----|
| FUNNEL | &quot;funnel&quot; |



