package com.may.vknews.data.repository

import com.may.extensions.mergeWith
import com.may.vknews.data.mapper.NewsFeedMapper
import com.may.vknews.data.network.ApiFactory
import com.may.vknews.data.network.ApiService
import com.may.vknews.data.network.VKTokenProvider
import com.may.vknews.domain.entity.FeedPost
import com.may.vknews.domain.entity.PostComment
import com.may.vknews.domain.entity.StatisticType
import com.may.vknews.domain.entity.StatisticsItem
import com.may.vknews.domain.entity.AuthState
import com.may.vknews.domain.repository.NewsFeedRepository
import com.vk.id.VKID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: NewsFeedMapper
) : NewsFeedRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()

    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)

    private val authStateFlow = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect {
            val token = VKID.instance.accessToken

            val loggedIn = if (token == null) AuthState.NotAuthorized else AuthState.Authorized
            emit(loggedIn)

            val response = ApiFactory.apiService.loadPosts(token?.token)
            val msgError = response.error?.errorMessage ?: "Unknown error"
            if (msgError.contains("authorization failed", ignoreCase = true) ||
                msgError.contains("invalid access_token", ignoreCase = true)
            ) {
                emit(AuthState.NotAuthorized)
            }
        }


    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    private val posts: StateFlow<List<FeedPost>> = flow {
        nextDataNeededEvents.emit(Unit)

        nextDataNeededEvents.collect {
            val startFrom = nextFrom

            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }

            val response = if (startFrom == null) {
                apiService.loadPosts(VKTokenProvider.requireToken())
            } else {
                apiService.loadPosts(token = VKTokenProvider.requireToken(), startFrom = startFrom)
            }
            nextFrom = response.newsFeedContent?.nextFrom
            val posts = mapper.mapResponseToPosts(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }
        .retry {
            delay(RETRY_TIMEOUT_MILLIS)
            true
        }.mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow

    override fun getPosts(): StateFlow<List<FeedPost>> = posts

    override suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    override suspend fun deletePost(feedPost: FeedPost) {
        apiService.deletePost(
            token = VKTokenProvider.requireToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }


    override fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>> = flow {
        val comments = apiService.getComments(
            token = VKTokenProvider.requireToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )

        emit(mapper.mapResponseToComments(comments))
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    override suspend fun checkAuthState() {
        checkAuthStateEvents.emit(Unit)
    }

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = VKTokenProvider.requireToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = VKTokenProvider.requireToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }

        val newLikesCount = response.likes?.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticsItem(type = StatisticType.LIKES, newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
        refreshedListFlow.emit(feedPosts)
    }

    companion object {
        private const val RETRY_TIMEOUT_MILLIS = 3000L
    }
}