package com.example.prm392.data.dto.Users


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponseModel(
    @Json(name = "token")
    val token: String,
    @Json(name = "refreshToken")
    val refreshToken: String
)