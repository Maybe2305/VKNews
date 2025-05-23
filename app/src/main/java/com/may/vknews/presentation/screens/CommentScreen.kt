package com.may.vknews.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.may.vknews.presentation.viewmodel.CommentsViewModel
import com.may.vknews.domain.entity.FeedPost
import com.may.vknews.domain.entity.PostComment
import com.may.vknews.presentation.NewsFeedApplication
import com.may.vknews.presentation.ViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentScreen(
    onBackPressed: () -> Unit,
    feedPost: FeedPost
) {

    val component = (LocalContext.current.applicationContext as NewsFeedApplication)
        .component
        .getCommentsScreenComponentFactory()
        .create(feedPost)

    val viewModel: CommentsViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
    val screenState = viewModel.screenState.collectAsState(CommentsScreenState.Initial)
    val currentState = screenState.value

    if (currentState is CommentsScreenState.Comments) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Комментарии:")
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
            },

            ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 8.dp,
                    top = 16.dp,
                    bottom = 72.dp
                )
            ) {
                items(currentState.comments) { comment ->
                    CommentScreenCard(comment)
                }
            }
        }
    }
}

@Composable
fun CommentScreenCard(
    postComment: PostComment
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = postComment.authorAvatarUrl,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop

        )

        Spacer(Modifier.width(12.dp))

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    text = postComment.authorName,
                    fontSize = 12.sp,
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = postComment.commentText,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = postComment.publicationDate,
                color = Color.LightGray,
                fontSize = 10.sp
            )
        }

    }
}