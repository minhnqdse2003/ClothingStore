package com.example.prm392.data.dto.Users.GetUserAuth


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Address(
    @Json(name = "address")
    val address: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "state")
    val state: String,
    @Json(name = "stateCode")
    val stateCode: String,
    @Json(name = "postalCode")
    val postalCode: String,
    @Json(name = "coordinates")
    val coordinates: Coordinates,
    @Json(name = "country")
    val country: String
)