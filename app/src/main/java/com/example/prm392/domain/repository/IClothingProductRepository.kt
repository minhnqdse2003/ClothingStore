package com.example.prm392.domain.repository

import com.example.prm392.data.dto.products.get_all.GetAllProductResponseModel
import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.domain.model.ClothingProduct.request.ClothingProductFilterParam

interface IClothingProductRepository {
    suspend fun getAllClothing(
        page : Int,
        pageCount : Int,
        clothingProductFilterParam : ClothingProductFilterParam
    ) : GetAllProductResponseModel

    suspend fun getClothingById(
        id : Int
    ) : Product
}