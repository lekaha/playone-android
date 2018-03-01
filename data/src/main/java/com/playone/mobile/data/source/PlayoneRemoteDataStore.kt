package com.playone.mobile.data.source

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.repository.PlayoneDataStore
import com.playone.mobile.data.repository.PlayoneRemote
import io.reactivex.Single

/**
 * Implementation of the [PlayoneDataStore] interface to provide a means of communicating
 * with the remote data source [PlayoneRemote].
 */
open class PlayoneRemoteDataStore(private val remote: PlayoneRemote) : PlayoneDataStore {

    override fun clearPlayoneList() = throw UnsupportedOperationException()

    override fun savePlayoneList(playoneList: List<PlayoneEntity>) =
        throw UnsupportedOperationException()

    /**
     * Retrieve a list of [PlayoneEntity] instances from the API
     */
    override fun getPlayoneList(): Single<List<PlayoneEntity>> = remote.fetchPlayoneList()
}