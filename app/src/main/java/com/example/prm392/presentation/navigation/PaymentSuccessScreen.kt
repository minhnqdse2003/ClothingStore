package com.example.prm392.presentation.navigation

import android.net.Uri
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.prm392.ui.theme.Vegur
import kotlinx.coroutines.delay

@Composable
fun PaymentSuccessScreen(modifier: Modifier = Modifier, onNavigateToHomePage: () -> Unit) {
    var showHomePage by remember { mutableStateOf(false) }
    var remainingSeconds by remember { mutableStateOf(5) }

    LaunchedEffect(Unit) {
        while (remainingSeconds > 0) {
            delay(1000)
            remainingSeconds--
        }
        showHomePage = true
    }

    if (showHomePage) {
        onNavigateToHomePage()
    }

    // UI Content
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Webm here
        LottieAnimationFromUrl(url = "https://lottie.host/63fd09b4-3c5f-4d90-874a-3f3d559cc463/4AHxjbBh6N.json")

        Text(
            text = "Payment Successful!",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Green,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Thank you for your purchase!",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = { onNavigateToHomePage() }, // Go to home page immediately
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green.copy(.7f))
        ) {
            Text(
                text = "Go to Home Page",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = Vegur,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "Redirecting in ${remainingSeconds} second${if (remainingSeconds != 1) "s" else ""}",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = Vegur,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun LottieAnimationFromUrl(url: String) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Url(url))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier.size(200.dp)
    )
}