package com.example.prm392.presentation.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prm392.presentation.home_screen.components.ProductItem
import com.example.prm392.utils.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val clothingProducts = viewModel.clothingProducts.collectAsState().value
    val hasMoreData = viewModel.hasMoreData.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val isRefreshing = viewModel.isRefreshing.collectAsState().value

    val gridState = rememberLazyGridState()

    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo }
            .collect { layoutInfo ->
                if (layoutInfo.visibleItemsInfo.isNotEmpty() &&
                    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1 &&
                    hasMoreData
                ) {
                    viewModel.loadMoreClothing()
                }
            }
    }

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            viewModel.refreshClothing()
        }
    }

    LaunchedEffect(isRefreshing) {
        if(isRefreshing){
            pullToRefreshState.startRefresh()
        } else {
            gridState.scrollToItem(0)
            pullToRefreshState.endRefresh()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .fillMaxSize()
        )
        Text(
            text = "Clothing Products",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        // Loading indicator at the top if needed
        if (viewModel.isLoading.collectAsState().value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }

        when (clothingProducts) {
            is Result.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is Result.Success -> {
                val products = clothingProducts.data

                LazyVerticalGrid(
                    state = gridState,
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(products) { product ->
                        ProductItem(product)
                    }

                    if (isLoading) {
                        item {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
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
}