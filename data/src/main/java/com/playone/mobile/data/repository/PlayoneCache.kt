package com.playone.mobile.data.repository

import com.playone.mobile.data.CacheChecker
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.PlayoneItem
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.ext.invalidInt
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Interface defining methods for the caching of [PlayoneItem]. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface PlayoneCache : CacheChecker {

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

    /**
     * Retrieve a list of [PlayoneEntity] from the cache.
     */
    fun getPlayoneList(userId: Int = invalidInt): Single<List<PlayoneEntity>>

    fun clearJoinedPlayoneList(): Completable

    fun saveJoinedPlayoneList(playoneList: List<PlayoneEntity>): Completable

    fun getJoinedPlayoneList(userId: Int): Single<List<PlayoneEntity>>

    fun clearFavoritePlayoneList(): Completable

    fun saveFavoritePlayoneList(playoneList: List<PlayoneEntity>): Completable

    fun getFavoritePlayoneList(userId: Int): Single<List<PlayoneEntity>>

    fun clearPlayoneDetail(): Completable

    fun savePlayoneDetail(playoneEntity: PlayoneEntity): Completable

    fun getPlayoneDetail(playoneId: Int): Single<PlayoneEntity>

    fun clearUserEntity(): Completable

    fun saveUserEntity(userEntity: UserEntity): Completable

    fun getUserEntityById(userId: Int): Single<UserEntity>

    fun getUserEntityByEmail(email: String): Single<UserEntity>

}