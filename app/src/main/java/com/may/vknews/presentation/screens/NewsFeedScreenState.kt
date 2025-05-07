package com.may.vknews.presentation.screens

import com.may.vknews.domain.entity.FeedPost

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    object Loading : NewsFeedScreenState()

    data class Posts(
        val feedPosts: List<FeedPost>,
        val nextDataIsLoading: Boolean = false
    ): NewsFeedScreenState()

    data class Error(val error: String): NewsFeedScreenState()

    data object LogOut : NewsFeedScreenState()
}