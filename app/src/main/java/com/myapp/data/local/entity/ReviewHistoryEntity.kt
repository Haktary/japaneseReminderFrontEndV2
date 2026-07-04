package com.myapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "review_history",
    indices = [
        Index(value = ["vocabulary_id"]),
        Index(value = ["created_at"]),
        Index(value = ["is_synced"]),
    ],
)
data class ReviewHistoryEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "vocabulary_id")
    val vocabularyId: String,
    val result: String,
    @ColumnInfo(name = "ease_factor_after")
    val easeFactorAfter: Double,
    @ColumnInfo(name = "interval_days_after")
    val intervalDaysAfter: Int,
    @ColumnInfo(name = "repetitions_after")
    val repetitionsAfter: Int,
    @ColumnInfo(name = "response_time_ms")
    val responseTimeMs: Int = 0,
    @ColumnInfo(name = "review_source")
    val reviewSource: String = "notification",
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false,
)
