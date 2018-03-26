package com.playone.mobile.domain.interactor

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Abstract class for a UseCase that returns an instance of a [Single].
 */
abstract class SingleUseCaseWithoutParams<T> constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    /**
     * Builds a [Single] which will be used when the current [SingleUseCaseWithoutParams] is executed.
     */
    protected abstract fun buildUseCaseObservable(): Single<T>

    /**
     * Executes the current use case.
     */
    open fun execute(singleObserver: DisposableSingleObserver<T>) {
        val single = this.buildUseCaseObservable()
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(postExecutionThread.scheduler) as Single<T>
        addDisposable(single.subscribeWith(singleObserver))
    }
}