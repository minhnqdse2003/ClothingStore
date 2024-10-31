package com.example.prm392.domain.model.Cart.request

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItemRequestDto (
    @Json(name = "productID")
    val productID : Int,
    @Json(name = "quantity")
    val quantity: Int,
) : Parcelable