package com.myapp.core

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable, val message: String? = null) : Result<Nothing>()

    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error

    fun getOrNull(): T? = when (this) {
        is Success -> data
        is Error -> null
    }

    fun <R> map(transform: (T) -> R): Result<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> Error(exception, message)
    }

    companion object {
        fun <T> success(data: T): Result<T> = Success(data)
        fun error(exception: Throwable, message: String? = null): Result<Nothing> = Error(exception, message)

        suspend fun <T> wrap(block: suspend () -> T): Result<T> {
            return try {
                Success(block())
            } catch (e: Exception) {
                Error(e, e.message)
            }
        }
    }
}
