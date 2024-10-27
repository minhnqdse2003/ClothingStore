package com.example.prm392.data.dto


import com.example.prm392.domain.model.ProductSearchResultData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductSearchResultDto(
    @Json(name = "products")
    val products: List<ProductX>,
    @Json(name = "total")
    val total: Int,
    @Json(name = "skip")
    val skip: Int,
    @Json(name = "limit")
    val limit: Int
)

fun ProductSearchResultDto.toProductSearchResultData() : ProductSearchResultData {
    return ProductSearchResultData(
        products = products
    )
}