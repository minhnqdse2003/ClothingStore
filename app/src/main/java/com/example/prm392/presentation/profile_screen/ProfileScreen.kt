package com.example.prm392.presentation.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.size.Size
import com.example.prm392.ui.theme.Vegur
import com.example.prm392.utils.Constants
import com.example.prm392.utils.MySpacer

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Box(
      modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp)
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
            ){
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
                text = "Nguyen Quy Duc Minh",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = Vegur,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            MySpacer(8.dp)
            Text(
                text = "minhnqdse172303@fpt.edu.vn",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = Vegur,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            MySpacer(8.dp)
            
        }
    }
}