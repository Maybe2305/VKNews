package com.may.vknews.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.may.vknews.domain.entity.FeedPost

@Composable
fun AppNavGraph(
    navController: NavHostController,
    feedPostsContent: @Composable () -> Unit,
    commentsContent: @Composable (FeedPost) -> Unit,
    favouriteContent: @Composable () -> Unit,
    profileContent: @Composable () -> Unit,

    ) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
    ) {

        homeScreenNavGraph(
            feedPostsContent = feedPostsContent,
            commentsContent = commentsContent
        )

        composable(Screen.FavouriteScreen.route) {
            favouriteContent()
        }

        composable(Screen.ProfileScreen.route) {
            profileContent()
        }
    }
}