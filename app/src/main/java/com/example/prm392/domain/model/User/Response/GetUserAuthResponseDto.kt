package com.example.prm392.domain.model.User.Response

data class GetUserAuthResponseDto (
    val firstName: String,
    val lastName: String,
    val maidenName: String,
    val age: Int,
)