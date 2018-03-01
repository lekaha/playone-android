package com.playone.mobile.data.source

import com.playone.mobile.data.repository.PlayoneCache
import com.playone.mobile.data.repository.PlayoneDataStore

/**
 * Create an instance of a [PlayoneDataStore].
 */
open class PlayoneDataStoreFactory(
    private val cache: PlayoneCache,
    private val cacheDataStore: PlayoneCacheDataStore,
    private val remoteDataStore: PlayoneRemoteDataStore
) {

    /**
     * Returns a [PlayoneDataStore] based on whether or not there is content in the
     * cache and the cache has not expired.
     */
    open fun obtainDataStore() = if (cache.isCached() && !cache.isExpired()) {
        getCacheDataStore()
    }
    else {
        getRemoteDataStore()
    }

    /**
     * @return an instance of the Remote Data Store [PlayoneCacheDataStore].
     */
    open fun getCacheDataStore(): PlayoneDataStore = cacheDataStore

    /**
     * @return an instance of the cache data store [PlayoneRemoteDataStore].
     */
    open fun getRemoteDataStore(): PlayoneDataStore = remoteDataStore
}