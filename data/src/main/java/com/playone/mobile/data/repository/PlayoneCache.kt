package com.playone.mobile.data.repository

import com.playone.mobile.data.model.IPlayoneEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Interface defining methods for the caching of [IPlayoneEntity]. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface PlayoneCache {
    /**
     * Clear all elements from the cache.
     */
    fun clearPlayone(): Completable

    /**
     * Save a given list of [IPlayoneEntity] to the cache.
     *
     * @param playones a list of [IPlayoneEntity].
     */
    fun savePlayone(playones: List<IPlayoneEntity>): Completable

    /**
     * Retrieve a list of [IPlayoneEntity] from the cache.
     *
     * @return
     */
    fun getPlayone(): Single<List<IPlayoneEntity>>

    /**
     * Checks if an element [IPlayoneEntity] exists in the cache.
     *
     * @return true if the element is cached, otherwise false.
     */
    fun isCached(): Boolean

    // TODO(jieyi): 2018/02/17 Add the comment here. I'm so sorry that I don't really get the meaning of this.
    fun setLastCacheTime(lastCache: Long)

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    fun isExpired(): Boolean
}