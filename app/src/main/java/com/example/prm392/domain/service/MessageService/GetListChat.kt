package com.example.prm392.domain.service.MessageService

import android.util.Log
import com.example.prm392.data.dto.Message.GetListChatModel
import com.example.prm392.data.dto.Message.GetMessagesResponseModel
import com.example.prm392.domain.repository.IMessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetListChat @Inject constructor(
    private val repository: IMessageRepository
) {
    suspend operator fun invoke(
        userId: Int,
    ): Flow<List<GetListChatModel>> = flow {
        try {
            val response = repository.getListChat(userId)
            Log.d("List Chat", "Mapped chat: $response")
            emit(response)

        } catch (e: Exception) {
            throw e
        }
    }
}