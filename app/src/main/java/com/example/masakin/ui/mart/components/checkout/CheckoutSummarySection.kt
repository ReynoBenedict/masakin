package com.example.masakin.ui.mart.components.checkout

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masakin.ui.mart.utils.CurrencyFormatter
import com.example.masakin.ui.mart.viewmodel.PaymentMethod

@Composable
fun CheckoutSummarySection(
    itemCount: Int,
    subtotal: Int,
    shippingCost: Int,
    insuranceCost: Int,
    protectionCost: Int,
    totalCost: Int,
    selectedPayment: PaymentMethod?
) {
    Text(
        text = "Ringkasan belanjamu",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(12.dp))

    CheckoutSummaryRow(
        label = "Total Harga ($itemCount Barang)",
        value = CurrencyFormatter.formatRupiah(subtotal)
    )
    Spacer(Modifier.height(8.dp))

    CheckoutSummaryRow(
        label = "Total Ongkos Kirim",
        value = if (shippingCost == 0) "Rp 0" else CurrencyFormatter.formatRupiah(shippingCost),
        highlight = shippingCost == 0
    )
    Spacer(Modifier.height(8.dp))

    CheckoutSummaryRow(
        label = "Total Asuransi Pengiriman",
        value = CurrencyFormatter.formatRupiah(insuranceCost)
    )
    Spacer(Modifier.height(8.dp))

    CheckoutSummaryRow(
        label = "Total Biaya Proteksi",
        value = CurrencyFormatter.formatRupiah(protectionCost)
    )
    
    if (selectedPayment != null) {
        Spacer(Modifier.height(8.dp))
        CheckoutSummaryRow(
            label = "Metode Pembayaran",
            value = selectedPayment.displayName
        )
    }

    Spacer(Modifier.height(12.dp))
    HorizontalDivider(color = Color(0xFFE5E7EB))
    Spacer(Modifier.height(12.dp))
    
    CheckoutSummaryRow(
        label = "Total Belanja",
        value = CurrencyFormatter.formatRupiah(totalCost),
        isTotal = true
    )

    Spacer(Modifier.height(24.dp))
}
