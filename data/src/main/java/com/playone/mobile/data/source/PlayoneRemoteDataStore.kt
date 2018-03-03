package com.playone.mobile.data.source

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.data.repository.PlayoneDataStore
import com.playone.mobile.data.repository.PlayoneRemote

/**
 * Implementation of the [PlayoneDataStore] interface to provide a means of communicating
 * with the remote data source [PlayoneRemote].
 */
open class PlayoneRemoteDataStore(private val remote: PlayoneRemote) : PlayoneDataStore {

    /**
     * Retrieve a list of [PlayoneEntity] instances from the API.
     */
    override fun getPlayoneList(userId: Int) = remote.fetchPlayoneList(userId)

    /**
     * Retrieve a list of the joined [PlayoneEntity] instances from the API.
     */
    override fun getJoinedPlayoneList(userId: Int) = remote.fetchJoinedPlayoneList(userId)

    /**
     * Retrieve a list of the favorite [PlayoneEntity] instances from the API.
     */
    override fun getFavoritePlayoneList(userId: Int) = remote.fetchFavoritePlayoneList(userId)

    /**
     * Retrieve an entity [PlayoneEntity] detail instances from the API.
     */
    override fun getPlayoneDetail(playoneId: Int) = remote.fetchPlayoneDetail(playoneId)

    /**
     * Retrieve an entity [UserEntity] instances from the API.
     */
    override fun getUserEntity(userId: Int) = remote.fetchUserEntity(userId)

    //region Unsupported operation for the remote data store.
    override fun clearPlayoneList() = throw UnsupportedOperationException()

    override fun savePlayoneList(playoneList: List<PlayoneEntity>) =
        throw UnsupportedOperationException()

    override fun clearJoinedPlayoneList() = throw UnsupportedOperationException()

    override fun saveJoinedPlayoneList(playoneList: List<PlayoneEntity>) =
        throw UnsupportedOperationException()

    override fun clearFavoritePlayoneList() = throw UnsupportedOperationException()

    override fun saveFavoritePlayoneList(playoneList: List<PlayoneEntity>) =
        throw UnsupportedOperationException()

    override fun clearPlayoneDetail() = throw UnsupportedOperationException()

    override fun savePlayoneDetail(playoneEntity: PlayoneEntity) =
        throw UnsupportedOperationException()

    override fun clearUserEntity() = throw UnsupportedOperationException()

    override fun saveUserEntity(userEntity: UserEntity) =
        throw UnsupportedOperationException()
    //endregion

}