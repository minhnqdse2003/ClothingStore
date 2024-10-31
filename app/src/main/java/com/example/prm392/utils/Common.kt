package com.example.prm392.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun MySpacer(size:Dp) = Spacer(modifier =  Modifier.size(size))

fun String.trimStartEnd() = this.trimStart().trimEnd()

@SuppressLint("DefaultLocale")
fun formatPrice(price: Double): String {
    return String.format("%,.0f VNĐ", price)
}