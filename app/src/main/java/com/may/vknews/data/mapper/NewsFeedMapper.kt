package com.may.vknews.data.mapper

import com.may.vknews.data.model.NewsFeedResponseDto
import com.may.vknews.domain.FeedPost
import com.may.vknews.domain.StatisticType
import com.may.vknews.domain.StatisticsItem
import kotlin.math.absoluteValue

class NewsFeedMapper {

    fun mapResponseToPosts(responseDto: NewsFeedResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()
        val posts = responseDto.newsFeedContent?.posts ?: emptyList()
        val groups = responseDto.newsFeedContent?.groups ?: emptyList()

        for (post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: break
            val feedPost = FeedPost(
                id = post.id,
                groupName = group.name,
                publicationDate = post.date.toString(),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticsItem(type = StatisticType.LIKES, count = post.likes.count),
                    StatisticsItem(type = StatisticType.COMMENTS, count = post.comments.count),
                    StatisticsItem(type = StatisticType.SHARES, count = post.reposts.count),
                    StatisticsItem(type = StatisticType.VIEWS, count = post.views.count),
                )
            )

            result.add(feedPost)
        }
        return result
    }
}