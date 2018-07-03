package com.playone.mobile.domain.interactor.playone

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.CompletableUseCase
import com.playone.mobile.domain.model.Join
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.repository.PlayoneRepository

/**
 * Use case used for modifying a [Playone] team group instances from the
 * [com.playone.mobile.domain.repository.PlayoneRepository].
 */
open class JoinPlayone constructor(
    private val repository: PlayoneRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<Join.Request>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Join.Request) = repository.joinPlayone(params)
}