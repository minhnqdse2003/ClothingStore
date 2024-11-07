package com.example.prm392.domain.model.Cart.response

import com.example.prm392.data.dto.cart.CartResponseModelData
import com.squareup.moshi.Json

data class CartResponseDto (
    val status: Int?,
    val message:String?,
    val data : CartResponseModelData
)