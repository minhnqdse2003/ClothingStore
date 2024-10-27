package com.example.prm392.domain.model.User.Request

import com.squareup.moshi.Json

data class LoginRequestModel (
    @Json(name = "username")
    val username : String,
    @Json(name = "password")
    val password: String,
    @Json(name = "expiresInMins")
    val expiresInMins : Int?
)