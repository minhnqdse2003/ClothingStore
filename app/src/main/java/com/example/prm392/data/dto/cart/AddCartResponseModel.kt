package com.example.prm392.data.dto.cart

import com.example.prm392.domain.model.Cart.response.AddCartResponseDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddCartResponseModel (
    @Json(name = "data")
    val data : String,
    @Json(name = "status")
    val status: Int,
    @Json(name = "message")
    val message:String
)

fun AddCartResponseModel.toAddCartResponseDto() : AddCartResponseDto {
    return AddCartResponseDto(
        data = data,
        status = status,
        message = message
    )
}