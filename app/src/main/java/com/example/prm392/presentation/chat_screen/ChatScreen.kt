package com.example.prm392.presentation.chat_screen

import ChatTop
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.prm392.presentation.chat_screen.components.MessageInput
import com.example.prm392.presentation.chat_screen.components.MessageList
import com.example.prm392.presentation.detail_screen.components.ErrorScreen
import com.example.prm392.presentation.detail_screen.components.LoadingScreen
import com.example.prm392.utils.Result
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel = hiltViewModel(), navController: NavController) {
    var role by remember { mutableStateOf<String?>("Customer") }
    LaunchedEffect(Unit) {
        viewModel.fetchMessages(pageSize = 20, pageNumber = 1)
    }
    LaunchedEffect(true) {
        role = viewModel.tokenSlice.role.first() ?: "Customer"
    }

//    val chatDataResponse: Result<List<Pair<String, String>>> = Result.Success(mockMessages)
    val chatDataResponse by viewModel.chatDataResponse.collectAsState()
    var newMessage by remember { mutableStateOf("") }

    BackHandler {
        if (role == "Staff") {
            navController.navigate("chat")
        } else {
            navController.navigate("home")
        }

    }
    Scaffold(
        topBar = { ChatTop(title = "", navController = navController) },
        bottomBar = {
            MessageInput(
                newMessage = newMessage,
                onMessageChanged = { newMessage = it },
                onMessageSent = {
                    Log.d("Test1", "$newMessage")
                    if (newMessage.isNotBlank()) {
                        viewModel.sendMessage(newMessage)
                        newMessage = ""
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                AnimatedContent(
                    targetState = chatDataResponse,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(300, easing = LinearEasing)) togetherWith
                                fadeOut(animationSpec = tween(300, easing = LinearEasing))
                    }
                ) { response ->
                    when (response) {
                        is Result.Success -> MessageList(messages = response.data, viewModel)
                        is Result.Loading -> LoadingScreen()
                        is Result.Error -> ErrorScreen(
                            productTitle = "Chat",
                            errorMsg = response.error.localizedMessage ?: "Something went wrong!"
                        )

                        else -> Unit
                    }
                }
            }
        }
    )
}