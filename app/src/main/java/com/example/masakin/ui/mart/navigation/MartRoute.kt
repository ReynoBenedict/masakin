package com.example.masakin.ui.mart.navigation

import com.example.masakin.ui.mart.data.ProductCategory

sealed class MartRoute(val route: String) {
    object Home : MartRoute("mart_home")
    object Category : MartRoute("mart_category/{categoryName}") {
        fun createRoute(category: ProductCategory) = "mart_category/${category.name}"
    }
    object Detail : MartRoute("mart_detail/{productId}") {
        fun createRoute(productId: Int) = "mart_detail/$productId"
    }
    object Cart : MartRoute("mart_cart")
    object Checkout : MartRoute("mart_checkout")
    object Order : MartRoute("mart_order")
    object PaymentSuccess : MartRoute("mart_payment_success")
}
