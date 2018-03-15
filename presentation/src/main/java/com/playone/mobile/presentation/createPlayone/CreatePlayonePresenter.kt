package com.playone.mobile.presentation.createPlayone

import com.playone.mobile.domain.interactor.auth.SignUpAndSignIn
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.mapper.Mapper
import com.playone.mobile.presentation.model.PlayoneView
import io.reactivex.observers.DisposableSingleObserver

class CreatePlayonePresenter(
    val signUpAndSignIn: SignUpAndSignIn,
    val viewMapper: Mapper<PlayoneView, Playone>
) : CreatePlayoneContract.Presenter {

    var createPlayoneView: CreatePlayoneContract.View? = null

    override fun start() {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {

        createPlayoneView = null
    }

    override fun create(parameters: CreatePlayoneContract.CreatePlayoneParameters) {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        createPlayoneView?.onResponse(ViewResponse.loading())
    }

    inner class CreateSubscriber : DisposableSingleObserver<Playone>() {

        override fun onSuccess(t: Playone) {

            createPlayoneView?.onResponse(ViewResponse.success(viewMapper.mapToView(t)))
        }

        override fun onError(e: Throwable) {

            createPlayoneView?.onResponse(ViewResponse.error(e))
        }
    }
}