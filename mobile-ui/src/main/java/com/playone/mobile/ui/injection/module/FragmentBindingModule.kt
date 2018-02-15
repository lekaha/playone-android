package com.playone.mobile.ui.injection.module

import com.playone.mobile.ui.browse.BrowseFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector(modules =  arrayOf(
            BrowseActivityModule::class,
            BrowseModule::class))
    abstract fun provideBrowseFragment(): BrowseFragment
}