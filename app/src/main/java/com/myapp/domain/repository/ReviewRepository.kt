package com.myapp.domain.repository

import com.myapp.core.Result
import com.myapp.domain.model.DashboardStats
import com.myapp.domain.model.ReviewHistory
import com.myapp.domain.model.ReviewResult

interface ReviewRepository {
    suspend fun submitReview(vocabularyId: String, result: String, responseTimeMs: Int = 0, source: String = "notification"): Result<ReviewResult>
    suspend fun getReviewHistory(page: Int = 1, pageSize: Int = 20): Result<List<ReviewHistory>>
    suspend fun getDashboard(): Result<DashboardStats>
    suspend fun getLocalTodayReviews(): Int
}
