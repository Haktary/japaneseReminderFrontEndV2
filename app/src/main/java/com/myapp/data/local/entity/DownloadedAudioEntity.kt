package com.myapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_audio")
data class DownloadedAudioEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "vocabulary_id")
    val vocabularyId: String,
    @ColumnInfo(name = "file_path")
    val filePath: String,
    @ColumnInfo(name = "file_name")
    val fileName: String,
    @ColumnInfo(name = "mime_type")
    val mimeType: String,
    @ColumnInfo(name = "file_size")
    val fileSize: Long,
    @ColumnInfo(name = "downloaded_at")
    val downloadedAt: Long = System.currentTimeMillis(),
)
