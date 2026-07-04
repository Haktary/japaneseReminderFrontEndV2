package com.myapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myapp.data.local.entity.PendingSyncEntity

@Dao
interface PendingSyncDao {
    @Query("SELECT * FROM pending_sync ORDER BY created_at ASC")
    suspend fun getAll(): List<PendingSyncEntity>

    @Query("SELECT COUNT(*) FROM pending_sync")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: PendingSyncEntity)

    @Query("DELETE FROM pending_sync WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE pending_sync SET retry_count = :retryCount, last_error = :lastError WHERE id = :id")
    suspend fun updateRetry(id: Long, retryCount: Int, lastError: String?)

    @Query("DELETE FROM pending_sync WHERE retry_count >= :maxRetries")
    suspend fun deleteAbandoned(maxRetries: Int = 10)

    @Query("SELECT * FROM pending_sync WHERE retry_count < :maxRetries ORDER BY created_at ASC")
    suspend fun getPending(maxRetries: Int = 10): List<PendingSyncEntity>
}
