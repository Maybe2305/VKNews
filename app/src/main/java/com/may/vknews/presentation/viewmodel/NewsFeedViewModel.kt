package com.may.vknews.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.may.vknews.data.mapper.NewsFeedMapper
import com.may.vknews.data.network.ApiFactory
import com.may.vknews.domain.FeedPost
import com.may.vknews.domain.StatisticsItem
import com.may.vknews.presentation.screens.NewsFeedScreenState
import com.vk.id.VKID
import com.vk.id.logout.VKIDLogoutCallback
import com.vk.id.logout.VKIDLogoutFail
import kotlinx.coroutines.launch

class NewsFeedViewModel : ViewModel() {

    val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val mapper = NewsFeedMapper()

    val logoutCallback = object : VKIDLogoutCallback {
        override fun onFail(fail: VKIDLogoutFail) {
            TODO("Not yet implemented")
        }

        override fun onSuccess() {
            _screenState.value = NewsFeedScreenState.LogOut
        }

    }

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            val token = VKID.instance.accessToken?.token
            if (token == null) {
                _screenState.value = NewsFeedScreenState.Error("Access token is null")
                return@launch
            }
            try {
                val response = ApiFactory.apiService.loadPosts(token)
                if (response.error != null) {
                    val errorMsg = response.error.errorMessage
                    _screenState.value = NewsFeedScreenState.Error(errorMsg)

                    if (errorMsg.contains("authorization failed", ignoreCase = true) ||
                        errorMsg.contains("invalid access_token", ignoreCase = true)) {
                        VKID.instance.logout(logoutCallback)
                    }
                    return@launch
                }
                val feedPosts = mapper.mapResponseToPosts(response)
                _screenState.value = NewsFeedScreenState.Posts(feedPosts = feedPosts)
            } catch (e: Exception) {
                _screenState.value = NewsFeedScreenState.Error(e.message ?: "Unknown error")
            }



        }
    }

    fun updateCountStatisticItem(feedPost: FeedPost, item: StatisticsItem) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return
        val oldPosts = currentState.feedPosts.toMutableList()
        val oldStatistics = feedPost.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        val newPost = feedPost.copy(statistics = newStatistics)
        val newPosts = oldPosts.apply {
            replaceAll {
                if (it.id == newPost.id) {
                    newPost
                } else {
                    it
                }
            }
        }

        _screenState.value = NewsFeedScreenState.Posts(feedPosts = newPosts)
    }

    fun remove(feedPost: FeedPost) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.feedPosts.toMutableList()
        oldPosts.remove(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(feedPosts = oldPosts)
    }
}