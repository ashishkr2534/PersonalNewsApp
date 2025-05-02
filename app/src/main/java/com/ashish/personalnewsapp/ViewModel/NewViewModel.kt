package com.ashish.personalnewsapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.personalnewsapp.Data.Article
import com.ashish.personalnewsapp.Db.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun refresh(apiKey: String) {
        viewModelScope.launch {
            repository.refreshNews(apiKey)
        }
    }
}
