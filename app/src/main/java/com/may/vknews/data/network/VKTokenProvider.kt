package com.may.vknews.data.network

import com.vk.id.VKID

object VKTokenProvider {
    fun requireToken(): String {
        return VKID.instance.accessToken?.token ?: throw IllegalStateException("Token is null")
    }
}