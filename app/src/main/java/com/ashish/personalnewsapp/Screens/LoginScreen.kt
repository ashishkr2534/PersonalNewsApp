package com.ashish.personalnewsapp.Screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ashish.personalnewsapp.GoogleAuth.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

/**
 * Created by Ashish Kr on 02,May,2025
 */
//@Composable
//fun LoginScreen(){
//     Box(modifier = Modifier.fillMaxSize()){
//         Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//
//             Text( "Please Login to see page")
//         }
//     }
//}

//@Composable
//fun LoginScreen(navController: NavHostController,
//    viewModel: LoginViewModel = hiltViewModel(),
//   // onLoginSuccess: () -> Unit
//) {
//    val context = LocalContext.current
//
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//        try {
//            val account = task.getResult(ApiException::class.java)
//            account?.let {
//                viewModel.signInWithGoogle(
//                    account = it,
//                    onSuccess = {Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()},
//                    onFailure = { error ->
//                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
//                    }
//                )
//            }
//        } catch (e: ApiException) {
//            Toast.makeText(context, "Google sign-in failed", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Button(onClick = {
//            val signInIntent = viewModel.getGoogleSignInClient().signInIntent
//            launcher.launch(signInIntent)
//        }) {
//            Text("Sign in with Google")
//        }
//    }
//}

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn == true){
            navController.navigate("news_list_screen") {
                popUpTo("splash_screen") { inclusive = true }
            }
        }
    }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.let {
                viewModel.signInWithGoogle(
                    account = it,
                    onSuccess = {
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = { error ->
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        } catch (e: ApiException) {
            Toast.makeText(context, "Google sign-in failed", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            val signInIntent = viewModel.getGoogleSignInClient().signInIntent
            launcher.launch(signInIntent)
        }) {
            Text("Sign in with Google")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.signOut(){
                Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Sign out")
        }
    }
}
