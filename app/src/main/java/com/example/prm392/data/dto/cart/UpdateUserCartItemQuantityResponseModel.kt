package com.example.prm392.data.dto.cart

import com.example.prm392.domain.model.Cart.response.UpdateUserCartItemQuantityResponseDto
import com.squareup.moshi.Json

data class UpdateUserCartItemQuantityResponseModel(
    @Json(name = "data")
    val data : String,
    @Json(name = "status")
    val status: Int,
    @Json(name = "message")
    val message:String
)

fun UpdateUserCartItemQuantityResponseModel.toUpdateUserCartItemQuantityResponseDto():UpdateUserCartItemQuantityResponseDto {
    return UpdateUserCartItemQuantityResponseDto(
        data = data,
        status = status,
        message = message
    )
}