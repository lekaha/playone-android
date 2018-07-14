package com.playone.mobile.ui.model

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel(), LifecycleObserver {

    val isProgressing: MutableLiveData<Boolean> = MutableLiveData()
    val occurredError: MutableLiveData<Throwable> = MutableLiveData()

    fun observeIsProgressing(owner: LifecycleOwner, observer: Observer<Boolean>) =
        isProgressing.observe(owner, observer)

    fun observeIsProgressing(owner: LifecycleOwner, block: (Boolean?) -> Unit) =
        isProgressing.observe(owner, Observer { block(it) })
}