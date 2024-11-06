package com.example.prm392.domain.service.MessageService

import com.example.prm392.data.dto.Message.SendMessageResponseModel
import com.example.prm392.domain.model.Message.Request.SendMessageRequestModel
import com.example.prm392.domain.model.Message.Response.SendMessageResponseDto
import com.example.prm392.domain.repository.IMessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SendMessage @Inject constructor(
    private val repository: IMessageRepository
) {
    suspend operator fun invoke(
        requestModel: SendMessageRequestModel
    ): Flow<Result<Unit>> = flow {
        try {
            repository.sendMesssage(requestModel)
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}