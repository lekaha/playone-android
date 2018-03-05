package com.playone.mobile.cache

import android.database.sqlite.SQLiteDatabase
import com.playone.mobile.cache.db.DbOpenHelper
import com.playone.mobile.cache.db.mapper.PlayoneMapper
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
class PlayoneCacheImpl constructor(dbOpenHelper: DbOpenHelper,
                                   private val mapper: PlayoneMapper,
                                   private val preferencesHelper: PreferencesHelper):
    PlayoneCache {

    override fun isCached(which: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isExpired(which: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearPlayoneList(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun keepLastCacheTime(which: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun savePlayoneList(playoneList: List<PlayoneEntity>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlayoneList(userId: Int): Single<List<PlayoneEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearJoinedPlayoneList(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveJoinedPlayoneList(playoneList: List<PlayoneEntity>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getJoinedPlayoneList(userId: Int): Single<List<PlayoneEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearFavoritePlayoneList(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveFavoritePlayoneList(playoneList: List<PlayoneEntity>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFavoritePlayoneList(userId: Int): Single<List<PlayoneEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearPlayoneDetail(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun savePlayoneDetail(playoneEntity: PlayoneEntity): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlayoneDetail(playoneId: Int): Single<PlayoneEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearUserEntity(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveUserEntity(userEntity: UserEntity): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserEntityById(userId: Int): Single<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserEntityByEmail(email: String): Single<UserEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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