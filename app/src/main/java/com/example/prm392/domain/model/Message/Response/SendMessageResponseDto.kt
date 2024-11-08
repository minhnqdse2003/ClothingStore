package com.example.prm392.domain.model.Message.Response

import com.squareup.moshi.Json

data class SendMessageResponseDto (
        val success: Boolean,
        val message: String,
)