package com.playone.mobile.ui.model

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.os.Handler
import androidx.os.postDelayed
import com.playone.mobile.ext.ifFalse
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.onBoarding.LoginPlayoneContract
import com.playone.mobile.presentation.model.UserView
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise

class LoginViewModel(private var loginPresenter: LoginPlayoneContract.Presenter)
    : ViewModel(), LifecycleObserver, LoginPlayoneContract.View {

    val isProgressing: MutableLiveData<Boolean> = MutableLiveData()
    val isSignedIn: MutableLiveData<Boolean> = MutableLiveData()
    val isVerifiedEmail: MutableLiveData<Boolean> = MutableLiveData()
    val occurredError: MutableLiveData<Throwable> = MutableLiveData()

    private var isSentEmailVerification = true

    init {
        loginPresenter.setView(this)
    }

    override fun setPresenter(presenter: LoginPlayoneContract.Presenter) {

        this.loginPresenter = presenter
        this.loginPresenter.setView(this)
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
                isVerifiedEmail.value = response.data?.isVerified

                isSentEmailVerification.ifFalse {
                    sendEmailVerification()
                    isSentEmailVerification = true
                }
            }
        }
    }

    fun signInAnonymously() {

        loginPresenter.signInAnonymously()
    }

    fun signIn(email: String, password: String) {

        // TODO: Need Email format verification
        (email.isEmpty() or password.isEmpty()).ifTrue {
            occurredError.value = Exception("Email or Password cannot be empty")
        } otherwise  {
            loginPresenter.signIn(email, password)
        }
    }

    fun signIn(socialAccount: Any) {

        loginPresenter.signIn(socialAccount)
    }

    fun isSignedIn() {

        isProgressing.value = true

        Handler().postDelayed(500L) {
            isSignedIn.value = loginPresenter.isSignedIn()
            isProgressing.value = false
        }
    }

    fun sendEmailVerification() {

        isSentEmailVerification = false
        isProgressing.value = true

        Handler().post {
            loginPresenter.sendEmailVerificationToCurrentUser()
        }
    }

    fun signUp(email: String, password: String) {

        isSentEmailVerification = false

        (email.isEmpty() or password.isEmpty()).ifTrue {
            occurredError.value = Exception("Email or Password cannot be empty")
        } otherwise {
            loginPresenter.signUp(email, password)
        }
    }

    override fun onCleared() {

        super.onCleared()
        loginPresenter.stop()
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