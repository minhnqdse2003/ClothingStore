package com.example.prm392.data.dto.Message

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)

data class SendMessageResponseModel (
    @Json(name = "success")
    val success: Boolean,
    @Json(name = "message")
    val message: String,
)