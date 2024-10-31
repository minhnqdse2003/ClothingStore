package com.example.prm392.data

import com.example.prm392.data.dto.cart.AddCartResponseModel
import com.example.prm392.data.dto.cart.CartResponseModel
import com.example.prm392.data.dto.cart.RemoveCartResponseModel
import com.example.prm392.data.dto.cart.UpdateUserCartItemQuantityResponseModel
import com.example.prm392.domain.model.Cart.request.CartItemRequestDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ICartApi {
    @GET("api/v1/cart")
    suspend fun getUserCart(
        @HeaderMap headers: Map<String, String>,
    ): CartResponseModel

    @POST("api/v1/cart")
    suspend fun addUserCart(
        @HeaderMap headers: Map<String, String>,
        @Body requestModel:CartItemRequestDto
    ) : AddCartResponseModel

    @DELETE("api/v1/cart/{Id}")
    suspend fun removeUserCart(
        @HeaderMap headers: Map<String, String>,
        @Path(value = "Id") cartItemId : Int
    ) : RemoveCartResponseModel

    @PUT("api/v1/cart")
    suspend fun updateUserCartItemQuantity(
        @Body requestModel:CartItemRequestDto,
        @HeaderMap headers: Map<String, String>
    ) : UpdateUserCartItemQuantityResponseModel
}