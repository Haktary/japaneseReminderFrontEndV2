package com.myapp.domain.model

data class ReviewHistory(
    val id: String,
    val vocabularyId: String,
    val vocabularyWord: String,
    val result: String,
    val easeFactorAfter: Double,
    val intervalDaysAfter: Int,
    val repetitionsAfter: Int,
    val responseTimeMs: Int = 0,
    val reviewSource: String = "notification",
    val createdAt: String = "",
)
