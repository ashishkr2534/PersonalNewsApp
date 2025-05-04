package com.ashish.personalnewsapp.Splash

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.ashish.personalnewsapp.Navigation.RootRoute
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

/**
 * Created by Ashish Kr on 02,May,2025
 */
//@Composable
//fun SplashScreen1(navController: NavController) {
//
//    var navigateToHome by remember { mutableStateOf(false) }
//    LaunchedEffect(Unit) {
//        delay(3000)
//        navigateToHome = true
//    }
//
//    if (navigateToHome) {
//        navController.navigate("news_list_screen") // Navigate to Home screen
//    } else {
//        navController.navigate("login_screen") // Navigate to Login screen
//    }
//    // Show the video splash screen
//    VideoSplashScreen(onVideoCompleted = {
//        // Navigate to the appropriate screen after the video ends
//        //val isLoggedIn = checkIfUserIsLoggedIn() // Replace with your actual check
////        if (isLoggedIn) {
////            navController.navigate("home") // Navigate to Home screen
////        } else {
////            navController.navigate("login") // Navigate to Login screen
////        }
//
//
//
//    }
//
//    )
//
//}

//@Composable
//fun SplashScreen1(navController: NavController) {
//    var navigateToHome by remember { mutableStateOf<Boolean?>(null) }
//    val currentUser = FirebaseAuth.getInstance().currentUser
//     var context = LocalContext.current
//    LaunchedEffect(Unit) {
//        delay(3000)
//        // Simulate login check logic
//        val isLoggedIn = checkIfUserIsLoggedIn()
//        navigateToHome = isLoggedIn
//    }
//
//    LaunchedEffect(navigateToHome) {
//
//        if(navigateToHome == true){
//            if (currentUser != null) {
//                navController.navigate(RootRoute) {
//                    popUpTo("splash_screen") { inclusive = true }
//                }
//
//            } else {
//                navController.navigate("login_screen") {
//                    popUpTo("splash_screen") { inclusive = true }
//                }
//            }
//
//        }
//
//       Log.d("Current User", currentUser.toString())
//       Log.d("Current User", currentUser?.displayName.toString() ?:"No Name")
//    }
//
//    VideoSplashScreen(onVideoCompleted = {
//    })
//}

// Simulated login check function
fun checkIfUserIsLoggedIn(): Boolean {
    // Replace with real logic, such as checking a shared preference or secure storage
    return true
}

@Composable
fun SplashScreen(navController: NavController) {
    val currentUser = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(Unit) {
        delay(3000) // after splash video
        if (currentUser != null) {
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    // Your splash screen video Composable here
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun SplashScreen1(navController: NavController) {
    var navigateToHome by remember { mutableStateOf<Boolean?>(null) }
    val context = LocalContext.current
    val currentUser = FirebaseAuth.getInstance().currentUser

    // Make immersive fullscreen
    LaunchedEffect(Unit) {
        val window = (context as? Activity)?.window
        window?.let {
            WindowCompat.setDecorFitsSystemWindows(it, false)
            it.insetsController?.apply {
                hide(WindowInsets.Type.systemBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    // Delay and decide where to go
    LaunchedEffect(Unit) {
        delay(3000)
        val isLoggedIn = checkIfUserIsLoggedIn()
        navigateToHome = isLoggedIn
    }

    LaunchedEffect(navigateToHome) {
        if (navigateToHome == true) {
            if (currentUser != null) {
                navController.navigate(RootRoute) {
                    popUpTo("splash_screen") { inclusive = true }
                }
            } else {
                navController.navigate("login_screen") {
                    popUpTo("splash_screen") { inclusive = true }
                }
            }
        }

        Log.d("Current User", currentUser.toString())
        Log.d("Current User", currentUser?.displayName ?: "No Name")
    }

    // Play fullscreen video
    VideoSplashScreen(onVideoCompleted = {
        // You can also navigate here if preferred instead of 3-second timer
    })
}

