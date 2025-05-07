package com.may.vknews.presentation.screens

import com.may.vknews.domain.entity.FeedPost
import com.may.vknews.domain.entity.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ): CommentsScreenState()
}