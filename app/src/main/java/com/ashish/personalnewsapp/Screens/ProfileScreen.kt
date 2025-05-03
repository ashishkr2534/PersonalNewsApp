package com.ashish.personalnewsapp.Screens

import android.annotation.SuppressLint
import android.provider.ContactsContract.Profile
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ashish.personalnewsapp.GoogleAuth.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

/**
 * Created by Ashish Kr on 03,May,2025
 */
//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@Composable
//fun ProfileScreen(){
//
//    var currentUser = FirebaseAuth.getInstance().currentUser
//
//    Scaffold(topBar = {
//        TopAppBar({
//            Text("Profile")
//        })
//    }) {
//        Column {
//            Text("User Profile")
//            Text(currentUser?.displayName.toString())
//            Text(currentUser?.email.toString())
//            val profileImageUrl = currentUser?.photoUrl?.toString()
//
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                profileImageUrl?.let {
//                    AsyncImage(
//                        model = it,
//                        contentDescription = "Profile Image",
//                        modifier = Modifier
//                            .size(100.dp)
//                            .clip(CircleShape),
//                        contentScale = ContentScale.Crop
//                    )
//                }
//
//                Text(text = profileImageUrl ?: "No image URL")
//            }
//
//
//        }
//
//    }
//
//}

@Composable
fun ProfileScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val user = FirebaseAuth.getInstance().currentUser
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile") },
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Image
                AsyncImage(
                    model = user?.photoUrl,
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Name
                Text(
                    text = user?.displayName ?: "No Name",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Email
                Text(
                    text = user?.email ?: "No Email",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Text(
                    text = user?.phoneNumber ?: "No Number",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Text(
                    text = user?.isEmailVerified.toString() ?: "----",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Sign Out Button
                Button(
                    onClick = {
                        loginViewModel.signOut {
                            scope.launch {
                                Toast
                                    .makeText(context, "Signed out", Toast.LENGTH_SHORT)
                                    .show()
                                navController.navigate("login_screen") {
                                    popUpTo("splash_screen") { inclusive = true }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Sign Out", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    )
}
