package com.example.prm392.domain.service.ClothingProduct

import com.example.prm392.data.dto.products.get_all.GetAllProductResponseModel
import com.example.prm392.domain.repository.IClothingProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllClothing @Inject constructor(
    private val repository: IClothingProductRepository
){
    suspend operator fun invoke (
        page : Int,
        pageCount: Int
    ) : Flow<GetAllProductResponseModel> = flow {
        emit(repository.getAllClothing(page,pageCount))
    }
}