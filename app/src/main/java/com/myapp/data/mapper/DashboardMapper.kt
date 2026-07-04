package com.myapp.data.mapper

import com.myapp.data.remote.dto.DashboardResponse
import com.myapp.domain.model.DashboardStats

fun DashboardResponse.toDomain(): DashboardStats = DashboardStats(
    totalVocabulary = totalVocabulary,
    wordsLearned = wordsLearned,
    wordsRemaining = wordsRemaining,
    todayReviews = todayReviews,
    reviewStreak = reviewStreak,
    successRate = successRate,
    weeklyProgress = weeklyProgress,
    monthlyProgress = monthlyProgress,
)
