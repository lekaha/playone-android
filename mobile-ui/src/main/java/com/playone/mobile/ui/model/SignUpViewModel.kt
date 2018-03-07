package com.playone.mobile.ui.model

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise
import com.playone.mobile.presentation.onBoarding.LoginPlayoneContract

class SignUpViewModel(presenter: LoginPlayoneContract.Presenter)
    : LoginViewModel(presenter) {

    override fun signUp(email: String, password: String) {

        (email.isEmpty() or password.isEmpty()).ifTrue {
            occurredError.value = Exception("Email or Password cannot be empty")
        } otherwise {
            loginPresenter.signUp(email, password)
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