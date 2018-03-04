package com.playone.mobile.ui.injection.module

import com.playone.mobile.domain.interactor.auth.SignUpAndSignIn
import com.playone.mobile.presentation.login.LoginPlayonePresenter
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    @Provides
    internal fun provideLoginPresenter(signUpAndSignIn: SignUpAndSignIn) =
        LoginPlayonePresenter(signUpAndSignIn)
}