package com.example.prm392.presentation.login_screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LoginRegisterToggle(isLogin: Boolean, onToggle: (Boolean) -> Unit) {
    val activeColor = Color.Black
    val inactiveColor = Color(0xFF8F9CAe)
    val backgroundColor = Color(0xFFE2E8F0)

    // Animated colors
    val loginTextColor by animateColorAsState(
        targetValue = if (isLogin) activeColor else inactiveColor,
        animationSpec = tween(durationMillis = 300) // Duration of the animation
    )
    val registerTextColor by animateColorAsState(
        targetValue = if (isLogin) inactiveColor else activeColor,
        animationSpec = tween(durationMillis = 300)
    )
    val loginBackgroundColor by animateColorAsState(
        targetValue = if (isLogin) Color.White else Color.Transparent,
        animationSpec = tween(durationMillis = 300)
    )
    val registerBackgroundColor by animateColorAsState(
        targetValue = if (isLogin) Color.Transparent else Color.White,
        animationSpec = tween(durationMillis = 300)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .background(backgroundColor, shape = RoundedCornerShape(32.dp)) // Background color #e2e8f0
            .clip(RoundedCornerShape(32.dp))
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Login Text
        Text(
            text = "Login",
            color = loginTextColor,
            modifier = Modifier
                .clickable { onToggle(true) }
                .background(loginBackgroundColor, shape = RoundedCornerShape(36.dp))
                .weight(1f)
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        // Register Text
        Text(
            text = "Register",
            color = registerTextColor,
            modifier = Modifier
                .clickable { onToggle(false) }
                .background(registerBackgroundColor, shape = RoundedCornerShape(36.dp))
                .weight(1f)
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}