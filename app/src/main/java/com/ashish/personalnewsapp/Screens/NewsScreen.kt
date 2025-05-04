package com.ashish.personalnewsapp.Screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ashish.personalnewsapp.Components.NewsCard
import com.ashish.personalnewsapp.Components.UserEntity
import com.ashish.personalnewsapp.GoogleAuth.LoginViewModel
import com.ashish.personalnewsapp.ViewModel.NewsViewModel
import com.ashish.personalnewsapp.ViewModel.UserViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by Ashish Kr on 02,May,2025
 */

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class
)

@Composable
fun NewsListScreen(
    navController: NavHostController,
    viewModel: NewsViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val articles by viewModel.newsList.collectAsState()
    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf("") }
    var selectedSources by remember { mutableStateOf(setOf<String>()) }

    val allSources = remember(articles) {
        articles.mapNotNull { it?.source?.name }.distinct()
    }

    val filteredArticles = articles.filter { article ->
        val matchesSearch = searchQuery.isBlank() || article?.title?.contains(searchQuery, ignoreCase = true) == true
        val matchesSource = selectedSources.isEmpty() || article?.source?.name in selectedSources
        matchesSearch && matchesSource
    }

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    var locationText by remember { mutableStateOf("Requesting location permission...") }

    val firebaseUser = FirebaseAuth.getInstance().currentUser
    val uid = firebaseUser?.uid

    var dbUser by remember { mutableStateOf<UserEntity?>(null) }

    // Request permission and fetch location
    LaunchedEffect(locationPermissionState.status) {
        if (locationPermissionState.status.isGranted &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    locationText = "Lat: ${it.latitude}, Lng: ${it.longitude}"
                } ?: run {
                    locationText = "Unable to fetch location."
                }
            }
        }
    }

    // Refresh news once
    LaunchedEffect(Unit) {
        viewModel.refresh("290fcafa8eaa4940a318b0ee0fa72254")
    }

    LaunchedEffect(uid) {
        uid?.let {
            dbUser = userViewModel.getUser(it)

            // If user not in DB yet, save Firebase user to DB
            if (dbUser == null && firebaseUser != null) {
                userViewModel.saveFirebaseUserToDb(firebaseUser, null)
                dbUser = userViewModel.getUser(it)
            }
        }
    }


    val imageModel =  dbUser?.photoUri?.let { Uri.parse(it) }
        ?: firebaseUser?.photoUrl

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Top Headlines", style = MaterialTheme.typography.titleLarge)
                        if (locationPermissionState.status.isGranted) {
                            Text(
                                text = locationText,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                },
                actions = {
                    AsyncImage(
                        model = imageModel,
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .clickable {
                                navController.navigate("profile_screen") {
                                    popUpTo("splash_screen") { inclusive = true }
                                }
                            },
                                contentScale = ContentScale.Crop
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.padding()){
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    placeholder = { Text("Search news...") },
                    singleLine = true
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    items(allSources) { source ->
                        val isSelected = selectedSources.contains(source)
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedSources = if (isSelected) {
                                    selectedSources - source
                                } else {
                                    selectedSources + source
                                }
                            },
                            label = { Text(source) },
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }

                when {
                    !locationPermissionState.status.isGranted -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("Location permission required to show nearby news")
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(onClick = { locationPermissionState.launchPermissionRequest() }) {
                                Text("Grant Permission")
                            }
                        }
                    }

                    articles.isNotEmpty() -> {
//                    VerticalPager(
////                        count = articles.size,
////                        modifier = Modifier.fillMaxSize()
////                    ) { page ->
////                        articles[page]?.let {
////                            NewsCard(article = it)
////                        }
////                    }
                        VerticalPager(
                            count = filteredArticles.size,
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            filteredArticles[page]?.let {
                                NewsCard(article = it)
                            }
                        }
                    }

                    else -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }

        }
    }
}

