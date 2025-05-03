package com.ashish.personalnewsapp.Db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ashish.personalnewsapp.Components.UserEntity

/**
 * Created by Ashish Kr on 02,May,2025
 */
//@Database(entities = [ArticleEntity::class],[UserEntity::class], version = 1)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun newsDao(): NewsDao
//    abstract fun userDao(): UserDao
//}
@Database(entities = [ArticleEntity::class, UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun userDao(): UserDao
}


//@Database(entities = [UserEntity::class], version = 1)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
//}
