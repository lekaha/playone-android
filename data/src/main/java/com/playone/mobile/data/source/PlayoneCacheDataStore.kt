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

//    override fun clearPlayoneList() = cache.clearPlayoneList()
//
//    override fun savePlayoneList(playoneList: List<PlayoneEntity>) = TODO()
////        cache.savePlayoneList(playoneList).doOnComplete { cache.keepLastCacheTime("") }
//
//    override fun clearJoinedPlayoneList() = cache.clearJoinedPlayoneList()
//
//    override fun saveJoinedPlayoneList(playoneList: List<PlayoneEntity>) = TODO()
////        cache.saveJoinedPlayoneList(playoneList).doOnComplete { cache.keepLastCacheTime("") }
//
//    override fun clearFavoritePlayoneList() = cache.clearFavoritePlayoneList()
//
//    override fun saveFavoritePlayoneList(playoneList: List<PlayoneEntity>) = TODO()
////        cache.saveFavoritePlayoneList(playoneList).doOnComplete { cache.keepLastCacheTime("") }
//
//    override fun clearPlayoneDetail() = cache.clearPlayoneDetail()
//
//    override fun savePlayoneDetail(userId: String, playoneEntity: PlayoneEntity) = TODO()
////        cache.savePlayoneDetail(playoneEntity).doOnComplete { cache.keepLastCacheTime("") }
//
//    override fun clearUserEntity(userEntity: UserEntity) = cache.clearUserEntity(userEntity)
//
//    override fun saveUserEntity(userEntity: UserEntity) = TODO()
////        cache.saveUserEntity(userEntity).doOnComplete { cache.keepLastCacheTime("") }
//
//    //region Unsupported Operations
//    override fun fetchPlayoneList() = throw UnsupportedOperationException()
////        cache.fetchPlayoneList()
//
//    override fun fetchPlayoneList(userId: String) = throw UnsupportedOperationException()
////        cache.fetchPlayoneList(userId)
//
//    override fun fetchJoinedPlayoneList(userId: String) = throw UnsupportedOperationException()
////        cache.fetchJoinedPlayoneList(userId)
//
//    override fun fetchFavoritePlayoneList(userId: String) = throw UnsupportedOperationException()
////        cache.fetchFavoritePlayoneList(userId)
//
//    override fun fetchUserEntity(userId: String) = throw UnsupportedOperationException()
////        cache.fetchUserEntity(userId)
//
//    override fun fetchUserEntityByEmail(email: String) = throw UnsupportedOperationException()
////        cache.fetchUserEntity(email)
//
//    override fun fetchPlayoneDetail(playoneId: String) = throw UnsupportedOperationException()
////        cache.fetchPlayoneDetail(playoneId)
//
//    override fun createUser(userEntity: UserEntity) = throw UnsupportedOperationException()
//
//    override fun createPlayoneDetail(userId: String, playoneEntity: PlayoneEntity) =
//        throw UnsupportedOperationException()
//
//    override fun updatePlayoneDetail(userId: String, playoneEntity: PlayoneEntity) =
//        throw UnsupportedOperationException()
//
//    override fun joinTeamAsMember(playoneId: String, userId: String, isJoin: Boolean) =
//        throw UnsupportedOperationException()
//
//    override fun sendJoinRequest(playoneId: String, userId: String, msg: String) =
//        throw UnsupportedOperationException()
//
//    override fun toggleFavorite(playoneId: String, userId: String): Single<Boolean> =
//        throw UnsupportedOperationException()
//
//    override fun isFavorite(playoneId: String, userId: String): Single<Boolean> =
//        throw UnsupportedOperationException()
//
//    override fun isJoined(playoneId: String, userId: String): Single<Boolean> =
//        throw UnsupportedOperationException()
//
//    override fun updateUser(userEntity: UserEntity) =
//        throw UnsupportedOperationException()
//
//    override fun updateUser(userEntity: UserEntity, lastDeviceToken: String) =
//        throw UnsupportedOperationException()
//
//    override fun applyNotification(payload: NotificationPayloadEntity) =
//        throw UnsupportedOperationException()
//
//    override fun acceptedNotification(payload: NotificationPayloadEntity) =
//        throw UnsupportedOperationException()
//
//    override fun acceptNotification(payload: NotificationPayloadEntity) =
//        throw UnsupportedOperationException()
//
//    override fun dismissNotification(payload: NotificationPayloadEntity) =
//        throw UnsupportedOperationException()
//
//    override fun kickNotification(payload: NotificationPayloadEntity) =
//        throw UnsupportedOperationException()
//
//    override fun quitNotification(payload: NotificationPayloadEntity) =
//        throw UnsupportedOperationException()
//
//    override fun rejectedNotification(payload: NotificationPayloadEntity) =
//        throw UnsupportedOperationException()
//
//    override fun rejectNotification(payload: NotificationPayloadEntity) =
//        throw UnsupportedOperationException()
//    //endregion
}
