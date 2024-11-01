package com.example.prm392.presentation.cart_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prm392.data.dto.cart.toAddCartResponseDto
import com.example.prm392.data.dto.cart.toCartResponseDto
import com.example.prm392.data.dto.cart.toRemoveCartResponseDto
import com.example.prm392.data.dto.cart.toUpdateUserCartItemQuantityResponseDto
import com.example.prm392.domain.model.Cart.request.CartItemRequestDto
import com.example.prm392.domain.model.Cart.response.AddCartResponseDto
import com.example.prm392.domain.model.Cart.response.CartResponseDto
import com.example.prm392.domain.model.Cart.response.RemoveCartResponseDto
import com.example.prm392.domain.model.Cart.response.UpdateUserCartItemQuantityResponseDto
import com.example.prm392.domain.service.Cart.CartService
import com.example.prm392.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartService: CartService
) : ViewModel() {
    private val _selectedProducts = MutableStateFlow<Set<Int>>(emptySet())
    val selectedProducts = _selectedProducts.asStateFlow()

    private val _getUserCartResponse = MutableStateFlow<Result<CartResponseDto>>(Result.Idle)
    val getUserCartResponse = _getUserCartResponse.asStateFlow()

    private val _totalPrice = MutableStateFlow<Double>(0.0)
    val totalPrice = _totalPrice.asStateFlow()

    private val _addUserCartResponse = MutableStateFlow<Result<AddCartResponseDto>>(Result.Idle)
    val addUserCartResponse = _addUserCartResponse.asStateFlow()

    private val _removeUserCartResponse =
        MutableStateFlow<Result<RemoveCartResponseDto>>(Result.Idle)
    val removeUserCartResponse = _removeUserCartResponse.asStateFlow()

    private val _updateCartQuantityResponse =
        MutableStateFlow<Result<UpdateUserCartItemQuantityResponseDto>>(Result.Idle)
    val updateCartQuantityResponse = _updateCartQuantityResponse.asStateFlow()

    fun getUserCart() = viewModelScope.launch {
        cartService.getUserCartService()
            .onStart {
                _getUserCartResponse.value = Result.Loading
            }
            .catch {
                _getUserCartResponse.value = Result.Error(it)
            }
            .collect {
                _getUserCartResponse.value = Result.Success(
                    it.toCartResponseDto()
                )
            }
    }

    fun addUserCart(itemRequestDto: CartItemRequestDto) = viewModelScope.launch {
        cartService.addUserCartService(itemRequestDto)
            .onStart {
                _addUserCartResponse.value = Result.Loading
            }
            .catch {
                _addUserCartResponse.value = Result.Error(it)
            }
            .collect {
                _addUserCartResponse.value = Result.Success(it.toAddCartResponseDto())
            }
    }

    fun removeUserCart(cartItemId: Int) = viewModelScope.launch {
        val responseData = _getUserCartResponse.value
        if(responseData is Result.Success){
            val updatedProductList = responseData.data.data.product.filterNot { it.product.productID == cartItemId }

            _getUserCartResponse.value = Result.Success(
                responseData.data.copy(
                    data = responseData.data.data.copy(
                        product = updatedProductList
                    )
                )
            )
        }

        cartService.removeUserCartService(cartItemId)
            .onStart { _removeUserCartResponse.value = Result.Loading }
            .catch { _removeUserCartResponse.value = Result.Error(it) }
            .collect { it ->
                val currentCartResponse = _getUserCartResponse.value
                if(currentCartResponse is Result.Success){
                    val updatedProductList = currentCartResponse.data.data.product.filterNot { it.product.productID == cartItemId }

                    _getUserCartResponse.value = Result.Success(
                        currentCartResponse.data.copy(
                            data = currentCartResponse.data.data.copy(
                                product = updatedProductList
                            )
                        )
                    )
                }

                _removeUserCartResponse.value = Result.Success(it.toRemoveCartResponseDto())
            }
    }

    fun updateCartItemQuantity(itemRequestDto: CartItemRequestDto) = viewModelScope.launch {
        val currentResponse = _getUserCartResponse.value
        if (currentResponse is Result.Success) {
            val updatedCartItems = currentResponse.data.data.product.map { product ->
                if (product.product.productID == itemRequestDto.productID) {
                    product.copy(quantity = itemRequestDto.quantity) // Update the quantity
                } else {
                    product
                }
            }

            // Update the state with the new cart items list
            _getUserCartResponse.value = Result.Success(
                currentResponse.data.copy(
                    data = currentResponse.data.data.copy(product = updatedCartItems)
                )
            )
        }
        calculateTotal()
        cartService.updateUserCartItemQuantityService(itemRequestDto)
            .onStart { _updateCartQuantityResponse.value = Result.Loading }
            .catch { _updateCartQuantityResponse.value = Result.Error(it) }
            .collect {
                _updateCartQuantityResponse.value =
                    Result.Success(it.toUpdateUserCartItemQuantityResponseDto())
                val currentResponse = _getUserCartResponse.value
                if (currentResponse is Result.Success) {
                    val updatedCartItems = currentResponse.data.data.product.map { product ->
                        if (product.product.productID == itemRequestDto.productID) {
                            product.copy(quantity = itemRequestDto.quantity) // Update the quantity
                        } else {
                            product
                        }
                    }

                    _getUserCartResponse.value = Result.Success(
                        currentResponse.data.copy(
                            data = currentResponse.data.data.copy(product = updatedCartItems)
                        )
                    )
                }
                calculateTotal()
            }
    }


    fun toggleProductSelection(productId: Int, isSelected: Boolean) {
        _selectedProducts.value = if (isSelected) {
            _selectedProducts.value + productId
        } else {
            _selectedProducts.value - productId  // Remove from set
        }
        calculateTotal()
    }

    fun calculateTotal() {
        val currentData = _getUserCartResponse.value
        val currentSelected = _selectedProducts.value
        if (currentData is Result.Success) {
            _totalPrice.value = currentData.data.data.product
                .filter { currentSelected.contains(it.product.productID) }
                .sumOf { it.quantity * it.product.price }
        }
    }
}
