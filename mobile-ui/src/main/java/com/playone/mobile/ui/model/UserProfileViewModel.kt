package com.playone.mobile.ui.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.model.UserView
import com.playone.mobile.presentation.userProfile.UserProfileContract

class UserProfileViewModel(
    private var userProfilePresenter: UserProfileContract.Presenter
) : BaseViewModel(), UserProfileContract.View {

    val userProfile: MutableLiveData<UserView> = MutableLiveData()

    init {
        userProfilePresenter.setView(this)
    }

    override fun setPresenter(presenter: UserProfileContract.Presenter) {

        userProfilePresenter = presenter
        userProfilePresenter.setView(this)
    }

    override fun onResponse(response: ViewResponse<UserView>) {

        when(response.status) {
            ViewResponse.Status.LOADING -> {
                isProgressing.value = true
            }
            ViewResponse.Status.ERROR -> {
                isProgressing.value = false
                occurredError.value = response.error
            }
            ViewResponse.Status.SUCCESS -> {
                isProgressing.value = false
                userProfile.value = response.data
            }
        }
    }

    fun getCurrentUser() = userProfilePresenter.getCurrentUser()

    fun getUserById(userId: String) = userProfilePresenter.getUserById(userId)

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