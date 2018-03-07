package com.playone.mobile.ui.injection.module

import com.playone.mobile.ui.browse.BrowseFragment
import com.playone.mobile.ui.onboarding.SignInFragment
import com.playone.mobile.ui.onboarding.SignUpFragment
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
            PlayoneModule::class,
            LoginModule::class])
    abstract fun provideSignInFragment(): SignInFragment

    @ContributesAndroidInjector(
        modules = [
            PlayoneModule::class,
            LoginModule::class])
    abstract fun provideSignUpFragment(): SignUpFragment
}