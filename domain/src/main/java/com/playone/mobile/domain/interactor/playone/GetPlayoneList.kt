package com.playone.mobile.domain.interactor.playone

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.SingleUseCase
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.repository.PlayoneRepository

/**
 * Use case used for retrieving a [List] of [com.playone.mobile.domain.model.Playone]
 * instances from the [com.playone.mobile.domain.repository.PlayoneRepository]
 */
open class GetPlayoneList constructor(
    private val repository: PlayoneRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Playone>, String?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: String?) =
        params.let(repository::getPlayoneList)
}