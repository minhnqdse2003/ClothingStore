package com.example.prm392.domain.model.store_location

import com.example.prm392.data.dto.store_location.get_all.StoreLocation
import com.squareup.moshi.Json

data class GetAllStoreLocationResponseDto (
    val data: List<StoreLocation>
)