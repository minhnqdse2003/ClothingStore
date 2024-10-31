package com.example.prm392.data.dto.cart

import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.domain.model.Cart.response.CartResponseDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CartResponseModel (
    @Json(name = "status")
    val status: Int,
    @Json(name = "message")
    val message:String,
    @Json(name = "data")
    val data : CartResponseModelData
)

fun CartResponseModel.toCartResponseDto():CartResponseDto {
    return CartResponseDto(
        status = status,
        message = message,
        data = data
    )
}

@JsonClass(generateAdapter = true)
data class CartResponseModelData (
    @Json(name = "products")
    val product: List<CartProductsResponseModelData>,
    @Json(name = "totalPrice")
    val totalPrice: Int,
)

@JsonClass(generateAdapter = true)
data class CartProductsResponseModelData(
    @Json(name = "product")
    val product: Product,
    @Json(name = "quantity")
    val quantity: Int,
    @Json(name = "price")
    val totalPrice: Int
)