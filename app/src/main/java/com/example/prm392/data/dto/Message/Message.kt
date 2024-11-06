package com.example.prm392.data.dto.Message
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message (
    @Json(name = "id")
    val id: Int,
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "recipientId")
    val recipientId: Int,
    @Json(name = "message")
    val message: String,
    @Json(name = "sentAt")
    val sentAt: Int
)