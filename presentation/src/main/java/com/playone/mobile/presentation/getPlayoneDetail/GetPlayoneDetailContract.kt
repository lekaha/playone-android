package com.playone.mobile.presentation.getPlayoneDetail

import com.playone.mobile.presentation.BasePresenter
import com.playone.mobile.presentation.BaseView
import com.playone.mobile.presentation.model.PlayoneView

interface GetPlayoneDetailContract {

    interface View : BaseView<GetPlayoneDetailContract.Presenter, PlayoneView>

    interface Presenter : BasePresenter {

        fun setView(view: GetPlayoneDetailContract.View)
        fun getPlayoneDetail(playoneId: Int)
    }
}