package com.example.prm392.presentation.home_screen

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.dto.category.get_all.Category
import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.data.dto.products.get_all.toGetAllProductResponseDto
import com.example.prm392.domain.model.ClothingProduct.request.ClothingProductFilterParam
import com.example.prm392.domain.service.Category.CategoryService
import com.example.prm392.domain.service.ClothingProduct.ClothingProductService
import com.example.prm392.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val clothingService: ClothingProductService,
    private val categoryService: CategoryService,
) : ViewModel() {
    var searchInput = mutableStateOf("")
        private set
    private var selectedCategory = mutableStateOf<Category?>(null)
        private set
    var selectedCategoryId = mutableStateOf<Int?>(null)
        private set

    private val _clothingProducts = MutableStateFlow<Result<List<Product>>>(Result.Idle)
    val clothingProducts: StateFlow<Result<List<Product>>> = _clothingProducts.asStateFlow()

    private val _categories = MutableStateFlow<Result<List<Category>>>(Result.Idle)
    val categories: StateFlow<Result<List<Category>>> = _categories.asStateFlow()

    val filteredClothingProducts: StateFlow<Result<List<Product>>> = _clothingProducts
        .map { result ->
            if (result is Result.Success) {
                val filteredData = result.data.filter { product ->
                    (searchInput.value.isEmpty() || product.productName.contains(
                        searchInput.value,
                        ignoreCase = true
                    )) &&
                            (selectedCategoryId.value == null || product.category.categoryID == selectedCategoryId.value)
                }
                Result.Success(filteredData)
            } else result
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), Result.Idle)

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

    fun loadMoreClothing(
        endRefresh: (() -> Unit)? = null,
        onFilter: Boolean = false
    ) {
        if ((_isLoading.value || !_hasMoreData.value) && !onFilter) return

        if(isFirstLoading) {
            _isLoading.value = true
        }

        viewModelScope.launch {
            clothingService.getAllClothing(
                currentPage.intValue,
                pageSize.intValue,
                ClothingProductFilterParam(
                    categoryId = selectedCategoryId.value,
                    searchValue = searchInput.value
                )
            )
                .onStart {
                    if (isFirstLoading) {
                        _clothingProducts.value = Result.Loading
                        isFirstLoading = false
                    }

                    onFilter?.let {
                        if(it)
                            _isLoading.value = true
                    }
                }
                .catch { exception ->
                    _clothingProducts.value = Result.Error(exception)
                    _isLoading.value = false
                    endRefresh?.invoke()
                }
                .collect { responseModel ->
                    val newItems = responseModel.toGetAllProductResponseDto().products
                    val currentItems =
                        (_clothingProducts.value as? Result.Success)?.data ?: emptyList()

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
        selectedCategory.value = null
        searchInput.value = ""
        selectedCategoryId.value = null
        loadMoreClothing(endRefresh)
    }

    fun onCategorySelected(category: Category) {
        selectedCategory.value = if (selectedCategoryId.value != category.categoryID) category else null
        selectedCategoryId.value = if (selectedCategoryId.value != category.categoryID) category.categoryID else null
        _clothingProducts.value = Result.Success(data = emptyList())
        currentPage.intValue = 1
        loadMoreClothing(onFilter = true)
    }

    fun onSearchCompleted(searchValue:String){
        searchInput.value = searchValue
        _clothingProducts.value = Result.Success(data = emptyList())
        currentPage.intValue = 1
        loadMoreClothing(onFilter = true)
    }

    private fun loadCategory() {
        viewModelScope.launch {
            categoryService.getAllCategoryService()
                .onStart {
                    _categories.value = Result.Loading
                }
                .catch {
                    _clothingProducts.value = Result.Error(it)
                }
                .collect { responseModel ->
                    _categories.value = Result.Success<List<Category>>(data = responseModel)
                }
        }
    }
}
