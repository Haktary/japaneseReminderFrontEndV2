package com.myapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vocabulary",
    indices = [
        Index(value = ["hiragana"]),
        Index(value = ["kanji"]),
        Index(value = ["jlpt_level"]),
        Index(value = ["category_id"]),
        Index(value = ["is_active"]),
    ],
)
data class VocabularyEntity(
    @PrimaryKey
    val id: String,
    val hiragana: String,
    val katakana: String? = null,
    val kanji: String? = null,
    val romaji: String,
    @ColumnInfo(name = "french_translation")
    val frenchTranslation: String,
    @ColumnInfo(name = "english_translation")
    val englishTranslation: String,
    @ColumnInfo(name = "example_sentence")
    val exampleSentence: String? = null,
    @ColumnInfo(name = "example_translation")
    val exampleTranslation: String? = null,
    @ColumnInfo(name = "jlpt_level")
    val jlptLevel: String? = null,
    @ColumnInfo(name = "category_id")
    val categoryId: String? = null,
    @ColumnInfo(name = "category_name")
    val categoryName: String? = null,
    @ColumnInfo(name = "pitch_accent")
    val pitchAccent: String? = null,
    @ColumnInfo(name = "has_audio")
    val hasAudio: Boolean = false,
    @ColumnInfo(name = "audio_url")
    val audioUrl: String? = null,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean = true,
    @ColumnInfo(name = "tags")
    val tags: String? = null,
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
    val nextReviewAt: Double? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = true,
)
