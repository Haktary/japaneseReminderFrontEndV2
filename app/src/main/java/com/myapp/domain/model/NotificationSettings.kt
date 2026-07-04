package com.myapp.domain.model

data class NotificationSettings(
    val id: String = "",
    val userId: String = "",
    val isEnabled: Boolean = true,
    val startHour: Int = 9,
    val startMinute: Int = 0,
    val endHour: Int = 20,
    val endMinute: Int = 0,
    val notificationsPerDay: Int = 8,
    val minimumIntervalMinutes: Int = 15,
    val activeDays: List<Int> = listOf(0, 1, 2, 3, 4, 5, 6),
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val showEnglish: Boolean = false,
    val showRomaji: Boolean = true,
    val jlptLevels: List<String>? = null,
    val categoryIds: List<String>? = null,
)
