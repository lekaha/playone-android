package com.playone.mobile.data.source

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.data.repository.PlayoneCache
import com.playone.mobile.data.repository.PlayoneDataStore

/**
 * Implementation of the [PlayoneDataStore] interface to provide a means of communicating
 * with the local data source [PlayoneCache].
 */
open class PlayoneCacheDataStore(private val cache: PlayoneCache) : PlayoneDataStore {

    override fun clearPlayoneList() = cache.clearPlayoneList()

    override fun savePlayoneList(playoneList: List<PlayoneEntity>) =
        cache.savePlayoneList(playoneList).doOnComplete { cache.keepLastCacheTime("") }

    override fun getPlayoneList(userId: Int) = cache.getPlayoneList(userId)

    override fun clearJoinedPlayoneList() = cache.clearJoinedPlayoneList()

    override fun saveJoinedPlayoneList(playoneList: List<PlayoneEntity>) =
        cache.saveJoinedPlayoneList(playoneList).doOnComplete { cache.keepLastCacheTime("") }

    override fun getJoinedPlayoneList(userId: Int) = cache.getJoinedPlayoneList(userId)

    override fun clearFavoritePlayoneList() = cache.clearFavoritePlayoneList()

    override fun saveFavoritePlayoneList(playoneList: List<PlayoneEntity>) =
        cache.saveFavoritePlayoneList(playoneList).doOnComplete { cache.keepLastCacheTime("") }

    override fun getFavoritePlayoneList(userId: Int) = cache.getFavoritePlayoneList(userId)

    override fun clearPlayoneDetail() = cache.clearPlayoneDetail()

    override fun savePlayoneDetail(playoneEntity: PlayoneEntity) =
        cache.savePlayoneDetail(playoneEntity).doOnComplete { cache.keepLastCacheTime("") }

    override fun getPlayoneDetail(playoneId: Int) = cache.getPlayoneDetail(playoneId)

    override fun clearUserEntity() = cache.clearUserEntity()

    override fun saveUserEntity(userEntity: UserEntity) =
        cache.saveUserEntity(userEntity).doOnComplete { cache.keepLastCacheTime("") }

    override fun getUserEntity(userId: Int) = cache.getUserEntity(userId)

}