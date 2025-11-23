package com.example.masakin.ui.mart.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.example.masakin.ui.mart.components.MartCategoryRow
import com.example.masakin.ui.mart.components.MartHeader
import com.example.masakin.ui.mart.components.MartMenuButtons
import com.example.masakin.ui.mart.components.ProductCard
import com.example.masakin.ui.mart.data.ProductCategory
import com.example.masakin.ui.mart.viewmodel.MartViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@Composable
fun MartHomeScreen(
    onProductClick: (Int) -> Unit,
    onCategoryClick: (ProductCategory) -> Unit,
    onCartClick: () -> Unit,
    onOrderClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    viewModel: MartViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    
    // Group products by category for the scrollable list
    val groupedProducts = remember {
        ProductCategory.values().associateWith { category ->
            com.example.masakin.ui.mart.data.ProductRepository.getProductsByCategory(category)
        }
    }

    // Calculate cart total items for summary
    val cartItemCount = uiState.cartItems.sumOf { it.quantity }
    var showCartSummary by remember { mutableStateOf(false) }

    // Show summary when cart updates
    LaunchedEffect(cartItemCount) {
        if (cartItemCount > 0) {
            showCartSummary = true
            delay(3000)
            showCartSummary = false
        }
    }

    Scaffold(
        snackbarHost = { 
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(16.dp).shadow(4.dp, androidx.compose.foundation.shape.RoundedCornerShape(8.dp)),
                    containerColor = Color.White,
                    contentColor = Color(0xFF111827),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                    action = {
                        data.visuals.actionLabel?.let { actionLabel ->
                            TextButton(onClick = { data.performAction() }) {
                                Text(actionLabel, color = Color(0xFF4CAF50))
                            }
                        }
                    }
                ) {
                    Text(data.visuals.message)
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
                            onSearchChange = { query -> viewModel.onSearchQueryChanged(query) },
                            onLocationClick = { 
                                val client = LocationServices.getFusedLocationProviderClient(context)
                                viewModel.requestLocationUpdate(context, client)
                            },
                            onNotificationClick = { /* Handle notification */ },
                            onBackClick = { /* Handle back if needed */ }
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
                                    scope.launch {
                                        listState.animateScrollToItem(category.ordinal + 1)
                                    }
                                }
                            )
                            Spacer(Modifier.height(16.dp))
                        }
                    }
                }

                if (uiState.searchQuery.isNotEmpty()) {
                    // Search Results
                    items(uiState.products.chunked(2)) { rowProducts ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            rowProducts.forEach { product ->
                                Box(modifier = Modifier.weight(1f)) {
                                    ProductCard(
                                        product = product,
                                        onClick = { id -> onProductClick(id) },
                                        onAddToCart = { product ->
                                            viewModel.addToCart(product, 1)
                                            scope.launch {
                                                snackbarHostState.currentSnackbarData?.dismiss()
                                                snackbarHostState.showSnackbar(
                                                    message = "Produk berhasil dimasukkan ke Cart!",
                                                    duration = SnackbarDuration.Short
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                            if (rowProducts.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                    }
                } else {
                    // Category Sections
                    items(ProductCategory.values()) { category ->
                        val products = groupedProducts[category] ?: emptyList()
                        if (products.isNotEmpty()) {
                            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                                Text(
                                    text = category.name.lowercase().replaceFirstChar { it.uppercase() },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color(0xFF111827),
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                
                                val chunkedProducts = products.chunked(2)
                                chunkedProducts.forEach { rowProducts ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        rowProducts.forEach { product ->
                                            Box(modifier = Modifier.weight(1f)) {
                                                ProductCard(
                                                    product = product,
                                                    onClick = { id -> onProductClick(id) },
                                                    onAddToCart = { product ->
                                                        viewModel.addToCart(product, 1)
                                                        scope.launch {
                                                            snackbarHostState.currentSnackbarData?.dismiss()
                                                            snackbarHostState.showSnackbar(
                                                                message = "Produk berhasil dimasukkan ke Cart!",
                                                                duration = SnackbarDuration.Short
                                                            )
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                        if (rowProducts.size == 1) {
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(14.dp))
                                }
                            }
                        }
                    }
                }
            }

            // Dynamic Cart Summary
            AnimatedVisibility(
                visible = showCartSummary && cartItemCount > 0,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Surface(
                    color = Color(0xFF4CAF50), // Green color
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total item di cart: $cartItemCount",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Lihat Keranjang >",
                            color = Color.White,
                            modifier = Modifier.clickable { onCartClick() }
                        )
                    }
                }
            }
        }
    }
}
