package com.myapp.domain.model

data class User(
    val id: String,
    val email: String,
    val username: String,
    val fullName: String? = null,
    val isActive: Boolean = true,
    val isVerified: Boolean = false,
    val isSuperuser: Boolean = false,
    val preferredLanguage: String = "fr",
    val createdAt: String = "",
)
