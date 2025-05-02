package com.ashish.personalnewsapp.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Created by Ashish Kr on 02,May,2025
 */
@Composable
fun LoginScreen(){
     Box(modifier = Modifier.fillMaxSize()){
         Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

             Text( "Please Login to see page")
         }
     }
}