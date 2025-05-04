package com.ashish.personalnewsapp.Components

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Ashish Kr on 04,May,2025
 */
@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val uid: String,
    val name: String?,
    val email: String?,
    val phoneNumber: String?,
    val photoUri: String?, // this is local file URI if changed, else Firebase photo URL
    val isEmailVerified: Boolean
)
