package com.myapp.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")

    data object VocabularyDetail : Screen("vocabulary/{vocabularyId}") {
        fun createRoute(vocabularyId: String) = "vocabulary/$vocabularyId"
    }

    data object Settings : Screen("settings")
    data object NotificationSettings : Screen("notification_settings")
    data object About : Screen("about")
    data object ReviewSession : Screen("review")
    data object Search : Screen("search")
    data object History : Screen("history")
    data object Statistics : Screen("statistics")
}
