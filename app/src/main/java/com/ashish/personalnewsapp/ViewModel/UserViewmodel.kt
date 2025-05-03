package com.ashish.personalnewsapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.personalnewsapp.Components.UserEntity
import com.ashish.personalnewsapp.Db.UserDao
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ashish Kr on 04,May,2025
 */
@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDao: UserDao,
    application: Application
) : AndroidViewModel(application) {

    fun saveFirebaseUserToDb(user: FirebaseUser, photoUri: String?) {
        val userEntity = UserEntity(
            uid = user.uid,
            name = user.displayName,
            email = user.email,
            phoneNumber = user.phoneNumber,
            photoUri = photoUri ?: user.photoUrl?.toString(),
            isEmailVerified = user.isEmailVerified
        )
        viewModelScope.launch {
            userDao.insertUser(userEntity)
        }
    }

    fun updateUserPhoto(uid: String, newPhotoUri: String) {
        viewModelScope.launch {
            val existingUser = userDao.getUserById(uid)
            if (existingUser != null) {
                userDao.insertUser(existingUser.copy(photoUri = newPhotoUri))
            }
        }
    }

    suspend fun getUser(uid: String): UserEntity? {
        return userDao.getUserById(uid)
    }
}
