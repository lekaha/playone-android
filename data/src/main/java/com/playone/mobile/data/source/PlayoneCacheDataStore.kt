package com.playone.mobile.data.source

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.data.repository.PlayoneCache
import com.playone.mobile.data.repository.PlayoneDataStore
import io.reactivex.Single

/**
 * Implementation of the [PlayoneDataStore] interface to provide a means of communicating
 * with the local data source [PlayoneCache].
 */
open class PlayoneCacheDataStore(private val cache: PlayoneCache) : PlayoneDataStore {

    override fun createPlayoneDetail(userId: String, playoneEntity: PlayoneEntity): Single<PlayoneEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updatePlayoneDetail(userId: String, playoneEntity: PlayoneEntity): Single<PlayoneEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deletePlayoneDetail(userId: String, playoneEntity: PlayoneEntity): Single<PlayoneEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchPlayoneDetail(userId: String, playoneId: String): Single<PlayoneEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchPlayoneList(userId: String): Single<List<PlayoneEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchFavoritePlayoneList(userId: String): Single<List<PlayoneEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchJoinedPlayoneList(userId: String): Single<List<PlayoneEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchAllPlayoneList(userId: String): Single<List<PlayoneEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createUser(userEntity: UserEntity): Single<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUser(userId: String, userEntity: UserEntity): Single<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteUser(userEntity: UserEntity): Single<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchUserByEmail(email: String): Single<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchUserById(userId: String): Single<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun favoritePlayone(playoneId: String, userId: String, isFavorite: Boolean) =
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    override fun isFavorite(playoneId: String, userId: String) =
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}
