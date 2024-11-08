package com.example.prm392.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun MySpacer(size:Dp) = Spacer(modifier =  Modifier.size(size))

fun String.trimStartEnd() = this.trimStart().trimEnd()

@SuppressLint("DefaultLocale")
fun formatPrice(price: Double): String {
    return String.format("%,.0f VNĐ", price)
}


fun formatKmDistance(latLngFrom: LatLng, latLngTo: LatLng): Double {
    val distanceInMeters = SphericalUtil.computeDistanceBetween(latLngFrom, latLngTo)
    val distanceInKilometers = distanceInMeters / 1000

    // Use BigDecimal for rounding to 1 decimal place
    return BigDecimal(distanceInKilometers).setScale(1, RoundingMode.HALF_UP).toDouble()
}