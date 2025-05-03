package com.ashish.personalnewsapp.Screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ashish.personalnewsapp.Components.NewsCard
import com.ashish.personalnewsapp.GoogleAuth.LoginViewModel
import com.ashish.personalnewsapp.ViewModel.NewsViewModel
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
//@Composable
//fun NewsListScreen(navController: NavHostController, viewModel: NewsViewModel = hiltViewModel(),
//                   loginViewModel: LoginViewModel = hiltViewModel()) {
//    val articles by viewModel.newsList.collectAsState()
//    var context = LocalContext.current
////    var currentUser = loginViewModel.user.value
//    var currentUser = FirebaseAuth.getInstance().currentUser
//    var scope = rememberCoroutineScope()
//    LaunchedEffect(Unit) {
//        viewModel.refresh("290fcafa8eaa4940a318b0ee0fa72254")
//    }
//    LazyColumn {
//        item(){
//            Column {
//                Button(onClick = {
//                    loginViewModel.signOut() {
//                        scope.launch {
//                            Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
//                            navController.navigate("login_screen") {
//                                popUpTo("splash_screen") { inclusive = true }
//                            }
//                        }
//                    }
//                }) {
//
//                    Text("Sign out")
//                }
//
//                Text(currentUser?.displayName.toString())
//                Text(currentUser?.email.toString())
//                Text(currentUser?.photoUrl.toString())
//            }
//
//        }
//        item {
//            Button(
//                onClick = {
//                   navController.navigate("profile_screen"){
//                       popUpTo("splash_screen") { inclusive = true }
//                   }
//                }
//            ) {
//                Text("Profile")
//            }
//        }
//        items(articles) { article ->
//            Card(modifier = Modifier.padding(5.dp)) {
//                Text(article.title,modifier = Modifier.padding(5.dp))
//
//            }
//            Spacer(Modifier.height(4.dp))
//        }
//    }
//}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class
)
//@Composable
//fun NewsListScreen(
//    navController: NavHostController,
//    viewModel: NewsViewModel = hiltViewModel(),
//    loginViewModel: LoginViewModel = hiltViewModel()
//) {
//    val context = LocalContext.current
//    val currentUser = FirebaseAuth.getInstance().currentUser
//    val scope = rememberCoroutineScope()
//
//    val articles by viewModel.newsList.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.refresh("290fcafa8eaa4940a318b0ee0fa72254")
//    }
//
//    Scaffold() {
//        it.calculateTopPadding()
//
//    }
//
//    Box(Modifier.fillMaxSize()) {
//        Column {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                AsyncImage(
//                    model = currentUser?.photoUrl,
//                    contentDescription = "Profile Image",
//                    modifier = Modifier
//                        .clickable {
//                            navController.navigate("profile_screen") {
//                                popUpTo("splash_screen") { inclusive = true }
//                            }
//                        }
//                        .size(48.dp)
//                        .clip(CircleShape)
//                        .background(Color.Gray)
//                )
//            }
//
//        }
//
//        VerticalPager(
//            count = articles.size,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(top = 100.dp) // to avoid top UI
//        ) { page ->
//            val article = articles[page]
//            article?.let {
//                NewsCard(article = it)
//            }
//        }
//    }
//}

//@Composable
//fun NewsListScreen(
//    navController: NavHostController,
//    viewModel: NewsViewModel = hiltViewModel(),
//    loginViewModel: LoginViewModel = hiltViewModel()
//) {
//    val currentUser = FirebaseAuth.getInstance().currentUser
//    val articles by viewModel.newsList.collectAsState()
//
//
//
////    val fusedLocationClient = remember {
////        LocationServices.getFusedLocationProviderClient(context)
////    }
////
////    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
////    var locationText by remember { mutableStateOf("Requesting permission...") }
////
////    // Launch effect when permission is granted
////    LaunchedEffect(locationPermissionState.hasPermission) {
////        if (locationPermissionState.hasPermission) {
////            fusedLocationClient.lastLocation
////                .addOnSuccessListener { location ->
////                    location?.let {
////                        locationText = "Lat: ${it.latitude}, Lng: ${it.longitude}"
////                    } ?: run {
////                        locationText = "Unable to fetch location."
////                    }
////                }
////        }
////    }
//
//    LaunchedEffect(Unit) {
//        viewModel.refresh("290fcafa8eaa4940a318b0ee0fa72254")
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text("Top Headlines", style = MaterialTheme.typography.titleLarge)
//                },
//                actions = {
//                    AsyncImage(
//                        model = currentUser?.photoUrl,
//                        contentDescription = "Profile Image",
//                        modifier = Modifier
//                            .padding(end = 16.dp)
//                            .size(40.dp)
//                            .clip(CircleShape)
//                            .background(Color.Gray)
//                            .clickable {
//                                navController.navigate("profile_screen") {
//                                    popUpTo("splash_screen") { inclusive = true }
//                                }
//                            }
//                    )
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
//                )
//            )
//        }
//    ) { paddingValues ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//        ) {
//            if (articles.isNotEmpty()) {
//                VerticalPager(
//                    count = articles.size,
//                    modifier = Modifier.fillMaxSize()
//                ) { page ->
//                    articles[page]?.let {
//                        NewsCard(article = it)
//                    }
//                }
//            } else {
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            }
//        }
//    }
//}

//@OptIn(ExperimentalPermissionsApi::class)
//@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NewsListScreen(
    navController: NavHostController,
    viewModel: NewsViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val articles by viewModel.newsList.collectAsState()
    val context = LocalContext.current

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    var locationText by remember { mutableStateOf("Requesting location permission...") }

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
                        model = currentUser?.photoUrl,
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
                            }
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
                    VerticalPager(
                        count = articles.size,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        articles[page]?.let {
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

