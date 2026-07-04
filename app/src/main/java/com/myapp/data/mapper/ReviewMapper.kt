package com.myapp.data.mapper

import com.myapp.data.local.entity.ReviewHistoryEntity
import com.myapp.data.remote.dto.ReviewHistoryResponse
import com.myapp.data.remote.dto.ReviewResultResponse
import com.myapp.domain.model.ReviewHistory
import com.myapp.domain.model.ReviewResult

fun ReviewResultResponse.toDomain(): ReviewResult = ReviewResult(
    vocabularyId = vocabularyId,
    result = result,
    easeFactorAfter = easeFactorAfter,
    intervalDaysAfter = intervalDaysAfter,
    repetitionsAfter = repetitionsAfter,
    nextReviewAt = nextReviewAt,
)

fun ReviewHistoryResponse.toDomain(): ReviewHistory = ReviewHistory(
    id = id,
    vocabularyId = vocabularyId,
    vocabularyWord = vocabularyWord,
    result = result,
    easeFactorAfter = easeFactorAfter,
    intervalDaysAfter = intervalDaysAfter,
    repetitionsAfter = repetitionsAfter,
    responseTimeMs = responseTimeMs,
    reviewSource = reviewSource,
    createdAt = createdAt,
)

fun ReviewHistoryResponse.toEntity(): ReviewHistoryEntity = ReviewHistoryEntity(
    id = id,
    vocabularyId = vocabularyId,
    result = result,
    easeFactorAfter = easeFactorAfter,
    intervalDaysAfter = intervalDaysAfter,
    repetitionsAfter = repetitionsAfter,
    responseTimeMs = responseTimeMs,
    reviewSource = reviewSource,
    isSynced = true,
)

fun ReviewHistoryEntity.toDomain(): ReviewHistory = ReviewHistory(
    id = id,
    vocabularyId = vocabularyId,
    vocabularyWord = "",
    result = result,
    easeFactorAfter = easeFactorAfter,
    intervalDaysAfter = intervalDaysAfter,
    repetitionsAfter = repetitionsAfter,
    responseTimeMs = responseTimeMs,
    reviewSource = reviewSource,
    createdAt = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", java.util.Locale.US)
        .format(java.util.Date(createdAt)),
)
