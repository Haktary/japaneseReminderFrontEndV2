package com.myapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(Screen.Splash.route) {
            PlaceholderScreen(name = "Splash")
        }

        composable(Screen.Login.route) {
            PlaceholderScreen(name = "Login")
        }

        composable(Screen.Register.route) {
            PlaceholderScreen(name = "Register")
        }

        composable(Screen.Home.route) {
            PlaceholderScreen(name = "Home")
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
