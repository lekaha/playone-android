package com.playone.mobile.domain.interactor.favorite

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.CompletableUseCase
import com.playone.mobile.domain.interactor.favorite.FavoritePlayone.Companion.PARAMS_IS_FAVORITE
import com.playone.mobile.domain.interactor.favorite.FavoritePlayone.Companion.PARAMS_PLAYONE_ID
import com.playone.mobile.domain.interactor.favorite.FavoritePlayone.Companion.PARAMS_USER_ID
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.repository.PlayoneRepository

/**
 * Use case used for modifying a [Playone] team group instances from the
 * [com.playone.mobile.domain.repository.PlayoneRepository].
 */
open class FavoritePlayone constructor(
    private val repository: PlayoneRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<HashMap<String, Any>>(threadExecutor, postExecutionThread) {

    companion object {
        internal const val PARAMS_PLAYONE_ID = "playone id"
        internal const val PARAMS_USER_ID = "user id"
        internal const val PARAMS_IS_FAVORITE = "is favorite"
    }

    override fun buildUseCaseObservable(params: HashMap<String, Any>) =
        repository.favoritePlayone(
                params[PARAMS_PLAYONE_ID] as String,
                params[PARAMS_USER_ID] as String,
                params[PARAMS_IS_FAVORITE] as Boolean
                )
}

fun FavoritePlayone.createParameter(playoneId: String, userId: String, isFavorite: Boolean) =
        HashMap<String, Any>().apply {
            put(PARAMS_PLAYONE_ID, playoneId)
            put(PARAMS_USER_ID, userId)
            put(PARAMS_IS_FAVORITE, isFavorite)
        }