package com.example.prm392.domain.repository

import com.example.prm392.data.dto.products.get_all.GetAllProductResponseModel
import com.example.prm392.data.dto.products.get_by_id.GetProductByIdResponseModel

interface IClothingProductRepository {
    suspend fun getAllClothing(
        page : Int,
        pageCount : Int,
    ) : GetAllProductResponseModel

    suspend fun getClothingById(
        id : Int
    ) : GetProductByIdResponseModel
}