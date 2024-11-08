package com.example.prm392.domain.model.Message.Request

import com.squareup.moshi.Json

data class MessageRequestModel(
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "recipientId")
    val recipientId: Int,
    @Json(name = "pageSize")
    val pageSize: Int,
    @Json(name = "pageNumber")
    val pageNumber: Int

)