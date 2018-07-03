package com.playone.mobile.data.source

import com.playone.mobile.data.CacheChecker
import com.playone.mobile.data.repository.PlayoneDataStore

/**
 * Create an instance of a [PlayoneDataStore].
 */
open class PlayoneDataStoreFactory(
    private val cacheChecker: CacheChecker,
    private val cacheDataStore: PlayoneCacheDataStore,
    private val remoteDataStore: PlayoneRemoteDataStore
) {

    /**
     * Returns a [PlayoneDataStore] based on whether or not there is content in the
     * cache and the cache has not expired.
     */
    open fun obtainDataStore() =
        if (cacheChecker.isCached("") && !cacheChecker.isExpired("")) {
            getCacheDataStore()
        }
        else {
            getRemoteDataStore()
        }

    /**
     * @return an instance of the Remote Data Store [PlayoneCacheDataStore].
     */
    open fun getCacheDataStore(): PlayoneCacheDataStore = cacheDataStore

    /**
     * @return an instance of the cache data store [PlayoneRemoteDataStore].
     */
    open fun getRemoteDataStore(): PlayoneRemoteDataStore = remoteDataStore
}