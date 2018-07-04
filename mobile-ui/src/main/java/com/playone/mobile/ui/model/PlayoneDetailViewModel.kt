package com.playone.mobile.ui.model

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.getPlayoneDetail.GetPlayoneDetailContract
import com.playone.mobile.presentation.model.PlayoneView

class PlayoneDetailViewModel(
    private var getPlayoneListPresenter: GetPlayoneDetailContract.Presenter
) : ViewModel(), LifecycleObserver, GetPlayoneDetailContract.View {

    private val isProgressing: MutableLiveData<Boolean> = MutableLiveData()
    private val occurredError: MutableLiveData<Throwable> = MutableLiveData()
    private val playoneDetail: MutableLiveData<PlayoneView> = MutableLiveData()

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

        isProgressing.value = true
        getPlayoneListPresenter.getPlayoneDetail(playoneId)
    }

    fun fetchDetailData() = playoneDetail

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