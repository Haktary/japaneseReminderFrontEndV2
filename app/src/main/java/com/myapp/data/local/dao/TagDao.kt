package com.myapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myapp.data.local.entity.TagEntity

@Dao
interface TagDao {
    @Query("SELECT * FROM tags ORDER BY name ASC")
    suspend fun getAll(): List<TagEntity>

    @Query("SELECT * FROM tags WHERE id = :id")
    suspend fun getById(id: String): TagEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TagEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TagEntity)

    @Query("DELETE FROM tags")
    suspend fun deleteAll()
}
