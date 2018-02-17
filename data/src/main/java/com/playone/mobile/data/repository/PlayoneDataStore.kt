package com.playone.mobile.data.repository

import com.playone.mobile.data.model.BufferooEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to [com.playone.mobile.data.model.IPlayoneEntity].
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented.
 */
interface PlayoneDataStore {

    fun clearBufferoos(): Completable

    fun saveBufferoos(bufferoos: List<BufferooEntity>): Completable

    fun getBufferoos(): Single<List<BufferooEntity>>
}