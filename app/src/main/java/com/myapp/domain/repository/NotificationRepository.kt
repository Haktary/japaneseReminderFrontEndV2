package com.myapp.domain.repository

import com.myapp.core.Result
import com.myapp.domain.model.NotificationPayload
import com.myapp.domain.model.NotificationSettings

interface NotificationRepository {
    suspend fun getSettings(): Result<NotificationSettings>
    suspend fun updateSettings(settings: NotificationSettings): Result<NotificationSettings>
    suspend fun getNextNotification(): Result<NotificationPayload>
}
