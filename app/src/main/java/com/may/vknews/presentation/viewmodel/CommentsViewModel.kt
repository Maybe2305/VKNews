package com.may.vknews.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.may.vknews.data.repository.NewsFeedRepositoryImpl
import com.may.vknews.presentation.screens.CommentsScreenState
import com.may.vknews.domain.entity.FeedPost
import com.may.vknews.domain.usecases.GetCommentsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val feedPost: FeedPost,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

    val screenState = getCommentsUseCase(feedPost)
        .map { CommentsScreenState.Comments(
            feedPost = feedPost,
            comments = it
        ) }
}

