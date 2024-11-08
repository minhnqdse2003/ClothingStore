package com.example.prm392.data.dto.Users.GetUserProfile


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Order(
    @Json(name = "orderId")
    val orderId: Int,
    @Json(name = "userName")
    val userName: String,
    @Json(name = "locationAddress")
    val locationAddress: String,
    @Json(name = "totalPrice")
    val totalPrice: Int,
    @Json(name = "paymentMethod")
    val paymentMethod: String,
    @Json(name = "billingAddress")
    val billingAddress: String,
    @Json(name = "orderStatus")
    val orderStatus: String,
    @Json(name = "orderDate")
    val orderDate: String
)