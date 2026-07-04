package com.myapp.core

import com.myapp.BuildConfig

object Constants {
    const val API_BASE_URL = BuildConfig.API_BASE_URL
    const val DEFAULT_PAGE_SIZE = 20
    const val MAX_PAGE_SIZE = 100
    const val CONNECT_TIMEOUT_SECONDS = 30L
    const val READ_TIMEOUT_SECONDS = 30L
    const val WRITE_TIMEOUT_SECONDS = 30L
}
