package com.example.prm392.presentation.product_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.domain.model.Cart.request.CartItemRequestDto
import com.example.prm392.domain.service.Cart.CartService
import com.example.prm392.domain.service.ClothingProduct.ClothingProductService
import com.example.prm392.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val clothingProductService: ClothingProductService,
    private val cartService: CartService
) : ViewModel() {
    private val _clothingProduct = MutableStateFlow<Result<Product>>(Result.Idle)
    val clothingProduct = _clothingProduct.asStateFlow()

    private val _addUserCartResponse = MutableStateFlow<Result<Unit>>(Result.Idle)
    val addUserCartResponse = _addUserCartResponse.asStateFlow()

    fun getProduct(id: Int) {
        viewModelScope.launch {
            clothingProductService.getProductById(id)
                .onStart {
                    _clothingProduct.value = Result.Loading
                }
                .catch { exception ->
                    _clothingProduct.value = Result.Error(exception)
                }
                .collect { product ->
                    _clothingProduct.value = Result.Success<Product>(product)
                }
        }
    }

    fun addToCart(onNavigateToCart: () -> Unit) {
        viewModelScope.launch {
            if (_clothingProduct.value is Result.Success<Product>) {
                cartService.addUserCartService(
                    CartItemRequestDto(
                        productID = (_clothingProduct.value as Result.Success<Product>).data.productID,
                        quantity = 1
                    )
                )
                    .onStart {
                        _addUserCartResponse.value = Result.Loading // Indicate loading state
                    }
                    .catch { exception ->
                        _addUserCartResponse.value = Result.Error(exception)
                        onNavigateToCart()
                    }
                    .collect {
                        onNavigateToCart()
                    }
            }
        }
    }
}
