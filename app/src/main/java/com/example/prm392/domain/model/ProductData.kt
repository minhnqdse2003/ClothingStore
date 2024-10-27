package com.example.prm392.domain.model

import com.example.prm392.data.dto.Product
import com.example.prm392.data.dto.ProductDataDto
import com.squareup.moshi.Json

data class ProductData (
    val products: List<Product>,
)

