package com.playone.mobile.domain.interactor.playone

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.SingleUseCase
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.repository.PlayoneRepository
import java.security.InvalidParameterException

/**
 * Use case used for creating a [Playone] team group instances from the
 * [com.playone.mobile.domain.repository.PlayoneRepository].
 */
open class CreatePlayone constructor(
    private val repository: PlayoneRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Boolean, HashMap<String, Any>>(threadExecutor, postExecutionThread) {

    companion object {
        const val PARAMS_ID = "user id"
        const val PARAMS_PLAYONE = "playone"
    }

    public override fun buildUseCaseObservable(params: HashMap<String, Any>?) =
        params?.let {
            repository.createPlayone(it[PARAMS_ID] as Int, it[PARAMS_PLAYONE] as Playone)
        } ?: throw InvalidParameterException()
}