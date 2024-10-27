package com.example.prm392.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.dto.toProductData
import com.example.prm392.data.dto.toProductSearchResultData
import com.example.prm392.domain.model.ProductData
import com.example.prm392.domain.model.ProductSearchResultData
import com.example.prm392.domain.service.Services
import com.example.prm392.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val services: Services
) : ViewModel() {
    private val _productDataResponse = MutableStateFlow<Result<ProductData>>(Result.Idle)
    val productDataResponse = _productDataResponse.asStateFlow()

    private val _searchDataResponse = MutableStateFlow<Result<ProductSearchResultData>>(Result.Idle)
    val searchDataResponse = _searchDataResponse.asStateFlow()

    fun getProductData(
        limit: Int,
        skip: Int
    ) = viewModelScope.launch {
        services.getProductDataService(limit,skip)
            .onStart {
                _productDataResponse.value = Result.Loading
            }
            .catch {
                _productDataResponse.value = Result.Error(it)
            }
            .collect{
                val result = it.toProductData()
                _productDataResponse.value = Result.Success(result)
            }
    }

    fun getSearchProductData(
        limit: Int,
        skip: Int,
        value: String
    ) = viewModelScope.launch {
        services.getSearchProductDataService(value, skip, limit)
            .onStart {
                _searchDataResponse.value = Result.Loading
            }
            .catch {
                _searchDataResponse.value = Result.Error(it)
            }
            .collect{
                val result = it.toProductSearchResultData()
                _searchDataResponse.value = Result.Success(result)
            }
    }
}