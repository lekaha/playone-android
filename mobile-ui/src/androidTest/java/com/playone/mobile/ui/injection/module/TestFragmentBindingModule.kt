package com.playone.mobile.ui.injection.module

import com.playone.mobile.ui.browse.BrowseFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TestFragmentBindingModule {

    @ContributesAndroidInjector(
        modules = [BrowseModule::class, TestBrowseActivityModule::class]
    )
    abstract fun provideBrowseFragment(): BrowseFragment
}