package com.example.prm392.presentation.chat_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.SignalRClient
import com.example.prm392.data.dto.Message.Message
import com.example.prm392.domain.model.Message.Request.SendMessageRequestModel
import com.example.prm392.domain.service.Services
import com.example.prm392.utils.Result
import com.example.prm392.utils.TokenSlice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val services: Services,
     val tokenSlice: TokenSlice,
    private val signalRClient: SignalRClient
) : ViewModel() {

    private val _chatDataResponse = MutableStateFlow<Result<List<Message>>>(Result.Idle)
    val chatDataResponse = _chatDataResponse.asStateFlow()

    private val currentMessages = mutableListOf<Message>()

    init {
        observeNewMessages()
        signalRClient.startConnection()
    }

    fun fetchMessages(pageSize: Int, pageNumber: Int) {
        viewModelScope.launch {
            val senderMessagesList = mutableListOf<Message>()
            val recipientMessagesList = mutableListOf<Message>()

            try {
                val id = tokenSlice.userId.first() ?: return@launch
                services.getMessageService(id.toInt(), 1, pageSize, pageNumber)
                    .collect { response ->
                        senderMessagesList.addAll(response.messages)
                    }
                services.getMessageService(1, id.toInt(), pageSize, pageNumber)
                    .collect { response ->
                        recipientMessagesList.addAll(response.messages)
                    }
                currentMessages.addAll((senderMessagesList + recipientMessagesList)
                    .sortedBy { it.sentAt })

                _chatDataResponse.value = Result.Success(currentMessages)
            } catch (e: Exception) {
                _chatDataResponse.value = Result.Error(e)
            }
        }
    }

    fun sendMessage(recipientId: Int, messageText: String) {
        viewModelScope.launch {
            try {
                val senderId = tokenSlice.userId.first() ?: return@launch
                val requestModel = SendMessageRequestModel(
                    userId = senderId.toInt(),
                    recipientId = recipientId,
                    message = messageText,
                    sentAt = OffsetDateTime.now(ZoneOffset.UTC)
                )

                services.sendMessageService(requestModel)
                val newMessage = Message(
                    id = 0,
                    userId = senderId.toInt(),
                    recipientId = recipientId,
                    message = messageText,
                    sentAt = System.currentTimeMillis().toInt()
                )
                currentMessages.add(newMessage)
                _chatDataResponse.value = Result.Success(currentMessages)

            } catch (e: Exception) {
                _chatDataResponse.value = Result.Error(e)
            }
        }
    }


    private fun observeNewMessages() {
        viewModelScope.launch {
            signalRClient.newMessages.collectLatest { newMessage ->
                currentMessages.add(newMessage)
                _chatDataResponse.value = Result.Success(currentMessages)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        signalRClient.stopConnection()
    }
}
