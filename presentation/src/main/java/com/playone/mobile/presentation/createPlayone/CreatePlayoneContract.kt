package com.playone.mobile.presentation.createPlayone

import com.playone.mobile.presentation.BasePresenter
import com.playone.mobile.presentation.BaseView
import com.playone.mobile.presentation.model.PlayoneView
import java.util.Date

interface CreatePlayoneContract {

    interface View : BaseView<CreatePlayoneContract.Presenter, PlayoneView>

    interface Presenter : BasePresenter {
        fun setView(view: CreatePlayoneContract.View)
        fun create(parameters: CreatePlayoneParameters)
    }

    data class PlayonePlace(val longitude: Double, var latitude: Double, var address: String)

    data class CreatePlayoneParameters(
        var name: String,
        var description: String,
        var playoneDate: Date,
        var location: PlayonePlace,
        var limitPeople: Int,
        var level: Int
    ) {
        override fun toString() =
            "Name: $name, " +
            "Date: $playoneDate, " +
            "Place: ${location.address}, " +
            "People: $limitPeople, " +
            "Level: $level"
    }
}