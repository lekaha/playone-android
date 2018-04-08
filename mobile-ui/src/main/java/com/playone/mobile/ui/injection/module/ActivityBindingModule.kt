package com.playone.mobile.ui.injection.module

import com.playone.mobile.ui.browse.BrowseActivity
import com.playone.mobile.ui.create.CreatePlayoneActivity
import com.playone.mobile.ui.onboarding.OnBoardingActivity
import com.playone.mobile.ui.playone.PlayoneActivity
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
            LoginModule::class,
            PlayoneModule::class])
    abstract fun bindMainActivity(): OnBoardingActivity

    @ContributesAndroidInjector(
        modules = [
            NavigatorModule::class,
            FragmentBindingModule::class,
            PlayoneModule::class,
            PlayoneListModule::class,
            PlayoneDetailModule::class])
    abstract fun bindPlayoneActivity(): PlayoneActivity

    @ContributesAndroidInjector(
        modules = [
            NavigatorModule::class,
            FragmentBindingModule::class,
            PlayoneModule::class])
    abstract fun bindCreatePlayoneActivity(): CreatePlayoneActivity
}