package com.example.masakin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.example.masakin.ui.community.CommunityRoute
import com.example.masakin.ui.mart.navigation.MartNavGraph
import com.example.masakin.ui.recipe.RecipeViewModel
import com.example.masakin.ui.screens.*

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
    const val APPOINTMENT = "appointment/{consultantId}"
    const val CHAT_SCREEN_2 = "chat_screen_2/{consultantId}"


    fun appointmentRoute(consultantId: String) = "appointment/$consultantId"
    fun chatScreen2Route(consultantId: String) = "chat_screen_2/$consultantId"

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

                // Navigasi ke Chat List saat tombol Chat di Navbar Home ditekan
                onOpenChat = {
                    navController.navigate(Routes.CHAT) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onOpenMyFood = { navController.navigate(Routes.MYFOOD) },
                onOpenProfile = { navController.navigate(Routes.PROFILE) }
            )
        }

        // ================== BOTTOM NAVBAR HALAMAN ==================

        // UPDATE: Routes.CHAT sekarang mengarah ke ChatListScreen
        composable(Routes.CHAT) {
            ChatListScreen(
                onNavigateToHome = {
                    navController.navigate(Routes.HOME) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME) { inclusive = true } // Kembali ke home utama
                    }
                },
                onNavigateToMyFood = {
                    navController.navigate(Routes.MYFOOD) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onNavigateToProfile = {
                    navController.navigate(Routes.PROFILE) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onNavigateToChatDetail = { consultantId ->
                    // Membuka ChatScreen2 milik Dokter Trini
                    navController.navigate(Routes.chatScreen2Route(consultantId))
                }
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
                onOpenMyFood = {},
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
                onOpenChatbot = { navController.navigate(Routes.CHATBOT) },
                onOpenMyFood = {
                    navController.navigate(Routes.MYFOOD) {
                        launchSingleTop = true
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onOpenProfile = {},
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // ================== FITUR ==================
        composable(Routes.MART) {
            MartNavGraph()
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
            ConsultationScreen(
                onBack = { navController.popBackStack() },
                onConsultantClick = {
                    navController.navigate(Routes.appointmentRoute(it))
                }
            )
        }

        composable(
            route = Routes.APPOINTMENT,
            arguments = listOf(navArgument("consultantId") { type = NavType.StringType })
        ) { backStackEntry ->
            val consultantId = backStackEntry.arguments?.getString("consultantId")
            if (consultantId != null) {
                AppointmentScreen(
                    consultantId = consultantId,
                    onBack = { navController.popBackStack() },
                    onNavigateToChat = {
                        navController.navigate(Routes.chatScreen2Route(it)) {
                            popUpTo(Routes.HOME)
                        }
                    }
                )
            }
        }

        composable(
            route = Routes.CHAT_SCREEN_2,
            arguments = listOf(navArgument("consultantId") { type = NavType.StringType })
        ) { backStackEntry ->
            val consultantId = backStackEntry.arguments?.getString("consultantId")
            if (consultantId != null) {
                ChatScreen2(
                    consultantId = consultantId,
                    onBackToHome = { navController.popBackStack() }
                )
            }
        }
    }
}