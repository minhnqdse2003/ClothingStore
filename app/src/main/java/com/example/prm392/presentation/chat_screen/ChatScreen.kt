package com.example.prm392.presentation.chat_screen

import ChatTopBar
import MessageInput
import MessageList
import android.widget.GridLayout.Alignment
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
import com.example.prm392.presentation.MainViewModel
import com.example.prm392.presentation.detail_screen.components.ErrorScreen
import com.example.prm392.presentation.detail_screen.components.LoadingScreen
import com.example.prm392.utils.Constants
import com.example.prm392.utils.Result

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: MainViewModel = hiltViewModel()) {
    LaunchedEffect(true) {
        viewModel.getSearchProductData(Constants.ITEM_LIMIT, 0, "Chat")
    }

    val productDataResponse by viewModel.searchDataResponse.collectAsState()

    Scaffold(
        topBar = { ChatTopBar(title = "Chat with .....") },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                AnimatedContent(
                    targetState = productDataResponse,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(300, easing = LinearEasing)) togetherWith
                                fadeOut(animationSpec = tween(300, easing = LinearEasing))
                    }
                ) { response ->
                    when (response) {
//                        is Result.Success -> MessageList(messages = response.data)
                        is Result.Loading -> LoadingScreen()
                        is Result.Error -> ErrorScreen(
                            productTitle = "Chat",
                            errorMsg = response.error.localizedMessage ?: "Something went wrong!"
                        )
                        else -> Unit
                    }
                }
                MessageInput(onMessageSent = { message -> viewModel.sendMessage(message) })
            }
        }
    )
}