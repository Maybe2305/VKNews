package com.may.vknews

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.may.vknews.domain.Post
import com.may.vknews.domain.PostComment

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onCommentClickListener: (Post) -> Unit
) {

    val viewModel: NewsFeedViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(NewsFeedScreenState.Initial)

    when(val currentScreenState = screenState.value) {
        is NewsFeedScreenState.Posts -> {
            PostsScreen(
                viewModel = viewModel,
                paddingValues = paddingValues,
                posts = currentScreenState.posts,
                onCommentClickListener = onCommentClickListener
            )
        }

        NewsFeedScreenState.Initial -> {

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PostsScreen(
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    posts: List<Post>,
    onCommentClickListener: (Post) -> Unit
) {


    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        items(
            items = posts,
            key = { it.id }
        ) { post ->
            val dismissState = rememberSwipeToDismissBoxState()
            SwipeToDismissBox(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                backgroundContent = {  },
                enableDismissFromEndToStart = true,
                enableDismissFromStartToEnd = false
            ) {
                if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                    viewModel.remove(post)
                }
                CardPost(
                    post = post,
                    onViewClickListener = {
                        viewModel.updateCountStatisticItem(post, it)
                    },
                    onShareClickListener = {
                        viewModel.updateCountStatisticItem(post, it)
                    },
                    onCommentClickListener = {
                        onCommentClickListener(post)
                    },
                    onLikeClickListener = {
                        viewModel.updateCountStatisticItem(post, it)
                    }
                )
            }
        }
    }
}