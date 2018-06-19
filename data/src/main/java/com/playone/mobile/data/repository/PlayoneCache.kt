package com.playone.mobile.data.repository

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.PlayoneItem
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.domain.model.Playone
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Interface defining methods for the caching of [PlayoneItem]. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface PlayoneCache {

    /**
     * Clear all elements from the cache.
     */
    fun clearPlayoneList(): Completable

    /**
     * Save a given list of [PlayoneEntity] to the cache.
     *
     * @param playoneList a list of [PlayoneEntity].
     */
    fun savePlayoneList(playoneList: List<PlayoneEntity>): Completable

    fun getPlayoneList(): Single<List<PlayoneEntity>>

    fun clearJoinedPlayoneList(): Completable

    fun saveJoinedPlayoneList(playoneList: List<PlayoneEntity>): Completable

    fun getJoinedPlayoneList(): Single<List<PlayoneEntity>>

    fun clearFavoritePlayoneList(): Completable

    fun saveFavoritePlayoneList(playoneList: List<PlayoneEntity>): Completable

    fun getFavoritePlayoneList(): Single<List<PlayoneEntity>>

    fun clearPlayoneDetail(): Completable

    fun savePlayoneDetail(playoneEntity: PlayoneEntity): Completable

    fun getPlayoneDetail(playoneId: String): Single<PlayoneEntity>

    fun clearUser(): Completable

    fun saveUser(userEntity: UserEntity): Completable

    fun getUser(): Single<UserEntity>
}