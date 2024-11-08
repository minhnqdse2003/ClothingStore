package com.example.prm392.domain.service.Cart

data class CartService(
    val getUserCartService: GetUserCartService,
    val addUserCartService: AddUserCartService,
    val removeUserCartService: RemoveUserCartService,
    val updateUserCartItemQuantityService: UpdateUserCartItemQuantityService
)
