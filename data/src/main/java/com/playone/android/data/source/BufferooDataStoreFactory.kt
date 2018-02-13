package com.playone.android.data.source

import com.playone.android.data.repository.BufferooCache
import com.playone.android.data.repository.BufferooDataStore
import javax.inject.Inject

/**
 * Create an instance of a BufferooDataStore
 */
open class BufferooDataStoreFactory @Inject constructor(
        private val bufferooCache: BufferooCache,
        private val bufferooCacheDataStore: BufferooCacheDataStore,
        private val bufferooRemoteDataStore: BufferooRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(): BufferooDataStore {
        if (bufferooCache.isCached() && !bufferooCache.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveCacheDataStore(): BufferooDataStore {
        return bufferooCacheDataStore
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveRemoteDataStore(): BufferooDataStore {
        return bufferooRemoteDataStore
    }

}