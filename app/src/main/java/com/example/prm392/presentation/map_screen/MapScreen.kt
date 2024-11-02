package com.example.prm392.presentation.map_screen

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.prm392.R
import com.example.prm392.presentation.map_screen.components.LocationSelector
import com.example.prm392.presentation.payment_screen.PaymentViewModel
import com.example.prm392.ui.theme.Vegur
import com.example.prm392.utils.Result
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    viewModel: PaymentViewModel = hiltViewModel(),
    navController: NavController
) {
    val locationPermissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    val context = LocalContext.current

    val originalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.logo)
    val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 150, 150, false)


    val currentLocationFlow = viewModel.currentLocationLatLng.collectAsState()
    val currentLocationMarkerState = rememberMarkerState()
    val cameraPositionState = rememberCameraPositionState()
    val listOfStoreLocation = viewModel.storeLocations.collectAsState()

    LaunchedEffect(locationPermissionState.status) {
        when (locationPermissionState.status) {
            is PermissionStatus.Granted -> {
                viewModel.fetchCurrentLocation()
            }

            is PermissionStatus.Denied -> {
                locationPermissionState.launchPermissionRequest()
            }
        }
    }

    LaunchedEffect(currentLocationFlow.value) {
        when (locationPermissionState.status) {
            is PermissionStatus.Granted -> {
                if (currentLocationFlow.value == null) return@LaunchedEffect

                val location: LatLng = LatLng(
                    currentLocationFlow.value!!.Lat,
                    currentLocationFlow.value!!.Lng
                )
                cameraPositionState.position = CameraPosition.fromLatLngZoom(location, 15f)
                currentLocationMarkerState.position = location
            }

            is PermissionStatus.Denied -> {
                locationPermissionState.launchPermissionRequest()
            }
        }
    }

    currentLocationFlow.value?.let { location ->
        Box(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.systemBars)
                .fillMaxSize()
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                // Current Location Marker
                currentLocationFlow.value?.let { location ->
                    Marker(
                        state = rememberMarkerState(position = LatLng(location.Lat, location.Lng)),
                        title = "Your Location",
                        snippet = "Current Position"
                    )
                }

                if (listOfStoreLocation.value is Result.Success) {
                    val locations = listOfStoreLocation.value as Result.Success
                    locations.data.forEach { storeLocation ->
                        Marker(
                            state = rememberMarkerState(
                                position = LatLng(
                                    storeLocation.latitude,
                                    storeLocation.longitude
                                )
                            ),
                            title = storeLocation.address,
                            snippet = storeLocation.address,
                            icon = BitmapDescriptorFactory.fromBitmap(resizedBitmap)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.End
            ) {
                Card (
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .size(70.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Button(
                        onClick = {
                            val currentLocation = LatLng(location.Lat, location.Lng)
                            cameraPositionState.position =
                                CameraPosition.fromLatLngZoom(currentLocation, 20f)
                        },
                        shape = CircleShape,
                        modifier = Modifier
                            .aspectRatio(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.NearMe,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .size(100.dp)
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxHeight(.5f)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 32.dp
                    )
                ) {
                    when (val result = listOfStoreLocation.value) {
                        is Result.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is Result.Error -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Error: ${result.error.message}",
                                    color = Color.Red
                                )
                            }
                        }

                        is Result.Success -> {
                            LocationSelector(
                                locations = result.data,
                                currentLocation = currentLocationMarkerState.position,
                                onChangeCameraPosition = { changeLocationLatLng: LatLng ->
                                    cameraPositionState.position =
                                        CameraPosition.fromLatLngZoom(changeLocationLatLng, 20f)
                                },
                                onLocationSelected = { locationID: Int ->
                                    navController.previousBackStackEntry?.savedStateHandle?.set(
                                        key = "selectedId",
                                        value = locationID
                                    )
                                    navController.popBackStack()
                                },
                            )
                        }

                        else -> Unit
                    }
                }
            }

        }

    }

    if (locationPermissionState.status is PermissionStatus.Denied &&
        (locationPermissionState.status as PermissionStatus.Denied).shouldShowRationale
    ) {
        Text(
            text = "Location permission is required to display your address.",
            color = MaterialTheme.colorScheme.error,
            fontFamily = Vegur,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
}