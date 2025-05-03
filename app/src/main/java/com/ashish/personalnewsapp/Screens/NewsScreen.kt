package com.ashish.personalnewsapp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ashish.personalnewsapp.Components.NewsCard
import com.ashish.personalnewsapp.GoogleAuth.LoginViewModel
import com.ashish.personalnewsapp.ViewModel.NewsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
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

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
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

@Composable
fun NewsListScreen(
    navController: NavHostController,
    viewModel: NewsViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val articles by viewModel.newsList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refresh("290fcafa8eaa4940a318b0ee0fa72254")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Top Headlines", style = MaterialTheme.typography.titleLarge)
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
            if (articles.isNotEmpty()) {
                VerticalPager(
                    count = articles.size,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    articles[page]?.let {
                        NewsCard(article = it)
                    }
                }
            } else {
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
