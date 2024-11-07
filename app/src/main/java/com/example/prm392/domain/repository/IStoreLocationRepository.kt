package com.example.prm392.domain.repository

import com.example.prm392.data.dto.store_location.get_all.StoreLocation

interface IStoreLocationRepository {
    suspend fun getAllStoreLocation(): List<StoreLocation>
}