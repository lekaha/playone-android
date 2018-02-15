package com.playone.mobile.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import com.playone.mobile.data.model.BufferooEntity

/**
 * Interface defining methods for the data operations related to Bufferroos.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface BufferooDataStore {

    fun clearBufferoos(): Completable

    fun saveBufferoos(bufferoos: List<BufferooEntity>): Completable

    fun getBufferoos(): Single<List<BufferooEntity>>

}