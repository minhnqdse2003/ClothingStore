package com.example.prm392.data

import com.example.prm392.data.dto.store_location.get_all.StoreLocation
import retrofit2.http.GET

interface IStoreLocationApi {
    @GET("api/StoreLocation/store-locations")
    suspend fun getAllStoreLocation() : List<StoreLocation>
}