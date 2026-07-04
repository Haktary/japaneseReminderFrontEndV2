package com.myapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vocabularyChannel = NotificationChannel(
                CHANNEL_VOCABULARY,
                getString(R.string.channel_vocabulary_name),
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description = getString(R.string.channel_vocabulary_description)
                enableVibration(true)
            }

            val syncChannel = NotificationChannel(
                CHANNEL_SYNC,
                getString(R.string.channel_sync_name),
                NotificationManager.IMPORTANCE_LOW,
            ).apply {
                description = getString(R.string.channel_sync_description)
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(vocabularyChannel)
            manager.createNotificationChannel(syncChannel)
        }
    }

    companion object {
        const val CHANNEL_VOCABULARY = "vocabulary_learning"
        const val CHANNEL_SYNC = "data_sync"
    }
}
