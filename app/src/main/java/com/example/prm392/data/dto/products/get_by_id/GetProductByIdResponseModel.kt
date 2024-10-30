package com.example.prm392.data.dto.products.get_by_id

import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.domain.model.ClothingProduct.response.GetProductByIdResponseDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetProductByIdResponseModel(
    @Json(name = "data")
    val data: Product,
    @Json(name = "status")
    val status: Int,
    @Json(name = "message")
    val message: String,
)

fun GetProductByIdResponseModel.toGetProductByIdResponseDto() : GetProductByIdResponseDto {
    return GetProductByIdResponseDto(
        data = data
    )
}