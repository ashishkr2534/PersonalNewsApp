package com.ashish.personalnewsapp.Db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ashish.personalnewsapp.Data.Article
import com.ashish.personalnewsapp.Data.Source

/**
 * Created by Ashish Kr on 02,May,2025
 */
//@Entity(tableName = "articles")
//data class ArticleEntity(
//    @PrimaryKey val url: String,
//    val title: String,
//    val description: String?,
//    val urlToImage: String?,
//    val publishedAt: String
//)

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val url: String,
    val sourceName: String?,
    val author: String?,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)

fun Article.toEntity() = ArticleEntity(
    url = url,
    sourceName = source?.name,
    author = author,
    title = title,
    description = description,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content
)

fun ArticleEntity.toArticle() = Article(
    source = Source(null, sourceName),
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content
)


//fun Article.toEntity() = ArticleEntity(url, title, description, urlToImage, publishedAt)
//fun ArticleEntity.toArticle() = Article(title, description, url, urlToImage, publishedAt)
