package com.playone.mobile.data

import com.playone.mobile.data.model.PlayoneEntity

interface CacheChecker {

    /**
     * Checks if an element [PlayoneEntity] exists in the cache.
     *
     * @param which check a column is cached.
     * @return true if the element is cached, otherwise false.
     */
    fun isCached(which: String): Boolean

    /**
     * Checks if the cache is expired.
     *
     * @param which check a column is expired.
     * @return true, the cache is expired, otherwise false.
     */
    fun isExpired(which: String): Boolean

    /**
     * Keep the last caching the item time.
     *
     * @param which
     */
    fun keepLastCacheTime(which: String)

}