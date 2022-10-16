package com.github.iipekolict.preview.dtos

import com.google.gson.annotations.SerializedName

data class ExceptionDto(
    @SerializedName("method") val method: String,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("handlerName") val handlerName: String
)