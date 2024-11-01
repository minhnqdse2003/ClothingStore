package com.example.prm392.presentation.payment_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.prm392.R
import com.example.prm392.data.dto.cart.CartProductsResponseModelData
import com.example.prm392.data.dto.store_location.get_all.StoreLocation
import com.example.prm392.domain.model.Cart.request.CartItemRequestDto
import com.example.prm392.domain.model.Cart.request.CartItemRequestViewModel
import com.example.prm392.presentation.navigation.Screen
import com.example.prm392.presentation.product_screen.LoadingIndicator
import com.example.prm392.ui.theme.Vegur
import com.example.prm392.utils.Result
import com.example.prm392.utils.formatPrice
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel = hiltViewModel(),
    model: CartItemRequestDto?,
    models: List<CartProductsResponseModelData>? = emptyList(),
    navController: NavController,
) {
    val product by viewModel.product.collectAsState()
    val listOfStoreLocation = viewModel.storeLocations.collectAsState()
    var selectedLocationId by remember { mutableStateOf<Int?>(null) }
    var selectedLocationData by remember { mutableStateOf<StoreLocation?>(null) }

    LaunchedEffect(Unit) {
        if (model != null) {
            viewModel.getCurrentProduct(model.productID)
        } else if (models != null) {
            viewModel.setDataFromCart(models)
        }
    }

    LaunchedEffect(navController) {
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Int>("selectedId")
            ?.observeForever { id ->
                viewModel.getAllStoreLocation()
                selectedLocationId = id
            }
    }

    LaunchedEffect(listOfStoreLocation.value) {
        val result = listOfStoreLocation.value
        if(result !is Result.Success )
            return@LaunchedEffect
        if(result.data.isEmpty())
            return@LaunchedEffect
        if(selectedLocationId == null)
            return@LaunchedEffect
        selectedLocationData = result.data.first { it.locationID == selectedLocationId }
    }

    when (product) {
        is Result.Loading -> {
            LoadingIndicator()
        }

        is Result.Error -> {
            val errorMessage =
                (product as Result.Error).error.localizedMessage ?: "An error occurred."
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(8.dp)
            )
        }

        is Result.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                ) {
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
                            text = "Payment",
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
                    Text(
                        text = "Shipping Address",
                        fontSize = 18.sp,
                        fontFamily = Vegur,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Button(
                        onClick = {
                            navController.navigate(Screen.MapScreen.route)
                        }
                    ) {
                        Text(
                            text = selectedLocationData?.address ?: "Select Your Location Store",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = Vegur,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }

                    Card(
                        elevation = CardDefaults.cardElevation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        border = BorderStroke(.5.dp, Color.Gray),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )

                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(R.drawable.payoslogo),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp),
                                contentScale = ContentScale.Fit
                            )
                            Text(
                                text = "Payos",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }

                Column {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total",
                            fontSize = 18.sp,
                            fontFamily = Vegur,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            text = "${formatPrice((product as Result.Success<CartItemRequestViewModel>).data.price)} VNĐ",
                            fontSize = 18.sp,
                            fontFamily = Vegur,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }

                    FloatingActionButton(
                        onClick = {
                            viewModel.placeOrder()
                        },
                        containerColor = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Place your order",
                            fontSize = 18.sp,
                            fontFamily = Vegur,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

            }
        }

        else -> Unit
    }


}
