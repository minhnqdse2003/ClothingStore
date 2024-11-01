package com.example.prm392.presentation.home_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import com.example.prm392.R
import com.example.prm392.data.dto.category.get_all.Category
import com.example.prm392.data.dto.products.get_all.Product
import com.example.prm392.presentation.detail_screen.components.LoadingScreen
import com.example.prm392.presentation.home_screen.components.CategoryCarousel
import com.example.prm392.presentation.home_screen.components.HorizontalCarousel
import com.example.prm392.presentation.home_screen.components.ProductItem
import com.example.prm392.presentation.product_screen.LoadingIndicator
import com.example.prm392.ui.theme.Vegur
import com.example.prm392.utils.MySpacer
import com.example.prm392.utils.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    val clothingProducts by viewModel.filteredClothingProducts.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val hasMoreData by viewModel.hasMoreData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    var isScrollToTopTrigger by remember { mutableStateOf(false) }
    var searchInput by remember { mutableStateOf("") }
    val selectedCategoryId by viewModel.selectedCategoryId
    val focusManager = LocalFocusManager.current

    val onSelectedCategory = { category: Category ->
        viewModel.onCategorySelected(category)
    }

    val banners = listOf(
        R.drawable.banner_1,
        R.drawable.banner_2,
        R.drawable.banner_3,
        R.drawable.banner_4,
        R.drawable.banner_5,
    )

    val gridState = rememberLazyGridState()
    val pullToRefreshState = rememberPullToRefreshState()

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
            searchInput = ""
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
                    .padding(horizontal = 16.dp, vertical = 8.dp)
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

                TextField(
                    value = searchInput,
                    onValueChange = { newValue ->
                        searchInput = newValue
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null,
                            tint = Color.Gray.copy(0.5f)
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Search products",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Normal,
                                fontFamily = Vegur
                            )
                        )
                    },
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .fillMaxWidth(.8f)
                        .border(
                            BorderStroke(1.dp, Color.Gray.copy(0.3f)),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            viewModel.onSearchCompleted(searchInput)
                        }
                    )
                )


                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = null
                )
            }

            MySpacer(16.dp)

            HorizontalCarousel(
                banners = banners,
                onBannerClick = {}
            )

            when (categories) {
                is Result.Success -> {
                    CategoryCarousel(
                        selectedCategoryId = selectedCategoryId,
                        onSelectedCategory = onSelectedCategory,
                        categories = (categories as Result.Success<List<Category>>).data,
                    )
                }

                else -> Unit
            }

            if(isLoading)
                LoadingScreen()

            when (clothingProducts) {
                is Result.Loading -> {
                    LoadingScreen()
                }

                is Result.Success -> {
                    val products = (clothingProducts as Result.Success<List<Product>>).data

                    LazyVerticalGrid(
                        state = gridState,
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(products) { product ->
                            ProductItem(product) {
                                navController.navigate("product_detail/${product.productID}")
                            }
                        }
                    }
                }

                is Result.Error -> {
                    Text("Error loading products", modifier = Modifier.padding(16.dp))
                }

                else -> {
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
                .align(Alignment.BottomEnd),
            containerColor = Color.White
        ) {
            Icon(
                Icons.Outlined.ArrowUpward,
                contentDescription = "Scroll to Top",
                modifier = Modifier
                    .size(24.dp)
            )
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}
