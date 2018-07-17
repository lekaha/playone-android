package com.playone.mobile.presentation.getPlayoneList

import com.playone.mobile.presentation.BasePresenter
import com.playone.mobile.presentation.BaseView
import com.playone.mobile.presentation.model.PlayoneView

interface GetPlayoneListContract {

    interface View : BaseView<GetPlayoneListContract.Presenter, List<PlayoneView>>

    interface Presenter : BasePresenter {

        fun setView(view: GetPlayoneListContract.View)
        fun getAllPlayoneList()
        fun getFavoritePlayoneList()
        fun getJoinedPlayoneList()
    }
}