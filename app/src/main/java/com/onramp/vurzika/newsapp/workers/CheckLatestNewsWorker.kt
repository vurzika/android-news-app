package com.onramp.vurzika.newsapp.workers

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.onramp.vurzika.newsapp.notifications.NotificationsManager
import com.onramp.vurzika.newsapp.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckLatestNewsWorker @WorkerInject constructor(
        @Assisted context: Context,
        @Assisted workerParameters: WorkerParameters,
        private val repository: NewsRepository,
        private val notificationsManager: NotificationsManager
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        try {
            val latestNewsDate = repository.getLatestAvailableNewsDate()
            val lastRetrievedNewsDate = repository.getLastRetrievedNewsDate()

            latestNewsDate?.let {
                if (lastRetrievedNewsDate == null || it.after(lastRetrievedNewsDate)) {
                    notificationsManager.showNewNewsAvailableNotification()
                }
            }

            Result.success()
        } catch (error: Exception) {

            Result.failure()
        }
    }
}