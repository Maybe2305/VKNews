package com.may.vknews

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomAppBarItem(
    val labelResId: Int,
    val icon: ImageVector,
) {
    object Home : BottomAppBarItem(
        labelResId = R.string.bottom_bar_item_home,
        icon = Icons.Outlined.Home
    )

    object Favourite : BottomAppBarItem(
        labelResId = R.string.bottom_bar_item_favourite,
        icon = Icons.Outlined.Favorite
    )

    object Profile : BottomAppBarItem(
        labelResId = R.string.bottom_bar_item_profile,
        icon = Icons.Outlined.Person
    )
}