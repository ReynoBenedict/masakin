package com.example.masakin.ui.mart.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.masakin.R
import com.example.masakin.ui.mart.components.*
import com.example.masakin.ui.mart.utils.LocationPermissionHandler
import com.example.masakin.ui.mart.viewmodel.MartViewModel
import com.google.android.gms.location.LocationServices

@Composable
fun MartDagingScreen(
    onBack: () -> Unit = {},
    onProductClick: (String) -> Unit = {},
    viewModel: MartViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var showLocationDialog by remember { mutableStateOf(false) }

    // TESTING: Uncomment untuk force set Malang
    // LaunchedEffect(Unit) {
    //     viewModel.setManualLocation("Malang, Jawa Timur")
    // }

    // Auto-request location on first launch (optional)
    LaunchedEffect(Unit) {
        // Uncomment to auto-request location on screen launch
        // viewModel.requestLocationUpdate(context, fusedLocationClient)
    }

    // Location Permission Handler
    if (showLocationDialog) {
        LocationPermissionHandler(
            onPermissionGranted = { client ->
                viewModel.requestLocationUpdate(context, client)
                showLocationDialog = false
            },
            onPermissionDenied = {
                showLocationDialog = false
                // Show snackbar or dialog
            }
        )
    }

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
                onSearchChange = { viewModel.onSearchQueryChanged(it) },
                onLocationClick = {
                    showLocationDialog = true
                },
                onNotificationClick = { /* Handle notification */ },
                onBackClick = onBack // Tambahkan back button
            )

            Spacer(Modifier.height(16.dp))

            // Menu Buttons (Cart, Order, Favorite)
            MartMenuButtons(
                onCartClick = { /* Navigate to cart */ },
                onOrderClick = { /* Navigate to orders */ },
                onFavoriteClick = { /* Navigate to favorites */ }
            )

            Spacer(Modifier.height(20.dp))

            // Category Row
            MartCategoryRow(
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = { viewModel.onCategorySelected(it) }
            )

            Spacer(Modifier.height(20.dp))

            // Recent Product Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Product",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF111827)
                )

                // Filter Button dengan ukuran lebih besar
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF3F4F6))
                        .clickable { /* Handle filter */ },
                    contentAlignment = Alignment.Center
                ) {
                    CustomFilterIcon(
                        size = 28.dp,
                        color = Color(0xFF374151)
                    )
                }
            }

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
                            onClick = { onProductClick(product.id) }
                        )
                    }
                }
            }
        }
    }

    // Show error snackbar if location error
    uiState.locationError?.let { error ->
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                TextButton(onClick = { /* Dismiss */ }) {
                    Text("OK")
                }
            }
        ) {
            Text(error)
        }
    }
}