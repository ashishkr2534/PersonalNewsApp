package com.ashish.personalnewsapp.Screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
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
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ashish.personalnewsapp.Components.UserEntity
import com.ashish.personalnewsapp.GoogleAuth.LoginViewModel
import com.ashish.personalnewsapp.ViewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.io.File

/**
 * Created by Ashish Kr on 03,May,2025
 */


fun copyUriToInternalStorage(context: Context, uri: Uri): String? {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null
    val fileName = "profile_${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, fileName)
    file.outputStream().use { output ->
        inputStream.copyTo(output)
    }
    return file.absolutePath
}

@Composable
fun ProfileScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val user = FirebaseAuth.getInstance().currentUser
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val userId = user?.uid
    var showDialog by remember { mutableStateOf(false) }
    val firebaseUser = FirebaseAuth.getInstance().currentUser
    val uid = firebaseUser?.uid
    var dbUser by remember { mutableStateOf<UserEntity?>(null) }
    LaunchedEffect(uid) {
        uid?.let {
            dbUser = userViewModel.getUser(it)
            if (dbUser == null && firebaseUser != null) {
                userViewModel.saveFirebaseUserToDb(firebaseUser, null)
                dbUser = userViewModel.getUser(it)
            }
        }
    }

    val imageToShow: Any? = when {
        selectedImageUri != null -> selectedImageUri
        !dbUser?.photoUri.isNullOrEmpty() -> Uri.fromFile(File(dbUser?.photoUri!!))
        firebaseUser?.photoUrl != null -> firebaseUser.photoUrl
        else -> null
    }

    // Declare tempCameraImageUri before camera launcher
    var tempCameraImageUri by remember { mutableStateOf<Uri?>(null) }

    val imageFromCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempCameraImageUri != null && userId != null) {
            val filePath = copyUriToInternalStorage(context, tempCameraImageUri!!)
            if (filePath != null) {
                selectedImageUri = Uri.fromFile(File(filePath)) // ✅ Show photo
                userViewModel.updateUserPhoto(userId, filePath) // ✅ Save to DB
            }
        }
    }



    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            launchCamera(context) { uri ->
                tempCameraImageUri = uri
                imageFromCameraLauncher.launch(uri) // ✅ Now it works
            }
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Gallery image picker
    val imageFromGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val filePath = copyUriToInternalStorage(context, it)
            if (filePath != null && userId != null) {
                selectedImageUri = Uri.fromFile(File(filePath))
                userViewModel.updateUserPhoto(userId, filePath)
            }
        }
    }



    fun requestAndOpenCamera() {
        val permission = Manifest.permission.CAMERA
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            launchCamera(context) { uri ->
                tempCameraImageUri = uri
                imageFromCameraLauncher.launch(uri)
            }
        } else {
            cameraPermissionLauncher.launch(permission)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Update Profile Picture") },
            text = { Text("Select image from camera or gallery") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    requestAndOpenCamera()
                }) {
                    Text("Camera")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    imageFromGalleryLauncher.launch("image/*")
                }) {
                    Text("Gallery")
                }
            }
        )
    }

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
                // Profile Image with edit
                Box(contentAlignment = Alignment.BottomEnd) {
                    AsyncImage(
                        model = selectedImageUri ?: user?.photoUrl,
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .clickable { showDialog = true },
                        contentScale = ContentScale.Crop
                    )
                    AsyncImage(
                        model = imageToShow,
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .clickable { showDialog = true },
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
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = user?.displayName ?: "No Name",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column(modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Email: ${user?.email ?: "----"}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray

                    )

                    Text(
                        text = "Phone: ${user?.phoneNumber ?: "----"}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )

                    if (user?.isEmailVerified == true) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Email Verified:",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Gray
                            )
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Verified",
                                tint = Color.Green,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    } else {
                        Text(
                            text = "Email Verified: Not Verified",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                    }

                }



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
    )
}

// Utility function to launch camera and return a URI
fun launchCamera(context: Context, onUriReady: (Uri) -> Unit) {
    val photoFile = File(context.cacheDir, "temp_camera_photo_${System.currentTimeMillis()}.jpg")
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        photoFile
    )
    onUriReady(uri)
}
