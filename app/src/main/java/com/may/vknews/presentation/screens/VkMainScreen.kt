package com.may.vknews.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.may.vknews.domain.BottomAppBarItem
import com.may.vknews.navigation.AppNavGraph
import com.may.vknews.navigation.NavigationState
import com.may.vknews.navigation.rememberNavigationState
import com.may.vknews.presentation.ViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModelFactory: ViewModelFactory) {

    val navigationState = rememberNavigationState()

    Scaffold(bottomBar = {
        BottomBar(navigationState)
    }) {

        AppNavGraph(
            navController = navigationState.navHostController,
            feedPostsContent = {
                NewsFeedScreen(
                    viewModelFactory = viewModelFactory,
                    paddingValues = it,
                    onCommentClickListener = {
                        navigationState.navigateToComments(it)
                    }
                )
            },
            favouriteContent = { Text(text = "This is Favourite Screen") },
            profileContent = { Text(text = "This is Profile Screen") },
            commentsContent = { post ->
                CommentScreen(
                    onBackPressed = { navigationState.navHostController.popBackStack() },
                    feedPost = post
                )
            }
        )

    }
}

@Composable
fun BottomBar(
    navigationState: NavigationState
) {

    NavigationBar {

        val navBackStackEntry = navigationState.navHostController.currentBackStackEntryAsState()

        val items = listOf(
            BottomAppBarItem.Home, BottomAppBarItem.Favourite, BottomAppBarItem.Profile
        )

        items.forEach { item ->

            val selected = navBackStackEntry.value?.destination?.hierarchy?.any {
                it.route == item.screen.route
            } ?: false

            NavigationBarItem(
                selected = selected,
                onClick = { navigationState.navigateTo(item.screen.route) },
                icon = { Icon(imageVector = item.icon, contentDescription = null) },
                label = { Text(text = stringResource(item.labelResId)) },
            )
        }
    }
}