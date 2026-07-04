package com.myapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_sync")
data class PendingSyncEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "entity_type")
    val entityType: String,
    @ColumnInfo(name = "entity_id")
    val entityId: String,
    val action: String,
    val payload: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "retry_count")
    val retryCount: Int = 0,
    @ColumnInfo(name = "last_error")
    val lastError: String? = null,
)
