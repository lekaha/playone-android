package com.playone.mobile.ui

import com.playone.mobile.ui.ext.debugTree
import com.playone.mobile.ui.ext.plant
import com.playone.mobile.ui.injection.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BufferooApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        var appComponent = DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
        appComponent.inject(this)
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            plant{
                debugTree()
            }
        }
    }

}
