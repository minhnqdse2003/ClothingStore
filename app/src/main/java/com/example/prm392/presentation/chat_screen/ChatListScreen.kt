package com.example.prm392.presentation.chat_screen

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.prm392.presentation.MainViewModel
import com.example.prm392.presentation.chat_list.components.ChatListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val chats = listOf(
        Chat("Shop Support", "How can we help you?", "10:30 AM"),
        Chat("John Doe", "Got the information, thanks!", "Yesterday"),
        Chat("Family Group", "Let's plan for the weekend!", "2 days ago"),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat with shop") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                modifier = Modifier.shadow(elevation = 3.dp, shape = RoundedCornerShape(16.dp)),
            )
        },
        containerColor = Color.White,

        content = { paddingValues ->
            LazyColumn(
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(chats) { chat ->
                    ChatListItem(
                        chat = chat,
                        onClick = {
                            navController.navigate("message/${chat.title}")
                        }
                    )
                }
            }
        }
    )
}

data class Chat(val title: String, val lastMessage: String, val timestamp: String)
