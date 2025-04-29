package com.may.vknews.data.repository

import com.may.vknews.data.mapper.NewsFeedMapper
import com.may.vknews.data.network.ApiFactory
import com.may.vknews.data.network.VKTokenProvider
import com.may.vknews.domain.FeedPost
import com.may.vknews.domain.PostComment
import com.may.vknews.domain.StatisticType
import com.may.vknews.domain.StatisticsItem

class NewsFeedRepository {
    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    suspend fun loadPosts(): List<FeedPost> {
        val startFrom = nextFrom

        if (startFrom == null && feedPosts.isNotEmpty()) return feedPosts

        val response = if (startFrom == null) {
            apiService.loadPosts(VKTokenProvider.requireToken())
        } else {
            apiService.loadPosts(token = VKTokenProvider.requireToken(), startFrom = startFrom)
        }
        nextFrom = response.newsFeedContent?.nextFrom
        val posts = mapper.mapResponseToPosts(response)
        _feedPosts.addAll(posts)
        return feedPosts
    }

    suspend fun deletePost(feedPost: FeedPost) {
        apiService.deletePost(
            token = VKTokenProvider.requireToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
    }

    suspend fun getComments(feedPost: FeedPost): List<PostComment> {
        val comments = apiService.getComments(
            token = VKTokenProvider.requireToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )

        return mapper.mapResponseToComments(comments)
    }

    suspend fun changeLikeStatus(feedPost: FeedPost) {
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
    }
}