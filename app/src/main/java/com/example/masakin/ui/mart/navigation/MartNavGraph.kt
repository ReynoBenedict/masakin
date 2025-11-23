package com.example.masakin.ui.mart.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.masakin.ui.mart.data.ProductCategory
import com.example.masakin.ui.mart.screens.cart.CartScreen
import com.example.masakin.ui.mart.screens.checkout.CheckoutScreen
import com.example.masakin.ui.mart.screens.home.MartHomeScreen
import com.example.masakin.ui.mart.screens.order.OrderScreen
import com.example.masakin.ui.mart.screens.detail.ProductDetailScreen
import com.example.masakin.ui.mart.screens.order.PaymentSuccessScreen
import com.example.masakin.ui.mart.viewmodel.MartViewModel

@Composable
fun MartNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: MartViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = MartRoute.Home.route
    ) {
        composable(MartRoute.Home.route) {
            MartHomeScreen(
                onProductClick = { productId ->
                    navController.navigate(MartRoute.Detail.createRoute(productId))
                },
                onCategoryClick = { category ->
                    // Categories scroll on home screen, handled internally
                },
                onCartClick = {
                    navController.navigate(MartRoute.Cart.route)
                },
                onOrderClick = { 
                    navController.navigate(MartRoute.Order.route)
                },
                onFavoriteClick = { /* Navigate to Favorites if exists */ },
                viewModel = viewModel
            )
        }

        composable(
            route = MartRoute.Detail.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            
            ProductDetailScreen(
                productId = productId,
                onBack = { 
                    navController.popBackStack(MartRoute.Home.route, inclusive = false)
                },
                onCartClick = { navController.navigate(MartRoute.Cart.route) },
                viewModel = viewModel
            )
        }

        composable(MartRoute.Cart.route) {
            CartScreen(
                onBack = { 
                    navController.popBackStack(MartRoute.Home.route, inclusive = false)
                },
                onCheckout = { navController.navigate(MartRoute.Checkout.route) },
                viewModel = viewModel
            )
        }

        composable(MartRoute.Checkout.route) {
            CheckoutScreen(
                onBack = { 
                    navController.popBackStack(MartRoute.Home.route, inclusive = false)
                },
                onPaymentSuccess = {
                    navController.navigate(MartRoute.PaymentSuccess.route) {
                        popUpTo(MartRoute.Home.route) { inclusive = false }
                    }
                },
                viewModel = viewModel
            )
        }

        composable(MartRoute.Order.route) {
            OrderScreen(
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }

        composable(MartRoute.PaymentSuccess.route) {
            val orderId = viewModel.uiState.value.lastCreatedOrderId ?: "ORD-000000"
            val totalPaid = viewModel.uiState.value.orders.lastOrNull()?.total ?: 0
            
            PaymentSuccessScreen(
                orderId = orderId,
                totalPaid = totalPaid,
                onViewOrder = {
                    navController.navigate(MartRoute.Order.route) {
                        popUpTo(MartRoute.Home.route) { inclusive = false }
                    }
                },
                onBackToHome = {
                    navController.popBackStack(MartRoute.Home.route, inclusive = false)
                }
            )
        }
    }
}