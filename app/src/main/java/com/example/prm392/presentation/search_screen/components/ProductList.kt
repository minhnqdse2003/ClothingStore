package com.example.prm392.presentation.search_screen.components

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prm392.domain.model.ProductSearchResultData

@Composable
fun ProductList(
    searchResultData: ProductSearchResultData,
    onItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            Text(
                text = "Search Result",
                fontFamily = FontFamily.Monospace,
                modifier = Modifier
                    .basicMarquee()
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                maxLines = 1
            )
        }

        items(searchResultData.products) { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = product.title,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(8f)
                            .basicMarquee(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1
                    )
                    IconButton(onClick = { onItemClick(product.title) }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier
                                .weight(2f)
                        )
                    }
                }
            }

        }
    }
}
