package com.example.prm392.data.dto.Message

import com.example.prm392.domain.model.Message.Response.GetMessageResponseDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetMessagesResponseModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "value")
    val messages: List<Message>
) {
    override fun toString(): String {
        return "GetMessagesResponseModel(id='$id', messages=$messages)"
    }
}

fun GetMessagesResponseModel.toGetMessageResDto(): GetMessageResponseDto {
    return GetMessageResponseDto(
        messages = this.messages
    )
}