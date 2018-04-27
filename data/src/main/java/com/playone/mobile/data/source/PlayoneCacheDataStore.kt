package com.playone.mobile.data.source

import com.playone.mobile.data.model.NotificationPayloadEntity
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

    override fun clearPlayoneList() = cache.clearPlayoneList()

    override fun savePlayoneList(playoneList: List<PlayoneEntity>) = TODO()
//        cache.savePlayoneList(playoneList).doOnComplete { cache.keepLastCacheTime("") }

    override fun fetchPlayoneList() = cache.fetchPlayoneList()

    override fun fetchPlayoneList(userId: String) = cache.fetchPlayoneList(userId)

    override fun clearJoinedPlayoneList() = cache.clearJoinedPlayoneList()

    override fun saveJoinedPlayoneList(playoneList: List<PlayoneEntity>) = TODO()
//        cache.saveJoinedPlayoneList(playoneList).doOnComplete { cache.keepLastCacheTime("") }

    override fun fetchJoinedPlayoneList(userId: Int) = cache.fetchJoinedPlayoneList(userId)

    override fun clearFavoritePlayoneList() = cache.clearFavoritePlayoneList()

    override fun saveFavoritePlayoneList(playoneList: List<PlayoneEntity>) = TODO()
//        cache.saveFavoritePlayoneList(playoneList).doOnComplete { cache.keepLastCacheTime("") }

    override fun fetchFavoritePlayoneList(userId: Int) = cache.fetchFavoritePlayoneList(userId)

    override fun clearPlayoneDetail() = cache.clearPlayoneDetail()

    override fun savePlayoneDetail(userId: String, playoneEntity: PlayoneEntity) = TODO()
//        cache.savePlayoneDetail(playoneEntity).doOnComplete { cache.keepLastCacheTime("") }

    override fun fetchPlayoneDetail(playoneId: Int) = cache.fetchPlayoneDetail(playoneId)

    override fun clearUserEntity(userEntity: UserEntity) = cache.clearUserEntity(userEntity)

    override fun saveUserEntity(userEntity: UserEntity) = TODO()
//        cache.saveUserEntity(userEntity).doOnComplete { cache.keepLastCacheTime("") }

    override fun fetchUserEntity(userId: Int) = cache.fetchUserEntity(userId)

    override fun fetchUserEntity(email: String) = cache.fetchUserEntity(email)

    //region Unsupported Operations
    override fun createUser(userEntity: UserEntity) = throw UnsupportedOperationException()

    override fun createPlayoneDetail(userId: String, playoneEntity: PlayoneEntity) =
        throw UnsupportedOperationException()

    override fun updatePlayoneDetail(userId: String, playoneEntity: PlayoneEntity) =
        throw UnsupportedOperationException()

    override fun joinTeamAsMember(playoneId: Int, userId: Int, isJoin: Boolean) =
        throw UnsupportedOperationException()

    override fun sendJoinRequest(playoneId: Int, userId: Int, msg: String) =
        throw UnsupportedOperationException()

    override fun toggleFavorite(playoneId: Int, userId: Int): Single<Boolean> =
        throw UnsupportedOperationException()

    override fun isFavorite(playoneId: Int, userId: Int): Single<Boolean> =
        throw UnsupportedOperationException()

    override fun isJoined(playoneId: Int, userId: Int): Single<Boolean> =
        throw UnsupportedOperationException()

    override fun updateUser(userEntity: UserEntity) =
        throw UnsupportedOperationException()

    override fun updateUser(userEntity: UserEntity, lastDeviceToken: String) =
        throw UnsupportedOperationException()

    override fun applyNotification(payload: NotificationPayloadEntity) =
        throw UnsupportedOperationException()

    override fun acceptedNotification(payload: NotificationPayloadEntity) =
        throw UnsupportedOperationException()

    override fun acceptNotification(payload: NotificationPayloadEntity) =
        throw UnsupportedOperationException()

    override fun dismissNotification(payload: NotificationPayloadEntity) =
        throw UnsupportedOperationException()

    override fun kickNotification(payload: NotificationPayloadEntity) =
        throw UnsupportedOperationException()

    override fun quitNotification(payload: NotificationPayloadEntity) =
        throw UnsupportedOperationException()

    override fun rejectedNotification(payload: NotificationPayloadEntity) =
        throw UnsupportedOperationException()

    override fun rejectNotification(payload: NotificationPayloadEntity) =
        throw UnsupportedOperationException()
    //endregion
}