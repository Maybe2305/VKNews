package com.may.vknews.presentation.screens

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
import com.may.vknews.presentation.custom.CardPost
import com.may.vknews.presentation.viewmodel.NewsFeedViewModel
import com.may.vknews.domain.FeedPost
import com.may.vknews.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NewsFeedScreen(
    paddingValues: PaddingValues,
    onCommentClickListener: (FeedPost) -> Unit
) {

    val viewModelNewsFeed: NewsFeedViewModel = viewModel()
    val viewModelAuth: MainViewModel = viewModel()
    val screenState = viewModelNewsFeed.screenState.observeAsState(NewsFeedScreenState.Initial)

    when(val currentScreenState = screenState.value) {
        is NewsFeedScreenState.Posts -> {
            PostsScreen(
                viewModel = viewModelNewsFeed,
                paddingValues = paddingValues,
                feedPosts = currentScreenState.feedPosts,
                onCommentClickListener = onCommentClickListener
            )
        }

        is NewsFeedScreenState.Error -> {

        }

        is NewsFeedScreenState.LogOut -> {
            LoginScreen {
                viewModelAuth.onSuccess()
            }
        }

        is NewsFeedScreenState.Initial -> {

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PostsScreen(
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    feedPosts: List<FeedPost>,
    onCommentClickListener: (FeedPost) -> Unit
) {


    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        items(
            items = feedPosts,
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
                    feedPost = post,
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