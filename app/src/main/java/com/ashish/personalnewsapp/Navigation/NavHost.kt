package com.ashish.personalnewsapp.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ashish.personalnewsapp.Screens.LoginScreen
import com.ashish.personalnewsapp.Screens.NewsListScreen
import com.ashish.personalnewsapp.Screens.ProfileScreen
import com.ashish.personalnewsapp.Splash.SplashScreen1


/**
 * Created by Ashish Kr on 02,May,2025
 */

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen1(navController = navController)
        }
        composable("news_list_screen") {
            NewsListScreen(navController) // Replace with your actual home screen Composable
        }
        composable("login_screen") {
            LoginScreen(navController) // Replace with your actual login screen Composable
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController)
        }
    }
}


