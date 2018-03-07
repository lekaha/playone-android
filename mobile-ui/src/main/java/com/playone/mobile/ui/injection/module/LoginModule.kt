package com.playone.mobile.ui.injection.module

import com.playone.mobile.domain.interactor.auth.SignUpAndSignIn
import com.playone.mobile.presentation.login.LoginPlayoneContract
import com.playone.mobile.presentation.login.LoginPlayonePresenter
import com.playone.mobile.presentation.mapper.UserMapper
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.model.LoginViewModel
import com.playone.mobile.ui.playone.PlayoneActivity
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    companion object {
        const val READ_PERMISSIONS = "READ_PERMISSIONS"
    }

    @Provides
    internal fun provideLoginPresenter(signUpAndSignIn: SignUpAndSignIn, mapper: UserMapper)
        : LoginPlayoneContract.Presenter = LoginPlayonePresenter(signUpAndSignIn, mapper)

    @Provides
    internal fun provideLoginViewModelFactory(presenter: LoginPlayoneContract.Presenter) =
        LoginViewModel.LoginViewModelFactory(presenter)

    @Provides
    internal fun provideLoginReadPermission(): ArrayList<String> =
        arrayListOf("email", "public_profile")
}