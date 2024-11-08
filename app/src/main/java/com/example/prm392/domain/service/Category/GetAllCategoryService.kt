package com.example.prm392.domain.service.Category

import com.example.prm392.data.dto.category.get_all.Category
import com.example.prm392.domain.repository.ICategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllCategoryService @Inject constructor(
    private val repository: ICategoryRepository
) {
    suspend operator fun invoke() : Flow<List<Category>> = flow {
        emit(repository.getAllCategory())
    }
}