package com.example.prm392.presentation.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface NavigationDestination {
    val route: String
}

sealed class Screen(override val route: String) : NavigationDestination {
    object LoginScreen : Screen("login")
    object HomeScreen : Screen("home")
    object SearchScreen : Screen("search")
    object DetailScreen : Screen("detail/{title}")
    object ProductDetailScreen : Screen("product_detail")
    object ProductPaymentScreen : Screen("payment")
    object MapScreen : Screen("map")
    object ChatScreen : Screen("message")
    object ChatListScreen : Screen("chat")
    object CartScreen : Screen("cart")
    object ProfileScreen : Screen("profile")
    object NotificationScreen : Screen("notify")
}

class Navigator {
    private val _destination = MutableStateFlow<NavigationDestination>(Screen.LoginScreen)
    val destination = _destination.asStateFlow()

    // Method to update the current destination
    fun navigate(destination: NavigationDestination) {
        _destination.value = destination
    }
}
