package com.ashish.personalnewsapp.Screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ashish.personalnewsapp.Components.NewsCard
import com.ashish.personalnewsapp.Components.UserEntity
import com.ashish.personalnewsapp.GoogleAuth.LoginViewModel
import com.ashish.personalnewsapp.R
import com.ashish.personalnewsapp.ViewModel.NewsViewModel
import com.ashish.personalnewsapp.ViewModel.UserViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by Ashish Kr on 02,May,2025
 */

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    navController: NavHostController,
    viewModel: NewsViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val firebaseUser = FirebaseAuth.getInstance().currentUser
    val uid = firebaseUser?.uid

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var dbUser by remember { mutableStateOf<UserEntity?>(null) }

    var searchQuery by remember { mutableStateOf("") }
    var selectedSources by remember { mutableStateOf(setOf<String>()) }
    var locationText by remember { mutableStateOf("Requesting location permission...") }
    var permissionStatusText by remember { mutableStateOf("") }

    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val notificationPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
    } else null

    val articles by viewModel.newsList.collectAsState()

    val allSources = remember(articles) {
        articles.mapNotNull { it?.source?.name }.distinct()
    }

    val filteredArticles = articles.filter { article ->
        val matchesSearch = searchQuery.isBlank() || article?.title?.contains(searchQuery, ignoreCase = true) == true
        val matchesSource = selectedSources.isEmpty() || article?.source?.name in selectedSources
        matchesSearch && matchesSource
    }

    LaunchedEffect(locationPermissionState.status) {
        if (locationPermissionState.status.isGranted &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    locationText = "Lat: ${it.latitude}, Lng: ${it.longitude}"
                } ?: run {
                    locationText = "Unable to fetch location."
                }
            }
        } else {
            locationText = "Location permission not granted."
        }
    }

    LaunchedEffect(notificationPermissionState?.status) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionStatusText = when {
                notificationPermissionState?.status?.isGranted == true -> "Notification permission granted."
                notificationPermissionState?.status?.shouldShowRationale == true -> "Please allow notifications from settings."
                else -> "Notification permission not granted."
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.refresh("290fcafa8eaa4940a318b0ee0fa72254")
    }

    LaunchedEffect(uid) {
        uid?.let {
            dbUser = userViewModel.getUser(it)
            if (dbUser == null && firebaseUser != null) {
                userViewModel.saveFirebaseUserToDb(firebaseUser, null)
                dbUser = userViewModel.getUser(it)
            }
        }
    }
    LaunchedEffect(Unit) {
        if (!locationPermissionState.status.isGranted){
            locationPermissionState.launchPermissionRequest()
        }

    }

    val imageModel = dbUser?.photoUri?.let { Uri.parse(it) } ?: firebaseUser?.photoUrl

    var menuExpanded by remember { mutableStateOf(false) }
    val refreshing by viewModel.isRefreshing.collectAsState()

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

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = refreshing),
            onRefresh = {
                viewModel.refresh("290fcafa8eaa4940a318b0ee0fa72254")
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    placeholder = { Text("Search news...") },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(30.dp)
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        if (permissionStatusText.isNotBlank()) {
                            Text(
                                text = permissionStatusText,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                    }

                    Row {

                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
                        }

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Allow Notification") },
                                onClick = {
                                    menuExpanded = false
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                        notificationPermissionState?.launchPermissionRequest()
                                    } else {
                                        Toast.makeText(context, "Not required for your OS version", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Allow Location") },
                                onClick = {
                                    menuExpanded = false
                                    locationPermissionState.launchPermissionRequest()
                                }
                            )
                        }                }


                }



                when {

                    filteredArticles.isNotEmpty() -> {
                        VerticalPager(
                            count = filteredArticles.size,
                            modifier = Modifier.weight(1f)
                        ) { page ->
                            filteredArticles[page]?.let {
                                NewsCard(article = it)
                            }
                        }
                    }

                    else -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No articles found.")
                        }
                    }
                }
            }
        }

    }
}


