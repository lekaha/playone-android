package com.playone.mobile.presentation.createPlayone

import com.playone.mobile.domain.interactor.playone.CreatePlayone
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.mapper.Mapper
import com.playone.mobile.presentation.model.PlayoneView
import io.reactivex.observers.DisposableSingleObserver

class CreatePlayonePresenter(
    private val createPlayone: CreatePlayone,
    val viewMapper: Mapper<PlayoneView, Playone>
) : CreatePlayoneContract.Presenter {

    var createPlayoneView: CreatePlayoneContract.View? = null

    override fun setView(view: CreatePlayoneContract.View) {
        createPlayoneView = view
    }

    override fun start() {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {

        createPlayone.dispose()
        createPlayoneView = null
    }

    override fun create(parameters: CreatePlayoneContract.CreatePlayoneParameters) {

        createPlayoneView?.onResponse(ViewResponse.loading())
        createPlayone.execute(
            CreateSubscriber(),
            Playone.CreateParameters(
                parameters.name,
                parameters.description,
                parameters.playoneDate,
                parameters.location.longitude,
                parameters.location.latitude,
                parameters.location.address,
                parameters.limitPeople,
                parameters.level
            )
        )
    }

    inner class CreateSubscriber : DisposableSingleObserver<Playone.Detail>() {
        override fun onSuccess(t: Playone.Detail) {
            createPlayoneView?.onResponse(ViewResponse.success(viewMapper.mapToView(t)))
        }

        override fun onError(e: Throwable) {

            createPlayoneView?.onResponse(ViewResponse.error(e))
        }
    }
}