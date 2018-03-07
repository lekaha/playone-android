package com.playone.mobile.ui.injection.module

import android.content.Context
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.injection.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class NavigatorModule {

    @Provides
    fun provideNavigator(@ApplicationContext context: Context) = Navigator(context)
}