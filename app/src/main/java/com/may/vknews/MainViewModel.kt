package com.may.vknews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.may.vknews.domain.Post
import com.may.vknews.domain.StatisticsItem

class MainViewModel : ViewModel() {

    private val sourceList = mutableListOf<Post>().apply {
        repeat(10) {
            add(Post(id = it))
        }
    }

    private val _posts = MutableLiveData<List<Post>>(sourceList)
    val posts: LiveData<List<Post>> = _posts

    fun updateCountStatisticItem(post: Post, item: StatisticsItem) {
        val oldPosts = _posts.value?.toMutableList() ?: mutableListOf()
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
        _posts.value = oldPosts.apply {
            replaceAll {
                if (it.id == newPost.id) {
                    newPost
                } else {
                    it
                }
            }
        }
    }

    fun remove(post: Post) {
        val oldPosts = _posts.value?.toMutableList() ?: mutableListOf()
        oldPosts.remove(post)
        _posts.value = oldPosts
    }
}