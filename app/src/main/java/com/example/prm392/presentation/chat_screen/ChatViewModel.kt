package com.example.prm392.presentation.chat_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.prm392.data.SignalRClient
import com.example.prm392.data.dto.Message.GetListChatModel
import com.example.prm392.data.dto.Message.Message
import com.example.prm392.domain.model.Message.Request.SendMessageRequestModel
import com.example.prm392.domain.service.MessageService.MessageService
import com.example.prm392.domain.service.Services
import com.example.prm392.presentation.navigation.Navigator
import com.example.prm392.presentation.navigation.Screen
import com.example.prm392.utils.Result
import com.example.prm392.utils.TokenSlice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val services: MessageService,
    val tokenSlice: TokenSlice,
    private val signalRClient: SignalRClient,
) : ViewModel() {

    private val _chatDataResponse = MutableStateFlow<Result<List<Message>>>(Result.Idle)
    private val _chatListDataResponse =
        MutableStateFlow<Result<List<GetListChatModel>>>(Result.Idle)
    val chatDataResponse = _chatDataResponse.asStateFlow()
    val chatListDataResponse = _chatListDataResponse.asStateFlow()

    private val currentMessages = mutableListOf<Message>()

    private var currentPage = 1

    private var receptId = 1

    val isLoading = mutableStateOf(false)


    init {
//        observeNewMessages()
//      signalRClient.startConnection()
    }

    suspend fun checkPageByRole(navController: NavController) {
        val role = tokenSlice.role.first() ?: "Customer"
        if (role == "Staff") {
            navController.navigate(Screen.ChatListScreen.route)
        } else {
            onChooseUser(1, navController)
        }
    }

    fun onChooseUser(user: Int, navController: NavController) {
        receptId = user
        navController.navigate(Screen.ChatScreen.route)
    }

    fun fetchListMessage() {
        viewModelScope.launch {
            tokenSlice.userId.first()?.let {
                services.getListChat(userId = it.toInt()).onStart {
                    _chatListDataResponse.value = Result.Loading
                }.catch { exception ->
                    _chatListDataResponse.value = Result.Error(exception)

                }.collect { res ->
                    _chatListDataResponse.value = Result.Success(res)

                }
            }

        }
    }

    fun fetchMessages(pageSize: Int, pageNumber: Int) {
        Log.d(
            "FetchMessages",
            "Function fetchMessages started with pageSize: $pageSize, pageNumber: $pageNumber"
        )
        viewModelScope.launch {
            val senderMessagesList = mutableListOf<Message>()
            val recipientMessagesList = mutableListOf<Message>()
            try {
                isLoading.value = true

                val id = tokenSlice.userId.first() ?: return@launch
                val role = tokenSlice.role.first() ?: "Customer"
//                if (role == "Staff") {
//                    receptId
//                }
                Log.d("ReceptId", "$receptId")
                services.getMessageById(id.toInt(), receptId, pageSize, pageNumber)
                    .onStart {
                        _chatDataResponse.value = Result.Loading
                    }
                    .catch { exception ->
                        _chatDataResponse.value = Result.Error(exception)
                    }
                    .collect { response ->
                        senderMessagesList.addAll(response)
                    }

                services.getMessageById(receptId, id.toInt(), pageSize, pageNumber)
                    .onStart {
                    }
                    .catch { exception ->
                        _chatDataResponse.value = Result.Error(exception)
                    }
                    .collect { response ->
                        recipientMessagesList.addAll(response)
                    }
                val allMessages = (senderMessagesList + recipientMessagesList)
                    .sortedBy { it.sentAt }
                currentMessages.addAll(0, allMessages)

                _chatDataResponse.value = Result.Success(currentMessages)
                currentPage = pageNumber
                isLoading.value = false

            } catch (e: Exception) {
                isLoading.value = false
                _chatDataResponse.value = Result.Error(e)
            }
        }
    }

    fun sendMessage(messageText: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val senderId = tokenSlice.userId.first() ?: return@launch
                val role = tokenSlice.role.first() ?: "Customer"
                if (role == "Staff") {
                    receptId = 1
                }

                val requestModel = SendMessageRequestModel(
                    userId = senderId.toInt(),
                    recipientId = receptId,
                    message = messageText,
                    sentAt = OffsetDateTime.now(ZoneOffset.UTC).toString()
                )
                val result = services.sendMessage(requestModel)
                Log.d("Test1", "${result.first()}")
                if (result.first().isSuccess) {
                    isLoading.value = false
                    val newMessage = Message(
                        userId = senderId.toInt(),
                        recipientId = receptId,
                        message = messageText,
                        sentAt = OffsetDateTime.now(ZoneOffset.UTC).toString()
                    )
                    currentMessages.add(newMessage)
                    _chatDataResponse.value = Result.Success(currentMessages)
                }

            } catch (e: Exception) {
                isLoading.value = false

                Log.d("Test1", "Exp ${e}")

            }
        }
    }

    fun observeNewMessages() {
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
