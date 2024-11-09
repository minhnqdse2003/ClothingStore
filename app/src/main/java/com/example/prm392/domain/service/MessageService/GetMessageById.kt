package com.example.prm392.domain.service.MessageService

import android.util.Log
import com.example.prm392.data.dto.Message.Message
import com.example.prm392.domain.repository.IMessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMessageById @Inject constructor(
    private val repository: IMessageRepository
) {
    suspend operator fun invoke(
        userId: Int,
        recipientId: Int,
        pageSize: Int,
        pageNumber: Int
    ): Flow<List<Message>> = flow {
        try {
            Log.d("FetchMessages", "Function fetchMessages started with pageSize: $pageSize, pageNumber: $pageNumber | $userId, $recipientId")
            val response = repository.getMessageById(userId, recipientId, pageSize, pageNumber)
            Log.d("ChatMessages", "Mapped Messages: $response")
            emit(response)

        } catch (e: Exception) {
            throw e
        }
    }
}