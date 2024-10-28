package com.example.prm392.data.repository

import com.example.prm392.data.IClothingProductApi
import com.example.prm392.data.dto.products.get_all.GetAllProductResponseModel
import com.example.prm392.domain.repository.IClothingProductRepository
import com.example.prm392.utils.HeaderProcessing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClothingProductRepository @Inject constructor(
    private val api: IClothingProductApi,
    private val headerProcessing: HeaderProcessing
) : IClothingProductRepository{

    override suspend fun getAllClothing(page: Int, pageCount: Int): GetAllProductResponseModel {
        return withContext(Dispatchers.Default){
            api.getAllClothingProduct(page,pageCount)
        }
    }
}