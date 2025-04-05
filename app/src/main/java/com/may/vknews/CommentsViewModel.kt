package com.may.vknews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.may.vknews.domain.Post
import com.may.vknews.domain.PostComment

class CommentsViewModel(
    post: Post
) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(post)
    }

    fun loadComments(post: Post) {
        val comments = mutableListOf<PostComment>().apply {
            repeat(10) {
                add(PostComment(id = it))
            }
        }

        _screenState.value = CommentsScreenState.Comments(
            post = post,
            comments = comments
        )
    }
}

