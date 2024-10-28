package com.example.prm392.data.dto.products.get_all


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "categoryID")
    val categoryID: String,
    @Json(name = "categoryName")
    val categoryName: String
)