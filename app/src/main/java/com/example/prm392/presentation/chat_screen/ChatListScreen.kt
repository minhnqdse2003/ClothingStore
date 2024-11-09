package com.example.prm392.presentation.chat_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import com.example.prm392.utils.Result

import com.example.prm392.presentation.chat_list.components.ChatListItem
import com.example.prm392.presentation.detail_screen.components.ErrorScreen
import com.example.prm392.presentation.detail_screen.components.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val chatListDataResponse by viewModel.chatListDataResponse.collectAsState()

    LaunchedEffect(true) {
        viewModel.checkPageByRole(navController)
        viewModel.fetchListMessage()
    }
    LaunchedEffect(Unit) {
        viewModel.fetchListMessage()
    }

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
            Column(modifier = Modifier.padding(paddingValues)) {
                AnimatedContent(
                    targetState = chatListDataResponse,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(300, easing = LinearEasing)) togetherWith
                                fadeOut(animationSpec = tween(300, easing = LinearEasing))
                    }
                ) { response ->
                    when (response) {
                        is Result.Success -> {
                            LazyColumn(
                                contentPadding = PaddingValues(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            ) {
                                items(response.data) { chat ->
                                    ChatListItem(
                                        chat = chat,
                                        onClick = {
                                            viewModel.onChooseUser(user = chat.recipientId, navController = navController)
                                        }
                                    )
                                }
                            }
                        }
                        is Result.Loading -> {
                            LoadingScreen()
                        }
                        is Result.Error -> {
                            ErrorScreen(
                                productTitle = "Chat",
                                errorMsg = response.error.localizedMessage ?: "Something went wrong!"
                            )
                        }
                        else -> Unit
                    }
                }
            }
        }
    )
}


data class Chat(val id: Int,val title: String, val lastMessage: String, val timestamp: String)
