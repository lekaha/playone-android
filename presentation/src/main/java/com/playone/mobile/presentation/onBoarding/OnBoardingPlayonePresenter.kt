package com.playone.mobile.presentation.onBoarding

import com.playone.mobile.domain.interactor.auth.SignUpAndSignIn

class OnBoardingPlayonePresenter(
    val signUpAndSignIn: SignUpAndSignIn
) : OnBoardingPlayoneContract.Presenter {

    var onBoardingView: OnBoardingPlayoneContract.View? = null

    override fun start() {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {

        onBoardingView = null
    }

    override fun setView(
        view: OnBoardingPlayoneContract.View
    ) {
        onBoardingView = view
    }

    override fun isSignedIn() = signUpAndSignIn.isSignedIn()
}