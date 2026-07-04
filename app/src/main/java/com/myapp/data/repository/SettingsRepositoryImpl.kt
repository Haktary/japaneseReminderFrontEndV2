package com.myapp.data.repository

import com.myapp.data.local.preferences.UserPreferences
import com.myapp.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val userPreferences: UserPreferences,
) : SettingsRepository {

    override val darkMode: Flow<Boolean> = userPreferences.darkMode
    override val frenchInterface: Flow<Boolean> = userPreferences.frenchInterface
    override val dailyGoal: Flow<Int> = userPreferences.dailyGoal
    override val soundEnabled: Flow<Boolean> = userPreferences.soundEnabled
    override val vibrationEnabled: Flow<Boolean> = userPreferences.vibrationEnabled
    override val audioMode: Flow<String> = userPreferences.audioMode

    override suspend fun setDarkMode(enabled: Boolean) = userPreferences.setDarkMode(enabled)
    override suspend fun setFrenchInterface(enabled: Boolean) = userPreferences.setFrenchInterface(enabled)
    override suspend fun setDailyGoal(goal: Int) = userPreferences.setDailyGoal(goal)
    override suspend fun setSoundEnabled(enabled: Boolean) = userPreferences.setSoundEnabled(enabled)
    override suspend fun setVibrationEnabled(enabled: Boolean) = userPreferences.setVibrationEnabled(enabled)
    override suspend fun setAudioMode(mode: String) = userPreferences.setAudioMode(mode)
    override suspend fun setLastSyncTimestamp(timestamp: Long) = userPreferences.setLastSyncTimestamp(timestamp)
    override suspend fun getLastSyncTimestamp(): Flow<Long> = userPreferences.lastSyncTimestamp
}
