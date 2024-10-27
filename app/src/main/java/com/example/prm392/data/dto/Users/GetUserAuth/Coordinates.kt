package com.example.prm392.data.dto.Users.GetUserAuth


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coordinates(
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "lng")
    val lng: Double
)