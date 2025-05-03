package com.ashish.personalnewsapp.Db

/**
 * Created by Ashish Kr on 04,May,2025
 */
import androidx.room.*
import com.ashish.personalnewsapp.Components.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)


    @Query("SELECT * FROM user_table WHERE uid = :uid LIMIT 1")
    suspend fun getUserById(uid: String): UserEntity?

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM user_table WHERE uid = :uid")
    suspend fun deleteUserById(uid: String)

    @Query("SELECT * FROM user_table LIMIT 1")
    suspend fun getAnyUser(): UserEntity? // helpful for single-user apps
}
