package com.ashish.personalnewsapp.Screens

import android.annotation.SuppressLint
import android.net.Uri
import android.provider.ContactsContract.Profile
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ashish.personalnewsapp.Components.UserEntity
import com.ashish.personalnewsapp.GoogleAuth.LoginViewModel
import com.ashish.personalnewsapp.ViewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

/**
 * Created by Ashish Kr on 03,May,2025
 */

@Composable
fun ProfileScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val firebaseUser = FirebaseAuth.getInstance().currentUser
    val uid = firebaseUser?.uid
    val scope = rememberCoroutineScope()

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var dbUser by remember { mutableStateOf<UserEntity?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it

            // Save new image URI to DB
            uid?.let { id ->
                userViewModel.updateUserPhoto(id, it.toString())
                Toast.makeText(context,"Profile Image changed",Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Load user from DB when screen starts
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile") },
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image
            Box(contentAlignment = Alignment.BottomEnd) {
                val imageModel = selectedImageUri
                    ?: dbUser?.photoUri?.let { Uri.parse(it) }
                    ?: firebaseUser?.photoUrl

                AsyncImage(
                    model = imageModel,
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .clickable {
                            launcher.launch("image/*")
                        },
                    contentScale = ContentScale.Crop
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.White,
                    modifier = Modifier
                        .offset((-8).dp, (-8).dp)
                        .size(24.dp)
                        .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                        .padding(4.dp)
                        .clickable {
                            launcher.launch("image/*")
                        }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = dbUser?.name ?: firebaseUser?.displayName ?: "No Name",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = dbUser?.email ?: firebaseUser?.email ?: "No Email",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Text(
                text = dbUser?.phoneNumber ?: firebaseUser?.phoneNumber ?: "No Number",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Text(
                text = dbUser?.isEmailVerified?.toString() ?: firebaseUser?.isEmailVerified.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    loginViewModel.signOut {
                        scope.launch {
                            Toast.makeText(context, "Signed out", Toast.LENGTH_SHORT).show()
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
}
