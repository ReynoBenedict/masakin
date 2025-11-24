package com.example.masakin.ui.mart.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.masakin.ui.mart.components.FloatingCartBar
import com.example.masakin.ui.mart.components.MartCategoryRow
import com.example.masakin.ui.mart.components.MartHeader
import com.example.masakin.ui.mart.components.MartMenuButtons
import com.example.masakin.ui.mart.components.MartProductSection
import com.example.masakin.ui.mart.data.ProductCategory
import com.example.masakin.ui.mart.data.ProductRepository
import com.example.masakin.ui.mart.viewmodel.MartViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

@Composable
fun MartHomeScreen(
    onProductClick: (Int) -> Unit,
    onCategoryClick: (ProductCategory) -> Unit,
    onCartClick: () -> Unit,
    onOrderClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onBackToHome: () -> Unit = {},
    viewModel: MartViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    
    // Fetch initial location when screen loads
    LaunchedEffect(Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        viewModel.requestLocationUpdate(context, fusedLocationClient)
    }

    
    // Track category section positions
    val categoryPositions = remember { mutableStateMapOf<ProductCategory, Int>() }
    var isScrollingProgrammatically by remember { mutableStateOf(false) }
    
    // Group products by category for the scrollable list
    val groupedProducts = remember {
        ProductCategory.entries.associateWith { category ->
            ProductRepository.getProductsByCategory(category)
        }
    }
    
    // Snackbar visibility state
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    
    // Auto-update selected category based on scroll position
    LaunchedEffect(listState.firstVisibleItemIndex, listState.isScrollInProgress) {
        if (!isScrollingProgrammatically && listState.isScrollInProgress) {
            val currentIndex = listState.firstVisibleItemIndex
            
            // Find which category section we're in
            val currentCategory = categoryPositions.entries
                .sortedBy { it.value }
                .findLast { it.value <= currentIndex }
                ?.key
            
            if (currentCategory != null && currentCategory != uiState.selectedCategory) {
                viewModel.selectCategory(currentCategory)
            }
        }
    }

    Scaffold(
        snackbarHost = { 
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                AnimatedVisibility(
                    visible = showSnackbar,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it })
                ) {
                    Surface(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .shadow(4.dp, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF3F4F6)
                    ) {
                        Text(
                            text = snackbarMessage,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF111827)
                        )
                    }
                }
            }
        },
        containerColor = Color(0xFFFAFAFA)
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                // Header Section
                item {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Spacer(Modifier.height(24.dp))
                        MartHeader(
                            deliveryAddress = uiState.deliveryAddress,
                            searchText = uiState.searchQuery,
                            isLoadingLocation = uiState.isLoadingLocation,
                            onSearchChange = { query -> viewModel.updateSearchQuery(query) },
                            onLocationClick = { 
                                val client = LocationServices.getFusedLocationProviderClient(context)
                                viewModel.requestLocationUpdate(context, client)
                            },
                            onNotificationClick = { /* Handle notification */ },
                            onBackClick = onBackToHome
                        )
                        Spacer(Modifier.height(16.dp))
                        MartMenuButtons(
                            onCartClick = onCartClick,
                            onOrderClick = onOrderClick,
                            onFavoriteClick = onFavoriteClick
                        )
                        Spacer(Modifier.height(20.dp))
                        
                        // Only show category row if NOT searching
                        if (uiState.searchQuery.isEmpty()) {
                            MartCategoryRow(
                                selectedCategory = uiState.selectedCategory,
                                onCategorySelected = { category ->
                                    viewModel.selectCategory(category)
                                    scope.launch {
                                        isScrollingProgrammatically = true
                                        val targetIndex = categoryPositions[category] ?: 0
                                        listState.animateScrollToItem(targetIndex)
                                        isScrollingProgrammatically = false
                                    }
                                }
                            )
                            Spacer(Modifier.height(16.dp))
                        }
                    }
                }

                MartProductSection(
                    searchQuery = uiState.searchQuery,
                    products = uiState.products,
                    groupedProducts = groupedProducts,
                    onProductClick = onProductClick,
                    onCategoryPositionCalculated = { category, index ->
                        categoryPositions[category] = index
                    },
                    onAddToCart = { product ->
                        viewModel.addToCart(product, 1)
                        snackbarMessage = "Produk berhasil dimasukkan ke Cart!"
                        showSnackbar = true
                        scope.launch {
                            kotlinx.coroutines.delay(1500)
                            showSnackbar = false
                        }
                    }
                )
            }

            FloatingCartBar(
                itemCount = uiState.cartItems.sumOf { it.quantity },
                totalPrice = uiState.cartItems.sumOf { it.product.price * it.quantity },
                onCartClick = onCartClick
            )
        }
    }
}
