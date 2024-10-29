package com.example.prm392.data.dto.products.get_all


import com.example.prm392.domain.model.ClothingProduct.response.GetAllProductResponseDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetAllProductResponseModel(
    @Json(name = "data")
    val products: List<Product>,
    @Json(name = "totalCount")
    val totalCount: Int,
    @Json(name = "pageNumber")
    val currentPage: Int,
    @Json(name = "totalPages")
    val totalPages: Int
)

fun GetAllProductResponseModel.toGetAllProductResponseDto() : GetAllProductResponseDto{
    return  GetAllProductResponseDto(
        products = products
    )
}