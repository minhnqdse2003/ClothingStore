package com.example.prm392.domain.service.Cart

import com.example.prm392.data.dto.cart.AddCartResponseModel
import com.example.prm392.domain.model.Cart.request.CartItemRequestDto
import com.example.prm392.domain.repository.ICartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddUserCartService @Inject constructor(
    private val repository: ICartRepository
) {
    suspend operator fun invoke(requestModel: CartItemRequestDto): Flow<AddCartResponseModel> = flow {
        emit(repository.addUserCart(requestModel))
    }
}
