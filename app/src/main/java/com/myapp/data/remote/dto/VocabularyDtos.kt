package com.myapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VocabularyResponse(
    val id: String,
    val hiragana: String,
    val katakana: String? = null,
    val kanji: String? = null,
    val romaji: String,
    @SerialName("french_translation")
    val frenchTranslation: String,
    @SerialName("english_translation")
    val englishTranslation: String,
    @SerialName("example_sentence")
    val exampleSentence: String? = null,
    @SerialName("example_translation")
    val exampleTranslation: String? = null,
    @SerialName("jlpt_level")
    val jlptLevel: String? = null,
    @SerialName("category_id")
    val categoryId: String? = null,
    @SerialName("category_name")
    val categoryName: String? = null,
    @SerialName("pitch_accent")
    val pitchAccent: String? = null,
    val tags: List<String> = emptyList(),
    @SerialName("has_audio")
    val hasAudio: Boolean = false,
    @SerialName("audio_url")
    val audioUrl: String? = null,
    @SerialName("is_active")
    val isActive: Boolean = true,
    @SerialName("success_count")
    val successCount: Int = 0,
    @SerialName("failure_count")
    val failureCount: Int = 0,
    @SerialName("ease_factor")
    val easeFactor: Double = 2.5,
    @SerialName("interval_days")
    val intervalDays: Int = 0,
    val repetitions: Int = 0,
    @SerialName("next_review_at")
    val nextReviewAt: Double? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
)

@Serializable
data class PaginatedVocabularyResponse(
    val items: List<VocabularyResponse>,
    val total: Int,
    val page: Int,
    @SerialName("page_size")
    val pageSize: Int,
    val pages: Int,
)

@Serializable
data class DueVocabularyResponse(
    val items: List<VocabularyResponse>,
    val count: Int,
)
