package com.playone.mobile.ui

import android.content.Context
import android.support.annotation.CallSuper

abstract class BaseInjectingFragment: BaseFragment() {

    @CallSuper protected open fun onInject() {}

    @CallSuper
    override fun onAttach(context: Context?) {
        onInject()
        super.onAttach(context)
    }
}