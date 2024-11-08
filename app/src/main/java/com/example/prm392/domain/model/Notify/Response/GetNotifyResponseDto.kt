package com.example.prm392.domain.model.Notify.Response

import com.example.prm392.data.dto.Notify.Notification

data class GetNotifyResponseDto(
    val Notifications: List<Notification>
)