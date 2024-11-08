package com.example.prm392.presentation.home_screen.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.size.Size

@Composable
fun HorizontalCarousel(
    banners: List<Int>,
    onBannerClick: (Int) -> Unit // Callback for banner click events
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp), // Padding around the carousel
        horizontalArrangement = Arrangement.spacedBy(12.dp) // Spacing between items
    ) {
        items(banners) { banner ->
            BannerCard(bannerImageRes = banner, onClick = { onBannerClick(banner) })
        }
    }
}

@Composable
fun BannerCard(
    @DrawableRes bannerImageRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(300.dp) // Width of each banner card
            .height(180.dp) // Height of each banner card
            .clickable(onClick = onClick), // Clickable card
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Card background color
        ),
        border = BorderStroke(.1.dp, Color.Gray)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(bannerImageRes)
                .size(Size(300, 180))
                .build(),
            contentDescription = "Banner Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
