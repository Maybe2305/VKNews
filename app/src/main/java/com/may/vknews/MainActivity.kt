package com.may.vknews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.may.vknews.ui.theme.VKNewsTheme
import com.may.vknews.domain.entity.AuthState
import com.may.vknews.presentation.NewsFeedApplication
import com.may.vknews.presentation.ViewModelFactory
import com.may.vknews.presentation.getApplicationComponent
import com.may.vknews.presentation.screens.LoginScreen
import com.may.vknews.presentation.screens.MainScreen
import com.may.vknews.presentation.viewmodel.MainViewModel
import com.vk.id.VKID
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        VKID.init(this)

        super.onCreate(savedInstanceState)
        setContent {
            val component = getApplicationComponent()
            val viewModel: MainViewModel = viewModel(factory = component.getViewModelFactory())
            val authState = viewModel.authState.collectAsState(AuthState.Initial)

            VKNewsTheme {
                when(authState.value) {
                    is AuthState.Initial -> {}
                    is AuthState.NotAuthorized -> {
                        LoginScreen {
                            viewModel.onSuccess()
                        }
                    }
                    is AuthState.Authorized -> {
                        MainScreen()
                    }
                }
            }
        }
    }
}

