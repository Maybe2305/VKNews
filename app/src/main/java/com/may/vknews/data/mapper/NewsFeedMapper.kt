package com.may.vknews.data.mapper

import android.annotation.SuppressLint
import com.may.vknews.data.model.CommentsResponseDto
import com.may.vknews.data.model.NewsFeedResponseDto
import com.may.vknews.domain.entity.FeedPost
import com.may.vknews.domain.entity.PostComment
import com.may.vknews.domain.entity.StatisticType
import com.may.vknews.domain.entity.StatisticsItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.absoluteValue

class NewsFeedMapper @Inject constructor() {

    fun mapResponseToPosts(responseDto: NewsFeedResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()
        val posts = responseDto.newsFeedContent?.posts ?: emptyList()
        val groups = responseDto.newsFeedContent?.groups ?: emptyList()

        for (post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: break
            val feedPost = FeedPost(
                id = post.id,
                communityId = post.communityId,
                communityName = group.name,
                publicationDate = mapTimestampToDate(post.date),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticsItem(type = StatisticType.LIKES, count = post.likes.count),
                    StatisticsItem(type = StatisticType.COMMENTS, count = post.comments.count),
                    StatisticsItem(type = StatisticType.SHARES, count = post.reposts.count),
                    StatisticsItem(type = StatisticType.VIEWS, count = post.views.count),
                ),
                isLiked = post.likes.userLikes > 0
            )

            result.add(feedPost)
        }
        return result
    }

    @SuppressLint("SuspiciousIndentation")
    fun mapResponseToComments(response: CommentsResponseDto): List<PostComment> {
        val result = mutableListOf<PostComment>()
        val comments = response.content.comments
        val profiles = response.content.profiles

        for (comment in comments) {
            if (comment.text.isBlank()) continue
                val author = profiles.firstOrNull { it.id == comment.authorId } ?: continue
                val postComment = PostComment(
                    id = comment.id,
                    authorName = "${author.firstName} ${author.lastName}",
                    authorAvatarUrl = author.avatarUrl,
                    commentText = comment.text,
                    publicationDate = mapTimestampToDate(comment.date)
                )
                result.add(postComment)
            }

        return result
    }

    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        return SimpleDateFormat("dd MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }
}