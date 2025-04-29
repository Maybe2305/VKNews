package com.may.vknews.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.may.vknews.data.mapper.NewsFeedMapper
import com.may.vknews.data.network.ApiFactory
import com.may.vknews.data.network.VKTokenProvider
import com.may.vknews.presentation.screens.AuthState
import com.may.vknews.presentation.screens.NewsFeedScreenState
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.VKIDUser
import com.vk.id.auth.VKIDAuthCallback
import com.vk.id.auth.VKIDAuthParams
import com.vk.id.auth.VKIDAuthUiParams
import com.vk.id.logout.VKIDLogoutCallback
import com.vk.id.logout.VKIDLogoutFail
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _authState = MutableLiveData<AuthState>(AuthState.Initial)
    val authState: LiveData<AuthState> = _authState

    init {
        viewModelScope.launch {
            val token = VKID.instance.accessToken

            _authState.value = if (token == null) AuthState.NotAuthorized else AuthState.Authorized

            val response = ApiFactory.apiService.loadPosts(token?.token)
            val msgError = response.error?.errorMessage ?: "Unknown error"
            if (msgError.contains("authorization failed", ignoreCase = true) ||
                msgError.contains("invalid access_token", ignoreCase = true)) {
                _authState.value = AuthState.NotAuthorized
            }


        }
    }

    fun onSuccess() {
        _authState.value = AuthState.Authorized
    }

    fun onFail(fail: String) {
        _authState.value = AuthState.NotAuthorized
    }
}