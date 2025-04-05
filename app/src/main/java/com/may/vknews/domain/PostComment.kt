package com.may.vknews.domain

import com.may.vknews.R

data class PostComment(
    val id: Int,
    val authorName: String = "Author",
    val authorAvatarId: Int = R.drawable.author_comment_avatar,
    val commentText: String = "Text comments",
    val publicationDate: String = "14:05"
)
