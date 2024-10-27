package com.example.prm392.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewX(
    @Json(name = "rating")
    val rating: Int,
    @Json(name = "comment")
    val comment: String,
    @Json(name = "date")
    val date: String,
    @Json(name = "reviewerName")
    val reviewerName: String,
    @Json(name = "reviewerEmail")
    val reviewerEmail: String
)