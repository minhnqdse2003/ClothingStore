package com.example.prm392.data.repository

import com.example.prm392.data.IStoreLocationApi
import com.example.prm392.data.dto.store_location.get_all.StoreLocation
import com.example.prm392.domain.repository.IStoreLocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StoreLocationRepository @Inject constructor(
    private val api: IStoreLocationApi
) : IStoreLocationRepository {
    override suspend fun getAllStoreLocation(): List<StoreLocation> {
        return withContext(Dispatchers.Default) {
            api.getAllStoreLocation()
        }
    }
}