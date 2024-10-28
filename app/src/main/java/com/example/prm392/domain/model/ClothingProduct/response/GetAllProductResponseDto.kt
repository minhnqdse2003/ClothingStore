package com.example.prm392.domain.model.ClothingProduct.response

import com.example.prm392.data.dto.products.get_all.Product

data class GetAllProductResponseDto(
    val products: List<Product>
)
