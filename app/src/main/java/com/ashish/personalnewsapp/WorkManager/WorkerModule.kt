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

@Module
@InstallIn(SingletonComponent::class)
object WorkManagerModule {

    @Provides
    fun provideWorkManager(@ApplicationContext context: Context, workerFactory: WorkerFactory): WorkManager {
        val configuration = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        // initialize WorkManager
        WorkManager.initialize(context, configuration)

        // instance  WorkManager
        return WorkManager.getInstance(context)
    }
}
