package com.ashish.personalnewsapp.WorkManager

/**
 * Created by Ashish Kr on 03,May,2025
 */

import android.content.Context
import androidx.work.ListenableWorker

import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.ashish.personalnewsapp.Db.NewsRepository
import javax.inject.Inject

class NewsWorkerFactory @Inject constructor(
    private val repository: NewsRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return if (workerClassName == NewsWorker::class.java.name) {
            //  worker  assisted injection
            NewsWorker(appContext, workerParameters, repository)
        } else {
            null
        }
    }
}
