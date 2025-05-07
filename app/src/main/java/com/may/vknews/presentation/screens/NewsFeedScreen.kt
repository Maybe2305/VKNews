package com.may.vknews.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.may.vknews.presentation.custom.CardPost
import com.may.vknews.presentation.viewmodel.NewsFeedViewModel
import com.may.vknews.domain.entity.FeedPost
import com.may.vknews.presentation.ViewModelFactory
import com.may.vknews.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NewsFeedScreen(
    viewModelFactory: ViewModelFactory,
    paddingValues: PaddingValues,
    onCommentClickListener: (FeedPost) -> Unit
) {

    val viewModelNewsFeed: NewsFeedViewModel = viewModel(factory = viewModelFactory)
    val viewModelAuth: MainViewModel = viewModel(factory = viewModelFactory)
    val screenState = viewModelNewsFeed.screenState.collectAsState(NewsFeedScreenState.Initial)


    when(val currentScreenState = screenState.value) {
        is NewsFeedScreenState.Posts -> {
            PostsScreen(
                viewModel = viewModelNewsFeed,
                paddingValues = paddingValues,
                feedPosts = currentScreenState.feedPosts,
                onCommentClickListener = onCommentClickListener,
                nextDataIsLoading = currentScreenState.nextDataIsLoading
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

        NewsFeedScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Gray)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PostsScreen(
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    feedPosts: List<FeedPost>,
    onCommentClickListener: (FeedPost) -> Unit,
    nextDataIsLoading: Boolean
) {

    val context = LocalContext.current

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
                        Toast.makeText(
                            context,
                            "Данный функционал в разработке...",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onShareClickListener = {
                        Toast.makeText(
                            context,
                            "Данный функционал в разработке...",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onCommentClickListener = {
                        onCommentClickListener(post)
                    },
                    onLikeClickListener = {
                        viewModel.changeLikeStatus(post)
                    }
                )
            }
        }
        item {
            if (nextDataIsLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.Gray
                    )
                }
            } else {
                SideEffect {
                    viewModel.loadNextPosts()
                }
            }
        }
    }
}