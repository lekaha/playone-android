package com.playone.mobile.domain.interactor.user

import com.playone.mobile.domain.executor.PostExecutionThread
import com.playone.mobile.domain.executor.ThreadExecutor
import com.playone.mobile.domain.interactor.SingleUseCase
import com.playone.mobile.domain.model.User
import com.playone.mobile.domain.repository.PlayoneRepository

class UpdateUserProfile(
    val playoneRepository: PlayoneRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
): SingleUseCase<User, User.Update>(
    threadExecutor,
    postExecutionThread
) {

    override fun buildUseCaseObservable(params: User.Update) =
        playoneRepository.updateUser(params.userId, params)

}