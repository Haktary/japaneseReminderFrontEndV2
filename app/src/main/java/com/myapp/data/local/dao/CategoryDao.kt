package com.myapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myapp.data.local.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name ASC")
    suspend fun getAll(): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getById(id: String): CategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CategoryEntity)

    @Query("DELETE FROM categories")
    suspend fun deleteAll()
}
