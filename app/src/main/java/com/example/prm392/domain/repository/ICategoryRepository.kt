package com.example.prm392.domain.repository

import com.example.prm392.data.dto.category.get_all.GetAllCategoryResponseModel

interface ICategoryRepository {
    suspend fun getAllCategory(

    ) : GetAllCategoryResponseModel
}