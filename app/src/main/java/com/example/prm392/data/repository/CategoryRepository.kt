package com.example.prm392.data.repository

import com.example.prm392.data.ICategoryApi
import com.example.prm392.data.dto.category.get_all.Category
import com.example.prm392.domain.repository.ICategoryRepository
import com.example.prm392.utils.HeaderProcessing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val api: ICategoryApi,
    private val headerProcessing: HeaderProcessing
) : ICategoryRepository {

    override suspend fun getAllCategory(): List<Category> {
        return withContext(Dispatchers.Default) {
            api.getAllCategory()
        }
    }
}