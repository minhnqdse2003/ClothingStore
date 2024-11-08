package com.example.prm392.presentation.profile_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.size.Size
import com.example.prm392.data.dto.Users.GetUserProfile.GetUserProfileResponseModel
import com.example.prm392.data.dto.Users.GetUserProfile.Order
import com.example.prm392.presentation.product_screen.LoadingIndicator
import com.example.prm392.ui.theme.Vegur
import com.example.prm392.utils.Constants
import com.example.prm392.utils.MySpacer
import com.example.prm392.utils.Result
import com.example.prm392.utils.formatPrice

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val userProfile = viewModel.userProfile.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }

    when (userProfile) {
        is Result.Loading -> {
            LoadingIndicator()
        }

        is Result.Error -> {
            ErrorDisplay(userProfile.error)
        }

        is Result.Success -> {
            MainScreen(modifier = modifier, data = userProfile.data)
        }

        else -> Unit
    }
}

@Composable
fun MainScreen(data: GetUserProfileResponseModel, modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Constants.DEFAULT_USER_PROFILE)
                        .size(Size(150, 150))
                        .build(),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Text(
                text = data.username,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = Vegur,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            MySpacer(8.dp)
            Text(
                text = data.email,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = Vegur,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            MySpacer(8.dp)
            OrderHistoryList(data.orders)
        }
    }
}

@Composable
fun OrderHistoryList(orders: List<Order>) {
    LazyColumn {
        items(orders) { order ->
            OrderCard(order = order)
        }
    }
}

@Composable
fun OrderCard(order: Order) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Order #${order.orderId}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = Vegur,
                        fontWeight = FontWeight.Bold
                    )
                )
                Chip(
                    content = {
                        Text(
                            text = order.orderStatus,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Color.White
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    },
                    border = ChipDefaults.chipBorder(null),
                    onClick = {},
                    colors = ChipDefaults.chipColors(
                        backgroundColor = if (order.orderStatus == "Success") Color(0xFF4CAF50) else Color(
                            0xFFFFC107
                        )
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Date: ${order.orderDate}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    fontFamily = Vegur
                )
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Location",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = order.locationAddress,
                    style = MaterialTheme.typography.bodyMedium.copy(fontFamily = Vegur),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Billing Address",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = order.billingAddress,
                    style = MaterialTheme.typography.bodyMedium.copy(fontFamily = Vegur),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Payment Method",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = order.paymentMethod,
                    style = MaterialTheme.typography.bodyMedium.copy(fontFamily = Vegur)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = Vegur,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = formatPrice(order.totalPrice.toDouble()),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = Vegur,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}


@Composable
fun ErrorDisplay(error: Throwable) {
    Text(
        text = "Error: ${error.message}",
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
    )
}
