package com.may.vknews.presentation.screens

import com.may.vknews.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    data class Posts(val feedPosts: List<FeedPost>): NewsFeedScreenState()

    data class Error(val error: String): NewsFeedScreenState()

    data object LogOut : NewsFeedScreenState()
}