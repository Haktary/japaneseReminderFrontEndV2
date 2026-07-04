package com.myapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_progress",
    indices = [
        Index(value = ["vocabulary_id"], unique = true),
        Index(value = ["next_review_at"]),
        Index(value = ["is_synced"]),
    ],
)
data class UserProgressEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "vocabulary_id")
    val vocabularyId: String,
    @ColumnInfo(name = "success_count")
    val successCount: Int = 0,
    @ColumnInfo(name = "failure_count")
    val failureCount: Int = 0,
    @ColumnInfo(name = "ease_factor")
    val easeFactor: Double = 2.5,
    @ColumnInfo(name = "interval_days")
    val intervalDays: Int = 0,
    val repetitions: Int = 0,
    @ColumnInfo(name = "next_review_at")
    val nextReviewAt: Double = 0.0,
    @ColumnInfo(name = "consecutive_correct")
    val consecutiveCorrect: Int = 0,
    @ColumnInfo(name = "consecutive_incorrect")
    val consecutiveIncorrect: Int = 0,
    @ColumnInfo(name = "total_reviews")
    val totalReviews: Int = 0,
    @ColumnInfo(name = "total_correct")
    val totalCorrect: Int = 0,
    @ColumnInfo(name = "total_incorrect")
    val totalIncorrect: Int = 0,
    @ColumnInfo(name = "average_response_time_ms")
    val averageResponseTimeMs: Double = 0.0,
    @ColumnInfo(name = "last_sync_at")
    val lastSyncAt: Long? = null,
    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false,
)
