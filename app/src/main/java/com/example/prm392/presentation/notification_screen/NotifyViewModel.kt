package com.example.prm392.presentation.notification_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.dto.Notify.Notification
import com.example.prm392.domain.service.Services
import com.example.prm392.utils.TokenSlice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    private val services: Services,
    val tokenSlice: TokenSlice,

    ) : ViewModel() {
    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> get() = _notifications

    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        viewModelScope.launch {
            try {
                val id = tokenSlice.userId.first() ?: return@launch
                services.getNotifyService(userId = id.toInt()).collect { responseList ->
                    val notificationsList = responseList.map { response ->
                        Notification(
                            id = response.id,
                            userId = response.userId,
                            message = response.message,
                            isRead = response.isRead,
                            createdAt = response.createdAt
                        )
                    }
                    _notifications.value = notificationsList
                }
            } catch (e: Exception) {
            }
        }
    }

    fun markAsRead(notificationId: Int) {
        viewModelScope.launch {
            services.updateStatus(notificationId)
        }
    }
}