package com.example.prm392.domain.repository

import com.example.prm392.domain.model.Order.OrderRequestDto

interface IOrdersRepository {
    suspend fun placeOrder(request: OrderRequestDto): String
}