package com.myapp.data.repository

import com.myapp.core.Result
import com.myapp.data.local.dao.NotificationSettingsDao
import com.myapp.data.mapper.toDomain
import com.myapp.data.mapper.toEntity
import com.myapp.data.remote.api.JapaneseApi
import com.myapp.data.remote.dto.NotificationSettingsUpdateRequest
import com.myapp.domain.repository.NotificationRepository
import com.myapp.domain.model.NotificationPayload
import com.myapp.domain.model.NotificationSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    private val api: JapaneseApi,
    private val notificationSettingsDao: NotificationSettingsDao,
) : NotificationRepository {

    override suspend fun getSettings(): Result<NotificationSettings> = Result.wrap {
        try {
            val response = api.getNotificationSettings()
            if (response.isSuccessful) {
                val dto = response.body() ?: throw Exception("Empty response")
                notificationSettingsDao.insert(dto.toEntity())
                dto.toDomain()
            } else {
                throw Exception("Failed to fetch settings")
            }
        } catch (e: Exception) {
            val cached = notificationSettingsDao.getById("default")
            if (cached != null) {
                cached.toDomain()
            } else {
                NotificationSettings()
            }
        }
    }

    override suspend fun updateSettings(settings: NotificationSettings): Result<NotificationSettings> = Result.wrap {
        val response = api.updateNotificationSettings(
            NotificationSettingsUpdateRequest(
                isEnabled = settings.isEnabled,
                startHour = settings.startHour,
                startMinute = settings.startMinute,
                endHour = settings.endHour,
                endMinute = settings.endMinute,
                notificationsPerDay = settings.notificationsPerDay,
                minimumIntervalMinutes = settings.minimumIntervalMinutes,
                activeDays = settings.activeDays,
                soundEnabled = settings.soundEnabled,
                vibrationEnabled = settings.vibrationEnabled,
                showEnglish = settings.showEnglish,
                showRomaji = settings.showRomaji,
                jlptLevels = settings.jlptLevels,
                categoryIds = settings.categoryIds,
            )
        )
        if (response.isSuccessful) {
            val dto = response.body() ?: throw Exception("Empty response")
            notificationSettingsDao.insert(dto.toEntity())
            dto.toDomain()
        } else {
            throw Exception("Failed to update notification settings")
        }
    }

    override suspend fun getNextNotification(): Result<NotificationPayload> = Result.wrap {
        val response = api.getNextNotification()
        if (response.isSuccessful) {
            response.body()?.toDomain() ?: throw Exception("Empty response")
        } else {
            throw Exception("No notifications available")
        }
    }
}
