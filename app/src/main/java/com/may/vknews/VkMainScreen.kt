package com.may.vknews

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.may.vknews.domain.Post

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    Scaffold(
        bottomBar = {
            BottomBar()
        }
    ) {
        val posts = viewModel.posts.observeAsState(listOf())


        LazyColumn(
            modifier = Modifier.padding(it)
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
}

@Composable
fun BottomBar() {
    NavigationBar {
        val selectedItem = remember {
            mutableStateOf(0)
        }
        val items = listOf(
            BottomAppBarItem.Home,
            BottomAppBarItem.Favourite,
            BottomAppBarItem.Profile
        )

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem.value == index,
                onClick = { selectedItem.value = index },
                icon = { Icon(imageVector = item.icon, contentDescription = null) },
                label = { stringResource(item.labelResId) }
            )
        }
    }
}