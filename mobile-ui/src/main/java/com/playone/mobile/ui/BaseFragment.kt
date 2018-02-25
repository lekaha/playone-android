package com.playone.mobile.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.playone.mobile.ui.ext.debugTree
import com.playone.mobile.ui.ext.plant
import dagger.android.support.DaggerFragment

abstract class BaseFragment: DaggerFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        plant { debugTree() }
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) =
        inflater.inflate(getLayoutId(), container, false)
}