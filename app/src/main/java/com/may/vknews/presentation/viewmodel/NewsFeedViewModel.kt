package com.may.vknews.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.may.vknews.data.repository.NewsFeedRepository
import com.may.vknews.domain.FeedPost
import com.may.vknews.domain.StatisticsItem
import com.may.vknews.presentation.screens.NewsFeedScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFeedViewModel : ViewModel() {

    val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val repository = NewsFeedRepository()

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            val feedPosts = repository.loadPosts()
            _screenState.value = NewsFeedScreenState.Posts(feedPosts = feedPosts)
        }
    }

    fun loadNextPosts() {
        Log.d("MyLog", "Подгружаем новые посты...")
        _screenState.value = NewsFeedScreenState.Posts(
            feedPosts = repository.feedPosts,
            nextDataIsLoading = true
        )
        loadPosts()
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedPost)
            _screenState.value = NewsFeedScreenState.Posts(feedPosts = repository.feedPosts)
        }
    }

    fun remove(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.deletePost(feedPost)
            _screenState.value = NewsFeedScreenState.Posts(feedPosts = repository.feedPosts)
        }
    }
}