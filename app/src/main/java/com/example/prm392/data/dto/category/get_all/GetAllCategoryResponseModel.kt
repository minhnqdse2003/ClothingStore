package com.example.prm392.data.dto.category.get_all


import com.example.prm392.domain.model.Category.GetAllCategoryDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetAllCategoryResponseModel(
    @Json(name = "status")
    val status: Int,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val data: List<Category>
)

fun GetAllCategoryResponseModel.toGetAllCategoryDto() : GetAllCategoryDto {
    return GetAllCategoryDto(
        data = data
    )
}