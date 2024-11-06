package com.example.prm392.data

import com.example.prm392.data.dto.Message.Message
import com.example.prm392.utils.Constants
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignalRClient @Inject constructor() {
    private val hubConnection: HubConnection = HubConnectionBuilder
        .create(Constants.SIGNALR_URL)
        .build()

    private val _newMessages = MutableSharedFlow<Message>(
        replay = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val newMessages: SharedFlow<Message> get() = _newMessages

    init {
        setupListeners()
    }

    private fun setupListeners() {
        hubConnection.on("ReceiveMessage", { message: Message ->
            _newMessages.tryEmit(message)
        }, Message::class.java)
    }

    fun startConnection() {
        if (hubConnection.connectionState == HubConnectionState.DISCONNECTED) {
            hubConnection.start().blockingAwait()
        }
    }

    fun stopConnection() {
        if (hubConnection.connectionState == HubConnectionState.CONNECTED) {
            hubConnection.stop().blockingAwait()
        }
    }
}
