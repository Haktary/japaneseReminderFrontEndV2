package com.myapp.data.mapper

import com.myapp.data.remote.dto.UserResponse
import com.myapp.domain.model.User

fun UserResponse.toDomain(): User = User(
    id = id,
    email = email,
    username = username,
    fullName = fullName,
    isActive = isActive,
    isVerified = isVerified,
    isSuperuser = isSuperuser,
    preferredLanguage = preferredLanguage,
    createdAt = createdAt,
)
