package com.playone.mobile.domain.interactor.playone

import com.playone.mobile.domain.Authenticator
import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.SingleUseCaseWithoutParams
import com.playone.mobile.domain.model.User
import io.reactivex.Single

/**
 * Use case used for retrieving the current user
 */
open class GetCurrentUser constructor(
    private var authenticator: Authenticator,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCaseWithoutParams<User>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(): Single<User> =
        Single.just(authenticator.getCurrentUser())
}