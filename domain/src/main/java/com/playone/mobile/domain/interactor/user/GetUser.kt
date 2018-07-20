package com.playone.mobile.domain.interactor.user

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.SingleUseCase
import com.playone.mobile.domain.model.User
import com.playone.mobile.domain.repository.PlayoneRepository
import io.reactivex.Single

class GetUser constructor(
    private val repository: PlayoneRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<User, String>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(userId: String): Single<User> =
        repository.getUserById(userId)
}