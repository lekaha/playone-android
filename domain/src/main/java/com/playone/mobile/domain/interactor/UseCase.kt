package com.playone.mobile.domain.interactor

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Abstract class for a UseCase
 */
abstract class UseCase constructor(
    protected val threadExecutor: ThreadExecutor,
    protected val postExecutionThread: PostExecutionThread) {

    protected val disposables = CompositeDisposable()

    /**
     * Dispose from current [CompositeDisposable].
     */
    fun dispose() = if (!disposables.isDisposed) disposables.dispose() else Unit

    /**
     * Dispose from current [CompositeDisposable].
     */
    protected fun addDisposable(disposable: Disposable) = disposables.add(disposable)
}