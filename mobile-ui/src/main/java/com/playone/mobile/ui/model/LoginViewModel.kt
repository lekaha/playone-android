package com.playone.mobile.ui.model

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.os.Handler
import androidx.os.postDelayed
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.login.LoginPlayoneContract
import com.playone.mobile.presentation.model.UserView
import com.playone.mobile.ui.ext.ifTrue
import com.playone.mobile.ui.ext.otherwise

class LoginViewModel(private var presenter: LoginPlayoneContract.Presenter)
    : ViewModel(), LifecycleObserver, LoginPlayoneContract.View {

    val isProgressing: MutableLiveData<Boolean> = MutableLiveData()
    val isSignedIn: MutableLiveData<Boolean> = MutableLiveData()
    val occurredError: MutableLiveData<Throwable> = MutableLiveData()

    init {
        presenter.setView(this)
    }

    override fun setPresenter(presenter: LoginPlayoneContract.Presenter) {
        this.presenter = presenter
        this.presenter.setView(this)
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
                isSignedIn.value = true
            }
        }
    }

    fun signIn(email: String, password: String) {
        // TODO: Need Email format verification
        (email.isEmpty() or password.isEmpty()).ifTrue {
            occurredError.value = Exception("Email or Password cannot be empty")
        } otherwise  {
            presenter.signIn(email, password)
        }
    }

    fun signUp(email: String, password: String) {
        // TODO: Implementation
    }

    fun isSignedIn() {
        isProgressing.value = true

        Handler().postDelayed(500L) {
            isSignedIn.value = presenter.isSignedIn()
            isProgressing.value = false
        }

    }

    class LoginViewModelFactory(private val presenter: LoginPlayoneContract.Presenter) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(presenter) as T
            }

            throw IllegalArgumentException("Illegal ViewModel class")
        }

    }
}