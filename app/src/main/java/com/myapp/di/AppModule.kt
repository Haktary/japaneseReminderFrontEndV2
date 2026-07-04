package com.myapp.di

import android.content.Context
import com.myapp.data.local.preferences.UserPreferences
import com.myapp.data.sync.SyncManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserPreferences(
        @ApplicationContext context: Context,
    ): UserPreferences {
        return UserPreferences(context)
    }

    @Provides
    @Singleton
    fun provideSyncManager(
        @ApplicationContext context: Context,
        syncRepository: com.myapp.data.repository.SyncRepositoryImpl,
    ): SyncManager {
        return SyncManager(context, syncRepository)
    }
}
