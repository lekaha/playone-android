package com.playone.mobile.domain.interactor.playone

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.SingleUseCase
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.repository.PlayoneRepository
import java.security.InvalidParameterException

/**
 * Use case used for modifying a [Playone] team group instances from the
 * [com.playone.mobile.domain.repository.PlayoneRepository].
 */
open class IsJoined constructor(
    private val repository: PlayoneRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Boolean, HashMap<String, Any>>(threadExecutor, postExecutionThread) {

    companion object {
        const val PARAMS_PLAYONE_ID = "playone id"
        const val PARAMS_USER_ID = "user id"
    }

    public override fun buildUseCaseObservable(params: HashMap<String, Any>?) =
        params?.let {
            repository.isJoined(it[PARAMS_PLAYONE_ID] as Int, it[PARAMS_USER_ID] as Int)
        } ?: throw InvalidParameterException()
}