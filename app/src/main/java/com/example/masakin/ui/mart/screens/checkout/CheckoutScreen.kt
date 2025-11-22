package com.example.masakin.ui.mart.screens.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.masakin.R
import com.example.masakin.ui.mart.components.checkout.CheckoutItemCard
import com.example.masakin.ui.mart.components.checkout.CheckoutSummaryRow
import com.example.masakin.ui.mart.screens.checkout.PaymentBottomSheet
import com.example.masakin.ui.mart.screens.checkout.ShippingBottomSheet
import com.example.masakin.ui.mart.utils.CurrencyFormatter
import com.example.masakin.ui.mart.viewmodel.MartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    onBack: () -> Unit,
    viewModel: MartViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showShippingModal by remember { mutableStateOf(false) }
    var showPaymentModal by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    
    val selectedTotal = viewModel.getSelectedCartTotal()
    val shippingCost = viewModel.getShippingCost()
    val protectionCost = viewModel.getProtectionCost()
    val totalCost = viewModel.getCheckoutTotal()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Check Out",
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
                color = Color.White
            ) {
                Button(
                    onClick = { 
                        if (uiState.selectedPayment == null) {
                            showPaymentModal = true
                        } else {
                            /* Place order - would trigger order placement */
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    if (uiState.selectedPayment != null) {
                        Text(
                            text = "Buat Pesanan",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    } else {
                        Text(
                            text = "Pilih Pembayaran",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        },
        containerColor = Color.White
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Delivery address
            item {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Alamat pengiriman",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(12.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.mart_ic_cat_daging),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFD32F2F)
                        )
                        Spacer(Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Rumah Richard",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Jl. Tapak Dewandaru no 23, Kasin, Kec Klojen, Kota Malang, Jawa...",
                                fontSize = 12.sp,
                                color = Color.Gray,
                                maxLines = 1
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.mart_ic_cat_daging),
                            contentDescription = "Change",
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFD32F2F)
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            // Checkout items
            items(uiState.cartItems.filter { uiState.selectedCartItems.contains(it.product.id) }) { cartItem ->
                CheckoutItemCard(
                    cartItem = cartItem,
                    selectedShipping = uiState.selectedShipping,
                    onSelectShipping = { showShippingModal = true },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            // Voucher section
            item {
                Spacer(Modifier.height(12.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Select voucher */ },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE2E2)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.mart_ic_cat_daging),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFD32F2F)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Yuk, tukarkan voucher kamu",
                            fontSize = 14.sp,
                            color = Color(0xFF111827),
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.mart_ic_cat_daging),
                            contentDescription = "Select",
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFFD32F2F)
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            // Order summary
            item {
                Text(
                    text = "Ringkasan belanjamu",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(12.dp))

                CheckoutSummaryRow(
                    label = "Total Harga (${uiState.cartItems.filter { uiState.selectedCartItems.contains(it.product.id) }.size} Barang)",
                    value = CurrencyFormatter.formatRupiah(selectedTotal)
                )
                Spacer(Modifier.height(8.dp))

                CheckoutSummaryRow(
                    label = "Total Biaya proteksi",
                    value = CurrencyFormatter.formatRupiah(protectionCost)
                )
                Spacer(Modifier.height(8.dp))

                CheckoutSummaryRow(
                    label = "Total Belanja",
                    value = "",
                    isTotal = true
                )

                if (uiState.selectedShipping != null) {
                    Spacer(Modifier.height(8.dp))
                    CheckoutSummaryRow(
                        label = "Total Ongkos Kirim",
                        value = "Rp ${shippingCost}",
                        highlight = true
                    )
                    Spacer(Modifier.height(8.dp))
                    CheckoutSummaryRow(
                        label = "Total Asuransi Pengiriman",
                        value = CurrencyFormatter.formatRupiah(protectionCost)
                    )
                    Spacer(Modifier.height(8.dp))
                    CheckoutSummaryRow(
                        label = "Total Biaya Proteksi",
                        value = CurrencyFormatter.formatRupiah(protectionCost)
                    )
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(color = Color(0xFFE5E7EB))
                    Spacer(Modifier.height(12.dp))
                    CheckoutSummaryRow(
                        label = "Total Belanja",
                        value = CurrencyFormatter.formatRupiah(totalCost),
                        isTotal = true
                    )
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }

    // Shipping Modal
    if (showShippingModal) {
        ModalBottomSheet(
            onDismissRequest = { showShippingModal = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            ShippingBottomSheet(
                selectedMethod = uiState.selectedShipping,
                onSelectMethod = { method ->
                    viewModel.selectShippingMethod(method)
                    showShippingModal = false
                },
                onDismiss = { showShippingModal = false }
            )
        }
    }

    // Payment Modal
    if (showPaymentModal) {
        ModalBottomSheet(
            onDismissRequest = { showPaymentModal = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            PaymentBottomSheet(
                selectedMethod = uiState.selectedPayment,
                onSelectMethod = { method ->
                    viewModel.selectPaymentMethod(method)
                    showPaymentModal = false
                },
                onDismiss = { showPaymentModal = false }
            )
        }
    }
}
