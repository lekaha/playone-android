package com.playone.mobile.data.source

import com.playone.mobile.data.model.BufferooEntity
import com.playone.mobile.data.repository.BufferooDataStore
import com.playone.mobile.data.repository.BufferooRemote
import io.reactivex.Completable

/**
 * Implementation of the [BufferooDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class BufferooRemoteDataStore constructor(private val bufferooRemote: BufferooRemote) :
        BufferooDataStore {

    override fun clearBufferoos(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveBufferoos(bufferoos: List<BufferooEntity>): Completable {
        throw UnsupportedOperationException()
    }

    /**
     * Retrieve a list of [BufferooEntity] instances from the API
     */
    override fun getBufferoos() = bufferooRemote.getBufferoos()

}