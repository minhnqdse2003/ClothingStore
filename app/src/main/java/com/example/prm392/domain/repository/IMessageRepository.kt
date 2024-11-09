package com.example.prm392.domain.repository

import com.example.prm392.data.dto.Message.GetListChatModel
import com.example.prm392.data.dto.Message.GetMessagesResponseModel
import com.example.prm392.data.dto.Message.Message
import com.example.prm392.data.dto.Message.SendMessageResponseModel
import com.example.prm392.domain.model.Message.Request.SendMessageRequestModel

interface IMessageRepository {
    suspend fun getMessageById(
        userId: Int,
        recipientId: Int,
        pageSize: Int,
        pageNumber: Int
    ): List<Message>

    suspend fun getListChat(
        userId: Int,
    ): List<GetListChatModel>

    suspend fun sendMessage(requestModel: SendMessageRequestModel)
}