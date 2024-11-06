package com.example.prm392.presentation.chat_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prm392.data.dto.Message.Message
import com.example.prm392.presentation.chat_screen.ChatViewModel
import com.example.prm392.utils.TokenSlice
import kotlinx.coroutines.launch

@Composable
fun MessageList(messages: List<Message>) {
    val listState = rememberLazyListState()
    var showScrollToBottom by remember { mutableStateOf(false) }
    var selectedMessageIndex by remember { mutableStateOf(-1) }
    val coroutineScope = rememberCoroutineScope()
    val tokenSlice: TokenSlice = hiltViewModel<ChatViewModel>().tokenSlice
    var userId by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        tokenSlice.userId.collect { id ->
            userId = id?.toInt() ?: 0
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { firstVisibleItemIndex ->
                showScrollToBottom = firstVisibleItemIndex < (messages.size - 20)
            }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(messages.size) { index ->
                val message = messages[index]
                val isMe = message.userId == userId
                val isSelected = index == selectedMessageIndex

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedMessageIndex = if (isSelected) -1 else index },
                    horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .shadow(elevation = 3.dp, shape = RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isMe) Color.DarkGray else Color.White)
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = message.message,
                                color = if (isMe) Color.White else Color.DarkGray
                            )
                            if (isSelected) {
                                Text(
                                    text = message.sentAt.toString(),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showScrollToBottom) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(messages.lastIndex)
                    }
                },
                modifier = Modifier
                    .size(75.dp)
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(40.dp),
                containerColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDownward,
                    contentDescription = "Scroll"
                )
            }
        }
    }
}
