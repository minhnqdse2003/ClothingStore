package com.example.prm392.domain.model.Message.Response

import com.example.prm392.data.dto.Message.Message

data class GetMessageResponseDto (
    val messages: List<Message>
    )