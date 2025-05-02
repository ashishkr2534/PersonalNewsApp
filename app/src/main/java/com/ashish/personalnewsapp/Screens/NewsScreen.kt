package com.ashish.personalnewsapp.Screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashish.personalnewsapp.ViewModel.NewsViewModel

/**
 * Created by Ashish Kr on 02,May,2025
 */
@Composable
fun NewsListScreen(viewModel: NewsViewModel = hiltViewModel()) {
    val articles by viewModel.newsList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refresh("290fcafa8eaa4940a318b0ee0fa72254")
    }
    LazyColumn {
        items(articles) { article ->
            Card(modifier = Modifier.padding(5.dp)) {
                Text(article.title,modifier = Modifier.padding(5.dp))

            }
            Spacer(Modifier.height(4.dp))
        }
    }
}