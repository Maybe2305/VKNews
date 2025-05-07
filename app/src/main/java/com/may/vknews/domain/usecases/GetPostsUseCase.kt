package com.may.vknews.domain.usecases

import com.may.vknews.domain.entity.FeedPost
import com.may.vknews.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(): StateFlow<List<FeedPost>> {
        return repository.getPosts()
    }
}