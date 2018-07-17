package com.playone.mobile.ui.model

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import com.playone.mobile.ext.ifFalse
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.getPlayoneDetail.GetPlayoneDetailContract
import com.playone.mobile.presentation.model.PlayoneView
import com.playone.mobile.ui.view.ToggleDebouncer
import java.util.concurrent.TimeUnit

class PlayoneDetailViewModel(
    private var getPlayoneListPresenter: GetPlayoneDetailContract.Presenter
) : BaseViewModel(), GetPlayoneDetailContract.View {

    private val playoneDetail: MutableLiveData<PlayoneView> = MutableLiveData()
    private val isFavorited: MutableLiveData<Boolean> = MutableLiveData()

    private val toggleObject = ToggleDebouncer.ToggleObject()
    private val toggleDebouncer =
        ToggleDebouncer(toggleObject, 1, TimeUnit.SECONDS) {
            postFavorite()
        }

    init {

        getPlayoneListPresenter.setView(this)
    }

    override fun setPresenter(presenter: GetPlayoneDetailContract.Presenter) {

        getPlayoneListPresenter = presenter
        getPlayoneListPresenter.setView(this)
    }

    override fun onResponse(response: ViewResponse<PlayoneView>) {

        when (response.status) {
            ViewResponse.Status.LOADING -> {
                isProgressing.value = true
            }
            ViewResponse.Status.ERROR -> {
                isProgressing.value = false
                occurredError.value = response.error
            }
            ViewResponse.Status.SUCCESS -> {
                isProgressing.value = false
                playoneDetail.value = response.data
            }
        }
    }

    fun load(playoneId: String) {

        getPlayoneListPresenter.getPlayoneDetail(playoneId)
    }

    fun load(playone: PlayoneView) {

        getPlayoneListPresenter.getPlayoneDetail(playone.id)
    }

    fun fetchDetailData() = playoneDetail

    fun observePlayoneDetail(owner: LifecycleOwner, observer: Observer<PlayoneView>) {

        (playoneDetail.hasObservers()).ifFalse {
            playoneDetail.observe(owner, observer)
        }
    }

    fun postFavorite() {

        playoneDetail.value?.apply {
            val favorited = this@PlayoneDetailViewModel.isFavorited.value
            getPlayoneListPresenter.setFavorite(id, favorited ?: false)
        }
    }

    fun setFavorite(isFavorite: Boolean) {

        toggleObject.toggle()
        isFavorited.value = isFavorite
    }

    override fun onCleared() {

        super.onCleared()
        toggleDebouncer.stop()
    }

    class PlayoneDetailViewModelFactory(
        private var presenter: GetPlayoneDetailContract.Presenter
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(PlayoneDetailViewModel::class.java)) {
                return PlayoneDetailViewModel(presenter) as T
            }

            throw IllegalArgumentException("Illegal ViewModel class")
        }

    }
}
