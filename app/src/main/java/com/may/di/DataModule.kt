package com.may.di

import com.may.vknews.data.network.ApiFactory
import com.may.vknews.data.network.ApiService
import com.may.vknews.data.repository.NewsFeedRepositoryImpl
import com.may.vknews.domain.repository.NewsFeedRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }

}