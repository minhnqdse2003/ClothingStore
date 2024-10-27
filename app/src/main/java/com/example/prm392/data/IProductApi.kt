package com.example.prm392.data

import com.example.prm392.data.dto.ProductDataDto
import com.example.prm392.data.dto.ProductSearchResultDto
import com.example.prm392.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface IProductApi {

    @GET("products")
    suspend fun getProductData(
        @Query("limit") limit : Int = Constants.ITEM_LIMIT,
        @Query("skip") skip:Int = 0
    ) : ProductDataDto

    @GET("products/search")
    suspend fun getSearchProductData(
        @Query("q") value : String,
        @Query("limit") limit : Int = Constants.ITEM_LIMIT,
        @Query("skip") skip : Int
    ):ProductSearchResultDto
}