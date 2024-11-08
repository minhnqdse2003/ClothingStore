package com.example.prm392.domain.model.Order

import com.example.prm392.domain.model.Cart.request.CartItemRequestDto
import com.squareup.moshi.Json

data class OrderRequestDto (
    @Json(name = "cartItems")
    val cartItems : List<CartItemRequestDto>,
    @Json(name = "billingAddress")
    val billingAddress : String,
    @Json(name = "LocationId")
    val locationId : Int
)