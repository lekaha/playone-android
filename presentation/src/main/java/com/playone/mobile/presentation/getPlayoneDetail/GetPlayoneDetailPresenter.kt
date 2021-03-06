package com.playone.mobile.presentation.getPlayoneDetail

import com.playone.mobile.domain.interactor.favorite.FavoritePlayone
import com.playone.mobile.domain.interactor.favorite.createParameter
import com.playone.mobile.domain.interactor.playone.GetCurrentUser
import com.playone.mobile.domain.interactor.playone.GetPlayoneDetail
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.model.User
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.mapper.Mapper
import com.playone.mobile.presentation.model.PlayoneView
import io.reactivex.observers.DisposableSingleObserver

class GetPlayoneDetailPresenter(
    val getCurrentUser: GetCurrentUser,
    val getPlayoneDetail: GetPlayoneDetail,
    val favoritePlayone: FavoritePlayone,
    val viewMapper: Mapper<PlayoneView, Playone>
) : GetPlayoneDetailContract.Presenter {

    var getPlayoneDetailView: GetPlayoneDetailContract.View? = null

    var currentUser: User? = null

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

        getPlayoneDetailView?.onResponse(ViewResponse.loading())
        getCurrentUser.execute(object : DisposableSingleObserver<User>() {
            override fun onSuccess(t: User) {

                currentUser = t
                getPlayoneDetail.execute(GetDetailSubscriber(), Pair(t.id, playoneId))
            }

            override fun onError(e: Throwable) {

                getPlayoneDetailView?.onResponse(ViewResponse.error(e))
            }

        })
    }

    override fun setFavorite(playoneId: String, isFavorite: Boolean) {

        currentUser?.apply {
            favoritePlayone.execute(
                FavoritePlayoneSubscriber(),
                favoritePlayone.createParameter(playoneId, id, isFavorite)
            )
        } ?: getCurrentUser.execute(object : DisposableSingleObserver<User>() {
                override fun onSuccess(t: User) {

                    favoritePlayone.execute(
                        FavoritePlayoneSubscriber(),
                        favoritePlayone.createParameter(playoneId, t.id, isFavorite)
                    )
                }

                override fun onError(e: Throwable) {

                    getPlayoneDetailView?.onResponse(ViewResponse.error(e))
                }

            })
    }

    inner class GetDetailSubscriber : DisposableSingleObserver<Playone>() {

        override fun onSuccess(t: Playone) {

            getPlayoneDetailView?.onResponse(ViewResponse.success(viewMapper.mapToView(t)))
        }

        override fun onError(e: Throwable) {

            getPlayoneDetailView?.onResponse(ViewResponse.error(e))
        }
    }

    inner class FavoritePlayoneSubscriber : DisposableSingleObserver<Boolean>() {

        override fun onSuccess(t: Boolean) {


        }

        override fun onError(e: Throwable) {

            getPlayoneDetailView?.onResponse(ViewResponse.error(e))
        }

    }
}