package com.playone.mobile.data.repository

import com.playone.mobile.data.model.PlayoneEntity
import com.playone.mobile.data.model.UserEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to
 * [com.playone.mobile.data.model.PlayoneItem]. This is to be implemented by external
 * data source layers, setting the requirements for the operations that need to
 * be implemented.
 */
interface PlayoneDataStore {

    fun clearPlayoneList(): Completable

    fun savePlayoneList(playoneList: List<PlayoneEntity>): Completable

    fun getPlayoneList(userId: Int = -1): Single<List<PlayoneEntity>>

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

    fun getUserEntity(userId: Int): Single<UserEntity>

}