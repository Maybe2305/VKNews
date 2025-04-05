package com.may.vknews

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.may.vknews.domain.Post

class CommentsViewModelFactory(
    private val post: Post
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(post) as T
    }
}