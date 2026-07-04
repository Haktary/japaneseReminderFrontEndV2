package com.myapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myapp.data.local.entity.ReviewHistoryEntity

@Dao
interface ReviewHistoryDao {
    @Query("SELECT * FROM review_history ORDER BY created_at DESC LIMIT :limit OFFSET :offset")
    suspend fun getHistory(limit: Int = 20, offset: Int = 0): List<ReviewHistoryEntity>

    @Query("SELECT * FROM review_history WHERE vocabulary_id = :vocabularyId ORDER BY created_at DESC")
    suspend fun getByVocabularyId(vocabularyId: String): List<ReviewHistoryEntity>

    @Query("SELECT COUNT(*) FROM review_history WHERE created_at >= :startOfDay")
    suspend fun getTodayReviewCount(startOfDay: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(review: ReviewHistoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reviews: List<ReviewHistoryEntity>)

    @Query("UPDATE review_history SET is_synced = 1 WHERE id = :id")
    suspend fun markSynced(id: String)

    @Query("SELECT * FROM review_history WHERE is_synced = 0 ORDER BY created_at ASC")
    suspend fun getUnsynced(): List<ReviewHistoryEntity>

    @Query("DELETE FROM review_history WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM review_history")
    suspend fun deleteAll()
}
