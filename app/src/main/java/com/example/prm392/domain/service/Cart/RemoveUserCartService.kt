package com.example.prm392.domain.service.Cart

import com.example.prm392.data.dto.cart.RemoveCartResponseModel
import com.example.prm392.domain.repository.ICartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveUserCartService @Inject constructor(
    private val repository: ICartRepository
) {
    suspend operator fun invoke(cartItemId: Int): Flow<RemoveCartResponseModel> = flow {
        emit(repository.removeUserCart(emptyMap(), cartItemId))
    }
}
