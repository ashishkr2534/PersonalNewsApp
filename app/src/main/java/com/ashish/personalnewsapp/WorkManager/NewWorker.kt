//package com.ashish.personalnewsapp.WorkManager

package com.ashish.personalnewsapp.WorkManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ashish.personalnewsapp.Db.NewsRepository
import com.ashish.personalnewsapp.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class NewsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: NewsRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val apiKey = "290fcafa8eaa4940a318b0ee0fa72254"
            repository.refreshNews(apiKey)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}

fun showNotification(context: Context, title: String, message: String) {
    val channelId = "news_update_channel"
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "News Updates",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setContentTitle(title)
        .setContentText(message)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(System.currentTimeMillis().toInt(), notification)
}
