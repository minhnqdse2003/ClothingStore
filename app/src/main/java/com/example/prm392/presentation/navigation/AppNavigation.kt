package com.example.prm392.presentation.navigation

import BottomNavigationComponent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prm392.data.dto.cart.CartProductsResponseModelData
import com.example.prm392.domain.model.Cart.request.CartItemRequestDto
import com.example.prm392.presentation.SearchScreen
import com.example.prm392.presentation.detail_screen.DetailScreen
import com.example.prm392.presentation.home_screen.HomeScreen
import com.example.prm392.presentation.login_screen.LoginScreen
import com.example.prm392.presentation.chat_screen.ChatScreen
import com.example.prm392.presentation.cart_screen.CartScreen
import com.example.prm392.presentation.map_screen.MapScreen
import com.example.prm392.presentation.payment_screen.PaymentScreen
import com.example.prm392.presentation.product_screen.ProductDetailsScreen
import com.example.prm392.presentation.chat_screen.ChatListScreen
import com.example.prm392.presentation.notification_screen.NotificationScreen
import com.example.prm392.presentation.profile_screen.ProfileScreen
import com.example.prm392.utils.TokenSlice
import kotlinx.coroutines.flow.first

@Composable
fun AppNavigation(
    tokenSlice: TokenSlice
) {
    val navController = rememberNavController()
    val tokenExists = tokenSlice.token.collectAsState(initial = null).value != null
    val role = tokenSlice.role.collectAsState(initial = null).value
    val userId = tokenSlice.userId.collectAsState(initial = null).value

    LaunchedEffect (Unit) {
        tokenSlice.decodeTokenPayload(tokenSlice.token.first() ?: return@LaunchedEffect)
    }
    val startDestination = if (tokenExists) {
        Screen.HomeScreen.route
    } else {
        Screen.LoginScreen.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable("/*") {
            if (!tokenExists) {
                navController.navigate(Screen.LoginScreen.route)
            }
        }

        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController)
        }

        composable(route = Screen.SearchScreen.route) {
            SearchScreen(
                onItemClick = { title ->
                    navController.navigate("${Screen.DetailScreen.route}/$title")
                }
            )
        }

        composable(
            route = "${Screen.DetailScreen.route}/{title}",
            arguments = listOf(navArgument("title") { type = NavType.StringType }),
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("title")?.let { title ->
                DetailScreen(title)
            }
        }
        composable(
            route = Screen.NotificationScreen.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
        ) { navBackStackEntry ->
            NotificationScreen(navController = navController)
        }
        composable(
            route = Screen.ChatScreen.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
        ) { navBackStackEntry ->
            ChatScreen(navController = navController)
        }

        composable(
            route = "${Screen.ProductDetailScreen.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType }),
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("id")?.let { id ->
                ProductDetailsScreen(id,navController)
            }
        }

        // Home screen with Bottom Navigation
        composable(route = Screen.HomeScreen.route) {
            Scaffold(
                bottomBar = { BottomNavigationComponent(navController = navController) },
                containerColor = Color.White
            ) { paddingValues ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInHorizontally(initialOffsetX = { it }),
                    exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it })
                ) {
                    HomeScreen(paddingValues = paddingValues, navController = navController)
                }
            }
        }

        composable(route = Screen.ChatListScreen.route) {
            Scaffold(
                bottomBar = { BottomNavigationComponent(navController = navController) },
                containerColor = Color.White
            ) { paddingValues ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInHorizontally(initialOffsetX = { it }),
                    exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it })
                ) {
                    ChatListScreen(
                        navController = navController,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }

        // Cart screen with Bottom Navigation
        composable(route = Screen.CartScreen.route) {
            Scaffold(
                bottomBar = { BottomNavigationComponent(navController = navController) },
                containerColor = Color.White
            ) { paddingValues ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInHorizontally(initialOffsetX = { it }),
                    exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it })
                ) {
                    CartScreen(modifier = Modifier.padding(paddingValues),navController = navController)
                }
            }
        }

        // Profile screen with Bottom Navigation
        composable(route = Screen.ProfileScreen.route) {
            Scaffold(
                bottomBar = { BottomNavigationComponent(navController = navController) },
                containerColor = Color.White
            ) { paddingValues ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInHorizontally(initialOffsetX = { it }),
                    exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it })
                ) {
                    ProfileScreen(modifier = Modifier.padding(paddingValues))
                }
            }
        }

        composable(route = Screen.ProductPaymentScreen.route) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInHorizontally(initialOffsetX = { it }),
                exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it })
            ) {
                val model:CartItemRequestDto? = navController.previousBackStackEntry?.savedStateHandle?.get<CartItemRequestDto>("Buy")
                val models = navController.previousBackStackEntry?.savedStateHandle?.get<List<CartProductsResponseModelData>>("Buy Cart")
                PaymentScreen(model = model, navController = navController, models = models)
            }
        }

        composable(route = Screen.MapScreen.route) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInHorizontally(initialOffsetX = { it }),
                exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it })
            ) {
                MapScreen(navController = navController)
            }
        }
    }
}
