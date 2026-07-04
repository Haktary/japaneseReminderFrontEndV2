package com.myapp.data.remote.api

import com.myapp.data.remote.dto.AuthResponse
import com.myapp.data.remote.dto.DashboardResponse
import com.myapp.data.remote.dto.DueVocabularyResponse
import com.myapp.data.remote.dto.LoginRequest
import com.myapp.data.remote.dto.NotificationPayloadResponse
import com.myapp.data.remote.dto.NotificationSettingsResponse
import com.myapp.data.remote.dto.NotificationSettingsUpdateRequest
import com.myapp.data.remote.dto.PaginatedReviewHistoryResponse
import com.myapp.data.remote.dto.PaginatedVocabularyResponse
import com.myapp.data.remote.dto.RefreshRequest
import com.myapp.data.remote.dto.RegisterRequest
import com.myapp.data.remote.dto.ReviewHistoryResponse
import com.myapp.data.remote.dto.ReviewResultResponse
import com.myapp.data.remote.dto.ReviewSubmitRequest
import com.myapp.data.remote.dto.SyncProgressRequest
import com.myapp.data.remote.dto.SyncProgressResponse
import com.myapp.data.remote.dto.SyncResponse
import com.myapp.data.remote.dto.TokenResponse
import com.myapp.data.remote.dto.UserResponse
import com.myapp.data.remote.dto.VocabularyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface JapaneseApi {

    @POST("api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/v1/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("api/v1/auth/refresh")
    suspend fun refreshToken(@Body request: RefreshRequest): Response<TokenResponse>

    @GET("api/v1/auth/me")
    suspend fun getCurrentUser(): Response<UserResponse>

    @GET("api/v1/vocabulary")
    suspend fun getVocabulary(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 20,
        @Query("query") query: String? = null,
        @Query("jlpt_level") jlptLevel: String? = null,
    ): Response<PaginatedVocabularyResponse>

    @GET("api/v1/vocabulary/{id}")
    suspend fun getVocabularyById(@Path("id") id: String): Response<VocabularyResponse>

    @GET("api/v1/vocabulary/due")
    suspend fun getDueVocabulary(@Query("limit") limit: Int = 20): Response<DueVocabularyResponse>

    @POST("api/v1/reviews")
    suspend fun submitReview(@Body request: ReviewSubmitRequest): Response<ReviewResultResponse>

    @GET("api/v1/reviews")
    suspend fun getReviewHistory(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 20,
    ): Response<PaginatedReviewHistoryResponse>

    @GET("api/v1/reviews/{id}")
    suspend fun getReviewById(@Path("id") id: String): Response<ReviewHistoryResponse>

    @GET("api/v1/reviews/dashboard")
    suspend fun getDashboard(): Response<DashboardResponse>

    @GET("api/v1/notifications/settings")
    suspend fun getNotificationSettings(): Response<NotificationSettingsResponse>

    @PUT("api/v1/notifications/settings")
    suspend fun updateNotificationSettings(@Body request: NotificationSettingsUpdateRequest): Response<NotificationSettingsResponse>

    @GET("api/v1/notifications/next")
    suspend fun getNextNotification(): Response<NotificationPayloadResponse>

    @POST("api/v1/sync/progress")
    suspend fun syncProgress(@Body request: SyncProgressRequest): Response<SyncProgressResponse>

    @POST("api/v1/sync/vocabulary")
    suspend fun syncVocabulary(@Query("since") since: Long? = null): Response<PaginatedVocabularyResponse>

    @POST("api/v1/sync/reviews")
    suspend fun syncReviews(@Body request: List<ReviewSubmitRequest>): Response<SyncResponse>
}
