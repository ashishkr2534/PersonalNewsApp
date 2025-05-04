package com.ashish.personalnewsapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.personalnewsapp.Data.Article
import com.ashish.personalnewsapp.Db.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ashish Kr on 02,May,2025
 */
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    val newsList: StateFlow<List<Article>> = repository.articles.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

//    fun refresh(apiKey: String) {
//        viewModelScope.launch {
//            repository.refreshNews(apiKey)
//        }
//    }

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    fun refresh(apiKey: String) {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                // Your actual refresh logic
               repository.refreshNews(apiKey)
               // val news = repository.refreshNews(apiKey)
                //_newsList.value = news
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isRefreshing.value = false
            }
        }
    }

}
