package com.example.prm392.data.repository

import com.example.prm392.data.ICartApi
import com.example.prm392.data.dto.cart.AddCartResponseModel
import com.example.prm392.data.dto.cart.CartResponseModel
import com.example.prm392.data.dto.cart.RemoveCartResponseModel
import com.example.prm392.data.dto.cart.UpdateUserCartItemQuantityResponseModel
import com.example.prm392.domain.model.Cart.request.CartItemRequestDto
import com.example.prm392.domain.repository.ICartRepository
import com.example.prm392.utils.HeaderProcessing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val api: ICartApi,
    private val headerProcessing: HeaderProcessing
) : ICartRepository {

    override suspend fun getUserCart(): CartResponseModel {
        return withContext(Dispatchers.Default) {
            val header = headerProcessing.createDynamicHeaders(
                isTokenIncluded = true
            )
            api.getUserCart(header)
        }
    }

    override suspend fun addUserCart(
        requestModel: CartItemRequestDto
    ): AddCartResponseModel {
        return withContext(Dispatchers.Default) {
            val header = headerProcessing.createDynamicHeaders(
                isTokenIncluded = true
            )
            api.addUserCart(header, requestModel)
        }
    }

    override suspend fun removeUserCart(
        cartItemId: Int
    ): RemoveCartResponseModel {
        return withContext(Dispatchers.Default) {
            val header = headerProcessing.createDynamicHeaders(
                isTokenIncluded = true
            )
            api.removeUserCart(header, cartItemId)
        }
    }

    override suspend fun updateUserCartItemQuantity(
        requestModel: CartItemRequestDto,
    ): UpdateUserCartItemQuantityResponseModel {
        return withContext(Dispatchers.Default) {
            val header = headerProcessing.createDynamicHeaders(
                isTokenIncluded = true
            )
            api.updateUserCartItemQuantity(requestModel, header)
        }
    }
}