package com.myapp.domain.repository

import com.myapp.core.Result
import com.myapp.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, username: String, password: String, fullName: String? = null): Result<User>
    suspend fun refreshToken(): Result<String>
    suspend fun getCurrentUser(): Result<User>
    suspend fun logout()
    suspend fun isLoggedIn(): Boolean
    suspend fun getAccessToken(): String?
}
