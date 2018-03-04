package com.playone.mobile.ui.injection.module

import android.content.Context
import com.playone.mobile.ui.BaseActivity
import com.playone.mobile.ui.browse.BrowseActivity
import com.playone.mobile.ui.injection.qualifier.ActivityContext
import com.playone.mobile.ui.playone.PlayoneActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(
        modules = [
            ActivityModule::class,
            FragmentBindingModule::class])
    abstract fun bindBrowseActivity(): BrowseActivity

    @ContributesAndroidInjector(
        modules = [
            PlayoneModule::class,
            LoginModule::class])
    abstract fun bindMainActivity(): PlayoneActivity

    @Binds
    abstract fun bindMainActivity(activity: BrowseActivity): BaseActivity

    @Binds
    @ActivityContext
    abstract fun provideActivityContext(activity: BaseActivity): Context

}