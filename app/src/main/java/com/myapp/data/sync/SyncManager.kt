package com.myapp.data.sync

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.myapp.data.repository.SyncRepositoryImpl
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val syncRepository: SyncRepositoryImpl,
) {

    companion object {
        private const val SYNC_WORK_NAME = "sync_all"
        private const val SYNC_IMMEDIATE_WORK_NAME = "sync_immediate"
    }

    fun schedulePeriodicSync(intervalMinutes: Long = 15) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .setInitialDelay(intervalMinutes, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                SYNC_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                syncRequest,
            )
    }

    fun requestImmediateSync(source: String = "manual") {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .setInputData(workDataOf("source" to source))
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                SYNC_IMMEDIATE_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                syncRequest,
            )
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    suspend fun performSync(): Result<Unit> {
        if (!isNetworkAvailable()) {
            return Result.success(Unit)
        }
        return syncRepository.syncAll()
    }
}
