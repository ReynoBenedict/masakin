package com.example.masakin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.masakin.ui.screens.LoginScreen
import com.example.masakin.ui.screens.RegisterScreen
import com.example.masakin.ui.navigation.OnboardingRoute

object Routes {
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val REGISTER = "register"
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
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginClick = { _, _ -> /* TODO: navigate to Home */ },
                onForgotPasswordClick = { /* TODO */ },
                onRegisterClick = { navController.navigate(Routes.REGISTER) },
                onFacebookClick = { /* TODO */ },
                onGoogleClick = { /* TODO */ },
                onAppleClick = { /* TODO */ }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterClick = { _, _, _ ->
                    navController.popBackStack() // selesai daftar -> balik ke Login
                },
                onBackToLoginClick = { navController.popBackStack() }
            )
        }
    }
}
