package com.example.prm392.data.repository

import com.example.prm392.data.IProductApi
import com.example.prm392.data.dto.ProductDataDto
import com.example.prm392.data.dto.ProductSearchResultDto
import com.example.prm392.domain.repository.IRemoteDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: IProductApi
) : IRemoteDataRepository {
    override suspend fun getProductData(skip: Int, limit: Int): ProductDataDto {
        return withContext(Dispatchers.Default) {
            api.getProductData(skip = skip, limit = limit)
        }
    }

    override suspend fun getSearchProductData(value: String, skip: Int, limit: Int): ProductSearchResultDto {
            return withContext(Dispatchers.Default) {
                api.getSearchProductData(value = value,skip = skip, limit = limit)
            }
    }
}