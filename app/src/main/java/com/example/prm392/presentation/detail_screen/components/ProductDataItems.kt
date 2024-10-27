package com.example.prm392.presentation.detail_screen.components

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductDataItems(
    title: String,
    description:String
) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color(0XFF3559E0),
                    fontWeight = FontWeight.SemiBold
                )
            ){
                append("$title:")
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 20.sp
                )
            ){
                append("$description:")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 2.dp)
    )
}