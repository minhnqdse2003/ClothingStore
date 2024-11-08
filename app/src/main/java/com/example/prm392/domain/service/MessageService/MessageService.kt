package com.example.prm392.domain.service.MessageService

data class MessageService(
    val getMessageById: GetMessageById,
    val sendMessage: SendMessage,
    val getListChat: GetListChat
)