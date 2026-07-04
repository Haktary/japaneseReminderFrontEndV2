package com.myapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_settings")
data class NotificationSettingsEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "is_enabled")
    val isEnabled: Boolean = true,
    @ColumnInfo(name = "start_hour")
    val startHour: Int = 9,
    @ColumnInfo(name = "start_minute")
    val startMinute: Int = 0,
    @ColumnInfo(name = "end_hour")
    val endHour: Int = 20,
    @ColumnInfo(name = "end_minute")
    val endMinute: Int = 0,
    @ColumnInfo(name = "notifications_per_day")
    val notificationsPerDay: Int = 8,
    @ColumnInfo(name = "minimum_interval_minutes")
    val minimumIntervalMinutes: Int = 15,
    @ColumnInfo(name = "active_days")
    val activeDays: String = "0,1,2,3,4,5,6",
    @ColumnInfo(name = "sound_enabled")
    val soundEnabled: Boolean = true,
    @ColumnInfo(name = "vibration_enabled")
    val vibrationEnabled: Boolean = true,
    @ColumnInfo(name = "show_english")
    val showEnglish: Boolean = false,
    @ColumnInfo(name = "show_romaji")
    val showRomaji: Boolean = true,
    @ColumnInfo(name = "jlpt_levels")
    val jlptLevels: String? = null,
    @ColumnInfo(name = "category_ids")
    val categoryIds: String? = null,
)
