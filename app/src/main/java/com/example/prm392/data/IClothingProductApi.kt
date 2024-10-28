package com.example.prm392.data

import com.example.prm392.data.dto.products.get_all.GetAllProductResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface IClothingProductApi {

    @GET("products")
    suspend fun getAllClothingProduct(
        @Query("page") page: Int,
        @Query("limit") pageCount: Int
    ) : GetAllProductResponseModel
}