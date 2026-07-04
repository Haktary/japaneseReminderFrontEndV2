package com.myapp.data.repository

import com.myapp.core.Result
import com.myapp.data.local.preferences.UserPreferences
import com.myapp.data.mapper.toDomain
import com.myapp.data.remote.api.JapaneseApi
import com.myapp.data.remote.dto.LoginRequest
import com.myapp.data.remote.dto.RefreshRequest
import com.myapp.data.remote.dto.RegisterRequest
import com.myapp.domain.repository.AuthRepository
import com.myapp.domain.model.User
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: JapaneseApi,
    private val userPreferences: UserPreferences,
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> = Result.wrap {
        val response = api.login(LoginRequest(email, password))
        if (response.isSuccessful) {
            val authResponse = response.body() ?: throw Exception("Empty response")
            userPreferences.saveTokens(
                accessToken = authResponse.tokens.accessToken,
                refreshToken = authResponse.tokens.refreshToken,
            )
            userPreferences.saveUserInfo(
                userId = authResponse.user.id,
                email = authResponse.user.email,
                username = authResponse.user.username,
            )
            authResponse.user.toDomain()
        } else {
            val errorBody = response.errorBody()?.string() ?: "Login failed"
            throw Exception(errorBody)
        }
    }

    override suspend fun register(email: String, username: String, password: String, fullName: String?): Result<User> = Result.wrap {
        val response = api.register(RegisterRequest(email, username, password, fullName))
        if (response.isSuccessful) {
            val authResponse = response.body() ?: throw Exception("Empty response")
            userPreferences.saveTokens(
                accessToken = authResponse.tokens.accessToken,
                refreshToken = authResponse.tokens.refreshToken,
            )
            userPreferences.saveUserInfo(
                userId = authResponse.user.id,
                email = authResponse.user.email,
                username = authResponse.user.username,
            )
            authResponse.user.toDomain()
        } else {
            val errorBody = response.errorBody()?.string() ?: "Registration failed"
            throw Exception(errorBody)
        }
    }

    override suspend fun refreshToken(): Result<String> = Result.wrap {
        val currentRefreshToken = userPreferences.refreshToken.first()
            ?: throw Exception("No refresh token available")

        val response = api.refreshToken(RefreshRequest(currentRefreshToken))
        if (response.isSuccessful) {
            val tokenResponse = response.body() ?: throw Exception("Empty response")
            userPreferences.saveTokens(
                accessToken = tokenResponse.accessToken,
                refreshToken = tokenResponse.refreshToken,
            )
            tokenResponse.accessToken
        } else {
            throw Exception("Token refresh failed")
        }
    }

    override suspend fun getCurrentUser(): Result<User> = Result.wrap {
        val response = api.getCurrentUser()
        if (response.isSuccessful) {
            response.body()?.toDomain() ?: throw Exception("Empty response")
        } else {
            throw Exception("Failed to get user")
        }
    }

    override suspend fun logout() {
        userPreferences.clearAll()
    }

    override suspend fun isLoggedIn(): Boolean {
        return userPreferences.accessToken.first() != null
    }

    override suspend fun getAccessToken(): String? {
        return userPreferences.accessToken.first()
    }
}
