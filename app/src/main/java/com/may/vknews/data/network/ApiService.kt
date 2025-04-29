package com.may.vknews.data.network

import com.may.vknews.data.model.CommentsResponseDto
import com.may.vknews.data.model.LikesCountResponseDto
import com.may.vknews.data.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("newsfeed.get")
    suspend fun loadPosts(
        @Query("access_token") token: String?,
        @Query("filters") filters: String = "post",
        @Query("v") version: String = "5.199"
    ): NewsFeedResponseDto

    @GET("newsfeed.get")
    suspend fun loadPosts(
        @Query("access_token") token: String?,
        @Query("start_from") startFrom: String,
        @Query("filters") filters: String = "post",
        @Query("v") version: String = "5.199"
    ): NewsFeedResponseDto

    @GET("likes.add")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("type") type: String = "post",
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long,
        @Query("v") version: String = "5.199"
    ): LikesCountResponseDto

    @GET("likes.delete")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("type") type: String = "post",
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long,
        @Query("v") version: String = "5.199"
    ): LikesCountResponseDto

    @GET("newsfeed.ignoreItem?v=5.199")
    suspend fun deletePost(
        @Query("access_token") token: String,
        @Query("type") type: String = "wall",
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long,
    ): NewsFeedResponseDto

    @GET("wall.getComments?v=5.199&extended=1&fields=photo_100")
    suspend fun getComments(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("post_id") postId: Long,
    ): CommentsResponseDto
}