package com.example.prm392.data

import com.example.prm392.data.dto.products.get_all.GetAllProductResponseModel
import com.example.prm392.data.dto.products.get_by_id.GetProductByIdResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface IClothingProductApi {

    @GET("api/v1/products")
    suspend fun getAllClothingProduct(
        @Query("PageNumber") page: Int,
        @Query("PageSize") pageCount: Int,
        @QueryMap filters: Map<String, Any?>
    ) : GetAllProductResponseModel

    @GET("api/v1/products/{Id}")
    suspend fun getProductById(
        @Path("Id") productId : Int
    ) : GetProductByIdResponseModel
}