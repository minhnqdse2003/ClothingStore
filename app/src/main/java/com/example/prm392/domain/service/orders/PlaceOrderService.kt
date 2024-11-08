package com.example.prm392.domain.service.orders

import com.example.prm392.domain.model.Order.OrderRequestDto
import com.example.prm392.domain.repository.IOrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaceOrderService @Inject constructor(
    private val repository: IOrdersRepository
) {
    suspend operator fun invoke(
        requestDto: OrderRequestDto
    ): Flow<String> = flow {
        emit(repository.placeOrder(requestDto))
    }
}