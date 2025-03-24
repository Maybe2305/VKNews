package com.may.vknews

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.may.vknews.navigation.Screen

sealed class BottomAppBarItem(
    val screen: Screen,
    val labelResId: Int,
    val icon: ImageVector,
) {
    object Home : BottomAppBarItem(
        screen = Screen.HomeScreen,
        labelResId = R.string.bottom_bar_item_home,
        icon = Icons.Outlined.Home
    )

    object Favourite : BottomAppBarItem(
        screen = Screen.FavouriteScreen,
        labelResId = R.string.bottom_bar_item_favourite,
        icon = Icons.Outlined.Favorite
    )

    object Profile : BottomAppBarItem(
        screen = Screen.ProfileScreen,
        labelResId = R.string.bottom_bar_item_profile,
        icon = Icons.Outlined.Person
    )
}