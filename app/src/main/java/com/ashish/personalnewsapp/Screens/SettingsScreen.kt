package com.ashish.personalnewsapp.Screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by Ashish Kr on 03,May,2025
 */
//@Composable
//fun SettingsScreen() {
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Text("Settings Screen")
//    }
//}
//

@Composable
fun AboutScreen() {
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 10.dp, vertical = 20.dp)
        ) {
            Text(
                text = "📰 Personal News App - Modern News Reader App",
                modifier = Modifier.padding(10.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Personal New App is a personalized news reader app built using Jetpack Compose and Kotlin...",
                color = colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "🚀 Features",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorScheme.onBackground
            )

            FeatureSection(
                title = "🔐 Authentication",
                items = listOf(
                    "Google Sign-In using Firebase Auth",
                    "Fetch and store user profile details: Name, Email, Profile Picture",
                    "Conditional navigation logic:",
                    "• If user is logged in → Show Home (NewsList) Screen",
                    "• If not logged in → Show Login Screen",
                    "• Light Mode → Dark Mode"
                )
            )

            // Other FeatureSections remain the same
            FeatureSection(title = "📱 Home Screen", items = listOf(
                "Full-screen Inshorts-style swipe navigation (one news per screen)",
                "Location (Latitude & Longitude) shown at top of screen",
                "Notification permission prompt shown on first visit from Menu Button",
                "News content fetched from NewsAPI.org",
                "Offline caching with Room DB",
                "Pull-to-refresh & swipe gestures (delete/view)"
            ))

            FeatureSection(title = "🔍 Search Screen", items = listOf(
                "List/Grid layout for viewing multiple news articles",
                "Selection chips for news source to select from",
                "Live search functionality for articles"
            ))

            FeatureSection(title = "👤 Profile Screen", items = listOf(
                "View and update user profile details",
                "Change profile picture from: 📷 Camera, 🖼️ Gallery",
                "All permissions handled with dialogs & fallbacks",
                "Profile image is stored locally and synced across all screens"
            ))

            FeatureSection(title = "🔔 Notifications", items = listOf(
                "Prompt for Notification permission on Home screen",
                "Notification on app open when news fetched",
                "Future updates will include breaking news alerts"
            ))

            FeatureSection(title = "🧠 Data Persistence", items = listOf(
                "All user data and news articles are stored locally using Room Database",
                "Ensures smooth performance and offline access",
                "Profile images saved locally and fetched efficiently"
            ))

            FeatureSection(title = "🌐 Location Access", items = listOf(
                "User's latitude and longitude is displayed on Home screen (top area)",
                "Automatically updated with proper permission handling"
            ))

            FeatureSection(title = "🧰 Tech Stack", items = listOf(
                "Kotlin - Primary language",
                "Jetpack Compose - Declarative UI",
                "Firebase Auth - Google Sign-In",
                "Retrofit - API communication",
                "Room DB - Local storage for users and news",
                "Paging 3 - Pagination for smooth news loading",
                "WorkManager - Periodic news background refresh",
                "Coil - Image loading",
                "Location API - Fetching current user location",
                "Permissions API - Handling runtime permissions"
            ))

            FeatureSection(title = "🛠️ Setup Instructions", items = listOf(
                "Clone the Repository:",
                "git clone https://github.com/ashishkr2534/PersonalNewsApp.git",
                "cd PersonalNewsApp"
            ))

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ashishkr2534/PersonalNewsApp"))
                context.startActivity(intent)
            }) {
                Text("View on GitHub")
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun FeatureSection(title: String, items: List<String>) {
    val colorScheme = MaterialTheme.colorScheme

    Spacer(modifier = Modifier.height(12.dp))
    Text(title, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = colorScheme.onBackground)
    Spacer(modifier = Modifier.height(4.dp))
    items.forEach { item ->
        Text(
            text = "• $item",
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 8.dp, bottom = 2.dp),
            color = colorScheme.onBackground
        )
    }
}
