package com.example.prm392.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prm392.presentation.SearchScreen
import com.example.prm392.presentation.components.animations.Animations
import com.example.prm392.presentation.detail_screen.DetailScreen
import com.example.prm392.presentation.login_screen.LoginScreen
import com.example.prm392.utils.TokenSlice

@Composable
fun AppNavigation(
    navigator: Navigator,
    tokenSlice: TokenSlice
) {
    val navController = rememberNavController()
    val destination = navigator.destination.collectAsState()

    LaunchedEffect(destination.value) {
        if(navController.currentDestination?.route == Screen.LoginScreen.route)
            navController.navigate(Screen.SearchScreen.route)

        if (navController.currentDestination?.route == destination.value.route)
            return@LaunchedEffect

        when (destination.value) {
            is Screen.LoginScreen -> {
                if (tokenSlice.tokenExists()) {
                    navController.navigate(Screen.SearchScreen.route)
                } else {
                    navController.navigate(Screen.LoginScreen.route)
                }
            }

            else -> {
                navController.navigate(destination.value.route)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen()
        }

        composable(route = Screen.SearchScreen.route) {
            SearchScreen(
                onItemClick = { title ->
                    navController.navigate(
                        route = "${Screen.DetailScreen.route}/$title"
                    )
                }
            )
        }

        composable(
            route = "${Screen.DetailScreen.route}/{title}",
            arguments = listOf(
                navArgument("title") {
                    type = NavType.StringType
                }
            ),
            enterTransition = { Animations.horizontalSlideIn() },
            exitTransition = { Animations.horizontalSlideOut() }
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("title").let { title ->
                DetailScreen(title!!)
            }
        }
    }
}