package com.example.prm392.data.dto.Users.GetUserProfile


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetUserProfileResponseModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "username")
    val username: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "role")
    val role: String,
    @Json(name = "phoneNumber")
    val phoneNumber: String?,
    @Json(name = "address")
    val address: String?,
    @Json(name = "orders")
    val orders: List<Order>
)