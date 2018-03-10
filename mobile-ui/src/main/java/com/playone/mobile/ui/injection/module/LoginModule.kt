package com.playone.mobile.ui.injection.module

import com.playone.mobile.domain.interactor.auth.SignUpAndSignIn
import com.playone.mobile.presentation.mapper.UserViewMapper
import com.playone.mobile.presentation.onBoarding.LoginPlayoneContract
import com.playone.mobile.presentation.onBoarding.LoginPlayonePresenter
import com.playone.mobile.ui.model.LoginViewModel
import com.playone.mobile.ui.model.SignUpViewModel
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    companion object {
        const val READ_PERMISSIONS = "READ_PERMISSIONS"
    }

    @Provides
    internal fun provideLoginPresenter(signUpAndSignIn: SignUpAndSignIn, viewMapper: UserViewMapper)
        : LoginPlayoneContract.Presenter = LoginPlayonePresenter(signUpAndSignIn, viewMapper)

    @Provides
    internal fun provideLoginViewModelFactory(presenter: LoginPlayoneContract.Presenter) =
        LoginViewModel.LoginViewModelFactory(presenter)

    @Provides
    internal fun provideLoginReadPermission(): ArrayList<String> =
        arrayListOf("email", "public_profile")

    @Provides
    internal fun provideSignUpViewModelFactory(presenter: LoginPlayoneContract.Presenter) =
        SignUpViewModel.SignUpViewModelFactory(presenter)
}