package com.example.prm392

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.prm392.presentation.navigation.AppNavigation
import com.example.prm392.presentation.navigation.Navigator
import com.example.prm392.ui.theme.PRM392Theme
import com.example.prm392.utils.TokenSlice
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var navigator: Navigator
    @Inject lateinit var tokenSlice: TokenSlice
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PRM392Theme {
                AppNavigation(navigator,tokenSlice)
            }
        }
    }
}
