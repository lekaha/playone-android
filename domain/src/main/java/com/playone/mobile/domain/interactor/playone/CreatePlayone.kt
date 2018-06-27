package com.playone.mobile.domain.interactor.playone

import com.playone.mobile.common.exception.NotSignedInException
import com.playone.mobile.common.exception.NotVerifiedEmailException
import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.SingleUseCase
import com.playone.mobile.domain.interactor.auth.SignUpAndSignIn
import com.playone.mobile.domain.model.Playone
import com.playone.mobile.domain.repository.PlayoneRepository
import com.playone.mobile.ext.ifFalse
import io.reactivex.Single

/**
 * Use case used for creating a [Playone]
 */
class CreatePlayone constructor(
    private val signUpAndSignIn: SignUpAndSignIn,
    private val getCurrentUser: GetCurrentUser,
    private val repository: PlayoneRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
): SingleUseCase<Playone, Playone.CreateParameters>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Playone.CreateParameters): Single<Playone> =
        if (signUpAndSignIn.isSignedIn()) {
            getCurrentUser.buildUseCaseObservable().flatMap {
                // TODO: should do verify
//                it.isVerified.ifFalse {
//                    throw NotVerifiedEmailException()
//                }
                repository.createPlayone(params)
            }
        } else {
            Single.error(NotSignedInException())
        }

}