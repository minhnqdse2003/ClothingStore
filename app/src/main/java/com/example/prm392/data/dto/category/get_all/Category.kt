package com.example.prm392.data.dto.category.get_all


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "categoryID")
    val categoryID: Int,
    @Json(name = "categoryName")
    val categoryName: String
)