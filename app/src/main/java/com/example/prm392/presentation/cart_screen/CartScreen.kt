package com.example.prm392.presentation.cart_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prm392.domain.model.Cart.response.CartResponseDto
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.prm392.R
import com.example.prm392.data.dto.cart.CartProductsResponseModelData
import com.example.prm392.domain.model.Cart.request.CartItemRequestDto
import com.example.prm392.presentation.navigation.Screen
import com.example.prm392.ui.theme.Vegur
import com.example.prm392.utils.Result
import com.example.prm392.utils.formatPrice

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    modifier: Modifier,
    navController: NavHostController
) {
    val cartResponse by viewModel.getUserCartResponse.collectAsState()
    val selectedProductIds = viewModel.selectedProducts.collectAsState().value
    val totalPrice = viewModel.totalPrice.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getUserCart()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (cartResponse) {
            is Result.Loading -> {
                // Display loading indicator
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is Result.Error -> {
                // Handle error
                Text(
                    text = "Error loading cart: ${(cartResponse as Result.Error).error.message}",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            is Result.Success -> {
                val cartItems = (cartResponse as Result.Success<CartResponseDto>).data.data
                Text(
                    text = "My Cart (${cartItems.product.size})",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cartItems.product) { item ->
                        CartItem(
                            cartProduct = item,
                            isSelected = selectedProductIds.contains(item.product.productID),
                            onSelectionChanged = { isSelected ->
                                viewModel.toggleProductSelection(item.product.productID, isSelected)
                            },
                            onRemove = { viewModel.removeUserCart(item.product.productID) },
                            onUpdateQuantity = { newQuantity ->
                                viewModel.updateCartItemQuantity(
                                    CartItemRequestDto(item.product.productID, newQuantity)
                                )
                            }
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Gray.copy(.2f),
                    thickness = 1.dp
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(

                    ) {
                        Text(
                            text = "Total Price",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Medium,
                        )

                        Text(
                            text = "${formatPrice(totalPrice)} VNĐ",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = colorResource(R.color.dark_green),
                                fontWeight = FontWeight.Bold,
                                fontFamily = Vegur
                            ),
                            fontWeight = FontWeight.Bold,
                        )

                    }

                    Button(
                        onClick = {
                            if (selectedProductIds.size != 0) {
                                navController.navigate(Screen.ProductPaymentScreen.route)
                            }
                        },
                        enabled = selectedProductIds.isNotEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        shape = RoundedCornerShape(0.dp),
                        colors = ButtonDefaults.buttonColors(Color.Black.copy(.7f))
                    ) {
                        Text(
                            text = "Checkout ( ${selectedProductIds.size} )",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = Vegur,
                                fontWeight = FontWeight.Bold,
                            ),
                            fontSize = 18.sp
                        )
                    }
                }
            }

            else -> Unit
        }
    }
}



@Composable
fun CartItem(
    cartProduct: CartProductsResponseModelData,
    isSelected: Boolean,
    onSelectionChanged: (Boolean) -> Unit,
    onRemove: () -> Unit,
    onUpdateQuantity: (Int) -> Unit
) {
    var quantity by remember { mutableIntStateOf(cartProduct.quantity) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onSelectionChanged(it) },
                modifier = Modifier.size(20.dp),
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Black,
                    uncheckedColor = Color.Gray
                )
            )

            Image(
                painter = rememberAsyncImagePainter(cartProduct.product.imageURL),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = cartProduct.product.productName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = formatPrice(cartProduct.product.price),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = Vegur
                    ),
                    color = colorResource(R.color.dark_green)
                )

                Row(
                    modifier = Modifier
                        .border(1.dp, Color.Black.copy(alpha = 0.1f)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        onClick = {
                            if (quantity > 1) {
                                quantity -= 1; onUpdateQuantity(quantity)
                            }
                        },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(0.dp),
                        modifier = Modifier
                            .border(.1.dp, Color.Gray.copy(alpha = 0.1f))
                            .size(width = 36.dp, height = 23.dp)
                            .fillMaxHeight(),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Remove,
                                contentDescription = "Decrease",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(12.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }

                    Text(
                        text = "$quantity",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = Vegur,
                            fontWeight = FontWeight.Bold,
                        ),
                        color = Color.Black
                    )

                    Card(
                        onClick = { quantity += 1; onUpdateQuantity(quantity) },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .border(.1.dp, Color.Gray.copy(alpha = 0.1f))
                            .size(width = 36.dp, height = 23.dp)
                            .fillMaxHeight(),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Increase",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(12.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}

