package com.example.prm392.data.repository

import android.util.Log
import com.example.prm392.data.IMessageApi
import com.example.prm392.data.dto.Message.GetListChatModel
import com.example.prm392.data.dto.Message.GetMessagesResponseModel
import com.example.prm392.data.dto.Message.Message
import com.example.prm392.data.dto.Message.SendMessageResponseModel
import com.example.prm392.domain.model.Message.Request.SendMessageRequestModel
import com.example.prm392.domain.repository.IMessageRepository
import com.example.prm392.domain.repository.IRemoteDataRepository
import com.example.prm392.utils.HeaderProcessing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val api: IMessageApi,
    private val headerProcessing: HeaderProcessing
) : IMessageRepository {
    override suspend fun getMessageById(
        userId: Int,
        recipientId: Int,
        pageSize: Int,
        pageNumber: Int
    ): List<Message> {
        return withContext(Dispatchers.Default) {
            Log.d("MessageRepository", "getMessageById called with recipientId: $recipientId")
            val header = headerProcessing.createDynamicHeaders(
                isTokenIncluded = true
            )
            api.getMessageData(header,userId, recipientId, pageSize, pageNumber)
        }
    }
    override suspend fun getListChat(
        userId: Int,
    ): List<GetListChatModel> {
        return withContext(Dispatchers.Default) {
            val header = headerProcessing.createDynamicHeaders(
                isTokenIncluded = true
            )
            api.getListChat(header,userId)
        }
    }

    override suspend fun sendMessage(requestModel: SendMessageRequestModel
    ) {
        return withContext(Dispatchers.Default) {
            val header = headerProcessing.createDynamicHeaders(
                isTokenIncluded = true
            )
            api.postMessageData(header, requestModel)
        }
    }
}