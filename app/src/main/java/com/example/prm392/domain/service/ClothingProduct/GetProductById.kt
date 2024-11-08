package com.example.prm392.domain.service.ClothingProduct

import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.domain.repository.IClothingProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductById @Inject constructor(
    private val repository: IClothingProductRepository
) {
    suspend operator fun invoke(
        productId: Int
    ) : Flow<Product> = flow {
        emit(repository.getClothingById(productId))
    }
}