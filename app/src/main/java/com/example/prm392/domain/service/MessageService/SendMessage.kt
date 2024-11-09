package com.example.prm392.domain.service.MessageService

import android.util.Log
import com.example.prm392.data.dto.Message.SendMessageResponseModel
import com.example.prm392.domain.model.Message.Request.SendMessageRequestModel
import com.example.prm392.domain.model.Message.Response.SendMessageResponseDto
import com.example.prm392.domain.repository.IMessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SendMessage @Inject constructor(
    private val repository: IMessageRepository
) {
    suspend operator fun invoke(
        requestModel: SendMessageRequestModel
    ): Flow<Result<Unit>> = flow {

        repository.sendMessage(requestModel)
        emit(Result.success(Unit))
        Log.d("SendMessage", "Message sent successfully")
    }.catch { e ->
        Log.d("SendMessage", "Failed to send message: ${e.message}")
        emit(Result.failure(e))
    }
}