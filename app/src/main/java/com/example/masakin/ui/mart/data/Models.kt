package com.example.masakin.ui.mart.data

import com.example.masakin.ui.mart.viewmodel.PaymentMethod
import com.example.masakin.ui.mart.viewmodel.ShippingMethod

// Backend Preparation Data Classes

data class OrderRequest(
    val userId: String,
    val items: List<CartItemPayload>,
    val totalAmount: Int,
    val shippingMethod: String,
    val paymentMethod: String,
    val deliveryAddress: String
)

data class CartItemPayload(
    val productId: Int,
    val quantity: Int,
    val price: Int
)

data class PaymentOption(
    val id: String,
    val name: String,
    val type: String
)

data class ShippingOption(
    val id: String,
    val name: String,
    val price: Int,
    val estimatedDays: String
)

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?
)
