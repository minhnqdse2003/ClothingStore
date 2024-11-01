package com.example.prm392.data.dto.store_location.get_all

import com.example.prm392.data.dto.category.get_all.Category
import com.example.prm392.domain.model.store_location.GetAllStoreLocationResponseDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetAllStoreLocationResponseModel(
    @Json(name = "status")
    val status: Int,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val data: List<StoreLocation>
)

data class StoreLocation (
    @Json(name = "locationID") val locationID: Int,
    @Json(name = "address") val address: String,
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
)

fun GetAllStoreLocationResponseModel.toGetAllStoreLocationResponseDto() : GetAllStoreLocationResponseDto{
    return GetAllStoreLocationResponseDto(
        data = data
    )
}
