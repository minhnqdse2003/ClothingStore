package com.example.prm392.data

import com.example.prm392.data.dto.category.get_all.Category
import retrofit2.http.GET

interface ICategoryApi {
    @GET("api/v1/categories")
    suspend fun getAllCategory(
    ) : List<Category>
}