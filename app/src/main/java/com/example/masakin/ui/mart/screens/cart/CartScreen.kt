package com.example.masakin.ui.mart.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.masakin.ui.mart.components.CartItemCard
import com.example.masakin.ui.mart.components.CartRecommendations
import com.example.masakin.ui.mart.components.CartSummarySection
import com.example.masakin.ui.mart.viewmodel.MartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBack: () -> Unit,
    onCheckout: () -> Unit,
    viewModel: MartViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    val selectedCartTotal = uiState.cartItems
        .filter { uiState.selectedCartItems.contains(it.product.id) }
        .sumOf { it.product.price * it.quantity }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cart",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            if (uiState.cartItems.isNotEmpty()) {
                CartSummarySection(
                    totalPrice = selectedCartTotal,
                    itemCount = uiState.selectedCartItems.size,
                    onCheckout = onCheckout
                )
            }
        },
        containerColor = Color(0xFFFAFAFA)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Cart items
            items(uiState.cartItems) { cartItem ->
                CartItemCard(
                    cartItem = cartItem,
                    isSelected = uiState.selectedCartItems.contains(cartItem.product.id),
                    onSelectionChange = { viewModel.toggleCartItemSelection(cartItem.product) },
                    onQuantityChange = { newQty ->
                        viewModel.updateCartQuantity(cartItem.product, newQty)
                    },
                    onDelete = { viewModel.removeFromCart(cartItem.product) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            // Empty state
            if (uiState.cartItems.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Keranjang Anda kosong",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Recommendations
            if (uiState.cartItems.isNotEmpty()) {
                CartRecommendations()
            }
        }
    }
}
