package com.myapp.domain.model

data class ReviewResult(
    val vocabularyId: String,
    val result: String,
    val easeFactorAfter: Double,
    val intervalDaysAfter: Int,
    val repetitionsAfter: Int,
    val nextReviewAt: Double,
)
