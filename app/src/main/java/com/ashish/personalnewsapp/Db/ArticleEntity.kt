package com.ashish.personalnewsapp.Db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ashish.personalnewsapp.Data.Article

/**
 * Created by Ashish Kr on 02,May,2025
 */
@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val url: String,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String
)

fun Article.toEntity() = ArticleEntity(url, title, description, urlToImage, publishedAt)
fun ArticleEntity.toArticle() = Article(title, description, url, urlToImage, publishedAt)
