package com.ashish.personalnewsapp.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ashish.personalnewsapp.GoogleAuth.LoginViewModel
import com.ashish.personalnewsapp.ViewModel.NewsViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

/**
 * Created by Ashish Kr on 02,May,2025
 */
@Composable
fun NewsListScreen(navController: NavHostController, viewModel: NewsViewModel = hiltViewModel(),
                   loginViewModel: LoginViewModel = hiltViewModel()) {
    val articles by viewModel.newsList.collectAsState()
    var context = LocalContext.current
//    var currentUser = loginViewModel.user.value
    var currentUser = FirebaseAuth.getInstance().currentUser
    var scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.refresh("290fcafa8eaa4940a318b0ee0fa72254")
    }
    LazyColumn {
        item(){
            Column {
                Button(onClick = {
                    loginViewModel.signOut() {
                        scope.launch {
                            Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
                            navController.navigate("login_screen") {
                                popUpTo("splash_screen") { inclusive = true }
                            }
                        }
                    }
                }) {

                    Text("Sign out")
                }

                Text(currentUser?.displayName.toString())
                Text(currentUser?.email.toString())
                Text(currentUser?.photoUrl.toString())
            }

        }
        items(articles) { article ->
            Card(modifier = Modifier.padding(5.dp)) {
                Text(article.title,modifier = Modifier.padding(5.dp))

            }
            Spacer(Modifier.height(4.dp))
        }
    }
}