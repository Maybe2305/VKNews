package com.may.vknews.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navController: NavHostController,
    homeContent: @Composable () -> Unit,
    favouriteContent: @Composable () -> Unit,
    profileContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
    ) {
        composable(Screen.HomeScreen.route) {
            homeContent()
        }

        composable(Screen.FavouriteScreen.route) {
            favouriteContent()
        }

        composable(Screen.ProfileScreen.route) {
            profileContent()
        }
    }
}