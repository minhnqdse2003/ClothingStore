package com.example.prm392.presentation.notification_screen.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTop(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                textAlign = TextAlign.Center,
                text = "Thông báo",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(
                 Icons.Default.ArrowBack,
                    contentDescription = "Back to Home Screen",
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = Modifier.height(60.dp),
    )
}
