package com.playone.mobile.ui.injection.module

import com.playone.mobile.domain.interactor.auth.SignUpAndSignIn
import com.playone.mobile.domain.interactor.playone.GetCurrentUser
import com.playone.mobile.domain.model.User
import com.playone.mobile.presentation.mapper.Mapper
import com.playone.mobile.presentation.model.UserView
import com.playone.mobile.presentation.onBoarding.LoginPlayoneContract
import com.playone.mobile.presentation.onBoarding.LoginPlayonePresenter
import com.playone.mobile.ui.model.LoginViewModel
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    companion object {
        const val READ_PERMISSIONS = "READ_PERMISSIONS"
    }

    @Provides
    @JvmSuppressWildcards
    internal fun provideLoginPresenter(
        signUpAndSignIn: SignUpAndSignIn,
        getCurrentUser: GetCurrentUser,
        viewMapper: Mapper<UserView, User>
    ): LoginPlayoneContract.Presenter =
        LoginPlayonePresenter(signUpAndSignIn, getCurrentUser, viewMapper)

    @Provides
    internal fun provideLoginViewModelFactory(presenter: LoginPlayoneContract.Presenter) =
        LoginViewModel.LoginViewModelFactory(presenter)

    @Provides
    internal fun provideLoginReadPermission(): ArrayList<String> =
        arrayListOf("email", "public_profile")
}