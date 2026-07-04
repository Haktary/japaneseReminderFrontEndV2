package com.myapp.domain.repository

import com.myapp.core.Result

interface SyncRepository {
    suspend fun syncAll(): Result<Unit>
    suspend fun syncProgress(): Result<Unit>
    suspend fun syncVocabulary(): Result<Unit>
    suspend fun enqueueSync(entityType: String, entityId: String, action: String, payload: String)
    suspend fun processPendingSyncs(): Result<Int>
    suspend fun getLastSyncTimestamp(): Long
}
