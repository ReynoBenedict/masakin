package com.example.masakin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.masakin.ui.screens.*

object Routes {
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val CHATBOT = "chatbot"
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
                // ⬇️ langsung navigasi ke HOME, abaikan kredensial
                onLoginClick = { _, _ ->
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true } // hapus dari back stack
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
                onOpenChatbot = { navController.navigate(Routes.CHATBOT) }
            )
        }

        composable(Routes.CHATBOT) {
            ChatbotScreen(onBack = { navController.popBackStack() })
        }
    }
}
