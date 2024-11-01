package com.example.prm392.presentation.map_screen.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.prm392.data.dto.store_location.get_all.StoreLocation
import com.example.prm392.utils.formatKmDistance
import com.google.android.gms.maps.model.LatLng

@Composable
fun LocationSelector(
    locations: List<StoreLocation>,
    currentLocation: LatLng,
    onLocationSelected: (Int) -> Unit
) {
    var selectedLocation = remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(.8f)
            .fillMaxWidth()
    ) {
        items(locations) { location ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedLocation.value = location.locationID
                    }
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedLocation.value == location.locationID,
                    onClick = {}
                )

                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "Location marker",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    tint = Color.Gray
                )

                Text(
                    text = location.address,
                    modifier = Modifier.weight(3f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${formatKmDistance(
                        currentLocation,
                        LatLng(location.latitude, location.longitude)
                    )} km",
                    modifier = Modifier.weight(1f),
                    maxLines = 1
                )
            }
        }
    }
    Button(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        onClick = {
            selectedLocation.value?.let { onLocationSelected(it) }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black
        ),
    ) {
        Text(
            text = "Place order here",
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.White
            )
        )
    }
}
