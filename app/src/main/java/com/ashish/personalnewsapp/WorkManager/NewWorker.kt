//package com.ashish.personalnewsapp.WorkManager
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.os.Build
//import android.util.Log
//import androidx.core.app.NotificationCompat
//import androidx.hilt.work.HiltWorker
//import androidx.work.CoroutineWorker
//import androidx.work.Worker
//import androidx.work.WorkerFactory
//import androidx.work.WorkerParameters
//import com.ashish.personalnewsapp.Db.NewsRepository
//import com.ashish.personalnewsapp.R
//import dagger.assisted.Assisted
//import dagger.assisted.AssistedInject
//import javax.inject.Inject
//
//@HiltWorker
//class NewsWorker @AssistedInject constructor(
//    @Assisted context: Context,
//    @Assisted workerParams: WorkerParameters,
//    private val repository: NewsRepository
//) : CoroutineWorker(context, workerParams) {
//
//    override suspend fun doWork(): Result {
//        return try {
//            Log.d("NewsWorker", "doWork called")
//            val apiKey = "290fcafa8eaa4940a318b0ee0fa72254" // Secure this appropriately in production
//            repository.refreshNews(apiKey)
//            //showNotification(applicationContext, "News Updated", "Top headlines just refreshed.")
//            Result.success()
//
//        } catch (e: Exception) {
//            Result.retry()
//        }
//    }
//}

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
//
//class NewsWorker @AssistedInject constructor(
//    @Assisted context: Context,
//    @Assisted workerParams: WorkerParameters,
//    private val repository: NewsRepository
//) : CoroutineWorker(context, workerParams) {
//
//    override suspend fun doWork(): Result {
//        return try {
//            // Your worker logic here
//            val apiKey = "290fcafa8eaa4940a318b0ee0fa72254" // Secure this appropriately in production
//            repository.refreshNews(apiKey)
//            Result.success()
//        } catch (e: Exception) {
//            Result.retry()
//        }
//    }
//}

class NewsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: NewsRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val apiKey = "290fcafa8eaa4940a318b0ee0fa72254" // Secure this properly
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
        .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your icon
        .setAutoCancel(true)
        .build()

    notificationManager.notify(System.currentTimeMillis().toInt(), notification)
}
