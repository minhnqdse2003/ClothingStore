package com.example.prm392.presentation.payment_screen

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.prm392.presentation.navigation.Screen

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PayOsPaymentScreen(navController: NavController, paymentUrl: String) {
    var loading = true
    AndroidView(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                        if (request.url.toString().contains("https://www.youtube.com/")) {
                            loading = false
                            Log.d("Payment", "Payment successfully!")
                            navController.navigate(Screen.PaymentSuccessScreen.route)
                        }
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }
                loadUrl(paymentUrl)
            }
        }
    )
}