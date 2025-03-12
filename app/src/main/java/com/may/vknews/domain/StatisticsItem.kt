package com.may.vknews.domain

data class StatisticsItem(
    val type: StatisticType,
    val count: Int = 0,
)

enum class StatisticType {
    VIEWS, COMMENTS, SHARES, LIKES
}
