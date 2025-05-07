package com.may.vknews.presentation.custom

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.may.vknews.R
import com.may.vknews.domain.entity.FeedPost
import com.may.vknews.domain.entity.StatisticType
import com.may.vknews.domain.entity.StatisticsItem

@Composable
fun CardPost(
    feedPost: FeedPost,
    onViewClickListener: (StatisticsItem) -> Unit,
    onLikeClickListener: (StatisticsItem) -> Unit,
    onShareClickListener: (StatisticsItem) -> Unit,
    onCommentClickListener: (StatisticsItem) -> Unit
) {
    Card(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 24.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Column {
            HeadContent(feedPost)
            MainContent(feedPost)
            BottomContent(
                statistics = feedPost.statistics,
                onLikeClickListener = onLikeClickListener,
                onCommentClickListener = onCommentClickListener,
                onShareClickListener = onShareClickListener,
                onViewClickListener = onViewClickListener,
                isFavourite = feedPost.isLiked
            )
        }
    }

}

@Composable
fun HeadContent(
    feedPost: FeedPost
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = feedPost.communityImageUrl,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentDescription = stringResource(R.string.avatar)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = feedPost.communityName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = feedPost.publicationDate,
                color = Color.Gray
            )
        }
        IconButton(
            onClick = { },
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = stringResource(R.string.icon_button_more_vert),
            )
        }
    }
}

@Composable
fun MainContent(
    feedPost: FeedPost
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = feedPost.contentText
        )
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = feedPost.contentImageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentDescription = stringResource(R.string.image_akita_inu),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun BottomContent(
    statistics: List<StatisticsItem>,
    onViewClickListener: (StatisticsItem) -> Unit,
    onLikeClickListener: (StatisticsItem) -> Unit,
    onShareClickListener: (StatisticsItem) -> Unit,
    onCommentClickListener: (StatisticsItem) -> Unit,
    isFavourite: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            TextAndIcon(
                text = viewsItem.count.toString(),
                icon = Icons.Filled.Person,
                onItemClickListener = { onViewClickListener(viewsItem) }
            )
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val sharesItem = statistics.getItemByType(StatisticType.SHARES)
            TextAndIcon(
                text = sharesItem.count.toString(),
                icon = Icons.Outlined.Share,
                onItemClickListener = { onShareClickListener(sharesItem) }
            )

            Spacer(modifier = Modifier.width(16.dp))

            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            TextAndIcon(
                text = commentsItem.count.toString(),
                icon = Icons.Outlined.Create,
                onItemClickListener = { onCommentClickListener(commentsItem) }
            )

            Spacer(modifier = Modifier.width(16.dp))

            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            TextAndIcon(
                text = likesItem.count.toString(),
                icon = if (isFavourite) {
                    ImageVector.vectorResource(R.drawable.liked_svg)
                } else {
                    ImageVector.vectorResource(R.drawable.unliked_svg)
                },
                onItemClickListener = { onLikeClickListener(likesItem) },
                tint = if (isFavourite) Color.Red else Color.Gray
            )
        }

    }
}

@SuppressLint("DefaultLocale")
private fun formatStatisticCount(count: Int): String {
    return if (count > 100_000) {
        String.format("%sK", (count / 1000))
    } else if (count > 1000) {
        String.format("%.1fK", (count / 1000f))
    } else {
        count.toString()
    }
}

private fun List<StatisticsItem>.getItemByType(statisticType: StatisticType): StatisticsItem {
    return this.find { it.type == statisticType } ?: throw IllegalArgumentException()
}

@Composable
fun TextAndIcon(
    text: String,
    icon: ImageVector,
    onItemClickListener: () -> Unit,
    tint: Color = Color.Gray
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onItemClickListener() }
    ) {
        Text(text = formatStatisticCount(text.toInt()))
        Spacer(modifier = Modifier.width(2.dp))
        Icon(
            modifier = Modifier.size(25.dp),
            imageVector = icon,
            contentDescription = stringResource(R.string.image),
            tint = tint
        )
    }
}


