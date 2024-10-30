package com.example.prm392.presentation.home_screen

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.dto.category.get_all.toGetAllCategoryDto
import com.example.prm392.data.dto.category.get_all.Category
import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.data.dto.products.get_all.toGetAllProductResponseDto
import com.example.prm392.domain.service.Category.CategoryService
import com.example.prm392.domain.service.ClothingProduct.ClothingProductService
import com.example.prm392.presentation.navigation.Navigator
import com.example.prm392.utils.Result
import com.example.prm392.utils.TokenSlice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val clothingService: ClothingProductService,
    private val categoryService: CategoryService,
) : ViewModel() {

    private val _clothingProducts = MutableStateFlow<Result<List<Product>>>(Result.Idle)
    val clothingProducts: StateFlow<Result<List<Product>>> = _clothingProducts.asStateFlow()

    private val _categories = MutableStateFlow<Result<List<Category>>>(Result.Idle)
    val categories : StateFlow<Result<List<Category>>> = _categories.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
        .onStart {
            loadMoreClothing()
            loadCategory()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )

    private val _hasMoreData = MutableStateFlow(true)
    val hasMoreData: StateFlow<Boolean> = _hasMoreData

    private val _isRefreshing = MutableStateFlow(false)

    private var currentPage = mutableIntStateOf(1)
    private val pageSize = mutableIntStateOf(10)
    private var isFirstLoading = true

    fun loadMoreClothing(endRefresh: (() -> Unit)? = null) {
        if (_isLoading.value || !_hasMoreData.value) return

        _isLoading.value = true

        viewModelScope.launch {
            clothingService.getAllClothing(currentPage.intValue, pageSize.intValue)
                .onStart {
                    if (isFirstLoading) {
                        _clothingProducts.value = Result.Loading
                        isFirstLoading = false
                    }
                }
                .catch { exception ->
                    _clothingProducts.value = Result.Error(exception)
                    _isLoading.value = false
                    endRefresh?.invoke()
                }
                .collect { responseModel ->
                    val newItems = responseModel.toGetAllProductResponseDto().products
                    val currentItems = (_clothingProducts.value as? Result.Success)?.data ?: emptyList()

                    _clothingProducts.value = Result.Success(data = currentItems + newItems)

                    _hasMoreData.value = responseModel.currentPage < responseModel.totalPages
                    if (_isRefreshing.value) {
                        _isRefreshing.value = false
                        currentPage.intValue += 1
                    } else if (_hasMoreData.value) {
                        currentPage.intValue += 1
                    }

                    endRefresh?.invoke()
                    _isLoading.value = false
                }
        }
    }

    fun refreshClothing(endRefresh: (() -> Unit)? = null) {
        _isRefreshing.value = true
        currentPage.intValue = 1
        _hasMoreData.value = true
        _clothingProducts.value = Result.Success(data = emptyList())
        loadMoreClothing(endRefresh)
    }

    private fun loadCategory(){
        viewModelScope.launch {
            categoryService.getAllCategoryService()
                .onStart {
                    _categories.value = Result.Loading
                }
                .catch {
                    _clothingProducts.value = Result.Error(it)
                }
                .collect{ responseModel ->
                    val data = responseModel.toGetAllCategoryDto().data
                    _categories.value = Result.Success<List<Category>>(data = data)
                }
        }
    }
}
