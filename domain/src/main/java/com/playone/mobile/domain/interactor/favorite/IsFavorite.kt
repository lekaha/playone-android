package com.playone.mobile.domain.interactor.favorite

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.SingleUseCase
import com.playone.mobile.domain.interactor.favorite.IsFavorite.Companion.PARAMS_PLAYONE_ID
import com.playone.mobile.domain.interactor.favorite.IsFavorite.Companion.PARAMS_USER_ID
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.repository.PlayoneRepository

/**
 * Use case used for modifying a [Playone] team group instances from the
 * [com.playone.mobile.domain.repository.PlayoneRepository].
 */
open class IsFavorite constructor(
    private val repository: PlayoneRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Boolean, HashMap<String, Any>>(threadExecutor, postExecutionThread) {

    companion object {
        const val PARAMS_PLAYONE_ID = "playone id"
        const val PARAMS_USER_ID = "user id"
    }

    public override fun buildUseCaseObservable(params: HashMap<String, Any>) =
        params.let {
            repository.isFavorite(it[PARAMS_PLAYONE_ID] as String, it[PARAMS_USER_ID] as String)
        }
}


fun FavoritePlayone.createParameter(playoneId: String, userId: String) =
        HashMap<String, Any>().apply {
            put(PARAMS_PLAYONE_ID, playoneId)
            put(PARAMS_USER_ID, userId)
        }