package com.may.vknews.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.may.vknews.presentation.screens.AuthState
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.VKIDUser
import com.vk.id.auth.VKIDAuthCallback
import com.vk.id.auth.VKIDAuthParams
import com.vk.id.auth.VKIDAuthUiParams
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _authState = MutableLiveData<AuthState>(AuthState.Initial)
    val authState: LiveData<AuthState> = _authState

    val vkid = VKID

    init {
        val token = vkid.instance.accessToken
        _authState.value = if (token == null) AuthState.NotAuthorized else AuthState.Authorized
    }

    fun onSuccess() {
        _authState.value = AuthState.Authorized
    }

    fun onFail(fail: String) {
        _authState.value = AuthState.NotAuthorized
    }
}