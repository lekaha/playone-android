package com.playone.mobile.ui.injection.module

import com.playone.mobile.domain.interactor.auth.SignUpAndSignIn
import com.playone.mobile.presentation.login.LoginPlayoneContract
import com.playone.mobile.presentation.login.LoginPlayonePresenter
import com.playone.mobile.presentation.mapper.UserMapper
import com.playone.mobile.ui.model.LoginViewModel
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    @Provides
    internal fun provideLoginPresenter(signUpAndSignIn: SignUpAndSignIn, mapper: UserMapper)
        : LoginPlayoneContract.Presenter = LoginPlayonePresenter(signUpAndSignIn, mapper)

    @Provides
    internal fun provideLoginViewModelFactory(presenter: LoginPlayoneContract.Presenter) =
        LoginViewModel.LoginViewModelFactory(presenter)
}