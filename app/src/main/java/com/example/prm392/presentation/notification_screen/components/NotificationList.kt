package com.example.prm392.presentation.notification_screen.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prm392.presentation.notification_screen.NotifyViewModel


@Composable
fun NotificationList(
    modifier: Modifier = Modifier,
    viewModel: NotifyViewModel = hiltViewModel()
) {
    val notifications = viewModel.notifications.collectAsState().value

    LazyColumn(modifier = modifier) {
        items(notifications.size) { index ->
            NotificationItem(notification = notifications[index])
        }
    }
}
