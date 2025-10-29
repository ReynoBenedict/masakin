package com.example.masakin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.masakin.ui.community.CommunityRoute
//import com.example.masakin.ui.mart.screens.MartHomeScreen
import com.example.masakin.ui.mart.screens.MartDagingScreen
import com.example.masakin.ui.screens.*
import com.example.masakin.ui.recipe.RecipeViewModel
import com.example.masakin.ui.screens.RecipeRoute

object Routes {
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val CHATBOT = "chatbot"
    const val RECIPE = "recipe"
    const val COMMUNITY = "community"
    const val MART =  "mart"

}

@Composable
fun MasakinNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.ONBOARDING
    ) {
        composable(Routes.ONBOARDING) {
            OnboardingRoute(
                onFinish = {
                    navController.navigate(Routes.LOGIN, navOptions {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    })
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginClick = { _, _ ->
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onForgotPasswordClick = { /* TODO */ },
                onRegisterClick = { navController.navigate(Routes.REGISTER) },
                onFacebookClick = { /* TODO */ },
                onGoogleClick = { /* TODO */ },
                onAppleClick = { /* TODO */ }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterClick = { _, _, _ -> navController.popBackStack() },
                onBackToLoginClick = { navController.popBackStack() }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                onOpenChatbot = { navController.navigate(Routes.CHATBOT) },
                onOpenRecipe = { navController.navigate(Routes.RECIPE) },
                onOpenCommunity = { navController.navigate(Routes.COMMUNITY) },
                onOpenMart = {navController.navigate(Routes.MART)}

            )
        }

        composable(Routes.MART) {
            MartDagingScreen()
        }

        composable(Routes.CHATBOT) {
            ChatbotScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.RECIPE) {
            val vm = RecipeViewModel()
            RecipeRoute(
                viewModel = vm,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.COMMUNITY) {
            CommunityRoute(
                onBack = { navController.popBackStack() },
                onCreatePost = { /* navigate to composer */ }
            )
        }
    }
}
