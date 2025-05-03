package com.ashish.personalnewsapp.Screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ashish.personalnewsapp.GoogleAuth.LoginViewModel
import com.ashish.personalnewsapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

/**
 * Created by Ashish Kr on 02,May,2025
 */
//@Composable
//fun LoginScreen(){
//     Box(modifier = Modifier.fillMaxSize()){
//         Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//
//             Text( "Please Login to see page")
//         }
//     }
//}

//@Composable
//fun LoginScreen(navController: NavHostController,
//    viewModel: LoginViewModel = hiltViewModel(),
//   // onLoginSuccess: () -> Unit
//) {
//    val context = LocalContext.current
//
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//        try {
//            val account = task.getResult(ApiException::class.java)
//            account?.let {
//                viewModel.signInWithGoogle(
//                    account = it,
//                    onSuccess = {Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()},
//                    onFailure = { error ->
//                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
//                    }
//                )
//            }
//        } catch (e: ApiException) {
//            Toast.makeText(context, "Google sign-in failed", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Button(onClick = {
//            val signInIntent = viewModel.getGoogleSignInClient().signInIntent
//            launcher.launch(signInIntent)
//        }) {
//            Text("Sign in with Google")
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn == true) {
            navController.navigate("news_list_screen") {
                popUpTo("splash_screen") { inclusive = true }
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.let {
                viewModel.signInWithGoogle(
                    account = it,
                    onSuccess = {
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = { error ->
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        } catch (e: ApiException) {
            Toast.makeText(context, "Google sign-in failed", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Welcome", style = MaterialTheme.typography.headlineSmall) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Login Icon",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Sign in to continue",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 32.dp)
                )


                Button(
                    onClick = {
                        val signInIntent = viewModel.getGoogleSignInClient().signInIntent
                        launcher.launch(signInIntent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.google_icon_png), // you need to add this asset
                        contentDescription = "Google logo",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Sign in with Google",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))


//                TextButton(
//                    onClick = {
//                        viewModel.signOut {
//                            Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                ) {
//                    Text("Sign out", color = MaterialTheme.colorScheme.error)
//                }
            }
        }
    )
}
