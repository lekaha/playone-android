package com.playone.mobile.data.source

import com.playone.mobile.data.repository.BufferooCache
import com.playone.mobile.data.repository.BufferooDataStore

/**
 * Create an instance of a BufferooDataStore
 */
open class BufferooDataStoreFactory constructor(
        private val bufferooCache: BufferooCache,
        private val bufferooCacheDataStore: BufferooCacheDataStore,
        private val bufferooRemoteDataStore: BufferooRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore() = if (bufferooCache.isCached() && !bufferooCache.isExpired()) {
        retrieveCacheDataStore()
    }
    else {
        retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveCacheDataStore(): BufferooDataStore = bufferooCacheDataStore

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveRemoteDataStore(): BufferooDataStore = bufferooRemoteDataStore

}