package com.example.prm392.data.dto.Notify

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)

data class GetNotifyResponseModel(
    @Json(name = "NotificationId")
    val id: Int,
    @Json(name = "UserId")
    val userId: Int,
    @Json(name = "Message")
    val message: String,
    @Json(name = "IsRead")
    val isRead: Boolean,
    @Json(name = "CreatedAt")
    val createdAt: String
) {
    override fun toString(): String {
        return "GetNotifyResponseModel(id='$userId', messages=$message)"
    }
}
