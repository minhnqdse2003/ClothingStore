package com.example.prm392.presentation.product_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.domain.model.Cart.request.CartItemRequestDto
import com.example.prm392.presentation.navigation.Screen
import com.example.prm392.ui.theme.Vegur
import com.example.prm392.utils.MySpacer
import com.example.prm392.utils.Result
import com.example.prm392.utils.formatPrice

@Composable
fun ProductDetailsScreen(
    id: String,
    navController: NavController,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val product = viewModel.clothingProduct.collectAsState().value

    val onClickBuy = { requestModel: CartItemRequestDto ->
        navController.navigate(Screen.ProductPaymentScreen.route) {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                key = "Buy",
                value = CartItemRequestDto(
                    productID = requestModel.productID,
                    quantity = requestModel.quantity
                )
            )
        }
    }

    LaunchedEffect(id) {
        viewModel.getProduct(id.toInt())
    }


    Box(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp)
                .fillMaxWidth(),
        ) {
            FloatingActionButton(
                onClick = {
                    viewModel.addToCart {
                        navController.navigate(Screen.CartScreen.route) {
                            popUpTo(Screen.CartScreen.route) {inclusive = true}
                            launchSingleTop = true
                        }
                    }
                },
                containerColor = Color.White,
                modifier = Modifier
                    .weight(.5f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.ShoppingCart,
                        "Add to Cart",
                        tint = Color.Black
                    )
                    Text(
                        text = "ADD TO CART",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = Vegur,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            FloatingActionButton(
                onClick = {
                    onClickBuy(
                        CartItemRequestDto(id.toInt(), 1)
                    )
                },
                containerColor = Color.Black,
                contentColor = Color.White,
                modifier = Modifier
                    .weight(.5f),
            ) {
                Text(
                    text = "BUY NOW",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = Vegur,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
        }



        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Text(
                    text = "Detail Product",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = Vegur,
                        fontWeight = FontWeight.Bold,
                    )
                )

                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Transparent
                    )
                }
            }

            when (product) {
                is Result.Loading -> {
                    LoadingIndicator()
                }

                is Result.Error -> {
                    ErrorDisplay(product.error)
                }

                is Result.Success -> {
                    ProductDetailsContent(product.data)
                }

                else -> Unit
            }
        }
    }
}

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator()
}

@Composable
fun ErrorDisplay(error: Throwable) {
    Text(text = "Error: ${error.message}")
}

@Composable
fun ProductDetailsContent(product: Product) {
    Column(
        modifier = Modifier
    ) {
        AsyncImage(
            model = product.imageURL,
            contentDescription = "Product image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = product.productName,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = Vegur,
                    fontWeight = FontWeight.Bold,
                )
            )
            MySpacer(8.dp)
            Text(
                text = formatPrice(product.price),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Red,
                    fontFamily = Vegur,
                    fontWeight = FontWeight.Bold,
                )
            )
            MySpacer(8.dp)
            Text(
                text = product.fullDescription,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Gray,
                    fontFamily = Vegur,
                    fontWeight = FontWeight.Normal,
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}
