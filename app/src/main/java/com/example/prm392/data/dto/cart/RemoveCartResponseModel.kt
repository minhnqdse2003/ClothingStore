package com.example.prm392.data.dto.cart

import com.example.prm392.domain.model.Cart.response.RemoveCartResponseDto
import com.squareup.moshi.Json

data class RemoveCartResponseModel(
    @Json(name = "data")
    val data : String,
    @Json(name = "status")
    val status: Int,
    @Json(name = "message")
    val message:String
)

fun RemoveCartResponseModel.toRemoveCartResponseDto () :RemoveCartResponseDto {
    return RemoveCartResponseDto(
        data = data,
        message = message,
        status = status
    )
}