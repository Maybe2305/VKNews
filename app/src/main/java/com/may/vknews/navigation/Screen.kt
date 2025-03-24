package com.may.vknews.navigation

sealed class Screen(
    val route: String
) {
    object HomeScreen: Screen(HOME_ROUTE)
    object FavouriteScreen: Screen(FAVOURITE_ROUTE)
    object ProfileScreen: Screen(PROFILE_ROUTE)

    companion object {
        const val HOME_ROUTE = "home"
        const val FAVOURITE_ROUTE = "favourite"
        const val PROFILE_ROUTE = "profile"
    }
}