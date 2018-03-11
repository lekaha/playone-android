package com.playone.mobile.domain.interactor.playone

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.SingleUseCase
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.repository.PlayoneRepository
import java.security.InvalidParameterException

/**
 * Use case used for retrieving a [List] of [Playone] team group instances from the
 * [com.playone.mobile.domain.repository.PlayoneRepository].
 */
open class GetFavotitePlayoneList constructor(
    private val repository: PlayoneRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Playone>, Int>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Int?) =
        params?.let(repository::getFavoritePlayoneList) ?: throw InvalidParameterException()
}