package com.ashish.personalnewsapp.Db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Ashish Kr on 02,May,2025
 */
@Database(entities = [ArticleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
