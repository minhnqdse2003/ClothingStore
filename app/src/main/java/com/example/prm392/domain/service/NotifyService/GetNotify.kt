package com.example.prm392.domain.service.NotifyService

import android.util.Log
import com.example.prm392.data.dto.Message.GetMessagesResponseModel
import com.example.prm392.data.dto.Notify.GetNotifyResponseModel
import com.example.prm392.domain.repository.IMessageRepository
import com.example.prm392.domain.repository.INotifyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNotify @Inject constructor(
    private val repository: INotifyRepository
) {
    suspend operator fun invoke(
        userId : Int
    ): Flow<List<GetNotifyResponseModel>> = flow {
        try {
            val response = repository.getNotifyById(userId)
            Log.d("Notify", "Mapped Notify: $response")
            emit(response)
        } catch (e: Exception) {
            throw e
        }
    }
}