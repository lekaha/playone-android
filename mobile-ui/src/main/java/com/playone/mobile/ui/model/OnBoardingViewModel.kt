package com.playone.mobile.ui.model

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.os.Handler
import androidx.os.postDelayed
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.model.UserView
import com.playone.mobile.presentation.onBoarding.OnBoardingPlayoneContract

class OnBoardingViewModel(private var presenter: OnBoardingPlayoneContract.Presenter)
    : ViewModel(), LifecycleObserver, OnBoardingPlayoneContract.View {

    val isProgressing: MutableLiveData<Boolean> = MutableLiveData()
    val isSignedIn: MutableLiveData<Boolean> = MutableLiveData()
    val occurredError: MutableLiveData<Throwable> = MutableLiveData()

    init {
        presenter.setView(this)
    }

    override fun setPresenter(presenter: OnBoardingPlayoneContract.Presenter) {

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

    fun isSignedIn() {

        isProgressing.value = true

        Handler().postDelayed(500L) {
            isSignedIn.value = presenter.isSignedIn()
            isProgressing.value = false
        }

    }

    class OnBoardingViewModelFactory(private val presenter: OnBoardingPlayoneContract.Presenter)
        : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(OnBoardingViewModel::class.java)) {
                return OnBoardingViewModel(presenter) as T
            }

            throw IllegalArgumentException("Illegal ViewModel class")
        }
    }
}