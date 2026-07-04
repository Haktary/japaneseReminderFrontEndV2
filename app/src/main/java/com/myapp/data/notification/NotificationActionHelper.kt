package com.myapp.data.notification

import com.myapp.data.repository.ReviewRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationActionHelper @Inject constructor(
    private val reviewRepository: ReviewRepositoryImpl,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun markReviewed(vocabularyId: String, result: String) {
        scope.launch {
            reviewRepository.submitReview(
                vocabularyId = vocabularyId,
                result = result,
                responseTimeMs = 0,
                source = "notification",
            )
        }
    }

    fun snooze(vocabularyId: String) {
    }
}
