package com.playone.mobile.presentation.login

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
        fun signUp()
        fun signIn()
    }

}