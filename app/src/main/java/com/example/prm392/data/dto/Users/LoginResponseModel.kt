package com.example.prm392.data.dto.Users


import com.example.prm392.domain.model.User.Response.LoginResponseDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponseModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "username")
    val username: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "accessToken")
    val accessToken: String,
    @Json(name = "refreshToken")
    val refreshToken: String
)

fun LoginResponseModel.toLoginResponseDto() : LoginResponseDto {
    return LoginResponseDto(
        accessToken = accessToken,
        refreshToken = refreshToken,
        email = email
    )
}