package com.may.vknews.data.model

import com.google.gson.annotations.SerializedName

data class NewsFeedResponseDto(
    @SerializedName("response") val newsFeedContent: NewsFeedContentDto?,
    @SerializedName("error") val error: VkApiError?,
)
