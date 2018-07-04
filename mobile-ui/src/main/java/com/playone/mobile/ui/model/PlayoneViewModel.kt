package com.playone.mobile.ui.model

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.annotation.IntDef
import com.playone.mobile.ext.ifFalse

class PlayoneViewModel: BaseViewModel() {

    companion object {
        const val CONTENT_MODE_LIST = 0x301
        const val CONTENT_MODE_DETAIL = 0x302
    }

    private val contentMode: MutableLiveData<Int> = MutableLiveData()

    @IntDef(CONTENT_MODE_LIST, CONTENT_MODE_DETAIL)
    @Retention(AnnotationRetention.SOURCE)
    annotation class ContentMode

    fun observeContentMode(owner: LifecycleOwner, observer: Observer<Int>) {
        (contentMode.hasObservers()).ifFalse {
            contentMode.observe(owner, observer)
        }
    }

    fun changeMode(@ContentMode mode: Int) {
        contentMode.postValue(mode)
    }


    class PlayoneViewModelFactory :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(PlayoneViewModel::class.java)) {
                return PlayoneViewModel() as T
            }

            throw IllegalArgumentException("Illegal ViewModel class")
        }

    }
}