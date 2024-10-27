package com.example.prm392.domain.service

import com.example.prm392.data.dto.ProductDataDto
import com.example.prm392.domain.repository.IRemoteDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductDataService @Inject constructor(
    private val repository: IRemoteDataRepository
) {
    suspend operator fun invoke(skip : Int,limit : Int) : Flow<ProductDataDto> = flow {
        emit(repository.getProductData(skip,limit))
    }
}