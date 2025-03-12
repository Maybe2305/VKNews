package com.may.vknews.domain

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.may.vknews.R

data class Post(
    val id: Int,
    val groupName: Int = R.string.group_name,
    val avatarImageResId: Int = R.drawable.bigel,
    val publicationDate: Int = R.string.time,
    val contentText: Int = R.string.main_content,
    val contentImageResId: Int = R.drawable.akita_inu,
    val statistics: List<StatisticsItem> = listOf(
        StatisticsItem(type = StatisticType.VIEWS, count = 966),
        StatisticsItem(type = StatisticType.SHARES, count = 7),
        StatisticsItem(type = StatisticType.COMMENTS, count = 8),
        StatisticsItem(type = StatisticType.LIKES, count = 27)
    )
)
