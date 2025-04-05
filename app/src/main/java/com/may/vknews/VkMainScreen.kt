package com.may.vknews

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.may.vknews.domain.Post
import com.may.vknews.navigation.AppNavGraph
import com.may.vknews.navigation.NavigationState
import com.may.vknews.navigation.Screen
import com.may.vknews.navigation.rememberNavigationState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {

    val navigationState = rememberNavigationState()

    Scaffold(bottomBar = {
        BottomBar(navigationState)
    }) {

        AppNavGraph(
            navController = navigationState.navHostController,
            feedPostsContent = {
                HomeScreen(
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
                    post = post
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