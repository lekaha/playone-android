package com.playone.mobile.data.source

import com.playone.mobile.data.model.BufferooEntity
import com.playone.mobile.data.repository.BufferooCache
import com.playone.mobile.data.repository.BufferooDataStore

/**
 * Implementation of the [BufferooDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class BufferooCacheDataStore constructor(private val bufferooCache: BufferooCache) :
        BufferooDataStore {

    /**
     * Clear all Bufferoos from the cache
     */
    override fun clearBufferoos() = bufferooCache.clearBufferoos()

    /**
     * Save a given [List] of [BufferooEntity] instances to the cache
     */
    override fun saveBufferoos(bufferoos: List<BufferooEntity>) =
        bufferooCache.saveBufferoos(bufferoos)
            .doOnComplete { bufferooCache.setLastCacheTime(System.currentTimeMillis()) }

    /**
     * Retrieve a list of [BufferooEntity] instance from the cache
     */
    override fun getBufferoos() = bufferooCache.getBufferoos()

}