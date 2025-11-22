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
import com.example.masakin.ui.mart.screens.category.MartCategoryScreen
import com.example.masakin.ui.mart.screens.category.MartDagingScreen
import com.example.masakin.ui.mart.screens.home.MartHomeScreen
import com.example.masakin.ui.mart.screens.order.OrderScreen
import com.example.masakin.ui.mart.screens.detail.ProductDetailScreen
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
                    if (category == ProductCategory.DAGING) {
                         // Example specific screen if needed, or just generic category
                         navController.navigate(MartRoute.Category.createRoute(category))
                    } else {
                         navController.navigate(MartRoute.Category.createRoute(category))
                    }
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
            route = MartRoute.Category.route,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName")
            val category = ProductCategory.valueOf(categoryName ?: ProductCategory.DAGING.name)
            
            // If we want to use MartDagingScreen for DAGING specifically
            if (category == ProductCategory.DAGING) {
                 MartDagingScreen(
                    onBack = { navController.popBackStack() },
                    onProductClick = { productId ->
                        navController.navigate(MartRoute.Detail.createRoute(productId))
                    },
                    viewModel = viewModel
                )
            } else {
                MartCategoryScreen(
                    category = category,
                    onBack = { navController.popBackStack() },
                    onProductClick = { productId ->
                        navController.navigate(MartRoute.Detail.createRoute(productId))
                    },
                    viewModel = viewModel
                )
            }
        }

        composable(
            route = MartRoute.Detail.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            
            ProductDetailScreen(
                productId = productId,
                onBack = { navController.popBackStack() },
                onCartClick = { navController.navigate(MartRoute.Cart.route) },
                viewModel = viewModel
            )
        }

        composable(MartRoute.Cart.route) {
            CartScreen(
                onBack = { navController.popBackStack() },
                onCheckout = { navController.navigate(MartRoute.Checkout.route) },
                viewModel = viewModel
            )
        }

        composable(MartRoute.Checkout.route) {
            CheckoutScreen(
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }

        composable(MartRoute.Order.route) {
            OrderScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
