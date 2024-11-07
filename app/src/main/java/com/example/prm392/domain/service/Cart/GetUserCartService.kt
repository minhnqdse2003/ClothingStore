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
        emit(repository.getUserCart())
    }
}
