package com.may.vknews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.may.vknews.domain.Post
import com.may.vknews.domain.StatisticsItem

class NewsFeedViewModel : ViewModel() {

    private val sourceList = mutableListOf<Post>().apply {
        repeat(10) {
            add(Post(id = it))
        }
    }

    val initialState = NewsFeedScreenState.Posts(sourceList)

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    fun updateCountStatisticItem(post: Post, item: StatisticsItem) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return
        val oldPosts = currentState.posts.toMutableList()
        val oldStatistics = post.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        val newPost = post.copy(statistics = newStatistics)
        val newPosts = oldPosts.apply {
            replaceAll {
                if (it.id == newPost.id) {
                    newPost
                } else {
                    it
                }
            }
        }

        _screenState.value = NewsFeedScreenState.Posts(posts = newPosts)
    }

    fun remove(post: Post) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(post)
        _screenState.value = NewsFeedScreenState.Posts(posts = oldPosts)
    }
}