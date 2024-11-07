package com.example.prm392.presentation.payment_screen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.dto.cart.CartProductsResponseModelData
import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.data.dto.products.get_by_id.toGetProductByIdResponseDto
import com.example.prm392.data.dto.store_location.get_all.StoreLocation
import com.example.prm392.domain.model.Cart.request.CartItemRequestDto
import com.example.prm392.domain.model.Cart.request.CartItemRequestViewModel
import com.example.prm392.domain.model.Order.OrderRequestDto
import com.example.prm392.domain.service.ClothingProduct.ClothingProductService
import com.example.prm392.domain.service.store_location.StoreLocationService
import com.example.prm392.utils.Result
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.OkHttpClient
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@HiltViewModel
class PaymentViewModel @Inject constructor(
    application: Application,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val service: ClothingProductService,
    private val storeLocationService: StoreLocationService,
) : AndroidViewModel(application) {

    private val _address = MutableStateFlow<String?>("Fetching address...")
    val address = _address.asStateFlow()

    private val _currentLocationLatLng = MutableStateFlow<LocationModel?>(null)
    val currentLocationLatLng = _currentLocationLatLng.asStateFlow()

    private val _storeLocations = MutableStateFlow<Result<List<StoreLocation>>>(Result.Idle)
    val storeLocations = _storeLocations.asStateFlow()

    private val _product = MutableStateFlow<Result<CartItemRequestViewModel>>(Result.Idle)
    val product = _product.asStateFlow()

    private val _selectedLocation = mutableStateOf<Int>(1)

    private val cartItems = mutableListOf<CartItemRequestDto>()

    fun getCurrentProduct(id: Int) {
        viewModelScope.launch {
            service.getProductById(id)
                .onStart {
                    _product.value = Result.Loading
                }
                .catch { exception ->
                    _product.value = Result.Error(exception)
                }
                .collect { product ->
                    val responseData =
                        Result.Success<Product>(product)
                    _product.value = Result.Success<CartItemRequestViewModel>(
                        CartItemRequestViewModel(
                            price = responseData.data.price
                        )
                    )

                    cartItems.add(
                        CartItemRequestDto(
                            productID = responseData.data.productID,
                            quantity = 1
                        )
                    )
                }
        }
    }

    fun getAllStoreLocation() {
        viewModelScope.launch {
            storeLocationService.getAllStoreLocation()
                .onStart {
                    _storeLocations.value = Result.Loading // Set loading state
                }
                .catch { exception ->
                    _storeLocations.value = Result.Error(exception) // Handle errors
                }
                .collect { locations ->
                    _storeLocations.value = Result.Success(locations)
                }
        }
    }


    fun placeOrder() {
        val requestModel = _address.value?.let {
            OrderRequestDto(
                cartItems = cartItems,
                billingAddress = it,
                _selectedLocation.value
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
                val location = getCurrentLocation()
                location?.let { currentLocation ->
                    _address.value = getCompleteAddressString(
                        currentLocation.latitude,
                        currentLocation.longitude
                    )
                    _currentLocationLatLng.value = LocationModel(
                        Lat = currentLocation.latitude,
                        Lng = currentLocation.longitude
                    )
                    getAllStoreLocation()
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

    private fun getCompleteAddressString(LATITUDE: Double, LONGITUDE: Double): String {
        var strAdd = ""
        val geocoder = Geocoder(getApplication(), Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                val returnedAddress: Address = addresses[0]
                val strReturnedAddress = StringBuilder("")

                for (i in 0..returnedAddress.getMaxAddressLineIndex()) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                strAdd = strReturnedAddress.toString()
                Log.w("My Current loction address", strReturnedAddress.toString())
            } else {
                Log.w("My Current loction address", "No Address returned!")
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.w("My Current loction address", "Canont get Address!")
        }
        return strAdd
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLastKnownLocation() =
        suspendCancellableCoroutine<Location?> { continuation ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location -> continuation.resume(location) }
                .addOnFailureListener { exception -> continuation.resumeWithException(exception) }
        }

    @SuppressLint("MissingPermission")
    private suspend fun getCurrentLocation(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            )
                .addOnSuccessListener { location ->
                    continuation.resume(location)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
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

    fun setDataFromCart(models: List<CartProductsResponseModelData>) {
        var price: Double = 0.0
        for (product in models) {
            price += product.product.price * product.quantity
            cartItems.add(
                CartItemRequestDto(
                    quantity = product.quantity,
                    productID = product.product.productID
                )
            )
        }
        _product.value = Result.Success<CartItemRequestViewModel>(
            CartItemRequestViewModel(
                price = price
            )
        )
    }

}

data class LocationModel(
    val Lat: Double,
    val Lng: Double
)
