import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.prm392.presentation.navigation.Screen
import com.example.prm392.utils.modifierUtils.topBorder

@Composable
fun BottomNavigationComponent(
    navController: NavController,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    tonalElevation: Dp = NavigationBarDefaults.Elevation
) {
    val items = listOf(
        NavigationItem("Home", Icons.Filled.Home, Screen.HomeScreen.route),
        NavigationItem("Chat", Icons.AutoMirrored.Filled.Chat, Screen.ChatListScreen.route),
        NavigationItem("Cart", Icons.Filled.ShoppingCart, Screen.CartScreen.route),
        NavigationItem("Profile", Icons.Filled.Person, Screen.ProfileScreen.route)
    )

    val currentRoute by navController.currentBackStackEntryAsState()

    NavigationBar(
        modifier = modifier
            .topBorder(12.dp, color = Color.Gray),
        containerColor = Color.White,
        contentColor = contentColor,
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.route
                    )
                },
                label = {
                    Text(item.title)
                },
                selected = currentRoute?.destination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                )
            )
        }
    }
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)