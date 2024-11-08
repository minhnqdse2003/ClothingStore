package com.example.prm392.presentation.chat_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MessageInput(
    newMessage: String,
    onMessageChanged: (String) -> Unit,
    onMessageSent: () -> Unit,
) {
    val tint = if (newMessage.isEmpty()) Color.LightGray else Color.DarkGray

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
//            .padding(bottom = 80.dp)
            .imePadding()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
            .background(Color.White, shape = RoundedCornerShape(16.dp))
    ) {Row(
        modifier = Modifier
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BasicTextField(
            value = newMessage,
            onValueChange = onMessageChanged,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .weight(2f)
                .background(Color(0xFFE0E0E0))
                .padding(12.dp),
            singleLine = false,
            maxLines = 7,
            textStyle = TextStyle(fontSize = 16.sp),
            decorationBox = { innerTextField ->
                if (newMessage.isEmpty()) {
                    Text(
                        fontSize = 16.sp,
                        text = "Type message here...",
                        color = Color.Gray)} else return@BasicTextField innerTextField()
            },
        )
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(onClick = onMessageSent) {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send",
                tint = tint
            )
        }
    }
}}