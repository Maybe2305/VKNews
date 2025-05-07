package com.may.vknews.presentation

import android.app.Application
import com.may.di.ApplicationComponent
import com.may.di.DaggerApplicationComponent
import com.may.vknews.domain.entity.FeedPost

class NewsFeedApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}