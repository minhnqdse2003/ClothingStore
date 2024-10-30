package com.example.prm392.domain.service.Category

import com.example.prm392.data.dto.category.get_all.Category
import com.example.prm392.data.dto.category.get_all.GetAllCategoryResponseModel
import com.example.prm392.domain.repository.ICategoryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllCategoryService @Inject constructor(
    private val repository: ICategoryRepository
) {
    suspend operator fun invoke() : Flow<GetAllCategoryResponseModel> = flow {
//        emit(repository.getAllCategory())

        val categories = listOf(
            Category(categoryID = 1, categoryName = "Technology"),
            Category(categoryID = 2, categoryName = "Science"),
            Category(categoryID = 3, categoryName = "Health"),
            Category(categoryID = 4, categoryName = "Education"),
            Category(categoryID = 5, categoryName = "Arts")
        )

        delay(4000L)

        // Returning a mock response
        emit(
            GetAllCategoryResponseModel(
                status = 200,
                message = "Success",
                data = categories
            )
        )
    }
}