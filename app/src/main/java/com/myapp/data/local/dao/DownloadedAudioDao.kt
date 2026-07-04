package com.myapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myapp.data.local.entity.DownloadedAudioEntity

@Dao
interface DownloadedAudioDao {
    @Query("SELECT * FROM downloaded_audio WHERE vocabulary_id = :vocabularyId LIMIT 1")
    suspend fun getByVocabularyId(vocabularyId: String): DownloadedAudioEntity?

    @Query("SELECT * FROM downloaded_audio ORDER BY downloaded_at DESC")
    suspend fun getAll(): List<DownloadedAudioEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(audio: DownloadedAudioEntity)

    @Query("DELETE FROM downloaded_audio WHERE vocabulary_id = :vocabularyId")
    suspend fun deleteByVocabularyId(vocabularyId: String)

    @Query("DELETE FROM downloaded_audio WHERE id = :id")
    suspend fun deleteById(id: String)
}
