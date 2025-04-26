package com.may.vknews.data.model

import com.google.gson.annotations.SerializedName

data class VkApiError(
    @SerializedName("error_code") val errorCode: Int,
    @SerializedName("error_msg") val errorMessage: String
)
