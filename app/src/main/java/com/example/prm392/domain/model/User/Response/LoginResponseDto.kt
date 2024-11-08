package com.example.prm392.domain.model.User.Response

data class LoginResponseDto (
    val accessToken: String,
    val refreshToken: String,
)