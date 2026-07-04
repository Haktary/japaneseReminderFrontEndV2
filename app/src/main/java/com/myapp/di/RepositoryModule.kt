package com.myapp.di

import com.myapp.data.repository.AuthRepositoryImpl
import com.myapp.data.repository.NotificationRepositoryImpl
import com.myapp.data.repository.ReviewRepositoryImpl
import com.myapp.data.repository.SettingsRepositoryImpl
import com.myapp.data.repository.SyncRepositoryImpl
import com.myapp.data.repository.VocabularyRepositoryImpl
import com.myapp.domain.repository.AuthRepository
import com.myapp.domain.repository.NotificationRepository
import com.myapp.domain.repository.ReviewRepository
import com.myapp.domain.repository.SettingsRepository
import com.myapp.domain.repository.SyncRepository
import com.myapp.domain.repository.VocabularyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindVocabularyRepository(impl: VocabularyRepositoryImpl): VocabularyRepository

    @Binds
    @Singleton
    abstract fun bindReviewRepository(impl: ReviewRepositoryImpl): ReviewRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(impl: NotificationRepositoryImpl): NotificationRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindSyncRepository(impl: SyncRepositoryImpl): SyncRepository
}
