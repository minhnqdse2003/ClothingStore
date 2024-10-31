package com.example.prm392.presentation.payment_screen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.data.dto.products.get_by_id.toGetProductByIdResponseDto
import com.example.prm392.domain.model.Cart.request.CartItemRequestDto
import com.example.prm392.domain.model.Order.OrderRequestDto
import com.example.prm392.domain.service.ClothingProduct.ClothingProductService
import com.example.prm392.utils.Result
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@HiltViewModel
class PaymentViewModel @Inject constructor(
    application: Application,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val service: ClothingProductService
) : AndroidViewModel(application) {

    private val _address = MutableStateFlow<String?>("Fetching address...")
    val address = _address.asStateFlow()

    private val _product = MutableStateFlow<Result<Product>>(Result.Idle)
    val product = _product.asStateFlow()

    private val cartItems = mutableListOf<CartItemRequestDto>()

    fun getCurrentProduct(id : Int){
        viewModelScope.launch {
            service.getProductById(id)
                .onStart {
                    _product.value = Result.Loading
                }
                .catch { exception ->
                    _product.value = Result.Error(exception)
                }
                .collect { product ->
                    val responseData = Result.Success<Product>(product.toGetProductByIdResponseDto().data)
                    _product.value = responseData

                    cartItems.add(
                        CartItemRequestDto(
                            productID = responseData.data.productID,
                            quantity = 1
                        )
                    )
                }
        }
    }

    fun placeOrder() {
        val requestModel = _address.value?.let {
            OrderRequestDto(
                cartItems = cartItems,
                billingAddress = it
            )
        }
        Log.d("Order", requestModel.toString())
    }

    fun fetchCurrentLocation() {
        viewModelScope.launch {
            try {
                if (!checkLocationPermission()) {
                    _address.value = "Location permission not granted"
                    return@launch
                }

                // Fetch last known location
                val location = getLastKnownLocation()
                location?.let { currentLocation ->
                    val geocoder = Geocoder(getApplication(), Locale.getDefault())
                    val addresses = geocoder.getFromLocation(
                        currentLocation.latitude,
                        currentLocation.longitude,
                        1
                    )
                    _address.value =
                        addresses?.firstOrNull()?.getAddressLine(0) ?: "Address not found"
                } ?: run {
                    _address.value = "Unable to fetch location"
                }
            } catch (e: IOException) {
                _address.value = "Geocoder error: ${e.message}"
            } catch (e: Exception) {
                _address.value = "Error fetching location: ${e.message}"
            }
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLastKnownLocation() =
        suspendCancellableCoroutine<Location?> { continuation ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location -> continuation.resume(location) }
                .addOnFailureListener { exception -> continuation.resumeWithException(exception) }
        }

    private fun checkLocationPermission(): Boolean {
        val context = getApplication<Application>()
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

}
