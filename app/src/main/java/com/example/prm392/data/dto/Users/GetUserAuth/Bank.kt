package com.example.prm392.data.dto.Users.GetUserAuth


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Bank(
    @Json(name = "cardExpire")
    val cardExpire: String,
    @Json(name = "cardNumber")
    val cardNumber: String,
    @Json(name = "cardType")
    val cardType: String,
    @Json(name = "currency")
    val currency: String,
    @Json(name = "iban")
    val iban: String
)