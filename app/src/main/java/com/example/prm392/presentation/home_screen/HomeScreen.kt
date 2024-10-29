package com.example.prm392.presentation.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.prm392.R
import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.presentation.detail_screen.components.LoadingScreen
import com.example.prm392.presentation.home_screen.components.ProductItem
import com.example.prm392.utils.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val clothingProducts by viewModel.clothingProducts.collectAsState()
    val hasMoreData by viewModel.hasMoreData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var isScrollToTopTrigger by remember { mutableStateOf(false) }

    val gridState = rememberLazyGridState()
    val pullToRefreshState = rememberPullToRefreshState()

    // Trigger loading more items when reaching the end of the list
    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex == gridState.layoutInfo.totalItemsCount - 1 && hasMoreData && !isLoading) {
                    viewModel.loadMoreClothing()
                }
            }
    }

    LaunchedEffect(isScrollToTopTrigger) {
        if (isScrollToTopTrigger) {
            gridState.animateScrollToItem(0)
            isScrollToTopTrigger = false
        }
    }

    // Handle refreshing
    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing &&
            (clothingProducts as Result.Success<List<Product>>).data.isNotEmpty()
        ) {
            viewModel.refreshClothing {
                pullToRefreshState.endRefresh()
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp)
                    .aspectRatio(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.logo),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .size(60.dp),
                    contentScale = ContentScale.Crop
                )
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = null
                )
            }

            when (clothingProducts) {
                is Result.Loading -> {
                    LoadingScreen()
                }

                is Result.Success -> {
                    val products = (clothingProducts as Result.Success<List<Product>>).data

                    LazyVerticalGrid(
                        state = gridState,
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(products) { product ->
                            ProductItem(product)
                        }
                    }
                }

                is Result.Error -> {
                    Text("Error loading products", modifier = Modifier.padding(16.dp))
                }

                else -> {
                    // Handle the idle state or initial state
                    Text("No products available", modifier = Modifier.padding(16.dp))
                }
            }
        }

        SmallFloatingActionButton(
            onClick = {
                isScrollToTopTrigger = true
            },
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Scroll to Top")
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}
