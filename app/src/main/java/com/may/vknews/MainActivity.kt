package com.may.vknews

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.may.vknews.ui.theme.VKNewsTheme
import com.may.vknews.presentation.screens.AuthState
import com.may.vknews.presentation.screens.LoginScreen
import com.may.vknews.presentation.screens.MainScreen
import com.may.vknews.presentation.viewmodel.MainViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthCallback
import com.vk.id.auth.VKIDAuthParams
import com.vk.id.auth.VKIDAuthUiParams

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        VKID.init(this)

        super.onCreate(savedInstanceState)
        setContent {
            VKNewsTheme {
                val viewModel: MainViewModel = viewModel()
                val authState = viewModel.authState.observeAsState(AuthState.Initial)

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

