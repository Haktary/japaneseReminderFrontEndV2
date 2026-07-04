package com.myapp.data.repository

import com.myapp.core.Result
import com.myapp.data.local.dao.PendingSyncDao
import com.myapp.data.local.dao.ReviewHistoryDao
import com.myapp.data.local.dao.UserProgressDao
import com.myapp.data.local.entity.PendingSyncEntity
import com.myapp.data.remote.api.JapaneseApi
import com.myapp.data.remote.dto.SyncProgressItem
import com.myapp.data.remote.dto.SyncProgressRequest
import com.myapp.domain.repository.SyncRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncRepositoryImpl @Inject constructor(
    private val api: JapaneseApi,
    private val pendingSyncDao: PendingSyncDao,
    private val reviewHistoryDao: ReviewHistoryDao,
    private val userProgressDao: UserProgressDao,
) : SyncRepository {

    override suspend fun syncAll(): Result<Unit> = Result.wrap {
        syncProgress()
        syncVocabulary()
    }

    override suspend fun syncProgress(): Result<Unit> = Result.wrap {
        val unsyncedProgress = userProgressDao.getUnsynced()
        if (unsyncedProgress.isNotEmpty()) {
            val items = unsyncedProgress.map {
                SyncProgressItem(
                    vocabularyId = it.vocabularyId,
                    successCount = it.successCount,
                    failureCount = it.failureCount,
                    easeFactor = it.easeFactor,
                    intervalDays = it.intervalDays,
                    repetitions = it.repetitions,
                    nextReviewAt = it.nextReviewAt,
                )
            }

            val response = api.syncProgress(SyncProgressRequest(items))
            if (response.isSuccessful) {
                unsyncedProgress.forEach {
                    userProgressDao.markSynced(it.vocabularyId)
                }
            }
        }

        val unsyncedReviews = reviewHistoryDao.getUnsynced()
        if (unsyncedReviews.isNotEmpty()) {
            try {
                val requestItems = unsyncedReviews.map {
                    com.myapp.data.remote.dto.ReviewSubmitRequest(
                        vocabularyId = it.vocabularyId,
                        result = it.result,
                        responseTimeMs = it.responseTimeMs,
                        source = it.reviewSource,
                    )
                }
                val response = api.syncReviews(requestItems)
                if (response.isSuccessful) {
                    unsyncedReviews.forEach {
                        reviewHistoryDao.markSynced(it.id)
                    }
                }
            } catch (_: Exception) {
            }
        }

        processPendingSyncs()
    }

    override suspend fun syncVocabulary(): Result<Unit> = Result.wrap {
    }

    override suspend fun enqueueSync(entityType: String, entityId: String, action: String, payload: String) {
        pendingSyncDao.insert(
            PendingSyncEntity(
                entityType = entityType,
                entityId = entityId,
                action = action,
                payload = payload,
            )
        )
    }

    override suspend fun processPendingSyncs(): Result<Int> = Result.wrap {
        val pending = pendingSyncDao.getPending()
        var processed = 0

        for (item in pending) {
            try {
                when (item.entityType) {
                    "review" -> {
                        val progress = userProgressDao.getByVocabularyId(item.entityId)
                        if (progress != null) {
                            userProgressDao.markSynced(item.entityId)
                        }
                    }
                }
                pendingSyncDao.deleteById(item.id)
                processed++
            } catch (e: Exception) {
                pendingSyncDao.updateRetry(item.id, item.retryCount + 1, e.message)
            }
        }

        processed
    }

    override suspend fun getLastSyncTimestamp(): Long {
        return System.currentTimeMillis()
    }
}
