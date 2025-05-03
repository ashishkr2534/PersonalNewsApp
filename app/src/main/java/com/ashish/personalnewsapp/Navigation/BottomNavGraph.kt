package com.ashish.personalnewsapp.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Created by Ashish Kr on 03,May,2025
 */

sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    object News : BottomNavScreen("news", "News", Icons.Default.Home)
    object Profile : BottomNavScreen("profile", "Profile", Icons.Default.Person)
    object Settings : BottomNavScreen("settings", "Settings", Icons.Default.Settings)
}