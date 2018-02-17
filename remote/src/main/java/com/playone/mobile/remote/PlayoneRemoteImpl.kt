package com.playone.mobile.remote

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.repository.PlayoneRemote
import com.playone.mobile.remote.mapper.PlayoneEntityMapper

/**
 * Remote implementation for retrieving Playone instances. This class implements the
 * [com.playone.mobile.data.repository.PlayoneRemote] from the Data layer as it is that
 * layers responsibility for defining the operations in which data store implementation
 * layers can carry out.
 */
class PlayoneRemoteImpl constructor(
    private val service: PlayoneService,
    private val entityMapper: PlayoneEntityMapper
) : PlayoneRemote {
    /**
     * Retrieve a list of Playone from the cache.
     *
     * @return a list of the [PlayoneEntity].
     */
    override fun fetchPlayone() = Unit
}