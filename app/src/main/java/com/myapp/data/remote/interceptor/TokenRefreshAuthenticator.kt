package com.myapp.data.remote.interceptor

import com.myapp.data.local.preferences.UserPreferences
import com.myapp.data.remote.api.JapaneseApi
import com.myapp.data.remote.dto.RefreshRequest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import javax.inject.Provider
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRefreshAuthenticator @Inject constructor(
    private val userPreferences: UserPreferences,
    private val japaneseApi: Provider<JapaneseApi>,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.header("Authorization") == null) {
            return null
        }

        val refreshToken = runBlocking {
            userPreferences.refreshToken.first()
        }

        if (refreshToken.isNullOrBlank()) {
            clearTokens()
            return null
        }

        val newToken = try {
            val refreshResponse = runBlocking {
                japaneseApi.get().refreshToken(RefreshRequest(refreshToken))
            }
            if (refreshResponse.isSuccessful) {
                val tokenResponse = refreshResponse.body() ?: return null
                runBlocking {
                    userPreferences.saveTokens(
                        accessToken = tokenResponse.accessToken,
                        refreshToken = tokenResponse.refreshToken,
                    )
                }
                tokenResponse.accessToken
            } else {
                clearTokens()
                return null
            }
        } catch (e: Exception) {
            clearTokens()
            return null
        }

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newToken")
            .build()
    }

    private fun clearTokens() {
        runBlocking {
            userPreferences.saveTokens(
                accessToken = null,
                refreshToken = null,
            )
        }
    }
}
