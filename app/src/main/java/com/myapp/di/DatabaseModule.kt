package com.myapp.di

import android.content.Context
import androidx.room.Room
import com.myapp.data.local.dao.CategoryDao
import com.myapp.data.local.dao.DownloadedAudioDao
import com.myapp.data.local.dao.NotificationSettingsDao
import com.myapp.data.local.dao.PendingSyncDao
import com.myapp.data.local.dao.ReviewHistoryDao
import com.myapp.data.local.dao.TagDao
import com.myapp.data.local.dao.UserProgressDao
import com.myapp.data.local.dao.VocabularyDao
import com.myapp.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "japanese_app.db"

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME,
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideVocabularyDao(database: AppDatabase): VocabularyDao {
        return database.vocabularyDao()
    }

    @Provides
    fun provideReviewHistoryDao(database: AppDatabase): ReviewHistoryDao {
        return database.reviewHistoryDao()
    }

    @Provides
    fun provideUserProgressDao(database: AppDatabase): UserProgressDao {
        return database.userProgressDao()
    }

    @Provides
    fun provideNotificationSettingsDao(database: AppDatabase): NotificationSettingsDao {
        return database.notificationSettingsDao()
    }

    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    fun provideTagDao(database: AppDatabase): TagDao {
        return database.tagDao()
    }

    @Provides
    fun providePendingSyncDao(database: AppDatabase): PendingSyncDao {
        return database.pendingSyncDao()
    }

    @Provides
    fun provideDownloadedAudioDao(database: AppDatabase): DownloadedAudioDao {
        return database.downloadedAudioDao()
    }
}
