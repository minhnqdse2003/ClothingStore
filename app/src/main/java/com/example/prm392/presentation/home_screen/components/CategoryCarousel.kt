package com.example.prm392.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prm392.data.dto.category.get_all.Category

@Composable
fun CategoryCarousel(
    categories: List<Category>,
    selectedCategoryId: Int?,
    onSelectedCategory: (Category) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { category ->
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .width(80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CategoryBadge(
                    category = category,
                    isSelected = selectedCategoryId == category.categoryID,
                    onClick = { onSelectedCategory(category) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = category.categoryName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )
            }
        }
    }
}


@Composable
fun CategoryBadge(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    BadgedBox(
        modifier = Modifier
            .clickable { onClick() },
        badge = {
            if (isSelected) {
                Badge(
                    containerColor = Color.Transparent,
                    contentColor = contentColorFor(
                        backgroundColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        modifier = Modifier
                            .size(20.dp),
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                    shape = CircleShape
                )
                .wrapContentSize()
                .height(48.dp)
                .width(48.dp)
        ) {
            Text(
                text = category.categoryName.first().toString().uppercase(),
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}


