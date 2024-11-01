package com.example.prm392.data

import com.example.prm392.data.dto.store_location.get_all.GetAllStoreLocationResponseModel
import retrofit2.http.GET

interface IStoreLocationApi {
    @GET("api/v1/store-location")
    suspend fun getAllStoreLocation() : GetAllStoreLocationResponseModel
}