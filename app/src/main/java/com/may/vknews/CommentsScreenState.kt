package com.may.vknews

import com.may.vknews.domain.Post
import com.may.vknews.domain.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(
        val post: Post,
        val comments: List<PostComment>
    ): CommentsScreenState()
}