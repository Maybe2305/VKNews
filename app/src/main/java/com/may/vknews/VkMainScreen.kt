package com.may.vknews

import android.annotation.SuppressLint
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    Scaffold(
        bottomBar = {
            BottomBar()
        }
    ) {
        CardPost()
    }
}

@Composable
fun BottomBar() {
    NavigationBar {
        val selectedItem = remember {
            mutableStateOf(0)
        }
        val items = listOf(
            BottomAppBarItem.Home,
            BottomAppBarItem.Favourite,
            BottomAppBarItem.Profile
        )

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem.value == index,
                onClick = { selectedItem.value = index },
                icon = { Icon(imageVector = item.icon, contentDescription = null) },
                label = { stringResource(item.labelResId) }
            )
        }
    }
}