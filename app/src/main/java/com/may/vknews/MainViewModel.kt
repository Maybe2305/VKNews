package com.may.vknews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.may.vknews.domain.Post
import com.may.vknews.domain.StatisticsItem

class MainViewModel : ViewModel() {

    private val _postState = MutableLiveData(Post())
    val postState: LiveData<Post> = _postState

    fun updateCountStatisticItem(item: StatisticsItem) {
        val oldStatistics = _postState.value?.statistics ?: throw IllegalStateException()
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        _postState.value = postState.value?.copy(statistics = newStatistics)
    }
}