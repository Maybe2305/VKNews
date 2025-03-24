package com.may.vknews

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
) {
    val posts = viewModel.posts.observeAsState(listOf())

    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        items(
            items = posts.value,
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
                        viewModel.updateCountStatisticItem(post, it)
                    },
                    onLikeClickListener = {
                        viewModel.updateCountStatisticItem(post, it)
                    }
                )
            }
        }
    }
}
