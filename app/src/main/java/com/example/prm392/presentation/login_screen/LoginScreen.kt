package com.example.prm392.presentation.login_screen

import LoginComponent
import RegisterComponent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.prm392.R
import com.example.prm392.presentation.login_screen.components.LoginRegisterToggle
import com.example.prm392.utils.MySpacer

@Composable
fun LoginScreen() {
    // State to track whether the user is on the login or register screen
    var isLogin by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = rememberAsyncImagePainter(R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Foreground Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(top = 32.dp)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LoginRegisterToggle(isLogin) { isLogin = it }
            MySpacer(16.dp)
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(
                        Color.White.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = if (isLogin) "Login" else "Register",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Display the relevant component based on the switcher
                if (isLogin) {
                    LoginComponent()
                } else {
                    RegisterComponent()
                }
            }
        }
    }
}
