package com.playone.android.domain.interactor.browse

import io.reactivex.Single
import com.playone.android.domain.executor.PostExecutionThread
import com.playone.android.domain.executor.ThreadExecutor
import com.playone.android.domain.interactor.SingleUseCase
import com.playone.android.domain.model.Bufferoo
import com.playone.android.domain.repository.BufferooRepository
import javax.inject.Inject

/**
 * Use case used for retreiving a [List] of [Bufferoo] instances from the [BufferooRepository]
 */
open class GetBufferoos @Inject constructor(val bufferooRepository: BufferooRepository,
                                            threadExecutor: ThreadExecutor,
                                            postExecutionThread: PostExecutionThread):
        SingleUseCase<List<Bufferoo>, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Single<List<Bufferoo>> {
        return bufferooRepository.getBufferoos()
    }

}