package com.ashish.personalnewsapp.GoogleAuth

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.ashish.personalnewsapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Created by Ashish Kr on 03,May,2025
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val context: Application
) : AndroidViewModel(context) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

//    private val _isLoggedIn = mutableStateOf<Boolean>(false)
//    val isLoggedIn: State<Boolean> = _isLoggedIn
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn


    private val _user = mutableStateOf<FirebaseUser?>(null)
    val user: State<FirebaseUser?> = _user

    fun signInWithGoogle(account: GoogleSignInAccount, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _user.value = auth.currentUser
                    onSuccess()
                    _isLoggedIn.value = true
                } else {
                    onFailure(task.exception?.message ?: "Authentication failed")
                }
            }
    }

    fun getGoogleSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    fun signOut(onComplete: () -> Unit) {
        getGoogleSignInClient().signOut().addOnCompleteListener {
            auth.signOut()
            _user.value = null
            onComplete()
        }
    }
}
