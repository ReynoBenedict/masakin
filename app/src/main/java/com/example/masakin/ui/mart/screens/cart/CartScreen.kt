package com.example.masakin.ui.mart.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.masakin.ui.mart.components.CartItemCard
import com.example.masakin.ui.mart.data.ProductRepository
import com.example.masakin.ui.mart.utils.CurrencyFormatter
import com.example.masakin.ui.mart.viewmodel.MartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBack: () -> Unit,
    onCheckout: () -> Unit,
    viewModel: MartViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedCartTotal = viewModel.getSelectedCartTotal()

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
            Surface(
                shadowElevation = 8.dp,
                color =Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = CurrencyFormatter.formatRupiah(selectedCartTotal),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                    }

                    Button(
                        onClick = onCheckout,
                        modifier = Modifier
                            .height(48.dp)
                            .widthIn(min = 120.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                        enabled = uiState.selectedCartItems.isNotEmpty()
                    ) {
                        Text(
                            text = "Check Out",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
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
                    onSelectionChange = { viewModel.toggleCartItemSelection(cartItem.product.id) },
                    onQuantityChange = { newQty ->
                        viewModel.updateCartQuantity(cartItem.product.id, newQty)
                    },
                    onDelete = { viewModel.removeFromCart(cartItem.product.id) },
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

            // Seen before section
            if (uiState.cartItems.isNotEmpty()) {
                item {
                    Spacer(Modifier.height(24.dp))
                    Text(
                        text = "Kamu pernah lihat ini",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                }

                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Dummy previously viewed items
                        items(ProductRepository.getProductsByCategory(com.example.masakin.ui.mart.data.ProductCategory.BUAH).take(4)) { product ->
                            DummyProductCard(product)
                        }
                    }
                }

                // Recommendations section
                item {
                    Spacer(Modifier.height(24.dp))
                    Text(
                        text = "Rekomendasi untukmu",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                }

                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Dummy recommendations
                        items(ProductRepository.getProductsByCategory(com.example.masakin.ui.mart.data.ProductCategory.SAYUR).take(6)) { product ->
                            DummyProductCard(product)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DummyProductCard(product: com.example.masakin.ui.mart.data.Product) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .clickable { /* Navigate to product detail */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = product.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = CurrencyFormatter.formatRupiah(product.price),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD32F2F)
            )
            Text(
                text = product.unit,
                fontSize = 10.sp,
                color = Color.Gray
            )
        }
    }
}
