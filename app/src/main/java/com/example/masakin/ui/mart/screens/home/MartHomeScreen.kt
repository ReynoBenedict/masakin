package com.example.masakin.ui.mart.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    
    Scaffold(
        containerColor = Color(0xFFFAFAFA)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(24.dp))

            // Header
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

            // Menu Buttons
            MartMenuButtons(
                onCartClick = onCartClick,
                onOrderClick = onOrderClick,
                onFavoriteClick = onFavoriteClick
            )

            Spacer(Modifier.height(20.dp))

            // Category Row
            MartCategoryRow(
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = { category -> onCategoryClick(category) }
            )

            Spacer(Modifier.height(16.dp))

            // Recent Product Header
            Text(
                text = "Recent Product",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF111827),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(Modifier.height(12.dp))

            // Product Grid
            if (uiState.products.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No products found",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(uiState.products) { product ->
                        ProductCard(
                            product = product,
                            onClick = { id -> onProductClick(id) },
                            onAddToCart = { product ->
                                viewModel.addToCart(product, 1)
                            }
                        )
                    }
                }
            }
        }
    }
}
