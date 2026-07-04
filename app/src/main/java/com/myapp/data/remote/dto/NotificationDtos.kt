package com.myapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationSettingsResponse(
    val id: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("is_enabled")
    val isEnabled: Boolean = true,
    @SerialName("start_hour")
    val startHour: Int = 9,
    @SerialName("start_minute")
    val startMinute: Int = 0,
    @SerialName("end_hour")
    val endHour: Int = 20,
    @SerialName("end_minute")
    val endMinute: Int = 0,
    @SerialName("notifications_per_day")
    val notificationsPerDay: Int = 8,
    @SerialName("minimum_interval_minutes")
    val minimumIntervalMinutes: Int = 15,
    @SerialName("active_days")
    val activeDays: List<Int> = listOf(0, 1, 2, 3, 4, 5, 6),
    @SerialName("sound_enabled")
    val soundEnabled: Boolean = true,
    @SerialName("vibration_enabled")
    val vibrationEnabled: Boolean = true,
    @SerialName("show_english")
    val showEnglish: Boolean = false,
    @SerialName("show_romaji")
    val showRomaji: Boolean = true,
    @SerialName("jlpt_levels")
    val jlptLevels: List<String>? = null,
    @SerialName("category_ids")
    val categoryIds: List<String>? = null,
)

@Serializable
data class NotificationSettingsUpdateRequest(
    @SerialName("is_enabled")
    val isEnabled: Boolean? = null,
    @SerialName("start_hour")
    val startHour: Int? = null,
    @SerialName("start_minute")
    val startMinute: Int? = null,
    @SerialName("end_hour")
    val endHour: Int? = null,
    @SerialName("end_minute")
    val endMinute: Int? = null,
    @SerialName("notifications_per_day")
    val notificationsPerDay: Int? = null,
    @SerialName("minimum_interval_minutes")
    val minimumIntervalMinutes: Int? = null,
    @SerialName("active_days")
    val activeDays: List<Int>? = null,
    @SerialName("sound_enabled")
    val soundEnabled: Boolean? = null,
    @SerialName("vibration_enabled")
    val vibrationEnabled: Boolean? = null,
    @SerialName("show_english")
    val showEnglish: Boolean? = null,
    @SerialName("show_romaji")
    val showRomaji: Boolean? = null,
    @SerialName("jlpt_levels")
    val jlptLevels: List<String>? = null,
    @SerialName("category_ids")
    val categoryIds: List<String>? = null,
)

@Serializable
data class NotificationPayloadResponse(
    @SerialName("vocabulary_id")
    val vocabularyId: String,
    val hiragana: String,
    val katakana: String? = null,
    val kanji: String? = null,
    val romaji: String? = null,
    @SerialName("french_translation")
    val frenchTranslation: String,
    @SerialName("english_translation")
    val englishTranslation: String? = null,
    @SerialName("audio_url")
    val audioUrl: String? = null,
)
