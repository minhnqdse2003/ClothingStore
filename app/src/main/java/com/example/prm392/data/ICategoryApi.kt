package com.example.prm392.data

import com.example.prm392.data.dto.category.get_all.GetAllCategoryResponseModel
import retrofit2.http.GET

interface ICategoryApi {
    @GET("api/v1/category")
    suspend fun getAllCategory(

    ) : GetAllCategoryResponseModel
}