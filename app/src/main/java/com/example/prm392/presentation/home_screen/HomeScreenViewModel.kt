package com.example.prm392.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.data.dto.products.get_all.toGetAllProductResponseDto
import com.example.prm392.domain.service.ClothingProduct.ClothingProductService
import com.example.prm392.presentation.navigation.Navigator
import com.example.prm392.utils.Result
import com.example.prm392.utils.TokenSlice
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(
    private val repository: ClothingProductService,
    private val tokenSlice: TokenSlice,
    private val navigator: Navigator,
) : ViewModel() {

    private val _clothingProducts = MutableStateFlow<Result<List<Product>>>(Result.Idle)
    val clothingProducts = _clothingProducts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _hasMoreData = MutableStateFlow(true)
    val hasMoreData = _hasMoreData.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private var currentPage = 1
    private val pageSize = 20
    private var isFirstLoading = false

    init {
        loadMoreClothing()
    }

    fun loadMoreClothing() {
        // Avoid loading more if already loading or there's no more data
        if (_isLoading.value || !_hasMoreData.value) return

        _isLoading.value = true

        viewModelScope.launch {
            repository.getAllClothing(currentPage, pageSize)
                .onStart {
                    if (!_isRefreshing.value) {
                        _clothingProducts.value = Result.Loading // Only show loading for paginated loads
                    }
                }
                .catch { exception ->
                    _clothingProducts.value = Result.Error(exception)
                    _isLoading.value = false
                }
                .collect { responseModel ->
                    if(!isFirstLoading) isFirstLoading = true

                    val newItems = responseModel.toGetAllProductResponseDto().products
                    val currentItems = (_clothingProducts.value as? Result.Success)?.data ?: emptyList()

                    _clothingProducts.value = Result.Success(
                        data = currentItems + newItems
                    )

                    // Handle refreshing logic
                    if (_isRefreshing.value) {
                        currentPage = 1
                        _hasMoreData.value = true
                        _isRefreshing.value = false
                    } else {
                        currentPage = responseModel.currentPage + 1
                        _hasMoreData.value = responseModel.currentPage < responseModel.totalPages
                        _isLoading.value = false
                    }
                }
        }
    }

    fun refreshClothing() {
        _isRefreshing.value = true
        currentPage = 1
        loadMoreClothing()
    }
}
