package com.example.prm392.domain.model.Message.Request

import com.squareup.moshi.Json
import java.time.OffsetDateTime

data class SendMessageRequestModel (
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "recipientId")
    val recipientId: Int,
    @Json(name = "message")
    val message: String,
    @Json(name = "sentAt")
    val sentAt: OffsetDateTime
)