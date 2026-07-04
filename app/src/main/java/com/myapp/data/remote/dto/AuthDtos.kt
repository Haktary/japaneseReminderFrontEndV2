package com.myapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
)

@Serializable
data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String,
    @SerialName("full_name")
    val fullName: String? = null,
)

@Serializable
data class TokenResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("token_type")
    val tokenType: String = "bearer",
)

@Serializable
data class RefreshRequest(
    @SerialName("refresh_token")
    val refreshToken: String,
)

@Serializable
data class UserResponse(
    val id: String,
    val email: String,
    val username: String,
    @SerialName("full_name")
    val fullName: String? = null,
    @SerialName("is_active")
    val isActive: Boolean = true,
    @SerialName("is_verified")
    val isVerified: Boolean = false,
    @SerialName("is_superuser")
    val isSuperuser: Boolean = false,
    @SerialName("preferred_language")
    val preferredLanguage: String = "fr",
    @SerialName("created_at")
    val createdAt: String = "",
)

@Serializable
data class AuthResponse(
    val user: UserResponse,
    val tokens: TokenResponse,
)
