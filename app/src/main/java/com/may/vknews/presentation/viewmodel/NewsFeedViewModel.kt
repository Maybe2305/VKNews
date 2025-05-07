package com.may.vknews.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.may.extensions.mergeWith
import com.may.vknews.data.repository.NewsFeedRepositoryImpl
import com.may.vknews.domain.entity.FeedPost
import com.may.vknews.domain.usecases.ChangeLikeStatusUseCase
import com.may.vknews.domain.usecases.DeletePostUseCase
import com.may.vknews.domain.usecases.GetPostsUseCase
import com.may.vknews.domain.usecases.LoadNextDataUseCase
import com.may.vknews.presentation.screens.NewsFeedScreenState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val loadNextDataUseCase: LoadNextDataUseCase,
    private val changeLikeStatusUseCase: ChangeLikeStatusUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("NewsFeedViewModel", "Exception caught by exceptionHandler")
    }

    private val postsFlow = getPostsUseCase()

    private val loadNextDataFlow = MutableSharedFlow<NewsFeedScreenState>()

    val screenState = postsFlow
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.Posts(feedPosts = it) as NewsFeedScreenState }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)


    fun loadNextPosts() {
        viewModelScope.launch {
            loadNextDataFlow.emit(
                NewsFeedScreenState.Posts(
                    feedPosts = postsFlow.value,
                    nextDataIsLoading = true,
                )
            )
            loadNextDataUseCase()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUseCase(feedPost)
        }
    }

    fun remove(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            deletePostUseCase(feedPost)
        }
    }
}