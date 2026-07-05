package com.myapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myapp.ui.home.HomeScreen
import com.myapp.ui.login.LoginScreen
import com.myapp.ui.register.RegisterScreen
import com.myapp.ui.splash.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
            )
        }

        composable(Screen.Settings.route) {
            PlaceholderScreen(name = "Settings")
        }

        composable(Screen.NotificationSettings.route) {
            PlaceholderScreen(name = "Notification Settings")
        }

        composable(Screen.About.route) {
            PlaceholderScreen(name = "About")
        }

        composable(Screen.ReviewSession.route) {
            PlaceholderScreen(name = "Review Session")
        }

        composable(Screen.Search.route) {
            PlaceholderScreen(name = "Search")
        }

        composable(Screen.History.route) {
            PlaceholderScreen(name = "History")
        }

        composable(Screen.Statistics.route) {
            PlaceholderScreen(name = "Statistics")
        }

        composable(Screen.VocabularyDetail.route) {
            PlaceholderScreen(name = "Vocabulary Detail")
        }
    }
}
