package com.playone.mobile.ui

import com.playone.mobile.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * MainThread (UI Thread) implementation based on a [Scheduler]
 * which will execute actions on the Android UI thread
 */
class UiThread internal constructor() : PostExecutionThread {

    override val scheduler get() = AndroidSchedulers.mainThread()

}