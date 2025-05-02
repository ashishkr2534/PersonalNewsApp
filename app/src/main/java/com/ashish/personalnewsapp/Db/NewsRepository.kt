package com.ashish.personalnewsapp.Db

import com.ashish.personalnewsapp.Data.Article
import com.ashish.personalnewsapp.DataNetwork.NewsApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ashish Kr on 02,May,2025
 */
@Singleton
class NewsRepository @Inject constructor(
    private val apiService: NewsApiService,
    private val dao: NewsDao
) {
    val articles: Flow<List<Article>> = dao.getAllArticles().map { it.map { it.toArticle() } }

    suspend fun refreshNews(apiKey: String) {
        val response = apiService.getTopHeadlines(apiKey = apiKey)
        dao.clearArticles()
        dao.insertAll(response.articles.map { it.toEntity() })
    }
}
