package com.example.prm392.data.repository

import com.example.prm392.data.INotifyAPI
import com.example.prm392.data.dto.Notify.GetNotifyResponseModel
import com.example.prm392.domain.repository.INotifyRepository
import com.example.prm392.utils.HeaderProcessing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotifyRepository @Inject constructor(
    private val api: INotifyAPI,
    private val headerProcessing: HeaderProcessing
) : INotifyRepository {
    override suspend fun getNotifyById(id: Int): GetNotifyResponseModel {
        return withContext(Dispatchers.Default) {
            val header = headerProcessing.createDynamicHeaders(
                isTokenIncluded = true
            )
            api.getNotify(header, id)
        }
    }

    override suspend fun updateStatus(notificationId: Int) {
        return withContext(Dispatchers.Default) {
            val header = headerProcessing.createDynamicHeaders(
                isTokenIncluded = true
            )
            api.updateStatus(header, notificationId)
        }
    }
}