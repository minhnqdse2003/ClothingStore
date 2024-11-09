package com.example.prm392.data

import com.example.prm392.data.dto.Message.GetListChatModel
import com.example.prm392.data.dto.Message.GetMessagesResponseModel
import com.example.prm392.data.dto.Message.Message
import retrofit2.Response

import com.example.prm392.domain.model.Message.Request.SendMessageRequestModel

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface IMessageApi {
    @GET("api/chat/messages/{id}")
    suspend fun getMessageData(
        @HeaderMap headers: Map<String, String>,
        @Path("id") userId: Int = 71,
        @Query("recipientId") recipientId: Int = 3,
        @Query("pageSize") limit: Int = 50,
        @Query("pageNumber") skip: Int = 1
    ): List<Message>

    @GET("api/chat/recipients/{userId}")
    suspend fun getListChat(
        @HeaderMap headers: Map<String, String>,
        @Path("userId") userId: Int = 71,
    ): List<GetListChatModel>

    @POST("api/chat/send")
    suspend fun postMessageData(
        @HeaderMap headers: Map<String, String>,
        @Body sendMessageRequestModel: SendMessageRequestModel
    )

}