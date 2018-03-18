package com.playone.mobile.presentation.getPlayoneList

import com.playone.mobile.domain.interactor.playone.GetCurrentUser
import com.playone.mobile.domain.interactor.playone.GetPlayoneList
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.model.User
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.mapper.Mapper
import com.playone.mobile.presentation.model.PlayoneView
import io.reactivex.observers.DisposableSingleObserver

class GetPlayoneListPresenter(
    val getCurrentUser: GetCurrentUser,
    val getPlayoneList: GetPlayoneList,
    val viewMapper: Mapper<PlayoneView, Playone>
) : GetPlayoneListContract.Presenter {

    var getPlayoneListView: GetPlayoneListContract.View? = null

    override fun setView(view: GetPlayoneListContract.View) {

        getPlayoneListView = view
    }

    override fun start() {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {

        getPlayoneListView = null
    }

    override fun getAllPlayoneList() {

        getCurrentUser.execute(object : DisposableSingleObserver<User>() {

            override fun onError(e: Throwable) {

                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSuccess(t: User) {

                getPlayoneList.execute(GetListSubscriber(), if (t.isVerified) t.id else null)
            }
        })
    }

    inner class GetListSubscriber : DisposableSingleObserver<List<Playone>>() {

        override fun onSuccess(t: List<Playone>) {

            getPlayoneListView?.onResponse(ViewResponse.success(t.map { viewMapper.mapToView(it) }))
        }

        override fun onError(e: Throwable) {

            getPlayoneListView?.onResponse(ViewResponse.error(e))
        }
    }
}