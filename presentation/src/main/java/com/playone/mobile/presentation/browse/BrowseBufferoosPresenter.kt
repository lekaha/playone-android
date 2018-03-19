package com.playone.mobile.presentation.browse

import com.playone.mobile.domain.interactor.SingleUseCase
import com.playone.mobile.domain.model.Bufferoo
import com.playone.mobile.presentation.ViewResponse
import com.playone.mobile.presentation.mapper.BufferooMapper
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class BrowseBufferoosPresenter
@Inject constructor(
    val getBufferoosUseCase: SingleUseCase<List<Bufferoo>, Unit>,
    val bufferooMapper: BufferooMapper
) :
        BrowseBufferoosContract.Presenter {

    var browseView: BrowseBufferoosContract.View? = null

    override fun setView(view: BrowseBufferoosContract.View) {
        browseView = view
    }

    override fun start() {
        retrieveBufferoos()
        browseView?.onResponse(ViewResponse.loading())
    }

    override fun stop() {
        getBufferoosUseCase.dispose()
    }

    override fun retrieveBufferoos() {
        getBufferoosUseCase.execute(BufferooSubscriber(), {}())
    }

    internal fun handleGetBufferoosSuccess(bufferoos: List<Bufferoo>) {
        browseView?.onResponse(ViewResponse.success(bufferoos.map {
            bufferooMapper.mapToView(it)
        }))
    }

    inner class BufferooSubscriber: DisposableSingleObserver<List<Bufferoo>>() {

        override fun onSuccess(t: List<Bufferoo>) {
            handleGetBufferoosSuccess(t)
        }

        override fun onError(exception: Throwable) {
            browseView?.onResponse(ViewResponse.error(exception))
        }

    }

}