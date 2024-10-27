package com.example.prm392.domain.model.User.Request

import com.squareup.moshi.Json

data class RegisterRequestModel (
    @Json(name = "username")
    val username:String,
    @Json(name = "passwordHash")
    val password:String,
    @Json(name = "email")
    val email:String,
    @Json(name = "phoneNumber")
    val phoneNumber:String,
    @Json(name = "address")
    val address:String
)