package com.example.prm392.domain.service.store_location

import com.example.prm392.data.dto.store_location.get_all.StoreLocation
import com.example.prm392.domain.repository.IStoreLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllStoreLocationService @Inject constructor(
    private val repository: IStoreLocationRepository
) {
    suspend operator fun invoke(): Flow<List<StoreLocation>> = flow {
         emit(repository.getAllStoreLocation())
    }
}
