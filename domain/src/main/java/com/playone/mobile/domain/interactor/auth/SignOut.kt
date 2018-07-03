package com.playone.mobile.domain.interactor.auth

import com.playone.mobile.domain.Authenticator
import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.CompletableUseCase
import com.playone.mobile.domain.model.User
import io.reactivex.Completable

class SignOut constructor(
    var authenticator: Authenticator,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    CompletableUseCase<Nothing>(threadExecutor, postExecutionThread) {

    private fun signOut() =
        Completable.create {
            authenticator.signOut(object : Authenticator.AuthResultCallBack {

                override fun onSuccessful(user: User) {

                    it.onComplete()
                }

                override fun onFailed(throwable: Throwable) {

                    it.onError(throwable)
                }
            })
        }

    override fun buildUseCaseObservable(params: Nothing): Completable = signOut()
}