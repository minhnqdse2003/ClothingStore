package com.example.prm392.data.repository

import android.util.Log
import com.example.prm392.data.IOrdersApi
import com.example.prm392.domain.model.Order.OrderRequestDto
import com.example.prm392.domain.repository.IOrdersRepository
import com.example.prm392.utils.HeaderProcessing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrdersRepository @Inject constructor(
    private val api: IOrdersApi,
    private val headerProcessing: HeaderProcessing
) : IOrdersRepository {
    override suspend fun placeOrder(request: OrderRequestDto): String {
        return withContext(Dispatchers.Default) {
            val header = headerProcessing.createDynamicHeaders(
                isTokenIncluded = true
            )
            Log.d("Token", header.toString())
            api.placeOrder(header, request)
        }
    }
}