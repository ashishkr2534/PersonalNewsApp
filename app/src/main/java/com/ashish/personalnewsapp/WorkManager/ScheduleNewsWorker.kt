package com.ashish.personalnewsapp.WorkManager

/**
 * Created by Ashish Kr on 03,May,2025
 */

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

//fun scheduleNewsWorker(context: Context) {
//    val constraints = Constraints.Builder()
//        .setRequiredNetworkType(NetworkType.CONNECTED)
//        .build()
//
//    val request = PeriodicWorkRequestBuilder<NewsWorker>(1, TimeUnit.MINUTES)
//        .setConstraints(constraints)
//        .build()
//
//    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
//        "news_fetch_worker",
//        ExistingPeriodicWorkPolicy.KEEP,
//        request
//    )
//}

fun scheduleNewsWorker(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val request = PeriodicWorkRequestBuilder<NewsWorker>(1, TimeUnit.MINUTES)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "news_fetch_worker",
        ExistingPeriodicWorkPolicy.KEEP,
        request
    )
}
