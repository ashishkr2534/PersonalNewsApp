package com.ashish.personalnewsapp.WorkManager

import android.content.Context
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import androidx.work.Configuration

/**
 * Created by Ashish Kr on 02,May,2025
 */

import androidx.hilt.work.HiltWorkerFactory

//@Module
//@InstallIn(SingletonComponent::class)
//object WorkManagerModule {
//
//    @Provides
//    fun provideWorkManager(@ApplicationContext context: Context, workerFactory: WorkerFactory) {
//        val configuration = Configuration.Builder()
//            .setWorkerFactory(workerFactory)
//            .build()
//        return WorkManager.initialize(context, configuration)
//    }
//}
@Module
@InstallIn(SingletonComponent::class)
object WorkManagerModule {

    @Provides
    fun provideWorkManager(@ApplicationContext context: Context, workerFactory: WorkerFactory): WorkManager {
        val configuration = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        // Initialize WorkManager (no need to return it)
        WorkManager.initialize(context, configuration)

        // Return the instance directly from WorkManager
        return WorkManager.getInstance(context)
    }
}
