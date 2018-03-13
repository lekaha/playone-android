package com.playone.mobile.presentation.createPlayone

import com.playone.mobile.presentation.BasePresenter
import com.playone.mobile.presentation.BaseView
import com.playone.mobile.presentation.model.PlayoneView
import java.util.Date

interface CreatePlayoneContract {

    interface View : BaseView<CreatePlayoneContract.Presenter, PlayoneView>

    interface Presenter : BasePresenter {

        fun create(parameters: CreatePlayoneParameters)
    }

    data class PlayonePlace(val longitude: Long, var latitude: Long, var address: String)

    data class CreatePlayoneParameters(
        var name: String,
        var description: String,
        var playoneDate: Date,
        var location: PlayonePlace,
        var limitPeople: Int,
        var level: Int
    )
}