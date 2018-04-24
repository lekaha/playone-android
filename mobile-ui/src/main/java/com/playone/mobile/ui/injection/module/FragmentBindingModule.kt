package com.playone.mobile.ui.injection.module

import com.playone.mobile.ui.browse.BrowseFragment
import com.playone.mobile.ui.create.CreatePlayoneFragment
import com.playone.mobile.ui.injection.module.mapper.ViewModule
import com.playone.mobile.ui.create.SelectLocationFragment
import com.playone.mobile.ui.onboarding.SignInFragment
import com.playone.mobile.ui.onboarding.SignUpFragment
import com.playone.mobile.ui.playone.PlayoneListFragment
import com.playone.mobile.ui.view.DatePickerDialogFragment
import com.playone.mobile.ui.view.TimePickerDialogFragment
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
            ViewModule::class,
            PlayoneListModule::class,
            PlayoneListBindModule::class
        ])
    abstract fun providePlayoneListFragment(): PlayoneListFragment

    @ContributesAndroidInjector(
        modules = [
            CreatePlayoneModule::class])
    abstract fun provideSelectLocationFragment(): SelectLocationFragment

    @ContributesAndroidInjector(
        modules = [
            CreatePlayoneModule::class])
    abstract fun provideCreatePlayoneFragment(): CreatePlayoneFragment

    @ContributesAndroidInjector(
        modules = []
    )
    abstract fun provideDatePickerDialogFragment(): DatePickerDialogFragment

    @ContributesAndroidInjector(
        modules = []
    )
    abstract fun provideTimePickerDialogFragment(): TimePickerDialogFragment
}