package com.playone.mobile.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import com.playone.mobile.ui.ext.debugTree
import com.playone.mobile.ui.ext.plant
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity: DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        plant { debugTree() }
        setContentView(getLayoutId())
        super.onCreate(savedInstanceState)
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int
}