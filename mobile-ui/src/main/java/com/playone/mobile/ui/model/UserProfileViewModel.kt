package com.playone.mobile.ui.model

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.playone.mobile.presentation.userProfile.UserProfileContract

class UserProfileViewModel(
    private var userProfilePresenter: UserProfileContract.Presenter
) : BaseViewModel() {

    fun getCurrentUser() {}

    fun getUserById(userId: String) {}

    class UserProfileViewModelFactory(
        private var userProfilePresenter: UserProfileContract.Presenter
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
                return UserProfileViewModel(userProfilePresenter) as T
            }

            throw IllegalArgumentException("Illegal ViewModel class")
        }

    }
}