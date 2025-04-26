package com.may.vknews.presentation.screens

import com.vk.id.AccessToken

sealed class AuthState {

    object Authorized : AuthState()

    object NotAuthorized : AuthState()

    object Initial : AuthState()
}