package com.example.prm392.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Dimensions(
    @Json(name = "width")
    val width: Double,
    @Json(name = "height")
    val height: Double,
    @Json(name = "depth")
    val depth: Double
)