package com.example.prm392.domain.model.Cart.response

import com.squareup.moshi.Json

data class AddCartResponseDto (
    val data : String,
    val status: Int,
    val message:String
)