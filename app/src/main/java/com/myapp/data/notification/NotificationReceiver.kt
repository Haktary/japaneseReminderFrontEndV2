package com.myapp.data.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.myapp.data.sync.NotificationWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationHelper: NotificationActionHelper

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            NotificationWorker.ACTION_MARK_REVIEWED -> {
                val vocabularyId = intent.getStringExtra("vocabulary_id") ?: return
                val result = intent.getStringExtra("result") ?: "correct"
                notificationHelper.markReviewed(vocabularyId, result)
            }
            NotificationWorker.ACTION_SHOW_ANSWER -> {
                val vocabularyId = intent.getStringExtra("vocabulary_id") ?: return
                val hiragana = intent.getStringExtra("hiragana") ?: return
                val frenchTranslation = intent.getStringExtra("french_translation") ?: return
                val englishTranslation = intent.getStringExtra("english_translation")
                showAnswerNotification(context, vocabularyId, hiragana, frenchTranslation, englishTranslation)
            }
            NotificationWorker.ACTION_SNOOZE -> {
                val vocabularyId = intent.getStringExtra("vocabulary_id") ?: return
                notificationHelper.snooze(vocabularyId)
            }
        }
    }

    private fun showAnswerNotification(
        context: Context,
        vocabularyId: String,
        hiragana: String,
        frenchTranslation: String,
        englishTranslation: String?,
    ) {
        val contentText = buildString {
            append("Translation: $frenchTranslation")
            if (englishTranslation != null) {
                append(" ($englishTranslation)")
            }
        }

        val markReviewedIntent = PendingIntent.getBroadcast(
            context,
            vocabularyId.hashCode(),
            Intent(NotificationWorker.ACTION_MARK_REVIEWED).apply {
                putExtra("vocabulary_id", vocabularyId)
                putExtra("result", "correct")
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val notification = NotificationCompat.Builder(context, NotificationWorker.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(hiragana)
            .setContentText(contentText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .addAction(
                NotificationCompat.Action.Builder(
                    android.R.drawable.ic_menu_edit,
                    "Mark Learned",
                    markReviewedIntent,
                ).build()
            )
            .build()

        NotificationManagerCompat.from(context).notify(vocabularyId.hashCode(), notification)
    }
}
