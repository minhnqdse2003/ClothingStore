package com.example.prm392.data.dto.Message

import com.squareup.moshi.Json

data class GetListChatModel (
    @Json(name = "recipientId")
    val recipientId: Int,
    @Json(name = "username")
    val username: String,
)