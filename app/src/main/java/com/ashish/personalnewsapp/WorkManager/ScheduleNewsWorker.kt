package com.ashish.personalnewsapp.WorkManager

/**
 * Created by Ashish Kr on 03,May,2025
 */

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
//import org.chromium.base.Log
import java.util.concurrent.TimeUnit

fun scheduleNewsWorker(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val request = PeriodicWorkRequestBuilder<NewsWorker>(15, TimeUnit.MINUTES)
        .setConstraints(constraints)
        .build()

    val workManager = WorkManager.getInstance(context)

    Log.d("WorkScheduler", "Enqueuing periodic work: news_fetch_worker")

    workManager.enqueueUniquePeriodicWork(
        "news_fetch_worker",
        ExistingPeriodicWorkPolicy.KEEP,
        request
    )

    workManager.getWorkInfoByIdLiveData(request.id).observeForever { workInfo ->
        workInfo?.let {
            Log.d("WorkStatus", "Worker state: ${it.state}")
            if (it.state.isFinished && it.state != androidx.work.WorkInfo.State.SUCCEEDED) {
                Log.e("WorkStatus", "Worker failed with state: ${it.state}")
                val output = it.outputData
                Log.e("WorkStatus", "Output data: $output")
            }
        }
    }
}
