package com.example.masakin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.masakin.ui.community.CommunityRoute
import com.example.masakin.ui.mart.screens.MartDagingScreen
import com.example.masakin.ui.recipe.RecipeViewModel
import com.example.masakin.ui.screens.*
import androidx.lifecycle.viewmodel.compose.viewModel

object Routes {
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val REGISTER = "register"

    const val HOME = "home"

    // bottom navbar
    const val CHAT = "chat"
    const val MYFOOD = "myfood"
    const val PROFILE = "profile"

    // fitur lain
    const val CHATBOT = "chatbot"
    const val RECIPE = "recipe"
    const val COMMUNITY = "community"
    const val MART = "mart"
    const val CONSULTATION = "consultation"
}

@Composable
fun MasakinNavGraph(
    navController: NavHostController,
    onGoogleClick: () -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = Routes.ONBOARDING
    ) {

        // ================== ONBOARDING ==================
        composable(Routes.ONBOARDING) {
            OnboardingRoute(
                onFinish = {
                    navController.navigate(Routes.LOGIN, navOptions {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    })
                }
            )
        }

        // ================== LOGIN ==================
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginClick = { _, _ ->
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onForgotPasswordClick = {},
                onRegisterClick = { navController.navigate(Routes.REGISTER) },
                onFacebookClick = {},
                onGoogleClick = onGoogleClick,
                onAppleClick = {}
            )
        }

        // ================== REGISTER ==================
        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterClick = { _, _, _ ->
                    navController.popBackStack()
                },
                onBackToLoginClick = { navController.popBackStack() },
                onFacebookClick = {},
                onGoogleClick = onGoogleClick,
                onAppleClick = {}
            )
        }

        // ================== HOME + NAVBAR ==================
        composable(Routes.HOME) {
            HomeScreen(
                onOpenChatbot = { navController.navigate(Routes.CHATBOT) },
                onOpenRecipe = { navController.navigate(Routes.RECIPE) },
                onOpenCommunity = { navController.navigate(Routes.COMMUNITY) },
                onOpenMart = { navController.navigate(Routes.MART) },
                onOpenConsultation = { navController.navigate(Routes.CONSULTATION) },

                onOpenChat = { navController.navigate(Routes.CHAT) },
                onOpenMyFood = { navController.navigate(Routes.MYFOOD) },
                onOpenProfile = { navController.navigate(Routes.PROFILE) }
            )
        }

        // ================== BOTTOM NAVBAR HALAMAN ==================
        composable(Routes.CHAT) {
            ChatScreen(
                onOpenHome = {
                    navController.navigate(Routes.HOME) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onOpenChatbot = { navController.navigate(Routes.CHATBOT) },
                onOpenMyFood = { navController.navigate(Routes.MYFOOD) },
                onOpenProfile = { navController.navigate(Routes.PROFILE) }
            )
        }

        composable(Routes.MYFOOD) {
            MyFoodScreen(
                onOpenHome = {
                    navController.navigate(Routes.HOME) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onOpenChat = {
                    navController.navigate(Routes.CHAT) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onOpenMyFood = {

                },
                onOpenProfile = {
                    navController.navigate(Routes.PROFILE) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                }
            )
        }

        composable(Routes.PROFILE) {
            ProfileScreen(
                onOpenHome = {
                    navController.navigate(Routes.HOME) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onOpenChat = {
                    navController.navigate(Routes.CHAT) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onOpenChatbot = {
                    navController.navigate(Routes.CHATBOT)
                },
                onOpenMyFood = {
                    navController.navigate(Routes.MYFOOD) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onOpenProfile = {
                },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // ================== FITUR ==================
        composable(Routes.MART) {
            MartDagingScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.CHATBOT) {
            ChatbotScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.RECIPE) { backStackEntry ->
            val vm: RecipeViewModel = viewModel(backStackEntry)
            RecipeRoute(
                viewModel = vm,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.COMMUNITY) {
            CommunityRoute(
                onBack = { navController.popBackStack() },
                onCreatePost = {}
            )
        }

        composable(Routes.CONSULTATION) {
            ConsultationScreen(onBack = { navController.popBackStack() })
        }
    }
}
