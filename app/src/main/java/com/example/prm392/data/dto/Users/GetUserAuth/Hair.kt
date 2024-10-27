package com.example.prm392.data.dto.Users.GetUserAuth


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hair(
    @Json(name = "color")
    val color: String,
    @Json(name = "type")
    val type: String
)