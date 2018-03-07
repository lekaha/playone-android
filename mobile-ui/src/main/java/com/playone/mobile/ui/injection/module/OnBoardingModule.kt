package com.playone.mobile.ui.injection.module

import com.playone.mobile.domain.interactor.auth.SignUpAndSignIn
import com.playone.mobile.presentation.onBoarding.OnBoardingPlayoneContract
import com.playone.mobile.presentation.onBoarding.OnBoardingPlayonePresenter
import com.playone.mobile.ui.model.OnBoardingViewModel
import dagger.Module
import dagger.Provides

@Module
class OnBoardingModule {

    @Provides
    internal fun provideOnBoardingPresenter(signUpAndSignIn: SignUpAndSignIn)
        : OnBoardingPlayoneContract.Presenter = OnBoardingPlayonePresenter(signUpAndSignIn)

    @Provides
    internal fun provideOnBoardingViewModelFactory(presenter: OnBoardingPlayoneContract.Presenter) =
        OnBoardingViewModel.OnBoardingViewModelFactory(presenter)
}