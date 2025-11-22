package com.example.masakin.ui.mart.utils

import java.text.NumberFormat
import java.util.Locale

/**
 * Utility functions for currency formatting
 */
object CurrencyFormatter {
    
    /**
     * Format an amount in Indonesian Rupiah
     * @param amount The amount to format
     * @return Formatted string like "Rp 25.000"
     */
    fun formatRupiah(amount: Int): String {
        val localeID = Locale("id", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.maximumFractionDigits = 0
        return numberFormat.format(amount).replace("Rp", "Rp ")
    }
}
