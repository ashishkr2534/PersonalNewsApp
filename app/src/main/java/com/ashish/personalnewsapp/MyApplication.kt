package com.ashish.personalnewsapp


/**
 * Created by Ashish Kr on 02,May,2025
 */
//package com.ashish.personalnewsapp

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.ashish.personalnewsapp.WorkManager.NewsWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

//@HiltAndroidApp
//class MyApplication : Application() {
//
//    @Inject lateinit var newsWorkerFactory: NewsWorkerFactory
//
//    override fun onCreate() {
//        super.onCreate()
//
//        // Initialize WorkManager with custom WorkerFactory
//        val workManagerConfig = Configuration.Builder()
//            .setWorkerFactory(newsWorkerFactory)
//            .build()
//
//        WorkManager.initialize(this, workManagerConfig)
//    }
//}
//

@HiltAndroidApp
class MyApplication : Application() {
    // No need for manual initialization of WorkManager
    @Inject lateinit var newsWorkerFactory: NewsWorkerFactory
}