package com.example.prm392.domain.repository

import com.example.prm392.data.dto.cart.AddCartResponseModel
import com.example.prm392.data.dto.cart.CartResponseModel
import com.example.prm392.data.dto.cart.RemoveCartResponseModel
import com.example.prm392.data.dto.cart.UpdateUserCartItemQuantityResponseModel
import com.example.prm392.domain.model.Cart.request.CartItemRequestDto

interface ICartRepository {

    suspend fun getUserCart(): CartResponseModel

    suspend fun addUserCart(
        requestModel: CartItemRequestDto
    ): AddCartResponseModel

    suspend fun removeUserCart(
        headers: Map<String, String>,
        cartItemId: Int
    ): RemoveCartResponseModel

    suspend fun updateUserCartItemQuantity(
        requestModel: CartItemRequestDto,
    ): UpdateUserCartItemQuantityResponseModel
}
