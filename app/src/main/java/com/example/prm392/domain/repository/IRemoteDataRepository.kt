package com.example.prm392.domain.repository

import com.example.prm392.data.dto.ProductDataDto
import com.example.prm392.data.dto.ProductSearchResultDto

interface IRemoteDataRepository {
    suspend fun getProductData(skip: Int, limit: Int) : ProductDataDto

    suspend fun getSearchProductData(value: String, skip: Int, limit: Int) : ProductSearchResultDto
}