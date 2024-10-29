package com.example.prm392.domain.service.ClothingProduct

import com.example.prm392.data.dto.products.get_all.Category
import com.example.prm392.data.dto.products.get_all.GetAllProductResponseModel
import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.data.repository.ClothingProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.ceil

class GetAllClothing @Inject constructor(
    private val repository: ClothingProductRepository
) {
    suspend operator fun invoke(
        page: Int,
        pageCount: Int
    ): Flow<GetAllProductResponseModel> = flow {
        val totalCount = 200

        val mockProducts = List(totalCount) { index ->
            Product(
                productID = "ID$index",
                productName = "Product $index",
                briefDescription = "Brief description for Product $index",
                fullDescription = "Full description for Product $index, covering all product details.",
                technicalSpecifications = "Technical specifications for Product $index",
                price = 20.0 + index,  // Incremental price for variety
                imageURL = "https://firebasestorage.googleapis.com/v0/b/exe201-8a7e7.appspot.com/o/still-life-say-no-fast-fashion.jpg?alt=media&token=45e97687-d77f-45ac-a5be-8a99b8a47637",  // Mock image URL
                category = Category(
                    categoryID = "CAT$index",
                    categoryName = "Category $index"  // Mock category
                )
            )
        }

        // Calculate pagination parameters
        val totalPages = ceil(totalCount / pageCount.toDouble()).toInt()
        val startIndex = (page - 1) * pageCount
        val endIndex = (startIndex + pageCount).coerceAtMost(totalCount)

        // Slice the mock products list to simulate paginated data
        val paginatedProducts = mockProducts.subList(startIndex, endIndex)

        // Emit the mock paginated response

        delay(2000L)

        emit(
            GetAllProductResponseModel(
                products = paginatedProducts,
                totalCount = totalCount,
                currentPage = page,
                totalPages = totalPages
            )
        )
    }
}
