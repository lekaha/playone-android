package com.playone.mobile.domain.interactor

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Abstract class for a UseCase that returns an instance of a [Completable].
 */
abstract class CompletableUseCase<in Params> protected constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    /**
     * Builds a [Completable] which will be used when the current [CompletableUseCase] is executed.
     */
    abstract fun buildUseCaseObservable(params: Params): Completable

    /**
     * Executes the current use case.
     */
    fun execute(params: Params) = this.buildUseCaseObservable(params)
        .subscribeOn(Schedulers.from(threadExecutor))
        .observeOn(postExecutionThread.scheduler)

    /**
     * Unsubscribes from current [Disposable].
     */
    fun unsubscribe() = if (!disposables.isDisposed) disposables.dispose() else Unit

}