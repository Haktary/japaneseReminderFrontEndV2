package com.myapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewSubmitRequest(
    @SerialName("vocabulary_id")
    val vocabularyId: String,
    val result: String,
    @SerialName("response_time_ms")
    val responseTimeMs: Int = 0,
    val source: String = "notification",
)

@Serializable
data class ReviewResultResponse(
    @SerialName("vocabulary_id")
    val vocabularyId: String,
    val result: String,
    @SerialName("ease_factor_after")
    val easeFactorAfter: Double,
    @SerialName("interval_days_after")
    val intervalDaysAfter: Int,
    @SerialName("repetitions_after")
    val repetitionsAfter: Int,
    @SerialName("next_review_at")
    val nextReviewAt: Double,
)

@Serializable
data class ReviewHistoryResponse(
    val id: String,
    @SerialName("vocabulary_id")
    val vocabularyId: String,
    @SerialName("vocabulary_word")
    val vocabularyWord: String,
    val result: String,
    @SerialName("ease_factor_after")
    val easeFactorAfter: Double,
    @SerialName("interval_days_after")
    val intervalDaysAfter: Int,
    @SerialName("repetitions_after")
    val repetitionsAfter: Int,
    @SerialName("response_time_ms")
    val responseTimeMs: Int = 0,
    @SerialName("review_source")
    val reviewSource: String = "notification",
    @SerialName("created_at")
    val createdAt: String = "",
)

@Serializable
data class PaginatedReviewHistoryResponse(
    val items: List<ReviewHistoryResponse>,
    val total: Int,
    val page: Int,
    @SerialName("page_size")
    val pageSize: Int,
    val pages: Int,
)

@Serializable
data class DashboardResponse(
    @SerialName("total_vocabulary")
    val totalVocabulary: Int = 0,
    @SerialName("words_learned")
    val wordsLearned: Int = 0,
    @SerialName("words_remaining")
    val wordsRemaining: Int = 0,
    @SerialName("today_reviews")
    val todayReviews: Int = 0,
    @SerialName("review_streak")
    val reviewStreak: Int = 0,
    @SerialName("success_rate")
    val successRate: Double = 0.0,
    @SerialName("weekly_progress")
    val weeklyProgress: Int = 0,
    @SerialName("monthly_progress")
    val monthlyProgress: Int = 0,
)
