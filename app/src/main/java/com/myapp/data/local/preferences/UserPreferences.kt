package com.myapp.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USERNAME = stringPreferencesKey("username")
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
        private val FRENCH_INTERFACE = booleanPreferencesKey("french_interface")
        private val DAILY_GOAL = intPreferencesKey("daily_goal")
        private val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        private val VIBRATION_ENABLED = booleanPreferencesKey("vibration_enabled")
        private val AUDIO_MODE = stringPreferencesKey("audio_mode")
        private val LAST_SYNC_TIMESTAMP = longPreferencesKey("last_sync_timestamp")
        private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    }

    val accessToken: Flow<String?> = context.dataStore.data.map { it[ACCESS_TOKEN] }
    val refreshToken: Flow<String?> = context.dataStore.data.map { it[REFRESH_TOKEN] }
    val userId: Flow<String?> = context.dataStore.data.map { it[USER_ID] }
    val userEmail: Flow<String?> = context.dataStore.data.map { it[USER_EMAIL] }
    val username: Flow<String?> = context.dataStore.data.map { it[USERNAME] }
    val darkMode: Flow<Boolean> = context.dataStore.data.map { it[DARK_MODE] ?: false }
    val frenchInterface: Flow<Boolean> = context.dataStore.data.map { it[FRENCH_INTERFACE] ?: false }
    val dailyGoal: Flow<Int> = context.dataStore.data.map { it[DAILY_GOAL] ?: 10 }
    val soundEnabled: Flow<Boolean> = context.dataStore.data.map { it[SOUND_ENABLED] ?: true }
    val vibrationEnabled: Flow<Boolean> = context.dataStore.data.map { it[VIBRATION_ENABLED] ?: true }
    val audioMode: Flow<String> = context.dataStore.data.map { it[AUDIO_MODE] ?: "tts" }
    val lastSyncTimestamp: Flow<Long> = context.dataStore.data.map { it[LAST_SYNC_TIMESTAMP] ?: 0L }
    val onboardingCompleted: Flow<Boolean> = context.dataStore.data.map { it[ONBOARDING_COMPLETED] ?: false }

    suspend fun saveTokens(accessToken: String?, refreshToken: String?) {
        context.dataStore.edit { prefs ->
            if (accessToken != null) prefs[ACCESS_TOKEN] = accessToken else prefs.remove(ACCESS_TOKEN)
            if (refreshToken != null) prefs[REFRESH_TOKEN] = refreshToken else prefs.remove(REFRESH_TOKEN)
        }
    }

    suspend fun saveUserInfo(userId: String, email: String, username: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = userId
            prefs[USER_EMAIL] = email
            prefs[USERNAME] = username
        }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[DARK_MODE] = enabled }
    }

    suspend fun setFrenchInterface(enabled: Boolean) {
        context.dataStore.edit { it[FRENCH_INTERFACE] = enabled }
    }

    suspend fun setDailyGoal(goal: Int) {
        context.dataStore.edit { it[DAILY_GOAL] = goal }
    }

    suspend fun setSoundEnabled(enabled: Boolean) {
        context.dataStore.edit { it[SOUND_ENABLED] = enabled }
    }

    suspend fun setVibrationEnabled(enabled: Boolean) {
        context.dataStore.edit { it[VIBRATION_ENABLED] = enabled }
    }

    suspend fun setAudioMode(mode: String) {
        context.dataStore.edit { it[AUDIO_MODE] = mode }
    }

    suspend fun setLastSyncTimestamp(timestamp: Long) {
        context.dataStore.edit { it[LAST_SYNC_TIMESTAMP] = timestamp }
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { it[ONBOARDING_COMPLETED] = completed }
    }

    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }
}
