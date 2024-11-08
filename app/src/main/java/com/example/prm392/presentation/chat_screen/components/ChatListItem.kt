package com.example.prm392.presentation.chat_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prm392.presentation.chat_screen.Chat

@Composable
fun ChatListItem(
    chat: Chat,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
//            .background(Color(0xFFF2F2F2))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.Gray),

        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = chat.title,
                style = MaterialTheme.typography.titleMedium
            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = chat.lastMessage,
//                style = MaterialTheme.typography.bodySmall,
//                color = Color.Gray,
//                maxLines = 1
//            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = chat.timestamp,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}
