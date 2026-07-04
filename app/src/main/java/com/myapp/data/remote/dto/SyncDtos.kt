package com.myapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SyncProgressRequest(
    val items: List<SyncProgressItem>,
)

@Serializable
data class SyncProgressItem(
    @SerialName("vocabulary_id")
    val vocabularyId: String,
    @SerialName("success_count")
    val successCount: Int,
    @SerialName("failure_count")
    val failureCount: Int,
    @SerialName("ease_factor")
    val easeFactor: Double,
    @SerialName("interval_days")
    val intervalDays: Int,
    val repetitions: Int,
    @SerialName("next_review_at")
    val nextReviewAt: Double,
)

@Serializable
data class SyncProgressResponse(
    val synced: Int,
    val failed: Int,
)

@Serializable
data class SyncResponse(
    val success: Boolean,
    val message: String = "",
)
