package com.playone.mobile.presentation.browse

import com.playone.mobile.presentation.BasePresenter
import com.playone.mobile.presentation.BaseView
import com.playone.mobile.presentation.model.BufferooView

/**
 * Defines a contract of operations between the Browse Presenter and Browse View
 */
interface BrowseBufferoosContract {

    interface View : BaseView<Presenter, List<BufferooView>>

    interface Presenter : BasePresenter {
        fun setView(view: View)
        fun retrieveBufferoos()
    }

}