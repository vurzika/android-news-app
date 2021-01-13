package com.onramp.vurzika.newsapp.ui.settings.presenter

import android.content.Context
import android.content.SharedPreferences
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.onramp.vurzika.newsapp.R
import com.onramp.vurzika.newsapp.ui.base.mvp.BasePresenter
import com.onramp.vurzika.newsapp.ui.settings.SettingsContract
import com.onramp.vurzika.newsapp.workers.CheckLatestNewsWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
        @ApplicationContext val context: Context,
        private val userSettings: SharedPreferences
) : BasePresenter<SettingsContract.View>(), SettingsContract.Presenter, SharedPreferences.OnSharedPreferenceChangeListener {

    private companion object {
        const val WORD_NAME_NEWS_AUTO_REFRESH = "WORD_NAME_NEWS_AUTO_REFRESH"
    }

    private val settingsKeyBackgroundRefreshInterval by lazy {
        context.getString(R.string.setting_key_background_refresh_interval)
    }

    override fun onViewCreated() {
        userSettings.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        userSettings.unregisterOnSharedPreferenceChangeListener(this)

        super.onDestroy()
    }

    private fun reschedulePeriodicNotifications() {

        val currentRefreshInterval = getCurrentRefreshInterval()

        if (currentRefreshInterval == 0L) {
            WorkManager.getInstance(context).cancelUniqueWork(WORD_NAME_NEWS_AUTO_REFRESH)

            view?.notifyAutomaticNewsUpdatesCheckCancelled()
        } else {
            val periodicWork =
                    PeriodicWorkRequestBuilder<CheckLatestNewsWorker>(
                            currentRefreshInterval, TimeUnit.MINUTES
                    )
                            .setInitialDelay(currentRefreshInterval, TimeUnit.MINUTES)
                            .build()

            WorkManager.getInstance(context)
                    .enqueueUniquePeriodicWork(
                            WORD_NAME_NEWS_AUTO_REFRESH,
                            ExistingPeriodicWorkPolicy.REPLACE,
                            periodicWork
                    )

            view?.notifyAutomaticNewsUpdatesCheckScheduled()
        }
    }

    override fun onSharedPreferenceChanged(settings: SharedPreferences?, key: String?) {
        if (key == settingsKeyBackgroundRefreshInterval) {
            reschedulePeriodicNotifications()
        }
    }

    private fun getCurrentRefreshInterval(): Long {
        return try {
            userSettings.getString(settingsKeyBackgroundRefreshInterval, "0")?.toLong() ?: 0
        } catch (error: NumberFormatException) {
            0
        }
    }
}