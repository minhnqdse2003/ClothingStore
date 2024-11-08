package com.example.prm392.data

import com.example.prm392.domain.model.Order.OrderRequestDto
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface IOrdersApi {
    @POST("api/v1/orders")
    suspend fun placeOrder(
        @HeaderMap header : Map<String,String>,
        @Body request : OrderRequestDto
    ) : String
}