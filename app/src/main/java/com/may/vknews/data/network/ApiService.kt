package com.may.vknews.data.network

import com.may.vknews.data.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("newsfeed.get")
    suspend fun loadPosts(
        @Query("access_token") token: String,
        @Query("filters") filters: String = "post",
        @Query("v") version: String = "5.154"
    ): NewsFeedResponseDto
}