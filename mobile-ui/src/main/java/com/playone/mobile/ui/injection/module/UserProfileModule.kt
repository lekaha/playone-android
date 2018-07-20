package com.playone.mobile.ui.injection.module

import com.playone.mobile.domain.interactor.user.GetUser
import com.playone.mobile.domain.interactor.user.UpdateUserProfile
import com.playone.mobile.domain.model.User
import com.playone.mobile.presentation.mapper.Mapper
import com.playone.mobile.presentation.model.UserView
import com.playone.mobile.presentation.userProfile.UserProfileContract
import com.playone.mobile.presentation.userProfile.UserProfilePresenter
import com.playone.mobile.ui.model.UserProfileViewModel
import dagger.Module
import dagger.Provides

@Module
class UserProfileModule {

    @Provides
    @JvmSuppressWildcards
    internal fun provideUserProfilePresenter(
        getUser: GetUser,
        updateUserProfile: UpdateUserProfile,
        viewMapper: Mapper<UserView, User>
    ): UserProfileContract.Presenter =
        UserProfilePresenter(getUser, updateUserProfile, viewMapper)

    @Provides
    internal fun provideUserProfileViewModelFactory(presenter: UserProfileContract.Presenter) =
        UserProfileViewModel.UserProfileViewModelFactory(presenter)
}