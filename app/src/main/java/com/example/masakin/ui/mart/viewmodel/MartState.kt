package com.example.masakin.ui.mart.viewmodel

import com.example.masakin.ui.mart.data.Product
import com.example.masakin.ui.mart.data.ProductCategory
import com.example.masakin.ui.mart.data.CartItem
import java.util.UUID

/**
 * UI state for the Mart module. Includes all fields referenced by the ViewModel and UI components.
 */
data class MartUiState(
    val products: List<Product> = emptyList(),
    val cartItems: List<CartItem> = emptyList(),
    val selectedCategory: ProductCategory? = null,
    val searchQuery: String = "",
    val deliveryAddress: String = "Loading location...",
    // Checkout related selections
    val selectedShipping: ShippingMethod? = null,
    val selectedPayment: PaymentMethod? = null,
    // Checkout calculation fields (used during checkout flow)
    val checkoutSubtotal: Int = 0,
    val checkoutShippingCost: Int = 0,
    val checkoutInsurance: Int = 0,
    val checkoutProtection: Int = 3000,
    // Order history
    val orders: List<Order> = emptyList(),
    // Selected items in cart (by product id)
    val selectedCartItems: List<Int> = emptyList(),
    // Currently viewed product detail
    val selectedProduct: Product? = null,
    // Location handling
    val isLoadingLocation: Boolean = false,
    val locationError: String? = null,
    // Additional fields for order creation
    val lastCreatedOrderId: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)

/** Simple order representation used in UI and persisted via OrderRepository */
data class Order(
    val id: String = UUID.randomUUID().toString(),
    val date: String = "",
    val status: String = "",
    val items: List<CartItem>,
    val subtotal: Int,
    val shippingCost: Int,
    val insuranceCost: Int = 5000,
    val protectionCost: Int = 3000,
    val total: Int,
    val paymentMethod: PaymentMethod?,
    val shippingMethod: ShippingMethod?,
    val address: String,
    val timestamp: Long = System.currentTimeMillis()
)

/** Shipping options */
enum class ShippingMethod(val displayName: String, val price: Int, val estimatedDays: Int) {
    STANDARD("Standard", 10000, 3),
    EXPRESS("Express", 20000, 1)
}

/** Payment options */
enum class PaymentMethod(val displayName: String, val details: String = "") {
    CREDIT_CARD("Kartu Kredit/Debit", "Visa, Mastercard, JCB"),
    BANK_BCA("Transfer Bank BCA", "Gratis biaya admin"),
    CASH("Bayar di Tempat", "Bayar saat barang tiba")
}
