package com.myapp.data.mapper

import com.myapp.data.local.entity.NotificationSettingsEntity
import com.myapp.data.remote.dto.NotificationPayloadResponse
import com.myapp.data.remote.dto.NotificationSettingsResponse
import com.myapp.domain.model.NotificationPayload
import com.myapp.domain.model.NotificationSettings

fun NotificationSettingsResponse.toDomain(): NotificationSettings = NotificationSettings(
    id = id,
    userId = userId,
    isEnabled = isEnabled,
    startHour = startHour,
    startMinute = startMinute,
    endHour = endHour,
    endMinute = endMinute,
    notificationsPerDay = notificationsPerDay,
    minimumIntervalMinutes = minimumIntervalMinutes,
    activeDays = activeDays,
    soundEnabled = soundEnabled,
    vibrationEnabled = vibrationEnabled,
    showEnglish = showEnglish,
    showRomaji = showRomaji,
    jlptLevels = jlptLevels,
    categoryIds = categoryIds,
)

fun NotificationSettingsResponse.toEntity(): NotificationSettingsEntity = NotificationSettingsEntity(
    id = id,
    userId = userId,
    isEnabled = isEnabled,
    startHour = startHour,
    startMinute = startMinute,
    endHour = endHour,
    endMinute = endMinute,
    notificationsPerDay = notificationsPerDay,
    minimumIntervalMinutes = minimumIntervalMinutes,
    activeDays = activeDays.joinToString(","),
    soundEnabled = soundEnabled,
    vibrationEnabled = vibrationEnabled,
    showEnglish = showEnglish,
    showRomaji = showRomaji,
    jlptLevels = jlptLevels?.joinToString(","),
    categoryIds = categoryIds?.joinToString(","),
)

fun NotificationSettingsEntity.toDomain(): NotificationSettings = NotificationSettings(
    id = id,
    userId = userId,
    isEnabled = isEnabled,
    startHour = startHour,
    startMinute = startMinute,
    endHour = endHour,
    endMinute = endMinute,
    notificationsPerDay = notificationsPerDay,
    minimumIntervalMinutes = minimumIntervalMinutes,
    activeDays = activeDays.split(",").mapNotNull { it.trim().toIntOrNull() },
    soundEnabled = soundEnabled,
    vibrationEnabled = vibrationEnabled,
    showEnglish = showEnglish,
    showRomaji = showRomaji,
    jlptLevels = jlptLevels?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() },
    categoryIds = categoryIds?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() },
)

fun NotificationPayloadResponse.toDomain(): NotificationPayload = NotificationPayload(
    vocabularyId = vocabularyId,
    hiragana = hiragana,
    katakana = katakana,
    kanji = kanji,
    romaji = romaji,
    frenchTranslation = frenchTranslation,
    englishTranslation = englishTranslation,
    audioUrl = audioUrl,
)
