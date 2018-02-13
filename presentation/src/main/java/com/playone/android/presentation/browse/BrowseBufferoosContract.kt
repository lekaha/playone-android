package com.playone.android.presentation.browse

import com.playone.android.presentation.BasePresenter
import com.playone.android.presentation.BaseView
import com.playone.android.presentation.model.BufferooView

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