package com.myapp.data.repository

import com.myapp.core.Result
import com.myapp.data.local.dao.ReviewHistoryDao
import com.myapp.data.local.dao.UserProgressDao
import com.myapp.data.local.dao.VocabularyDao
import com.myapp.data.mapper.toDomain
import com.myapp.data.remote.api.JapaneseApi
import com.myapp.data.remote.dto.ReviewSubmitRequest
import com.myapp.domain.repository.ReviewRepository
import com.myapp.domain.model.DashboardStats
import com.myapp.domain.model.ReviewHistory
import com.myapp.domain.model.ReviewResult
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRepositoryImpl @Inject constructor(
    private val api: JapaneseApi,
    private val reviewHistoryDao: ReviewHistoryDao,
    private val userProgressDao: UserProgressDao,
    private val vocabularyDao: VocabularyDao,
) : ReviewRepository {

    override suspend fun submitReview(vocabularyId: String, result: String, responseTimeMs: Int, source: String): Result<ReviewResult> = Result.wrap {
        val remoteResult = try {
            val response = api.submitReview(
                ReviewSubmitRequest(
                    vocabularyId = vocabularyId,
                    result = result,
                    responseTimeMs = responseTimeMs,
                    source = source,
                )
            )
            if (response.isSuccessful) {
                response.body()?.toDomain()
            } else null
        } catch (e: Exception) {
            null
        }

        if (remoteResult != null) {
            vocabularyDao.updateProgress(
                id = vocabularyId,
                successCount = if (result == "correct" || result == "hard" || result == "easy") 1 else 0,
                failureCount = if (result == "incorrect" || result == "again") 1 else 0,
                easeFactor = remoteResult.easeFactorAfter,
                intervalDays = remoteResult.intervalDaysAfter,
                repetitions = remoteResult.repetitionsAfter,
                nextReviewAt = remoteResult.nextReviewAt,
            )

            reviewHistoryDao.insert(
                com.myapp.data.local.entity.ReviewHistoryEntity(
                    id = UUID.randomUUID().toString(),
                    vocabularyId = vocabularyId,
                    result = result,
                    easeFactorAfter = remoteResult.easeFactorAfter,
                    intervalDaysAfter = remoteResult.intervalDaysAfter,
                    repetitionsAfter = remoteResult.repetitionsAfter,
                    responseTimeMs = responseTimeMs,
                    reviewSource = source,
                    isSynced = true,
                )
            )

            remoteResult
        } else {
            val localResult = ReviewResult(
                vocabularyId = vocabularyId,
                result = result,
                easeFactorAfter = 2.5,
                intervalDaysAfter = 1,
                repetitionsAfter = 1,
                nextReviewAt = (System.currentTimeMillis() / 1000.0) + 86400,
            )

            reviewHistoryDao.insert(
                com.myapp.data.local.entity.ReviewHistoryEntity(
                    id = UUID.randomUUID().toString(),
                    vocabularyId = vocabularyId,
                    result = result,
                    easeFactorAfter = localResult.easeFactorAfter,
                    intervalDaysAfter = localResult.intervalDaysAfter,
                    repetitionsAfter = localResult.repetitionsAfter,
                    responseTimeMs = responseTimeMs,
                    reviewSource = source,
                    isSynced = false,
                )
            )

            localResult
        }
    }

    override suspend fun getReviewHistory(page: Int, pageSize: Int): Result<List<ReviewHistory>> = Result.wrap {
        try {
            val response = api.getReviewHistory(page, pageSize)
            if (response.isSuccessful) {
                val paginated = response.body() ?: throw Exception("Empty response")
                reviewHistoryDao.insertAll(paginated.items.map {
                    com.myapp.data.mapper.toEntity(it)
                })
                paginated.items.map { it.toDomain() }
            } else {
                throw Exception("Failed to fetch review history")
            }
        } catch (e: Exception) {
            val offset = (page - 1) * pageSize
            reviewHistoryDao.getHistory(pageSize, offset).map { it.toDomain() }
        }
    }

    override suspend fun getDashboard(): Result<DashboardStats> = Result.wrap {
        val response = api.getDashboard()
        if (response.isSuccessful) {
            response.body()?.toDomain() ?: DashboardStats()
        } else {
            DashboardStats(
                totalVocabulary = vocabularyDao.getCount(),
                wordsLearned = userProgressDao.getLearnedCount(),
                wordsRemaining = vocabularyDao.getCount() - userProgressDao.getLearnedCount(),
                todayReviews = reviewHistoryDao.getTodayReviewCount(
                    java.util.Calendar.getInstance().apply {
                        set(java.util.Calendar.HOUR_OF_DAY, 0)
                        set(java.util.Calendar.MINUTE, 0)
                        set(java.util.Calendar.SECOND, 0)
                        set(java.util.Calendar.MILLISECOND, 0)
                    }.timeInMillis
                ),
            )
        }
    }

    override suspend fun getLocalTodayReviews(): Int {
        val startOfDay = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.HOUR_OF_DAY, 0)
            set(java.util.Calendar.MINUTE, 0)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }.timeInMillis
        return reviewHistoryDao.getTodayReviewCount(startOfDay)
    }
}
