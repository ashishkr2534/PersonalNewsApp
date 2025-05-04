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
//    // No need for manual initialization of WorkManager
//    @Inject lateinit var newsWorkerFactory: NewsWorkerFactory
//}
@HiltAndroidApp
//@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {

    @Inject lateinit var newsWorkerFactory: NewsWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(newsWorkerFactory)
            .build()
}

