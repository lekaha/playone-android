package com.playone.mobile.presentation.onBoarding

import com.playone.mobile.presentation.BasePresenter
import com.playone.mobile.presentation.BaseView
import com.playone.mobile.presentation.model.UserView

/**
 * Defines a contract of operations between the Browse Presenter and Browse View
 */
interface LoginPlayoneContract {

    interface View : BaseView<Presenter, UserView>

    interface Presenter : BasePresenter {
        fun setView(view: View)
        fun signUp(email: String, password: String)
        fun signInAnonymously()
        fun signIn(email: String, password: String)
        fun signIn(secretContent: Any)
        fun isSignedIn(): Boolean
        fun isVerifiedEmail(): Boolean
        fun sendEmailVerification()
    }

}