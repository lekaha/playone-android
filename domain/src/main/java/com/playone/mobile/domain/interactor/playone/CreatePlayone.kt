package com.playone.mobile.domain.interactor.playone

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.CompletableUseCase
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.repository.PlayoneRepository

/**
 * Use case used for creating a [Playone]
 */
class CreatePlayone constructor(
    private val repository: PlayoneRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<Playone.CreateParameters>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Playone.CreateParameters) =
        repository.savePlayoneDetail(params)
}