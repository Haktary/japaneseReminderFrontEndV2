package com.myapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val detail: String? = null,
    val message: String? = null,
    val error: String? = null,
)

@Serializable
data class PaginationParams(
    val page: Int = 1,
    @SerialName("page_size")
    val pageSize: Int = 20,
)
