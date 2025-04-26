package com.may.vknews.navigation

import android.net.Uri
import com.google.gson.Gson
import com.may.vknews.domain.FeedPost

sealed class Screen(
    val route: String
) {
    object FeedPostsScreen: Screen(FEED_POSTS_ROUTE)
    object FavouriteScreen: Screen(FAVOURITE_ROUTE)
    object ProfileScreen: Screen(PROFILE_ROUTE)
    object HomeScreen: Screen(HOME_ROUTE)
    object CommentsScreen: Screen(COMMENTS_ROUTE)
     {

        private const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(feedPost: FeedPost): String {
            val postJson = Gson().toJson(feedPost)
            return "$ROUTE_FOR_ARGS/${postJson.encode()}"
        }
    }

    companion object {
        const val KEY_POST = "post"

        const val HOME_ROUTE = "home"
        const val COMMENTS_ROUTE = "comments/{$KEY_POST}"
        const val FEED_POSTS_ROUTE = "feed_posts"
        const val FAVOURITE_ROUTE = "favourite"
        const val PROFILE_ROUTE = "profile"
    }
}

fun String.encode(): String {
    return Uri.encode(this)
}