package com.myapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myapp.data.local.entity.UserProgressEntity

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE vocabulary_id = :vocabularyId")
    suspend fun getByVocabularyId(vocabularyId: String): UserProgressEntity?

    @Query("SELECT * FROM user_progress WHERE next_review_at <= :currentTime ORDER BY next_review_at ASC")
    suspend fun getDueForReview(currentTime: Double): List<UserProgressEntity>

    @Query("SELECT COUNT(*) FROM user_progress WHERE next_review_at <= :currentTime")
    suspend fun getDueCount(currentTime: Double): Int

    @Query("SELECT COUNT(*) FROM user_progress WHERE success_count > 0")
    suspend fun getLearnedCount(): Int

    @Query("SELECT COUNT(*) FROM user_progress")
    suspend fun getTotalCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: UserProgressEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<UserProgressEntity>)

    @Query("UPDATE user_progress SET success_count = :successCount, failure_count = :failureCount, ease_factor = :easeFactor, interval_days = :intervalDays, repetitions = :repetitions, next_review_at = :nextReviewAt, consecutive_correct = :consecutiveCorrect, consecutive_incorrect = :consecutiveIncorrect, total_reviews = :totalReviews, total_correct = :totalCorrect, total_incorrect = :totalIncorrect, average_response_time_ms = :averageResponseTimeMs, is_synced = 0 WHERE vocabulary_id = :vocabularyId")
    suspend fun updateProgress(vocabularyId: String, successCount: Int, failureCount: Int, easeFactor: Double, intervalDays: Int, repetitions: Int, nextReviewAt: Double, consecutiveCorrect: Int, consecutiveIncorrect: Int, totalReviews: Int, totalCorrect: Int, totalIncorrect: Int, averageResponseTimeMs: Double)

    @Query("DELETE FROM user_progress WHERE vocabulary_id = :vocabularyId")
    suspend fun deleteByVocabularyId(vocabularyId: String)

    @Query("DELETE FROM user_progress")
    suspend fun deleteAll()

    @Query("SELECT * FROM user_progress WHERE is_synced = 0")
    suspend fun getUnsynced(): List<UserProgressEntity>

    @Query("UPDATE user_progress SET is_synced = 1 WHERE vocabulary_id = :vocabularyId")
    suspend fun markSynced(vocabularyId: String)
}
