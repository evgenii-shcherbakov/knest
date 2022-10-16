package com.github.iipekolict.preview.dtos

import com.google.gson.annotations.SerializedName

data class TestBodyDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)