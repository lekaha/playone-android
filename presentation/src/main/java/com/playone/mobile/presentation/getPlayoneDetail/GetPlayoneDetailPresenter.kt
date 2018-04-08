package com.playone.mobile.presentation.getPlayoneDetail

import com.playone.mobile.domain.interactor.playone.GetPlayoneDetail
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.mapper.Mapper
import com.playone.mobile.presentation.model.PlayoneView
import io.reactivex.observers.DisposableSingleObserver

class GetPlayoneDetailPresenter(
    val getPlayoneDetail: GetPlayoneDetail,
    val viewMapper: Mapper<PlayoneView, Playone>
) : GetPlayoneDetailContract.Presenter {

    var getPlayoneDetailView: GetPlayoneDetailContract.View? = null

    override fun setView(view: GetPlayoneDetailContract.View) {

        getPlayoneDetailView = view
    }

    override fun start() {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {

        getPlayoneDetailView = null
    }

    override fun getPlayoneDetail(playoneId: String) {

        getPlayoneDetail.execute(object : DisposableSingleObserver<Playone>() {

            override fun onError(e: Throwable) {

                getPlayoneDetailView?.onResponse(ViewResponse.error(e))
            }

            override fun onSuccess(t: Playone) {

                TODO("Why need to call again `getPlayoneDetail.execute`")
                getPlayoneDetail.execute(GetDetailSubscriber(), playoneId)
            }
        }, playoneId)
    }

    inner class GetDetailSubscriber : DisposableSingleObserver<Playone>() {

        override fun onSuccess(t: Playone) {

            getPlayoneDetailView?.onResponse(ViewResponse.success(viewMapper.mapToView(t)))
        }

        override fun onError(e: Throwable) {

            getPlayoneDetailView?.onResponse(ViewResponse.error(e))
        }
    }
}