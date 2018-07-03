package com.playone.mobile.ui.model

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.getPlayoneList.GetPlayoneListContract
import com.playone.mobile.presentation.model.PlayoneView

class PlayoneListViewModel(private var getPlayoneListPresenter: GetPlayoneListContract.Presenter) :
    ViewModel(), LifecycleObserver, GetPlayoneListContract.View {

    private val isProgressing: MutableLiveData<Boolean> = MutableLiveData()
    private val occurredError: MutableLiveData<Throwable> = MutableLiveData()
    private val playoneList: MutableLiveData<List<PlayoneView>> = MutableLiveData()

    init {
        getPlayoneListPresenter.setView(this)
    }

    override fun setPresenter(presenter: GetPlayoneListContract.Presenter) {

        getPlayoneListPresenter = presenter
        getPlayoneListPresenter.setView(this)
    }

    override fun onResponse(response: ViewResponse<List<PlayoneView>>) {

        when(response.status) {
            ViewResponse.Status.LOADING -> { isProgressing.value = true }
            ViewResponse.Status.ERROR -> {
                isProgressing.value = false
                occurredError.value = response.error
            }
            ViewResponse.Status.SUCCESS -> {
                isProgressing.value = false
                playoneList.value = response.data
            }
        }
    }

    fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
            isProgressing.observe(owner, observer)

    fun load() {

        isProgressing.value = true
        getPlayoneListPresenter.getAllPlayoneList()
    }

    fun fetchListData() = playoneList

    class PlayoneListViewModelFactory(private var presenter: GetPlayoneListContract.Presenter) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(PlayoneListViewModel::class.java)) {
                return PlayoneListViewModel(presenter) as T
            }

            throw IllegalArgumentException("Illegal ViewModel class")
        }

    }
}