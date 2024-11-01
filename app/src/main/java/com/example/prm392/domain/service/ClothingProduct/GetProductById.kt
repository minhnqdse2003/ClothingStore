package com.example.prm392.domain.service.ClothingProduct

import com.example.prm392.data.dto.products.get_all.Category
import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.data.dto.products.get_by_id.GetProductByIdResponseModel
import com.example.prm392.domain.repository.IClothingProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductById @Inject constructor(
    private val repository: IClothingProductRepository
) {
    suspend operator fun invoke(
        productId: Int
    ) : Flow<GetProductByIdResponseModel> = flow {
//        emit(repository.getClothingById(productId))

        val mockProducts = List(200) { index ->
            Product(
                productID = index,
                productName = "Product ${index.toString()}",
                briefDescription = "Brief description for Product ${index.toString()}",
                fullDescription = "Full description for Product ${index.toString()}, covering all product details.",
                technicalSpecifications = "Technical specifications for Product ${index.toString()}",
                price = 20.0 + index,  // Incremental price for variety
                imageURL = "https://firebasestorage.googleapis.com/v0/b/exe201-8a7e7.appspot.com/o/still-life-say-no-fast-fashion.jpg?alt=media&token=45e97687-d77f-45ac-a5be-8a99b8a47637",  // Mock image URL
                category = Category(
                    categoryID = index,
                    categoryName = "Category ${index.toString()}"  // Mock category
                )
            )
        }

        emit(
            GetProductByIdResponseModel(
                data = mockProducts.find { it -> it.productID == productId } ?: mockProducts.first(),
                status = 200,
                message = "Success"
            )
        )
    }
}