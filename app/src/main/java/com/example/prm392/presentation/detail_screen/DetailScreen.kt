package com.example.prm392.presentation.detail_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.rounded.PriceChange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.prm392.presentation.MainViewModel
import com.example.prm392.presentation.detail_screen.components.ErrorScreen
import com.example.prm392.presentation.detail_screen.components.LoadingScreen
import com.example.prm392.presentation.detail_screen.components.ProductData
import com.example.prm392.presentation.navigation.Screen
import com.example.prm392.utils.Constants
import com.example.prm392.utils.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    title: String,
    viewModel: MainViewModel = hiltViewModel(),
    onChatClick: (String) -> Unit
) {
    LaunchedEffect(
        key1 = true,
        block = {
            viewModel.getSearchProductData(Constants.ITEM_LIMIT, 0,title)
        }
    )
    val productDataResponse = viewModel.searchDataResponse.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Product Database",
                        fontFamily = FontFamily.Serif,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Rounded.PriceChange,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton( onClick =  {
                        onChatClick("Chat")
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Message,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        AnimatedContent(
            targetState = productDataResponse,
            label = "animated_content",
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(
                        300,
                        easing = LinearEasing
                    )
                ) togetherWith fadeOut(
                    animationSpec = tween(
                        300,
                        easing = LinearEasing
                    )
                )
            }
        ) { response ->
            when (response) {
                is Result.Success -> {
                    val productData = response.data
                    ProductData(productData)
                }

                is Result.Loading -> {
                    LoadingScreen()
                }

                is Result.Error -> {
                    val errorMsg = response.error.localizedMessage ?: "Something went wrong!"
                    ErrorScreen(
                        productTitle = title,
                        errorMsg = errorMsg
                    )
                }

                else -> Unit
            }
        }
    }
}