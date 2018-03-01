package com.playone.mobile.data.source

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.repository.PlayoneCache
import com.playone.mobile.data.repository.PlayoneDataStore
import com.playone.mobile.ext.system.currentTime
import io.reactivex.Single

/**
 * Implementation of the [PlayoneDataStore] interface to provide a means of communicating
 * with the local data source [PlayoneCache].
 */
open class PlayoneCacheDataStore(private val cache: PlayoneCache) : PlayoneDataStore {

    override fun clearPlayoneList() = cache.clearPlayone()

    override fun savePlayoneList(playoneList: List<PlayoneEntity>) =
        cache.savePlayone(playoneList).doOnComplete { cache.setLastCacheTime(currentTime) }

    override fun getPlayoneList() = cache.getPlayone() as Single<List<PlayoneEntity>>
}