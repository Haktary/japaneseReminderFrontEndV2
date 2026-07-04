package com.myapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val darkMode: Flow<Boolean>
    val frenchInterface: Flow<Boolean>
    val dailyGoal: Flow<Int>
    val soundEnabled: Flow<Boolean>
    val vibrationEnabled: Flow<Boolean>
    val audioMode: Flow<String>

    suspend fun setDarkMode(enabled: Boolean)
    suspend fun setFrenchInterface(enabled: Boolean)
    suspend fun setDailyGoal(goal: Int)
    suspend fun setSoundEnabled(enabled: Boolean)
    suspend fun setVibrationEnabled(enabled: Boolean)
    suspend fun setAudioMode(mode: String)
    suspend fun setLastSyncTimestamp(timestamp: Long)
    suspend fun getLastSyncTimestamp(): Flow<Long>
}
