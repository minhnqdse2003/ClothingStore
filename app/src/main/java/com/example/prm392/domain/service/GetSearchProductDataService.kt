package com.example.prm392.domain.service

import com.example.prm392.data.dto.ProductSearchResultDto
import com.example.prm392.domain.repository.IRemoteDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchProductDataService @Inject constructor(
    private val repository: IRemoteDataRepository
) {
    suspend operator fun invoke(value: String, skip: Int,limit: Int): Flow<ProductSearchResultDto> = flow {
        emit(repository.getSearchProductData(value,skip,limit))
    }
}