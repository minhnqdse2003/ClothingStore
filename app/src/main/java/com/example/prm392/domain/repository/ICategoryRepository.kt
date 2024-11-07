package com.example.prm392.domain.repository

import com.example.prm392.data.dto.category.get_all.Category

interface ICategoryRepository {
    suspend fun getAllCategory(
    ) : List<Category>
}