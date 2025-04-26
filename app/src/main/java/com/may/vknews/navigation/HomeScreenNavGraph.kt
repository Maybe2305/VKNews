package com.may.vknews.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.may.vknews.domain.FeedPost

fun NavGraphBuilder.homeScreenNavGraph(
    feedPostsContent: @Composable () -> Unit,
    commentsContent: @Composable (FeedPost) -> Unit,
) {
    navigation(
        startDestination = Screen.FeedPostsScreen.route,
        route = Screen.HomeScreen.route
    ) {
        composable(Screen.FeedPostsScreen.route) {
            feedPostsContent()
        }

        composable(
            route = Screen.CommentsScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_POST) {
                    type = NavType.StringType
                },
            )
        ) {
            val postJson = it.arguments?.getString(Screen.KEY_POST) ?: ""
            val feedPost = Gson().fromJson(postJson, FeedPost::class.java)
            commentsContent(feedPost)
        }
    }
}