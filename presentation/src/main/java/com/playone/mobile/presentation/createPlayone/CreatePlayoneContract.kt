package com.playone.mobile.presentation.createPlayone

import com.playone.mobile.presentation.BasePresenter
import com.playone.mobile.presentation.BaseView
import com.playone.mobile.presentation.model.PlayoneView
import com.playone.mobile.presentation.onBoarding.LoginPlayoneContract
import java.util.Date

interface CreatePlayoneContract {

    interface View : BaseView<LoginPlayoneContract.Presenter, PlayoneView>

    interface Presenter : BasePresenter {

        fun create(name: String,
                   description: String,
                   playoneDate: Date,
                   location: PlayonePlace,
                   limitPeople: Int,
                   level: Int
        )
    }

    data class PlayonePlace(val longitude: Long, var latitude: Long, var address: String)
}