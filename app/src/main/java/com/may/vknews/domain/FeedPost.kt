package com.may.vknews.domain

data class FeedPost(
    val id: Long,
    val communityName: String,
    val communityId: Long,
    val communityImageUrl: String,
    val publicationDate: String,
    val contentText: String,
    val contentImageUrl: String?,
    val statistics: List<StatisticsItem>,
    val isLiked: Boolean
)
