package com.playone.mobile.ui.injection.module

import android.content.Context
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.injection.qualifier.ActivityContext
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {

    @Provides
    fun provideNavigator(@ActivityContext context: Context) = Navigator(context)
}