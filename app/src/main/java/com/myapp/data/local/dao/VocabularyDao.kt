package com.myapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myapp.data.local.entity.VocabularyEntity

@Dao
interface VocabularyDao {
    @Query("""
        SELECT * FROM vocabulary
        WHERE (:query IS NULL OR hiragana LIKE '%' || :query || '%' OR kanji LIKE '%' || :query || '%' OR romaji LIKE '%' || :query || '%' OR french_translation LIKE '%' || :query || '%')
        AND (:jlptLevel IS NULL OR jlpt_level = :jlptLevel)
        ORDER BY jlpt_level, hiragana
        LIMIT :limit OFFSET :offset
    """)
    suspend fun getVocabulary(query: String? = null, jlptLevel: String? = null, limit: Int = 20, offset: Int = 0): List<VocabularyEntity>

    @Query("SELECT * FROM vocabulary WHERE id = :id")
    suspend fun getById(id: String): VocabularyEntity?

    @Query("SELECT * FROM vocabulary WHERE is_active = 1 AND (next_review_at IS NULL OR next_review_at <= :currentTime) ORDER BY next_review_at ASC LIMIT :limit")
    suspend fun getDueForReview(currentTime: Double, limit: Int = 20): List<VocabularyEntity>

    @Query("SELECT * FROM vocabulary WHERE hiragana LIKE '%' || :query || '%' OR kanji LIKE '%' || :query || '%' OR romaji LIKE '%' || :query || '%' OR french_translation LIKE '%' || :query || '%'")
    suspend fun search(query: String): List<VocabularyEntity>

    @Query("SELECT COUNT(*) FROM vocabulary")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<VocabularyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: VocabularyEntity)

    @Query("UPDATE vocabulary SET success_count = :successCount, failure_count = :failureCount, ease_factor = :easeFactor, interval_days = :intervalDays, repetitions = :repetitions, next_review_at = :nextReviewAt, is_synced = 0 WHERE id = :id")
    suspend fun updateProgress(id: String, successCount: Int, failureCount: Int, easeFactor: Double, intervalDays: Int, repetitions: Int, nextReviewAt: Double?)

    @Query("DELETE FROM vocabulary WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM vocabulary")
    suspend fun deleteAll()

    @Query("SELECT * FROM vocabulary WHERE is_active = 1 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomActive(): VocabularyEntity?
}
