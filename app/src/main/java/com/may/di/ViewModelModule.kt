package com.may.di

import androidx.lifecycle.ViewModel
import com.may.vknews.presentation.viewmodel.CommentsViewModel
import com.may.vknews.presentation.viewmodel.MainViewModel
import com.may.vknews.presentation.viewmodel.NewsFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    @Binds
    fun bindNewsFeedViewModel(viewModel: NewsFeedViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}