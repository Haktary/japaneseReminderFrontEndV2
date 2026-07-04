package com.myapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.myapp.data.local.Converters
import com.myapp.data.local.dao.CategoryDao
import com.myapp.data.local.dao.DownloadedAudioDao
import com.myapp.data.local.dao.NotificationSettingsDao
import com.myapp.data.local.dao.PendingSyncDao
import com.myapp.data.local.dao.ReviewHistoryDao
import com.myapp.data.local.dao.TagDao
import com.myapp.data.local.dao.UserProgressDao
import com.myapp.data.local.dao.VocabularyDao
import com.myapp.data.local.entity.CategoryEntity
import com.myapp.data.local.entity.DownloadedAudioEntity
import com.myapp.data.local.entity.NotificationSettingsEntity
import com.myapp.data.local.entity.PendingSyncEntity
import com.myapp.data.local.entity.ReviewHistoryEntity
import com.myapp.data.local.entity.TagEntity
import com.myapp.data.local.entity.UserProgressEntity
import com.myapp.data.local.entity.VocabularyEntity

@Database(
    entities = [
        VocabularyEntity::class,
        ReviewHistoryEntity::class,
        UserProgressEntity::class,
        NotificationSettingsEntity::class,
        CategoryEntity::class,
        TagEntity::class,
        PendingSyncEntity::class,
        DownloadedAudioEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vocabularyDao(): VocabularyDao
    abstract fun reviewHistoryDao(): ReviewHistoryDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun notificationSettingsDao(): NotificationSettingsDao
    abstract fun categoryDao(): CategoryDao
    abstract fun tagDao(): TagDao
    abstract fun pendingSyncDao(): PendingSyncDao
    abstract fun downloadedAudioDao(): DownloadedAudioDao
}
