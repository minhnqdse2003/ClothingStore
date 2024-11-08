package com.example.prm392.data

import com.example.prm392.data.dto.Message.GetListChatModel
import com.example.prm392.data.dto.Message.GetMessagesResponseModel
import com.example.prm392.data.dto.ProductDataDto
import com.example.prm392.data.dto.Users.LoginResponseModel
import com.example.prm392.domain.model.Message.Request.SendMessageRequestModel
import com.example.prm392.domain.model.Message.Response.GetMessageResponseDto
import com.example.prm392.domain.model.User.Request.LoginRequestModel
import com.example.prm392.utils.Constants
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query

interface IMessageApi {
    @GET("api/chat/messages")
    suspend fun getMessageData(
        @HeaderMap headers: Map<String, String>,
        @Query("userId") userId: Int = 71,
        @Query("recipientId") recipientId: Int = 3,
        @Query("pageSize") limit: Int = 50,
        @Query("pageNumber") skip: Int = 0
    ): GetMessagesResponseModel
    @GET("api/chat/recipients")
    suspend fun getListChat(
        @HeaderMap headers: Map<String, String>,
        @Query("userId") userId: Int = 71,
    ): List<GetListChatModel>

    @POST("messages")
    suspend fun postMessageData(
        @HeaderMap headers: Map<String, String>,
        @Body sendMessageRequestModel: SendMessageRequestModel
    ): GetMessagesResponseModel

}