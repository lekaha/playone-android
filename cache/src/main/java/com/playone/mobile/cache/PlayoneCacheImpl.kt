package com.playone.mobile.cache

import android.database.sqlite.SQLiteDatabase
import com.playone.mobile.cache.db.DbOpenHelper
import com.playone.mobile.cache.db.mapper.PlayoneMapper
import com.playone.mobile.data.model.NotificationPayloadEntity
import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import com.playone.mobile.data.repository.PlayoneCache
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Cached implementation for retrieving and saving Bufferoo instances. This class implements the
 * [PlayoneCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class PlayoneCacheImpl constructor(
    dbOpenHelper: DbOpenHelper,
    private val mapper: PlayoneMapper,
    private val preferencesHelper: PreferencesHelper
) :
    PlayoneCache {

    override fun clearPlayoneList(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun savePlayoneList(playoneList: List<PlayoneEntity>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchPlayoneList(userId: String?) = TODO()

    override fun clearJoinedPlayoneList(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveJoinedPlayoneList(playoneList: List<PlayoneEntity>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchJoinedPlayoneList(userId: Int): Single<List<PlayoneEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearFavoritePlayoneList(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveFavoritePlayoneList(playoneList: List<PlayoneEntity>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchFavoritePlayoneList(userId: Int): Single<List<PlayoneEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearPlayoneDetail(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun savePlayoneDetail(playoneEntity: PlayoneEntity): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchPlayoneDetail(playoneId: Int): Single<PlayoneEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearUserEntity(userEntity: UserEntity): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveUserEntity(userEntity: UserEntity): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchUserEntity(userId: Int): Single<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchUserEntity(email: String): Single<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createPlayoneDetail(userId: Int, playoneEntity: PlayoneEntity) = TODO()

    override fun updatePlayoneDetail(userId: Int, playoneEntity: PlayoneEntity) = TODO()

    override fun joinTeamAsMember(playoneId: Int, userId: Int, isJoin: Boolean) = TODO()

    override fun sendJoinRequest(playoneId: Int, userId: Int, msg: String) = TODO()

    override fun toggleFavorite(playoneId: Int, userId: Int) = TODO()

    override fun isFavorite(playoneId: Int, userId: Int) = TODO()

    override fun isJoined(playoneId: Int, userId: Int) = TODO()

    override fun createUser(userEntity: UserEntity) = TODO()

    override fun updateUser(userEntity: UserEntity) = TODO()

    override fun updateUser(userEntity: UserEntity, lastDeviceToken: String) = TODO()

    override fun applyNotification(payload: NotificationPayloadEntity) = TODO()

    override fun acceptedNotification(payload: NotificationPayloadEntity) = TODO()

    override fun acceptNotification(payload: NotificationPayloadEntity) = TODO()

    override fun dismissNotification(payload: NotificationPayloadEntity) = TODO()

    override fun kickNotification(payload: NotificationPayloadEntity) = TODO()

    override fun quitNotification(payload: NotificationPayloadEntity) = TODO()

    override fun rejectedNotification(payload: NotificationPayloadEntity) = TODO()

    override fun rejectNotification(payload: NotificationPayloadEntity) = TODO()

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

    private var database = dbOpenHelper.writableDatabase

    /**
     * Retrieve an instance from the database, used for tests
     */
    internal fun getDatabase(): SQLiteDatabase {
        return database
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

}