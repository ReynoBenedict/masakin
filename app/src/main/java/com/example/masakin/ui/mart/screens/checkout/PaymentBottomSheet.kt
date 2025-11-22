package com.example.masakin.ui.mart.screens.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masakin.R
import com.example.masakin.ui.mart.viewmodel.PaymentMethod

@Composable
fun PaymentBottomSheet(
    selectedMethod: PaymentMethod?,
    onSelectMethod: (PaymentMethod) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Pilih Metode Pembayaran",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        }
        
        Spacer(Modifier.height(16.dp))

        // Payment options
        PaymentMethod.entries.forEach { method ->
            PaymentMethodOption(
                method = method,
                isSelected = selectedMethod == method,
                onClick = { onSelectMethod(method) }
            )
            Spacer(Modifier.height(12.dp))
        }

        // Security note
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD1FAE5)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.mart_ic_cat_daging),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFF10B981)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Kami akan melindungi data anda\ndan tidak akan kami jual data anda",
                    fontSize = 11.sp,
                    color = Color(0xFF065F46)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Continue button
        Button(
            onClick = onDismiss,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
        ) {
            Text(
                text = "Lanjut",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun PaymentMethodOption(
    method: PaymentMethod,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon placeholder
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        when (method) {
                            PaymentMethod.CREDIT_CARD -> Color(0xFF1A1F71)
                            PaymentMethod.BANK_BCA -> Color(0xFF0066AE)
                            PaymentMethod.CASH -> Color(0xFFFEF3C7)
                        },
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (method) {
                        PaymentMethod.CREDIT_CARD -> "VISA"
                        PaymentMethod.BANK_BCA -> "BCA"
                        else -> "💵"
                    },
                    color = if (method == PaymentMethod.CASH) Color(0xFF92400E) else Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = method.displayName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                if (method.details.isNotEmpty()) {
                    Text(
                        text = method.details,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFD32F2F))
            )
        }
    }
}
