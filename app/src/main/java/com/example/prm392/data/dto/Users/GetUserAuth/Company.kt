package com.example.prm392.data.dto.Users.GetUserAuth


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Company(
    @Json(name = "department")
    val department: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "address")
    val address: Address
)