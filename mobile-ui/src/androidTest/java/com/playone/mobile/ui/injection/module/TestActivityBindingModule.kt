package com.playone.mobile.ui.injection.module

import android.content.Context
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.browse.BrowseActivity
import com.playone.mobile.ui.injection.qualifier.ActivityContext
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TestActivityBindingModule {

    @ContributesAndroidInjector(
        modules = [ActivityModule::class, TestFragmentBindingModule::class]
    )
    abstract fun bindMainActivity(): BrowseActivity

    @Binds
    abstract fun bindMainActivity(activity: BrowseActivity): BaseActivity

    @Binds
    @ActivityContext
    abstract fun provideActivityContext(activity: BaseActivity): Context
}