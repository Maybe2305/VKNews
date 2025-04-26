package com.may.vknews.domain

data class FeedPost(
    val id: String,
    val groupName: String,
    val communityImageUrl: String,
    val publicationDate: String,
    val contentText: String,
    val contentImageUrl: String?,
    val statistics: List<StatisticsItem>,
)
