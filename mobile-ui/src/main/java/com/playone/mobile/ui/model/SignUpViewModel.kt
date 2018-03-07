package com.playone.mobile.ui.model

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.model.UserView
import com.playone.mobile.presentation.onBoarding.LoginPlayoneContract

class SignUpViewModel(presenter: LoginPlayoneContract.Presenter)
    : LoginViewModel(presenter) {

    fun signUp(email: String, password: String) {

        (email.isEmpty() or password.isEmpty()).ifTrue {
            occurredError.value = Exception("Email or Password cannot be empty")
        } otherwise {
            loginPresenter.signUp(email, password)
        }
    }

    override fun onResponse(response: ViewResponse<UserView>) {

        when(response.status) {
            ViewResponse.Status.LOADING -> { isProgressing.value = true }
            ViewResponse.Status.ERROR -> {
                isProgressing.value = false
                occurredError.value = response.error
            }
            ViewResponse.Status.SUCCESS -> {
                isProgressing.value = false
                isSignedIn.value = loginPresenter.isSignedIn()
                isVerifiedEmail.value = loginPresenter.isVerifiedEmail()
            }
        }
    }

    class SignUpViewModelFactory(private val presenter: LoginPlayoneContract.Presenter) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
                return SignUpViewModel(presenter) as T
            }

            throw IllegalArgumentException("Illegal ViewModel class")
        }

    }
}