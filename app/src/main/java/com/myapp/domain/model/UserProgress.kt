package com.myapp.domain.model

data class UserProgress(
    val id: String = "",
    val vocabularyId: String,
    val successCount: Int = 0,
    val failureCount: Int = 0,
    val easeFactor: Double = 2.5,
    val intervalDays: Int = 0,
    val repetitions: Int = 0,
    val nextReviewAt: Double = 0.0,
    val consecutiveCorrect: Int = 0,
    val consecutiveIncorrect: Int = 0,
    val totalReviews: Int = 0,
    val totalCorrect: Int = 0,
    val totalIncorrect: Int = 0,
    val averageResponseTimeMs: Double = 0.0,
    val lastSyncAt: Long? = null,
)
