package com.playone.mobile.presentation.onBoarding

import com.playone.mobile.presentation.BasePresenter
import com.playone.mobile.presentation.BaseView
import com.playone.mobile.presentation.model.UserView

/**
 * Defines a contract of operations between the OnBoarding Presenter and OnBoarding View
 */
interface OnBoardingPlayoneContract {

    interface View : BaseView<Presenter, UserView>

    interface Presenter : BasePresenter {
        fun setView(view: View)
        fun isSignedIn(): Boolean
    }

}