package com.ashish.personalnewsapp.Splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
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

@Composable
fun SplashScreen1(navController: NavController) {
    var navigateToHome by remember { mutableStateOf<Boolean?>(null) }
    val currentUser = FirebaseAuth.getInstance().currentUser
    LaunchedEffect(Unit) {
        delay(3000)
        // Simulate login check logic
        val isLoggedIn = checkIfUserIsLoggedIn()
        navigateToHome = isLoggedIn
    }

    // Perform navigation once when navigateToHome is set
    LaunchedEffect(navigateToHome) {
//        when (navigateToHome) {
//            true -> navController.navigate("news_list_screen") {
//                popUpTo("splash_screen") { inclusive = true }
//            }
//            false -> navController.navigate("login_screen") {
//                popUpTo("splash_screen") { inclusive = true }
//            }
//            else -> Unit // Still loading
//        }
        if (currentUser != null) {
            navController.navigate("news_list_screen") {
                popUpTo("splash_screen") { inclusive = true }
            }
        } else {
            navController.navigate("login_screen") {
                popUpTo("splash_screen") { inclusive = true }
            }
        }
    }

    // Show the video splash screen
    VideoSplashScreen(onVideoCompleted = {
        // This is optional if you use the above delay-based logic
    })
}

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

