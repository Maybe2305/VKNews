package com.may.vknews

import com.may.vknews.domain.Post

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    data class Posts(val posts: List<Post>): NewsFeedScreenState()
}