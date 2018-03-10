package com.playone.mobile.ui.injection.module

import com.playone.mobile.ui.browse.BrowseActivity
import com.playone.mobile.ui.onboarding.OnBoardingActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(
        modules = [
            NavigatorModule::class,
            FragmentBindingModule::class])
    abstract fun bindBrowseActivity(): BrowseActivity

    @ContributesAndroidInjector(
        modules = [
            NavigatorModule::class,
            FragmentBindingModule::class,
            OnBoardingModule::class,
            PlayoneModule::class])
    abstract fun bindMainActivity(): OnBoardingActivity
}