package com.ashish.personalnewsapp.Db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides

import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by Ashish Kr on 02,May,2025
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "news_db").build()

    @Provides
    fun provideNewsDao(db: AppDatabase) = db.newsDao()

    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()
}

