package com.example.prm392.presentation.notification_screen


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prm392.presentation.chat_screen.ChatViewModel
import com.example.prm392.presentation.notification_screen.components.NotificationList
import com.example.prm392.presentation.notification_screen.components.NotificationTop


@Composable
fun NotificationScreen(viewModel: NotifyViewModel = hiltViewModel(),) {
    Scaffold(
        topBar = { NotificationTop() },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        NotificationList(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        )
    }
}
