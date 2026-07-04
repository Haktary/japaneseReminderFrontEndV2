package com.myapp.domain.model

data class DashboardStats(
    val totalVocabulary: Int = 0,
    val wordsLearned: Int = 0,
    val wordsRemaining: Int = 0,
    val todayReviews: Int = 0,
    val reviewStreak: Int = 0,
    val successRate: Double = 0.0,
    val weeklyProgress: Int = 0,
    val monthlyProgress: Int = 0,
)
