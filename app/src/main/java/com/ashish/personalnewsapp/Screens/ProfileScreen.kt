package com.ashish.personalnewsapp.Screens

import android.provider.ContactsContract.Profile
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by Ashish Kr on 03,May,2025
 */
@Composable
fun ProfileScreen(){

    var currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(topBar = {
        TopAppBar({
            Text("Profile")
        })
    }) {
        Column {
            Text("User Profile")
            Text(currentUser?.displayName.toString())
            Text(currentUser?.email.toString())
            val profileImageUrl = currentUser?.photoUrl?.toString()

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                profileImageUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Text(text = profileImageUrl ?: "No image URL")
            }


        }

    }

}