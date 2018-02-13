package com.playone.android.ui.injection.module

import com.playone.android.ui.browse.BrowseActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules =
        arrayOf(FragmentBindingModule::class))
    abstract fun bindMainActivity(): BrowseActivity

}