package com.myapp.data.mapper

import com.myapp.data.local.entity.UserProgressEntity
import com.myapp.domain.model.UserProgress

fun UserProgressEntity.toDomain(): UserProgress = UserProgress(
    id = id,
    vocabularyId = vocabularyId,
    successCount = successCount,
    failureCount = failureCount,
    easeFactor = easeFactor,
    intervalDays = intervalDays,
    repetitions = repetitions,
    nextReviewAt = nextReviewAt,
    consecutiveCorrect = consecutiveCorrect,
    consecutiveIncorrect = consecutiveIncorrect,
    totalReviews = totalReviews,
    totalCorrect = totalCorrect,
    totalIncorrect = totalIncorrect,
    averageResponseTimeMs = averageResponseTimeMs,
    lastSyncAt = lastSyncAt,
)

fun UserProgress.toEntity(): UserProgressEntity = UserProgressEntity(
    id = id,
    vocabularyId = vocabularyId,
    successCount = successCount,
    failureCount = failureCount,
    easeFactor = easeFactor,
    intervalDays = intervalDays,
    repetitions = repetitions,
    nextReviewAt = nextReviewAt,
    consecutiveCorrect = consecutiveCorrect,
    consecutiveIncorrect = consecutiveIncorrect,
    totalReviews = totalReviews,
    totalCorrect = totalCorrect,
    totalIncorrect = totalIncorrect,
    averageResponseTimeMs = averageResponseTimeMs,
    lastSyncAt = lastSyncAt,
)
