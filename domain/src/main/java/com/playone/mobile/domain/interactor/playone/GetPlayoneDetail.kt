package com.playone.mobile.domain.interactor.playone

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.SingleUseCase
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.repository.PlayoneRepository
import com.playone.mobile.ext.isNotNull
import java.security.InvalidParameterException

/**
 * Use case used for retrieving a [com.playone.mobile.domain.model.Playone]'s detail
 * instances from the [com.playone.mobile.domain.repository.PlayoneRepository]
 */
open class GetPlayoneDetail constructor(
    private val repository: PlayoneRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Playone, Int?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Int?) =
        params
            .takeIf(Int?::isNotNull)
            ?.let(repository::getPlayoneDetail) ?: throw InvalidParameterException()
}