package com.example.masakin.ui.mart.screens.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

data class DummyOrder(
    val id: String,
    val date: String,
    val status: String,
    val total: Int,
    val items: String
)

val dummyOrders = listOf(
    DummyOrder("ORD-001", "20 Nov 2023", "Selesai", 150000, "Daging Sapi (1kg), Bawang Merah (500g)"),
    DummyOrder("ORD-002", "18 Nov 2023", "Selesai", 45000, "Telur Ayam (1kg)"),
    DummyOrder("ORD-003", "15 Nov 2023", "Dibatalkan", 75000, "Minyak Goreng (2L)")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Pesanan", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFFAFAFA)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(dummyOrders) { order ->
                OrderCard(order)
            }
        }
    }
}

@Composable
fun OrderCard(order: DummyOrder) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.id,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                
                val statusColor = when(order.status) {
                    "Selesai" -> Color(0xFF10B981) // Green
                    "Dibatalkan" -> Color(0xFFEF4444) // Red
                    else -> Color(0xFFF59E0B) // Orange
                }
                
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(statusColor.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = order.status,
                        color = statusColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = order.items,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.date,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                
                Text(
                    text = formatRupiah(order.total),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF111827)
                )
            }
        }
    }
}

private fun formatRupiah(amount: Int): String {
    val localeID = Locale("id", "ID")
    val numberFormat = NumberFormat.getCurrencyInstance(localeID)
    numberFormat.maximumFractionDigits = 0
    return numberFormat.format(amount).replace("Rp", "Rp ")
}
