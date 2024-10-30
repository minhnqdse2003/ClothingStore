package com.example.prm392.presentation.product_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.data.dto.products.get_by_id.toGetProductByIdResponseDto
import com.example.prm392.domain.service.ClothingProduct.ClothingProductService
import com.example.prm392.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val clothingProductService: ClothingProductService
) : ViewModel() {
    private val _clothingProduct = MutableStateFlow<Result<Product>>(Result.Idle)
    val clothingProduct = _clothingProduct.asStateFlow()

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
                    _clothingProduct.value = Result.Success<Product>(product.toGetProductByIdResponseDto().data)
                }
        }
    }
}
