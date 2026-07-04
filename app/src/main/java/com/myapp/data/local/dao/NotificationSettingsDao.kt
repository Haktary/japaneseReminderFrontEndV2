package com.myapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myapp.data.local.entity.NotificationSettingsEntity

@Dao
interface NotificationSettingsDao {
    @Query("SELECT * FROM notification_settings WHERE id = :id")
    suspend fun getById(id: String): NotificationSettingsEntity?

    @Query("SELECT * FROM notification_settings WHERE user_id = :userId LIMIT 1")
    suspend fun getByUserId(userId: String): NotificationSettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: NotificationSettingsEntity)

    @Query("DELETE FROM notification_settings WHERE id = :id")
    suspend fun deleteById(id: String)
}
