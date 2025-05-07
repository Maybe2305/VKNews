package com.may.vknews.domain.usecases

import com.may.vknews.domain.entity.AuthState
import com.may.vknews.domain.entity.FeedPost
import com.may.vknews.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(): StateFlow<AuthState> {
        return repository.getAuthStateFlow()
    }
}