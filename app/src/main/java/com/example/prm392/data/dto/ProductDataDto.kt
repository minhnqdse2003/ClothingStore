package com.example.prm392.data.dto


import com.example.prm392.domain.model.ProductData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDataDto(
    @Json(name = "products")
    val products: List<Product>,
    @Json(name = "total")
    val total: Int,
    @Json(name = "skip")
    val skip: Int,
    @Json(name = "limit")
    val limit: Int
)

fun ProductDataDto.toProductData(): ProductData {
    return ProductData(
        products = products
    )
}