package com.myapp.data.sync

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.myapp.data.repository.NotificationRepositoryImpl
import com.myapp.domain.model.NotificationPayload
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationRepository: NotificationRepositoryImpl,
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val CHANNEL_ID = "vocabulary_learning"
        const val NOTIFICATION_ID = 1001
        const val ACTION_MARK_REVIEWED = "com.myapp.action.MARK_REVIEWED"
        const val ACTION_SHOW_ANSWER = "com.myapp.action.SHOW_ANSWER"
        const val ACTION_SNOOZE = "com.myapp.action.SNOOZE"
    }

    override suspend fun doWork(): Result {
        return try {
            val notificationPayload = notificationRepository.getNextNotification()
            if (notificationPayload.isSuccess) {
                val payload = notificationPayload.getOrNull() ?: return Result.success()
                val settings = notificationRepository.getSettings()
                val showEnglish = settings.getOrNull()?.showEnglish ?: false
                val showRomaji = settings.getOrNull()?.showRomaji ?: true
                showNotification(payload, showEnglish, showRomaji)
                Result.success()
            } else {
                Result.success()
            }
        } catch (e: Exception) {
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }

    private fun showNotification(payload: NotificationPayload, showEnglish: Boolean, showRomaji: Boolean) {
        createNotificationChannel()

        val contentText = buildString {
            append(payload.frenchTranslation)
            if (showEnglish && payload.englishTranslation != null) {
                append(" - ").append(payload.englishTranslation)
            }
        }

        val bigText = buildString {
            appendLine(payload.hiragana)
            if (payload.kanji != null) {
                append("(").append(payload.kanji).appendLine(")")
            }
            if (showRomaji && payload.romaji != null) {
                appendLine(payload.romaji)
            }
            appendLine()
            append(contentText)
        }

        val showAnswerIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            Intent(ACTION_SHOW_ANSWER).apply {
                putExtra("vocabulary_id", payload.vocabularyId)
                putExtra("hiragana", payload.hiragana)
                putExtra("french_translation", payload.frenchTranslation)
                putExtra("english_translation", payload.englishTranslation)
                putExtra("audio_url", payload.audioUrl)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val markReviewedIntent = PendingIntent.getBroadcast(
            applicationContext,
            1,
            Intent(ACTION_MARK_REVIEWED).apply {
                putExtra("vocabulary_id", payload.vocabularyId)
                putExtra("result", "correct")
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val snoozeIntent = PendingIntent.getBroadcast(
            applicationContext,
            2,
            Intent(ACTION_SNOOZE).apply {
                putExtra("vocabulary_id", payload.vocabularyId)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(payload.hiragana)
            .setContentText(contentText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .addAction(
                NotificationCompat.Action.Builder(
                    android.R.drawable.ic_menu_edit,
                    "Mark Reviewed",
                    markReviewedIntent,
                ).build()
            )
            .addAction(
                NotificationCompat.Action.Builder(
                    android.R.drawable.ic_menu_info_details,
                    "Show Answer",
                    showAnswerIntent,
                ).build()
            )
            .addAction(
                NotificationCompat.Action.Builder(
                    android.R.drawable.ic_menu_close_clear_cancel,
                    "Snooze",
                    snoozeIntent,
                ).build()
            )
            .build()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(applicationContext).notify(NOTIFICATION_ID, notification)
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Vocabulary Learning",
            NotificationManager.IMPORTANCE_HIGH,
        ).apply {
            description = "Notifications for vocabulary review"
            enableVibration(true)
            setSound(
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build(),
            )
        }

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
