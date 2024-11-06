package com.example.prm392.domain.service.MessageService

import android.util.Log
import com.example.prm392.data.dto.Message.GetMessagesResponseModel
import com.example.prm392.data.dto.Message.toGetMessageResDto
import com.example.prm392.domain.model.Message.Response.GetMessageResponseDto
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
    ): Flow<GetMessagesResponseModel> = flow {
        try {
            val response = repository.getMessageById(userId, recipientId, pageSize, pageNumber)
            Log.d("ChatMessages", "Mapped Messages: $response")
            emit(response)

        } catch (e: Exception) {
            throw e
        }
    }
}