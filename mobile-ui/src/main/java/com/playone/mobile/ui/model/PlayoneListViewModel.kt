package com.playone.mobile.ui.model

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.getPlayoneList.GetPlayoneListContract
import com.playone.mobile.presentation.model.PlayoneView

class PlayoneListViewModel(private var getPlayoneListPresenter: GetPlayoneListContract.Presenter) :
    BaseViewModel(), GetPlayoneListContract.View {

    val playoneList: MutableLiveData<List<PlayoneView>> = MutableLiveData()
    val listFilterType: MutableLiveData<FilterType> = MutableLiveData()
    val listFilterString: MutableLiveData<String> = MutableLiveData()

    private val bgHandlerThread = HandlerThread("PlayoneAdapter")
    private val bgHandler by lazy {
        bgHandlerThread.start()
        Handler(bgHandlerThread.looper)
    }

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

    override fun onCleared() {
        super.onCleared()
        bgHandlerThread.looper?.quit()
    }

    fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
            isProgressing.observe(owner, observer)

    fun load(type: FilterType = FilterType.ALL) {
        playoneList.value
        isProgressing.value = true
        listFilterType.value = type
        when(type) {
            PlayoneListViewModel.FilterType.ALL -> getPlayoneListPresenter.getAllPlayoneList()
            PlayoneListViewModel.FilterType.FAVORITE -> getPlayoneListPresenter.getFavoritePlayoneList()
            PlayoneListViewModel.FilterType.JOIN -> getPlayoneListPresenter.getJoinedPlayoneList()
        }
    }

    fun fetchListData() = playoneList

    fun filterByName(query: String, post: (List<PlayoneView>) -> Unit) {
        bgHandler.post {
            playoneList.value?.filter {
                it.name.contains(query)
            }.also {
                it?.takeIf(List<PlayoneView>::isNotEmpty)?.let {
                    Handler(Looper.getMainLooper()).post {
                        post(it)
                    }
                }
            }

        }
    }

    class PlayoneListViewModelFactory(private var presenter: GetPlayoneListContract.Presenter) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(PlayoneListViewModel::class.java)) {
                return PlayoneListViewModel(presenter) as T
            }

            throw IllegalArgumentException("Illegal ViewModel class")
        }

    }

    enum class FilterType {
        ALL, FAVORITE, JOIN
    }
}