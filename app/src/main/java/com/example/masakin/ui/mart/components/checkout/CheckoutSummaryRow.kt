package com.example.masakin.ui.mart.components.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CheckoutSummaryRow(
    label: String,
    value: String,
    isTotal: Boolean = false,
    highlight: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = if (isTotal) 14.sp else 12.sp,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal,
            color = if (isTotal) Color(0xFF111827) else Color.Gray
        )
        Text(
            text = value,
            fontSize = if (isTotal) 14.sp else 12.sp,
            fontWeight = if (isTotal || highlight) FontWeight.Bold else FontWeight.Normal,
            color = if (highlight) Color(0xFFD32F2F) else if (isTotal) Color(0xFF111827) else Color.Gray
        )
    }
}
