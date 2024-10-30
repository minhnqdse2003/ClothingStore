package com.example.prm392.data.dto.products.get_all


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "productID")
    val productID: Int,
    @Json(name = "productName")
    val productName: String,
    @Json(name = "briefDescription")
    val briefDescription: String,
    @Json(name = "fullDescription")
    val fullDescription: String,
    @Json(name = "technicalSpecifications")
    val technicalSpecifications: String,
    @Json(name = "price")
    val price: Double,
    @Json(name = "imageURL")
    val imageURL: String,
    @Json(name = "category")
    val category: Category
)