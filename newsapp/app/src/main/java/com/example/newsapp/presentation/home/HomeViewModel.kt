package com.example.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.domain.model.News
import com.example.newsapp.util.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(
        val featuredNews: List<News>,
        val allNews: List<News>,
        val isOnline: Boolean
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val isOnline = connectivityObserver.observe()
        .stateIn(viewModelScope, SharingStarted.Lazily, ConnectivityObserver.Status.Unavailable)

    init {
        observeNews()
        refreshNews()
    }

    private fun observeNews() {
        viewModelScope.launch {
            combine(
                newsRepository.getFeaturedNews(),
                newsRepository.getAllNews(),
                searchQuery,
                isOnline
            ) { featured, all, query, online ->
                val filteredNews = if (query.isBlank()) {
                    all
                } else {
                    all.filter {
                        it.title.contains(query, ignoreCase = true) ||
                                it.description?.contains(query, ignoreCase = true) == true
                    }
                }
                HomeUiState.Success(
                    featuredNews = featured,
                    allNews = filteredNews,
                    isOnline = online == ConnectivityObserver.Status.Available
                )
            }.catch { e ->
                _uiState.value = HomeUiState.Error(e.message ?: "Unknown error")
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun refreshNews() {
        viewModelScope.launch {
            newsRepository.refreshNews()
            newsRepository.deleteExpiredCache()
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
