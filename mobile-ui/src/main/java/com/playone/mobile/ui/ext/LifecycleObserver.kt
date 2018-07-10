package com.playone.mobile.ui.ext

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer

fun <T> observer(body:(v: T?) -> Unit) = Observer<T> {
    body(it)
}

fun <T> MutableLiveData<T>.observe(owner: LifecycleOwner, body:(v: T?) -> Unit) {
    observe(owner, observer(body))
}