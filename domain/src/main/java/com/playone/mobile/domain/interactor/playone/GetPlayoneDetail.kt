package com.playone.mobile.domain.interactor.playone

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.SingleUseCase
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.repository.PlayoneRepository

/**
 * Use case used for retrieving a [com.playone.mobile.domain.model.Playone]'s detail
 * instances from the [com.playone.mobile.domain.repository.PlayoneRepository]
 */
open class GetPlayoneDetail constructor(
    private val repository: PlayoneRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Playone, Pair<String, String>>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Pair<String, String>) =
        repository.getPlayoneDetail(params.first, params.second)

}