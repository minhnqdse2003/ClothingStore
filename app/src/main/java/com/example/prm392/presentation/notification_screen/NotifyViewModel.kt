package com.example.prm392.presentation.notification_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.dto.Notify.Notification
import com.example.prm392.domain.service.Services
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    private val services: Services,

    ) : ViewModel() {
    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> get() = _notifications

    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        viewModelScope.launch {
            try {
//                val fetchedNotifications = services.getNotifications()
//                _notifications.value = fetchedNotifications
            } catch (e: Exception) {
            }
        }
}
    private fun updateNotification() {
        viewModelScope.launch {
            try {
//                val fetchedNotifications = services.getNotifications()
//                _notifications.value = fetchedNotifications
            } catch (e: Exception) {
            }
        }
    }

    }