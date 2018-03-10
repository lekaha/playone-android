package com.playone.mobile.domain.interactor.playone

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.SingleUseCase
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.repository.PlayoneRepository
import io.reactivex.Single

/**
 * Use case used for retrieving a [List] of [com.playone.mobile.domain.model.Playone]
 * instances from the [com.playone.mobile.domain.repository.PlayoneRepository]
 */
open class GetPlayoneList constructor(
    private val repository: PlayoneRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Playone>, Int?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Int?): Single<List<Playone>> =
        repository.getPlayoneList(params ?: -1)
}