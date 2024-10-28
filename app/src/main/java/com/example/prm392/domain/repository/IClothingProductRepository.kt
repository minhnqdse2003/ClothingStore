package com.example.prm392.domain.repository

import com.example.prm392.data.dto.products.get_all.GetAllProductResponseModel

interface IClothingProductRepository {
    suspend fun getAllClothing(
        page : Int,
        pageCount : Int,
    ) : GetAllProductResponseModel
}