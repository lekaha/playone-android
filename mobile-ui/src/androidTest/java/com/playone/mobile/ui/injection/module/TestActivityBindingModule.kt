package com.playone.mobile.ui.injection.module

import android.content.Context
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.injection.qualifier.ActivityContext
import com.playone.mobile.ui.playone.PlayoneActivity
import dagger.Binds
import dagger.Module

@Module
abstract class TestActivityBindingModule {

    @Binds
    abstract fun bindPlayoneActivity(activity: PlayoneActivity): BaseActivity

    @Binds
    @ActivityContext
    abstract fun provideActivityContext(activity: BaseActivity): Context
}