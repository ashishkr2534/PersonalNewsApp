package com.ashish.personalnewsapp.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ashish.personalnewsapp.Data.Article

/**
 * Created by Ashish Kr on 03,May,2025
 */
@Composable
fun NewsCard(article: Article) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        article.urlToImage?.let {
            AsyncImage(
                model = it,
                contentDescription = "News Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = article.publishedAt ?: "",
            style = MaterialTheme.typography.labelSmall
        )
    }
}

//@Composable
//fun NewsCardNEw(article: Article) {
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = article.title,
//                style = MaterialTheme.typography.titleLarge.copy(
//                    fontWeight = FontWeight.Bold
//                ),
//                color = MaterialTheme.colorScheme.onSurfaceVariant,
//                modifier = Modifier.padding(bottom = 12.dp)
//            )
//
//            article.urlToImage?.let {
//                AsyncImage(
//                    model = it,
//                    contentDescription = "News Image",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .height(200.dp)
//                        .fillMaxWidth()
//                        .clip(RoundedCornerShape(12.dp))
//                )
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Text(
//                text = article.publishedAt ?: "",
//                style = MaterialTheme.typography.labelMedium,
//                color = MaterialTheme.colorScheme.outline
//            )
//        }
//    }
//}
