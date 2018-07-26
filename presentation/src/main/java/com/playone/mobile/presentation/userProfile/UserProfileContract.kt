package com.playone.mobile.presentation.userProfile

import com.playone.mobile.presentation.BasePresenter
import com.playone.mobile.presentation.BaseView
import com.playone.mobile.presentation.model.UserView

/**
 * Defines a contract of operations between the UserProfile Presenter and UserProfile View
 */
interface UserProfileContract {

    interface View : BaseView<Presenter, UserView>

    interface Presenter : BasePresenter {
        fun setView(view: View)
        fun getUserById(userId: String)
        fun getCurrentUser()
    }

}