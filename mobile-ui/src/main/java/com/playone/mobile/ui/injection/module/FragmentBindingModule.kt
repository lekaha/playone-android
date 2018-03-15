package com.playone.mobile.ui.injection.module

import com.playone.mobile.ui.browse.BrowseFragment
import com.playone.mobile.ui.onboarding.SignInFragment
import com.playone.mobile.ui.onboarding.SignUpFragment
import com.playone.mobile.ui.playone.PlayoneListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector(
        modules = [
            BrowseActivityModule::class,
            BrowseModule::class])
    abstract fun provideBrowseFragment(): BrowseFragment

    @ContributesAndroidInjector(
        modules = [
            LoginModule::class])
    abstract fun provideSignInFragment(): SignInFragment

    @ContributesAndroidInjector(
        modules = [])
    abstract fun provideSignUpFragment(): SignUpFragment

    @ContributesAndroidInjector(
        modules = [
            PlayoneListModule::class,
            PlayoneListBindModule::class])
    abstract fun providePlayoneListFragment(): PlayoneListFragment
}