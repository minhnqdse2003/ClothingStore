package com.example.prm392.domain.service.ClothingProduct

import com.example.prm392.data.dto.products.get_all.Category
import com.example.prm392.data.dto.products.get_all.GetAllProductResponseModel
import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.data.repository.ClothingProductRepository
import com.example.prm392.domain.model.ClothingProduct.request.ClothingProductFilterParam
import com.example.prm392.domain.model.ClothingProduct.request.buildFilterMap
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.ceil

class GetAllClothing @Inject constructor(
    private val repository: ClothingProductRepository
) {
    suspend operator fun invoke(
        page: Int,
        pageCount: Int,
        clothingProductFilterParam : ClothingProductFilterParam
    ): Flow<GetAllProductResponseModel> = flow {
        emit(repository.getAllClothing(
            page,
            pageCount,
            clothingProductFilterParam
        ))
    }
}
