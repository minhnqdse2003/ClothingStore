package com.example.prm392.presentation.detail_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.prm392.domain.model.ProductSearchResultData
import com.example.prm392.presentation.components.ImageLoaderAsync
import com.example.prm392.utils.MySpacer

@Composable
fun ProductData(
    productData: ProductSearchResultData
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ){
            ImageLoaderAsync(
                Modifier,
                productData.products[0].images[0]
            )
        }
        MySpacer(12.dp)
        ProductDataItems(
            title = "Title",
            description = productData.products[0].title
        )
        ProductDataItems(
            title = "Category",
            description = productData.products[0].category
        )
    }
}