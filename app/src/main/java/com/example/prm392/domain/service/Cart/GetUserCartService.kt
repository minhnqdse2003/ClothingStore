package com.example.prm392.domain.service.Cart

import com.example.prm392.data.dto.cart.CartResponseModel
import com.example.prm392.data.dto.cart.CartResponseModelData
import com.example.prm392.data.dto.cart.CartProductsResponseModelData
import com.example.prm392.data.dto.products.get_all.Category
import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.domain.repository.ICartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserCartService @Inject constructor(
    private val repository: ICartRepository
) {
    suspend operator fun invoke(): Flow<CartResponseModel> = flow {
        // Mock data for CartResponseModel with 4 items, using the specified image URL
        val mockImageUrl = "https://firebasestorage.googleapis.com/v0/b/exe201-8a7e7.appspot.com/o/still-life-say-no-fast-fashion.jpg?alt=media&token=45e97687-d77f-45ac-a5be-8a99b8a47637"

        val mockCartResponse = CartResponseModel(
            status = 200,
            message = "Cart fetched successfully",
            data = CartResponseModelData(
                product = listOf(
                    CartProductsResponseModelData(
                        product = Product(
                            productID = 1,
                            productName = "Smartphone",
                            briefDescription = "Latest 5G smartphone",
                            fullDescription = "The latest model with a powerful processor and advanced camera",
                            technicalSpecifications = "6.5-inch display, 128GB storage, 5G connectivity",
                            price = 16990000.0, // VNĐ
                            imageURL = mockImageUrl,
                            category = Category(
                                categoryID = "1",
                                categoryName = "Electronics"
                            )
                        ),
                        quantity = 1,
                        totalPrice = 16990000 // VNĐ
                    ),
                    CartProductsResponseModelData(
                        product = Product(
                            productID = 2,
                            productName = "Laptop",
                            briefDescription = "High-performance laptop",
                            fullDescription = "A laptop with a sleek design, perfect for work and entertainment",
                            technicalSpecifications = "15-inch screen, 16GB RAM, 512GB SSD",
                            price = 27990000.0, // VNĐ
                            imageURL = mockImageUrl,
                            category = Category(
                                categoryID = "2",
                                categoryName = "Computers"
                            )
                        ),
                        quantity = 1,
                        totalPrice = 27990000 // VNĐ
                    ),
                    CartProductsResponseModelData(
                        product = Product(
                            productID = 3,
                            productName = "Wireless Earbuds",
                            briefDescription = "Noise-canceling wireless earbuds",
                            fullDescription = "Compact and lightweight earbuds with excellent sound quality",
                            technicalSpecifications = "Bluetooth 5.0, 20-hour battery life",
                            price = 1999000.0, // VNĐ
                            imageURL = mockImageUrl,
                            category = Category(
                                categoryID = "3",
                                categoryName = "Accessories"
                            )
                        ),
                        quantity = 2,
                        totalPrice = 3998000 // VNĐ
                    ),
                    CartProductsResponseModelData(
                        product = Product(
                            productID = 4,
                            productName = "Smart Watch",
                            briefDescription = "Water-resistant smart watch",
                            fullDescription = "Tracks your fitness activities, monitors your heart rate",
                            technicalSpecifications = "1.5-inch display, GPS, water-resistant up to 50m",
                            price = 4990000.0, // VNĐ
                            imageURL = mockImageUrl,
                            category = Category(
                                categoryID = "4",
                                categoryName = "Wearables"
                            )
                        ),
                        quantity = 1,
                        totalPrice = 4990000 // VNĐ
                    )
                ),
                totalPrice = 53968000 // VNĐ
            )
        )

        emit(mockCartResponse)
    }
}
