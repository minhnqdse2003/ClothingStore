package com.example.prm392.presentation.notification_screen.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prm392.presentation.notification_screen.NotifyViewModel


@Composable
fun NotificationList(
    modifier: Modifier = Modifier,
    viewModel: NotifyViewModel = hiltViewModel()
) {
    val notifications = viewModel.notifications.collectAsState().value
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier, state = listState
    ) {
        items(notifications.size) { index ->
            val notification = notifications[index]
            NotificationItem(notification = notification)
            LaunchedEffect(remember { derivedStateOf { listState.firstVisibleItemIndex } }) {
                val firstVisibleItem = listState.firstVisibleItemIndex
                if (index == firstVisibleItem && !notification.isRead) {
                    viewModel.markAsRead(notification.id)
                }
            }
        }
    }
}
