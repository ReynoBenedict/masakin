package com.example.masakin.ui.mart.screens.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.masakin.ui.mart.viewmodel.ShippingMethod

@Composable
fun ShippingBottomSheet(
    selectedMethod: ShippingMethod?,
    onSelectMethod: (ShippingMethod) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Warning banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE2E2)),
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
                    tint = Color(0xFFD32F2F)
                )
                Spacer(Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Tersedia bebas ongkir",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )
                    Text(
                        text = "Estimasi tiba 10 - 13 Agustus",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
                TextButton(onClick = onDismiss) {
                    Text("Pilih", color = Color(0xFFD32F2F), fontSize = 12.sp)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Shipping options
        ShippingMethod.entries.forEach { method ->
            ShippingMethodOption(
                method = method,
                isSelected = selectedMethod == method,
                onClick = { onSelectMethod(method) }
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun ShippingMethodOption(
    method: ShippingMethod,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFFEE2E2) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFD32F2F))
            )
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = method.displayName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${method.displayName.split(" ")[0]} (Rp ${method.price})",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Estimasi tiba ${method.estimatedDays}",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.mart_ic_cat_daging),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Color.Gray
            )
        }
    }
}
