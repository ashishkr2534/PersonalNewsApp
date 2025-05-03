package com.ashish.personalnewsapp.Navigation

import androidx.annotation.StringRes
import com.ashish.personalnewsapp.R

/**
 * Created by Ashish Kr on 02,May,2025
 */
const val RootRoute ="root"

sealed class Screen (val route:String,@StringRes val resourceId:Int){

    object SplashScreen : Screen("splash_screen", R.string.splash_screen)
    object NewListScreen : Screen("news_list_screen", R.string.news_list_screen)
    object LoginScreen : Screen("login_screen", R.string.login_screen)
    object ProfileScreen : Screen("profile_screen", R.string.profile_screen)



}