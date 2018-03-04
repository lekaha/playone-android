package com.playone.mobile.domain.interactor.auth

import com.playone.mobile.domain.Authenticator
import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.UseCase

class SignOut constructor(var authenticator: Authenticator,
                          threadExecutor: ThreadExecutor,
                          postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    fun signOut() = authenticator.signOut()
}