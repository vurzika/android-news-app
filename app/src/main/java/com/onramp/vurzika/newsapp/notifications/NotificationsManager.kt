package com.onramp.vurzika.newsapp.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.onramp.vurzika.newsapp.R
import com.onramp.vurzika.newsapp.ui.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsManager @Inject constructor(
        @ApplicationContext private val context: Context
) {

    private companion object {
        const val NOTIFICATION_CHANNEL_ID = "news-app-notification-channel"
        const val NOTIFICATION_ID = 1
    }

    init {
        registerNotificationChannel()
    }

    private fun registerNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notifications_channel_name)
            val descriptionText = context.getString(R.string.notifications_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNewNewsAvailableNotification() {
        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_rocket)
                .setContentTitle(context.getString(R.string.notifications_new_news_available_title))
                .setContentText(context.getString(R.string.notifications_new_news_available_descriptions))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(createOpenAppOnTapIntent())

        // send notification
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createOpenAppOnTapIntent(): PendingIntent? {
        val notifyIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        return PendingIntent.getActivity(
                context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}