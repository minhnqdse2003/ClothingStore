package com.example.prm392.data.dto.store_location.get_all

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StoreLocation (
    @Json(name = "locationId") val locationID: Int,
    @Json(name = "address") val address: String,
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
)
