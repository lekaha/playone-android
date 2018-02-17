package com.playone.mobile.data.repository

import com.playone.mobile.data.model.PlayoneEntity
import io.reactivex.Single

/**
 * Interface defining methods for the retrieving of Playone data. This is to be implemented by the
 * remote layer, using this interface as a way of communicating.
 */
interface PlayoneRemote {
    /**
     * Retrieve a list of Playone from the cache.
     *
     * @return a list of the [PlayoneEntity].
     */
    fun fetchPlayone(): Single<List<PlayoneEntity>>
}

