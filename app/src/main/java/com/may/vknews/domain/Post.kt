package com.may.vknews.domain

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.may.vknews.R

data class Post(
    val id: Int = 0,
    val groupName: String = "Dogs Club",
    val avatarImageResId: Int = R.drawable.bigel,
    val publicationDate: Int = R.string.time,
    val contentText: String = "У меня есть щенок. Его зовут Барбос. Он красивый, добрый и верный. Мы любим бегать по двору и играть. Он спит на коврике в коридоре со своей любимой игрушкой — зайцем. Я люблю его кормить. Барбос кушает специальный корм для собак. Моя собака — самая лучшая, я никому не дам её в обиду. ",
    val contentImageResId: Int = R.drawable.akita_inu,
    val statistics: List<StatisticsItem> = listOf(
        StatisticsItem(type = StatisticType.VIEWS, count = 966),
        StatisticsItem(type = StatisticType.SHARES, count = 7),
        StatisticsItem(type = StatisticType.COMMENTS, count = 8),
        StatisticsItem(type = StatisticType.LIKES, count = 27)
    )
)
