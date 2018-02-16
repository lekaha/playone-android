package com.playone.mobile.ui.model

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.browse.BrowseBufferoosContract
import com.playone.mobile.presentation.model.BufferooView

class BrowseViewModel(var onboardingPresenter: BrowseBufferoosContract.Presenter)
    : ViewModel(), LifecycleObserver, BrowseBufferoosContract.View {

    private var isProgressing: MutableLiveData<Boolean> = MutableLiveData()
    private var occurredError: MutableLiveData<Throwable> = MutableLiveData()
    private var bufferoos: MutableLiveData<List<BufferooView>> = MutableLiveData()

    init {
        onboardingPresenter.setView(this)
    }

    override fun setPresenter(presenter: BrowseBufferoosContract.Presenter) {
        onboardingPresenter = presenter
        onboardingPresenter.setView(this)
    }

    override fun onResponse(response: ViewResponse<List<BufferooView>>) {
        when(response.status) {
            ViewResponse.Status.LOADING -> { isProgressing.value = true }
            ViewResponse.Status.ERROR -> {
                isProgressing.value = false
                occurredError.value = response.error
            }
            ViewResponse.Status.SUCCESS -> {
                isProgressing.value = false
                bufferoos.value = response.data
            }
        }
    }

    fun isProgressing() = isProgressing

    fun occurredError() = occurredError

    fun fetchedData() = bufferoos

    fun load() = onboardingPresenter.start()

    override fun onCleared() = onboardingPresenter.stop()


}