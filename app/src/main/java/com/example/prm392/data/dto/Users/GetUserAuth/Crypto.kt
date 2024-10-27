package com.example.prm392.data.dto.Users.GetUserAuth


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Crypto(
    @Json(name = "coin")
    val coin: String,
    @Json(name = "wallet")
    val wallet: String,
    @Json(name = "network")
    val network: String
)