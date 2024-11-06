package com.example.prm392.presentation.notification_screen.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTop() {
    TopAppBar(
        title = {
            Text(
                textAlign = TextAlign.Center,
                text = "Thông báo",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = Modifier.height(60.dp),
    )
}
