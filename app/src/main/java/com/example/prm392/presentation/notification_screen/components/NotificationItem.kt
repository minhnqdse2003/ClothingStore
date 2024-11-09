package com.example.prm392.presentation.notification_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prm392.data.dto.Notify.Notification
import com.example.prm392.presentation.notification_screen.NotifyViewModel


@Composable
fun NotificationItem(notification: Notification, viewModel: NotifyViewModel = hiltViewModel()
) {
    val backgroundColor = if (!notification.isRead) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.surface
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                viewModel.markAsRead(notification.id)
            }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Message,
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = notification.message,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = notification.createdAt,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}