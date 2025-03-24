package com.may.vknews

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.may.vknews.navigation.AppNavGraph
import com.may.vknews.navigation.NavigationState
import com.may.vknews.navigation.rememberNavigationState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: MainViewModel
) {

    val navigationState = rememberNavigationState()

    Scaffold(bottomBar = {
        BottomBar(navigationState)
    }) {

        AppNavGraph(
            navController = navigationState.navHostController,
            homeContent = { HomeScreen(viewModel = viewModel, paddingValues = it) },
            favouriteContent = { Text(text = "This is Favourite Screen") },
            profileContent = { Text(text = "This is Profile Screen") },
        )

    }
}

@Composable
fun BottomBar(
    navigationState: NavigationState
) {



    NavigationBar {

        val navBackStackEntry = navigationState.navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        val items = listOf(
            BottomAppBarItem.Home, BottomAppBarItem.Favourite, BottomAppBarItem.Profile
        )

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = { navigationState.navigateTo(item.screen.route) },
                icon = { Icon(imageVector = item.icon, contentDescription = null) },
                label = { Text(text = stringResource(item.labelResId)) },
            )
        }
    }
}