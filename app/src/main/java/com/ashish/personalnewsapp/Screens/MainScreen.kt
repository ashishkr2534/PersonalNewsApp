package com.ashish.personalnewsapp.Screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ashish.personalnewsapp.Navigation.BottomBar
import com.ashish.personalnewsapp.Navigation.BottomNavScreen
import com.ashish.personalnewsapp.Splash.SplashScreen1

/**
 * Created by Ashish Kr on 03,May,2025
 */
@Composable
fun MainScreen(rootNavController: NavHostController) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = bottomNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavScreen.News.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavScreen.News.route) {
                NewsListScreen(navController = rootNavController) // use root for shared auth/profile nav
            }
            composable(BottomNavScreen.Profile.route) {
                ProfileScreen(navController = rootNavController)
            }
            composable(BottomNavScreen.Settings.route) {
                SettingsScreen()
                //SplashScreen1(navController = rootNavController)
            }
        }
    }
}
